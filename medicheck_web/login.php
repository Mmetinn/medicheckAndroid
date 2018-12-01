<?php
session_start();
$errorMsg = "";
include "connection.php";


//print_r($_POST);die;
function logincheck($u, $p) {
    global $link;
    $res=mysqli_query($link,"select * from doktorlar where doktor_kulad='$u' and doktor_sifre='$p' ");//bize gelen password ve username göre sorgu yapıtoruz
    while ($r = mysqli_fetch_array($res)) {
        return [
            'id' => $r['id'],
            'username' => $r['username'],];//gelen veriyi return ediyoruz.
    }
}

if ( count($_POST) > 0) {
    if (isset($_POST['username']) && isset($_POST['password'])) {
        $u = $_POST['username'];
        $p = $_POST['password'];
        $user = logincheck($u, $p);// kullanıcı adı ve şifremizi bir değişkene atıp fonksiyona göderiyoruz.ve return edilen veriyi user değişkeninin içerisine atıyoruz.
        if ( $user ) {//user boş değilse.
            $_SESSION['user_id'] = $user['id'];
            $_SESSION['user_name'] = $user['username'];
            header('Location: http://localhost/medicheck/anasayfa.php');//id ile usernamei sessiona ata ve anasayfaya git
            die;
        } else {
            $errorMsg = "Kullanıcı adı veya şifre hatalı!";//user boş ise error mesajı ver.
        }
    }
}


?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="js/jquery.min.js"></script>
    <title>Student Login Form | LMS </title>
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/animate.min.css" rel="stylesheet">
    <link href="css/custom.min.css" rel="stylesheet">
    <script src="js/jquery.min.js" type="text/javascript"></script>
</head>

<br>

<div class="col-lg-12 text-center ">
    <h1 style="font-family:Lucida Console">Medicheck Management System</h1>
</div>

<br>

<body class="login">


<div class="login_wrapper">

    <section class="login_content">
        <form name="form1" action="" method="post">
            <h1>User Login Form</h1>

            <div>
                <input type="text" name="username" class="form-control" placeholder="Username" required=""/>
            </div>
            <div>
                <input type="password" name="password" class="form-control" placeholder="Password" required=""/>
            </div>
            <div>

                <input class="btn btn-default submit" type="submit" name="submit1" value="Login">
                <a class="reset_pass" href="#">Şifrenizi mi unuttunuz?</a>
            </div>

            <div class="clearfix"></div>

            <div class="separator">
                <p class="change_link">Sitede yeni misin?
                    <a href="registration.php"> Yeni hesap oluşturmak için tıkla. </a>
                </p>

                <div class="clearfix"></div>
                <br/>


            </div>
        </form>
    </section>


</div>
<?php if ( strlen($errorMsg) > 0 ) { ?>
        <div class="alert alert-danger col-lg-6 col-lg-push-3">
            <strong style="color:white">Geçersiz</strong> kullanıcı adı veya şifre.
        </div>
    <?php } ?>


</body>
</html>
