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
            <div class="row">
                <div class="col text-center ">
                    <h1> КРАСИВАЯ ШАПКА С НАЗВАНИЕМ </h1>
                </div>
            </div>
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
                    <@ui.themes rows=content![]/>
                    <nav aria-label="Page navigation example">
                        <ul class="pagination justify-content-center">
                            <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                            <li class="page-item"><a class="page-link" href="/main/${sectionname}/1">1</a></li>
                            <li class="page-item"><a class="page-link" href="/main/${sectionname}/2">2</a></li>
                            <li class="page-item"><a class="page-link" href="/main/${sectionname}/3">3</a></li>
                            <li class="page-item"><a class="page-link" href="#">Next</a></li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </body>
</html>