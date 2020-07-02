<?php 	

require_once 'core.php';

$valid['success'] = array('success' => false, 'messages' => array());

if($_POST) {
	$productId = $_POST['productId'];

	$productName = htmlentities($_POST['editProductName']);
	$rate = htmlentities($_POST['editRate']);

	$brandName = htmlentities($_POST['editBrandName']);
  	$categoryName = htmlentities($_POST['editCategoryName']);
 
  	$quantity = htmlentities($_POST['editQuantity']);
  	$productStatus = htmlentities($_POST['editProductStatus']);

				
	$sql = "UPDATE product SET product_name = '$productName', brand_id = '$brandName', categories_id = '$categoryName', quantity = '$quantity', rate = '$rate', active = '$productStatus', status = 1 WHERE product_id = $productId ";

	if(mysqli_query($conn, $sql) === TRUE) {
		$valid['success'] = true;
		$valid['messages'] = "Successfully Update";	
	} else {
		$valid['success'] = false;
		$valid['messages'] = "Error while updating product info";
	}

} // /$_POST
	 
mysqli_close($conn);

echo json_encode($valid);
 
