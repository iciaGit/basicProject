/*
 * ajax 요청
 * 호출 URL, 파라메터, 타입, 결과값을 사용할 함수
*/
function requestAjax(url,param,type,f){
	var cType='application/x-www-form-urlencoded; charset=UTF-8';		
	if(type=="json"){		
		cType = 'application/json; charset=utf-8';
		param = JSON.stringify(param);
	}
	
	$.ajax({
		type:"POST",  
		url: url,   
		data:param,
		async:false,
		dataType : "json",
		contentType:cType,
		success:function(res){
			f(res);
		}, error:function(e){
			console.log(e);
		} 
	});
}


//숫자의 콤마 체크
function commaChk(num){	
	if(num == null){
		return "0";
	}else{
		if(typeof num === 'number'){
			num = String(num);
		}		
		return num.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	}	
}	


//특정 쿠키값 가져오기
function getCookie(name){
	var cookieValues = document.cookie.split(";");
	var ckMap = {};

	if(cookieValues[0] != ""){
		cookieValues.forEach(function(item){		
			var keyVal = item.split("=");
			ckMap[keyVal[0].trim()] = keyVal[1].trim();
		});
	}
	return ckMap[name];
}


//쿠키값 설정
function setCookie(key,value,day){	
	var expire = new Date();
    expire.setDate(expire.getDate() +day);//기간
    var cookieValues = key+'='+escape(value)+'; path=/ '; // 한글 깨짐을 막기위해 escape(cValue)를 합니다.
	cookieValues += ';expires=' + expire.toGMTString() + ';'; 	
	document.cookie = cookieValues;	    
}

//날짜 계산(며칠 후 또는 전, 구분자)
function getDate(num,delimiter){		
    var date = new Date();	    
    date.setDate(date.getDate() +(num));	 
    var sYear = date.getFullYear();
    var sMonth = date.getMonth() + 1;
    var sDay = date.getDate();	 
    sMonth = "" + sMonth;
    sMonth = (sMonth.length == 1) ? "0"+sMonth : sMonth
    sDay = "" + sDay;
    sDay = (sDay.length == 1) ? "0"+sDay : sDay	    
    return sYear + delimiter + sMonth + delimiter + sDay;
}
