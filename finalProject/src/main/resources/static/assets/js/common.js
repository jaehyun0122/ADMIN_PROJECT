/*테이블 페이지 버튼 생성 함수*/
/*페이지 버튼 생성 10개씩 보기 고정*/
function makePageBtn(dataSize){
    /*총 버튼 개수*/
    let btnCnt = Math.ceil(dataSize / 10);
    let pageBtn = '';
    /*버튼 개수 만큼 만들기*/
    for(let i = 1; i<=btnCnt; i++){
        pageBtn += `<button class="page-btn">${i}</button>`
    }

    $("#btnContainer").append(pageBtn);
}
/*테이블 페이지 버튼 생성 함수*/