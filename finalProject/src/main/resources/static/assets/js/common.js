/*테이블 페이지 버튼 생성 함수*/
/*페이지 버튼 생성 10개씩 보기 고정*/
function makePageBtn(dataSize, pagePerData){
    /*총 버튼 개수*/
    let btnCnt = Math.ceil(dataSize / pagePerData);
    let pageBtn = '';

    /*버튼 개수 만큼 만들기*/

    for(let i = 1; i<=btnCnt; i++){
        pageBtn += `<button value="${i}" class="page-btn">${i}</button>`
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


