/*
 * global.js

 * global variables & method in all pages
 * 2015 02 25 hjkim
 */
/*
 * global
 * root namespace
 */
let global = {};

/*
 * global.onSubmitValidForm
 * xenon.custom.js 의 validation을 이용하는 페이지에서, (필요시) callback function 으로 정의하여 사용.
 * global.onSubmitValidForm = function (form) {};
 * (argument >> form : html form object)
 * 2015 02 25 hjkim
 */
global.onSubmitValidForm = null;

/*
 * global.onSelectDateRange
 * xenon.custom.js 의 daterangepiecker를 이용하는 페이지에서, (필요시) callback function 으로 정의하여 사용.
 * global.onSelectDateRange = function (startDate, endDate) {};
 * (argument >> startDate : daterangepicer에서 선택한 시작 날, endDate : daterangepicer에서 선택한 끝 날)
 * 2015 03 11 hjkim
 */
global.onSelectDateRange = null;

/*
 * global.Modal
 * bootstrap panel 형태의 Modal을 생성, 관리 해준다.
 * 2015 03 10 hjkim
 * parameter : (JSON) settings
 * 		settings.cls
 * 			(String) bootstrap에서 제공하는 panel class명의 일부. 예를 들어 panel-warning을 사용할 경우, settings.cls = 'warning'
 * 		settings.title
 * 			(String) modal title text. html string 가능
 * 		settings.body
 * 			(String) modal contents text. html string 가능
 * 		settings.fbutton
 * 			(JSON) {기능버튼1:{cls:'', text:'', action:, actionParam:}, 기능버튼2:{cls:'', text:'', action:, actionParam:}} 와 같은 형태
 * 			settings.fbuttons.기능버튼1.cls
 * 				(String) bootstrap에서 제공하는 btn class명의 일부. 예를 들어 btn-warning을 사용할 경우, settings.fbuttons.기능버튼1.cls = 'warning'
 * 			settings.fbuttons.기능버튼1.text
 * 				(String) button text (name)
 *			settings.fbuttons.기능버튼1.action
 *				(function) 이 버튼을 클릭했을 경우 호출할 함수. (default : Modal 닫음. (modal hide))
 *				이 함수에는 기본적으로 가장 첫 번째 parameter로 현재 modal object(this)를 넘겨준다.
 *			settings.fbuttons.기능버튼1.actionParam
 *				action function이 호출될 때 전달 될 parameter.
 *			settings.fbuttons.기능버튼1.isHideActionEnd
 *				action function호출 후 modal hide 여부 (default: true)
 */
global.SimpleModal = {
	dom : {$modal:null, $heading:null, $title:null, $body:null, $footer:null},
	buttons : {},
	init : function(){
		if(this.dom.$modal != null){
			this.setHeadingCls('warning');
			this.setTitleText('Simple Modal - Warining');
			this.setBodyText('Modal Contents');
			this.setFooterBtn({
		   		confirm:{cls:'warning', text:'Close', action:null}
			});
			return;
		}
		var containerDOMString = '';
		containerDOMString += '<div class="modal fade">';
		containerDOMString += '<div class="modal-dialog" style="max-width:400px;">';
		containerDOMString += '<div class="panel panel-color panel-warning">'; 				// panel-this.def.cls
		containerDOMString += '<div class="panel-heading">';
		containerDOMString += '<h3 class="panel-title">Simple Modal - Warining</h3>';					//'+this.def.title+'
		containerDOMString += '</div>';
		containerDOMString += '<div class="panel-body">';
		containerDOMString += '<div class="modal-body">Modal Contents</div>';
		//containerDOMString += this.def.body;
		containerDOMString += '';
		containerDOMString += '<div class="modal-footer">';
		containerDOMString += '<button type="button" class="btn btn-warning pull-right" data-dismiss="modal">gg</button>';
		containerDOMString += '</div>';
		containerDOMString += '</div>';
		containerDOMString += '</div>';
		containerDOMString += '</div>';
		containerDOMString += '</div>';

		this.dom.$modal = $(containerDOMString);
		this.dom.$heading = this.dom.$modal.find('.panel.panel-color');
		this.dom.$title = this.dom.$modal.find('.panel-title');
		this.dom.$body = this.dom.$modal.find('.modal-body');
		this.dom.$footer = this.dom.$modal.find('.modal-footer');
	},
	setHeadingCls : function(pCls){
		this.dom.$heading.removeClass();
		this.dom.$heading.addClass('panel panel-color panel-'+pCls)
	},
	setTitleText : function(t){
		this.dom.$title.html(t);
	},
	setBodyText : function(t){
		this.dom.$body.html(t);
	},
	setFooterBtn : function(btnListObj){
		if(typeof(btnListObj) == 'object'){
			this.dom.$footer.find('button').remove();
			this.buttons = btnListObj;
			for(var btn in this.buttons){
				var $button = $('<button type="button" class="btn pull-right btn-'+this.buttons[btn].cls+'">'+this.buttons[btn].text+'</button>');
				if(typeof(this.buttons[btn].action) != 'function'){
					$button.attr('data-dismiss', 'modal');
				}else{
					var b = this.buttons[btn];
					var t = this;
					$button.click(function(){
						if(Object.prototype.toString.call(b.actionParam) === '[object Array]'){
							b.action.apply(null,b.actionParam);
						}else{
							b.action(b.actionParam);
						}
						if(b.isHideActionEnd !== false) t.hide();
					});
				}
				this.dom.$footer.append($button);
			}
		}
	},
	show : function(actionParam){
		for(var b in actionParam){
			this.buttons[b].actionParam = actionParam[b];
		}
		this.dom.$modal.modal('show', {backdrop: 'static'});
	},
	hide : function(){
		this.dom.$modal.modal('hide');
	}
};

/*
 * global.LoadingOverlay
 * xenon theme의 page loading overlay 생성/관리
 * 2015 03 19 hjkim
 * */
global.LoadingOverlay = {
	show : function(){
		var $pdom = $('.page-loading-overlay');
		var $cdom = $pdom.find('.loader-2');
		if($cdom.length==0){
			$pdom = $('<div class="page-loading-overlay"></div>');
			$cdom = $('<div class="loader-2"></div>');
			$('body').append($pdom.append($cdom));
			return;
		}
		$pdom.removeClass('loaded');
	},
	hide : function(){
		$('.page-loading-overlay').addClass('loaded');
	}
};

/*
 * global.Validation
 * validation check core function
 * 각종 포맷을 체크 함
 * xenon-custom.js, 각자 페이지에서 필요시 사용
 * 2015 05 13 hjkim
 * */
global.Validation = {
	// 모두 한글(자)인지 체크
	isKoWord:function(value){
		return !((/[^가-힣]/).test(value));
	},
	// 모두 영문인지 체크
	isEnWord:function(value){
		return !((/[^a-zA-Z]/).test(value));
	},
	// 유효 전화번호 형식인지 체크
	isPhoneNo:function(value, type){
		/* 국가번호를 포함한 전화번호 형식
		 * '+' 기호를 포함한 국가번호로 시작하여, 숫자와 '-'만 입력 가능 (Ex. +82-2-736-0226)
		 * 길이(length)는 제한하지 않음
		 * 숫자는 15개 이하 (ITU-T Recommandation)
		 */
		if(type=='global'){
			var b = (/^\+[1-9]{1,2}-?[0-9]{0,3}[0-9\-]{1,13}[0-9]$/).test(value);
			if(!b)
				return false;
			var tArr = value.match(/[0-9]/g);
			if(tArr == null)
				return false;
			if(tArr.length > 15)
				return false;

			return true;
		/* 국내전용 전화번호 형식 체크
		 * 번호(2~4자리)-번호(3~4자리)-번호(4자리) 형식 (Ex. 02-736-0226)
		 */
		}else if(type=='ko'){
			return (/^0[1-9][0-9]{0,2}-[0-9]{3,4}-[0-9]{4}$/).test(value);
		}else if(type=='ko-mobile'){
			return (/^01(0|1|6|7|8|9)-[0-9]{3,4}-[0-9]{4}$/).test(value);
		}
	},
	/* 날짜 형식인지 체크 (실제 존재하는 날 인지는 체크 안함)
	 * type == 'month' : yyyy-MM 형식인지 체크
	 * type == undefined or type == null or type == '' : yyyy-MM-dd 형식인지 체크
	 * 2015 05 18 hjkim
	 * */
	isDate:function(type, value){
		if(type == 'month'){	//yyyy-MM
			return (/^(1|2)[0-9]{3}-(0[1-9]|1[1-2])$/).test(value);
		}
		return (/^(1|2)[0-9]{3}-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/).test(value);
	},
	/* 시간 형식인지 체크 (구분자는 ':')
	 * type == 'mm' : 분 단위 까지
	 * 2015 08 19 hjkim
	 * */
	isTime:function(type, value){
		//hh:mm
		if(type=='mm'){
			if(value.length!=4 && value.length!=5)
				return false;
			var hmlist = value.split(':');
			if(hmlist.length==2){
				if(parseInt(hmlist[0], 10)<24 && parseInt(hmlist[1], 10)<60)
					return true;
			}
		}
		return false;
	}
};


/*
 * global.genDropzone
 * drag & drop 지원하는 파일첨부 영역 처리, 관련 유틸리티 생성 후 dropzone.js 의 dropzone 객체 return
 * http://www.dropzonejs.com/
 * required /assets/js/dropzone/dropzone.min.js , 각자 페이지에서 필요시 사용
 * 2015 08 04 hjkim
 *
 * * dropzone.info.upload(callback) 를 호출하여 upload
 * * 다른 form과 함께 사용시 callback function을 구현하여 사용
 * */
global.genDropzone = function(selector, options) {
	if(typeof options == 'undefined' || options==null)
		options = {};
	let defaultExtensions = ".pdf,.docx,.xlsx,.pptx,.jpg,.jpeg,.gif,.png,.bmp,.ai, webp, apng, svg";
	var url = typeof options.url!='undefined'?options.url:'/api/v1/file/file-upload';
	var paramName = typeof options.paramName!='undefined'?options.paramName:'files';
	var maxFileCount = typeof options.maxFileCount!='undefined'?options.maxFileCount:null;
	let acceptedExt = typeof options.acceptedExt!='undefined'?options.acceptedExt:[];
	var hasOrder = typeof options.hasOrder!='undefined'?options.hasOrder:true;
	var isSecure = typeof options.isSecure!='undefined'?options.isSecure:true;
	var required = typeof options.required!='undefined'?options.required:'';
	var description = typeof options.description!='undefined'?options.description:false;
	var previewCheck = typeof options.previewCheck!='undefined'?options.previewCheck:'';
	var dropzoneIdx = typeof options.dropzoneIdx!='undefined'?options.dropzoneIdx:'';
	var containerId = typeof options.containerId!='undefined'?options.containerId:'';
	var messages = typeof options.messages!='undefined'?options.messages:{};
	var maxFileSize = typeof options.maxFileSize!='undefined'?options.maxFileSize:'';
	var dropzoneIndex = typeof options.dropzoneIndex!='undefined'?options.dropzoneIndex:'';

	if(maxFileCount!=null && parseInt(maxFileCount, 10)==1){
		hasOrder = false;
	}

	// for(var i=0; i<acceptedExt.length; i++){
	// 	if(acceptedFiles=='')
	// 		acceptedFiles = global.getMIMEType(acceptedExt[i]);
	// 	else
	// 		acceptedFiles += ','+ global.getMIMEType(acceptedExt[i]);
	// }
	let acceptedFiles = '';
	if (acceptedExt.length > 0) {
		// Convert extensions array to MIME types and concatenate with comma
		acceptedFiles = acceptedExt.map(ext =>  ext).join(',');
	} else {
		acceptedFiles = defaultExtensions;
	}
	var params = options.params;
	var dzTableClassName = 'p_dropzoneFileTable';

	var nestableClass = 'nestable-list-item';
	let uploadResult = null;
	var dropzone = new Dropzone(selector, {
		dictMaxFilesExceeded:'업로드 가능 최대 파일 개수 초과',
		params:params,
		url:url,
		async: false,
		required:required,
		autoProcessQueue:false,
		paramName:paramName,
		maxFiles:maxFileCount,
		maxFileCount:maxFileCount,
		maxFilesize:maxFileSize,
		parallelUploads:maxFileCount,
		acceptedFiles:acceptedFiles,
		createImageThumbnails:true,
		hasOrder:hasOrder, // 20160829 hjkim
		isSecure:isSecure, // 20210722 min
		dictFileTooBig:'파일 크기가 너무 큽니다. ({{filesize}}MB). 최대 허용 크기: {{maxFilesize}}MB.',
		uploadMultiple:true,
		previewCheck:previewCheck,
		dropzoneIdx:dropzoneIdx,
		containerId:containerId,
		messages: messages,
		dropzoneIndex: dropzoneIndex,
		maxfileCountreached:function(f){
			//console.log('maxFileCountreached');
			//this.options.error(f);
		},

		init: function() {
			var _this = this;
			setupHiddenFileInput = function() {
				if (_this.hiddenFileInput) {
					_this.hiddenFileInput.parentNode.removeChild(_this.hiddenFileInput);
				}
				_this.hiddenFileInput = document.createElement("input");
				_this.hiddenFileInput.setAttribute("type", "file");
				_this.hiddenFileInput.setAttribute("multiple", "multiple");
				_this.hiddenFileInput.className = "dz-hidden-input";
				_this.hiddenFileInput.setAttribute("accept", acceptedFiles);  // 설정된 확장자 적용
				_this.hiddenFileInput.style.visibility = "hidden";
				_this.hiddenFileInput.style.position = "absolute";
				document.body.appendChild(_this.hiddenFileInput);
				_this.hiddenFileInput.addEventListener("change", function() {
					var files = _this.hiddenFileInput.files;
					for (var i = 0; i < files.length; i++) {
						_this.addFile(files[i]);
					}
					_this.emit("addedfiles", files);
					setupHiddenFileInput();
				});
			};
			setupHiddenFileInput();
			this.on("complete", function(file) {
				if (this.hiddenFileInput) {
					this.hiddenFileInput.value = '';
				}
			});

		},

		maxfilesexceeded:function(f){
			//console.log('maxFileCountexceeded');
			//this.removeFile(f);
		},
		thumbnail: function(file, data) {
			let thumbnailHtml = '<img src="'+data+'">';
			var $entry = $(thumbnailHtml);

			// 파일 객체에 저장된 고유 ID를 사용하여 썸네일 추가
			this.$dropzoneContainer.find('#' + file.previewElementId).append($entry);

		},
		addedfile: function(file){
			// 현재 Dropzone 인스턴스의 파일 배열 복사본 생성
			let existingFiles = this.files.slice();

			// 현재 인스턴스에서만 파일 제거
			for (let i = 0; i < existingFiles.length; i++) {
				// 현재 파일이 추가된 파일과 다르면 삭제
				if (existingFiles[i] !== file) {
					this.removeFile(existingFiles[i]);
				}
			}

			try {
				const dropzoneContainer = document.getElementById(this.options.containerId);
				const errorLength = dropzoneContainer.getElementsByClassName('file_error');
				if(errorLength.length > 0) {
					dropzoneContainer.getElementsByClassName('file_error').forEach(el => { el.remove() })
				}
			} catch (e) {
				console.log(e);
				console.log('드롭존 에러 메시지 삭제 오류');
			}

			// FileFormINC  에 있는 dropzone box 를 숨김처리
			let dropzoneBoxId = 'dropzone' + this.options.dropzoneIdx;
			let replaceImageIdx = dropzoneBoxId.replace('dropzone', 'dropzoneImage');
			const dropzoneBox = document.getElementById(dropzoneBoxId);
			if (dropzoneBox) {
				dropzoneBox.style.display = 'none';
			}

			// 비필수 첨부파일 교체시 삭제용으로 생성한 uid dom 삭제
			let deleteUidId = 'deleteUidArr' + this.options.dropzoneIdx;
			//console.log(deleteUidId);
			let deleteUidEl = document.getElementById(deleteUidId);
			if(deleteUidEl) {
				deleteUidEl.remove();
			}


			file.error = false;
			if (acceptedFiles != null){
				// if(file.type.split('/')[0] != 'image'){
				// 	file.error = true;
				// 	file.errorMsg = '허용하지 않는 형식의 파일이 있습니다.';
				// 	this.removeFile(file);
				// 	return;
				// }
			}

			let size = parseInt(file.size / 1024, 10);
			size = size < 1024 ? (size + ' KB') : (parseInt(size / 1024, 10) + ' MB');

			let uniquePreviewId = 'previewImg-' + Date.now();  // 고유한 ID 생성
			file.previewElementId = uniquePreviewId;  // 파일 객체에 고유 ID 저장

			let entry = '<tr id="' +replaceImageIdx + '">';
			if (this.options.hasOrder) {
				entry += '<td class="uk-nestable-handle text-center" width="43"><i class="fa fa-sort"></i></td>';
			}
			entry += '<td width="43" class="text-center">';
			entry += '<input type="hidden" class="p_fparam" name="FileIndex" value="" />';
			entry += '<input type="hidden" class="p_fparam" name="FileUid" value="" />';
			entry += '<input type="hidden" class="p_fparam" name="DeleteYN" value="" />';
			let isSecureVal = this.options.isSecure;
			if (this.options.isSecure != '' && this.options.isSecure != null) {
				entry += '<input type="hidden" class="p_fparam" name="IsSecure" value="'+isSecureVal+'" />';
			}
			entry += '            <a class="btn btn-sm btn-icon btn-danger" data-id="' + dropzoneBoxId + '" data-dropzone-index="' + dropzoneIndex +'" onclick="global.dropzoneDeleteFile(this)"><i class="icon-xl fas fa-times"></i></a></td>';
			entry += '<td id="valid' + this.options.dropzoneIdx + '">'+file.name+' ('+size+')';
			if (description) {
				entry += '<div style="margin:5px 0;"><input type="text" class="form-control p_fparam" name="Description" placeholder="대체텍스트" /></div>';
			} else {
				entry += '<input type="hidden" class="form-control p_fparam" name="Description" placeholder="설명" />';
			}

			entry += '<input type="hidden" class="p_fparam" name="SortOrder" value="' + $('.' + dzTableClassName).find('.' + nestableClass).length + '" />';
			entry += '</td>';
			entry += '<td id="' + uniquePreviewId + '"></td>';  // 고유한 ID로 컨테이너 생성

			if (this.options.isSecure == '' || this.options.isSecure == null) {
				entry += '<td><label class="form-check form-switch form-check-custom form-check-solid">';
				entry += '<input class="form-check-input p_fparam" name="IsSecure" type="checkbox" value="Y" />';
				entry += '</label></td>';
			}
			entry += '</tr>';
			let $entry = $(entry);

			if (this.options.hasOrder)
				$entry.addClass(nestableClass);

			//취소 버튼 click function
			let thisobj = this;
			$entry.find('.p_cancelBtn').click(function(){thisobj.removeFile(file);});
			this.$dropzoneContainer.find('tbody').append($entry);
			//// ->>>>>>>> 여기여기
			if (parseInt(maxFileCount,10) == 1) {	//여러 파일 첨부를 지원하지 않는다면, 파일 첨부 후 dropzone을 없애자.
				this.removeFileDropzone();
			} else
				if(this.$dropzoneContainer.find('input[name=DeleteYN]').length ==0) {
				if (this.isMax(true))
					this.removeFileDropzone();
			}
			file.$fileInfoTr = $entry;
			file.$progressBar = $entry.find('.progress-bar');
		},
		removedfile:function(file){


			if(typeof file.$fileInfoTr!='undefined'){
				file.$fileInfoTr.remove();
			}

			if(!this.hasDropzone && !this.isMax(true)){
				this.appendFileDropzone();
			}

		},
		processing:function(file){},
		queuecomplete:function(file){},
		uploadprogress: function(file, progress, bytesSent){
			file.$progressBar.width(progress + '%');
		},
		sending:function(file, xhr, form){

			$paramList = file.$fileInfoTr.find('.p_fparam');
			for(var i=0; i<$paramList.length; i++){
				param = $paramList[i];
				form.append(param.name, param.value);
			}
			file.$fileInfoTr.addClass('p_sended');
			this.info.sendCheckList.push(1);
		},
		success: function(file){

			let result = JSON.parse(file.xhr.responseText);
			uploadResult = result;
			if(result.resultCode === "FAIL") {
				this.removeFile(file);
				file.error = true;
				file.errorMsg = '파일첨부 처리 중 오류가 발생했습니다. 다시 시도 해 주세요';
				file.$progressBar.removeClass('progress-bar-warning').addClass('progress-bar-red');
				file.$progressBar.parent().after('<span class="text-danger">Failed(02)</span>');
				Swal.fire({
					html: "Please check the extension of the attached file",
					icon: "error",
					buttonsStyling: !0,
					customClass: {confirmButton: "btn btn-primary"}
				})
				return;
			}
			if(file.xhr.responseText == 'null' || file.xhr.responseText == '' || file.xhr.responseText == null){
				file.error = true;
				file.errorMsg = '파일첨부 처리 중 오류가 발생했습니다. 다시 시도 해 주세요. (02)';
				file.$progressBar.removeClass('progress-bar-warning').addClass('progress-bar-red');
				file.$progressBar.parent().after('<span class="text-danger">Failed</span>');
				return;
			}
			file.savedInfo = file.xhr.responseText;
			file.$progressBar.removeClass('progress-bar-warning').addClass('progress-bar-success');
			//file.$fileInfoTr.find('td:last').html('<span class="text-success">Uploaded</span>');
			if(result.resultCode === "SUCCESS") {
				file.$fileInfoTr.find('td:last').append('<span class="text-success">Uploaded</span>');
			}

			if(result.resultCode === "FAIL") {
				file.$fileInfoTr.find('td:last').append('<span class="text-danger">Failed</span>');
			}

			if(this.getQueuedFiles().length>0)
				this.processQueue();
		},
		error: function(file, msg){
			file.error = true;
			file.errorMsg = '파일첨부 처리 중 오류가 발생했습니다. 다시 시도 해 주세요. (03)';

			const dropzoneContainer = document.getElementById(this.options.containerId);
			const errorLength = dropzoneContainer.getElementsByClassName('fv-plugins-message-container invalid-feedback file_error');
			if(errorLength.length  === 0) {
				let errorSpan = document.createElement('span')
				errorSpan.classList.add('fv-plugins-message-container');
				errorSpan.classList.add('invalid-feedback');
				errorSpan.classList.add('file_error');
				errorSpan.textContent = this.options.messages.accept;
				dropzoneContainer.appendChild(errorSpan);
			}

			if(typeof file.$progressBar != 'undefined'){
				if(file.$progressBar.length>0){
					file.$progressBar.removeClass('progress-bar-warning').addClass('progress-bar-red');
					file.$progressBar.parent().after('<span class="text-danger">Failed(03) '+msg+'</span>');
				}
			}

		},
		complete: function(file){

			this.info.sendCheckList.pop();
			if(this.info.sendCheckList.length == 0){
				var files = this.files;
				var hasError = false;
				var errorMsg = '';
				this.info.fileList=[];
				for(i=0; i<files.length; i++){
					if(this.files[i].error){
						hasError = true;
						if(errorMsg !='')
							errorMsg += '<br/>';
						errorMsg += files[i].errorMsg;
					}else{
						this.info.fileList.push(files[i].savedInfo);
					}
				}
				if(hasError){

					if(typeof this.onEnd != 'undefined'){
						this.onEnd({error:true, errorMsg:errorMsg, fileInfo:null, deleteFile:file.deleteFile, dropzone:file.dropzone});
					}else{
						//alert(errorMsg);
					}
					return;
				}

				//submit
				// if(typeof this.onEnd != 'undefined'){
				// 	this.onEnd({error:false, errorMsg:'', fileInfo:this.info.fileList});
				// 	//this.onEnd({error:false, errorMsg:'', fileInfo:'['+this.info.fileList.toString()+']'});
				// }
			}
		},
		completemultiple: function(file){

			if(uploadResult.resultCode === "SUCCESS") {
				dropzone.sendAddedForm();
			}
		}
	});
	dropzone.$dropzoneContainer = $(selector).closest('.'+dzTableClassName);
	dropzone.$fileDropzone = dropzone.$dropzoneContainer.find('tfoot');
	dropzone.hasDropzone = true;
	dropzone.removeFileDropzone = function(){
		this.$fileDropzone.remove();
		this.hasDropzone = false;
	};
	dropzone.appendFileDropzone = function(){
		this.$dropzoneContainer.append(this.$fileDropzone);
		this.hasDropzone = true;
	};

	dropzone.info = {};
	dropzone.info.fileList = [];
	dropzone.info.sendCheckList = [];
	dropzone.isMax = function(bReached){
		var maxFileCount = this.options.maxFileCount;
		if(maxFileCount!=null){
			var fCount = this.$dropzoneContainer.find('tbody>tr').length;
			var dCount = this.$dropzoneContainer.find('input[name=DeleteYN]:checked').length;
			if(bReached){
				if((fCount-dCount)==maxFileCount){
					return true;
				}
			}
			if((fCount-dCount)>maxFileCount){
				return true;
			}
		}
		return false;
	};
	dropzone.upload = function (callback) {

		if(typeof callback!='undefined'){

			this.onEnd = callback;
		}

		if(this.isMax()){

			this.onEnd({error:true, errorMsg:'업로드 가능 최대 파일 개수 초과 (최대:'+this.options.maxFileCount+')', fileInfo:null});
			return;
		}

		if(this.getQueuedFiles().length>0){

			this.processQueue();
			//return;
		}else{

			this.sendAddedForm();
			/*
			if(typeof this.onEnd!='undefined'){
				console.log('called here onEnd3333');
				this.onEnd({error:false, errorMsg:'', fileInfo:this.info.fileList});
				//this.onEnd({error:false, errorMsg:'', fileInfo:'['+this.info.fileList.toString()+']'});
				//this.onEnd({error:false, errorMsg:'', fileInfo:''});

			}
			*/
		}
		/*
		else{
			if(this.$dropzoneContainer.find('tbody>tr').length == 0){
				this.onEnd({error:this, errorMsg:'NOFILE', fileInfo:this.info.fileList});
				return;
			}

			var newForm = document.createElement('form');
			var $paramList = this.$dropzoneContainer.find('.p_fparam');
			for(var i=0; i<$paramList.length; i++){
				newForm.appendChild($paramList[i]);
			}
			//console.log(newForm);


			this.uploadFiles([ ], newForm);
			//console.log(this.$dropzoneContainer.closest('form').get(0));
			console.log('uploadfiles:after');
			return;
		}


		*/

	};

	dropzone.sendAddedForm = function(){

		var newForm = document.createElement('form');
		var $paramList = this.$dropzoneContainer.find('tr:not(.p_sended) .p_fparam');
		for(var i=0; i<$paramList.length; i++){

			if($paramList[i].type == 'checkbox'){
				if(!$paramList[i].checked){
					$paramList[i].type = 'hidden';
					$paramList[i].value = '';
				}
			}

			newForm.appendChild($paramList[i]);
		}
		var onEndFunc = this.onEnd;
		var data = $(newForm).serialize();
		if(data!=''){
			if (this.options.params) {
				_ref1 = this.options.params;
				for (key in _ref1) {
					data += '&'+key+'='+_ref1[key];
				}
			}

			data += '&'+this.options.paramName+'=';

			// lucy filter 사용 시 FormData 타입으로 data 전송
			var fileData = new FormData(newForm);
			if (this.options.params) {
				_ref1 = this.options.params;
				for (key in _ref1) {
					value = _ref1[key];
					fileData.append(key, value);
				}
			}

			$.ajax({
				url: this.options.url
				, method: 'post'
				,processData: false // Don't process the files
				,contentType: false
				, data:fileData
				, beforeSend : function(xhr){
					xhr.setRequestHeader("X-CSRF-TOKEN", document.querySelector('meta[name=csrf-token]').getAttribute('content'));
					xhr.setRequestHeader("Accept", "application/json");
					xhr.setRequestHeader("Cache-Control", "no-cache");
					xhr.setRequestHeader("X-Requested-With", "XMLHttpRequest");
				}
				, complete: function(){
					if(typeof onEndFunc!='undefined'){

						onEndFunc({error:false, errorMsg:''});
					}
				}
			});
		}else{
			onEndFunc({error:false, errorMsg:''});
		}
	}
	return dropzone;
}

/*
 * global.MIMETypeList, global.getMIMEType
 * 확장자를 전달하면 해당하는 MIME Type을 return
 * 타입은 필요시 추가하자.
 * 2015 08 13 hjkim
 */
global.MIMETypeList = {
	'doc':'application/msword',
	'xls':'application/vnd.ms-excel',
	'ppt':'application/vnd.ms-powerpoint',
	'hwp':'application/haansofthwp',
	'pdf':'application/pdf',
	'docx':'application/vnd.openxmlformats-officedocument.wordprocessingml.document',
	'xlsx':'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
	'pptx':'application/vnd.openxmlformats-officedocument.presentationml.presentation',
	'html':'text/html',
};
global.getMIMEType = function(ext){
	return global.MIMETypeList[ext];
};

global.changePageListSize = (pageListSize) => {
	const url = new URL(location.href);
	url.searchParams.set('pageNo', '1');
	url.searchParams.set('pageListSize', pageListSize);

	location.href = url.toString();
}

global.changePage = (pageNo) => {
	const url = new URL(location.href);
	url.searchParams.set('pageNo', pageNo);

	location.href = url.toString();
}

global.dropzoneDeleteFile = function (element) {
	let idx = element.getAttribute('data-id');
	let dropzoneIndex = element.dataset.dropzoneIndex;
	let replaceImageIdx = idx.replace('dropzone', 'dropzoneImage');
	const test = document.getElementById(replaceImageIdx);
	const table = test.parentElement;

	test.remove();
	test.style.display = 'none';

	let replaceIdx = idx.replace('dropzone', '');
	try {
		const dropzoneContainer = document.getElementById(replaceIdx + `Container`);
		const errorLength = dropzoneContainer.getElementsByClassName('file_error');
		if(errorLength.length > 0) {
			dropzoneContainer.getElementsByClassName('file_error').forEach(el => { el.remove() })
		}
		ntdrop[dropzoneIndex].files = [];
		let dropzone = ntdrop[dropzoneIndex];
		if (dropzone && dropzone.hiddenFileInput) {
			dropzone.hiddenFileInput.value = '';

		}
	} catch (e) {
		console.log('드롭존 에러 메시지 삭제 오류');
	}

	// 비필수 첨부파일 삭제하기 위해 추가
	try {
		if(element.dataset.fileUid) {
			// if(ntdrop[dropzoneIndex].options.required === 'N') {
				const deleteUid = element.dataset.fileUid;
				const deleteHtml =
					`<tr id="deleteUidArr` + ntdrop[dropzoneIndex].options.dropzoneIdx + `" style="display: none">
						<td>
							<input type="hidden" data-drop-zone-delete-uid="Y" value="`+deleteUid+`">
						</td>
					</tr>`
				table.innerHTML = deleteHtml;
			// }
		}
	} catch (e) {
		console.log(e);
		console.log('드롭존 에러 메시지 삭제 오류1');
	}

	var parentElement = document.getElementById(replaceIdx);
	if (parentElement) {
		var dropzone = parentElement.querySelector('#' + idx);
		if (dropzone) {
			// 'display' 속성을 토글합니다.
			if (dropzone.style.display === 'none') {
				dropzone.style.display = 'block';
			} else {
				dropzone.style.display = 'none';
			}

		} else {

		}
	}
}


/**
 * for SK NETWORKS
 * @param lang
 * @param f
 */
global.skn = {};
global.skn.list = {};
/**
 * list - language tab
 * @param lang
 * @param f
 */
global.skn.list.langTab = function(lang, f) {
    if(f === undefined)
        f = document.searchForm;
    try {
        f.lang.value = lang;
        f.submit();
    } catch (e) {
        console.error(e);
    }
};
/**
 * list - search form - datepicker
 * @param sEl
 * @param eEl
 */
global.skn.list.bindDatepicker = function(sEl, eEl) {
	let thisObj = this;
	this.startFlatpickr = flatpickr(sEl, {
		dateFormat: 'Y-m-d',
		locale: 'ko',
		maxDate: new Date(),
		onChange: function (selectedDates) {
			if (selectedDates.length > 0) {
				const maxDate = new Date(selectedDates[0]);
				maxDate.setFullYear(maxDate.getFullYear() + 1);
				thisObj.endFlatpickr.latestSelectedDateObj = maxDate; // 연도 버그 처리
				thisObj.endFlatpickr.set('minDate', selectedDates[0]);
				thisObj.endFlatpickr.set('maxDate', maxDate);
			}
		}
	});

	this.endFlatpickr = flatpickr(eEl, {
		dateFormat: 'Y-m-d',
		locale: 'ko',
		onChange: function (selectedDates) {
			if (selectedDates.length > 0) {
				thisObj.startFlatpickr.set('maxDate', selectedDates[0]);
			}
		}
	});
};

global.skn.list.clearStartDate = function() {
	this.startFlatpickr.clear();
};

global.skn.list.clearEndDate = function() {
	this.endFlatpickr.clear();
};