<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
  <% 
  	List<String> companyList = (List) request.getAttribute("companyList");
  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="menuBar.jsp"/>
	<div id="content">
	<h2>> 사이트 등록</h2>
	<div>
	<form class="regForm" >
		<table>
			<tr>
				<th>사이트 유형</th>
				<td>
				<input type="radio"  id="marketBtn" value="마케팅 제휴사" name="siteType" >마케팅 제휴사</input>
				<input type="radio"  id="serviceBtn" value="서비스 제휴사" name="siteType" >서비스 제휴사</input> <!-- 클릭시 사이트 연동 url input 보이게  -->
				</td>
			</tr>
			<tr>
				<th>제휴사</th>
				<td>
 					<select id="company" name="option" >
						<c:forEach items="${companyList}" var="company">
							<option value="${company}">${company}</option>					
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>사이트명</th>
				<td>
					<input id="siteName" name="siteName" ></intput>
					<button type="button" class="regBtn" id="regNewCompanyBtn">신규 제휴사 등록</button>
				</td>
			</tr>
			<tr>
				<th>사이트상태</th>
				<td>					
					<select id="siteStatus" name="option" >
					  <option value="정상" selected>정상</option>
					  <option value="중지">중지</option>
					  <option value="준비중">준비중</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>정산여부</th>
				<td>
					<input id="radio" type="radio" value="Y" name="radioTxt" >Y</intput>
					<input type="radio" value="N" name="radioTxt">N</intput>
				</td>
			</tr>
			<tr id="siteConTr" style="display: none;">
				<th>사이트 연동URL</th>
				<td><input id="siteGearingUrl"  name="siteGearingUrl"/></td>
			</tr>
			<tr>
				<th>사이트 URL</th>
				<td><input id="siteUrl"  name="siteUrl"/>
					<label for="siteUrl" style="color : red;">http://, https:// 포함해서 입력해주세요</label>
				</td>
			</tr>
			<tr>
				<th>사이트 담당자</th>
				<td><input id="managerName"  name="managerName" /></td>
			</tr>
			<tr>
				<th>담당자 연락처</th>
				<td><input id="managerPhone"  name="managerPhone"/></td>
			</tr>
			<tr>
				<th>담당자 이메일</th>
				<td><input id="managerEmail"  name="managerEmail"/></td>
			</tr>
		</table>
		<button type="button" id="siteRegBtn">등록</button>
		<button type="button" id="cancelBtn">취소</button>
		</form>
		</div>
		
	  <div id="pop_info" class="pop_wrap"  style="display: none;">
	    <div class="pop_inner">
	      <h2>신규제휴사 등록</h2>
	      <input id="companyName" placeholder="제휴사 명을 입력해 주세요."></input>
	      <input id="feerate" placeholder="수수료율(단위%)"></input>
	      <button type="button" class="regBtn" id="btn_register">등록</button>
	      <button type="button" class="btn_close">취소</button>
	    </div>
	  </div>
	  
  </div>
</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="../../js/Common.js" ></script>
<script>
	$("#cancelBtn").on("click", function(){
		location.href = ""
	})

	$("#serviceBtn").on("click", function(){
		$("#siteConTr").css("display", "table-row")
	})
	
	$("#marketBtn").on("click", function(){
		$("#siteConTr").css("display", "none")
	})

 	$("#siteRegBtn").on("click", function(){
 		if(nullCheck()){
			
			if(alertConfirm("등록")){
				let siteGearingUrl = null;
				
				if($('input[name="siteType"]:checked').val() == "마케팅 제휴사"){
					siteGearingUrl = $('input[name="siteType"]:checked').val();
				}
				let params = {
						company : $("#company").val(),
						siteName : $("#siteName").val(),
						siteStatus : $("#siteStatus").val(),
						siteCalculate : $('input[name="radioTxt"]:checked').val(),
						siteUrl : $("#siteUrl").val(),
						siteManagerName : $("#managerName").val(),
						siteManagerNumber : $("#managerPhone").val(),
						siteManagerEmail : $("#managerEmail").val(),
						siteType: $('input[name="siteType"]:checked').val(),
						siteGearingUrl: siteGearingUrl
				}
				
	 			$.ajax({
						type: "POST",
						url : "/",
						data: params,
						success: function(){
							alert5()
							location.href="?offset=0"
						},
						error:function(){  
							alert("error")
						}
					}) 
			}// end confirms 
 		} // end null check 
	}) 
	
	// 신규 제휴회사 팝업 open
	$("#regNewCompanyBtn").click(function(e){
		$(".pop_wrap").css("display", "block")
	})
	// 신규 제휴회사 팝업 close
	$(".btn_close").click(function(){
		$(".pop_wrap").css("display", "none")
	})
	// 신규 제휴회사 등록
	$("#btn_register").click(function(){
		
		if($("#companyName").val().length == 0){
			alert("제휴사 명을 입력해 주세요")
		}
		
		
		if(alertConfirm("등록")){
			$.ajax({
				type: "POST",
				url : "newSite",
				data: {
					companyName : $("#companyName").val(),
					feerate : $("#feerate").val()
				},
				success: function(data){
						alert5();
						location.href="/register"
				},
				error:function(){  
				}
			})
		}
	})
	
	// 사이트명 한글 체크
	$("#siteName").on("keypress", function(){
		let siteNameVal = $("#siteName").val();
		let regex = new RegExp(/^[ㄱ-ㅎ|가-힣]+$/)

		if(!regex.test(siteNameVal)){
			alert("한글만 입력해주세요")
		}
	})
	// 사이트명 글자수 체크
	$("#siteName").on("keyup", function(){
		let siteNameVal = $("#siteName").val();
		
		if(siteNameVal.length >= 30){
			alert("30자 이하로 입력해주세요")
		}
	})
	// 담당자 입력
	$("#managerName").on("keypress", function(){
 		let siteManagerVal = $("#managerName").val()
		let regex = new RegExp(/^[ㄱ-ㅎ|가-힣]+$/)
		
		if(!regex.test(siteManagerVal)){
			alert("한글만 입력해주세요")
		}
		
	})
	$("#managerName").on("keyup", function(){
 		let siteManagerVal = $("#managerName").val()

 		if(siteManagerVal.length >= 10) {
			alert("10자 이하로 입력해주세요")
		}
	})
	
	// 제휴사
	$("#companyName").on("keypress", function(){
		let val = $("#companyName").val();
		let regex = /^[ㄱ-ㅎ|가-힣]+$/
		
		if(!regex.test(val)){
			alert("한글만 입력해주세요")
			$("#companyName").val("")
		}
		
	})
	$("#companyName").on("keyup", function(){
		let val = $("#companyName").val();
		
		if(val.length > 30){
			alert("30자 이하로 입력해주세요")
		}
	})
	// 수수료율
	$("#feerate").on("keyup", function(){
		let val = $("#feerate").val();
		
		if(Number(val) > 100 || Number(val) < 0){
			alert("0~100 사이의 숫자 입력해주세요")
			$("#feerate").val("")	
		}
		
		let regex = /^[0-9]+$/
			
		if(!regex.test(val)){
			alert("숫자만 입력해주세요")
			$("#feerate").val("")			
		}
	})
	
	
</script>
<style>
#content{
    text-align: center;
    display: flex;
    justify-content: center;
    flex-direction: inherit;
    }
#pop_info{
	position: absolute;
   backdrop-filter: blur(5px);
   height: 500px;
}
.regBtn{
 background-color: blue;
 color: white;
 }
 
 th{
 	border-top: 1px solid;
 	border-right: 1px solid;
 	border-bottom: 1px solid;
 }
td{
 	border-top: 1px solid;
 	border-right: 1px solid;
 	border-bottom: 1px solid;
 } 
</style>
</html>