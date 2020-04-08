<?php
error_reporting(E_ALL);
ini_set('display_errors',1);

include('dbcon.php');

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android ){
    $id = $_POST['id'];

    try{
    $stmt = $con->prepare("SELECT condiment0, condiment1 from recipe WHERE id={$id}");
    $stmt->execute();
    $row = ($stmt->fetch(PDO::FETCH_NUM));

    echo $row[0];
    echo $row[1];

        // SQL문을 실행하여 데이터를 MySQL 서버의 person 테이블에 저장합니다.
          // $stmt = $con->prepare('INSERT INTO person(name, country) VALUES(:name, :country)');
    $stmt = $con->prepare("UPDATE ino SET condiment={$row[0]} WHERE id=0");
    $stmt->execute();
    $stmt = $con->prepare("UPDATE ino SET condiment={$row[1]} WHERE id=1");
    $stmt->execute();

      } catch(PDOException $e) {
          die("Database error: " . $e->getMessage());
      }
}else echo 'fail';

?>
