$(() => {
  windowResize()
  allSelectCheck()
  minusCheck()
  sumUpdate()
  $(window).resize(windowResize)
  $(document).on('click', '.row .check', allSelectCheck)
  $(document).on('click', '.all-select .check', allSelect)
  $(document).on('click', '.check', syncSelect)
  $(document).on('click', '.option-box .del-btn', () => removeCart(0))
  $(document).on('click', '.row .del-btn', (e) => removeCart(e.target))
  $(document).on('keyup', '.count', (e) => modifyCart(e.target, 0))
  $(document).on('click', '.minus-btn', (e) => modifyCart(e.target, -1))
  $(document).on('click', '.plus-btn', (e) => modifyCart(e.target, 1))
})

function windowResize() {
  const width = $(window).width()
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
}

async function modifyCart(_this, typeCode) {
  const row = $(_this).closest('.row')
  const count = row.find('.count')
  const countVal = Number(count.val()) + typeCode
  const bookId = row.find('.check').data('id')
  const result = await fetch('/cart/modify?bookId=' + bookId + '&amount=' + countVal).then((res) => res.text())

  switch (result) {
    case 'ok':
      const originPrice = row.find('.origin-price')
      const originPriceVal = Number(originPrice.data('value')) * countVal
      const discountPrice = row.find('.discount-price')
      const discountPriceVal = Number(discountPrice.data('value')) * countVal
      originPrice.text(originPriceVal.toLocaleString('ko-KR') + '원')
      discountPrice.text(discountPriceVal.toLocaleString('ko-KR') + '원')
      if (typeCode != 0) count.val(countVal)
      sumUpdate()
      minusCheck()
      break
    case 'min':
      alert('최솟값입니다.')
      count.val(1)
      break
    case 'error':
      alert('요청이 실패하였습니다.')
      break
    default:
      alert('재고가 부족합니다.')
      count.val(result)
  }
}

function allSelect() {
  $('.check')
    .get()
    .forEach((e) => (e.checked = $('.all-select .check').prop('checked')))
}

function allSelectCheck() {
  $('.all-select .check').prop(
    'checked',
    $('.row .check')
      .get()
      .every((e) => e.checked)
  )
}

async function syncSelect() {
  const data = $('.row input:checked')
    .get()
    .map((e) => e.dataset.id)
  const result = await fetch('/cart/select', {
    method: 'post',
    cache: 'no-cache',
    body: new URLSearchParams({ bookIds: data })
  })
    .then((res) => res.json())
    .catch(console.log)

  switch (result.success) {
    case true:
      sumUpdate()
      break
    case false:
      alert('요청이 실패하였습니다.')
      console.log(result.error)
  }
}

async function removeCart(_this) {
  const isOne = _this == 0 ? false : true
  const data = isOne
    ? $(_this).closest('.row').find('.check').data('id')
    : $('.row input:checked')
        .get()
        .map((e) => e.dataset.id)

  const success = await fetch('/cart/remove', {
    method: 'post',
    cache: 'no-cache',
    body: new URLSearchParams({ bookIds: data })
  })
    .then((res) => res.json())
    .catch(console.log)

  switch (success) {
    case true:
      isOne
        ? $(_this).parent().remove()
        : $('.row input:checked')
            .get()
            .forEach((e) => e.parentNode.remove())
      $('.row').get().length == 0 ? location.reload() : sumUpdate()
      break
    case false:
      alert('요청이 실패하였습니다.')
  }
}

function minusCheck() {
  $('.amount-box')
    .get()
    .forEach((e) => {
      e.querySelector('input').value < 2
        ? e.querySelector('.minus-btn').classList.add('disable')
        : e.querySelector('.minus-btn').classList.remove('disable')
    })
}

async function sumUpdate() {
  const update = await fetch('/cart/sum').then((res) => res.text())
  $('#sum').replaceWith(update)
}
