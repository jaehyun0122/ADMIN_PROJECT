<%@page import="com.example.demo.dto.CompanySiteDto"%>
<%@page import="java.util.List"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	int total =(int) request.getAttribute("totalCnt");
	int pageNum = (int) Math.ceil((double) total/7); 
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- start 메뉴바 -->
<jsp:include page="menuBar.jsp"/>
<!-- end 메뉴바 -->

<!-- start search -->
<div id="search">
	<form class="regForm">
		<table>
			<tr>
				<th>제휴사</th>
				<td>
					<select id="company" name="option" >
						<c:forEach items="${companyList }" var="company">
							<option value="${company}">${company}</option>					
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>사이트명</th>
				<td><input id="siteName" name="siteName" ></td>
			</tr>
			<tr>
				<th>사이트상태</th>
				<td>					
					<select id="siteStatus" name="option" >
					  <option value="정상" >정상</option>
					  <option value="중지" >중지</option>
					  <option value="준비중">준비중</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>정산여부</th>
				<td>
					<input type="radio" value="Y" name="radioTxt" >Y</intput>
					<input type="radio" value="N" name="radioTxt" >N</intput>
				</td>
			</tr>
			<tr>
				<th>사이트 URL</th>
				<td><input id="siteUrl" name="siteUrl" ></input></td>
			</tr>
			<tr>
				<th>등록일자</th>
				<td><input id="startDate" type="text" autocomplete="off" ></input></td>
				<td><input id="endDate" type="text" autocomplete="off" ></input></td>
			</tr>
		</table>
		<button type="button" id="searchBtn" >조회</button>
	</form>
</div>
<!-- end search -->

<div id="content">
	<div>
	<table id="table">
		<caption>사이트 목록</caption>
		<thead>
			<tr>
				<th>No</th>
				<th>사이트명</th>
				<th>제휴사명</th>
				<th>상태</th>
				<th>정산여부</th>
				<th>등록일자</th>
				<th>등록자</th>
			</tr>
		</thead>
		<tbody id="tbody">
       <c:forEach items="${siteList}" var="site">
			<tr>
				<td><c:out value="${site.siteId }" /></td>
				<td id="siteName">
					<a href="/detail?id=${site.siteId}"><c:out value="${site.siteName }" /></a>
				</td>
				<td><c:out value="${site.companynm }" /></td>
				<td>
					<c:if test="${site.siteStatus eq '00'}"> <c:out value="정상" /> </c:if>
					<c:if test="${site.siteStatus  eq '01'}" > <c:out value="중지" /> </c:if>
					<c:if test="${site.siteStatus  eq '02'}" > <c:out value="준비중" /> </c:if>
				</td>
				<td><c:out value="${site.siteCalculate }" /></td>
				<td><c:out value="${site.siteRegDTTM }" /></td>
				<td><c:out value="${site.siteRegUser }" /></td>
			</tr>
      </c:forEach>
		</tbody>
	</table>
	</div>
	<div>
		<%
			for(int i=1; i<=pageNum; i++){
		%>
				<button class="pagBtn" value="<%= i %>" ><%= i %> </button>
		<%
			}
		%>
	</div>
	<div>
		<button id="btn" class="regBtn" type="button" onclick="location.href='register'">등록</button>
	</div>
	
</div>

</body>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<script src="../../js/Common.js" ></script>
<script>
 $(".pagBtn").on("click", function(e){
	 console.log(e.target.value)
	let offset = Number(e.target.value)-1
	location.href = "/?offset="+offset
			
 })
 
$("#startDate").datepicker();
$("#endDate").datepicker();
// 검색 
$("#searchBtn").on("click", function(){
	if(searchCheck()){
		
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
				dataType : "json",
				data: params,
				success: function(data){
					$("#tbody").remove()
					let tr = "";
					for(let i=0; i<data.length; i++){
						tr += "<tr><td>"+data[0].siteId+"</td>"
						tr += "<td><a href=\"/detail?id="+data[0].siteId+"\">"+data[0].siteName+"</a></td>"
						tr += "<td>"+data[0].companynm+"</td>"
						tr += "<td>"+data[0].siteStatus+"</td>"
						tr += "<td>"+data[0].siteCalculate+"</td>"
						tr += "<td>"+data[0].siteRegDTTM+"</td>"
						tr += "<td>"+data[0].siteRegUser+"</td>"
						tr += "</tr>"
					}
					
					$("#table").append(tr);
					console.log(tr)
				},
				error:function(){  
					alert("fail")
				}
		}) // end request
	} // end null check
})
 
</script>
<style>
#search{
	text-align: -webkit-center;
}
#content{
	text-align: center;
	display: flex;
	justify-content: center;
    flex-direction: inherit;
}
#siteName:hover{
	cursor: pointer;
	background-color: buttonhighlight;
	color: white;
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