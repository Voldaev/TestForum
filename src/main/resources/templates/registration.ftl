<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>registration page</title>
</head>
<script type="text/javascript" src="/static/js/jquery.js"></script>
<body>
    <div>
        <fieldset>
            <legend>Введите данные</legend>
            <form id="reg-form">
                <label>
                    Имя
                    <input name="name" type="text" required="required"/>
                </label>
                <br/><br/>
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
                <label>
                    E-mail
                    <input name="mail" type="email" required="required"/>
                </label>
                <br/><br/>
                <button type="button" onclick="regClick()">Зарегистрироваться</button>
            </form>

            <script>

                function regClick() {
                    let formData = Object.fromEntries(new FormData($('#reg-form')[0]).entries())
                    $.ajax({
                        url: '/api/registration/registration',
                        type: 'POST',
                        contentType: 'application/json',
                        dataType: 'json',
                        cache: false,
                        async: false,
                        data: JSON.stringify(formData),
                        success: function(resp) {
                            console.log(resp)
                            if (resp.success) {
                                alert('регистрация прошла успешно')
                                location.href = 'hello'
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