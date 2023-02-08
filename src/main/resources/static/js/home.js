$(function () {
  let st = $(window).scrollTop();
  let width = $(window).width();
  let index = 0;
  let gradientIndex = 0;
  let interval;

    if (width < 1000 && st > 100) {
      $(".top-nav-background").css('animation', 'fadein 0.5s both');
    } else if (width < 1000 && st < 101) {
      $(".top-nav-background").css('animation', 'fadeout 0.5s both');
    }
  $(window)
    .scroll(function () {
      st = $(this).scrollTop();
      width = $(this).width();
      if (width < 1000 && st > 100) {
        $(".top-nav-background").css('animation', 'fadein 0.5s both');
      } else if (width < 1000 && st < 101) {
        $(".top-nav-background").css('animation', 'fadeout 0.5s both');
      }
    })
    .resize(function () {
      st = $(this).scrollTop();
      width = $(this).width();
      if (width > 999) {
        $(".top-nav-background").css('animation', 'fadein 0.5s both');
      } else if (width < 1000 && st < 101) {
        $(".top-nav-background").css('animation', 'fadeout 0.5s both');
      }
    });


  $(".banner-img-wrapper:first-child").show();

  function startInterval() {
    return setInterval(function () {
      gradientIndex++;
      index++;
      index %= 6;
      $(".banner-img-wrapper").hide();
      $(".banner-img-wrapper:nth-child("+(index+1)+")").show();
      $(".banner-wrapper").animate({ backgroundPosition: 20 * gradientIndex + "%" }, 1000);
      $(".banner-current-index").text(index + 1);
      $(".banner-index-fill").animate({ 'left': (-55 + index * 11) + 'px' }, 500);
    }, 3000);
  }
  interval = startInterval();

  $(".btn-right").click(function () {
    clearInterval(interval);
    interval = startInterval();
    gradientIndex++;
    index++;
    index %= 6;
    $(".banner-img-wrapper").hide();
    $(".banner-img-wrapper:nth-child("+(index+1)+")").show();
    $(".banner-wrapper").animate({ backgroundPosition: 20 * gradientIndex + "%" }, 1000);
    $(".banner-current-index").text(index + 1);
    $(".banner-index-fill").animate({ 'left': (-55 + index * 11) + 'px' }, 500);
  });
  $(".btn-left").click(function () {
    clearInterval(interval);
    interval = startInterval();
    gradientIndex--;
    index += 5;
    index %= 6;
    $(".banner-img-wrapper").hide();
    $(".banner-img-wrapper:nth-child("+(index+1)+")").show();
    $(".banner-wrapper").animate({ backgroundPosition: 20 * gradientIndex + "%" }, 1000);
    $(".banner-current-index").text(index + 1);
    $(".banner-index-fill").animate({ 'left': (-55 + index * 11) + 'px' }, 500);
  });

  $(".banner-stop").click(function () {
    clearInterval(interval);
    $(this).fadeOut(100);
    $(".banner-play").fadeIn(200);
  });
  $(".banner-play").click(function () {
    interval = startInterval();
    $(this).fadeOut(100);
    $(".banner-stop").fadeIn(200);
  });



  function bookSearch() {
    $.ajax({                                                    
      url: "/home-search-input",
      type: "get",
      data: $("#search-form").serialize(),
      dataType: "html",
      async: true,
    }).done(function (data) {
      $('#search-input-results').replaceWith(data);
      if(data.length < 100) {
        $(".search-result-outer-wrapper").hide();
      } else {
        $(".search-result-outer-wrapper").show();
      }
    });
  }

  $("#search-input").keyup(function(key){
    if (key.keyCode == 13) {
      $("#search-form").submit();
    } else {
      bookSearch();
    }
  });

  $(".search-input .reset").click(function () {
    $(this).parent().find('input').val('');
    $(".search-result-category").remove();
    $(".search-result-book").remove();
    $(".search-result-outer-wrapper").hide();
  });

  $(document).ready(function (){

    $(document).on("click","#category-box",function (){
       $("#category-id-input").val($(this).find("#category-id").val());
        $("#search-form").submit();
    });

    $(document).on("keyup ready", ".simple-search input", function () {
    });

    let tbIndex = 1;

    $(document).on("click", ".tb-btn-left", function () {
      if (tbIndex > 1) {
        tbIndex--;
        $(".slides ul").animate({ left: -420 * (tbIndex - 1) + "px" }, 1000);
      }
    });
    $(document).on("click", ".tb-btn-right", function () {
      if (tbIndex < 3) {
        $(".slides ul").animate({ left: -420 * tbIndex + "px" }, 1000);
        tbIndex++;
      }
    });

    $(document).on("mouseover", ".slides", function () {
      if (tbIndex == 1) {
        $(".tb-btn-right").fadeIn(300);
      } else if (tbIndex == 2) {
        $(".tb-btn-left").fadeIn(300);
        $(".tb-btn-right").fadeIn(300);
      } else {
        $(".tb-btn-left").fadeIn(300);
      }
    });

    $(document).on("mouseleave", ".slides", function () {
      $(".tb-btn-left").fadeOut(300);
      $(".tb-btn-right").fadeOut(300);
    });


    $(document).on("click", ".tb-reset-box", function () {
      $(".slides ul li").css('display', 'none');
      $.ajax({
        url: "/todayBook-reset",
        type: "get",
        error: function (xhr, status, error) {
          console.log(error);
        }
      }).done(function(result){
        $(".slides").hide();
        $('.today-book-wrapper').replaceWith(result);
        $(".slides").hide();
        $(".slides ul").css('left','0px');
        $(".slides").fadeIn(300);
        tbIndex = 1;
      });
    });

  });



});

function bestBook(_this, criteria){
  $(".best-title-2 li").css('color','#b9b9b9');
  $(_this).css('color','#000000');
  $.ajax({
    url: "/bestBook/"+criteria,
    type: "get",
    error: function (xhr, status, error) {
      console.log(error);
    }
  }).done(function(result){
    $('.best-books-wrapper').replaceWith(result);
    $(".best-books-wrapper").fadeIn(300);
  });
}


