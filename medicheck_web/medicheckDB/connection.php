
 <?php
//$link=@mysqli_connect("localhost","root","") or die ("Sunucuya Bağlanamadık");
//$sec=@mysqli_select_db($link,"messageapp") or die ("Veritabanı Seçilemedi");
 $db_name = "medicheck";
 $mysql_username="root";
 $mysql_password="";
 $server_name="localhost";
 $conn=mysqli_connect($server_name,$mysql_username,$mysql_password,$db_name);
?>
