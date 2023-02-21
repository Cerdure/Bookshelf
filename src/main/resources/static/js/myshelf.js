$(() => {
  windowScroll()
  windowResize()
  $(window).scroll(windowScroll)
  $(window).resize(windowResize)

  $('.middle-nav div').click((e) => {
    it(e.target).activeClass('active-index', '.middle-nav div')
    $('.content-box').hide().css('opacity', '0')
    flexFadeIn('.content-box:nth-child(' + ($(e.target).index() + 1) + ')')
  })

  $(document).on('click', '.coupon-box', (e) => flexFadeIn_back('.coupon-modal'))
  $(document).on('click', '.coupon-modal .top img, .modal-background', (e) => flexFadeOut_back('.coupon-modal'))
  $(document).on('click', '.bookmark-contents-wrapper', (e) => flexFadeIn_back('.all-bookmark'))
  $(document).on('click', '.order-book-contents-wrapper', (e) => flexFadeIn_back('.all-order-book'))
  $(document).on('click', '.shelf-modal .close, .modal-background', (e) => flexFadeOut_back('.shelf-modal'))
  $(document).on('click', '.order-list-title-button', detailSearchOpen)
  $(document).on('click', '.search-info-box', detailSearchOpen)
  $(document).on('click', '.detail-title img', detailSearchHide)

  $('.detail-bottom-apply').click(async (e) => {
    const result = await reqOrderData()
    $('#order-box').replaceWith(result)
    $('.order-list-title-info').hide()
    detailSearchHide()
  })

  $('#detail-range-select').change((e) => {
    $('.detail-range-start, .detail-range-end').addClass('detail-range-disable')
    switch ($('#detail-range-select :selected').index()) {
      case 0:
        $('.detail-range-start').datepicker('setDate', '-1m')
        break
      case 1:
        $('.detail-range-start').datepicker('setDate', '-2m')
        break
      case 2:
        $('.detail-range-start').datepicker('setDate', '-3m')
        break
      case 3:
        $('.detail-range-start').datepicker('setDate', '-6m')
        break
      default:
        $('.detail-range-start, .detail-range-end').removeClass('detail-range-disable')
        $('.detail-range-start').datepicker('setDate', 'today')
    }
  })

  const datepickerFormat = {
    dateFormat: 'yy-mm-dd',
    showOtherMonths: true,
    showMonthAfterYear: true,
    changeYear: true,
    changeMonth: true,
    showOn: 'both',
    buttonImage: 'http://jqueryui.com/resources/demos/datepicker/images/calendar.gif',
    buttonImageOnly: true,
    buttonText: '선택',
    yearSuffix: '년',
    monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
    dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
    dayNames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
    minDate: '-5Y',
    maxDate: '0D'
  }

  $('.detail-range-start').datepicker(datepickerFormat).datepicker('setDate', '-1m')
  $('.detail-range-end').datepicker(datepickerFormat).datepicker('setDate', 'today')

  $('.detail-range-start').datepicker()
  $('.detail-range-start').datepicker('option', 'maxDate', $('.detail-range-end').val())
  $('.detail-range-start').datepicker('option', 'onClose', (selectedDate) =>
    $('.detail-range-end').datepicker('option', 'minDate', selectedDate)
  )

  $('.detail-range-end').datepicker()
  $('.detail-range-end').datepicker('option', 'minDate', $('.detail-range-start').val())
  $('.detail-range-end').datepicker('option', 'onClose', (selectedDate) =>
    $('.detail-range-start').datepicker('option', 'maxDate', selectedDate)
  )

  $('.detail-bottom-reset').click(async (e) => {
    const result = await fetch('/myshelf/order/reset').then((res) => res.text())
    detailSearchHide()
    $('.order-list-title-info').show()
    $('#order-box').replaceWith(result)
    $('.order-list-detail-search input').val('')
    $('.detail-range-start').datepicker('setDate', '-1m')
    $('#detail-state-select').val('ALL').prop('selected', true)
    $('#detail-range-select').val('month-1').prop('selected', true)
    $('.detail-range-start, .detail-range-end').addClass('detail-range-disable')
  })

  $(document).on('click', '.next-btn', async (e) => {
    const it = $(e.target)
    const result = await reqMoreOrderData(Number($('#page').val()) + 1)
    let div = document.createElement('add-page')
    div.innerHTML = result
    dom('.order-list-contents-wrapper').appendChild(div)
    const isLast = $('add-page:last-child #page-box').data('last')
    if (isLast) it.hide()
  })

  $(document).on('click', '.order-cancel-btn', (e) => {
    const it = $(e.target)
    selectRow = it.closest('.row')
    const price = selectRow.data('price')
    const items = selectRow.find('.item').get()
    const itemsCount = Number(items.length)
    const alert = dom('.alert-btn-2')
    alert.querySelector('.text').innerHTML = '주문을 취소하시겠습니까?'
    alert.querySelector('.data').innerHTML =
      '주문번호 : ' +
      selectRow.find('.order-id').text() +
      '<br>상품명 : ' +
      items[0].querySelector('.name').innerHTML +
      (itemsCount > 1 ? ' 외 ' + (itemsCount - 1) + '건<br>' : '<br>') +
      '환불 예상 금액 : ' +
      price.toLocaleString('ko-KR') +
      '원 (포인트 포함)'
    alert.querySelector('.ok').addEventListener('click', orderCancel)
    alert.querySelector('.no').addEventListener('click', () => flexFadeOut_back('.alert-btn-2'))
    flexFadeIn_back('.alert-btn-2')
  })

  $(document).on('click', '.item-cancel-btn', (e) => {
    const it = $(e.target)
    selectRow = it.closest('.row')
    selectItem = it.closest('.item')
    const price = selectItem.find('.price').text()
    const alert = dom('.alert-btn-2')
    alert.querySelector('.text').innerHTML = '주문을 취소하시겠습니까?'
    alert.querySelector('.data').innerHTML =
      '상품명 : ' + selectItem.find('.name').text() + '<br>' + '환불 예상 금액 : ' + price + ' (포인트 포함)'
    alert.querySelector('.ok').addEventListener('click', itemCancel)
    alert.querySelector('.no').addEventListener('click', () => flexFadeOut_back('.alert-btn-2'))
    flexFadeIn_back('.alert-btn-2')
  })

  $(document).on('click', '.order-detail-btn', async (e) => {
    const orderId = $(e.target).closest('.row').find('.order-id').text()
    const result = await fetch('/myshelf/order/detail?orderId=' + orderId)
      .then((res) => res.text())
      .catch((err) => {
        alert('요청이 실패하였습니다.')
        console.log(err)
      })
    $('.order-detail').replaceWith(result)
    flexFadeIn_back('.order-detail')
  })

  $(document).on('click', '.close, .modal-background', () => flexFadeOut_back('.order-detail'))

  $(document).on('click', '.reset', (e) => $(e.target).closest('.input-box').find('input').val(''))

  $(document).on('click change', '.change-btn', async (e) => {
    const info = $(e.target).closest('.info-group')
    const type = e.target.dataset.type
    let formData = new FormData()
    switch (type) {
      case 'profile':
        formData.append('profileImg', e.target.files[0])
        break
      case 'address':
        formData.set('zipcode', info.find('.zipcode').val())
        formData.set('city', info.find('.city').val())
        formData.set('street', info.find('.street').val())
        break
      case 'phone':
        const result = await fetch('/verify/check?phone=' + $('.new-phone').val() + '&code=' + $('.code').val())
          .then((res) => res.json())
          .catch(console.log)
        if (!result.success) {
          alert(result.error)
          return
        }
      default:
        formData.set(type, info.find('.info-data').val())
    }

    const result = await fetch('/myshelf/info/update', {
      method: 'post',
      cache: 'no-cache',
      body: formData
    })
      .then((res) => res.json())
      .catch((err) => console.log(err))
    switch (result.success) {
      case true:
        alert('변경되었습니다.')
        switch (type) {
          case 'profile':
            $('.profile').attr('src', result.profile)
            $('.info-profile').attr('src', result.profile)
            $('.profile-change').remove()
            let input = document.createElement('input')
            input.setAttribute('type', 'file')
            input.setAttribute('class', 'profile-change column-box change-btn')
            input.setAttribute('data-type', 'profile')
            $('.profile-change-icon').before(input)
            break
          case 'phone':
            alert('다시 로그인해주세요.')
            location.replace('/logout')
        }
        break
      case false:
        alert('요청이 실패하였습니다.')
        console.log(result.error)
    }
  })

  $(document).on('click', '.profile-reset', async (e) => {
    const result = await fetch('/myshelf/info/profile/reset')
      .then((res) => res.json())
      .catch((err) => console.log(err))
    switch (result.success) {
      case true:
        alert('변경되었습니다.')
        $('.profile').attr('src', '/img/icon/default_profile.png')
        $('.info-profile').attr('src', '/img/icon/default_profile.png')
        break
      case false:
        alert('요청이 실패하였습니다.')
        console.log(result.error)
    }
  })

  const originPhone = $('.phone').val()
  const originNickname = $('.nickname').val()
  const originName = $('.name').val()
  const originEmail = $('.email').val()
  const originZipcode = $('.zipcode').val()
  const originStreet = $('.street').val()

  $('.pw').on('keyup focus', (e) => pwCheck(e.target, pwRegex.test(e.target.value)))
  $('.pw-check').on('keyup focus', (e) => pwCheck(e.target, $('.pw').val() == $('.pw-check').val()))
  $('.new-phone').on('keyup focus', (e) =>
    passCheck(e.target, phoneRgx.test(e.target.value) && e.target.value != originPhone)
  )
  $('.nickname').on('keyup focus', (e) =>
    passCheck(e.target, nickRgx.test(e.target.value) && e.target.value != originNickname)
  )
  $('.name').on('keyup focus', (e) => passCheck(e.target, nameRgx.test(e.target.value) && e.target.value != originName))
  $('.email').on('keyup focus', (e) =>
    passCheck(e.target, emailRgx.test(e.target.value) && e.target.value != originEmail)
  )
  $(document).on('change keyup focus', '.zipcode, .street', (e) =>
    it('.address-box button')
      .if($('.zipcode').val() == originZipcode && $('.street').val() == originStreet)
      .addClass('disable')
  )

  $('.zipcode, .city').on('keyup focus', (e) => {
    $('.street').focus()
    new daum.Postcode({
      oncomplete: (data) => {
        $('.zipcode').val(data.zonecode)
        $('.city').val(data.address)
      }
    }).open()
  })

  $(document).on('click', '.leave-btn', (e) => {
    const alert = dom('.alert-btn-2')
    alert.querySelector('.text').innerHTML = '탈퇴 시 회원 정보가 사라집니다.<br>정말로 탈퇴하겠습니까?'
    alert.querySelector('.ok').addEventListener('click', leaveBookshelf)
    alert.querySelector('.no').addEventListener('click', () => flexFadeOut_back('.alert-btn-2'))
    flexFadeIn_back('.alert-btn-2')
  })
})

let selectRow, selectItem
const phoneRgx = /01[016789][0-9]{4}[0-9]{4}/
const pwRgx = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,14}$/
const nickRgx = /^[가-힣|a-z|A-Z|0-9]{3,10}$/
const nameRgx = /^[가-힣]{2,}$/
const emailRgx = new RegExp(
  '([!#-\'*+/-9=?A-Z^-~-]+(.[!#-\'*+/-9=?A-Z^-~-]+)*|"([]!#-[^-~ \t]|(\\[\t -~]))+")' +
    "@([!#-'*+/-9=?A-Z^-~-]+(.[!#-'*+/-9=?A-Z^-~-]+)*|[[\t -Z^-~]*])"
)

function windowScroll() {
  it('#page-content')
    .if($(window).scrollTop() > 220)
    .addClass('middle-nav-fix')
}

function windowResize() {
  $('.top-background').css('background-size', $('.top-back-wrapper').width() + 'px')
}

function detailSearchOpen() {
  $('.modal-background').fadeIn(100)
  $('.order-list-title-detail-wrapper').fadeIn(200)
  $('body').css('overflow-y', 'hidden')
}

function detailSearchHide() {
  $('.modal-background').hide()
  $('.order-list-title-detail-wrapper').hide()
  $('body').css('overflow-y', 'scroll')
}

const reqOrderData = () => {
  $('#page').val(1)
  return fetch('/myshelf/order', {
    method: 'post',
    cache: 'no-cache',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: $('.order-list-title-detail-wrapper').serialize()
  }).then((res) => res.text())
}

const reqMoreOrderData = (page) => {
  $('#page').val(page)
  return fetch('/myshelf/order/add', {
    method: 'post',
    cache: 'no-cache',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: $('.order-list-title-detail-wrapper').serialize()
  }).then((res) => res.text())
}

async function orderCancel() {
  const orderId = selectRow.find('.order-id').text()
  const items = selectRow.find('.item').get()
  const success = await fetch('/myshelf/order/cancel?orderId=' + orderId).then((res) => res.json())
  switch (success) {
    case true:
      selectRow
        .find('.order-cancel-btn')
        .text('취소된 주문')
        .addClass('disable btn-gray')
        .removeClass('order-cancel-btn')
        .removeClass('btn-red')
      items.forEach((item) => {
        item.querySelector('.state').innerHTML = '주문취소'
        const itemCancelBtn = item.querySelector('.item-cancel-btn')
        itemCancelBtn.innerHTML = '취소된 주문'
        itemCancelBtn.classList.add('disable')
        itemCancelBtn.classList.add('btn-gray')
        itemCancelBtn.classList.remove('item-cancel-btn')
        itemCancelBtn.classList.remove('btn-red')
      })
      alert('주문이 정상적으로 취소되었습니다.')
      break
    case false:
      alert('요청이 실패하였습니다.')
  }
  flexFadeOut_back('.alert-btn-2')
}

async function itemCancel() {
  const orderId = selectRow.find('.order-id').text()
  const orderItemId = selectItem.data('id')
  const success = await fetch('/myshelf/order/item/cancel?orderId=' + orderId + '&orderItemId=' + orderItemId).then(
    (res) => res.json()
  )
  switch (success) {
    case true:
      selectItem.find('.state').text('주문취소')
      selectItem
        .find('.item-cancel-btn')
        .text('취소된 주문')
        .addClass('disable btn-gray')
        .removeClass('item-cancel-btn')
        .removeClass('btn-red')
      const orderedItems = selectRow.find('.item-cancel-btn').get()
      if (orderedItems.length == 0) {
        selectRow
          .find('.order-cancel-btn')
          .text('취소된 주문')
          .addClass('disable btn-gray')
          .removeClass('order-cancel-btn')
          .removeClass('btn-red')
      }
      alert('주문이 정상적으로 취소되었습니다.')
      break
    case false:
      alert('요청이 실패하였습니다.')
  }
  flexFadeOut_back('.alert-btn-2')
}

function phoneChangeOpen() {
  flexFadeIn_back('.phone-change-modal')
  $('.phone-change-modal .close').click((e) => flexFadeOut_back('.phone-change-modal'))
  $('.modal-background').click((e) => flexFadeOut_back('.phone-change-modal'))
}

let timerInterval

async function verifySend(_this) {
  const it = $(_this)
  if (it.hasClass('clicked')) return
  it.addClass('clicked')
  const result = await fetch('/verify/send/?phone=' + $('.new-phone').val())
    .then((res) => res.json())
    .catch(console.log)

  switch (result.success) {
    case true:
      alert('인증번호가 발송되었습니다.')
      clearInterval(timerInterval)
      $('.timer').hide().text('03:00')
      it.hide().text('발송 완료').addClass('disable').fadeIn(300)
      $('.timer').fadeIn(300)

      let time = 180
      timerInterval = setInterval(() => {
        time--
        $('.timer').text('0' + Math.floor(time / 60) + ':' + (time % 60 < 10 ? '0' + (time % 60) : time % 60))
        if (time == 170) {
          $('.code-send').text('다시 보내기').removeClass('disable').removeClass('clicked')
        } else if (time <= 0) {
          $('.timer').text('시간 만료')
          fetch('/verify/expire?phone=' + $('.new-phone').val()).catch(alert)
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
}

function passCheck(_this, boolean) {
  const target = $(_this).closest('.info-group')
  const btn = target.find('button')
  it(target).if(boolean).addClass('passed').removeClass('not-passed')
  it(btn).if(boolean).removeClass('disable')
}

async function leaveBookshelf() {
  const result = await fetch('/myshelf/leave', {
    method: 'post'
  })
    .then((res) => res.json())
    .catch(console.log)
  switch (result.success) {
    case true:
      alert('탈퇴되었습니다.')
      location.replace('/')
      break
    case false:
      alert('요청이 실패하였습니다.')
      console.log(result.error)
  }
}
