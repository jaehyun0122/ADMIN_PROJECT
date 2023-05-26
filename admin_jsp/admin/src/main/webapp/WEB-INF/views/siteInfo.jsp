<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<form class="regForm">
		<table>
			<tr>
				<th>���޻�</th>
				<td>
					<select id="company" name="option" >
						<c:forEach items="${companyList }" var="company">
							<option value="${company}">${company}</option>					
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>����Ʈ��</th>
				<td><input id="siteName" name="siteName" ></td>
			</tr>
			<tr>
				<th>����Ʈ����</th>
				<td>					
					<select id="siteStatus" name="option" >
					  <option value="����" >����</option>
					  <option value="����" >����</option>
					  <option value="�غ���">�غ���</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>���꿩��</th>
				<td>
					<input type="radio" value="Y" name="radioTxt" >Y</intput>
					<input type="radio" value="N" name="radioTxt" >N</intput>
				</td>
			</tr>
			<tr>
				<th>����Ʈ URL</th>
				<td><input id="siteUrl" name="siteUrl" ></input></td>
			</tr>
			<tr>
				<th>�������</th>
				<td><input id="startDate" type="text" autocomplete="off" ></input></td>
				<td><input id="endDate" type="text" autocomplete="off" ></input></td>
			</tr>
		</table>
		<button type="button" id="searchBtn" >��ȸ</button>
		</form>
</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="../../js/Common.js" ></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
<script>
	$("#startDate").datepicker();
	$("#endDate").datepicker();
	// �˻� 
	$("#searchBtn").on("click", function(){
		let params = {
				company : $("#company").val(),
				siteName : $("#siteName").val(),
				siteStatus : $("#siteStatus").val(),
				siteCalculate : $('input[name="radioTxt"]:checked').val(),
				siteUrl : $("#siteUrl").val(),
				startDate: $("#startDate").val(),
				endDate: $("#endDate").val()
		}
			
  		$.ajax({
				type: "GET",
				url : "/search",
				data: params,
				success: function(){
					console.log("success")
				},
				error:function(){  
				}
		})	 
	})
	
</script>
</html>