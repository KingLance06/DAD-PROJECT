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

$sql = "SELECT title FROM books";
$result = $conn->query($sql);

$titles = array();

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $titles[] = $row['title'];
    }
    echo json_encode(array("success" => true, "titles" => $titles));
} else {
    echo json_encode(array("success" => false, "message" => "No books found."));
}

$conn->close();
?>
