<%@page import="java.text.DecimalFormat"%>

<%@page import="java.util.ArrayList"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@500&display=swap" rel="stylesheet">
<jsp:include page="../include/header.jsp" />

<script type="text/javascript">
	
	$(document).ready(function(){
		$("input[name='addressInputWay']").change(function(){
			var v = $("input[name='addressInputWay']:checked").val();
			
			if(v == 1){
				<%--<%
				String memid = (String) session.getAttribute("memid");
				MemberDao mdao = MemberDao.getInstance();
				MemberBean mbean = mdao.getMemberById(memid);
				%>--%>
				$("input[name='name']").val("${mbean.name}");
				$("input[name='mobile1']").val("${mbean.mobile1}");
				$("input[name='mobile2']").val("${mbean.mobile2}");
				$("input[name='mobile3']").val("${mbean.mobile3}");
				$("input[name='zip']").val("${mbean.zip}");
				$("input[name='addr1']").val("${mbean.addr1}");
				$("input[name='addr2']").val("${mbean.addr2}");
				
			}
			else if(v == 2){
				$("input[name='name']").val("");
				$("input[name='mobile1']").val("");
				$("input[name='mobile2']").val("");
				$("input[name='mobile3']").val("");
				$("input[name='zip']").val("");
				$("input[name='addr1']").val("");
				$("input[name='addr2']").val("");
			}	
		}).change();
		
	});
	
	function finishWithPay(){
		if($("input[name='name']").val() == ""){
			alert("받는 분의 성함을 입력해주세요");
			$("input[name='name']").focus();
			return;
		}
		if($("input[name='mobile1']").val() == ""){
			alert("전화번호를 입력해주세요");
			$("input[name='mobile1']").focus();
			return;
		}
		if($("input[name='mobile2']").val() == ""){
			alert("전화번호를 입력해주세요");
			$("input[name='mobile2']").focus();
			return;
		}
		if($("input[name='mobile3']").val() == ""){
			alert("전화번호를 입력해주세요");
			$("input[name='mobile3']").focus();
			return;
		}
		if($("input[name='zip']").val() == ""){
			alert("주소를 입력해주세요");
			return;
		}
		if($("input[name='addr2']").val() == ""){
			alert("상세주소를 입력해주세요");
			$("input[name='addr2']").focus();
			return;
		}
		if(isNaN($("input[name='mobile1']").val())){
			alert("전화번호는 숫자만 입력가능합니다.");
			$("input[name='mobile1']").select();
			return;
		}
		if(isNaN($("input[name='mobile2']").val())){
			alert("전화번호는 숫자만 입력가능합니다.");
			$("input[name='mobile2']").select();
			return;
		}
		if(isNaN($("input[name='mobile3']").val())){
			alert("전화번호는 숫자만 입력가능합니다.");
			$("input[name='mobile3']").select();
			return;
		}
		document.f.submit();
	}

</script>

<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	var newJquery = $.noConflict(true); // 다른 라이브러리랑 겹칠때 이렇게 해주면 됨.
	function openZipSearch() {
		new daum.Postcode({
			oncomplete: function(data) {
				newJquery('[name=zip]').val(data.zonecode); // 우편번호 (5자리)
				newJquery('[name=addr1]').val(data.address);
				newJquery('[name=addr2]').val(data.buildingName);
				newJquery('[name=addr2]').focus();
			}
		}).open();
	}
</script>

<form name=f action="orderProc" id="orderfin">
	<table id="orderTable">
		<caption>
			<strong>결제할 상품 정보</strong>
			<hr>
		</caption>
		<tr>
			<td>상품이미지</td>
			<td>상품명</td>
			<td>선택옵션</td>
			<td>단가</td>
			<td>수량</td>
			<td>소계금액</td>
		</tr>
		
		<c:forEach var="ctbean" items="${selectlist}" varStatus="status">
		 <tr>					
		  <c:set var="fullPath" value="${pageContext.request.contextPath}/product_images/${ctbean.mainimgn}"/>
		  <td><img src="${fullPath}"  style="max-height: 100px; max-width: 100px;"></td>
		  <td>${ctbean.pname}</td>
		  <td>${ctbean.opname}</td>
		  <td><fmt:formatNumber pattern="###,###" value="${ctbean.oneprice}"/>원</td>
		  <td>${ctbean.qty}</td>
		  <td>
		   <fmt:formatNumber pattern="###,###" value="${ctbean.oneprice*ctbean.qty}"/>원</td>
		 </tr>
		</c:forEach>

		<tr>
		 <td colspan="8">
		  <b>
		   결제하실 총 금액은 총 <strong><fmt:formatNumber pattern="###,###" value="${totalPrice}"/></strong>원 입니다.
		  </b>
		 </td>
		</tr>
   </table>
  <div class="d1-join">
  <div class="member-join">
  <div class="ec-base-table typeWrite">
  <table id="deliveryInfoTable">
   <caption style="color: #222;">
    <strong>배송지 정보</strong>
    <hr>
   </caption>
   <tr>
    <th>배송지 선택</th>
    <td class="placeholder"><input type="radio" name="addressInputWay" value="1" checked>주문자 정보와 동일 &nbsp&nbsp&nbsp <input type="radio" name="addressInputWay" value="2">새로운 배송지 작성</td>
   </tr>
   <tr>
    <th>받는분 성명</th>
    <td class="placeholder">
     <span id="nameContents">
      <input type="text" name="name" id="name">
     </span>
    </td>
   </tr>
   <tr>
    <th valign="top">연락처</th>
    <td class="placeholder">
     <input type="text" maxlength="3" name="mobile1" id="mobile1"> - <input type="text" maxlength="4" name="mobile2" id="mobile2"> - <input type="text" maxlength="4" name="mobile3"  id="mobile3">
    </td>
   </tr>
   <tr>
    <th valign="top">배송지주소</th>
    <td class="placeholder">
	 <p>
	  <input type="text" name="zip"  id="zip" readonly>
      <input type="button" value="우편번호" class="btnNormal" id="btnNormal" name="btnNormal"  onclick="openZipSearch()">
     </p>
     <input type="text" name="addr1"  id="addr1" readonly>  기본주소
	 <input type="text" name="addr2"  id="addr2">  상세주소
    </td>
   </tr>
   <tr>	
    <td colspan="8">
     <input type="hidden" name="ctno" value="${ctno}">
     <input type="hidden" name="rowcheck" value="${madedToTheOneRowcheck}">
     <input type="button" value="결제하기" class="custom-btn" onClick="finishWithPay();" style="width:433px; height:50px">
    </td>
   </tr>
  </table>
  </div>
  </div>
 </div>
</form>
<jsp:include page="../include/footer.jsp" />