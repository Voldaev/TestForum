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
                    <h2 >Добро пожаловать!</h2>
                </div>
            </div>
        </div>
        <div class="container position-absolute top-50 start-50 translate-middle">
            <div class="row">
                <div class="col text-center">
                    <div class="row">
                        <a href="/login"><h4> Пожалуйста, войдите под своей учётной записью</h4></a>
                    </div>
                    <div class="row">
                        <a href="/registration"><h4> Или зарегистрируйте новую учётную запись</h4></a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
