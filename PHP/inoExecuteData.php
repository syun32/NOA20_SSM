
<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

include('dbcon.php');

$id = $_GET['id'];

$stmt = $con->prepare("SELECT condiment from ino WHERE id={$id}");

$stmt->execute();

if ($stmt->rowCount() > 0) {
    $data = array();
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    extract($row);

    try {
        $stmt = $con->prepare("UPDATE ino SET condiment=0 WHERE id={$id}");
        $stmt->execute();
    } catch (PDOException $e) {
        die("Database error: " . $e->getMessage());
    }

    echo "*".$row['condiment'];
}
?>