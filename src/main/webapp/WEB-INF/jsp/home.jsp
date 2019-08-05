<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
		<title>Home</title>
		<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	</head>
	<body>
		<h1>Hello world! </h1>
		<button onclick="memberList()">리스트 불러오기</button>
		<button onclick="insert()">복잡한 값 넣기</button>		
		<p><a href="fileHandlerPage.go">파일 제어 페이지 이동</a></p>
		<form action="./insertVO.do" method="post">
		<p>이름 : <input type="text" name="sm_name" value=""/></p>
		<p>폰 : <input type="text" name="sm_phone" value=""/></p>
		<p>생년 : <input type="text" name="sm_year" value=""/></p>
		<p>메모 : <input type="text" name="sm_memo" value=""/></p>
		<input type="submit" value="VO 형태로 전송"/>
		</form>
		
		
	</body>
	<script src="./resources/js/dev_common.js"></script>
	<script>
		function memberList(){			
			requestAjax('./list.ajax',{},'');
		}
		
		function insert(){
			var arr=[1,2,3,4,5];
			var obj = {};
			obj.name="숫자놀이";
			obj.num = arr;
			var param={"values":obj}
			requestAjax('./ajaxInsert.ajax',param,'json');
		}
		
		
	</script>
</html>
