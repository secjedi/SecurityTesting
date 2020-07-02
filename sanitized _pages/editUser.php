<?php 	

require_once 'core.php';

$valid['success'] = array('success' => false, 'messages' => array());

if($_POST) {

	$edituserName = htmlentities($_POST['edituserName']);

	$editPassword = md5(htmlentities($_POST['editPassword']));
	$userid = htmlentities($_POST['userid']);
	$editBio = mysqli_real_escape_string($conn,htmlentities($_POST['editBio'])); ;
				
	$sql = "UPDATE users SET username = '$edituserName', password = '$editPassword', bio = '$editBio' WHERE user_id = $userid";

	if(mysqli_query($conn, $sql) === TRUE) {
		$valid['success'] = true;
		$valid['messages'] = "Successfully Update";	
	} else {
		$valid['success'] = false;
		$valid['messages'] = "Error while updating user info";
	}

} // /$_POST
	 
mysqli_close($conn);

echo json_encode($valid);
 
