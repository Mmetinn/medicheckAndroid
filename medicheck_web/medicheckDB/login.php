<?php
	require "connection.php";
	$user_name=$_POST["user_name"];
	$user_pass=$_POST["password"];
	$mysql_qry="select * from hastalar where hasta_kulad like '$user_name' and hasta_sifre like '$user_pass';";
	$result=mysqli_query($conn,$mysql_qry);
	if(mysqli_num_rows($result)>0){
		/*$mysql_qry2="select id from hastalar where hasta_kulad like '$user_name' and hasta_sifre like '$user_pass';";
		$result=mysqli_query($conn,$mysql_qry2);*/
		while ($row=mysqli_fetch_array($result)) {
			echo $row['hasta_id'];
		}
		
	}else{
		echo "-1";
	}
	
 ?> 