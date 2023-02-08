$(function () {
    $('.cancel').click(function (){
        $('.phonePart').hide();
    })
    $('.phone').click(function(){
        $('.warring').innerText="";
    })

    function pwCheck() {
        let originValue = $('.pw').val();
        let thisValue = $('.pw-check').val();
        if (thisValue==originValue && thisValue != "") {
            $('.pw-check').parent().css('border', "1px solid rgb(101, 168, 255)")
                .addClass('passed');
        } else if (thisValue != "") {
            $('.pw-check').parent().css('border', "1px solid #ff3873")
                .removeClass('passed');
        } else {
            $('.pw-check').parent().css('border', "1px solid lightgray")
                .removeClass('passed');
        }
        join();
    }

    $(".pw").on('keyup focus',function() {
        const pwRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,14}$/;
        let value = $(this).val();
        if (pwRegex.test(value)) {
            $(this).parent().css('border', "1px solid rgb(101, 168, 255)")
                .addClass('passed');
            $('.pw-info').css('color', "rgb(101, 168, 255)");
        } else if (value != "") {
            $(this).parent().css('border', "1px solid #ff3873")
                .removeClass('passed');
            $('.pw-info').css('color', "#ff3873");
        } else {
            $(this).parent().css('border', "1px solid lightgray")
                .removeClass('passed');
            $('.pw-info').css('color', "rgb(170, 170, 170)");
        }
        pwCheck();
    });
    $(".pw-check").on('keyup focus',function() {
        pwCheck();
    });
})

function join(){
    let allFormGroup = $('.form-password').get();
    if(allFormGroup.every(e => e.classList.contains('passed'))) {
        $('.chagePassword').removeAttr('disabled');
    } else {
        $('.chagePassword').attr('disabled','true');
    }
}
function popup1(){
    $('.phonePart').show()
}
function popup2(){
    $('.passwordPart').show()
}
function popup3(){
    if(memberJoinType==="BOOKSHELF"){
        $('.emailPart').show()
    }else{
        $('.memberJoinType-warring').show()
    }
}
async function checkPhoneNumber(){
    var phone =$('.phone').val();
    console.log("checkPhoneNumber")
    console.log("phone="+phone);

    var check=await fetch("/memberinfo/phone/check/?phone="+phone).then(re=>re.text()).catch(err=>console.log(err));
    if(check=="true"){
        var num=await fetch("/memberinfo/phone/makeNum/?phone="+phone).then(re=>re.text()).catch(err=>console.log(err));
        $('.hidenNumber').val(num);
        $('.phoneNum').val();
    }else{
        $('.warring').innerHTML="이미 존재하는 번호 입니다."
    }
}

async function checkMessageNumber(){
    var phone =$('.phone').val();
    var originPhone =$('.phoneNum');

    if($('.hidenNumber').val()===$('.code').val()){
        var changeNumber=await fetch("/memberinfo/phone/?phone="+orignphoneNum+"&newPhoneNum="+phone).then(re=>re.text()).catch(err=>console.log(err));
        console.log("changeNumber="+changeNumber)

        if(changeNumber===phone){
            alert("전화번호 변경이 완료되었습니다.")
            originPhone.innerHTML=changeNumber;
            $('.phonePart').hide();
        }else{
            alert("알맞지 않는 인증번호입니다.")
        }
    }
}

async function memberEmailChange(){
    var newEmail = $('.newEmail').val()
    var check=await fetch("/memberinfo/email/?email="+newEmail).then(re=>re.text()).catch(err=>console.log(err));

    if(check=="true"){
        alert("이메일 변경이 완료되었습니다")
        $('.email').innerHTML=newEmail;
        $('.emailPart').hide();
    }else{
        alert("이미 존재하는 이메일 입니다");
    }
}
async function changeNames(){
    var name = $('.name').val()
    var nickName = $('.nickName').val()

    var check=await fetch("/memberinfo/names/?name="+name+"&nickName="+nickName).then(re=>re.text()).catch(err=>console.log(err));

    alert("변경 되었습니다.")
}
async function changeAddress(){
    var zipcode = $('.zipcode').val()
    var street = $('.street').val()
    var city = $('.city').val()

    var check=await fetch("/memberinfo/address/?zipcode="+zipcode+"&street="+street+"&city="+city).then(re=>re.text()).catch(err=>console.log(err));
    if(check=="true"){
        alert("변경 되었습니다.")
    }else{
        alert("재입력 부탁드립니다.")
    }
}
async function changePassword(){
    var password = $('.password').val();
    var check=await fetch("/memberinfo/password/?password="+password).then(re=>re.text()).catch(err=>console.log(err));
    if(check=="true"){
        alert("변경 되었습니다.")
        $('.passwordPart').hide();
    }else{
        alert("재입력 부탁드립니다.")
    }
}

async function deleteMember(){

    var check=await fetch("/memberinfo/delete").then(re=>re.text()).catch(err=>console.log(err));
    if(check=="true"){
        alert("회원탈퇴가 완료되었습니다");
        window.location.href="/logout";
    }else{
        alert("이미 없는 회원입니다")
    }
}

async function changeProfile(){
    var file=document.querySelector('.multipartFile').files[0];
    var newProfile=document.querySelector('.memberProfile');

    console.log(file);
    var formData = new FormData();
    formData.append("file",file);
    var profile=await fetch("/memberInfo/profile",{
        method:'post',
        cache: 'no-cache',
        body: formData
    }).then(re=>re.text()).catch(err=>console.log(err));
    if(profile!==null){
        alert("완료되었습니다");
        newProfile.setAttribute("style","background-image:url("+profile+"); width:300px; height:300px" );
    }
}
async function changeBasic(){
    var basicProfile=document.querySelector('.memberProfile');
    var basic=await fetch("/memberInfo/profile/basic").then(re=>re.text()).catch(err=>console.log(err));
    if(basic!==null){
        alert("완료되었습니다");
        basicProfile.setAttribute("style","background-image:url("+basic+"); width:300px; height:300px" );
    }
}