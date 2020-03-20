<?php
error_reporting(E_ALL);
ini_set('display_errors',1);

include('dbcon.php');

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android ){
    $id = $_POST['id'];

    try{
    $stmt = $con->prepare("DELETE FROM recipe WHERE id={$id}");

          if($stmt->execute()) {
              $successMSG = "성공!";
          }
          else {
              $errMSG = "레코드 삭제 에러";
          }
      } catch(PDOException $e) {
          die("Database error: " . $e->getMessage());
      }
}

?>
