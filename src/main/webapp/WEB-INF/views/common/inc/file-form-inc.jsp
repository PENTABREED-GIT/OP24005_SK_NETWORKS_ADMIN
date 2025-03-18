<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.skn.admin.util.NTUtil" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
boolean hasOrder = Boolean.valueOf(request.getParameter("hasOrder"));
String maxFileCountString = NTUtil.isNull(request.getParameter("maxFileCount"), "0");
int maxFileCount = 0;
try{
	maxFileCount = Integer.parseInt(maxFileCountString, 10);
}catch(Exception e){
}
String DZID = request.getParameter("DZID");
String dropzoneIndex = request.getParameter("dropzoneIndex");

if(maxFileCount==1)
	hasOrder = false;

String isSecure = NTUtil.isNull(request.getParameter("isSecure"),"");
boolean isSecureSelect = ( isSecure.equals("") ? true : false ) ;

%>
<div class="table-responsive test px-0">
<table class="table align-middle table-row-dashed fw-bold fs-6 gy-5 p_dropzoneFileTable p_nestable" style="margin-bottom:0px;">
    <thead>
        <tr class="text-start text-muted fw-bolder fs-7 text-uppercase gs-0">
            <%if(hasOrder){ %><th width="43">순서</th><%} %>
            <c:if test="${viewType ne 'view'}">
                <th class="pt-0 min-w-50px view" style="display: ${viewType ne 'view' ? 'block' : 'none'}">삭제</th>
            </c:if>
            <th class="pt-0 min-w-125px">파일명</th>
            <%if(isSecureSelect) {%><th class="pt-0 min-w-50px">보안</th><%} %>
            <c:if test="${fn:contains(imgFileExtensions, fileList[0].fileExtension)}">
                <th class="pt-0 max-w-125px">미리보기</th>
            </c:if>
        </tr>
    </thead>
	<tbody<%if(hasOrder){ %> class="uk-nestable grouped-nestables" data-uk-nestable="{group:'<%=DZID %>',maxDepth:1,itemNodeName:'tr',listNodeName:'table.p_nestable',listClass:'table table-bordered table-striped',listitemClass:'nestable-list-item'}"<%} %>>
<c:forEach items="${fileList}" var="row" varStatus="status">
    <tr id="dropzoneImage<%=DZID %>"<%if(hasOrder){ %> class="nestable-list-item"<%} %>>
        <%if(hasOrder){ %><td class="uk-nestable-handle text-center" width="43"><i class="fa fa-sort"></i></td><%} %>
        <c:if test="${viewType ne 'view'}">
        <td width="43" class="text-center  view" <%--style="display: ${viewType ne 'view' ? 'block' : 'none'}"--%>>
            <input
                    type="hidden"
                    class="p_fparam"
                    name="FileIndex"
                    value="${row.fileIndex}"
            />
            <input
                    type="hidden"
                    class="p_fparam"
                    name="Uid"
                    value="${row.uid}"
            />

            <a class="btn btn-sm btn-icon btn-danger" data-id="dropzone<%=DZID %>" data-file-uid="${row.uid}" data-dropzone-index="<%=dropzoneIndex %>" onclick="global.dropzoneDeleteFile(this)"><i class="icon-xl fas fa-times"></i></a>

            <input
                    type="hidden"
                    class="p_fparam"
                    name="FileName"
                    value=""/>

            <input
                    type="hidden"
                    class="p_fparam"
                    value="${row.parentType}"
                    name="parentType"
            />
            <input
                    type="hidden"
                    class="p_fparam"
                    name="SortOrder"
                    value="${row.sortOrder}"
            />
            <%if(!isSecureSelect) {%>
            <input
                    type="hidden"
                    class="p_fparam"
                    name="IsSecure"
                    value="${row.isSecure}"
            />
            <%}%>
        </td>
        </c:if>
        <td>
                <c:if test="${row.fileType eq 'image' }">
                    <input type="hidden" name="fileUid" value="${row.uid}">
                <a href="${uploadUrl}${row.getSaveFilePath() }" target="_blank" style="color:#F1BC00;">${row.fileName }
                    <c:if test="${row.uid ne null and row.uid ne ''}"> (${row.uid})</c:if>
                </a>
                </c:if>

                <c:if test="${row.fileType ne 'image' }">
                    <input type="hidden" name="fileUid" value="${row.uid}">
                <a href="${uploadUrl}${row.getSaveFilePath() }" download="${row.fileName }" type="_blank" style="color:#F1BC00;">${row.fileName }<c:if test="${row.uid ne null and row.uid ne ''}"> (${row.uid})</c:if></a>
                    <c:if test="${row.uid ne null and row.uid ne ''}"> (${row.uid})</c:if>
                </a>
                </c:if>

            <input type="text" class="form-control p_fparam" name="description" value="${row.description }" placeholder="대체 텍스트" maxlength="500" <c:if test="${viewType eq 'view'}"> readonly</c:if>/>
            </td>
            <%if(isSecureSelect) {%>
            <td>
                <label class="form-check form-switch form-check-custom form-check-solid">
                    <input class="form-check-input p_fparam" name="IsSecure" type="checkbox" value="Y" <c:if test="${row.isSecure eq 'Y'}">checked</c:if> />
                </label>
            </td>
            <%} %>
            <td>
                <c:if test="${row.fileExtension ne 'jpg' || 'png' || 'gif' || 'bmp' || 'svg' || 'jpeg' || 'pdf'}">
<%--                    <a>--%>
<%--                        <img src="/assets/images/no_img.jpg" style="max-width:200px; max-height:180px">--%>
<%--                    </a>--%>
                </c:if>
                <c:if test="${fn:contains(imgFileExtensions, row.fileExtension)}">
                    <a href="${uploadUrl}${row.getSaveFilePath() }" target="_blank">
                        <img src="${uploadUrl}${row.getSaveFilePath() }" style="max-width:200px; max-height:180px">
                    </a>
                </c:if>
            </td>
        </tr>
</c:forEach>
	</tbody>
    <c:if test="${viewType ne 'view'}">
	<tfoot>
		<tr>
			<td class="pt-0" colspan="<%=(hasOrder)?((isSecureSelect)?4:3):((isSecureSelect)?4:3)%>" id="<%=DZID %>" style="cursor:pointer;text-align:center; padding-bottom: 5px;">
			<!-- i class="fa fa-plus-circle" style="z-index:10;"></i> z-index:11;-->
                <div class="dropzone-box bg-light-primary border-1 border-dashed" id="dropzone<%=DZID %>" style="display:  ${empty fileList || fileList[0].uid eq '' ? 'block' : 'none'}">
                    <!--begin::Message-->
                    <div class="dz-message">
                        <!--begin::Icon-->
                        <i class="bi bi-file-earmark-arrow-up text-warning fs-3x"></i>
                        <!--end::Icon-->
                        <!--begin::Info-->
                        <div class="ms-4">
                            <h3 class="fs-5 fw-bolder text-gray-900 mb-1">파일을 여기에 올려주세요</h3>
                            <c:if test="${not empty description}">
                                <span class="fs-7 fw-bold text-gray-400">${description}</span>
                            </c:if>
                            <c:if test="${empty description}">
                                ${fileList[0].getSaveFilePath()}
                                <span class="fs-7 fw-bold text-gray-400">(최대 50MB 크기의 파일(jpg,png,gif,bmp,svg,.zip) <%=maxFileCount %> 개, 이미지 변경시에는 삭제 체크 후 업로드)</span>
                            </c:if>
                        </div>
                        <!--end::Info-->
                    </div>
                </div>
			</td>
		</tr>
	</tfoot>
    </c:if>
</table>
</div>


    <!-- script src="/assets/js/dropzone/dropzone.min.js"></script-->
<script src="/assets/js/dropzone/dropzone.js"></script>
<link rel="stylesheet" href="/assets/js/uikit/uikit.css">
<style>
    .text-center {
        text-align: center;
    }

    .text-center a {
        display: inline-block; /* 또는 필요에 따라 'flex' 등 다른 스타일 적용 */
        margin: 0 auto; /* 자동 마진으로 중앙 정렬 */
    }

    .p_dropzoneFileTable th{font-size:12px;}
.p_dropzoneFileTable .p_thWidth43{width:43px;}
.p_alignMiddle {vertical-align:middle !important;}
.uk-nestable-handle {display:table-cell;font-size:inherit;color:inherit;}
.uk-nestable-handle:after {content:"";}
.uk-nestable-list-dragged div.checkbox {margin-top:0;margin-bottom:0;}
.dropzone-box{
    min-height: auto;
    padding: 1.5rem 1.75rem;
    text-align: center;
    cursor: pointer;
    border-radius: .475rem!important;

}
.dropzone-box .dz-message {
    margin: 0;
    display: flex;
    text-align: left;
}
.uk-nestable-empty {
    min-height: 0px !important;
}
</style>
<script src="/assets/js/uikit/js/uikit.min.js"></script>
<script src="/assets/js/uikit/js/addons/nestable.js"></script>
<script type="text/javascript">

global.setNestable();
var nestableElements = document.querySelectorAll('.uk-nestable');


nestableElements.forEach(function(nestableElement) {
    nestableElement.addEventListener('nestable-change', function(ev) {
        let a = ev.target;
        let parent = a.parentNode;
        let children = Array.prototype.slice.call(parent.children);

        children.forEach(function(child, index) {

            let input = child.querySelector('input[name=SortOrder]');
            if (input) {
                input.value = index;
            }
        });
    });
});

</script>
