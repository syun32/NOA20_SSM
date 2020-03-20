<?php
error_reporting(E_ALL);
ini_set('display_errors', 1);

include('dbcon.php');

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if ((($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android) {
    $recipe = $_POST['recipe'];

    if ($recipe == "all") $stmt = $con->prepare('SELECT * from recipe');
    else $stmt = $con->prepare("SELECT * from recipe WHERE title='{$recipe}'");

    $stmt->execute();

    if ($stmt->rowCount() > 0) {
        $data = array();

        while ($row = ($stmt->fetch(PDO::FETCH_ASSOC))) {
            extract($row);

            array_push(
                $data,
                array(
                    'id' => $id,
                    'title' => $title,
                    'condiment0' => $condiment0,
                    'condiment1' => $condiment1
                )
            );
        }

        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("recipe" => $data), JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE);

        echo $json;
    }
}
?>