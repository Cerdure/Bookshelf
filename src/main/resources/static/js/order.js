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

  $(document).on("click", ".info-option", function () {
    (async () => {
      const type = $(this).data("type");
      switch (type) {
        case 'direct':
          $("#info input").val(''); break;
        default:
          const result = await fetch("/order/info?type=" + type).then(res => res.text());
          $("#info").replaceWith(result);
      }
    })();
  });

  $(document).on("click", ".img-box", function () {
    $(this).find("input").prop('checked', true);
    $(".select-icon").hide();
    $(".img-box").css("border", "1px solid lightgray");
    $(this).find(".select-icon").fadeIn(0);
    $(this).css("border", "1px solid cornflowerblue");
  });

  $(document).on("click", ".post-btn", () => {
    new daum.Postcode({
      oncomplete: function (data) {
        $('.zipcode').val(data.zonecode);
        $('.city').val(data.address);
      }
    }).open();
  });

  const restPointCheck = point => fetch("/order/point/rest?point=" + point).then(res => res.json());

  $(document).on("click", ".all-point", function () {
    (async () => {
      const allPoint = $(".rest").data("value");
      const priceSum = $(".origin-sum").data("value") + $(".delivery-charge").data("value");
      if (allPoint > 0) {
        const usePoint = priceSum > allPoint ? allPoint : priceSum;
        const restPoint = await restPointCheck(usePoint);
        if (restPoint < 0) {
          alert("요청이 실패하였습니다.");
        } else {
          priceCalc(priceSum, usePoint, restPoint);
        }
      }
    })();
  });

  $(document).on("keyup", ".point-value", function () {
    (async () => {
      const allPoint = $(".rest").data("value");
      const usePoint = $(this).val();
      const restPoint = await restPointCheck(usePoint);
      const priceSum = $(".origin-sum").data("value") + $(".delivery-charge").data("value");

      if (restPoint < 0) {
        alert("사용 가능한 포인트를 초과했습니다.");
        priceCalc(priceSum, allPoint, 0);
      } else {
        priceCalc(priceSum, usePoint, restPoint);
      }
    })();
  });

  $(document).on("click", ".agree-check", function () {
    $(this).prop("checked") ? $(".pay-btn").removeClass("disable") : $(".pay-btn").addClass("disable");
  });

  $(document).on("click", ".pay-btn", () => {
    (async () => {
      if (!$(".agree-check").prop("checked")) {
        alert("구매조건 및 결제에 동의해야 진행가능합니다.");
        return;
      }

      const orderInfo = await fetch("/order/create/prev").then(res => res.json());
      const name = $(".row").get().length == 1 ?
        $(".row:nth-child(2) .book").text()
        : $(".row:nth-child(2) .book").text() + ' 외 ' + ($(".row").get().length - 1) + '건';
      IMP.init('imp78314138');
      IMP.request_pay({
        pg: $(".pay-type:checked").val(),
        pay_method: "card",
        merchant_uid: orderInfo.id,
        name: name,
        amount: $(".order-price").val(),
        buyer_email: orderInfo.member.eamil,
        buyer_name: orderInfo.member.name,
        buyer_tel: orderInfo.member.phone,
        buyer_addr: orderInfo.member.address.city,
        buyer_postcode: orderInfo.member.address.zipcode
      },
        res => {
          if (res.success) {
            createOrder(orderInfo.id);
          } else {
            alert("실패 : 코드(" + res.error_code + ") / 메세지(" + res.error_msg + ")");
          }
        });
    })();
  });

});

function priceCalc(priceSum, usePoint, restPoint) {
  const orderPrice = priceSum - usePoint;
  $(".point-value").val(usePoint);
  $(".rest").attr("data-value", restPoint).text('잔여 포인트 : ' + restPoint);

  if (restPoint == 0) {
    $(".all-point").addClass("disable");
  } else {
    $(".all-point").removeClass("disable");
  }

  $(".payment-sum-box .point").text(usePoint + ' 포인트');
  $(".payment-sum-box .data").text(orderPrice);
  $(".order-price").val(orderPrice);
  orderPrice <= 0 ? $(".payment-method-box").addClass("disable") : '';
}

function createOrder(orderId) {
  (async () => {
    const orderResult = await fetch("/order/create", {
      method: 'post',
      cache: 'no-cache',
      body: new URLSearchParams({
        id: orderId,
        receiver: $(".receiver").val(),
        phone: $(".phone-1").val() + $(".phone-2").val() + $(".phone-3").val(),
        tel: $(".tel-1").val() + $(".tel-2").val() + $(".tel-3").val(),
        zipcode: $(".zipcode").val(),
        city: $(".city").val(),
        street: $(".street").val(),
        deliveryPlace: $(".where").val(),
        originSum: $(".origin-sum").data("value"),
        deliveryCharge: $(".delivery-charge").data("value"),
        point: $(".point-value").val(),
        orderPrice: $(".order-price").val(),
        payType: $(".pay-type:checked").val(),
      })
    }).then(res => res.text());

    if (orderResult) {
      const itemResult = e => fetch("/order/item", {
        method: 'post',
        cache: 'no-cache',
        body: new URLSearchParams({
          orderId: orderId,
          bookId: e.querySelector(".book").dataset.id,
          amount: e.querySelector(".amount").innerHTML
        })
      }).then(res => res.json());

      const result = await $(".row").get().map(e => itemResult(e));

      if (result.every(e => e)) {
        const getPoint = (Number($(".order-price").val()) * $(".point-rate").data("value")) / 100;
        location.href = "/order/success?orderId=" + orderId + "&point=" + getPoint;
        return;
      }
    }
    alert('요청이 실패하였습니다. (error: order)');
  })();
}



