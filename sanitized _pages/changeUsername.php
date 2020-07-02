<?php 

require_once 'core.php';

if($_POST) {

	$valid['success'] = array('success' => false, 'messages' => array());

	$username = htmlentities($_POST['username']);
	$userId = htmlentities($_POST['user_id']);
s
	$sql = "UPDATE users SET username = '$username' WHERE user_id = {$userId}";
	if(mysqli_query($conn, $sql) === TRUE) {
		$valid['success'] = true;
		$valid['messages'] = "Successfully Update";	
	} else {
		$valid['success'] = false;
		$valid['messages'] = "Error while updating user info";
	}

	mysqli_close($conn);

	echo json_encode($valid);

}

?>

