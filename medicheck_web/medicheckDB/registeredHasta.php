<?php
	require "connection.php";
	$user_name=$_POST["user_name"];
	$password=$_POST["password"];
	$hasta_ad=$_POST["adi"];
	$hasta_soyad=$_POST["soyadi"];
	$hasta_email=$_POST["email"];
	$hasta_telefon=$_POST["telefon"];
	$hasta_tc=$_POST["tc"];	
	$mysql_qry="insert into hastalar (hasta_kulad,hasta_sifre,hasta_adi,hasta_soyadi,hasta_tc,hasta_email,hasta_telefon) values ('$user_name','$password','$hasta_ad','$hasta_soyad','$hasta_email','$hasta_telefon','$hasta_tc')";
	if($conn->query($mysql_qry)=== TRUE){
		echo "insert successful";
	}else{
		echo "Error: ".$mysql_qry."<br>".$conn->error;
	}
	$conn->close();
 ?> 