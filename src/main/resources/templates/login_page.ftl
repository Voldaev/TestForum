<#import "macro.ftl" as m/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>login page</title>
</head>
<body>
<div>
    <fieldset>
        <legend>Введите данные</legend>
        <form name="login_pass" action="login" method="POST">
            <@m.formInput id="t1" name="sign" label="Логин   "/> <br/>
            <@m.formInput id="t2" name="pass" label="Пароль  "/> <br/>
            <input type="submit" value="Войти" />
        </form>
    </fieldset>
</div>
</body>
</html>