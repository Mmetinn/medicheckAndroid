<?php
	require "connection.php";
	$deger=$_POST["deger"];
	$aciklama=$_POST["aciklama"];
	$hangi_olcum=$_POST["hangiOlcum"];
	$hasta_id=$_POST["hastaId"];
	$tarih=$_POST["date"];

	$mysql_qry="insert into olcumler (deger,aciklama,hangiOlcum,hasta_id,tarih) values ('$deger','$aciklama','$hangi_olcum','$hasta_id','$tarih')";
	if($conn->query($mysql_qry)=== TRUE){
		echo "insert successful";
	}else{
		echo "Error: ".$mysql_qry."<br>".$conn->error;
	}
	$conn->close();
 ?> 