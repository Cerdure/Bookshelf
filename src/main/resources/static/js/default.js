$(function () {
  let currentIndex = $('#page-content').data('index')

  $('.top-nav ul li')
    .hover(function () {
      let index = $(this).index()
      if (index == 0) {
        $('#underbar').stop().animate({ left: '0' }, 200, 'swing')
      } else {
        $('#underbar')
          .stop()
          .animate({ left: 20 * index + '%' }, 200, 'swing')
      }
    })
    .click(function () {
      $(this).animate({ color: 'black' }, 300)
      $('.top-nav ul li').not(this).css('color', 'rgb(180, 180, 180)')
      currentIndex = $(this).index()
    })
    .mouseleave(function () {
      if (currentIndex == 0) {
        $('#underbar').stop().animate({ left: '0' }, 200, 'swing')
      } else {
        $('#underbar')
          .stop()
          .animate({ left: 20 * currentIndex + '%' }, 200, 'swing')
      }
    })

  $('.bottom-nav ul li')
    .hover(function () {
      let index = $(this).index()
      if (index == 0) {
        $('#overbar').stop().animate({ left: '0%' }, 600, 'swing')
      } else {
        $('#overbar')
          .stop()
          .animate({ left: 20 * (index - 1) + '%' }, 300, 'swing')
      }
    })
    .mouseleave(function () {
      if (currentIndex == 0) {
        $('#overbar').stop().animate({ left: '0%' }, 600, 'swing')
      } else {
        $('#overbar')
          .stop()
          .animate({ left: 20 * currentIndex + '%' }, 300, 'swing')
      }
    })

  let st = $(window).scrollTop()
  let width = $(window).width()

  if (width > 999) {
    $('.top-nav ul li').fadeIn(500)
    $('.bottom-nav-wrapper').stop().animate({ bottom: '-80px' }, 500)
    $('.top-move-button').stop().animate({ bottom: '20px' }, 500)
    $('footer').stop().animate({ 'margin-bottom': '0px' }, 500)
  } else {
    $('.bottom-nav-wrapper').stop().animate({ bottom: '0px' }, 500)
    $('.top-move-button').stop().animate({ bottom: '60px' }, 500)
    $('footer').stop().animate({ 'margin-bottom': '40px' }, 500)
    $('.top-nav ul li').fadeOut(500)
  }

  $(window).resize(function () {
    st = $(this).scrollTop()
    width = $(this).width()
    if (width > 999) {
      $('.top-nav ul li').fadeIn(500)
      $('.bottom-nav-wrapper').stop().animate({ bottom: '-80px' }, 500)
      $('.top-move-button').stop().animate({ bottom: '20px' }, 500)
      $('footer').stop().animate({ height: '173px' }, 500)
    } else {
      $('.bottom-nav-wrapper').stop().animate({ bottom: '0px' }, 500)
      $('.top-move-button').stop().animate({ bottom: '60px' }, 500)
      $('footer').stop().animate({ height: '223px' }, 500)
      $('.top-nav ul li').fadeOut(500)
    }
  })

  $('.footer-infor-title').click(function () {
    if (!$(this).hasClass('clicked')) {
      $('.footer-infor').fadeIn(300)
      $(this).text('사업자 정보 닫기 ∧')
      $(this).addClass('clicked')
    } else {
      $('.footer-infor').fadeOut(300)
      $(this).text('사업자 정보 열기 ∨')
      $(this).removeClass('clicked')
    }
  })

  $('.top-move-button').click(function () {
    $('html').stop().animate({ scrollTop: 0 }, 1000)
  })

  $('#cart').click(function () {
    location.href = '/cart'
  })
})

function dom(_this) {
  return document.querySelector(_this)
}

function domAll(_this) {
  return document.querySelectorAll(_this)
}

const all = (_class) => {
  return {
    hasClass: (_hasClass) =>
      $(_class)
        .get()
        .every((e) => e.classList.contains(_hasClass))
  }
}

const allPassed = (...passed) => passed.every((e) => e)

function it(_this) {
  let _if = null
  return {
    if: function (boolean) {
      _if = boolean
      return this
    },
    addClass: function (_class) {
      if (_if) $(_this).addClass(_class)
      else $(_this).removeClass(_class)
      return this
    },
    removeClass: function (_class) {
      if (_if) $(_this).removeClass(_class)
      else $(_this).addClass(_class)
    },
    activeClass: function (_class, other) {
      $(other).removeClass(_class)
      $(_this).addClass(_class)
      return this
    },
    toggleSelf: function (time) {
      if (_if) $(_this).fadeIn(time)
      else $(_this).fadeOut(time)
      return this
    }
  }
}

function formLineResolver(_form) {
  const textareaArr = $(_form).find('textarea').get()
  textareaArr.forEach((e) => (e.value = e.value.replace(/\n/g, '<br>')))
}

function resize(_this) {
  _this.style.height = _this.scrollHeight + 'px'
}

async function anonymousBlock() {
  const isAnonymous = await fetch('/login/check').then((res) => res.json())
  if (isAnonymous) {
    alert('로그인 후 이용가능합니다.')
    location.href = '/login'
    return
  }
}

function hideModal() {
  $('.alert-modal').fadeOut(200)
}

function hideModal_back() {
  $('.alert-modal').fadeOut(100)
  $('.modal-background').fadeOut(200)
}

function flexFadeIn(_this) {
  $(_this).css('display', 'flex')
  $(_this).animate({ opacity: '1' }, 500)
}

function flexFadeIn_back(_this) {
  $('.modal-background').fadeIn(100)
  $(_this).css('display', 'flex')
  $(_this).animate({ opacity: '1' }, 500)
  $('body').css('overflow-y', 'hidden')
}

function flexFadeOut(_this) {
  $(_this).animate({ opacity: '0' }, 200)
  $(_this).css('display', 'none')
}

function flexFadeOut_back(_this) {
  $(_this).animate({ opacity: '0' }, 100)
  $(_this).css('display', 'none')
  $('.modal-background').fadeOut(200)
  $('body').css('overflow-y', 'auto')
}
