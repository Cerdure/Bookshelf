$(() => {
  let width = $(window).width()

  if (width < 1000) {
    $('.bottom-nav-wrapper').stop().animate({ bottom: '0px' }, 500)
    $('.bottom-nav-sub-wrapper').stop().animate({ bottom: '50px' }, 500)
    $('.main-wrapper').stop().animate({ 'padding-right': '0' }, 500)
    $('.side-wrapper').stop().animate({ right: '-300px' }, 500)
    $('.other-reviews').stop().css({ 'margin-bottom': '100px' })
  } else {
    $('.bottom-nav-wrapper').stop().animate({ bottom: '-80px' }, 500)
    $('.bottom-nav-sub-wrapper').stop().animate({ bottom: '-80px' }, 500)
    $('.main-wrapper').stop().animate({ 'padding-right': '200px' }, 500)
    $('.side-wrapper').stop().animate({ right: '0' }, 300)
    $('.other-reviews').stop().css({ 'margin-bottom': '0' })
  }

  $(window).resize((e) => {
    width = $(e.target).width()
    if (width < 1000) {
      $('.bottom-nav-wrapper').stop().animate({ bottom: '0px' }, 500)
      $('.bottom-nav-sub-wrapper').stop().animate({ bottom: '50px' }, 500)
      $('.main-wrapper').stop().animate({ 'padding-right': '0' }, 500)
      $('.side-wrapper').stop().animate({ right: '-300px' }, 500)
      $('.other-reviews').stop().css({ 'margin-bottom': '100px' })
    } else {
      $('.bottom-nav-wrapper').stop().animate({ bottom: '-80px' }, 500)
      $('.bottom-nav-sub-wrapper').stop().animate({ bottom: '-80px' }, 500)
      $('.main-wrapper').stop().animate({ 'padding-right': '200px' }, 500)
      $('.side-wrapper').stop().animate({ right: '0' }, 300)
      $('.other-reviews').stop().css({ 'margin-bottom': '0' })
    }
  })

  $(document).on('click', '.info-option', async (e) => {
    const it = $(e.target).closest('.info-option')
    const type = it.data('type')
    if (type == 'direct') {
      $('#info input').val('')
      return
    }
    const result = await fetch('/order/info?type=' + type).then((res) => res.text())
    $('#info').replaceWith(result)
  })

  $(document).on('click', '.img-box', (e) => {
    $('.select-icon').hide()
    $('.img-box').css('border', '1px solid lightgray')
    const box = $(e.target).closest('.img-box')
    box.find('.select-icon').fadeIn(0)
    box.css('border', '1px solid cornflowerblue')
    box.find('input').prop('checked', true)
  })

  $(document).on('click', '.post-btn', (e) => {
    new daum.Postcode({
      oncomplete: function (data) {
        $('.zipcode').val(data.zonecode)
        $('.city').val(data.address)
      }
    }).open()
  })

  $(document).on('click', '.coupon-value, .all-coupon', (e) => {
    $('.modal-background').fadeIn(100)
    flexFadeIn('.coupon-modal')
  })

  $(document).on('click', '.coupon', (e) => {
    const it = $(e.target).closest('.coupon')
    couponVal = it.find('.coupon-price').data('value')
    $('.coupon').removeClass('select-coupon')
    it.addClass('select-coupon')
    priceCalc()
    flexFadeOut('.coupon-modal')
    $('.modal-background').fadeOut(100)
  })

  $(document).on('click', '.coupon-modal .top img, .modal-background', (e) => {
    flexFadeOut('.coupon-modal')
    $('.modal-background').fadeOut(100)
  })

  $(document).on('click', '.coupon-cancel', (e) => {
    $('.coupon').removeClass('select-coupon')
    couponVal = 0
    priceCalc()
  })

  const restPointCheck = (point) => fetch('/order/point/rest?point=' + point).then((res) => res.json())

  $(document).on('click', '.all-point', async (e) => {
    const allPoint = $('.rest').data('value')
    priceSum = $('.origin-sum').data('value') + $('.delivery-charge').data('value')
    if (allPoint > 0) {
      usePoint = priceSum - couponVal > allPoint ? allPoint : priceSum - couponVal
      restPoint = await restPointCheck(usePoint)
      if (restPoint < 0) {
        alert('요청이 실패하였습니다.')
      } else {
        priceCalc()
      }
    }
  })

  $(document).on('keyup', '.point-value', async (e) => {
    const it = $(e.target)
    if (it.val() == '') return
    const allPoint = $('.rest').data('value')
    priceSum = $('.origin-sum').data('value') + $('.delivery-charge').data('value')
    usePoint = it.val() > priceSum - couponVal ? priceSum - couponVal : it.val()
    restPoint = await restPointCheck(usePoint)

    if (restPoint < 0) {
      alert('사용 가능한 포인트를 초과했습니다.')
      usePoint = allPoint
      restPoint = 0
    }
    priceCalc()
  })

  $(document).on('click', '.agree-check', (e) => {
    e.target.checked ? $('.pay-btn').removeClass('disable') : $('.pay-btn').addClass('disable')
  })

  $(document).on('click', '.pay-btn', async (e) => {
    if (!$('.agree-check').prop('checked')) {
      alert('구매조건 및 결제에 동의해야 진행가능합니다.')
      return
    }
    const orderInfo = await fetch('/order/create/prev').then((res) => res.json())
    if (orderPrice == 0) {
      createOrder(orderInfo.id)
      return
    }
    const name =
      $('.row').get().length == 1
        ? $('.row:nth-child(2) .book').text()
        : $('.row:nth-child(2) .book').text() + ' 외 ' + ($('.row').get().length - 1) + '건'
    IMP.init('imp78314138')
    IMP.request_pay(
      {
        pg: $('.pay-type:checked').val(),
        pay_method: 'card',
        merchant_uid: orderInfo.id,
        name: name,
        amount: $('.order-price').val(),
        buyer_email: orderInfo.member.eamil,
        buyer_name: orderInfo.member.name,
        buyer_tel: orderInfo.member.phone,
        buyer_addr: orderInfo.member.address.city,
        buyer_postcode: orderInfo.member.address.zipcode
      },
      (res) => {
        if (res.success) {
          createOrder(orderInfo.id)
        } else {
          alert('실패 : 코드(' + res.error_code + ') / 메세지(' + res.error_msg + ')')
        }
      }
    )
  })
})

let priceSum,
  orderPrice,
  couponVal = 0,
  usePoint = 0,
  restPoint = -1

function priceCalc() {
  priceSum = $('.origin-sum').data('value') + $('.delivery-charge').data('value')
  restPoint = restPoint == -1 ? $('.rest').data('value') : restPoint
  usePoint = usePoint > priceSum - couponVal ? priceSum - couponVal : usePoint
  orderPrice = priceSum - couponVal - usePoint
  $('.coupon-value').val(couponVal)
  $('.point-value').val(usePoint)
  $('.rest')
    .attr('data-value', restPoint)
    .text('잔여 포인트 : ' + restPoint.toLocaleString('ko-KR'))
  it('.all-point')
    .if(restPoint == 0)
    .addClass('disable')
  it('.coupon-data')
    .if(couponVal > 0)
    .toggleSelf(0)
  it('.point-data')
    .if(usePoint > 0)
    .toggleSelf(0)
  $('.payment-sum-box .coupon').text(couponVal.toLocaleString('ko-KR') + ' 쿠폰')
  $('.payment-sum-box .point').text(usePoint.toLocaleString('ko-KR') + ' 포인트')
  $('.payment-sum-box .data').text(orderPrice.toLocaleString('ko-KR'))
  $('.order-price').val(orderPrice)
  orderPrice <= 0 ? $('.payment-method-box').addClass('disable') : ''
  return orderPrice
}

async function createOrder(orderId) {
  const couponId = $('.select-coupon').data('id') == undefined ? -1 : $('.select-coupon').data('id')
  const getPoint = Math.ceil((Number($('.order-price').val()) * $('.point-rate').data('value')) / 1000) * 10
  console.log(couponId)
  const orderResult = await fetch('/order/create', {
    method: 'post',
    cache: 'no-cache',
    body: new URLSearchParams({
      id: orderId,
      receiver: $('.receiver').val(),
      phone: $('.phone-1').val() + $('.phone-2').val() + $('.phone-3').val(),
      tel: $('.tel-1').val() + $('.tel-2').val() + $('.tel-3').val(),
      zipcode: $('.zipcode').val(),
      city: $('.city').val(),
      street: $('.street').val(),
      deliveryPlace: $('.where').val(),
      originSum: $('.origin-sum').data('value'),
      deliveryCharge: $('.delivery-charge').data('value'),
      couponId: couponId,
      point: $('.point-value').val(),
      orderPrice: $('.order-price').val(),
      payType: $('.pay-type:checked').val()
    })
  }).then((res) => res.text())

  if (orderResult) {
    const itemResult = (e) =>
      fetch('/order/item', {
        method: 'post',
        cache: 'no-cache',
        body: new URLSearchParams({
          orderId: orderId,
          bookId: e.querySelector('.book').dataset.id,
          amount: e.querySelector('.amount').innerHTML
        })
      }).then((res) => res.json())

    const result = await $('.row')
      .get()
      .map((e) => itemResult(e))

    if (result.every((e) => e)) {
      location.href = '/order/success?orderId=' + orderId + '&couponId=' + couponId + '&point=' + getPoint
      return
    }
  }
  alert('요청이 실패하였습니다. (error: order)')
}
