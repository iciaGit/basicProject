<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
		<title>Home</title>
		<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/resources/assets/smartEditor/js/HuskyEZCreator.js" charset="utf-8"></script>		
	</head>
	<body>
		<div>		
			<form action="./write" id="writefrm" method="post">
				<div>
					<input id="text" type="text" name="subject" placeholder="제목을 입력하세요" style="width:798px;"/>
			    	<textarea name="content" id="editor" rows="10" cols="100" style="width:798px; height:412px;"></textarea>
			    </div>
		    	<div>
	    			<input id="save" type="button" value="저장" />
	    		</div>
			</form>
		</div>
	</body>
	<script>
	//사진 기능을 빼고 싶다면  SmartEditor2Skin.html 의 사진 추가 버튼 부분을 주석 처리 해 준다.
	var oEditors = [];
	var count = 0;
	//스마트에디터 프레임생성
	nhn.husky.EZCreator.createInIFrame({
		oAppRef : oEditors,
		elPlaceHolder: "editor",
		sSkinURI: "<%= request.getContextPath() %>/resources/assets/smartEditor/SmartEditor2Skin.html",
	     htParams : {				
			bUseToolbar : true,// 툴바 사용 여부        				
			bUseVerticalResizer : false,  // 입력창 크기 조절바 사용 여부			
			bUseModeChanger : true,// 모드 탭(Editor | HTML | TEXT) 사용 여부
	     }
	});	
	
	$("#save").click(function(){
		oEditors.getById["editor"].exec("UPDATE_CONTENTS_FIELD", []);
		
		if($("#text").val() == ""){
			alert("제목을 입력하세요");
		}else if($("#editor").val() == "<p>&nbsp;</p>" || $("#editor").val() == ""){
			alert("내용을 입력하세요.");
		}else{
			if($("#editor").val().length > 2000){
				alert("최대 2000자까지 입력 가능합니다.");
			}else{
				var str = $("#editor").val();
				var pattern = /src="(.*?)"/g;
				var list = str.match(pattern);
				if(list != null){
					for(var i = 0; i < list.length; i++) {
					   list[i] = list[i].substring(list[i].lastIndexOf('/')+1);
					   list[i] = list[i].substring(0, list[i].length - 1);
					}
				}
				
				var param = "";
				if(list != null){
					for(var i = 0; i < list.length; i++){
						param += "&filePath"+i+"="+list[i];
						count++;
					}
				}
				alert("첨부된 파일 수 : "+count);
				$("#writefrm").submit();
			}
		}
	});	
	</script>
</html>