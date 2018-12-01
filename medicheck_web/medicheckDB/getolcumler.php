<?php
	require "connection.php";

	$hasta_id=$_POST["hastaId"];

	$mysql_qry="select * from olcumler where hasta_id = ".$hasta_id.";";
	$result=mysqli_query($conn,$mysql_qry);
	$array = "";
	while ($row=mysqli_fetch_array($result)) {
		$array.=trim($row['id']."--".$row['deger'])."--".trim($row['aciklama'])."--".trim($row['hangiOlcum'])."--".trim($row['hasta_id'])."--".trim($row['tarih'])."_";
	}
	echo trim($array);
	
 ?> 