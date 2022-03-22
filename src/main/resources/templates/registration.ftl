<#import "macro.ftl" as m/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>registration page</title>
</head>
<body>
    <div>
        <fieldset>
            <legend>Введите данные</legend>
            <form name="person" action="save" method="POST">
                <@m.formInput id="t1" name="name" label="Имя     "/> <br/>
                <@m.formInput id="t2" name="sign" label="Логин   "/> <br/>
                <@m.formInput id="t3" name="pass" label="Пароль  "/> <br/>
                <@m.formInput id="t4" name="mail" label="Эл.почта"/> <br/>
                <input type="submit" value="Зарегистрироваться" />
            </form>
        </fieldset>
    </div>
</body>
</html>