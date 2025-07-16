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

$sql = "SELECT * FROM users";
$result = $conn->query($sql);

$users = array();

if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $user = array(
            "id" => $row['id'],
            "username" => $row['username'],
            "role" => $row['role']
            // Add more fields as needed
        );
        $users[] = $user;
    }
    echo json_encode(array("success" => true, "users" => $users));
} else {
    echo json_encode(array("success" => false, "message" => "No users found."));
}

$conn->close();
?>
