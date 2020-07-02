<?php 	

require_once 'core.php';

$valid['success'] = array('success' => false, 'messages' => array());

if($_POST) {	

  $brandName = htmlentities($_POST['editCategoriesName']);

  $brandStatus = htmlentities($_POST['editCategoriesStatus']); 
  $categoriesId = htmlentities($_POST['editCategoriesId']);

	$sql = "UPDATE categories SET categories_name = '$brandName', categories_active = '$brandStatus' WHERE categories_id = '$categoriesId'";

	if(mysqli_query($conn, $sql) === TRUE) {
	 	$valid['success'] = true;
		$valid['messages'] = "Successfully Updated";	
	} else {
	 	$valid['success'] = false;
	 	$valid['messages'] = "Error while updating the categories";
	}
	 
	mysqli_close($conn);

	echo json_encode($valid);
 
} // /if $_POST
