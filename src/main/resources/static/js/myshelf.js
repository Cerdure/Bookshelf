
$(function () {
  let st = $(window).scrollTop();

  if (st > 220) {
    $(".middle-nav-wrapper").css({
      'position': 'fixed',
      'top': '60px',
      'border-top': 'none',
      'border-bottom': '1px solid #ebebeb',
      'box-shadow': 'none'
    });
    $(".top-nav-background").css({
      'box-shadow': 'none'
    });
    $(".record-top").css('margin-top', '45px');
    $(".order-list-title-wrapper").css('margin-top', '45px');
  } else {
    $(".middle-nav-wrapper").css({
      'position': 'relative',
      'top': '0px',
      'border-top': '1px solid lightgray',
      'box-shadow': '0px -3px 5px 0px rgba(0, 0, 0, 0.05)'
    });
    $(".top-nav-background").css({
      'box-shadow': '0px 3px 5px 0px rgba(0, 0, 0, 0.1)'
    });
    $(".record-top").css('margin-top', '0px');
    $(".order-list-title-wrapper").css('margin-top', '0px');
  }
  if (st > 224) {
    $(".order-list-title-wrapper").css({
      'position': 'fixed',
      'top': '60px',
      'border-bottom': '1px solid #ebebeb',
      'height': '60px',
      'box-shadow': '0px 3px 5px 0px rgba(0,0,0,0.1)'
    });
    $(".order-list-title-inner-wrapper").css({
      'height': '60px'
    });
    $(".order-list-wrapper").css(
      'margin-top', '111px'
    )
  } else {
    $(".order-list-title-wrapper").css({
      'position': 'relative',
      'top': '0',
      'border-bottom': 'none',
      'height': '70px',
      'box-shadow': 'none'
    });
    $(".order-list-title-inner-wrapper").css({
      'height': '70px'
    });
    $(".order-list-wrapper").css(
      'margin-top', '0px'
    )
  }

  $(window)
    .scroll(function () {
      st = $(this).scrollTop();
      if (st > 220) {
        $(".middle-nav-wrapper").css({
          'position': 'fixed',
          'top': '60px',
          'border-top': 'none',
          'border-bottom': '1px solid #ebebeb',
          'box-shadow': 'none'
        });
        $(".top-nav-background").css({
          'box-shadow': 'none'
        });
        $(".record-top").css('margin-top', '45px');
        $(".order-list-title-wrapper").css('margin-top', '45px');

      } else {
        $(".middle-nav-wrapper").css({
          'position': 'relative',
          'top': '0px',
          'border-top': '1px solid lightgray',
          'box-shadow': '0px -3px 5px 0px rgba(0, 0, 0, 0.05)'
        });
        $(".top-nav-background").css({
          'box-shadow': '0px 3px 5px 0px rgba(0, 0, 0, 0.1)'
        });
        $(".record-top").css('margin-top', '0px');
        $(".order-list-title-wrapper").css('margin-top', '0px');

      }
      if (st > 224) {
        $(".order-list-title-wrapper").css({
          'position': 'fixed',
          'top': '60px',
          'border-bottom': '1px solid #ebebeb',
          'height': '60px',
          'box-shadow': '0px 3px 5px 0px rgba(0,0,0,0.1)'
        });
        $(".order-list-title-inner-wrapper").css({
          'height': '60px'
        });
        $(".order-list-wrapper").css(
          'margin-top', '111px'
        )
      } else {
        $(".order-list-title-wrapper").css({
          'position': 'relative',
          'top': '0',
          'border-bottom': 'none',
          'height': '70px',
          'box-shadow': 'none'
        });
        $(".order-list-title-inner-wrapper").css({
          'height': '70px'
        });
        $(".order-list-wrapper").css(
          'margin-top', '0px'
        )
      }
    });

  let width = $(".top-back-wrapper").width();
  $(".top-background").css('background-size', width + 'px');


  $(window).resize(function () {
    let width = $(".top-back-wrapper").width();
    $(".top-background").css('background-size', width + 'px');
  });


  $(".middle-nav div").click(function () {
    $(".content-box").hide().css('opacity', '0');
    flexFadeIn(".content-box:nth-child(" + ($(this).index() + 1) + ")");
    $(".middle-nav div").removeClass("active-index");
    $(this).addClass("active-index");
  });
  
  $(document).on("click", ".coupon-box", e => {
    $(".modal-background").fadeIn(100);
    flexFadeIn(".coupon-modal");
  });

  $(document).on("click", ".coupon-modal .top img, .modal-background", e => {
    flexFadeOut(".coupon-modal");
    $(".modal-background").fadeOut(100);
  });

  $(".order-list-title-button").click(() => {
    detailSearchOpen();
  });

  $(document).on("click", ".search-info-box", detailSearchOpen);

  $(".detail-title img").click(function () {
    detailSearchHide();
  });

  $(".detail-bottom-apply").click(function () {
    (async () => {
      const result = await reqOrderData();
      $("#order-box").replaceWith(result);
      $(".order-list-title-info").hide();
      detailSearchHide();
    })();
  });

  $("#detail-range-select").change(function () {
    let drsIndex = $("#detail-range-select :selected").index();
    switch (drsIndex) {
      case 0:
        $(".detail-range-start, .detail-range-end").addClass("detail-range-disable");
        $(".detail-range-start").datepicker('setDate', '-1m');
        break;
      case 1:
        $(".detail-range-start, .detail-range-end").addClass("detail-range-disable");
        $(".detail-range-start").datepicker('setDate', '-2m');
        break;
      case 2:
        $(".detail-range-start, .detail-range-end").addClass("detail-range-disable");
        $(".detail-range-start").datepicker('setDate', '-3m');
        break;
      case 3:
        $(".detail-range-start, .detail-range-end").addClass("detail-range-disable");
        $(".detail-range-start").datepicker('setDate', '-6m');
        break;
      case 4:
        $(".detail-range-start, .detail-range-end").removeClass("detail-range-disable");
        $(".detail-range-start").datepicker('setDate', 'today');
        break;
    }
  });

  $(".detail-range-start").datepicker({
    dateFormat: 'yy-mm-dd'
    , showOtherMonths: true
    , showMonthAfterYear: true
    , changeYear: true
    , changeMonth: true
    , showOn: "both"
    , buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif"
    , buttonImageOnly: true
    , buttonText: "선택"
    , yearSuffix: "년"
    , monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
    , monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
    , dayNamesMin: ['일', '월', '화', '수', '목', '금', '토']
    , dayNames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일']
    , minDate: "-5Y"
    , maxDate: "0D"
  }).datepicker('setDate', '-1m');

  $(".detail-range-end").datepicker({
    dateFormat: 'yy-mm-dd'
    , showOtherMonths: true
    , showMonthAfterYear: true
    , changeYear: true
    , changeMonth: true
    , showOn: "both"
    , buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif"
    , buttonImageOnly: true
    , buttonText: "선택"
    , yearSuffix: "년"
    , monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
    , monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월']
    , dayNamesMin: ['일', '월', '화', '수', '목', '금', '토']
    , dayNames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일']
    , minDate: "-5Y"
    , maxDate: "0D"
  }).datepicker('setDate', 'today');

  $('.detail-range-start').datepicker();
  $('.detail-range-start').datepicker("option", "maxDate", $(".detail-range-end").val());
  $('.detail-range-start').datepicker("option", "onClose", selectedDate =>
    $(".detail-range-end").datepicker("option", "minDate", selectedDate));

  $('.detail-range-end').datepicker();
  $('.detail-range-end').datepicker("option", "minDate", $(".detail-range-start").val());
  $('.detail-range-end').datepicker("option", "onClose", selectedDate => 
    $(".detail-range-start").datepicker("option", "maxDate", selectedDate));


  $(".detail-bottom-reset").click(function () {
    (async () => {
      const result = await fetch("/myshelf/order/reset").then(res => res.text());
      $(".order-list-title-info").show();
      detailSearchHide();
      $("#order-box").replaceWith(result);
      $("#detail-range-select").val("month-1").prop("selected", true);
      $(".detail-range-start, .detail-range-end").css({
        'background-color': '#f5f5f5',
        'pointer-events': 'none',
        'cursor': ''
      });
      $(".detail-range-start").datepicker('setDate', '-1m');
      $("#detail-state-select").val("ALL").prop("selected", true);
      $(".order-list-detail-search input").val("");
    })();
  });

  $(document).on("click", ".next-btn", function () {
    (async () => {
      const result = await reqMoreOrderData(Number($("#page").val()) + 1);
      let div = document.createElement("add-page");
      div.innerHTML = result;
      dom(".order-list-contents-wrapper").appendChild(div);
      const isLast = $("add-page:last-child #page-box").data("last");
      if (isLast) $(this).hide();
    })();
  });

  $(document).on("click", ".order-cancel-btn", function () {
    selectRow = $(this).closest(".row");
    const price = selectRow.data("price");
    const items = selectRow.find(".item").get();
    const itemsCount = Number(items.length);
    const alert = dom(".alert-btn-2");
    alert.querySelector(".text").innerHTML =
      "주문을 취소하시겠습니까?";
    alert.querySelector(".data").innerHTML =
      "주문번호 : " + selectRow.find(".order-id").text() + "<br>" +
      "상품명 : " + items[0].querySelector(".name").innerHTML + (itemsCount > 1 ? " 외 " + (itemsCount - 1) + "건<br>" : "<br>") +
      "환불 예상 금액 : " + price.toLocaleString("ko-KR") + "원 (포인트 포함)";
    alert.querySelector(".ok").addEventListener("click", orderCancel);
    alert.querySelector(".no").addEventListener("click", () => {
      $(".modal-background").fadeOut(100);
      flexFadeOut(".alert-btn-2");
    });
    $(".modal-background").fadeIn(100);
    flexFadeIn(".alert-btn-2");
  });

  $(document).on("click", ".item-cancel-btn", function () {
    selectRow = $(this).closest(".row");
    selectItem = $(this).closest(".item");
    const price = selectItem.find(".price").text();
    const alert = dom(".alert-btn-2");
    alert.querySelector(".text").innerHTML =
      "주문을 취소하시겠습니까?";
    alert.querySelector(".data").innerHTML =
      "상품명 : " + selectItem.find(".name").text() + "<br>" +
      "환불 예상 금액 : " + price + " (포인트 포함)";
    alert.querySelector(".ok").addEventListener("click", itemCancel);
    alert.querySelector(".no").addEventListener("click", () => {
      $(".modal-background").fadeOut(100);
      flexFadeOut(".alert-btn-2");
    });
    $(".modal-background").fadeIn(100);
    flexFadeIn(".alert-btn-2");
  });

});

let selectRow, selectItem;

function detailSearchOpen() {
  $(".modal-background").fadeIn(100);
  $(".order-list-title-detail-wrapper").fadeIn(200);
  $("body").css('overflow-y', 'hidden');
}

function detailSearchHide() {
  $(".modal-background").hide();
  $(".order-list-title-detail-wrapper").hide();
  $("body").css('overflow-y', 'scroll');
}

const reqOrderData = () => {
  $("#page").val(1);
  return fetch("/myshelf/order", {
    method: 'post',
    cache: 'no-cache',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: $(".order-list-title-detail-wrapper").serialize()
  }).then(res => res.text());
}

const reqMoreOrderData = page => {
  $("#page").val(page);
  return fetch("/myshelf/order/add", {
    method: 'post',
    cache: 'no-cache',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: $(".order-list-title-detail-wrapper").serialize()
  }).then(res => res.text());
}

function orderCancel() {
  (async () => {
    const orderId = selectRow.find(".order-id").text();
    const items = selectRow.find(".item").get();
    const success = await fetch("/myshelf/order/cancel?orderId=" + orderId).then(res => res.json());
    if (success) {
      selectRow.find(".order-cancel-btn")
        .text("취소된 주문")
        .addClass("disable btn-gray")
        .removeClass("order-cancel-btn")
        .removeClass("btn-red");
      items.forEach(item => {
        item.querySelector(".state").innerHTML = "주문취소";
        const itemCancelBtn = item.querySelector(".item-cancel-btn");
        itemCancelBtn.innerHTML = "취소된 주문";
        itemCancelBtn.classList.add("disable");
        itemCancelBtn.classList.add("btn-gray");
        itemCancelBtn.classList.remove("item-cancel-btn");
        itemCancelBtn.classList.remove("btn-red");
      });
      alert("주문이 정상적으로 취소되었습니다.");
    } else {
      alert("요청이 실패하였습니다.");
    }
    $(".modal-background").fadeOut(100);
    flexFadeOut(".alert-btn-2");
  })();
}

function itemCancel() {
  (async () => {
    const orderId = selectRow.find(".order-id").text();
    const orderItemId = selectItem.data("id");
    const success =
      await fetch("/myshelf/order/item/cancel?orderId=" + orderId + "&orderItemId=" + orderItemId)
        .then(res => res.json());
    if (success) {
      selectItem.find(".state")
        .text("주문취소");
      selectItem.find(".item-cancel-btn")
        .text("취소된 주문")
        .addClass("disable btn-gray")
        .removeClass("item-cancel-btn")
        .removeClass("btn-red");
      const orderedItems = selectRow.find(".item-cancel-btn").get();
      if (orderedItems.length == 0) {
        selectRow.find(".order-cancel-btn")
          .text("취소된 주문")
          .addClass("disable btn-gray")
          .removeClass("order-cancel-btn")
          .removeClass("btn-red");
      }
      alert("주문이 정상적으로 취소되었습니다.");
    } else {
      alert("요청이 실패하였습니다.");
    }
    $(".modal-background").fadeOut(100);
    flexFadeOut(".alert-btn-2");
  })();
}