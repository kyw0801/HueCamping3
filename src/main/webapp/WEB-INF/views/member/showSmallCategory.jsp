<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="../include/header.jsp" />
<script src="../js/jquery.js"></script>
<script>
	$(document).ready(function(){
		$('#banner_image').show();
	});
</script>

<div class="-frame">
 <div class="contents">
<!-- 안내바 S -->
  <div id="expath" class="path">
   <ol>
    <li>
     <a href="member_main">Home</a>
    </li>
    <li>
     <a href="showLargeCategory?lcno=${lcno}"><strong>${plist[0].lcname}</strong></a>
    </li>
    <li>
     <a href="#"><strong>${plist[0].scname}</strong></a>
    </li>
   </ol>
  </div>
<!-- 안내바 E --> 

<!-- 카테고리 S -->
  <div class="menupackage">
   <div class="listtopimg">
    <p class="banner"></p>
   </div>
   <div class="titleArea">
    <h2><span><c:out value="${plist[0].scname}" /></span></h2>
   </div>
   <!--
   <ul class="menucategory">
    <c:forEach items="${lplist}" var="c" varStatus="status">
     <li style="display:;"><a href="showSmallCategory?lcno=${lcno}&scno=${scno}">${c.scname}</a></li>
    </c:forEach>
   </ul>
   -->
  </div>
<!-- 카테고리 E -->
<div class="prodpackage">
<div class="sort-menu">
    <a class="sort-item" href="showSmallCategory?lcno=${lcno}&scno=${scno}&sort=latest">최신순</a>
    <a class="sort-item" href="showSmallCategory?lcno=${lcno}&scno=${scno}&sort=highprice">높은가격순</a>
    <a class="sort-item" href="showSmallCategory?lcno=${lcno}&scno=${scno}&sort=lowprice">낮은가격순</a>
</div>

<c:choose>
    <c:when test="${param.sort eq 'latest'}">
        <%-- 최신순일 경우 plist 사용 --%>
        <c:set var="productList" value="${plist}" />
    </c:when>
    <c:when test="${param.sort eq 'highprice'}">
        <%-- 높은가격순일 경우 plist2 사용 --%>
        <c:set var="productList" value="${plist2}" />
    </c:when>
    <c:when test="${param.sort eq 'lowprice'}">
        <%-- 낮은가격순일 경우 plist3 사용 --%>
        <c:set var="productList" value="${plist3}" />
    </c:when>
    <c:otherwise>
        <%-- 기본값으로 plist 사용 --%>
        <c:set var="productList" value="${plist}" />
    </c:otherwise>
</c:choose>

<c:if test="${empty productList}">
    <%-- 리스트가 비어있을 경우 처리 --%>
    <table id="mainList">
        <tr>
            <td>해당 카테고리에는 진열된 상품이 존재하지 않습니다.</td>
        </tr>
    </table>
</c:if>

<c:if test="${not empty productList}">
    <%-- productList를 사용하여 상품 리스트 출력 --%>
    <table id="mainList">
        <c:forEach items="${productList}" var="pbean" varStatus="status">
   <c:set var="fullPath" value="${pageContext.request.contextPath}/product_images/${pbean.mainImgN}"/>
   <td>
    <table id="mainListInside">
     <tr>
      <td class="thumnail_img">
       <a href="getProduct_detail_ok?no=${pbean.no}">
       <img src="${fullPath}" style="max-height: 300px; max-width: 300px;"></a>
      </td>
     </tr>
     <tr>
      <td class="thumnail_prod">
       <h3>${pbean.name}</h3>
      </td>
     </tr>
     <tr>
      <td class="thumnail_price">
       <span style="text-decoration:line-through;" class="thumnail_price1">
       <fmt:formatNumber pattern="###,###" value="${pbean.oriprice}"/>원</span>
       <span class="thumnail_price2">
       <fmt:formatNumber pattern="###,###" value="${pbean.discprice}"/>원</span>
      </td>
     </tr>
    </table>
   </td>
    <c:if test="${status.count % 4 == 0}">
     <tr>
    </c:if>
   </c:forEach>
    </table>
</c:if>

<%-- 검색 전후 페이징 --%>
  <div id="bList_paging">
   <%--검색 전 페이징 --%>
   <c:if test="${(empty find_field) && (empty find_name)}"> <%--검색필드와 검색어가 없는 경우 --%>
     <c:if test="${page <= 1}">
      [이전]&nbsp;
     </c:if>
     <c:if test="${page>1}">
      <a href="showSmallCategory?lcno=${lcno}&scno=${scno}&page=${page-1}">[이전]</a>&nbsp;
     </c:if>
     
     <%--현재 쪽번호 출력 --%>
     <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
       <c:if test="${a == page}"> <%-- 현재 페이지가 선택된 경우 --%>
        <${a}>
       </c:if>
       <c:if test="${a != page}"> <%--현재 쪽번호가 선택 안 된 경우--%>
        <a href="showSmallCategory?lcno=${lcno}&scno=${scno}&page=${a}">[${a}]</a>&nbsp;
       </c:if>
     </c:forEach>
       
    
    <c:if test="${page >= maxpage}">
      [다음]
    </c:if>
    <c:if test="${page < maxpage}">
     <a href="showSmallCategory?lcno=${lcno}&scno=${scno}&page=${page+1}">[다음]</a>
    </c:if>
   </c:if>
   
    
   <%-- 검색 후 페이징 --%>
   <c:if test="${(!empty find_field) && (!empty find_name)}"> <%--검색필드와 검색어가 있는 경우 --%>
     <c:if test="${page <= 1}">
      [이전]&nbsp;
     </c:if>
     <c:if test="${page>1}">
      <a href="showSmallCategory?page=${page-1}&find_field=${find_field}&find_name=${find_name}">[이전]</a>&nbsp;
     </c:if> 
       --%>
   <%--현재 쪽번호 출력 --%>
     <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
       <c:if test="${a == page}"> <%-- 현재 페이지가 선택된 경우 --%>
        <${a}>
       </c:if>
       <c:if test="${a != page}"> <%--현재 쪽번호가 선택 안 된 경우--%>
        <a href="showSmallCategory?page=${a}&find_field=${find_field}&find_name=${find_name}">[${a}]</a>&nbsp;
       </c:if>
     </c:forEach>      
    
    <c:if test="${page >= maxpage}">
      [다음]
    </c:if>
    <c:if test="${page < maxpage}">
     <a href="showSmallCategory?page=${page+1}&find_field=${find_field}&find_name=${find_name}">[다음]</a>
    </c:if>
   </c:if> 
   </div>
   </div>
    </div>
</div>
   

<jsp:include page="../include/footer.jsp" />