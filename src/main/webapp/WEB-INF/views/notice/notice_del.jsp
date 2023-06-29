<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="../css/main.css" >
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<jsp:include page="../include/header.jsp" />
<script>
function del_check(){
	 if($.trim($('#del_pwd').val()) == ''){
		 alert('비번을 입력하세요!');
		 $('#del_pwd').val('').focus();
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
     <a href="notice"><strong>공지사항</strong></a>
    </li>
   </ol>
  </div>
<!-- 안내바 E -->

<div class="guideLink_title">공지사항</div>
   <div class="guideMenu">
    <ul class="menucategory">
     <li><a href="notice"><strong style="color: #222;">공지사항</strong></a></li>
     <li><a href="productQnA_list">QnA</a></li>
     <li><a href="FAQ">FAQ</a></li> 
     <li><a href="review_list">포토후기</a></li>  
    </ul>
   </div>
   
<%-- 본문 --%>
<div id="bDel_wrap">
 <form method="post" action="notice_del_ok" onsubmit="return del_check();">
  <input type="hidden" name="board_no" value="${nov.board_no}" />
  <input type="hidden" name="page" value="${page}" />
  
  <table class="del_alert">
   <tr>
    <th>삭제하려면 비밀번호를 입력하세요!</th>
   </tr>
  </table>
  
  <table>
   <tr>
    <th class="proQnAth">비밀번호</th>
    <td><input type="password" name="del_pwd" id="del_pwd" size="20" /></td>
   </tr>
  </table>
  
  <div id="bDel_menu">
   <input class="product_QnA_btn" type="submit" value="삭제" />
   <input class="product_QnA_btn" type="reset" value="취소" onclick="$('#del_pwd').focus();"/>
   <input class="product_QnA_btn" type="button" value="목록" onclick="location='notice?page=${page}';"/>
  </div>  
 </form>
</div>



</div> 
</div>
<jsp:include page="../include/footer.jsp" />