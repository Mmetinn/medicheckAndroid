<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Medicheck Registration Form | LMS </title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/animate.min.css" rel="stylesheet">
    <link href="css/custom.min.css" rel="stylesheet">
</head>

<br>

<div class="col-lg-12 text-center ">
    <h1 style="font-family:Lucida Console">Medicheck Yönetim Sistemi</h1>
</div>


<body class="login" style="margin-top: -20px;">



<div class="login_wrapper">

    <section class="login_content" style="margin-top: -40px;">
        <form name="form1" action="" method="post">
            <h2>Medicheck Kayıt Formu</h2><br>
            <div>
                <input type="text" class="form-control" placeholder="Adı" name="ad" required=""/>
            </div>
            <div>
                <input type="text" class="form-control" placeholder="Soyadı" name="soyad" required=""/>
            </div>

            <div>
                <input type="text" class="form-control" placeholder="KullanıcıAdı" name="kullanici_adi" required=""/>
            </div>
            <div>
                <input type="text" class="form-control" placeholder="Şifre" name="sifre" required=""/>
            </div>
            <div>
                <input type="text" class="form-control" placeholder="email" name="email" required=""/>
            </div>
            <div>
                <input type="text" class="form-control" placeholder="telefon" name="tel" required=""/>
            </div>
            <div>
                <input type="text" class="form-control" placeholder="tcniz" name="tc" required=""/>
            </div>
            <div class="col-lg-12  col-lg-push-3">
                <input class="btn btn-default submit" type="submit" name="submit1" value="Kayıt Ol">
            </div>
        </form>
    </section>
    <?php
        if(isset($_POST["submit1"])){
            $link=mysqli_connect("localhost","root","");
            mysqli_select_db($link,("medicheck"));
            mysqli_query($link,"insert into doktorlar values('','$_POST[ad]','$_POST[soyad]','$_POST[kullanici_adi]','$_POST[sifre]','$_POST[email]','$_POST[tel]','$_POST[tc]')");
        ?>
            <div class="alert alert-success col-lg-6 col-lg-push-3">
                Kayıt başarıyla tamamlandı, hesabınız onaylandığında e-posta alacaksınız
            </div>
            <?php


        }
    ?>
</div>
</body>
</html>
