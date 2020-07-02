<?php 	

require_once 'core.php';

$valid['success'] = array('success' => false, 'messages' => array());

if($_POST) {	
  $orderId = htmlentities($_POST['orderId']);
  $orderDate = date('Y-m-d', strtotime($_POST['orderDate']));

  $clientName = htmlentities($_POST['clientName']);

  $clientContact = htmlentities($_POST['clientContact']);
  $subTotalValue = htmlentities($_POST['subTotalValue']);
  $vatValue = htmlentities($_POST['vatValue']);
  $totalAmountValue = htmlentities($_POST['totalAmountValue']);
  $discount = htmlentities($_POST['discount']);
  $grandTotalValue = htmlentities($_POST['grandTotalValue']);
  $paid = htmlentities($_POST['paid']);
  $dueValue = htmlentities($_POST['dueValue']);
  $paymentType = htmlentities($_POST['paymentType']);
  $paymentStatus = htmlentities($_POST['paymentStatus']);
  $paymentPlace = htmlentities($_POST['paymentPlace']);
  $gstn = htmlentities($_POST['gstn']);
  $userid = htmlentities($_SESSION['userId']);
				
	$sql = "UPDATE orders SET order_date = '$orderDate', client_name = '$clientName', client_contact = '$clientContact', sub_total = '$subTotalValue', vat = '$vatValue', total_amount = '$totalAmountValue', discount = '$discount', grand_total = '$grandTotalValue', paid = '$paid', due = '$dueValue', payment_type = '$paymentType', payment_status = '$paymentStatus', order_status = 1 ,user_id = '$userid',payment_place = '$paymentPlace' , gstn = '$gstn' WHERE order_id = {$orderId}";	
	mysqli_query($conn, $sql);
	
	$readyToUpdateOrderItem = false;
	// add the quantity from the order item to product table
	for($x = 0; $x < count($_POST['productName']); $x++) {		
		//  product table
		$updateProductQuantitySql = "SELECT product.quantity FROM product WHERE product.product_id = ".$_POST['productName'][$x]."";
		$updateProductQuantityData = mysqli_query($conn, $updateProductQuantitySql);	
			
		while ($updateProductQuantityResult = mysqli_fetch_row($updateProductQuantityData)) {
			// order item table add product quantity
			$orderItemTableSql = "SELECT order_item.quantity FROM order_item WHERE order_item.order_id = {$orderId}";
			$orderItemResult = mysqli_query($conn, $orderItemTableSql);	
			$orderItemData = mysqli_fetch_row($orderItemResult);

			$editQuantity = $updateProductQuantityResult[0] + $orderItemData[0];							

			$updateQuantitySql = "UPDATE product SET quantity = $editQuantity WHERE product_id = ".$_POST['productName'][$x]."";
			mysqli_query($conn, $updateQuantitySql);			
		} // while	
		
		if(count($_POST['productName']) == count($_POST['productName'])) {
			$readyToUpdateOrderItem = true;			
		}
	} // /for quantity

	// remove the order item data from order item table
	for($x = 0; $x < count($_POST['productName']); $x++) {			
		$removeOrderSql = "DELETE FROM order_item WHERE order_id = {$orderId}";
		mysqli_query($conn, $removeOrderSql);		
	} // /for quantity

	if($readyToUpdateOrderItem) {
			// insert the order item data 
		for($x = 0; $x < count($_POST['productName']); $x++) {			
			$updateProductQuantitySql = "SELECT product.quantity FROM product WHERE product.product_id = ".$_POST['productName'][$x]."";
			$updateProductQuantityData = mysqli_query($conn, $updateProductQuantitySql);	
			
			while ($updateProductQuantityResult = mysqli_fetch_row($updateProductQuantityData)) {
				$updateQuantity[$x] = $updateProductQuantityResult[0] - $_POST['quantity'][$x];							
					// update product table
					$updateProductTable = "UPDATE product SET quantity = '".$updateQuantity[$x]."' WHERE product_id = ".$_POST['productName'][$x]."";
					mysqli_query($conn, $updateProductTable);	

					// add into order_item
				$orderItemSql = "INSERT INTO order_item (order_id, product_id, quantity, rate, total, order_item_status) 
				VALUES ({$orderId}, '".$_POST['productName'][$x]."', '".$_POST['quantity'][$x]."', '".$_POST['rateValue'][$x]."', '".$_POST['totalValue'][$x]."', 1)";

				mysqli_query($conn, $orderItemSql);		
			} // while	
		} // /for quantity
	}

	

	$valid['success'] = true;
	$valid['messages'] = "Successfully Updated";		
	
	mysqli_close($conn);

	echo json_encode($valid);
 
} // /if $_POST
// echo json_encode($valid);
