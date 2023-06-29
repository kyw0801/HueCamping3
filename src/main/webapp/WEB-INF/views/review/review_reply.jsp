<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../css/main.css" >
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<jsp:include page="../include/header.jsp" />
<script>
 function replycont_btn_check(){//등록 버튼 내용 없을 때 경고창
	if($.trim($('#reply_name').val())==''){
		 alert('작성자를 입력하세요!');
		 $('#reply_name').val('').focus();
		 return false;
	}
	if($.trim($('#reply_title').val())==''){
		 alert('제목을 입력하세요!');
		 $('#reply_title').val('').focus();
		 return false;
	}
	if($.trim($('#reply_pwd').val())==''){
		 alert('비밀번호를 입력하세요!');
		 $('#reply_pwd').val('').focus();
		 return false;
	}
	if($.trim($('#reply_cont').val())==''){
		 alert('글내용을 입력하세요!');
		 $('#reply_cont').val('').focus();
		 return false;
	}
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
     <a href="review_list"><strong>포토후기</strong></a>
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
<%--댓글 창(답변 창) 구역--%>

<div id="bReply_wrap">
<form method="post" action="review_reply_ok" onsubmit="return replycont_btn_check()">


<%--답변글 정렬순서 --%>
<input type="hidden" name="page" value="${page}" >
<input type="hidden" name="board_no" value="${rc.board_no}">

<table id="bCont_t">


 <tr>
  <th class="proQnAth" scope="row">댓글 제목</th>
  <td><input name="reply_title" id="reply_name"></td>
 </tr>
 
 <tr>
  <th class="proQnAth" scope="row">작성자</th>
  <td><input name="reply_name" id="reply_title"></td>  
 </tr>
 
 <tr>
  <th class="proQnAth" scope="row">비밀번호</th>
  <td><input type="password" name="reply_pwd" id="reply_pwd"></td>
 </tr>
 
 <tr>
  <td colspan="2">
   <textarea name="reply_cont" id="reply_cont"></textarea>
  </td>
 </tr>
</table>

<div class="okcancellist_btn">
 <input class="product_QnA_btn" type="submit" value="댓글 등록">
 <input class="product_QnA_btn" type="button" value="취소" 
 onclick="location='review_view?board_no=${rc.board_no}&page=${page}&state=cont';">
</div>


</form>

</div>
<%-- 본문 --%>
 </div>
</div>
<jsp:include page="../include/footer.jsp" />