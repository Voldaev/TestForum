<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>edit profile page</title>
</head>

<script type="text/javascript" src="/static/jquery.js"></script>
<body>
<img src="${useravatar}" width="100" height="100" alt="">
<br>
<a href="/main">вернуться на главную</a>
<br>
<div>
    <button type="button" onclick="clickclick()">редактировать профиль</button>
    <script>
        function clickclick() {
            document.getElementById("edit-view").click();
        }
    </script>
        <input type="checkbox" id="edit-view" onchange="doreverse()" hidden>
            <script>
                function doreverse() {
                        var isChecked = document.getElementById("edit-view").checked;
                        if (isChecked) {
                            $(".edit").show();
                            $(".show").hide();
                        } else {
                            $(".edit").hide();
                            $(".show").show();
                        }
                }
            </script>
    <div class="show" >
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
<#--   <fieldset>-->
<#--       <legend>Изменить аватар</legend>-->
<#--       <form id="ava-edit-form">-->
<#--           <label>-->
<#--               url новой аватарки-->
<#--               <input name="url" type="text" required="required" />-->
<#--           </label>-->
<#--           <button type="button" onclick="editAvaClick()">Сохранить изменения</button>-->
<#--       </form>-->
<#--       <script>-->
<#--            function editAvaClick() {-->
<#--                let formData = Object.fromEntries(new FormData($('#ava-edit-form')[0]).entries())-->
<#--                $.ajax({-->
<#--                    url: '/main/profile/img/save',-->
<#--                    type: 'POST',-->
<#--                    contentType: 'application/json',-->
<#--                    dataType: 'json',-->
<#--                    cache: false,-->
<#--                    async: false,-->
<#--                    data: JSON.stringify(formData),-->
<#--                    success: function(resp) {-->
<#--                        console.log(resp)-->
<#--                        if (resp.success) {-->
<#--                            alert('данные сохранены')-->
<#--                            location.href = '/main/profile'-->
<#--                        } else {-->
<#--                            alert(resp.message)-->
<#--                        }-->
<#--                    }-->
<#--                });-->
<#--            }-->
<#--        </script>-->
<#--   </fieldset>-->
       <fieldset>
           <legend>Изменить аватар</legend>
           <form>
               <label>
                   <input type="file" name="file" id="file_input">
                   <button type="button" onclick="doupload()">Загрузить файл</button>
               </label>
           </form>
           <script>
                function doupload() {
                    var formData = new FormData();
                    var filesLength = document.getElementById('file_input').files.length;
                    for (var i = 0; i < filesLength; i++) {
                        formData.append("file", document.getElementById('file_input').files[i]);
                    }
                    $.ajax({
                        url: '/main/profile/avatar',
                        type: 'POST',
                        data: formData,
                        contentType: false,
                        cache: false,
                        processData: false,
                        success: function(resp) {
                        console.log(resp)
                        if (resp.success) {
                            alert(resp.message)
                            location.reload();
                        } else {
                            alert(resp.message)
                        }
                    }
                    });
                }
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
                                alert(resp.message)
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
                                alert(resp.message)
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