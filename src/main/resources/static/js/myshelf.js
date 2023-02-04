
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
    $(".content-box").hide();
    $(".content-box:nth-child(" + ($(this).index() + 1) + ")").fadeIn(300);
    $(".middle-nav div").removeClass("active-index");
    $(this).addClass("active-index");
  });


  $(".record-sort span").click(function () {
    let recordSortIndex = $(this).index();
    $(".record-sort-button").animate({ 'left': (5 + 65 * recordSortIndex) + 'px' }, 300);
    $(".record-sort span").animate({ 'color': '#b3b3b3' }, 100);
    $(this).animate({ 'color': '#6495ed' }, 300);
  });



  const graphValue = [];
  let labelLength;
  for (var i = 0; i < 3; i++) {
    graphValue[i] = $(".record-graph-data-label-" + (i + 1)).text();
    labelLength = graphValue[i].length;
    graphValue[i] = Number(graphValue[i].substring(0, labelLength - 1));
  }
  let maxgraphValue = Math.max(graphValue[0], graphValue[1], graphValue[2]);

  for (var i = 1; i < 4; i++) {
    $(".record-graph-stick-wrapper-" + i).css('height', (240 * graphValue[i - 1] / maxgraphValue) + 'px');
    $(".record-graph-stick-" + i).css('height', ((240 * graphValue[i - 1] / maxgraphValue) - 30) + 'px');
  }


  $(".order-list-title-button").click(function () {
    $(".modal-background").fadeIn(100);
    $(".order-list-title-detail-wrapper").fadeIn(200);
    $("body").css('overflow-y', 'hidden');
  });

  $(".detail-title img").click(function () {
    detailHide();
  });

  $(".detail-bottom-apply").click(function () {
    (async () => {
      const result = await fetch("/myshelf/order", {
        method: 'post',
        cache: 'no-cache',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: $(".order-list-title-detail-wrapper").serialize()
      }).then(res => res.text());
      $("#order-box").replaceWith(result);
      detailHide();
    })();
  });

  function detailHide() {
    $(".modal-background").hide();
    $(".order-list-title-detail-wrapper").hide();
    $("body").css('overflow-y', 'scroll');
  }

  $("#detail-range-select").change(function () {
    let drsIndex = $("#detail-range-select :selected").index();
    switch (drsIndex) {
      case 0:
        $(".detail-range-start, .detail-range-end").css({
          'background-color': '#f5f5f5',
          'pointer-events': 'none',
          'cursor': ''
        });
        $(".detail-range-start").datepicker('setDate', '-1m');
        break;
      case 1:
        $(".detail-range-start, .detail-range-end").css({
          'background-color': '#f5f5f5',
          'pointer-events': 'none',
          'cursor': ''
        });
        $(".detail-range-start").datepicker('setDate', '-2m');
        break;
      case 2:
        $(".detail-range-start, .detail-range-end").css({
          'background-color': '#f5f5f5',
          'pointer-events': 'none',
          'cursor': ''
        });
        $(".detail-range-start").datepicker('setDate', '-3m');
        break;
      case 3:
        $(".detail-range-start, .detail-range-end").css({
          'background-color': '#f5f5f5',
          'pointer-events': 'none',
          'cursor': ''
        });
        $(".detail-range-start").datepicker('setDate', '-6m');
        break;
      case 4:
        $(".detail-range-start, .detail-range-end").css({
          'background-color': 'white',
          'pointer-events': 'all',
          'cursor': 'pointer'
        });
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
  $('.detail-range-start').datepicker("option", "onClose", function (selectedDate) {
    $(".detail-range-end").datepicker("option", "minDate", selectedDate);
  });

  $('.detail-range-end').datepicker();
  $('.detail-range-end').datepicker("option", "minDate", $(".detail-range-start").val());
  $('.detail-range-end').datepicker("option", "onClose", function (selectedDate) {
    $(".detail-range-start").datepicker("option", "maxDate", selectedDate);
  });


  $(".detail-bottom-reset").click(function () {
    $("#detail-range-select").val("month-1").prop("selected", true);
    $(".detail-range-start, .detail-range-end").css({
      'background-color': '#f5f5f5',
      'pointer-events': 'none',
      'cursor': ''
    });
    $(".detail-range-start").datepicker('setDate', '-1m');
    $("#detail-state-select").val("ALL").prop("selected", true);
    $(".order-list-detail-search input").val("");
  });


  $(document).on("click", "")

});


function orderPageMove(page) {
  (async () => {
    const result = await fetch("/myshelf/order?page=" + page).then(res => res.text());
    $("#order-box").replaceWith(result);
  })();
}    