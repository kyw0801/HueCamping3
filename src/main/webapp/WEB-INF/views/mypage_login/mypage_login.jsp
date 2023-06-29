<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link rel="stylesheet" type="text/css" href="./css/main.css">
<jsp:include page="../include/header.jsp"/>

<script type="text/javascript">
function allCheck() {
	ac = document.f.allcheck;
	rc = document.f.rowcheck;

	if (typeof (rc.length) == 'undefined') { // 체크박스가 1개일 때 자꾸 오류났는데 이렇게 하니까 해결됨
		if (rc.checked) {
			rc.checked = false;
		} else {
			rc.checked = true;
		}
	} else {
		if (ac.checked == true) {
			for (var i = 0; i < rc.length; i++) {
				rc[i].checked = true;
			}
		} else {
			for (var i = 0; i < rc.length; i++) {
				rc[i].checked = false;
			}
		}
	}
}

function wishlist_del() {
	rc = document.f.rowcheck;

	if (typeof (rc.length) == 'undefined' && rc.checked) {
		document.f.submit();
		return;
	}

	isChecked = false;

	for (var i = 0; i < rc.length; i++) {
		if (rc[i].checked) {
			isChecked = true;
			break;
		}
	}

	if (isChecked == false) {
		alert("결제할 상품을 선택하세요.");
		return;
	}

	document.f.submit();
}
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
      <a href="member_mypag"><strong>마이페이지</strong></a>
     </li>
    </ol>
   </div>
   <!-- 안내바 E-->
   
   <!-- 카테고리 S -->
   <div class="menupackage">
   <div class="listtopimg">
    <p class="banner"></p>
   </div>
   <div class="titleArea">
    <h2><span>마이페이지</span></h2>
   </div>
   <ul class="menucategory">
    <li style="display:;"><a href="member_mypage"><strong style="color: #222;">관심상품</strong></a></li>
    <li style="display:;"><a href="member_mypage_edit">회원정보수정</a></li>
    <li style="display:;"><a href="member_mypage_del">회원탈퇴</a></li>
    <li style="display:;"><a href="member_mypage_orderlist">주문내역</a></li>
   </ul>
  </div>
<!-- 카테고리 E -->


<!-- 본문 -->
<form name=f method="post" action="wishlist_del">
<table class="orderManage">
 <caption>
  <strong>관심상품</strong>
 </caption>
 <tr class="ManageMenu">
  <%-- <th><input type="checkbox" name="allcheck" onClick="allCheck()"></th>--%>
  <th>번호</th>
  <th>상품명</th>
  <%-- <th>수량</th>--%>
  <th>단가</th>
  <th>상품이미지</th>
  <th></th>
 </tr>
 <c:if test="${!empty wlist}">
  <c:forEach items="${wlist}" var="w">
			<tr>
				<%-- <td><input type="checkbox" name="rowcheck" value="${w.no}"></td>--%>
				<td>${w.no}</td>
				<td>
				<c:set var="pbean" value="${plist}"></c:set>
				<a href="getProduct_detail_ok?no=${w.pno}">${w.pname}</a></td>
				<%-- <td>${w.qty}개</td>--%>
				<td><fmt:formatNumber pattern="###,###" value="${w.oneprice}" />원</td>
				<c:set var="fullPath"
					value="${pageContext.request.contextPath}/product_images/${w.mainimgn}" />
				<td><img src="${fullPath}"
					style="max-height: 100px; max-width: 100px;"></td>
				<td><a href="wishlist_del?itemno=${w.no}">삭제</a></td>							
			</tr>
		</c:forEach>
 </c:if>
 <%-- <tr>
  <td colspan="8"><br> <br>
  <input type="submit" value="삭제" class="custom-btn" style="width: 433px; height: 50px"
  onClick="wishlist_del()">
  </td>
 </tr>--%>
</table>
</form>

 </div> <%--contents --%>
</div> <%---fram --%>
<jsp:include page="../include/footer.jsp"/>














