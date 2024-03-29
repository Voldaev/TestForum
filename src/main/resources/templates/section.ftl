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
            <div class="row">
                <div class="col text-center">
                    <h2 >Добро пожаловать в раздел ${sectionname}!</h2>
                    <#if status??><h4>${status}</h4><#else></#if>
                </div>
            </div>
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
                    <nav aria-label="Page navigation example">
                        <ul class="pagination justify-content-center">
                            <#if (page>1)><li class="page-item"><a class="page-link" href="/main/${sectionname}/${page-1}">Предыдущая страница</a></li><#else></#if>
                            <li class="page-item"><a class="page-link" href="/main/${sectionname}/${page+1}">Следующая страница</a></li>
                        </ul>
                    </nav>
                    <div class="row">

                        <button id="button1" type="button" onclick="showSet('theme-fld')"  >Добавить новую тему</button>
                        <script>
                                function showSet(id){
                                let elem = document.getElementById(id);
                                let state = elem.style.display;
                                if (state ==='') elem.style.display='none';
                                else elem.style.display='';
                            }
                        </script>
                        <div id="theme-fld" style="display: none">
                            <fieldset>
                                <legend>Новая тема</legend>
                                <form id="theme-form">
                                    <label>
                                        Название темы
                                        <input name="desc" type="text" required="required" />
                                    </label>
                                    <br/><br/>
                                    <label>
                                        Содержание
                                        <input name="text" type="text" required="required" />
                                    </label>
                                    <br/><br/>
                                    <button type="button" onclick="addTheme()">Опубликовать</button>
                                </form>
                                <script>
                                    function addTheme() {
                                        let formData = Object.fromEntries(new FormData($('#theme-form')[0]).entries())
                                        $.ajax({
                                            url: '/main/${sectionname}/create',
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
                    <@ui.themes rows=content![]/>
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
                    <nav aria-label="Page navigation example">
                        <ul class="pagination justify-content-center">
                            <#if (page>1)><li class="page-item"><a class="page-link" href="/main/${sectionname}/${page-1}">Предыдущая страница</a></li><#else></#if>
                            <li class="page-item"><a class="page-link" href="/main/${sectionname}/${page+1}">Следующая страница</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </body>
</html>