$(function () {
  $('.form-group').on({
    keyup: function () {
      $(this).find('label').stop().animate(
        {
          position: 'relative',
          'font-size': '10px',
          top: '10px',
          left: '10px'
        },
        200
      )
    },
    mouseover: function () {
      $(this).find('label').stop().animate(
        {
          position: 'relative',
          'font-size': '10px',
          top: '10px',
          left: '10px'
        },
        200
      )
    },
    mouseleave: function () {
      if ($(this).find('input').val() == '') {
        $(this).find('label').stop().animate(
          {
            position: 'absolute',
            top: '50%',
            transform: 'translateY(-50%)',
            'font-size': '14px',
            left: '20px'
          },
          300
        )
      }
    }
  })

  $('.form-group input').click(function () {
    let anotherInput = $('.form-group input').not($(this))
    if (anotherInput.val() == '') {
      anotherInput.next().stop().animate(
        {
          position: 'absolute',
          top: '50%',
          transform: 'translateY(-50%)',
          'font-size': '14px',
          left: '20px'
        },
        300
      )
    }
  })

  $('.pw-wrapper img').click(function () {
    if (!$(this).hasClass('clicked')) {
      $(this).attr('src', '/img/icon/ico_eye_active@2x.png').addClass('clicked')
      $('.user-pw').attr('type', 'text')
    } else {
      $(this).attr('src', '/img/icon/ico_eye@2x.png').removeClass('clicked')
      $('.user-pw').attr('type', 'password')
    }
  })

  $('.id-save-check').click(function () {
    if (!$(this).hasClass('clicked')) {
      $('#rememberMe').prop('checked', true)
      $(this)
        .attr('src', '/img/icon/ico_checkbox_active@2x.png')
        .css({ border: 'none', width: '12px', height: '12px' })
        .animate({ backgroundColor: 'rgb(101, 168, 255)' }, 200)
        .addClass('clicked')
    } else {
      $('#rememberMe').prop('checked', false)
      $(this)
        .attr('src', '/img/icon/ico_checkbox@2x.png')
        .css({ border: '1px solid rgb(211, 211, 211)', width: '10px', height: '10px' })
        .animate({ backgroundColor: 'white' }, 200)
        .removeClass('clicked')
    }
  })

  $('.pw-change').click(function () {
    flexFadeIn_back('.pw-change-modal')
  })

  $('.pw-change-modal .close, .modal-background').click(function () {
    flexFadeOut_back('.pw-change-modal')
  })

  const pwRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,14}$/
  $(document).on('keyup focus', '.pw', (e) => pwCheck(e.target, pwRegex.test(e.target.value)))
  $(document).on('keyup focus', '.pw-check', (e) => pwCheck(e.target, $('.pw').val() == $('.pw-check').val()))

  $('.kakaoLogin').click(function () {
    Kakao.Auth.authorize({
      redirectUri: 'http://localhost:8080/login/api/kakao'
    })
  })

  $('.naverLogin').click(function () {
    const url =
      'https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=r5UySheAPrPbFPYnqJCq' +
      '&redirect_uri=' +
      'http://localhost:8080/login/api/naver' +
      '&state=1234'
    window.location.href = url
  })

  $('.googleLogin').click(function () {
    const url =
      'https://accounts.google.com/o/oauth2/v2/auth?client_id=' +
      '216152233037-ll5ocvvp8d3mn367k7s0kmsb1r09v02d.apps.googleusercontent.com' +
      '&redirect_uri=' +
      'http://localhost:8080/login/api/google' +
      '&response_type=code' +
      '&scope=email profile'
    window.location.href = url
  })
})

let timerInterval

async function verifySend(_this) {
  const it = $(_this)
  const phone = $('.pw-change-modal .phone').val()
  if (it.hasClass('clicked')) return
  it.addClass('clicked')
  const result = await fetch('/login/verify/send/?phone=' + phone)
    .then((res) => res.json())
    .catch(console.log)

  switch (result.success) {
    case true:
      alert('인증번호가 발송되었습니다.')
      clearInterval(timerInterval)
      it.hide().text('발송 완료').addClass('disable').fadeIn(300)
      $('.timer').hide().text('03:00').fadeIn(300)

      let time = 180
      timerInterval = setInterval(() => {
        time--
        $('.timer').text('0' + Math.floor(time / 60) + ':' + (time % 60 < 10 ? '0' + (time % 60) : time % 60))
        if (time == 170) {
          $('.code-send').text('다시 보내기').removeClass('disable').removeClass('clicked')
        } else if (time <= 0) {
          $('.timer').text('시간 만료')
          fetch('/verify/expire?phone=' + phone).catch(alert)
          clearInterval(timerInterval)
        }
      }, 1000)
      break
    case false:
      alert(result.error)
      it.removeClass('clicked')
      break
  }
}

async function verifyCheck() {
  const phone = $('.pw-change-modal .phone').val()
  const code = $('.code').val()
  const result = await fetch('/verify/check?phone=' + phone + '&code=' + code)
    .then((res) => res.json())
    .catch(console.log)
  switch (result.success) {
    case true:
      alert('인증이 완료되었습니다.')
      $('.code-send').addClass('disable').text('인증 완료')
      $('.timer').hide()
      clearInterval(timerInterval)
      $('.code-box').animate({ height: '0' }, 300, () => $('.code-box').hide())
      showPwChangeBox()
      break
    case false:
      alert('인증번호가 일치하지 않습니다.')
  }
}

function showPwChangeBox() {
  let div = document.createElement('div')
  div.setAttribute('class', 'pw-change-box column-box')
  div.innerHTML =
    "<div class='pw-group column-box'>" +
    "<div class='pw-label-box row-box'>" +
    '<label>새로운 비밀번호</label>' +
    "<span class='pw-info'>영문, 숫자, 특수문자 8~14글자</span>" +
    '</div>' +
    "<div class='row-box'>" +
    "<div class='input-box'>" +
    "<input class='pw column-box' type='password' placeholder='새로운 비밀번호 입력'>" +
    '</div>' +
    '</div>' +
    '</div>' +
    "<div class='pw-group column-box'>" +
    '<label>비밀번호</label>' +
    "<div class='row-box'>" +
    "<div class='input-box'>" +
    "<input class='pw-check column-box' type='password' placeholder='비밀번호 확인'>" +
    '</div>' +
    '</div>' +
    '</div>' +
    "<button class='pw-set btn-yellow-fill disable' onclick='pwSet()'>비밀번호 변경</button>"
  $('.code-box').after(div)
  $('.pw-change-box').animate({ height: '300px' }, 300)
}

function pwCheck(_this, boolean) {
  const target = $(_this).closest('.pw-group')
  switch ($(_this).val() == '') {
    case true:
      target.removeClass('passed not-passed')
      break
    case false:
      it(target).if(boolean).addClass('passed').removeClass('not-passed')
      allPassed = all('.pw-group').hasClass('passed')
      it('.pw-change').if(allPassed).removeClass('disable')
  }
  allPassed = all('.pw-group').hasClass('passed')
  it('.pw-set').if(allPassed).removeClass('disable')
}

async function pwSet() {
  let formData = new FormData()
  formData.set('phone', $('.phone').val())
  formData.set('pw', $('.pw').val())

  const result = await fetch('/login/pw/update', {
    method: 'post',
    cache: 'no-cache',
    body: formData
  })
    .then((res) => res.json())
    .catch((err) => console.log(err))

  switch (result.success) {
    case true:
      alert('비밀번호가 변경되었습니다.')
      flexFadeOut_back('.pw-change-modal')
      break
    case false:
      alert('요청이 실패하였습니다.')
      console.log(result.error)
  }
}
