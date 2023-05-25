/*테이블 페이지 버튼 생성 함수*/
function makePageBtn(dataSize, pagePerData){
    /*총 버튼 개수*/
    let btnCnt = Math.ceil(dataSize / pagePerData);
    let pageBtn = '';

    /*버튼 개수 만큼 만들기*/
    for(let i = 1; i<=btnCnt; i++){
        pageBtn += `<button value="${i}" class="page-btn" style="margin: 10px">${i}</button>`
    }

    $("#btnContainer").append(pageBtn);
}

/*10, 20, 30, 모두 보기 selected 속성 적용*/
function setSelected(){
// URL에서 pagePerData 변수 가져오기
    const urlParams = new URLSearchParams(window.location.search);
    const pagePerDataValue = urlParams.get('pagePerData');

    // 값이 있을 때만
    if(pagePerDataValue){
        // id가 pagePerData인 select 요소에서 value 값이 pagePerDataValue인 옵션에 selected 속성 적용
        document.querySelector('#pagePerData option[value="' + pagePerDataValue + '"]').selected = true;
    }
}

/*회원 가입, 비밀번호 변경 시 패스워드 확인 함수*/
function passwordCheck(){
    if ($('input[name="password"]').val() !== $('input[name="passwordCheck"]').val()) {
        $(".passwordCheckMsg").css("display", "block");
        $(".passwordCheck").focus();
        return false;
    }else{
        $(".passwordCheckMsg").css("display", "none");
        return true;
    }
}

/*유효성 검사*/
/* 1. 회원 정보 */
    /*패스워드*/
    /*최소 8자 이상의 길이를 가져야 함
    영문 대문자, 영문 소문자, 숫자, 특수문자 중 적어도 3가지 이상을 포함해야 함*/
    function validPassword() {
        let password = $('input[name="password"]').val();
        // 패스워드 유효성 검사 정규식
        let passwordRegex = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,}$/;

        // 유효성 검사 실행
        if (passwordRegex.test(password) && password.length > 0) {
            // 유효한 패스워드인 경우
            $(".passMsg").css("display", "none");
            return true;
        } else {
            // 유효하지 않은 패스워드인 경우
            // 해당 input focus
            $("#password").focus();
            $(".passMsg").css("display", "block");
            return false;
        }
    }

    /*이름*/
    /*한글 또는 영어만 허용*/
    function validName() {
        // 영어와 한글만 허용하는 정규식
        let regex = /^(?:[ㄱ-ㅎㅏ-ㅣ가-힣]+|[a-zA-Z\s]+)$/;
        let input = $('input[name="name"]').val();

        // 공백인 경우
        if(input.trim() == '') {
            $("#name").focus();
            $(".nameMsg").css("display", "block");
            return false;
        }

        if(regex.test(input)){
            $(".nameMsg").css("display", "none");
            return true;
        }else{
            // 유효하지 않은 경우
            // 해당 input focus
            $("#name").focus();
            $(".nameMsg").css("display", "block");
            return false;
        }
    }

    /*이메일*/
    function validEmail() {
        let email = $('input[name="email"]').val();
        // 이메일 유효성을 검사하는 정규식
        let regex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

        if(regex.test(email) && email.length > 0){
            $(".emailMsg").css("display", "none");
            return true;
        }else{
            // 유효하지 않는 경우
            // 해당 input focus
            $("#email").focus();
            $(".emailMsg").css("display", "block");
            return false;
        }

    }

    /*이메일 중복 확인 검사*/
    function emailCheckClick(emailCheck){
        if(emailCheck){
            return true;
        }else{
            alert("이메일 중복확인을 진행해주세요.");
        }
    }

    /*전화번호*/
    function validPhone() {
        let phoneNumber = $('input[name="phone"]').val();
        console.log(phoneNumber)
        // 전화번호 유효성을 검사하는 정규식
        let regex = /^[0-9]+$/;

        if(regex.test(phoneNumber) && phoneNumber.length > 0){
            console.log("good")
            $(".phoneMsg").css("display", "none");
            return true;
        }else{
            console.log("bad")
            $("#phone").focus();
            $(".phoneMsg").css("display", "block");
            return false;
        }
    }

/* 2. 서비스 등록 */
    /*사업자 등록번호 숫자만 && - 제거*/
    function companyNoCheck(){
        $("input[name='companyNo']").on("input",function (){
            let replaceValue = $(this).val().replace(/\D/g, '');
            $(this).val(replaceValue);
        })
    }

/*유효성 검사*/




