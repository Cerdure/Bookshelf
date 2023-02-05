
$(function () {
  let st = $(window).scrollTop();

  if (st > 160) {
    $(".middle-nav-wrapper").css({
      'position': 'fixed',
      'top': '60px',
      'border-top': 'none',
      'border-bottom': '1px solid #ebebeb',
      'box-shadow': 'none'
    });
    $(".top-nav-background").css({
      'box-shadow': 'none'
    });
    $(".meta-wrapper").css('margin-top', '45px');
  } else {
    $(".middle-nav-wrapper").css({
      'position': 'relative',
      'top': '0px',
      'border-top': '1px solid lightgray',
      'box-shadow': '0px -3px 5px 0px rgba(0, 0, 0, 0.03)'
    });
    $(".top-nav-background").css({
      'box-shadow': '0px 3px 5px 0px rgba(0, 0, 0, 0.05)'
    });
    $(".meta-wrapper").css('margin-top', '0px');
  }

  $(window)
    .scroll(function () {
      st = $(this).scrollTop();
      if (st > 160) {
        $(".middle-nav-wrapper").css({
          'position': 'fixed',
          'top': '60px',
          'border-top': 'none',
          'border-bottom': '1px solid #ebebeb',
          'box-shadow': 'none'
        });
        $(".top-nav-background").css({
          'box-shadow': 'none'
        });
        $(".meta-wrapper").css('margin-top', '45px');
      } else {
        $(".middle-nav-wrapper").css({
          'position': 'relative',
          'top': '0px',
          'border-top': '1px solid lightgray',
          'box-shadow': '0px -3px 5px 0px rgba(0, 0, 0, 0.03)'
        });
        $(".top-nav-background").css({
          'box-shadow': '0px 3px 5px 0px rgba(0, 0, 0, 0.05)'
        });
        $(".meta-wrapper").css('margin-top', '0px');
      }
    });
});


$(function () {
  let width = $(".top-back-wrapper").width();
  $(".top-background").css('background-size', width + 'px');


  $(window).resize(function () {
    let width = $(".top-back-wrapper").width();
    $(".top-background").css('background-size', width + 'px');
  });

  $(".top-back-title").show();



  $(document).ready(function () {
    $(document).on("click", ".inquire-write", function () {
      $(".modal-background").fadeIn(100);
      $(".inquire-write-wrapper").fadeIn(200);
      $("body").css('overflow-y', 'hidden');
      iwOpened = true;
    });

    $(document).on("keyup", ".inquire-input-header", function () {
      headerPassed = $(this).val() == '' ? false : true;
      registCheck(headerPassed, mainPassed);
    });

    $(document).on("keyup", ".inquire-input-main", function () {
      let val = $(this).val();
      $(".write-number").text(val.length + "/3000");
      mainPassed = val.length > 9 ? true : false;
      registCheck(headerPassed, mainPassed);
    });

    $(document).on("click", ".inquire-write-top-icon", function () {
      $(".inquire-write-wrapper").css('filter','brightness(0.8)');
      const alert = document.querySelector(".alert-btn-2");
      alert.querySelector(".text").innerHTML = "작성한 내용은 저장되지 않습니다.<br>취소하겠습니까?";
      alert.querySelector(".no").addEventListener("click", () => {
        $(".inquire-write-wrapper").css('filter','brightness(1)');
      });
      alert.querySelector(".ok").addEventListener("click", formClose);
      flexFadeIn(".alert-btn-2");
    });



    $(document).on("click", ".private-check", function () {
      if ($(this).is(':checked')) {
        $(".private-pw").stop().show();
        $(".private-pw").stop().animate({ 'width': '200px' }, 300);
      } else {
        $(".private-pw").stop().animate({ 'width': '0px' }, 300, function () {
          $(".private-pw").stop().hide();
        });
      }
    });


  });       // doc---------------------------------------------------



  $(".pw-input input").keyup(function (key) {
    if (key.keyCode == 13) {
      pwCheck(this);
    }
  });

  $("#search-input").keyup(function (key) {
    if (key.keyCode == 13) {
      $('.search').submit();
    }
  });

  $(".my-inquire-search").click(function () {
    document.location.replace("/inquire-my");
  });
});



let headerPassed = false;
let mainPassed = false;
let iwOpened = false;

function formClose() {
  $(".modal-background").fadeOut(100);
  $(".inquire-write-wrapper").hide();
  $("body").css('overflow-y', 'scroll');
  $(".inquire-write-wrapper").css('filter', 'brightness(100%)');
  $(".inquire-write-wrapper textarea").val('');
  $(".write-number").text('0/3000');
  $(".inquire-write-photo-wrapper").remove();
  $(".inquire-write-attach-photo-button").show();
  $(".private-check").prop("checked", false);
  $(".private-pw").val('');
  $(".regist-button").addClass("disable");
  $(".inquire-write").css('pointer-events', 'all');
  headerPassed = false;
  mainPassed = false;
  iwOpened = false;
  hideModal();
}

function registCheck(...passed) {
  if (passed.every(e => { return e; })) {
    $(".regist-button").removeClass("disable");
  } else {
    $(".regist-button").addClass("disable");
  }
}

function inquireDetail(_this) {
  inquireId = $(_this).parent().find("#inquireId").val();
  $.ajax({
    url: "/inquire-closedCheck/" + inquireId,
    type: "get",
    data_type: "text",
    error: function (xhr, status, error) {
      console.log(error);
    }
  }).done(function (result) {
    if (result == null || result == '') {
      document.location.replace("/inquire-detail/" + inquireId);
    } else {
      inquirePw = result;
      $(".write-wrapper-back").addClass("modal-background");
      $(".pw-input").fadeIn(300);
      $(".pw-input input").focus();
    }
  });
}

let inquireId;
let inquirePw;

function pwCheck(_this) {
  if ($(_this).parent().find("input").val() == inquirePw) {
    document.location.replace("/inquire-detail/" + inquireId);
  } else {
    $(".pw-input input").css('border', '1px solid #ff3873');
  }
}

function hideAlert(_this) {
  $(".write-wrapper-back").removeClass("modal-background");
  $("form").css('filter', 'brightness(100%)');
  $(_this).parent().hide();
}

function formSubmit(_this) {
  let form = $(_this).closest("form");
  let content = form.find("[name=content]");
  content.val(content.val().replace(/\n/g, "<br>"));
  form.submit();
}

