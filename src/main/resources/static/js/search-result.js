$(() => {
  $('.category').click(() => bookSearch())
  $('.publish-date').click(() => bookSearch())

  $('.search-input .reset').click((e) => {
    const it = $(e.target)
    it.parent().find('input').val('')
    $('.search-result-category').remove()
    $('.search-result-book').remove()
    $('#search-form').submit()
  })

  $('.reset-btn').click(() => {
    $('.side-wrapper input').prop('checked', false)
    $('.publish-date-wrapper .default').prop('checked', true)
    bookSearch()
  })

  $(document).on('change', '#sortOrderSelect', (e) => {
    $('#sortOrder').val($(e.target).val())
    bookSearch()
  })
  $(document).on('click', '.search-controller .controller-btn-left', () => {
    let page = Number($('.search-controller .index-active').text()) - 1
    bookSearch(page)
  })
  $(document).on('click', '.search-controller .controller-btn-right', () => {
    let page = Number($('.search-controller .index-active').text()) + 1
    bookSearch(page)
  })
  $(document).on('click', '.search-controller #idx', (e) => {
    let page = $(e.target).text()
    bookSearch(page)
  })
  $(document).on('keyup ready', '.search-input input', (e) => {
    $('#search-input-hidden').val($(e.target).val())
    if (e.keyCode == 13) {
      $('#search-form').submit()
    } else {
      inputBookSearch()
    }
  })
  $(document).on('click', '.search-input .icon', () => {
    bookSearch(0)
  })
})

function inputBookSearch() {
  $.ajax({
    url: '/search-result-input',
    type: 'get',
    data: $('#search-form').serialize(),
    dataType: 'html',
    async: true
  }).done(function (data) {
    $('#search-input-results').replaceWith(data)
  })
}

function bookSearch(_page) {
  const $chk = $('category')
  let formData = $('.meta-wrapper').serializeArray()
  if (!$chk.is(':checked')) {
    formData.push({
      name: $chk.attr('name'),
      value: false
    })
  }
  $.ajax({
    url: '/search-result/book-search?page=' + _page,
    type: 'get',
    data: formData,
    dataType: 'html',
    async: true,
    error: function (xhr, status, error) {
      console.log(error)
    }
  }).done(function (data) {
    $('#search-results').replaceWith(data)
  })
}
