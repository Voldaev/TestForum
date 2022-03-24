<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>edit profile page</title>
</head>
<script type="text/javascript" src="/static/jquery.js"></script>
<body>
<br>
<a href="/main">вернуться на главную</a>
<div>
    <fieldset>
        <legend>Данные профиля</legend>
        <form id="edit-form">
            <label>
                имя
                <input name="name" type="text" required="required" value=${username}/>
            </label>
            <br/><br/>
            <label>
                логин
                <input name="sign" type="text" required="required" value=${userlogin}/>
            </label>
            <br/><br/>
            <label>
                email
                <input name="mail" type="text" required="required" value=${usermail}/>
            </label>
            <br/><br/>
            <button type="button" onclick="editClick()">Сохранить изменения</button>
        </form>

        <script>

                function editClick() {
                    let formData = Object.fromEntries(new FormData($('#edit-form')[0]).entries())
                    $.ajax({
                        url: '/api/registration/edit',
                        type: 'POST',
                        contentType: 'application/json',
                        dataType: 'json',
                        cache: false,
                        async: false,
                        data: JSON.stringify(formData),
                        success: function(resp) {
                            console.log(resp)
                            if (resp.success) {
                                alert('данные сохранены')
                            } else {
                                alert(resp.message)
                            }
                        }
                    });
                }

            </script>

    </fieldset>
    <fieldset>
        <legend>изменение пароля</legend>
        <form id="pass-form">
            <label>
                старый пароль
                <input name="oldPass" type="text" required="required" />
            </label>
            <br/><br/>
            <label>
                новый пароль
                <input name="newPass" type="text" required="required" />
            </label>
            <br/><br/>
            <button type="button" onclick="editPassClick()">Сохранить пароль</button>
        </form>

        <script>

                function editPassClick() {
                    let formData = Object.fromEntries(new FormData($('#pass-form')[0]).entries())
                    $.ajax({
                        url: '/api/registration/edit_pass',
                        type: 'POST',
                        contentType: 'application/json',
                        dataType: 'json',
                        cache: false,
                        async: false,
                        data: JSON.stringify(formData),
                        success: function(resp) {
                            console.log(resp)
                            if (resp.success) {
                                alert('данные сохранены')
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