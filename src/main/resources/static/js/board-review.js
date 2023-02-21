$(() => {
  windowScroll()
  $(window).scroll(windowScroll)
  windowResize()
  $(window).resize(windowResize)
  reviewResize()
  $(document).on('resize', window, reviewResize)

  $('.top-back-title').show()

  let rfbClicked = false

  $('.my-review .fold-button').click(function () {
    if (!rfbClicked) {
      $(this).parent().find('.review').css({ overflow: 'visible', display: 'block' })
      $(this).find('.icon').css('transform', 'rotate(180deg)')

      rfbClicked = true
    } else {
      $(this).parent().find('.review').css({ overflow: 'hidden', display: '-webkit-box' })
      $(this).find('.icon').css('transform', 'rotate(0deg)')
      rfbClicked = false
    }
  })

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
      $(this).fadeOut(10, function () {
        $(this).css({
          position: 'fixed',
          top: '50%',
          right: 'auto',
          left: '50%',
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
    let photos = $(this).parent()
    photos.hide(100, function () {
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
      photos.fadeIn(300, function () {
        phOpened = false
      })
    })
  })

  let phCurrentIndex = 1
  $('.photos .btn-left').click(function () {
    let photos = $(this).parent()
    let maxIndex = photos.find('img').toArray().length
    if (phCurrentIndex != 1) {
      photos.fadeOut(100, function () {
        phCurrentIndex--
        photos.css({
          'background-image': 'url(' + photos.children().eq(phCurrentIndex).attr('src') + ')'
        })
        photos.find('.number').text(phCurrentIndex + ' / ' + maxIndex)
      })
      photos.fadeIn(300)
    }
  })

  $('.photos .btn-right').click(function () {
    let photos = $(this).parent()
    let maxIndex = photos.find('img').toArray().length
    if (phCurrentIndex != maxIndex) {
      photos.fadeOut(100, function () {
        phCurrentIndex++
        photos.css({
          'background-image': 'url(' + photos.children().eq(phCurrentIndex).attr('src') + ')'
        })
        photos.find('.number').text(phCurrentIndex + ' / ' + maxIndex)
      })
      photos.fadeIn(300)
    }
  })

  $(document).on('click', '.search-result .select', function () {
    let bookId = $(this).parent().find('.book-id').val()
    let bookImg = $(this).parent().find('img').attr('src')
    let bookName = $(this).parent().find('.name').text()
    $('.review-book-id').val(bookId)
    $('.review-book-img').attr('src', bookImg)
    $('.review-book-name').text(bookName)
    $('.review-write-book-wrapper').show()
    $('.review-write-search-wrapper').hide()
    swOpened = false
    $('.review-write-find').animate({ backgroundColor: 'lightgray' }, 500)
    $('.review-write-find-text').text('상품 재검색')
  })

  $(document).on('change', '#sortOrder', bookSearch)

  $(document).on('click', '.review-title-write', function () {
    flexFadeIn_back('.review-write-wrapper')
    rwOpened = true
  })

  $(document).on('mouseover', 'form .star-empty img', function () {
    let index = $(this).index()
    $(this)
      .closest('.review-book-rating')
      .find('.star-fill')
      .stop()
      .animate({ width: 34.5 * (index + 1) + 'px' }, 300)
    $(this)
      .closest('.review-book-info')
      .find('.rating-number')
      .text(index + 1)
  })

  $(document).on('click', 'form .star-empty img', function () {
    $(this)
      .closest('.review-book-info')
      .find('.rating-number-input')
      .val($(this).index() + 1)
    bookPassed = true
    registCheck()
  })

  $(document).on('mouseleave', 'form .star-empty img', function () {
    let clickidx = $(this).closest('.review-book-info').find('.rating-number-input').val()
    $(this)
      .closest('.review-book-rating')
      .find('.star-fill')
      .stop()
      .animate({ width: 34.5 * clickidx + 'px' }, 300)
    $(this).closest('.review-book-info').find('.rating-number').text(clickidx)
  })

  $(document).on('click', '.review-write-tag .tag', function () {
    it(this).activeClass('tag-active', '.review-write-tag .tag')
    $('.review-write-tag-input').val($(this).text())
    tagPassed = true
    registCheck()
  })

  $(document).on('keyup', 'form textarea', function () {
    const reviewVal = $(this).val()
    $('.write-number').text(reviewVal.length + '/3000')
    reviewPassed = reviewVal.length > 9
    registCheck()
  })

  $(document).on('click', '.review-write-top-icon', function () {
    $('form').css('filter', 'brightness(0.8)')
    const alert = dom('.alert-btn-2')
    alert.querySelector('.text').innerHTML = '작성한 내용은 저장되지 않습니다.<br>취소하겠습니까?'
    alert.querySelector('.no').addEventListener('click', () => $('form').css('filter', 'brightness(1)'))
    alert.querySelector('.ok').addEventListener('click', formClose)
    flexFadeIn('.alert-btn-2')
  })

  $(document).on('click', '.review-title-my', function () {
    document.location.replace('/review-my')
  })

  $('.review-write-find').click(function () {
    $('.review-write-search-wrapper').fadeIn(200)
    swOpened = true
  })

  $('#search-input').keyup(function () {
    bookSearch(0)
  })

  $('#search-icon').click(function () {
    bookSearch(0)
  })

  $('.search-title img').click(function () {
    $('.review-write-search-wrapper').hide()
    swOpened = false
  })

  window.onkeyup = function (e) {
    var key = e.keyCode ? e.keyCode : e.which
    if (swOpened && key == 27) {
      $('.review-write-search-wrapper').hide()
      swOpened = false
    } else if (rwOpened && key == 27) {
      $('.review-write-wrapper').css('filter', 'brightness(0.8)')
      const alert = dom('.alert-btn-2')
      alert.querySelector('.text').innerHTML = '작성한 내용은 저장되지 않습니다.<br>취소하겠습니까?'
      alert.querySelector('.no').addEventListener('click', () => {
        $('.review-write-wrapper').css('filter', 'brightness(1)')
      })
      alert.querySelector('.ok').addEventListener('click', formClose)
      flexFadeIn('.alert-btn-2')
    }
  }

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

  $('.my-review .modify').click(function () {
    let mdReviewId = $(this).closest('.my-review').find('#review-id').val()
    $.ajax({
      url: '/review/' + mdReviewId,
      type: 'get',
      dataType: 'html',
      error: function (xhr, status, error) {
        console.log(error)
      }
    }).done(function (review) {
      $('.review-modify-wrapper').replaceWith(review)
      $('.review-modify-wrapper').attr('action', '/review-modify/' + mdReviewId)
      flexFadeIn_back('.review-modify-wrapper')
      bookPassed = true
      tagPassed = true
      reviewPassed = true
    })
  })

  $('.my-review .delete').click(function () {
    selectReviewId = $(this).closest('.my-review').find('#review-id').val()
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
let swOpened = false
let rwOpened = false
let imgCount = 0
let selectReviewId

async function bookSearch(_page) {
  console.log(_page)
  const name = $('#search-input').val()
  const sortOrder = $('#sortOrder option:selected').val()
  const result = await fetch('/review/book-search?page=' + _page + '&name=' + name + '&sortOrder=' + sortOrder)
    .then((res) => res.text())
    .catch(alert)
  $('#search-results').replaceWith(result)
  $('.search-result-info .input-value').text("'" + name + "'")
  $('#sortOrder').val(sortOrder).prop('selected', true)
}

function imgChange() {
  if (imgCount == 5) {
    $('.review-write-attach-photo-button').hide()
  } else {
    $('.review-write-attach-photo-button').show()
  }
  $('.review-write-attach-title span:last-child').text(imgCount + '/5')
}

function setThumbnail(event, type) {
  if (event.target.files.length > 0 && event.target.files.length < 6) {
    imgCount = 0
    let imgs = domAll('.review-write-photo-wrapper')
    if (imgs != null) {
      imgs.forEach((e) => e.remove())
    }

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
    '<input name="imageFiles" class="review-write-photo-input" ' +
    'type="file" multiple="multiple" accept=".jpg, .jpeg, .png, .gif" ' +
    'onchange="setThumbnail(event, this)"></input>'
}

function deleteImg(_this) {
  $(_this).parent().remove()
  imgCount--
  imgChange()
}

function formClose(_this) {
  flexFadeOut_back('form')
  $('.review-write-book-wrapper').hide()
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
  bookPassed = false
  tagPassed = false
  reviewPassed = false
  rwOpened = false
  imgCount = 0
  starClickIndex = -1
  $('.review-write').css('height', '110px')
  hideModal()
}

function registCheck() {
  it('.regist-button').if(allPassed(tagPassed, reviewPassed, bookPassed)).removeClass('disable')
}

function reviewDelete() {
  $.ajax({
    url: '/review-delete/' + selectReviewId,
    type: 'post',
    error: function (xhr, status, error) {
      console.log(error)
    }
  }).done(function (result) {
    document.location.replace('/review')
  })
}

function formSubmit(_this) {
  let form = $(_this).closest('form')
  let content = form.find('[name=content]')
  content.val(content.val().replace(/\n/g, '<br>'))
  form.submit()
}

function windowScroll() {
  if ($(window).scrollTop() > 160) {
    $('.middle-nav-wrapper').css({
      position: 'fixed',
      top: '60px',
      'border-top': 'none',
      'border-bottom': '1px solid #ebebeb',
      'box-shadow': 'none'
    })
    $('.top-nav-background').css({
      'box-shadow': 'none'
    })
    $('.meta-wrapper').css('margin-top', '45px')
  } else {
    $('.middle-nav-wrapper').css({
      position: 'relative',
      top: '0px',
      'border-top': '1px solid lightgray',
      'box-shadow': '0px -3px 5px 0px rgba(0, 0, 0, 0.03)'
    })
    $('.top-nav-background').css({
      'box-shadow': '0px 3px 5px 0px rgba(0, 0, 0, 0.05)'
    })
    $('.meta-wrapper').css('margin-top', '0px')
  }
}

function windowResize() {
  let width = $('.top-back-wrapper').width()
  $('.top-background').css('background-size', width + 'px')
}

function reviewResize() {
  $('.my-review .body')
    .toArray()
    .forEach((e) =>
      e.querySelector('.review').offsetHeight < e.querySelector('span').offsetHeight
        ? (e.querySelector('.fold-button').style.display = 'block')
        : (e.querySelector('.fold-button').style.display = 'none')
    )
}
