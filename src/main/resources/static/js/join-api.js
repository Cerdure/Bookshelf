$(function (){
    var nameValue = $("input#memberName").val();
    var regExp1 = /^[가-힣]{2,10}$/;
    $(".memberName").on('keyup focus',function() {
        if (regExp1.test(nameValue)){
            error_txt_name.innerHTML = null;
        }
    });
    $(".phoneNumber").on('keyup focus',function() {
        if (phoneNumber.value !== ""){
            error_txt_phone.innerHTML = null;
        }
    });

    $(".memberAddress").on('keyup focus',function() {
        var zipcode=document.querySelector('.zipcode');
        var city=document.querySelector('.city');
        var street=document.querySelector('.street');
        if (zipcode.value !== "" && city.value !== "" && street.value !== ""){
            error_txt_address.innerHTML = null;
        }
    });



})
async function submitButton(){

    var nameValue = $("input#memberName").val();
    var regExp1 = /^[가-힣]{2,10}$/;

    var formdata = new FormData();
    var email=$('#email').val();
    var memberName=$('#memberName');
    var phoneNumber=$('#phoneNumber');

    if (!regExp1.test(nameValue)){
        error_txt_name.innerHTML = "한글 2글자 ~ 10글자";
        memberName.focus();
        return false;
    }
    if (phoneNumber.value == ""){
        error_txt_phone.innerHTML = "전화번호 입력하세요";
        phoneNumber.focus();
        return false;
    }
    var zipcode=document.querySelector('.zipcode');
    var city=document.querySelector('.city');
    var street=document.querySelector('.street');
    if (zipcode.value== "" || city.value == "" || street.value == ""){
        error_txt_address.innerHTML = "정확한 주소를 입력 하세요";
        zipcode.focus();
        return false;
    }



    formdata.append('email',email);
    formdata.append('memberName',memberName.val());
    formdata.append('phoneNumber',phoneNumber.val());
    formdata.append('zipcode',zipcode.value);
    formdata.append('city',city.value);
    formdata.append('street',street.value);


    var check= await fetch('/login/apiJoin',{
        method: 'post',
        cache: 'no-cache',
        body:formdata
    }).then(re=>re.text()).catch(err=>console.log(err));
    console.log(check)
    if(check=="true"){
        alert('회원가입이 완료되었습니다.');
        window.location.href="/";
    }else{
        alert('이미 가입한 회원입니다.');
        window.location.href="/login";
    }
}