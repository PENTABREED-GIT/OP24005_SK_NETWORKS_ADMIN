pipeline {
    agent any
    tools {
        jdk 'jdk-17'
    }
    environment {
        TEST_COMMIT = '#test'
        DEPLOY_COMMIT = '#deploy'
        JSP_FILES_CHANGED = 'nothing'

    }

    stages {
        stage('Preparation') {
            steps {
                script {


                    def recentCommits = sh(script: "git log -2 --pretty=%B", returnStdout: true).trim().split('\n\n')
                    def commitMessage = ''
                    def jspCh = false

                    if (recentCommits.size() > 0 && recentCommits[0].toLowerCase().contains("merge")) {
                        if (recentCommits.size() > 1) {
                            echo "Next commit message after merge: ${recentCommits[1]}"
                            commitMessage = recentCommits[1]
                        } else {
                            echo "No commit message available after merge"
                        }
                    } else {
                        echo "Most recent commit message: ${recentCommits[0]}"
                        commitMessage = recentCommits[0]
                    }

                    if (commitMessage.contains(env.DEPLOY_COMMIT)) {
                        echo "Deploy commit message found: ${commitMessage}"
                        env.SHOULD_DEPLOY = 'true'
                        jspCh = true
                    } else {
                        echo "Deploy commit message not found: ${commitMessage}"
                        env.SHOULD_DEPLOY = 'false'
                        jspCh = false
                    }

                    if (commitMessage.contains(env.TEST_COMMIT)) {
                        echo "Deploy commit message found: ${commitMessage}"
                        env.SHOULD_TEST = 'true'
                    } else {
                        echo "Deploy commit message not found: ${commitMessage}"
                        env.SHOULD_TEST = 'false'
                    }

                    if (jspCh) {
                        echo "FIRST = JSP files changed: ${env.JSP_FILES_CHANGED}"
                        def changedFiles = sh(script: "git diff HEAD^ HEAD --name-only", returnStdout: true).trim().split('\n')
                        def jspFilesChanged = changedFiles.findAll { it.endsWith('.jsp') }
                        def onlyGroovyFilesChanged = changedFiles.findAll { it.endsWith('.groovy') }
                        echo "Changed files: ${changedFiles.join(', ')}"
                        echo "JSP files changed: ${jspFilesChanged.join(', ')}"
                        echo "JSP files size : ${jspFilesChanged.size()}"
                        echo "Changed files size : ${changedFiles.size()}"

                        env.ONLY_JSP_CHANGED = (jspFilesChanged.size() > 0 && jspFilesChanged.size() == changedFiles.size()) ? 'true' : 'false'
                        env.ONLY_GROOVY_CHANGED = (onlyGroovyFilesChanged.size() > 0 && onlyGroovyFilesChanged.size() == changedFiles.size()) ? 'true' : 'false'
                        echo "jspFilesChanged.join(', ') : ${jspFilesChanged.join(', ')}"

                    }
                }
            }
        }

        stage('Deploy JSP Changes') {
            when {
                expression { env.SHOULD_DEPLOY == 'true' }
                expression { env.ONLY_JSP_CHANGED == 'true' }
                expression { env.ONLY_GROOVY_CHANGED == 'false' }
            }
            steps {
                sshagent(credentials: ['centos']) {
                    script {

                        def changedFiles = sh(script: "git diff HEAD^ HEAD --name-only", returnStdout: true).trim().split('\n')
                        def jspFilesChanged = changedFiles.findAll { it.endsWith('.jsp') }

                        sh """
                            ssh -v -o StrictHostKeyChecking=no ec2-user@172.26.6.126 '
                            sudo chown -R ec2-user:ec2-user /home/WebService/SKNETWORKS/ADMIN/www '
                        """
                        jspFilesChanged.each { jspFile ->
                            int viewsIndex = jspFile.indexOf("views/")
                            String relativePath = jspFile.substring(viewsIndex + "views/".length())
                            def remotePath = "/home/WebService/SKNETWORKS/ADMIN/www/WEB-INF/views/${relativePath}"
                            sh "scp /var/lib/jenkins/workspace/skn.admin/${jspFile} ec2-user@172.26.6.126:${remotePath}"
                        }
                        sh "ssh -v ec2-user@172.26.6.126 'sudo chown -R ftpuser:ftpuser /home/WebService/SKNETWORKS/ADMIN/www && sudo chmod -R 775 /home/WebService/SKNETWORKS/ADMIN/www'"
                    }
                }
            }
        }
        stage('Run Tests') {
            when {
                allOf {
                    expression { env.SHOULD_DEPLOY == 'true' }
                    expression { env.ONLY_JSP_CHANGED == 'false' }
                    expression { env.SHOULD_TEST == 'true' }
                    expression { env.ONLY_GROOVY_CHANGED == 'false' }
                }
            }
            steps {
                script {
                    def testDirectory = 'src/test'
                    if (fileExists(testDirectory)) {
                        def testResult = sh(script: '/opt/maven/bin/mvn test', returnStatus: true)

                        if (testResult == 0) {
                            echo 'All tests passed. Proceeding to the next stage.'
                        } else {
                            error 'Tests failed. Aborting the pipeline.'
                        }
                    } else {
                        echo 'No tests found in the specified directory. Skipping tests.'
                    }
                }
            }

        }

        stage('Build') {
            when {
                allOf {
                    expression { env.SHOULD_DEPLOY == 'true' }
                    expression { env.ONLY_JSP_CHANGED == 'false' }
                    expression { env.ONLY_GROOVY_CHANGED == 'false' }
//                    expression { currentBuild.resultIsBetterOrEqualTo('SUCCESS') }
                }
            }
            steps {
                sh '/opt/maven/bin/mvn clean install -DskipTests -Pdev'
            }
        }

        stage('Deploy') {
            when {
                allOf {
                    expression { env.SHOULD_DEPLOY == 'true' }
                    expression { env.ONLY_JSP_CHANGED == 'false' }
                    expression { env.ONLY_GROOVY_CHANGED == 'false' }
                    expression { currentBuild.result == null || currentBuild.result == 'SUCCESS' }
                }
            }

            steps {
                sshagent(credentials: ['ec2-user']) {
                    script {
                        def deployStatus = 1

                        if (env.ONLY_JSP_CHANGED == 'false') {
                            sh """
                                ssh -v ec2-user@172.26.6.126 '
                                sudo chown -R ec2-user:ec2-user /home/WebService/SKNETWORKS/ADMIN/www &&
                                if [ -d /home/WebService/SKNETWORKS/ADMIN/ADMIN_ROLLBACK ]; then
                                    sudo rm -rf /home/WebService/SKNETWORKS/ADMIN/ADMIN_ROLLBACK;
                                else
                                    sudo mkdir /home/WebService/SKNETWORKS/ADMIN/ADMIN_ROLLBACK;
                                fi &&
                                sudo find /home/WebService/SKNETWORKS/ADMIN/www/ -mindepth 1 -maxdepth 1 -not -name \"robots.txt\" -exec mv {} /home/WebService/SKNETWORKS/ADMIN/ADMIN_ROLLBACK/ \\;'
                            """


                            retry(3) {      // 3 try
                                deployStatus = sh(script: """
                                scp -r /var/lib/jenkins/workspace/skn.admin/target/sknadmin-0.0.1-SNAPSHOT.war ec2-user@172.26.6.126:/home/WebService/SKNETWORKS/ADMIN/www &&
                                ssh -v -o StrictHostKeyChecking=no ec2-user@172.26.6.126 '
                                cd /opt/tomcat10/instances/sknadmin &&
                                sudo sh shutdown-sknadmin.sh  &&
                                sudo unzip -o /home/WebService/SKNETWORKS/ADMIN/www/sknadmin-0.0.1-SNAPSHOT.war -d /home/WebService/SKNETWORKS/ADMIN/www &&
                                sudo rm -rf /home/WebService/SKNETWORKS/ADMIN/www/sknadmin-0.0.1-SNAPSHOT.war &&
                                sudo rm -rf /home/WebService/SKNETWORKS/ADMIN/www/org &&
                                sudo rm -rf /home/WebService/SKNETWORKS/ADMIN/www/META-INF &&
                                sudo sh startup-sknadmin.sh'
                            """, returnStatus: true)

                                if (deployStatus != 0) {
                                    sleep(time: 15, unit: 'SECONDS')
                                }
                            }
                        }

                        if (deployStatus != 0) {  // 실패
                            sh "ssh -v ec2-user@172.26.6.126 'sudo chown -R ftpuser:ftpuser /home/WebService/SKNETWORKS/ADMIN/www && sudo chmod -R 775 /home/WebService/SKNETWORKS/ADMIN/www'"
                            echo "배포 실패, 롤백을 시작합니다."
                            def rollbackStatus = sh(script: "ssh -v ec2-user@172.26.6.126 'sudo rm -rf /home/WebService/SKNETWORKS/ADMIN/www/WEB-INF " +
                                    "&& sudo rm -rf /home/WebService/SKNETWORKS/ADMIN/www/assets " +
                                    "&& sudo rm -rf /home/WebService/SKNETWORKS/ADMIN/www/META-INF " +
                                    "&& sudo mv /home/WebService/SKNETWORKS/ADMIN/ADMIN_ROLLBACK/* /home/WebService/SKNETWORKS/ADMIN/www " +
                                    "&& cd /opt/tomcat/instance/sknadmin && sudo sh shutdown-sknadmin.sh" +
                                    "&& sudo sh startup-sknadmin.sh'", returnStatus: true)
                            if (rollbackStatus != 0) {
                                echo "롤백 실패"
                            } else {
                                echo "롤백 성공"
                            }
                        }

                        sh "ssh -v ec2-user@172.26.6.126 'sudo chown -R ftpuser:ftpuser /home/WebService/SKNETWORKS/ADMIN/www && sudo chmod -R 775 /home/WebService/SKNETWORKS/ADMIN/www'"
                    }
                }
            }
        }

    }

    post {
        // 항상 실행
        always {
            echo 'This will2 always run'
            echo 't2e132t2213312313'
        }
        // 이번 빌드가 성공일때 실행
        success {
            echo 'Build success.'
            // TODO  배포 성공 시 알림 추가 작업 예정 2024-01-04
        }
        // 지난번 빌드와 빌드 결과가 다를때 실행
        changed {
            echo 'Build result changed.'
        }
        // 지난번 빌드는 실패이고 이번에 성공했을때 실행
        fixed {
            echo 'Build fixed.'
        }
        // 지난번 빌드는 성공이고, 이번에는 실패, unstable,aborted 될때 실행
        regression {
            echo 'Build regression.'
        }
        // 이번 빌드가 aborted 되었을때 실행
        aborted {
            echo 'Build aborted.'
        }
        // 이번 빌드가 실팯 되었을때 실행
        failure {
            echo 'Build failed.'
        }
        // 이번 빌드가 unstable 일때 실행
        unstable {
            echo 'Build unstable.'
        }
        // 이번 빌드가 성공이 아닐때 실행
        unsuccessful {
            echo 'Build unsuccessful.'
        }
    }
}


