
$(function () {

    let currentIndex = $("#page-content").data("index");
  
    $(".top-nav ul li")
      .hover(function () {
        let index = $(this).index();
        if (index == 0) {
          $("#underbar").stop().animate({ 'left': '0' }, 200, 'swing');
        } else {
          $("#underbar").stop().animate({ 'left': 20 * index + '%' }, 200, 'swing');
        }
      })
      .click(function () {
        $(this).animate({ 'color': 'black' }, 300);
        $(".top-nav ul li").not(this).css('color', "rgb(180, 180, 180)");
        currentIndex = $(this).index();
      })
      .mouseleave(function () {
        if (currentIndex == 0) {
          $("#underbar").stop().animate({ 'left': '0' }, 200, 'swing');
        } else {
          $("#underbar").stop().animate({ 'left': 20 * currentIndex + '%' }, 200, 'swing');
        }
      });
  
    $(".bottom-nav ul li")
      .hover(function () {
        let index = $(this).index();
        if (index == 0) {
          $("#overbar").stop().animate({ 'left': '0%' }, 600, 'swing');
        } else {
          $("#overbar").stop().animate({ 'left': 20 * (index - 1) + '%' }, 300, 'swing');
        }
      })
      .mouseleave(function () {
        if (currentIndex == 0) {
          $("#overbar").stop().animate({ 'left': '0%' }, 600, 'swing');
        } else {
          $("#overbar").stop().animate({ 'left': 20 * currentIndex + '%' }, 300, 'swing');
        }
      });


  let st = $(window).scrollTop();
  let width = $(window).width();

  if (width > 999) {
    $(".top-nav ul li").fadeIn(500);
    $(".bottom-nav-wrapper").stop().animate({ 'bottom': '-80px' }, 500);
    $(".top-move-button").stop().animate({ 'bottom': '20px' }, 500);
    $("footer").stop().animate({ 'margin-bottom': '0px' }, 500);
  } else {
    $(".bottom-nav-wrapper").stop().animate({ 'bottom': '0px' }, 500);
    $(".top-move-button").stop().animate({ 'bottom': '60px' }, 500);
    $("footer").stop().animate({ 'margin-bottom': '40px' }, 500);
    $(".top-nav ul li").fadeOut(500);
  }

  $(window)
    .resize(function () {
      st = $(this).scrollTop();
      width = $(this).width();
      if (width > 999) {
        $(".top-nav ul li").fadeIn(500);
        $(".bottom-nav-wrapper").stop().animate({ 'bottom': '-80px' }, 500);
        $(".top-move-button").stop().animate({ 'bottom': '20px' }, 500);
        $("footer").stop().animate({ 'height': '173px' }, 500);
      } else {
        $(".bottom-nav-wrapper").stop().animate({ 'bottom': '0px' }, 500);
        $(".top-move-button").stop().animate({ 'bottom': '60px' }, 500);
        $("footer").stop().animate({ 'height': '223px' }, 500);
        $(".top-nav ul li").fadeOut(500);
      }
    });


  $(".footer-infor-title").click(function () {
    if (!$(this).hasClass("clicked")) {
      $(".footer-infor").fadeIn(300);
      $(this).text("사업자 정보 닫기 ∧");
      $(this).addClass("clicked");
    } else {
      $(".footer-infor").fadeOut(300);
      $(this).text("사업자 정보 열기 ∨");
      $(this).removeClass("clicked");
    }
  });

  $(".top-move-button").click(function () {
    $('html').stop().animate({ scrollTop: 0 }, 1000);
  });

  $("#cart").click(function(){
    location.href="/cart";
  });
});

function hideModal(){
  $(".alert-modal").fadeOut(200);
}

function modalFadeIn(_this){
  $(_this).css("display", "flex");
  $(_this).animate({"opacity":"1"}, 500);
}

function modalFadeOut(_this){
  $(_this).animate({"opacity":"0"}, 200);
  $(_this).css("display", "none");
}


