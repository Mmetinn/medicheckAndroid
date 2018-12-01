<?php
	require "connection.php";
	$mesaj=$_POST["mesaj"];
	$mesaj_atan_id=$_POST["userId"];
	$mesaj_alan_id=$_POST["alanId"];
	$mesaj_zamani=$_POST["date"];
	$registration_id=$_POST["gcm"];	
	$mysql_qry="insert into mesajlar (mesaj,mesaj_atan_id,	mesaj_alan_id,mesaj_zamani,registration_id) values ('$mesaj','$mesaj_atan_id','$mesaj_alan_id','$mesaj_zamani','$registration_id')";
	if($conn->query($mysql_qry)=== TRUE){
		echo "insert successful";
	}else{
		echo "Error: ".$mysql_qry."<br>".$conn->error;
	}
	$conn->close();
 ?> 