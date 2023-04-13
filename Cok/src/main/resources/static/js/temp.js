let pos; // 스크롤값
const dim = document.querySelector(".dim"); // 레이어 팝업 회색 배경

window.addEventListener('scroll', function(){
    pos = window.scrollY;
	goTop.check(pos); //  위로가기
    header.header_set(pos); // 그림자 넣기
});



/* 컨텐츠의 높이값이 작을때 */
var layout;
layout = {
	load : function(){
		var wrap = $(".wrap");
		if(wrap.outerHeight() <= window.innerHeight) {
			$("html").addClass("flexCol");
		} else {
			$("html").removeClass("flexCol");
		}
	},
	active : function(pos){
		var wrap = $(".wrap");
		if(wrap.outerHeight() < window.innerHeight) {
			$("html").addClass("flexCol");
		} else {
			$("html").removeClass("flexCol");
		}
	},
	observerAction : function(){
		var wrap = $(".wrap");
		var header = $(".wrap header");
		if(!header) {
			if(wrap.outerHeight() < window.innerHeight) {
				$("html").addClass("flexCol");
				return;
			} else {
				$("html").removeClass("flexCol");
				return;
			}
		} else {
			if(window.innerHeight >= ($(header).prop('scrollHeight') + $("section").prop('scrollHeight'))) {
				$("html").addClass("flexCol");
				return;
			} else {
				$("html").removeClass("flexCol");
				return;
			}
		}
	}
}
layout.load();

// On element change. test1
// $('section').on('DOMSubtreeModified', function () {
// 	layout.observerAction();
// });




/* nav scroll */
let pos_nav;
const navSection = document.querySelector(".nav_section")
if(navSection != null) {
	navSection.addEventListener('scroll', function(){
		pos_nav = navSection.scrollTop;
		// goTop.check(pos_nav);
		navHeader.navHeader_set(pos_nav);
		// layout.active(pos_nav)
	});
}
let navHeader;
navHeader = {
	item : document.querySelector('.nav_header'),
	navHeader_set : function(pos_nav){
		if(!navHeader.item) return;
		console.log(pos_nav)
		if(pos_nav > ( navHeader.item.offsetHeight * 0)) {
			navHeader.item.classList.add('active')
		} else {
			navHeader.item.classList.remove('active')
		}
	},
	navHeader_height : function(){
		if(!navHeader.item) return;
		// navHeader.item.style.height = navHeader.inner.offsetHeight + 'px';
	}
}
navHeader.navHeader_height();






/* 키패드 테스트 실패. 안됨.*/
const ipt_num = document.querySelectorAll('input[type="number"]');
ipt_num.forEach(function(item){
	item.addEventListener("focusin", function(event){
		console.log(event)
		this.setAttribute('type', 'tel')
	})
	item.addEventListener("focusout", function(event){
		console.log(event)
		this.setAttribute('type', 'text')
	})
})



function agreeEv(){
	dim.style.display = "block";
	const popup_temp = document.querySelector("#ev_temp");
	popup_temp.style.display = "block"
}
/* 이벤트 참여 완료 */
const btn_ev_join = document.querySelector(".event_footer .btn_ev_join");
if(btn_ev_join != null) {
	btn_ev_join.addEventListener("click", agreeEv)
}


/*
function containerPadding(){
	var $container = $("body .container");
	$container.each(function(){
		var $footerBtn = $(this).siblings("footer");
		footerHeight = $footerBtn.outerHeight() + 15;
		$(this).css({
			"padding-bottom" : footerHeight
		})
	})
}
containerPadding();
*/



/* 상단 메뉴 버튼 */
const html = document.querySelector("html");
const btn_menu = document.querySelector("header .btn_menu");
const side_nav_wrap = document.querySelector(".side_nav_wrap");
if(side_nav_wrap != null) {
	const btn_close = side_nav_wrap.querySelector(".btn_close button")
	btn_close.addEventListener("click", function(){
		side_nav_wrap.classList.remove("active");
		html.classList.remove("scrollHidden");
	})
	btn_menu.addEventListener("click", function(){
		side_nav_wrap.classList.add("active");
		html.classList.add("scrollHidden");
	})
}





/* FAQ (아코디언) */
/*
$(".item .q").on("click", function(){
	$(this).closest(".item").siblings().removeClass("active");
	$(this).closest(".item").toggleClass("active");
})
*/
const accordion_list = document.querySelector(".accordion_list");
if(accordion_list != null) {
	items = accordion_list.querySelectorAll(".item");
	items.forEach(function(item, index){
		triger = item.querySelector(".q");
		triger.addEventListener("click", function(){
			if(items[index].classList.contains("active")) {
				items[index].classList.remove("active");
				return false;
			}
			for(i=0; i <= items.length-1; i++) {
				items[i].classList.remove("active");
				console.log("for")
			}
			items[index].classList.add("active");
		})
	});
}








/* 위로가기 버튼 보이기 (버튼 없을때 오류남.) */
var goTop;
goTop = {
	btn : document.querySelector(".toTop"),
	header_wrap : document.querySelector('.header_wrap'),
	check : function(pos){
		if(goTop.btn == null) {
			return;
		}
		if(pos > goTop.header_wrap.offsetHeight) {
			goTop.btn.classList.add("active")
		} else {
			goTop.btn.classList.remove("active")
		}
		// (pos > window.innerHeight) ? goTop.btn.classList.add("active") : goTop.btn.classList.remove("active");
	},
	action : function(pos){
		window.scrollTo({
			top: 0,
			behavior: 'smooth'
		});
	}
}
if(goTop.btn != null) {
	goTop.btn.addEventListener("click", function(){
		goTop.action();
	})
}





/* 헤더 그림자 넣기 */
var header;
header = {
	item : document.querySelector('.header_wrap'),
	header_set : function(pos){
		if(header.item == null) {
			return;
		}
		if(pos > header.item.offsetHeight) {
			header.item.classList.add('active')
		} else {
			header.item.classList.remove('active')
		}
		// (pos > header.item.offsetHeight * 1.5) ? header.item.classList.add('active') : header.item.classList.remove('active');
	}
}





/* alert, modal */
function dimChk() {
	const alert_arr = document.querySelectorAll(".layer.alert");
	let count = 0;
	alert_arr.forEach(item => {
		if(item.style.display !== "none"){
			count++;
		}
	});
	if(count == 0) {
		dim.style.display = "none";
	}
}

function delLayer(event){
	const layerDiv = event.target.closest(".layer.alert"); // 조건을 만족하는 가장 가까운 상위 ele 선택
	// const li = event.target.parentElement; // 바로 상위 ele 선택
	layerDiv.style.display = "none";
}


const layer_buttons = document.querySelectorAll(".layer button");
layer_buttons.forEach(function(item){
	item.addEventListener("click", delLayer)
	item.addEventListener("click", dimChk)
})





window.onload = function(){

	/* footer 간격 샘플 */
	var footer_wrap;
	footer_wrap = {
		item : document.querySelector('.divide_bottom'),
		footer_wrap : document.querySelector('.footer_wrap'),
		active : function(pos){
			if(footer_wrap.item == null || footer_wrap.footer_wrap == null) {
				return;
			}

			footer_wrap.footer_wrap.style.marginTop = 0;
		}
	}
	footer_wrap.active();

}




/* 복사하기 테스트 */
const btn_copy = document.querySelector(".btn_copy");
if(btn_copy != null) {
	btn_copy.addEventListener("click", function(event){
		const target = "#" + btn_copy.getAttribute("data-target");
		getSelection().removeAllRanges();
		var range = document.createRange();
		// range.selectNodeContents(document.querySelector(".copy_code"));
		range.selectNodeContents(document.querySelector(target));
		getSelection().addRange(range);
		document.execCommand("copy");
		getSelection().removeAllRanges();
	})
}
