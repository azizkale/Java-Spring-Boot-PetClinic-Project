<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>my title</title>
</head>
<body>
<!--
    -thymeleaf eklendigi zaman .jsp sayfalarina gerek kalmaz, onlar otomatik iptal olur.
    -Thymeleaf ve freeMarker template enginleri kullanildiginda cache busting otomatik devreye girer.
Ama JSP veya diger template engine ler kullanildiginda cache-busting icin manuel ayar yapilmalidir.

bunun icin ResourceUrlEncodingFilter in devreye alinmasi lazim. bunun icin de @Configuretion sinifi
altinda FilterRegistrationBean Bean i tanimlanmalidir
-->
	<table>
		<thead>
			<tr style="font-weight: bold;" bgcolor="lightblue">
				<td>ID</td>
				<td>First Name</td>
				<td>Last Name</td>
			</tr>
		</thead>
		<c:forEach items="${owners}" var="owner" varStatus="status">
			<tr bgcolor=${status.index % 2 == 0?'white':'lightgray'}>
				<td>${owner.id}</td>
				<td>${owner.firstName}</td>
				<td>${owner.lastName}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>