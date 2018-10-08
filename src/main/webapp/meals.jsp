<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table>
    <c:forEach var="meal" items="${pageContext.request.getAttribute('mealsList')}">
        <tr style="${ meal.exceed ? 'color:red' : 'color:green'}">
            <td width="300">
                <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${ parsedDateTime }" />
            </td>
            <td width="100">${meal.description}</td>
            <td width="100">${meal.calories}</td>
            <td width="50"><a href="meals?action=edit&id=${meal.id}">edit</a></td>
            <td width="50"><a href="meals?action=del&id=${meal.id}">delete</a></td>
        </tr>
    </c:forEach>
</table>
<a href="meals?action=new">Add new</a>

</body>
</html>