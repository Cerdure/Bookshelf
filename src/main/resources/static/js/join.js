$(function () {

  $(".form-group").mouseover(function () {
    $(this).find(".clear-btn").stop().fadeIn(300);
  }).mouseleave(function () {
    $(this).find(".clear-btn").stop().fadeOut(100);
  });
  $('.clear-btn').click(function () {
    $(this).parent().find('input').val('').focus();
  });


  $(".phone").on('keyup focus', function () {
    const rgx1 = /^[0-9]{0,11}$/;
    const rgx2 = /^[0-9]{11}$/
    let val = $(this).val();
    if (!rgx1.test(val)) {
      $(this).val(val.substr(0, val.length - 1));
    }
    if (rgx2.test(val)) {
      $('.code-send').css({ 'pointer-events': 'all', 'color': 'rgb(98,98,98)' });
    } else {
      $('.code-send').css({ 'pointer-events': 'none', 'color': 'rgb(170,170,170)' });
    }
  });

  function pwCheck() {
    let originValue = $('.pw').val();
    let thisValue = $('.pw-check').val();
    if (thisValue == originValue && thisValue != "") {
      $('.pw-check').parent().css('border', "1px solid rgb(101, 168, 255)")
    } else if (thisValue != "") {
      $('.pw-check').parent().css('border', "1px solid #ff3873")
    } else {
      $('.pw-check').parent().css('border', "1px solid lightgray")
    }
  }

  $(".pw").on('keyup focus', function () {
    const pwRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,14}$/;
    let value = $(this).val();
    if (pwRegex.test(value)) {
      $(this).parent().css('border', "1px solid rgb(101, 168, 255)");
      $('.pw-info').css('color', "rgb(101, 168, 255)");
    } else if (value != "") {
      $(this).parent().css('border', "1px solid #ff3873");
      $('.pw-info').css('color', "#ff3873");
    } else {
      $(this).parent().css('border', "1px solid lightgray");
      $('.pw-info').css('color', "rgb(170, 170, 170)");
    }
    pwCheck();
  });
  $(".pw-check").on('keyup focus', function () {
    pwCheck();
  });

  let timerInterval;
  $(".code-send").click(async e => {
    const it = $(e.target);
    let phone = $('.phone').val();
    let time = 180;
    const num = await fetch("/join/makeCheckNum/?phone=" + phone).then(res => res.text()).catch(err => console.log(err));
    clearInterval(timerInterval);
    $(".timer").hide().text('03:00');

    if (!it.hasClass("clicked")) {
      it.hide()
        .text('발송 완료')
        .css({ 'pointer-events': 'none', 'line-height': '40px', 'color': 'rgb(170,170,170)' })
        .addClass("clicked")
        .fadeIn(300);
      $(".timer").fadeIn(300);
      $(".verify").show().animate({ 'height': '60px' }, 200, function () {
        $(".code-check").fadeIn(300);
      });
      timerInterval = setInterval(function () {
        time--;
        $(".timer").text('0' + Math.floor(time / 60) + ':' + (time % 60 < 10 ? '0' + time % 60 : time % 60));
        if (time == 170) {
          $('.code-send').text('다시 보내기')
            .css({ 'pointer-events': 'all', 'color': 'rgb(98,98,98)' })
            .removeClass("clicked");
        } else if (time <= 0) {
          $(".timer").text('시간 만료');
          // $('.hidenNumber').val(null);
        }
      }, 1000);
    }
  });

  $(".code-check").click(function () {
    $(".verify").animate({ 'height': '0' }, 300, function () {
      $(".verify").hide();
    });
    clearInterval(timerInterval);
    // var number = $('.hidenNumber').val();

    var inputNumber = $('.code').val();
    if (number == inputNumber) {
      $(".code-send").hide().text('인증 완료')
        .css({ 'pointer-events': 'none', 'line-height': '60px', 'color': 'rgb(170,170,170)' })
        .addClass("clicked")
        .fadeIn(300);
      $(".timer").hide();
      // $('.phone').attr('disabled','true');

      $('.phone, .code').parent().addClass('passed');
      $('.phone').parent().find('.clear-btn').remove();
      joinCheck();
      $('.verify').hide();
    } else {
      alert("올바르지 않은 번호입니다.")
    }
  });

  $(".view").click(function () {
    if (!$(this).hasClass('clicked')) {
      $(this).attr('src', '/img/icon/ico_eye_active@2x.png').addClass('clicked');
      $(this).parent().find('input').attr('type', 'text');
    } else {
      $(this).attr('src', '/img/icon/ico_eye@2x.png').removeClass('clicked');
      $(this).parent().find('input').attr('type', 'password');
    }
  });

  const pwRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,14}$/;

  function pwCheck() {
    let originValue = $('.pw').val();
    let thisValue = $('.pw-check').val();
    if (thisValue == originValue && thisValue != "") {
      $('.pw-check').parent().css('border', "1px solid rgb(101, 168, 255)")
        .addClass('passed');
    } else if (thisValue != "") {
      $('.pw-check').parent().css('border', "1px solid #ff3873")
        .removeClass('passed');
    } else {
      $('.pw-check').parent().css('border', "1px solid lightgray")
        .removeClass('passed');
    }
    joinCheck();
  }

  $(".pw").on('keyup focus', function () {
    let value = $(this).val();
    if (pwRegex.test(value)) {
      $(this).parent().css('border', "1px solid rgb(101, 168, 255)")
        .addClass('passed');
      $('.pw-info').css('color', "rgb(101, 168, 255)");
    } else if (value != "") {
      $(this).parent().css('border', "1px solid #ff3873")
        .removeClass('passed');
      $('.pw-info').css('color', "#ff3873");
    } else {
      $(this).parent().css('border', "1px solid lightgray")
        .removeClass('passed');
      $('.pw-info').css('color', "rgb(170, 170, 170)");
    }
    pwCheck();
  });
  $(".pw-check").on('keyup focus', function () {
    pwCheck();
  });

  $('.zipcode, .city').on('keyup focus', function () {
    $('.street').focus();
    new daum.Postcode({
      oncomplete: function (data) {
        $('.zipcode').val(data.zonecode);
        $('.city').val(data.address);
      }
    }).open();
    $('.zipcode').parent().addClass($('.zipcode').val() == "" ? '' : 'passed');
    $('.city').parent().addClass($('.city').val() == "" ? '' : 'passed');
    $('.street').parent().addClass($('.street').val() == "" ? '' : 'passed');
    joinCheck();
  });

  $('.street').on('keyup focus', function () {
    $('.zipcode').parent().addClass($('.zipcode').val() == "" ? '' : 'passed');
    $('.city').parent().addClass($('.city').val() == "" ? '' : 'passed');
    $('.street').parent().addClass($('.street').val() == "" ? '' : 'passed');
    joinCheck();
  });

  let verifiedPhone;

  $(document).on("click", ".join", e => {
    $(".phone").val(verifiedPhone);

  });

});





