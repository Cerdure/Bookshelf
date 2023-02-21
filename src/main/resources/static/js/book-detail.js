$(function () {
  windowResize()
  $(window).resize(windowResize)
  sideWrapperFix()
  $(window).scroll(sideWrapperFix)
  reviewResize()
  $(window).resize(reviewResize)

  $('.reviews').click((e) =>
    $('html')
      .stop()
      .animate({ scrollTop: $('.main-wrapper').height() - 900 }, 300)
  )

  $('.fold-btn').click((e) => {
    const it = $(e.target)
    switch (!it.hasClass('clicked')) {
      case true:
        it.parent().css({ height: 'auto' })
        it.css('transform', 'rotate(180deg)').addClass('clicked')
        break
      case false:
        it.parent().css({ height: '40px' })
        it.css('transform', 'rotate(0deg)').removeClass('clicked')
    }
    sideWrapperFix()
  })

  $(document).on('click', '.my-review .fold-button', (e) => {
    const parent = $(e.target).closest('.body')
    switch (parent.hasClass('clicked')) {
      case true:
        parent.find('.review').css({ overflow: 'hidden', display: '-webkit-box' })
        parent.find('.icon').css('transform', 'rotate(0deg)')
        break
      case false:
        parent.find('.review').css({ overflow: 'visible', display: 'block' })
        parent.find('.icon').css('transform', 'rotate(180deg)')
    }
    parent.toggleClass('clicked')
    sideWrapperFix()
  })

  let phNum = $('.photos').length

  for (var i = 2; i <= phNum + 1; i++) {
    let photos = '.my-review:nth-child(' + i + ') .photos'
    $(photos).css('background-image', 'url(' + $(photos + ' img:nth-child(2)').attr('src') + ')')
    $(photos + ' .number').text('+' + ($(photos + ' img').length - 1))
  }

  $('.photos')
    .mouseover(function () {
      if (!phOpened) $(this).find('.plus').stop().fadeIn(300)
    })
    .mouseleave(function () {
      if (!phOpened) $(this).find('.plus').stop().fadeOut(300)
    })

  let phOpened = false

  $('.photos').click(function () {
    if (!phOpened) {
      $(this).fadeOut(10, () => {
        $(this).css({
          position: 'fixed',
          top: '50%',
          right: 'auto',
          left: '42%',
          width: '600px',
          height: '400px',
          transform: 'translate(-50%,-50%)',
          'background-size': 'contain',
          'z-index': '11'
        })
        $(this).find('.plus').fadeOut(0)
        $(this).find('.close').show()
        $(this).find('.btn-left').show()
        $(this).find('.btn-right').show()
        $(this).prev().show()
        $(this)
          .find('.number')
          .text('1 / ' + $(this).find('img').toArray().length)
        $(this).find('.number').css({
          bottom: '-30px',
          right: 'auto',
          left: '50%',
          transform: 'translateX(-50%)',
          width: 'auto',
          height: 'auto',
          'background-color': 'rgba(0, 0, 0, 0)',
          'font-size': '25px',
          'line-height': '25px',
          filter: 'drop-shadow(black 0px 0px 2px)',
          'text-shadow': '0px 0px 2px black'
        })
        $(this).show(300)
        phOpened = true
      })
    }
  })

  $('.photos .close').click(function () {
    const photos = $(this).parent()
    photos.hide(100, () => {
      photos.css({
        position: 'absolute',
        top: '0',
        right: '0',
        left: 'auto',
        transform: 'auto',
        width: '64px',
        height: '64px',
        transform: 'none',
        'background-size': 'cover',
        'z-index': '0',
        'background-image': 'url(' + photos.children('img').eq(0).attr('src') + ')'
      })
      photos.find('.close').hide()
      photos.find('.btn-left').hide()
      photos.find('.btn-right').hide()
      photos.find('.number').text('+' + (photos.find('img').toArray().length - 1))
      photos.find('.number').css({
        bottom: '0',
        right: '0',
        left: 'auto',
        transform: 'none',
        width: '20px',
        height: '15px',
        'background-color': 'rgba(0, 0, 0, 0.6)',
        'font-size': '11px',
        'line-height': '15px',
        filter: 'none',
        'text-shadow': 'none'
      })
      phCurrentIndex = 1
      photos.fadeIn(300, () => (phOpened = false))
    })
  })

  let phCurrentIndex = 1

  $('.photos .btn-left').click((e) => {
    const it = $(e.target)
    let photos = it.parent()
    let maxIndex = photos.find('img').toArray().length
    if (phCurrentIndex != 1) {
      photos.fadeOut(100, () => {
        phCurrentIndex--
        photos.css({
          'background-image': 'url(' + photos.children().eq(phCurrentIndex).attr('src') + ')'
        })
        photos.find('.number').text(phCurrentIndex + ' / ' + maxIndex)
      })
      photos.fadeIn(300)
    }
  })

  $('.photos .btn-right').click((e) => {
    const it = $(e.target)
    let photos = it.parent()
    let maxIndex = photos.find('img').toArray().length
    if (phCurrentIndex != maxIndex) {
      photos.fadeOut(100, () => {
        phCurrentIndex++
        photos.css({
          'background-image': 'url(' + photos.children().eq(phCurrentIndex).attr('src') + ')'
        })
        photos.find('.number').text(phCurrentIndex + ' / ' + maxIndex)
      })
      photos.fadeIn(300)
    }
  })

  $('.write-review, .first-review').click(() => {
    $('.modal-background').fadeIn(100)
    $('.review-write-wrapper').fadeIn(200)
    $('body').css('overflow-y', 'hidden')
    rwOpened = true
  })

  $('.cart-btn').click(async () => {
    const result = await fetch('/cart/modify?bookId=' + $('#book-id').data('value') + '&amount=1').then((res) =>
      res.text()
    )
    const alert = dom('.alert-btn-1')
    const alertText = alert.querySelector('.text')
    switch (result) {
      case 'ok':
        alertText.innerHTML = '장바구니에 담겼습니다.'
        break
      case 'sold':
        alertText.innerHTML = '재고가 부족합니다.'
        break
      default:
        alertText.innerHTML = '요청이 실패하였습니다.'
    }
    alert.querySelector('.ok').addEventListener('click', hideModal_back)
    flexFadeIn_back('.alert-btn-1')
  })

  $(document).on('mouseover', 'form .star-empty img', (e) => {
    const it = $(e.target)
    let index = it.index()
    it.closest('.review-book-rating')
      .find('.star-fill')
      .stop()
      .animate({ width: 34.5 * (index + 1) + 'px' }, 300)
    it.closest('.review-book-info')
      .find('.rating-number')
      .text(index + 1)
  })

  $(document).on('click', 'form .star-empty img', (e) => {
    const it = $(e.target)
    it.closest('.review-book-info')
      .find('.rating-number-input')
      .val(it.index() + 1)
    bookPassed = true
    registCheck(tagPassed, reviewPassed, bookPassed)
  })

  $(document).on('mouseleave', 'form .star-empty img', (e) => {
    const it = $(e.target)
    const clickidx = it.closest('.review-book-info').find('.rating-number-input').val()
    it.closest('.review-book-rating')
      .find('.star-fill')
      .stop()
      .animate({ width: 34.5 * clickidx + 'px' }, 300)
    it.closest('.review-book-info').find('.rating-number').text(clickidx)
  })

  $(document).on('click', '.review-write-tag .tag', (e) => {
    it(e.target).activeClass('tag-active', '.review-write-tag .tag')
    $('.review-write-tag-input').val($(e.target).text())
    tagPassed = true
    registCheck(tagPassed, reviewPassed, bookPassed)
  })

  $(document).on('keyup', 'form textarea', (e) => {
    const it = $(e.target)
    const reviewVal = it.val()
    $('.write-number').text(reviewVal.length + '/3000')
    reviewPassed = reviewVal.length > 9
    registCheck(tagPassed, reviewPassed, bookPassed)
  })

  $(document).on('click', '.review-write-top-icon', (e) => {
    $('form').css('filter', 'brightness(0.8)')
    const alert = dom('.alert-btn-2')
    alert.querySelector('.text').innerHTML = '작성한 내용은 저장되지 않습니다.<br>취소하겠습니까?'
    alert.querySelector('.no').addEventListener('click', () => {
      $('form').css('filter', 'brightness(1)')
    })
    alert.querySelector('.ok').addEventListener('click', formClose)
    flexFadeIn_back('.alert-btn-2')
  })

  $(document).on('click', '.review-write-wrapper .regist-button', async () => {
    formLineResolver('.review-write-wrapper')
    const result = await fetch('/review', {
      method: 'post',
      cache: 'no-cache',
      body: new FormData(dom('.review-write-wrapper'))
    })
      .then((res) => res.json())
      .catch(console.log)

    switch (result.success) {
      case true:
        if (result.getPoint) alert('포인트가 지급되었습니다.')
        location.reload()
        break
      case false:
        alert('요청이 실패하였습니다.')
        console.log(result.error)
    }
  })

  $('.my-review .modify').click(async (e) => {
    const it = $(e.target)
    let mdReviewId = it.closest('.my-review').find('#review-id').val()
    const result = await fetch('/bookReview/' + mdReviewId)
      .then((res) => res.text())
      .catch(console.log)
    $('.review-modify-wrapper').replaceWith(result)
    $('.review-modify-wrapper').attr('action', '/bookReview-modify/' + mdReviewId)
    flexFadeIn_back('.review-modify-wrapper')
    bookPassed = true
    tagPassed = true
    reviewPassed = true
  })

  $('.my-review .delete').click((e) => {
    selectReviewId = $(e.target).closest('.my-review').find('#review-id').val()
    const alert = dom('.alert-btn-2')
    alert.querySelector('.text').innerHTML = '리뷰를 삭제하시겠습니까?'
    alert.querySelector('.ok').addEventListener('click', reviewDelete)
    alert.querySelector('.no').addEventListener('click', hideModal_back)
    flexFadeIn_back('.alert-btn-2')
  })
})

let starClickIndex = -1
let bookPassed = false
let tagPassed = false
let reviewPassed = false
let rwOpened = false
let swOpened = false
let imgCount = 0
let selectReviewId

async function bookMark() {
  const bookId = $('#book-id').data('value')
  const result = await fetch('/book/bookmark?bookId=' + bookId)
    .then((res) => res.json())
    .catch(console.log)
  switch (result.success) {
    case true:
      it('.bookmark').if(result.marked).addClass('marked')
      $('.bookmark span').text($('.bookmark').hasClass('marked') ? '북마크 해제' : '북마크')
      const update = await fetch('/book/bookmark/update?bookId=' + bookId)
        .then((res) => res.text())
        .catch(console.log)
      $('.shelves').replaceWith(update)
      break
    case false:
      alert('요청이 실패하였습니다.')
      console.log(result.error)
  }
}

function imgChange() {
  it('.review-write-attach-photo-button')
    .if(imgCount != 5)
    .toggleSelf(0)
  $('.review-write-attach-title span:last-child').text(imgCount + '/5')
}

function setThumbnail(event, type) {
  if (event.target.files.length > 0 && event.target.files.length < 6) {
    imgCount = 0
    let imgs = domAll('.review-write-photo-wrapper')
    if (imgs != null) imgs.forEach((e) => e.remove())

    for (var image of event.target.files) {
      imgCount++
      let reader = new FileReader()
      reader.onload = function (event) {
        let div = document.createElement('div')
        let img = document.createElement('input')
        div.setAttribute('class', 'review-write-photo-wrapper')
        div.innerHTML = '<div class="review-write-photo-cancel" onclick="deleteImg(this)">X</div>'
        img.setAttribute('style', 'background-image: url(' + event.target.result + ')')
        img.setAttribute('type', 'file')
        img.setAttribute('class', 'review-write-photo')
        img.setAttribute('disabled', true)
        if (type == 'mod') {
          dom('.review-modify-attach-photo').appendChild(div).appendChild(img)
        } else {
          dom('.review-write-attach-photo').appendChild(div).appendChild(img)
        }
      }
      reader.readAsDataURL(image)
      imgChange()
    }
  } else if (event.target.files.length > 5) {
    alert('이미지는 최대 5장까지 업로드 가능합니다.')
    attchReset()
  }
}

function attchReset() {
  imgCount = 0
  let imgs = domAll('.review-write-photo-wrapper')
  if (imgs != null) imgs.forEach((e) => e.remove())

  let parent = dom('.review-write-attach-photo-button')
  dom('.review-write-photo-input').remove()

  parent.innerHTML =
    '<input name="imageFiles" class="review-write-photo-input" type="file" multiple="multiple" accept=".jpg, .jpeg, .png, .gif" onchange="setThumbnail(event, this)"></input>'
}

function deleteImg(_this) {
  $(_this).parent().remove()
  imgCount--
  imgChange()
}

function formClose(_this) {
  $('.modal-background').fadeOut(100)
  $('form').hide()
  $('body').css('overflow-y', 'scroll')
  $('form').css('filter', 'brightness(100%)')
  $('.review-write-find-text').text('상품 검색')
  $('.review-book-id').val('')
  $('.review-book-rating .star-fill').stop().css('width', '0px')
  $('.review-book-rating-number .rating-number-input').val(0)
  $('.review-book-rating-number .rating-number').text(0)
  $('.review-write-tag .tag').removeClass('tag-active')
  $('.review-write-tag-input').val('')
  $('form textarea').val('')
  $('.write-number').text('0/3000')
  $('.review-write-photo-wrapper').remove()
  $('.review-write-attach-photo-button').show()
  $('.regist-button').addClass('disable')
  $('.review-title-write').css('pointer-events', 'all')
  bookPassed = false
  tagPassed = false
  reviewPassed = false
  rwOpened = false
  imgCount = 0
  starClickIndex = -1
  hideModal()
}

function registCheck(...passed) {
  it('.regist-button')
    .if(passed.every((e) => e))
    .removeClass('disable')
}

function reviewDelete() {
  const bookId = $('#book-id').data('value')
  $.ajax({
    url: '/bookReview-delete/' + selectReviewId + '/' + bookId,
    type: 'post',
    error: function (xhr, status, error) {
      console.log(error)
    }
  }).done(function (result) {
    document.location.replace('/book?id=' + bookId)
  })
}

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

function sideWrapperFix() {
  $('.side-wrapper').css('transform', 'translateY(' + $(window).scrollTop() + 'px)')
}

function reviewResize() {
  const reviews = $('.my-review .body').toArray()
  reviews.forEach((e) =>
    e.querySelector('.review').offsetHeight < e.querySelector('span').offsetHeight
      ? (e.querySelector('.fold-button').style.display = 'block')
      : (e.querySelector('.fold-button').style.display = 'none')
  )
}
