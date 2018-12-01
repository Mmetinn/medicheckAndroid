<?php
	require "connection.php";
	$mysql_qry="select * from hasta_doktor;";
	$result=mysqli_query($conn,$mysql_qry);
	$array = "";
	while ($row=mysqli_fetch_array($result)) {
		$array.=trim($row['id'])."--".trim($row['hasta_id'])."--".trim($row["doktor_id"]."_");
	}
	$array.="getdoktorhasta";
	echo trim($array);
	
 ?> 