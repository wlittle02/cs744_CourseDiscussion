<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Instructor List</title>
</head>
<body>
<center>
<br><br><br><br><br><br>
<div style="color: teal;font-size: 30px">Instructor List</div>
<br><br>
<c:if test="${!empty users}">
<table border="1" bgcolor="black" width="600px">
<tr style="background-color: teal;color: white;text-align: center;" height="40px">
<td>Id</td>
<td>First Name</td>
<td>Last Name</td>
<td>Username</td>
<td>Email</td>
</tr>
<c:forEach items="${users}" var="instructor">
<tr style="background-color:white;color: black;text-align: center;" height="30px" >
<td><c:out value="${instructor.id}"/></td>
<td><c:out value="${instructor.firstName}"/></td>
<td><c:out value="${instructor.lastName}"/></td>
<td><c:out value="${instructor.username}"/></td>
<td><c:out value="${instructor.email}"/></td>
</tr>
</c:forEach>
</table>
</c:if>
<br>
<a href="<c:url value="/home"/>">Click Here to add new User</a>
</center>
</body>
</html>