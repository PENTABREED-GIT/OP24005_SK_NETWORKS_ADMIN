// 이미지 업로드 테스트 function
function dropzoneCheck(previewValue) {

    let dropzoneCount = 0;
    let isFileUpload = true;
    document.querySelectorAll('.dropzone-box').forEach(el => {
        // if(el.style.display === 'none') {
            dropzoneCount++;
        // }
    })

    if(typeof (ntdrop) != 'undefined') {

        if (ntdrop.length > 0) {
            // check required
            for(let i=0;i<ntdrop.length;i++) {

                if ((!previewValue && ntdrop[i].options.required === 'Y') || (previewValue && previewValue === 'PDF' && (ntdrop[i].options.required === 'Y' || ntdrop[i].options.previewCheck === 'Y'))) {
                    const dropzoneContainer = document.getElementById('dropzoneImage' + ntdrop[i].options.dropzoneIdx);
                    // x 버튼 div 태그 id 체크로 변경
                    if(dropzoneContainer == null) {
                        const dropzoneContainer = document.getElementById(ntdrop[i].options.containerId);
                        const errorLength = dropzoneContainer.getElementsByClassName('fv-plugins-message-container invalid-feedback file_error');
                        if(errorLength.length  === 0) {
                            let errorSpan = document.createElement('span')
                            errorSpan.classList.add('fv-plugins-message-container');
                            errorSpan.classList.add('invalid-feedback');
                            errorSpan.classList.add('file_error');
                            errorSpan.textContent = ntdrop[i].options.messages.empty;
                            dropzoneContainer.appendChild(errorSpan);
                        }
                        isFileUpload = false;
                    }
                }
            }

            for(let i=0;i<ntdrop.length;i++) {
                if (ntdrop[i].files.length > 0) {
                    if (ntdrop[i].files[0].error) {
                        isFileUpload = false;
                    }
                }
            }

            // call upload recursive
            // if(isFileUpload) {
            //     uploadByDropzone(0);
            // }
        }

        return isFileUpload;
    } else {
        // dropzone 없을 시 true 리턴
        return true;
    }
}

async function uploadByDropzone(index) {
    try {
        if (ntdrop[index].files.length > 0) {
            ntdrop[index].upload(async function (result) {
                if (!result.error) {
                    if (index < (ntdrop.length - 1)) {
                        index++;
                        uploadByDropzone(index);
                    } else {
                        await dropZoneDeleteFile();
                        saveFormData();
                    }
                } else {
                    onSubmitError(result.errorMsg);
                }
            });
        } else {
            if (index < (ntdrop.length - 1)) {
                index++;
                uploadByDropzone(index);
            } else {
                await dropZoneDeleteFile();
                saveFormData();
            }
        }
    } catch (e) {
        console.log(e);
    }

}

async function dropZoneDeleteFile() {
    const deleteUid = document.querySelectorAll('[data-drop-zone-delete-uid="Y"]');

    if (deleteUid.length > 0) {
        let deleteUidArray = [];
        deleteUid.forEach(el => {
            deleteUidArray.push(el.value);
        });

        const response = await fetch('/api/v1/file/file-delete/delete', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                Uid: deleteUidArray
            })
        });
    }
}

function onSubmitError(r) {
    Swal.fire({
        html: r,
        icon: "error",
        buttonsStyling: !0,
        customClass: {confirmButton: "btn btn-primary"}
    })
    global.LoadingOverlay.hide();
}

function getErrorElements() {
    let errorEl = [];

    document.querySelectorAll('span.fv-plugins-message-container.invalid-feedback').forEach(el => {
        if(el.textContent !== '') {
            errorEl.push(el);
        }
    });

    return errorEl;
}