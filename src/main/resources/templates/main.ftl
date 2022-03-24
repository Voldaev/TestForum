<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>main</title>
</head>
<body>
<h1 >Добро пожаловать, ${name}!</h1>

<#if status??><h2 >${status}</h2><#else></#if>
<h3 >страница только для авторизованных пользователей</h3>
</body>
</html>