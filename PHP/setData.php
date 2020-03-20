<?php
error_reporting(E_ALL);
ini_set('display_errors',1);

include('dbcon.php');

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android ){
    $title = $_POST['title'];
    $condiment0 = $_POST['condiment0'];
    $condiment1 = $_POST['condiment1'];

    try{
        // SQL문을 실행하여 데이터를 MySQL 서버의 person 테이블에 저장합니다.
          // $stmt = $con->prepare('INSERT INTO person(name, country) VALUES(:name, :country)');
          $stmt = $con->prepare("INSERT INTO recipe (title, condiment0, condiment1) VALUES('{$title}', {$condiment0}, {$condiment1})");

          if($stmt->execute()) {
              $successMSG = "성공!";
          }
          else {
              $errMSG = "레시피 추가 에러";
          }
      } catch(PDOException $e) {
          die("Database error: " . $e->getMessage());
      }
}else echo 'fail';

?>
