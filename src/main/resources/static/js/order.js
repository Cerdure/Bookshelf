$(function () {

  let width = $(window).width();

  if (width < 1000) {
    $(".bottom-nav-wrapper").stop().animate({ 'bottom': '0px' }, 500);
    $(".bottom-nav-sub-wrapper").stop().animate({ 'bottom': '50px' }, 500);
    $(".main-wrapper").stop().animate({ 'padding-right': '0' }, 500);
    $(".side-wrapper").stop().animate({ 'right': '-300px' }, 500);
    $(".other-reviews").stop().css({ 'margin-bottom': '100px' });
  } else {
    $(".bottom-nav-wrapper").stop().animate({ 'bottom': '-80px' }, 500);
    $(".bottom-nav-sub-wrapper").stop().animate({ 'bottom': '-80px' }, 500);
    $(".main-wrapper").stop().animate({ 'padding-right': '200px' }, 500);
    $(".side-wrapper").stop().animate({ 'right': '0' }, 300);
    $(".other-reviews").stop().css({ 'margin-bottom': '0' });
  }

  $(window).resize(function () {
    width = $(this).width();
    if (width < 1000) {
      $(".bottom-nav-wrapper").stop().animate({ 'bottom': '0px' }, 500);
      $(".bottom-nav-sub-wrapper").stop().animate({ 'bottom': '50px' }, 500);
      $(".main-wrapper").stop().animate({ 'padding-right': '0' }, 500);
      $(".side-wrapper").stop().animate({ 'right': '-300px' }, 500);
      $(".other-reviews").stop().css({ 'margin-bottom': '100px' });
    } else {
      $(".bottom-nav-wrapper").stop().animate({ 'bottom': '-80px' }, 500);
      $(".bottom-nav-sub-wrapper").stop().animate({ 'bottom': '-80px' }, 500);
      $(".main-wrapper").stop().animate({ 'padding-right': '200px' }, 500);
      $(".side-wrapper").stop().animate({ 'right': '0' }, 300);
      $(".other-reviews").stop().css({ 'margin-bottom': '0' });
    }
  });


});





