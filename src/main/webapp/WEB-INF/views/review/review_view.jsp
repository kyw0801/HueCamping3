<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" type="text/css" href="../css/main.css" >
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<jsp:include page="../include/header.jsp" />


<div class="-frame">
 <div class="contents">
<!-- 안내바 S -->
<div id="expath" class="path">
   <ol>
    <li>
     <a href="member_main">Home</a>
    </li>
    <li>
     <a href="review"><strong>포토후기</strong></a>
    </li>
   </ol>
  </div>
<!-- 안내바 E -->

  <div class="guideLink_title">포토후기</div>
   <div class="guideMenu">
    <ul class="menucategory">
     <li><a href="notice">공지사항</a></li>
     <li><a href="productQnA_list">QnA</a></li>
     <li><a href="FAQ">FAQ</a></li> 
     <li><a href="review_list"><strong style="color: #222;">포토후기</strong></a></li>   
    </ul>
   </div>
   
<%-- 본문 --%>
<div id="bCont_wrap">
	<table id="bCont_t">
	 <tr>
	  <th class="proQnAth" scope="row" >제목</th>
	  <td>${rc.board_title}</td>
	 </tr>
	 
	 <tr>
	  <th class="proQnAth" scope="row" >작성자</th>
	  <td>${rc.board_name}</td>
	 </tr>
	 
	 <tr>
	  <td colspan="2" style="font-weight:bold; border-bottom: none;">작성일:${rc.board_date}</td>
	 </tr>
	 <tr>
	  <td colspan="2" class="b_cont">
	   ${rc.board_cont}
	   <c:if test="${!empty rc.board_file1}">
        <img src="./upload${rc.board_file1}" alt="리뷰 사진1" class="reviewphoto" >
       </c:if>
	  </td>
	 </tr> 
	</table>

	
<div id="bCont_btn"> 
 <input class="product_QnA_btn" type="button" value="수정" onclick="location=
	   'review_view?board_no=${rc.board_no}&page=${page}&state=edit';" />
 <input class="product_QnA_btn" type="button" value="삭제" onclick="location=
	   'review_view?board_no=${rc.board_no}&page=${page}&state=del';" />	
 <input class="product_QnA_btn" type="button" value="목록" onclick="location='review_list?page=${page}';" />
 </div>
</div>

<%--댓글 --%>
<div id="bReply_wrap">

	<input type="hidden" name="page" value="${page}">
	
	<table id="bReply_t">
 <tr>
  <th class="proQnAth" width="10%">작성자</th>
  <th class="proQnAth" width="80%">댓글내용</th>
  <th class="proQnAth" width="10%">작성일</th> 
 </tr>
 
 
 <c:forEach var="rb" items="${replist}"> 
 <tr> 
  <td align="center">${rb.reply_name}</td>
  <td width="80%" style="padding-left: 10px;">${rb.reply_cont}</td>
  <td align="center">${fn:substring(rb.reply_date,0,10)}</td>  
 </tr>
 </c:forEach>
</table> 
 
 <input class="product_QnA_btn" style="float:right;" type="button" value="댓글" onclick="location=
	  'review_view?board_no=${rc.board_no}&page=${page}&state=reply';" />

</div>
<%-- 댓글 --%>
<%-- 본문 --%>
 </div>
 </div>
<jsp:include page="../include/footer.jsp" />