<?php 	

require_once 'core.php';

$valid['success'] = array('success' => false, 'messages' => array());

if($_POST) {	

  $brandName = htmlentities($_POST['editBrandName']);
  $brandStatus = htmlentities($_POST['editBrandStatus']); 
  $brandId = htmlentities($_POST['brandId']);

	$sql = "UPDATE brands SET brand_name = '$brandName', brand_active = '$brandStatus' WHERE brand_id = '$brandId'";

	if(mysqli_query($conn, $sql) === TRUE) {
	 	$valid['success'] = true;
		$valid['messages'] = "Successfully Updated";	
	} else {
	 	$valid['success'] = false;
	 	$valid['messages'] = "Error while adding the members";
	}
	 
	mysqli_close($conn);

	echo json_encode($valid);
 
} // /if $_POST
