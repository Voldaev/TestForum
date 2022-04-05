<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>main</title>
</head>
<body>
<h3 >страница только для авторизованных пользователей</h3>
<br/>
<h1 >Добро пожаловать, ${name}!</h1>
<a href="/main/profile">профиль пользователя</a>
<#if status??><h2 >${status}</h2><#else></#if>


</body>
</html>