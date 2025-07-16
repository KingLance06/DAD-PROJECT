<?php
header('Content-Type: application/json');

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "library_db";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die(json_encode(array("success" => false, "message" => "Connection failed: " . $conn->connect_error)));
}

$data = json_decode(file_get_contents("php://input"));

$title = $data->title;

$sql = "DELETE FROM books WHERE title = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $title);

if ($stmt->execute()) {
    echo json_encode(array("success" => true));
} else {
    echo json_encode(array("success" => false, "message" => "Error: " . $stmt->error));
}

$stmt->close();
$conn->close();
?>
