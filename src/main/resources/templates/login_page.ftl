<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>login page</title>
</head>
<script type="text/javascript" src="/static/jquery.js"></script>
<body>
<div>
    <fieldset>
        <legend>Введите данные</legend>
        <form id="login-form">
            <label>
                Логин
                <input name="sign" type="text" required="required"/>
            </label>
            <br/><br/>
            <label>
                Пароль
                <input name="pass" type="password" required="required"/>
            </label>
            <br/><br/>
            <button type="button" onclick="loginClick()">Войти</button>
        </form>

        <script>

                function loginClick() {
                    let formData = Object.fromEntries(new FormData($('#login-form')[0]).entries())
                    $.ajax({
                        url: '/api/registration/login',
                        type: 'POST',
                        contentType: 'application/json',
                        dataType: 'json',
                        cache: false,
                        async: false,
                        data: JSON.stringify(formData),
                        success: function(resp) {
                            console.log(resp)
                            if (resp.success) {
                                alert('вход подтвержден')
                                location.href = 'main'
                            } else {
                                alert(resp.message)
                            }
                        }
                    });
                }

            </script>

    </fieldset>
</div>
</body>
</html>