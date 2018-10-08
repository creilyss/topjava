<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>${meal.id != 0 ? "Edit" : "Add"} meal</h2>

<form method="post" action="meals">
    Meal time <input name="mealTime" type="datetime-local" value="${meal.dateTime}"><br>
    Description <input name="description" type="text" value="${ meal.description }"><br>
    Calories <input name="calories" type="number" value="${ meal.calories }"><br>
    <input name="id" type="hidden" value="${ meal.id }">
    <input type="submit" value="${meal.id != 0 ? "update" : "add"}">
</form>
<a href="meals">back</a>

</body>
</html>