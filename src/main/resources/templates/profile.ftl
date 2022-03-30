<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>edit profile page</title>
</head>

<script type="text/javascript" src="/static/jquery.js"></script>
<body>
<img src="/main/profile/img/${useravatar}" width="100" height="100" alt="">
<br>
<a href="/main">вернуться на главную</a>
<div>
    <div role="radiogroup">
        <input type="radio" name="action" value="up" checked> Просмотр данных профиля
        <br/>
        <input type="radio" name="action" value="down"> Изменение данных профиля
        <br/>
    </div>
            <script>
                    $('input[name="action"]').on('change', function() {

                    let test = $('input[name="action"]:checked').val();

                    if (test == 'up') {
                        $(".edit").hide();
                        $(".show").show();
                    } else {
                        $(".edit").show();
                        $(".show").hide();
                    }
                    }).change();
            </script>
    <div class="show">
        <p> имя пользователя</p>
        <p>${username}</p>
        <br/>
        <p> логин пользователя</p>
        <p>${userlogin}</p>
        <br/>
        <p> email пользователя</p>
        <p>${usermail}</p>
        <br/>
    </div>
   <div class="edit" hidden>
   <fieldset>
       <legend>Изменить аватар</legend>
       <form id="ava-edit-form">
           <label>
               url новой аватарки
               <input name="url" type="text" required="required" />
           </label>
           <button type="button" onclick="editAvaClick()">Сохранить изменения</button>
       </form>
       <script>
            function editAvaClick() {
                let formData = Object.fromEntries(new FormData($('#ava-edit-form')[0]).entries())
                $.ajax({
                    url: '/main/profile/img/save',
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
                            location.href = '/main/profile'
                        } else {
                            alert(resp.message)
                        }
                    }
                });
            }
        </script>
   </fieldset>
       <fieldset>
           <legend>Изменить аватар 2</legend>
           <form>
               <label>
                   Загрузить свою картинку (перетащите в поле или выберите путь)
                   <input type="file" name="file" id="file">
                   <button onclick="doupload()" name="submit">Upload File</button>
               </label>
           </form>
           <script>
                function doupload() {
                    let data = document.getElementById("file").files[0];
                    let entry = document.getElementById("file").files[0];
                    console.log('doupload',entry,data)
                    fetch('/main/kek', {method:'POST',body:data});
                    alert('your file has been uploaded');
                    location.reload();
                };

        </script>
       </fieldset>

    <fieldset>
        <legend>Данные профиля</legend>
        <form id="edit-form">
            <label>
                имя
                <input name="name" type="text" required="required" value=${username} />
            </label>
            <br/><br/>
            <label>
                логин
                <input name="sign" type="text" required="required" value=${userlogin} />
            </label>
            <br/><br/>
            <label>
                email
                <input name="mail" type="text" required="required" value=${usermail} />
            </label>
            <br/><br/>
            <button type="button" onclick="editClick()">Сохранить изменения</button>
        </form>

        <script>

                function editClick() {
                    let formData = Object.fromEntries(new FormData($('#edit-form')[0]).entries())
                    $.ajax({
                        url: '/main/profile/edit',
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
                        url: '/main/profile/edit_pass',
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
</div>
</body>
</html>