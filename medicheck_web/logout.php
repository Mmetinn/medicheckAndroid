<?php

function logout() {

	// expire session 
	// redirect login page

	session_start();
	session_destroy();
	header('Location: http://localhost/medicheck/login.php');
    die;

}

logout();
