$(function(){
	$('#username').focus().blur(checkName);
	$('#password').blur(checkPassword);
});

function checkName(){
	var name = $('#username').val();
	if(name == null || name == ""){
		//鎻愮ず閿欒
		$('#count-msg').html("鐢ㄦ埛鍚嶄笉鑳戒负绌�);
		return false;
	}
	/*var reg = /^\w{3,10}$/;
	if(!reg.test(name)){
		$('#count-msg').html("杈撳叆3-10涓瓧姣嶆垨鏁板瓧鎴栦笅鍒掔嚎");
		return false;
	}*/
	$('#count-msg').empty();
	return true;
}

function checkPassword(){
	var password = $('#password').val();
	if(password == null || password == ""){
		//鎻愮ず閿欒
		$('#password-msg').html("瀵嗙爜涓嶈兘涓虹┖");
		return false;
	}
	/*var reg = /^\w{3,10}$/;
	if(!reg.test(password)){
		$('#password-msg').html("杈撳叆3-10涓瓧姣嶆垨鏁板瓧鎴栦笅鍒掔嚎");
		return false;
	}*/
	$('#password-msg').empty();
	return true;
}