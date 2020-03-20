<?php
error_reporting(E_ALL);
ini_set('display_errors',1);

include('dbcon.php');

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android ){
    $condiment0 = $_POST['condiment0'];
    $condiment1 = $_POST['condiment1'];

    try{
        // SQL문을 실행하여 데이터를 MySQL 서버의 person 테이블에 저장합니다.
          // $stmt = $con->prepare('INSERT INTO person(name, country) VALUES(:name, :country)');
    $stmt = $con->prepare("UPDATE ino SET condiment={$condiment0} WHERE id=0");
    $stmt->execute();
    $stmt = $con->prepare("UPDATE ino SET condiment={$condiment1} WHERE id=1");
    $stmt->execute();
      } catch(PDOException $e) {
          die("Database error: " . $e->getMessage());
      }
}else echo 'fail';

?>
