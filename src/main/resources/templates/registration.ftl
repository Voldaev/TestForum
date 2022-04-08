<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Registration page</title>

    <script type="text/javascript" src="/static/js/jquery.js"></script>
    <link href="/static/Bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="/static/Bootstrap-5.1.3-dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>

</head>
<body>
    <div class="container-fluid">
        <form id="reg-form" class="position-absolute top-50 start-50 translate-middle">
            <div class="mb-3 text-center">
                <label for="name" class="form-label">Имя</label>
                <input type="text" class="form-control" name="name" required="true">

            </div>
            <div class="mb-3 text-center">
                <label for="sign" class="form-label">Логин</label>
                <input type="text" class="form-control" name="sign" required="true">
            </div>
            <div class="mb-3 text-center">
                <label for="pass" class="form-label">Пароль</label>
                <input type="password" class="form-control" name="pass" required="true">
            </div>
            <div class="mb-3 text-center">
                <label for="email" class="form-label">email</label>
                <input type="email" class="form-control" name="email" required="true">

            </div>
            <div class="col text-center">
                <button type="button" class="btn btn-primary" onclick="regClick()">Зарегистрироваться</button>
            </div>
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
                        alert(resp.message)
                        location.href = 'hello'
                    } else {
                        alert(resp.message)
                    }
                }
            });
        }

    </script>
</div>

</body>
</html>