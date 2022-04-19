<#import "macro.ftl" as ui/>

<!doctype html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <title>Main</title>

        <script type="text/javascript" src="/static/js/jquery.js"></script>
        <link href="/static/Bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet"
              crossorigin="anonymous">
        <script src="/static/Bootstrap-5.1.3-dist/js/bootstrap.bundle.min.js"
                crossorigin="anonymous"></script>

    </head>
    <body>
        <div class="container">
            <@ui.nicehat></@ui.nicehat>
            <#if status??><h4>${status}</h4><#else></#if>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-1">
                    <div class="row">
                        <img src="${useravatar}" width="100" height="100" alt="">
                    </div>
                    <div class="row">
                        <a href="/main/profile">Профиль</a>
                    </div>
                </div>
                <div class="col text-center">
                    <@ui.themes rows=theme/>
                    <div class="row">
                        <script>
                            function showSet(id){
                                let elem = document.getElementById(id);
                                let state = elem.style.display;
                                if (state ==='') elem.style.display='none';
                                else elem.style.display='';
                            }
                        </script>
                        <button class="buttonNewTheme" type="button" onclick="showSet('comm-form')" >Добавить комментарий</button>
                            <fieldset>
                                <form id="comm-form" style="display: none">
                                    <label>
                                        <input name="desc" type="text" hidden/>
                                    </label>
                                    <br/>
                                    <label>
                                        текст комментария
                                        <input name="text" type="text" required="required" />
                                    </label>
                                    <br/>
                                    <button class="buttonNewTheme" type="button" onclick="addComment()" >Опубликовать</button>
                                </form>
                                <script>
                                    function addComment() {
                                        let formData = Object.fromEntries(new FormData($('#comm-form')[0]).entries())
                                        $.ajax({
                                            url: '/main/${thistheme}/comment',
                                            type: 'POST',
                                            contentType: 'application/json',
                                            dataType: 'json',
                                            cache: false,
                                            async: false,
                                            data: JSON.stringify(formData),
                                            success: function(resp) {
                                                console.log(resp)
                                                if (resp.success) {
                                                    alert(resp.message);
                                                    location.reload();
                                                } else {
                                                    alert(resp.message);
                                                }
                                            }
                                        });
                                    }
                                </script>
                            </fieldset>
                        </div>
                    </div>
                    <@ui.comments rows=comms/>
                <script>
                    function vote(kkk) {
                        $.ajax({
                            url: kkk,
                            type: 'POST',
                            cache: false,
                            async: false,
                            success: function(resp) {
                                console.log(resp)
                                if (resp.success) {
                                    alert(resp.message);
                                    location.reload();
                                } else {
                                    alert(resp.message)
                                }

                            }
                        });
                    }
                </script>
                </div>
            </div>
    </body>
</html>