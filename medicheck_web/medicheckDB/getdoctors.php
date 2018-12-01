<?php
	require "connection.php";
	$mysql_qry="select * from doktorlar;";
	$result=mysqli_query($conn,$mysql_qry);
	$array = "";
	while ($row=mysqli_fetch_array($result)) {
		$array.=trim($row['id']."--".$row['doktor_adi'])." ".trim($row["doktor_soyadi"]."_");
	}
	$array.="getdoctors";
	echo trim($array);
	
 ?> 