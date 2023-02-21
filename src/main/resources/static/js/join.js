$(() => {
  $('.reset').click((e) => $(e.target).parent().find('input').val('').focus())

  $('.phone').on('keyup focus', (e) => {
    const rgx1 = /^[0-9]{0,11}$/
    const rgx2 = /^[0-9]{11}$/
    const it = $(e.target)
    let val = it.val()
    if (!rgx1.test(val)) {
      it.val(val.substr(0, val.length - 1))
    }
    if (rgx2.test(val)) {
      $('.code-send').css({ 'pointer-events': 'all', color: 'rgb(98,98,98)' })
    } else {
      $('.code-send').css({
        'pointer-events': 'none',
        color: 'rgb(170,170,170)'
      })
    }
  })

  let timerInterval

  $('.code-send').click(async (e) => {
    const it = $(e.target)
    if (it.hasClass('clicked')) return
    it.addClass('clicked')
    const result = await fetch('/verify/send/?phone=' + $('.phone').val())
      .then((res) => res.json())
      .catch(console.log)

    switch (result.success) {
      case true:
        alert('인증번호가 발송되었습니다.')
        clearInterval(timerInterval)
        $('.timer').hide().text('03:00')
        it.hide()
          .text('발송 완료')
          .css({
            'pointer-events': 'none',
            'line-height': '40px',
            color: 'rgb(170,170,170)'
          })
          .fadeIn(300)
        $('.timer').fadeIn(300)
        $('.verify')
          .show()
          .animate({ height: '60px' }, 200, () => $('.code-check').fadeIn(300))

        let time = 180
        timerInterval = setInterval(() => {
          time--
          $('.timer').text('0' + Math.floor(time / 60) + ':' + (time % 60 < 10 ? '0' + (time % 60) : time % 60))
          if (time == 170) {
            $('.code-send')
              .text('다시 보내기')
              .css({ 'pointer-events': 'all', color: 'rgb(98,98,98)' })
              .removeClass('clicked')
          } else if (time <= 0) {
            $('.timer').text('시간 만료')
            fetch('/verify/expire?phone=' + $('.phone').val()).catch(alert)
            clearInterval(timerInterval)
          }
        }, 1000)
        break
      case false:
        alert(result.error)
        it.removeClass('clicked')
        break
    }
  })

  $('.code-check').click(async (e) => {
    const phone = $('.phone')
    const result = await fetch('/verify/check?phone=' + phone.val() + '&code=' + $('.code').val())
      .then((res) => res.json())
      .catch(console.log)

    switch (result.success) {
      case true:
        alert('인증이 완료되었습니다.')
        $('.code-send')
          .hide()
          .text('인증 완료')
          .css({
            'pointer-events': 'none',
            'line-height': '60px',
            color: 'rgb(170,170,170)'
          })
          .addClass('clicked')
          .fadeIn(300)
        $('.timer').hide()
        clearInterval(timerInterval)
        phone.prop('disabled', true)
        phone.parent().find('.reset').remove()
        $('#verifyCodeId').val(result.codeId)
        $('.verify').animate({ height: '0' }, 300, () => $('.verify').hide())
        break
      case false:
        alert('인증번호가 일치하지 않습니다.')
        break
    }
  })

  $('.view').click((e) => {
    const it = $(e.target)
    switch (it.hasClass('clicked')) {
      case true:
        it.attr('src', '/img/icon/ico_eye@2x.png').removeClass('clicked')
        it.parent().find('input').attr('type', 'password')
        break
      case false:
        it.attr('src', '/img/icon/ico_eye_active@2x.png').addClass('clicked')
        it.parent().find('input').attr('type', 'text')
        break
    }
  })

  const pwRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,14}$/
  $('.pw').on('keyup focus', (e) => pwCheck(e.target, pwRegex.test(e.target.value)))
  $('.pw-check').on('keyup focus', (e) => pwCheck(e.target, $('.pw').val() == $('.pw-check').val()))

  function pwCheck(_this, boolean) {
    const target = $(_this).closest('.form-group')
    switch ($(_this).val() == '') {
      case true:
        target.removeClass('passed not-passed')
        break
      case false:
        it(target).if(boolean).addClass('passed').removeClass('not-passed')
        it('.pw-change').if(all('.form-group').hasClass('passed')).removeClass('disable')
        break
    }
  }

  $('.zipcode, .city').on('keyup focus', (e) => {
    $('.street').focus()
    new daum.Postcode({
      oncomplete: (data) => {
        $('.zipcode').val(data.zonecode)
        $('.city').val(data.address)
      }
    }).open()
    addressCheck()
  })

  $('.street').on('keyup focus', (e) => {
    addressCheck()
  })

  function addressCheck() {
    it('.zipcode')
      .if($('.zipcode').val() != '')
      .addClass('passed')
    it('.city')
      .if($('.city').val() != '')
      .addClass('passed')
    it('.street')
      .if($('.street').val() != '')
      .addClass('passed')
  }

  $(document).on('click', '.join', async (e) => {
    const result = await fetch('/join', {
      method: 'post',
      cache: 'no-cache',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: $('form').serialize()
    })
      .then((res) => res.json())
      .catch(alert)

    switch (result.success) {
      case true:
        alert('회원가입이 완료되었습니다.')
        location.replace('/login')
        break
      case false:
        alert(result.error)
        break
    }
  })
})
