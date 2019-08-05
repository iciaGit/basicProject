<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"	uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
	<head>
		<title>Home</title>
		<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
		<style>
			table,th,td{
				border : 1px solid black;
				border-collapse: collapse;
				padding : 5px 10px;
			}
		</style>
	</head>
	<body>
		<h1>파일 업로드, 삭제, 다운로드</h1>
		<table>
			<thead>
				<tr>
					<th>파일명</th>
					<th>다운로드</th>
					<th>삭제</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${fileList}" var="list">
				<tr>
					<td>${list}</td>
					<td><a href="./fileDownload.do?fileName=${list}">다운로드</a></td>
					<td><a href="./fileDelete.do?fileName=${list}">삭제</a></td>
				</tr>			
			</c:forEach>
			</tbody>
		</table>
		<br/><br/>
		<form action="./fileUpload.do" enctype="multipart/form-data" method="post">
			업로드 할 파일 : <input type="file" name="file"/>
			<input type="submit" value="업로드"/>
		</form>

	</body>
	<script src="./resources/js/dev_common.js"></script>
	<script>
	
	</script>
</html>