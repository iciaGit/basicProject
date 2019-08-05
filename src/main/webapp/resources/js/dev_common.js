//ajax 요청
function requestAjax(url,param,type){
	var cType='application/x-www-form-urlencoded; charset=UTF-8';		
	if(type=="json"){		
		cType = 'application/json; charset=utf-8';
		param = JSON.stringify(param);
	}
	
	console.log("url",url);
	console.log("param",param);	
	
	$.ajax({
		type:"POST",  
		url: url,   
		data:param,
		async:false,
		dataType : "json",
		contentType:cType,
		success:function(res){
			console.log(res);
		}, error:function(e){
			console.log(e);
		} 
	});
}