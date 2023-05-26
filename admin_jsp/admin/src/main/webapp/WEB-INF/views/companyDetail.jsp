<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="menuBar.jsp"/>
	<div id="content">
	<form class="regForm">
		<table>
			<tr>
				<th>제휴사</th>
				<td>
					<input id="company" readonly value="${siteDetail.company}" />
				</td>
			</tr>
			<tr>
				<th>사이트명</th>
				<td><input id="siteName" name="siteName"  value="${siteDetail.siteName }"></td>
			</tr>
			<tr>
				<th>사이트상태</th>
				<td>					
					<select id="siteStatus" name="option" >
					  <option value="정상" <c:if test="${siteDetail.siteStatus eq '00' }">selected </c:if> >정상</option>
					  <option value="중지" <c:if test="${siteDetail.siteStatus eq '01' }">selected </c:if>>중지</option>
					  <option value="준비중" <c:if test="${siteDetail.siteStatus eq '02' }">selected </c:if>>준비중</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>정산여부</th>
				<td>
					<input type="radio" value="Y" name="radioTxt"  <c:if test="${siteDetail.siteCalculate eq 'Y' }">checked </c:if> >Y</intput>
					<input type="radio" value="N" name="radioTxt" <c:if test="${siteDetail.siteCalculate eq 'N' }">checked </c:if> >N</intput>
				</td>
			</tr>
			<tr>
				<th>사이트 URL</th>
				<td><input id="siteUrl" name="siteUrl"  value="${siteDetail.siteProtocol }://${siteDetail.siteUrlDetail}"></input></td>
			</tr>
			<tr>
				<th>사이트 담당자</th>
				<td><input id="managerName" name="managerName"  value="${siteDetail.siteManagerName }"></input></td>
			</tr>
			<tr>
				<th>담당자 연락처</th>
				<td><input id="managerPhone" name="managerPhone"   value="${siteDetail.siteManagerNumber }"/></td>
			</tr>
			<tr>
				<th>담당자 이메일</th>
				<td><input id="managerEmail" name="managerEmail"  value="${siteDetail.siteManagerEmail }"></input></td>
			</tr>
		</table>
		<button type="button" class="regBtn" id="siteRegBtn" >수정</button>
		<button id="deleteBtn">삭제</button>
		</form>
		</div>
</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="../../js/Common.js" ></script>
<script>
$("#siteRegBtn").on("click", function(){
	if(nullCheck()){
		 if(alertConfirm("수정")){
			 
				let params = {
						siteId : ${siteDetail.siteId},
						company : '${siteDetail.company}',
						siteName : $("#siteName").val(),
						siteStatus : $("#siteStatus").val(),
						siteCalculate : $('input[name="radioTxt"]:checked').val(),
						siteUrl : $("#siteUrl").val(),
						siteManagerName : $("#managerName").val(),
						siteManagerNumber : $("#managerPhone").val(),
						siteManagerEmail : $("#managerEmail").val()
				}
				
				$.ajax({
						type: "POST",
						url : "/modify",
						data: params,
						success: function(){
							alert8()
							location.href="/?offset=0"
						},
						error:function(){  
						}
				})
				
		}// end validate
	} // end null check
})

$("#deleteBtn").on("click",function(){
	if(alertConfirm("삭제")){

		$.ajax({
				type: "POST",
				url : "/delete",
				data: {siteId : ${siteDetail.siteId}},
				success: function(){
					alert3();
					location.href="/?offset=0"
				},
				error:function(){  
				}
			})
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