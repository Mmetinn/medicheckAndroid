        <?php
include 'connection.php';
error_reporting(0);
session_start();
$idim=$_SESSION['user_id'];
$sql="select * from doktorlar where id=".$idim;
$res=mysqli_query($link,$sql);
while ($row=mysqli_fetch_array($res)) {
    break;
}
?>
<!DOCTYPE html>
<html lang="en">
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>KYS</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="css/nprogress.css" rel="stylesheet">
    <link href="css/custom.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">





</head>

<body class="nav-md">
<div class="container body">
    <div class="main_container">
        <div class="col-md-3 left_col">
            <div class="left_col scroll-view">
                <div class="navbar nav_title" style="border: 0;">
                    <a href="#" class="site_title"><i class="fa fa-book"></i> <span>MEDİCHECK</span></a>
                </div>
                <div class="clearfix"></div>
                <div class="profile clearfix">
                    <div class="profile_pic">
                         <i class="fa fa-user fa-5x" aria-hidden="true"></i>
   
                    </div>
                    <div class="profile_info">
                        <span>Hoşgeldin,</span>

                        <h2><?php echo $row['doktor_adi']; ?></h2>
                    </div>
                    <div class="clearfix"></div>
                </div>
                <br/>
                <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
                    <div class="menu_section">
                        <h3>General</h3>
                        <ul class="nav side-menu">
                            <li><a href="display_user_info.php"><i class="fa fa-home"></i> ---- <span class="fa fa-chevron-down"></span></a>

                            </li>
                            <li><a href="add_users.php" ><i class="fa fa-edit"></i> --- <span class="fa fa-chevron-down"></span></a>

                            </li>
                            <li><a href="add_books.php"><i class="fa fa-desktop"></i> --- <span
                                        class="fa fa-chevron-down"></span></a>

                            </li>
                            <li><a href="kitap_alis_veris.php"><i class="fa fa-table"></i> --- <span class="fa fa-chevron-down"></span></a>

                            </li>
                            <li><a href="olcumHastaList.php"><i class="fa fa-bar-chart-o"></i> Ölçümler <span
                                        class="fa fa-chevron-down"></span></a>

                            </li>
                            </li>
                            <li><a href="bildirimgonder.php"><i class="fa fa-bar-chart-o"></i> Kullanıcılara Bildirim Gönder <span
                                        class="fa fa-chevron-down"></span></a>

                            </li>
                            <li><a href="mesajlar.php"><i class="fa fa-bar-chart-o"></i>--<span
                                        class="fa fa-chevron-down"></span></a>

                            </li>

                        </ul>
                    </div>


                </div>

            </div>
        </div>
        <div class="top_nav">
            <div class="nav_menu">
                <nav>
                    <div class="nav toggle">
                        <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                    </div>

                    <ul class="nav navbar-nav navbar-right">
                        <li class="">
                            <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown"
                               aria-expanded="false">
                                <i class="fa fa-user fa-3x" aria-hidden="true"><br></i><?php echo $row['doktor_kulad'];?>
                                <span class=" fa fa-angle-down"></span>
                            </a>
                            <ul class="dropdown-menu dropdown-usermenu pull-right">
                            <strong>
                                <?php echo "ad:".$row['doktor_adi']; ?><br>
                                <?php echo "soyad:".$row['doktor_soyadi']; ?><br>
                                <?php echo "email:".$row['doktor_email']; ?><br>
                                <?php echo "telefon:".$row['doktor_tel']; ?><br>
                            </strong>                                
                                <li><a href="logout.php"><i class="fa fa-sign-out pull-right"></i> Çıkış Yap</a></li>  
                            </ul>
                        </li>

                        <li role="presentation" class="dropdown">
                            <a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown"
                               aria-expanded="false">
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>