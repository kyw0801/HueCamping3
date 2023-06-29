<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- 05.02 --%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="../include/header.jsp" />
<script src="../js/jquery.js"></script>
<script>
	$(document).ready(function(){
		$('#banner_image').show();
	});
</script>

<div class="menupackage">
   <div class="listtopimg">
    <p class="banner"></p>
   </div>
   <div class="titleArea">
    <h4 style="margin-bottom: 10px;">검색결과</h4>
    <h2 style="font-size: 30px; font-style:italic; "><span><c:out value="\"${find_name}\"" /></span></h2>
   </div>
  </div>
 
<c:choose>

 <c:when test="${empty slist}">
  <table id="mainList">
   <tr>
    <td>검색하신 물품이 없습니다.</td>
   </tr>
  </table><%-- mainList --%>
 </c:when>
 
 <c:otherwise>
  <table id="mainList">
   <c:forEach items="${slist}" var="pbean" varStatus="status">
   <c:set var="fullPath" value="${pageContext.request.contextPath}/product_images/${pbean.mainImgN}"/>
	<td>
     <table id="mainListInside">
      <tr>
       <td class="thumnail_img">
        <a href="getProduct_detail_ok?no=${pbean.no}">
        <%-- <img src="${fullPath}" style="max-height: 300px; max-width: 300px;"></a>--%>
        <%--  <c:set var="fullPath" value="/HueCamping/admin/product_images/${pbean.mainImgN}" />--%>
	    <img src="${fullPath}" style="max-height: 300px; max-width: 300px;"></a>
       </td>
      </tr>
    
      <tr>
       <td class="thumnail_prod">
        <h3>${pbean.name}</h3>
       </td><%-- thumnail_prod --%>
      </tr>
       
      <tr>
       <td class="thumnail_price">
        <span style="text-decoration:line-through;" class="thumnail_price1">
        <fmt:formatNumber pattern="###,###" value="${pbean.oriprice}"/>원</span>
         																	
        <span class="thumnail_price2">
        <fmt:formatNumber pattern="###,###" value="${pbean.discprice}"/>원</span>
        </td><%-- thumnail_price --%>
       </tr>
      </table><%-- mainListInside --%>
     </td>
     <c:if test= "${status.count % 4 == 0}">
      <tr>
     </c:if>
    </c:forEach>
   </table><%-- mainList --%>
  </c:otherwise>
  
 </c:choose>

<%-- 검색 전후 페이징 --%>
  <div id="bList_paging">
   <%--검색 전 페이징 --%>
   <c:if test="${(empty find_field) || (empty find_name)}"> <%--검색필드와 검색어가 없는 경우 --%>
     <c:if test="${page <= 1}">
      [이전]&nbsp;
     </c:if>
     <c:if test="${page>1}">
      <a href="showSearchResult?page=${page-1}">[이전]</a>&nbsp;
     </c:if>
     
     <%--현재 쪽번호 출력 --%>
     <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
       <c:if test="${a == page}"> <%-- 현재 페이지가 선택된 경우 --%>
        <${a}>
       </c:if>
       <c:if test="${a != page}"> <%--현재 쪽번호가 선택 안 된 경우--%>
        <a href="showSearchResult?page=${a}">[${a}]</a>&nbsp;
       </c:if>
     </c:forEach>
       
    
    <c:if test="${page >= maxpage}">
      [다음]
    </c:if>
    <c:if test="${page < maxpage}">
     <a href="showSearchResult?page=${page+1}">[다음]</a>
    </c:if>
   </c:if>
   
    
   <%-- 검색 후 페이징 --%>
   <c:if test="${(!empty find_field) && (!empty find_name)}"> <%--검색필드와 검색어가 있는 경우 --%>
     <c:if test="${page <= 1}">
      [이전]&nbsp;
     </c:if>
     <c:if test="${page>1}">
      <a href="showSearchResult?page=${page-1}&find_field=${find_field}&find_name=${find_name}">[이전]</a>&nbsp;
     </c:if> 
      
   <%--현재 쪽번호 출력 --%>
     <c:forEach var="a" begin="${startpage}" end="${endpage}" step="1">
       <c:if test="${a == page}"> <%-- 현재 페이지가 선택된 경우 --%>
        <${a}>
       </c:if>
       <c:if test="${a != page}"> <%--현재 쪽번호가 선택 안 된 경우--%>
        <a href="showSearchResult?page=${a}&find_field=${find_field}&find_name=${find_name}">[${a}]</a>&nbsp;
       </c:if>
     </c:forEach>      
    
    <c:if test="${page >= maxpage}">
      [다음]
    </c:if>
    <c:if test="${page < maxpage}">
     <a href="showSearchResult?page=${page+1}&find_field=${find_field}&find_name=${find_name}">[다음]</a>
    </c:if>
   </c:if> 
   
   </div>

<jsp:include page="../include/footer.jsp" />