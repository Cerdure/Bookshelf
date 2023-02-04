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

});

function addressSearch() {
    $('.street').focus();
    new daum.Postcode({
        oncomplete: function (data) {
            $('.zipcode').val(data.zonecode);
            $('.city').val(data.address);
        }
    }).open();
}


function submitButton() {

    var name = $("input#memberName").val();
    var regExp1 = /^[가-힣]{2,10}$/;

    if (!regExp1.test(name)) {
        error_txt_name.innerHTML = "한글 2글자 ~ 10글자";
        saveJoin.name.focus();
        return false;
    }
    if (saveJoin.phoneNumber.value == "") {
        error_txt_phone.innerHTML = "전화번호 입력하세요";
        saveJoin.phone.focus();
        return;
    }
    if (saveJoin.zipcode.value == "" || saveJoin.city.value == "" || saveJoin.street.value == "") {
        error_txt_address.innerHTML = "정확한 주소를 입력 하세요";
        saveJoin.zipcode.focus();
        return false;
    }

    apiJoin.submit();
}