function alertConfirm (type){
	let returnValue = false;
	switch(type){
		case "등록":
			returnValue = alert4();
			break;
		case "수정":
			returnValue = alert7()
			break;
		case "삭제":
			returnValue = alert2()
			break;
	}
	return returnValue
}

function alert1(){
	return confirm("취소하시겠습니까?")
}
function alert2(){
	return confirm("삭제하시겠습니까?")
}
function alert3(){
	alert("삭제 되었습니다.")
}

function alert4(){
	return confirm("등록하시겠습니까")
}

function alert5(){
	alert("등록 되었습니다")
}
function alert6(){
	alert("필수 입력 항목을 입력해 주세요.")
}
function alert7(){
	return confirm("수정하시겠습니까?")
}
function alert8(){
	alert("수정 되었습니다.")
}

// 조회시 입력값 체크
function searchCheck(){
	let urlRegex = /(http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
	
	if($("#siteName").val().length == 0){
		alert6()
		$("#siteName").focus()
		return false;
	}
	
	if($('input[name="radioTxt"]:checked').val() == undefined){
		alert6()
		return false;
	}
	
	if($("#siteUrl").val().length == 0){
		$("#siteUrl").focus()
		alert6()
		return false;
	}
	
	if(!urlRegex.test($("#siteUrl").val())
	){
		alert("url형식을 확인해주세요")
		$("#siteUrl").focus()
		return false;
	}
	
	if($("#startDate").val().length == 0
		|| $("#endDate").val().length == 0
	){
		$("#startDate").focus()
		alert6();
		return false;
	}
	
	return true;
}

// 제휴사 입력값 체크
function companyCheck(){
	let korReg = /^[ㄱ-ㅎ|가-힣]+$/
	if($("#companyName").val().length == 0){
		alert6();
	}
	
	if($("#feerate").val().length == 0){
		aler6()
	}
	

}

// 등록시 입력값 없는지 확인
function nullCheck(){
	
	let emailRegex = /([a-z0-9]+@[a-z]+\.[a-z]{2,3})/;
	let phoneRegex = /(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/;
	let urlRegex = /(http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
	
	
	if($("#siteName").val().length == 0){
		alert6()
		$("#siteName").focus()
		return false;
	}
	
	if($('input[name="radioTxt"]:checked').val() == undefined){
		alert6()
		$("#radio").focus()
		return false;
	}
	if($("#siteUrl").val().length == 0
		|| !urlRegex.test($("#siteUrl").val()
		)
	){
		alert("url형식을 확인해주세요")
		$("#siteUrl").focus()
		return false;
	}
	
	if($("#managerName").val().length == 0){
		alert6()
		$("#managerName").focus()
		return false;
	}
	
	if($("#managerPhone").val().length == 0
		|| !phoneRegex.test($("#managerPhone").val()
		)
	){
		alert("전화번호형식을 확인해주세요")
		$("#managerPhone").focus()
		return false;
	}
	if($("#managerEmail").val().length == 0
		|| !emailRegex.test($("#managerEmail").val()
		)
	){
		alert("E-MAIL 형식을 확인해주세요")
		$("#managerEmail").focus()
		return false;
	}
	
	return true;
}


