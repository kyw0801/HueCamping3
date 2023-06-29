<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>휴:캠핑마켓</title>
<link rel="icon" type="image/png" href="./images/logomain.png">
<link rel="stylesheet" type="text/css" href="./css/main.css" >
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">

<!-- ************************* 동작 스크립트 ********************* -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script type="text/javascript">
	
	jQuery(document).ready(function(){
 		//카테고리 펼쳐지고 접히기
		jQuery(".tmenu").live("mouseenter", function() {
		   jQuery(this).find(".depth2").slideDown("fast");
		});
		jQuery(".tmenu").live("mouseleave", function() {
		   jQuery(this).find(".depth2").slideUp("fast");
		});
	});

	function gotoCart() {
		<c:choose>
			<c:when test="${empty sessionScope.memno}">
				<c:url var="loginUrl" value="member_login" />
				location.href = "<c:out value='${loginUrl}'/>";
			</c:when>
			<c:otherwise>
				location.href = "${pageContext.request.contextPath}/showCart";
			</c:otherwise>
		</c:choose>
	}
	
	$( '.top' ).click( function() {
		window.scrollTo(0, 0);
	} );
	$( '.bottom' ).click( function() {
		window.scrollTo(0, document.body.scrollHeight);
	} );
</script>
<!-- ************************ 경계선 ************************** -->

</head>
<body id="main">
 <div id="wrap">
  <header id="header" class="header">
<!-- 상단 sec01 -->
   <div class="sec01wrap">
   	<div class="-frame">
   	 <div class="sec01">
   	  <div class="-left">
   	   <ul class="menu">
   	    <li class="-layerCSwrap text">
   	     <a href="/notice">고객센터</a> 
   	     <div class="layerCS">
   	      <ul class="CS-boardinfo">
   	       <li class="record-">
   	          <a href="/notice">공지사항</a>
   	       </li>
   	       <li class="record-">
   	          <a href="/review_list">포토후기</a>
   	       </li>
   	       <li class="record-">
   	          <a href="/productQnA_list">QnA</a>
   	       </li>
   	       <li class="record-">
   	          <a href="/FAQ">FAQ</a>
   	       </li>
   	      </ul>
   	     </div>
   	    </li>
   	   </ul>
   	  </div>
   	 
   	  <div class="-right">
       <ul class="menu">
 		<c:if test="${empty memid}">
        <li><a href="member_login">로그인</a></li>
        <li><a href="member_join">회원가입</a></li>
        <li class="statelogoff">
         <a href="javascript:gotoCart()">
	   		장바구니
          <span class="bsCount">
           (<span id="user_basket_quantity" class="user_basket_quantity">0</span>)
          </span>
	     </a>
        </li>
        </c:if>
      	<c:if test="${not empty memid}">
        <li><a>환영합니다!&nbsp&nbsp<strong>${mbean.name}</strong>&nbsp님</a></li>
        <li><a href="member_log_out">로그아웃</a></li>
        <li><a href="/member_mypage">마이페이지</a></li>
	    <li class="statelogoff">
         <a href="javascript:gotoCart()">
	   		장바구니
          <span class="bsCount">
           (<span id="user_basket_quantity" class="user_basket_quantity">${result}</span>)
          </span>
	     </a>
        </li>
	    <c:if test="${memid eq'admin'}">
	     <li><a href="admin_category">관리자페이지</a></li>
		</c:if>
	</c:if>
	    <li>
         <div class="searcharea"> 
          <form action="showSearchResult" method="get" name="search" id="main_search">
          <select name="find_field" style="display:none;">  
           <option selected value="board_title">상품명</option>
          </select>
          
           <input type="text" id="search" name="find_name" value="${find_name}" > 
           <%-- <input type="submit">--%>
           <a class="searhBtn" type="submit" onclick="document.getElementById('main_search').submit();">
             <div class="searchbtn">
<%-- 돋보기 설명.. --%>	 
<i class="xi-search"></i>
             </div> 
            </a>
            </form>
           </div>
         </li>
	   </ul>	 
      </div>
     </div>  
    </div>
   </div>
   
   <div class="clear"></div>

   <%--상단 sec2 --%>
   <div class="-frame">
    <div class="sec02">
     <div class="ex_top_left">
     </div>
     <div id="logo" class="logoarea">
      <ul class="logo">
       <li class="banner-logo">
        <a href="member_main"> 
         <img src="./images/logomain.png" alt="Camp Market" >
        </a>
       </li>
      </ul>
     </div>
     <div class="ex_top_right"></div>
    </div>
   </div>
  
<%-- 카테고리탭 S --%>
   <div class="categorymenuwrap">
    <div class="-frame">
     <table class="hd2">
	  <tr>
	   <td>
	    <div class="categorymenu">
		 <li class="-d1">
          <a href="#" class="allmenubtn" style="padding-left: 0px;"></a>  
			<c:set var="beforeLname" value=""/>
			<c:forEach items="${clist}" var="cbean">
			<c:if test="${cbean.sstep == 1 or cbean.sname == null}">
		  		 </ul>
		  		 </span>
		  		 </c:if>
			<c:if test="${not beforeLname.equals(cbean.lname) and (cbean.sstep == 1 or cbean.sname == null) and cbean.lstep != 0}">
			<span class="tmenu"> <a href="/showLargeCategory?lcno=${cbean.lno}">${cbean.lname}</a>
						<ul class="depth2" style="display: none;">
			</c:if>
				<c:if test="${not empty cbean.sname}">
					<li>
					<a href="showSmallCategory?lcno=${cbean.lno}&scno=${cbean.sno}">${cbean.sname}</a></li>
				<c:set var="beforeLname" value="${cbean.lname}"/>
		  </c:if>
		  </c:forEach>
		  </li>
         </div>
        </td> 
  	   </tr>
	  </table>
     </div>
	</div>
<%-- 카테고리탭 E --%>

<input type="checkbox" id="menuicon">
<label for="menuicon">
 <span></span>
 <span></span>
 <span></span>
</label>
<div class="sidebar">
 <div style="height: 100%;">
  <ul style="height: 100%;">
   <li><a href="member_main"><i class="xi-home"></i></a></li>
   <li><a href="member_mypage"><i class="xi-heart-o"></i></a></li>
   <li><a href="showCart"><i class="xi-cart-o"></i></a></li>
   <li><a href="productQnA_list"><i class="xi-help-o"></i></a></li>
   <li><a href="review_list"><i class="xi-camera-o"></i></a></li>
   <li><a href="#" class="top"><i class="xi-caret-up-square-o"></i></a></li>
  </ul>
 </div>
</div>

   </header>