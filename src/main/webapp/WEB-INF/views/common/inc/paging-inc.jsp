<%@ page pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:parseNumber var="currentPage" value="${param.currentPage}" integerOnly="true"/>
<fmt:parseNumber var="totalPage" value="${param.totalPage}" integerOnly="true"/>

<c:if test="${param.totalCount > 0}">
<div class="pt-3 d-flex justify-content-center">
    <ul class="pagination" id="pagination">
        <li class="page-item previous <c:if test="${currentPage <= 1}">disabled</c:if>">
            <a href="javascript:;"
               class="page-link"
               data-taget="firstPage"
               onclick="${param.listFunction}(1)">
                <<
            </a>
        </li>

        <li class="page-item previous <c:if test="${currentPage <= 1}">disabled</c:if>">
            <a href="javascript:;"
               class="page-link"
               onclick="${param.listFunction}('${currentPage - 1}')">
                <i class="previous"> </i>
            </a>
        </li>

        <c:forEach
                var="i"
                begin="${param.startOfPageBlock}"
                end="${param.endOfPageBlock}"
                step="1" >
            <li class="page-item <c:if test="${i eq currentPage}">active</c:if>">
                <a href="javascript:;"
                   onclick="${param.listFunction}('${i}')"
                   class="page-link">
                        ${i}
                </a>
            </li>
        </c:forEach>

        <li class="page-item next ${currentPage >= totalPage ? 'disabled' : ''}">
            <a href="javascript:;"
               onclick="${param.listFunction}('${currentPage + 1}')"
               class="page-link" >
                <i class="next"> </i>
            </a>
        </li>

        <li class="page-item next ${currentPage >= totalPage ? 'disabled' : ''}">
            <a href="javascript:;"
               class="page-link"
               data-taget="lastPage"
               onclick="${param.listFunction}('${totalPage}')">
                >>
            </a>
        </li>
    </ul>
</div>
</c:if>