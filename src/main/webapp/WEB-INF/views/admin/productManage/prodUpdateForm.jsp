<%@page import="net.hue.vo.CategoryVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.hue.dao.CategoryDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../admin_include/top_admin.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<script type="text/javascript" src="../../js/jquery.js"></script>
<script type="text/javascript">
	var opcount = 1; // 전달할 추가한 옵션의 개수(최소는 1개)
	var opArr = [];
	opArr.push(1);
	var haveToBedelete="";
	
	$(document).ready(function(){
		<c:forEach var="sbean" items="${slist}" begin="1">
		    $('#optionAdd').parent().append("<span><input type='text' value='${sbean.opname}' size='5' name='opn_${opcount+1}'> <input type='text' value='${sbean.count}' size='5' name='stock_${opcount+1}'> <input type='button' value='-' class='deleteopt'> <br></span>");
		    opArr.push(${opcount+1});
		</c:forEach>
		
		$("select[name='selLargeCategory']").change(function(){
			var choicedNo = $(this).val(); // 선택된 것의 lno 값
		
			$.ajax({
				url : "/returnSCategory",
				dataType : "json",
				data :{
					lno : choicedNo
				},
				success : function(responseData){		
					
					var len =Object.keys(responseData.ITEMS).length;
					var temp = "<option value='-1'>소분류 생략</option>";
					
					for(i = 0; i < len ; i++){
						if( (JSON.stringify(responseData.ITEMS[i].sname)).replace(/"/g,"") == "${pbean.scname}"){
							temp += "<option value=" + JSON.stringify(responseData.ITEMS[i].sno) + " selected>" + (JSON.stringify(responseData.ITEMS[i].sname)).replace(/"/g,"") + "</option>";	
						}else{
							temp += "<option value=" + JSON.stringify(responseData.ITEMS[i].sno) + ">" + (JSON.stringify(responseData.ITEMS[i].sname)).replace(/"/g,"") + "</option>";	
						}			
					}
					if(len == 0){
						$("select[name='selSmallCategory']").html("<option value='-1'>소분류가 존재하지 않습니다</option>");
						//$("select[name='selSmallCategory']").parent().parent().hide();
					}else{
						$("select[name='selSmallCategory']").parent().parent().show();
						$("select[name='selSmallCategory']").html(temp);
					}
				}
			});
		}).change(); /* 로드 되자마자 반응하게 */
		
		$('#optionAdd').live("click", function(){
			$(this).parent().append("<span><input type='text' value='' size='5' name='opn_" + (++opcount) + "'> <input type='text' value='' size='5' name='stock_" + opcount +"'> <input type='button' value='-' class='deleteopt'> <br></span>");
			opArr.push(opcount);
		});
		
		
		$('.deleteopt').live("click", function(){
			//name속성 뒤에 붙은 값을 분리해서 이걸 배열에서 제거하자.
			var temp = $(this).prev().attr("name"); // stock_2
			var tempArr = temp.split('_');
			//alert(tempArr[1] + "삭제");
			for(var i = 0; i < opArr.length; i++) {
				  if(opArr[i] == tempArr[1])  {
					  opArr.splice(i, 1);
				    i--;
				    break;
				  }
			}
			//alert(opArr);
			//alert($(this).parent().prev().children().attr("name"));
			
			$(this).parent().remove();
		});
		
		$('.deleteBeforeImg').live("click", function(){
			haveToBedelete += $(this).prev().val() + ",";
			$(this).prev().val("");	
		});
		
	});//document_ready()
	
	function check(){
		if($("select[name='selLargeCategory']").val() == "선택안함"){
			alert("대분류를 선택해주세요");
			return false;
		}
		
		if($("input[name='name']").val() == ""){
			alert("상품명을 입력해주세요");
			return false;
		}
		
		if($("input[name='oriprice']").val() == ""){
			alert("정가를 입력해주세요");
			return false;
		}
		
		if($("input[name='discprice']").val() == ""){
			alert("할인가를 입력해주세요");
			return false;
		}
		
		if($("input[name='opn_1']").val() == ""){
			alert("옵션명을 입력해주세요");
			return false;
		}
		
		if($("input[name='stock_1']").val() == ""){
			alert("재고를 입력해주세요");
			return false;
		}
		
		if(isNaN($("input[name='stock_1']").val())){
			alert("재고는 숫자만 입력 가능합니다.");
			return false;
		}
		
		//alert(opcount);
		var temp = "";
		
		for(var i = 0; i < opArr.length; i++){
			temp += opArr[i] + ",";
		}
		document.f.opnums.value = temp;
		//alert(temp);
		//alert(document.f.opnums.value);
		//return false;
		
		//null로 보내면 안되므로
		//alert($("select[name='selSmallCategory']").val());
		
		$('input[name="haveToBedelete"]').val(haveToBedelete);
		//alert($('input[name="haveToBedelete"]').val());
		document.f.submit();
	}
</script>
 
<style type="text/css">
	#producRegister{
		margin : auto;
		/* border : 1px solid black; */
	}
	
	#producRegister > caption{
		font-size: 19px;
	}
	
	#t2{
		width : 250px;
	}
	textarea{
		resize: none; 
		width: 100%; 
		height:70px;
	}
</style>

<form name="f" action="prodUpdateProc" method="post" enctype="multipart/form-data">
 <table class="producRegister">
  <caption>
   <strong>상품수정</strong>
   <hr>
  </caption>
		
  <!--대분류 -->
  <tr>
   <td>대분류</td>
   <td>
   <select name="selLargeCategory">
     <option value="선택안함" selected>선택안함</option>
	  <c:forEach var="cbean" items="${lcateList}">
       <c:if test="${cbean.lno != -1}">
        <option value="${cbean.lno}" ${cbean.lname == pbean.lcname ? 'selected' : ''}>${cbean.lname}</option>
       </c:if>
      </c:forEach>
    </select>	 	
   </td>
		 
  <!-- 소분류 -->
   <tr>
    <td>소분류</td>
    <td>
     <select name="selSmallCategory">
      <option value="선택안함">대분류를 먼저 선택해주세요</option>
     <!-- 여기 소분류 옵션들이 추가됨  -->
     </select>	 	
    </td>
		 
   <tr>
    <td>이름</td>
    <td><input type="text" name="name" value="${pbean.name}"></td>
   </tr>
   <tr>
    <td>정가</td>
    <td><input type="text" name="oriprice" value="${pbean.oriprice}"></td>
   </tr>
   <tr>
    <td>할인판매가</td>
    <td><input type="text" name="discprice" value="${pbean.discprice}"></td>
   </tr>
   <tr>
    <td>상품설명</td>
    <td>
     <textarea name="info">${pbean.info}</textarea>
    </td>
   </tr>
   <tr>
    <td>상품이미지</td>
    <td>
     <input type="text" name="beforeMainImg" value="${pbean.mainImgN}" readonly>
     <input type="button" value="삭제" class="deleteBeforeImg" style="width: 50px; height: 22px;">
     <input type="file" name="mainImg">
    </td>
   </tr>
   <tr>
    <td>설명이미지1</td>
    <td>
     <input type="text" name="beforeImg1" value="${pbean.detailImgN1}" readonly>
     <input type="button" value="삭제" class="deleteBeforeImg" style="width: 50px; height: 22px;">
     <input type="file" name="detailImg1">
    </td>
   </tr>
   <tr>
    <td>설명이미지2</td>
    <td>
     <input type="text" name="beforeImg2" value="${pbean.detailImgN2}" readonly>
     <input type="button" value="삭제" class="deleteBeforeImg" style="width: 50px; height: 22px;">
     <input type="file" name="detailImg2">
    </td>
   </tr>
   <tr>
    <td>설명이미지3</td>
    <td>
     <input type="text" name="beforeImg3" value="${pbean.detailImgN3}" readonly>
     <input type="button" value="삭제" class="deleteBeforeImg" style="width: 50px; height: 22px;">
     <input type="file" name="detailImg3">
    </td>
   </tr>
   <tr>
    <td>설명이미지4</td>
    <td>
     <input type="text" name="beforeImg4" value="${pbean.detailImgN4}" readonly>
     <input type="button" value="삭제" class="deleteBeforeImg" style="width: 50px; height: 22px;">
     <input type="file" name="detailImg4">
    </td>
   </tr>
   <tr>
    <td colspan="2"><hr></td>
   </tr>
   
   <tr class="produc_option">
    <td>
         옵션/재고
    </td>
    <td>
  
  <span>
    <input type="text" value="${slist.size() > 0 ? slist.get(0).opname : ''}" size="5" placeholder="사이즈 등" name="opn_1">
    <input type="text" value="${slist.size() > 0 ? slist.get(0).count : ''}" size="5" placeholder="재고수량" name="stock_1">
    <input type="button" value="추가" id="optionAdd"><br>
  </span>
</td>
   <tr>
		 
   <tr class="produc_submit">
    <td colspan="2" align="center">
     <input type="hidden" value="" name="opnums">
     <input type="hidden" value="${pbean.no}" name="pno">
     <input type="hidden" value="" name="haveToBedelete">
     <input type="button" value="상품수정" onClick="check()">
     <input type="button" value="취소" onClick="location.href='admin_product'">
    </td>
   </tr>
  </table>
</form>