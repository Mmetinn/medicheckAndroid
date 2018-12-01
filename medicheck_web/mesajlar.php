 <?php
error_reporting(-1);
ini_set('display_errors', 'On');
?>
<?php
//include "header.php";
require_once __DIR__ . '/demo.php';
$demo = new Demo();
$admin_id = $demo->getDemoUser();

include "connection.php";
?>
<!-- page content area main -->
<div class="right_col" role="main">
    <div class="">
        <div class="page-title">
            <div class="title_left">
                <h3>Plain Page</h3>
            </div>
            <link href='https://fonts.googleapis.com/css?family=Raleway:400,800,100' rel='stylesheet' type='text/css'>
            <link href='style.css' rel='stylesheet' type='text/css'>
            <link href='http://api.androidhive.info/gcm/styles/default.css' rel='stylesheet' type='text/css'>
            <script type="text/javascript" src="https://code.jquery.com/jquery-2.2.0.min.js"></script>

            <script type="text/javascript">
                var user_id = '<?= $admin_id ?>';
                $(document).ready(function () {

                    getChatroomMessages($('#topics li:first').attr('id'));


                    $('ul#topics li').on('click', function () {
                        $('ul#topics li').removeClass('selected');
                        $(this).addClass('selected');
                        getChatroomMessages($(this).prop('id'))
                    });

                    function getChatroomMessages(id) {
                        $.getJSON("v1/chat_rooms/" + id, function (data) {
                            var li = '';
                            $.each(data.messages, function (i, message) {
                                li += '<li class="others"><label class="name">' + message.user.username + '</label><div class="message">' + message.message + '</div><div class="clear"></div></li>';
                            });
                            $('ul#topic_messages').html(li);
                            if (data.messages.length > 0) {
                                scrollToBottom('msg_container_topic');
                            }
                        }).done(function () {

                        }).fail(function () {
                            alert('Sorry! Unable to fetch topic messages');
                        }).always(function () {

                        });

                    // attaching the chatroom id to send button
                    $('#send_to_topic').attr('chat_room', id);                    
                }
                 $('#send_to_topic').on('click', function () {
                    var msg = $('#send_to_topic_message').val();
                    if (msg.trim().length === 0) {
                        alert('Enter a message');
                        return;
                    }

                    $('#send_to_topic_message').val('');
                    $('#loader_topic').show();

                    $.post("v1/chat_rooms/" + $(this).attr('chat_room') + '/message',
                            {user_id: user_id, message: msg},
                    function (data) {
                        if (data.error === false) {
                            var li = '<li class="self" tabindex="1"><label class="name">' + data.user.name + '</label><div class="message">' + data.message.message + '</div><div class="clear"></div></li>';
                            $('ul#topic_messages').append(li);
                            scrollToBottom('msg_container_topic');

                        } else {
                            alert('Afedersiniz! Mesaj gönderilemiyor.');
                        }
                    }).done(function () {

                    }).fail(function () {
                        alert('Mesaj Gönderilemiyor');
                    }).always(function () {
                        $('#loader_topic').hide();
                    });
                });

                function scrollToBottom(cls) {
                    $('.' + cls).scrollTop($('.' + cls + ' ul li').last().position().top + $('.' + cls + ' ul li').last().height());
                }
            });
        </script>
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
               <div class="topics">
                <div class="separator"></div>
                <h2 class="heading">Sending message to a `topic`</h2>
                Select any of the topics below and send a message.<br/><br/>
                <div class="box">
                    <div class="usr_container">
                        <ul id="topics">
                            <?php
                            $chatrooms = $demo->getAllChatRooms();
                            foreach ($chatrooms as $key => $chatroom) {
                                $sql="SELECT * FROM hastalar where hasta_id=".$chatroom['mesaj_atan_id'];
                                $res=mysqli_query($link, $sql);
                                $cls = $key == 0 ? 'selected' : '';
                                $row=mysqli_fetch_array($res);
                                ?>
                                <li id="<?= $chatroom['mesaj_atan_id'] ?>" class="<?= $cls ?>">
                                    <label><?= $row['hasta_adi']." ".$row['hasta_soyadi'] ?></label>
                                    <span>topic_<?= $row['hasta_kulad'] ?></span>
                                </li>
                                <?php

                            }
                            ?>
                        </ul>
                    </div>
                <div class="msg_container msg_container_topic">
                    <ul id="topic_messages"></ul>
                </div>
                <div class="send_container">
                    <textarea placeholder="Mesajı giriniz..." id="send_to_topic_message"></textarea>
                    <input id="send_to_topic" type="button" value="Mesajı Gönder"/>
                    <img src="loader.gif" id="loader_topic" class="loader"/>
                </div>       
                <div class="clear"></div>
            </div>
        </div>
    </div>
</div>
</div>
</div>
</div>
<!-- /page content -->
