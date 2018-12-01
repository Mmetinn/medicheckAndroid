<?php
include "header.php";
include "connection.php";
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
  if(isset($_POST['submit'])){//kontrol  
   $registatoin_ids = array();//registration idlerimizi tutacak array ı oluşturuyoruz
   
   $sql = "SELECT *FROM wp_gcm_kullanicilar";//Tüm kullanıcı gcm registration idlerini alıcak sql sorgumuz
   $result = mysqli_query($link, $sql);//sorguyu çalıştırıyoruz
   while($row = mysqli_fetch_assoc($result)){
    array_push($registatoin_ids, $row['registration_id']);//databaseden dönen registration idleri $registatoin_ids arrayine atıyoruz
   }
   // GCM servicelerine gidecek veri
   //Arkadaşlar aşşağıdaki PHP kodlarıyla oynamıyoruz. Bu Google 'n bizden kullanmamızı istediği kodlar
   //Sadece registration_ids,mesaj ve Authorization: key değerlerini değiştiriyoruz
    $url = 'https://android.googleapis.com/gcm/send';
   
    $mesaj = array("notification_message" => $_POST['mesaj']); //gönderdiğimiz mesaj POST 'tan alıyoruz.Androidde okurken notification_message değerini kullanacağız
         $fields = array(
             'registration_ids' => $registatoin_ids,
             'data' => $mesaj,
         );
        
        //Alttaki Authorization: key= kısmına Google Apis kısmında oluşturduğumuz key'i yazacağız
    //api key yazildi
         $headers = array(
             'Authorization: key=AIzaSyAeqeURNK-qi7rpZp2r8qL8KWMr_yPKYD0',                                  
             'Content-Type: application/json'
         );
         // Open connection
         $ch = curl_init();
   
         // Set the url, number of POST vars, POST data
         curl_setopt($ch, CURLOPT_URL, $url);
   
         curl_setopt($ch, CURLOPT_POST, true);
         curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
         curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
   
         // Disabling SSL Certificate support temporarly
         curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
   
         curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
   
         // Execute post
         $result = curl_exec($ch);
         if ($result === FALSE) {
             die('Curl failed: ' . curl_error($ch));
         }
   
         // Close connection
         curl_close($ch);
         echo $result;
  }
 ?>
 
 <form method="post" action="bildirimgonder.php">
  <label>Mesajı giriniz: </label><input type="text" name="mesaj" />
 
  <input type="submit" name="submit" value="Send" />
 </form>                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /page content -->
<?php
include "footer.php";
?>
