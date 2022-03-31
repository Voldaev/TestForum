<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>registration page</title>

    <style type="text/css">
        .row {
            flex-wrap: nowrap!important;
        }
    </style>

</head>
<script type="text/javascript" src="/static/js/jquery.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
    integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
    integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
    crossorigin="anonymous"></script>

<body style="margin: 1em;">
    <div class="container-fluid">
        <form class="position-absolute top-50 start-50 translate-middle" data-toggle="validator">
            <div class="container">
            <div class="mb-4 row">
                <label for="name" class="col-sm-4 col-form-label">Имя</label>
                <div class="col-sm-12">
                    <input type="text" class="form-control" name="name" required="true"/>
                </div>
            </div>
            <div class="mb-4 row">
                <label for="sign" class="col-sm-4 col-form-label">Логин</label>
                <div class="col-sm-12">
                    <input type="text" class="form-control" name="sign" required="true"/>
                </div>
            </div>
            <div class="mb-4 row">
                <label for="password" class="col-sm-4 col-form-label">Пароль</label>
                <div class="col-sm-12">
                    <input type="password" class="form-control" name="password" required="true"/>
                </div>
            </div>
            <div class="mb-4 row">
                <label for="email" class="col-sm-4 col-form-label">Почта</label>
                <div class="col-sm-12">
                    <input type="text" class="form-control" name="email" required="true"/>
                </div>
            </div>
            <div class="col text-center">
                <button type="button" class="btn btn-primary" onclick="regClick()">Зарегистрироваться</button>
            </div>
        </div>
        </form>

        <script>

            function regClick() {


                /*let form = $('#reg-form');
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }*/

                // form.classList.add('was-validated')

                let formData = Object.fromEntries(new FormData($('#reg-form')[0]).entries())
                $.ajax({
                    url: '/api/registration/registration',
                    type: 'POST',
                    contentType: 'application/json',
                    dataType: 'json',
                    cache: false,
                    async: false,
                    data: JSON.stringify(formData),
                    success: function (resp) {
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
    </div>
</body>

</html>