<?php
include "header.php";

session_start();
?>
<!-- page content area main -->
<div class="right_col" role="main">
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3>Plain Page</h3>
            </div>

            <div class="title_right">
                <div class="col-md-5 col-sm-5 col-xs-12 form-group pull-right top_search">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search for...">
                        <span class="input-group-btn">
                          <button class="btn btn-default" type="button">Go!</button>
                      </span>
                  </div>
              </div>
          </div>
      </div>

      <div class="clearfix"></div>
      <div class="row" style="min-height:500px">
        <div class="col-md-12 col-sm-12 col-xs-12">
            <div class="x_panel">
                <div class="x_title">
                    <h2>Plain Page</h2>

                    <div class="clearfix"></div>
                </div>
                <div class="x_content">
                    <?php
                    
                  //  echo "------------".$_SESSION['user_id'];
                        $sql = "select hasta_id from hasta_doktor where doktor_id = ".$_SESSION['user_id'] ;

                    /*if(isset($_POST["submit1"])){
                        $sql = $sql . " where kitap_ad like ('%$_POST[t1]%') ";
                    }*/
                            //kitap ismini tablo olarak gösterir
                        $res=mysqli_query($link, $sql);

                    echo "<table id='tablo' class='table table-bordered'>";
                    echo "<tr>";
                    echo "<th>"; echo "Kullanıcı Adı"; echo "</th>";
                    echo "<th>"; echo "Adı"; echo "</th>";
                    echo "<th>"; echo "Soyadı"; echo "</th>";
                    echo "<th>"; echo "Tc"; echo "</th>";
                    echo "<th>"; echo "Email"; echo "</th>";
                    echo "<th>"; echo "Telefon"; echo "</th>";
                    echo "</tr>";
                    while ($row=mysqli_fetch_array($res)){ 
    
                        $sql2="select * from hastalar where hasta_id = ".$row['hasta_id'];
                        $res2=mysqli_query($link, $sql2);
                        $row2=mysqli_fetch_array($res2);
                        ?>
                        <tr>
                          <!--  <td><abbr title="GÜNCELLE"><a style="color: black" href="update.php?id=<?php// echo $row['id'];  ?>" onclick="return window.confirm ('Devam etmek istediğinizden emin misini')" ><i class="fa fa-refresh fa-2x" aria-hidden="true"></a></abbr></td>
                            <td><abbr title="SİL" ><a style="color: black" href="delete.php?id=<?php// echo $row['id']; ?>" onclick="return window.confirm ('Devam etmek istediğinizden emin misini')"><i class="fa fa-trash-o fa-2x" aria-hidden="true"></a></abbr></td>-->
                                <td><abbr title="GEÇ" ><a style="color: black" href="olcumlist.php?id=<?php echo $row2['hasta_id']; ?>" ><i class="material-icons" style="font-size:48px;color:red;">favorite</i>
</a></abbr></td>
                                <td><?php echo $row2["hasta_kulad"]; ?></td>
                                <td><?php echo $row2["hasta_adi"]; ?></td>
                                <td><?php echo $row2["hasta_soyadi"]; ?></td>
                                <td> <?php echo $row2["hasta_tc"]; ?> </td>
                                <td> <?php echo $row2["hasta_email"]; ?> </td>
                                <td> <?php echo $row2["hasta_telefon"]; ?> </td>
                            </tr>
                            <?php
                        }
                        ?>

                    </table>                            
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
