<?php
error_reporting(E_ALL);
ini_set('display_errors',1);

include('dbcon.php');

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android ){
    $id = $_POST['id'];
    $title = $_POST['title'];
    $condiment0 = $_POST['condiment0'];
    $condiment1 = $_POST['condiment1'];

    try{
        // SQL문을 실행하여 데이터를 MySQL 서버의 person 테이블에 저장합니다.
          // $stmt = $con->prepare('INSERT INTO person(name, country) VALUES(:name, :country)');
    $stmt = $con->prepare("UPDATE recipe SET title='{$title}', condiment0={$condiment0}, condiment1={$condiment1} WHERE id={$id}");

        if($stmt->execute()) {
            $successMSG = "성공!";
        }
        else {
            $errMSG = "레시피 업데이트 에러";
        }
    } catch(PDOException $e) {
        die("Database error: " . $e->getMessage());
    }
}else echo 'fail';

?>
