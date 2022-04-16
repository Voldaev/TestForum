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
                    <h2 >Добро пожаловать, ${name}!</h2>
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
                    <div class="row">
                        <h4> посетите разделы, вот их список: </h4>
                    </div>
                    <div class="row">
                        <@ui.sections rows=sections![]/>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>