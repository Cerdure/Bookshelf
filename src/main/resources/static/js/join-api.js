function submitButton(){

    var name = $("input#memberName").val();
    var regExp1 = /^[가-힣]{2,10}$/;

    if (!regExp1.test(name)){
        error_txt_name.innerHTML = "한글 2글자 ~ 10글자";
        saveJoin.name.focus();
        return false;
    }
    if (saveJoin.phoneNumber.value == ""){
        error_txt_phone.innerHTML = "전화번호 입력하세요";
        saveJoin.phone.focus();
        return;
    }
    if (saveJoin.zipcode.value == "" || saveJoin.city.value == "" || saveJoin.street.value == ""){
        error_txt_address.innerHTML = "정확한 주소를 입력 하세요";
        saveJoin.zipcode.focus();
        return false;
    }

    apiJoin.submit();
}