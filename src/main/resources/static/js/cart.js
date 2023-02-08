$(function () {

  let width = $(window).width();

  if (width < 1000) {
    $(".bottom-nav-wrapper").stop().animate({ 'bottom': '0px' }, 500);
    $(".bottom-nav-sub-wrapper").stop().animate({ 'bottom': '50px' }, 500);
    $(".main-wrapper").stop().animate({ 'padding-right': '0' }, 500);
    $(".side-wrapper").stop().animate({ 'right': '-300px' }, 500);
    $(".other-reviews").stop().css({ 'margin-bottom': '100px' });
  } else {
    $(".bottom-nav-wrapper").stop().animate({ 'bottom': '-80px' }, 500);
    $(".bottom-nav-sub-wrapper").stop().animate({ 'bottom': '-80px' }, 500);
    $(".main-wrapper").stop().animate({ 'padding-right': '200px' }, 500);
    $(".side-wrapper").stop().animate({ 'right': '0' }, 300);
    $(".other-reviews").stop().css({ 'margin-bottom': '0' });
  }

  $(window).resize(function () {
    width = $(this).width();
    if (width < 1000) {
      $(".bottom-nav-wrapper").stop().animate({ 'bottom': '0px' }, 500);
      $(".bottom-nav-sub-wrapper").stop().animate({ 'bottom': '50px' }, 500);
      $(".main-wrapper").stop().animate({ 'padding-right': '0' }, 500);
      $(".side-wrapper").stop().animate({ 'right': '-300px' }, 500);
      $(".other-reviews").stop().css({ 'margin-bottom': '100px' });
    } else {
      $(".bottom-nav-wrapper").stop().animate({ 'bottom': '-80px' }, 500);
      $(".bottom-nav-sub-wrapper").stop().animate({ 'bottom': '-80px' }, 500);
      $(".main-wrapper").stop().animate({ 'padding-right': '200px' }, 500);
      $(".side-wrapper").stop().animate({ 'right': '0' }, 300);
      $(".other-reviews").stop().css({ 'margin-bottom': '0' });
    }
  });

  $(document).on("click", ".all-select .check", function () {
    $(".check").get().forEach(e => e.checked = $(this).prop("checked"));
  });

  $(document).on("click", ".row .check", () => {
    $(".all-select .check").prop("checked", $(".row .check").get().every(e => { return e.checked }));
  });

  $(document).on("click", ".option-box .del-btn", () => {
    removeCart(0);
  });

  $(document).on("click", ".row .del-btn", function () {
    removeCart(this);
  });

  $(document).on("keyup", ".count", function () {
    modifyCart(this, 0);
  });

  $(document).on("click", ".minus-btn", function () {
    modifyCart(this, -1);
  });

  $(document).on("click", ".plus-btn", function () {
    modifyCart(this, 1);
  });

  minusCheck();

});

function modifyCart(_this, typeCode) {
  (async () => {
    const row = $(_this).closest(".row");
    const count = row.find(".count");
    const countVal = Number(count.val()) + typeCode;
    const bookId = row.find(".check").data("id");
    const result = await fetch("/cart/modify?bookId=" + bookId + "&amount=" + countVal).then(res => res.text());

    if (result == 'ok') {
      const originPrice = row.find(".origin-price");
      const originPriceVal = Number(originPrice.data("value")) * countVal;
      const discountPrice = row.find(".discount-price");
      const discountPriceVal = Number(discountPrice.data("value")) * countVal;
      originPrice.text(originPriceVal.toLocaleString('ko-KR') + '원');
      discountPrice.text(discountPriceVal.toLocaleString('ko-KR') + '원');
      if (typeCode != 0) count.val(countVal);
      sumUpdate();
      minusCheck();
    } else if (result == 'min') {
      alert("최솟값입니다.");
      count.val(1);
    } else if (result == 'error') {
      alert("요청이 실패하였습니다.");
    } else {
      alert("재고가 부족합니다.");
      count.val(result);
    }
  })();
}

function removeCart(_this) {
  (async () => {
    const isOne = _this == 0 ? false : true;
    const data = isOne ?
      $(_this).closest(".row").find(".check").data("id")
      : $(".row input:checked").get().map(e => { return e.dataset.id });

    const success = await fetch("/cart/remove", {
      method: 'post',
      cache: "no-cache",
      body: new URLSearchParams({ bookIds: data })
    }).then(res => res.json());

    if (success) {
      isOne ?
        $(_this).parent().remove()
        : $(".row input:checked").get().forEach(e => e.parentNode.remove());
      $(".row").get().length == 0 ? location.reload() : sumUpdate();
    } else {
      alert("요청이 실패하였습니다.");
    }
  })();
}

function sumUpdate() {
  (async () => {
    const update = await fetch("/cart/sum").then(res => res.text());
    $("#sum").replaceWith(update);
  })();
}

function minusCheck() {
  $(".amount-box").get().forEach(e => {
    e.querySelector("input").value < 2 ?
      e.querySelector(".minus-btn").classList.add('disable')
      : e.querySelector(".minus-btn").classList.remove('disable');
  });
}




