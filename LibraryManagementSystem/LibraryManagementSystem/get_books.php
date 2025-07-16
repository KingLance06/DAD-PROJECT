<?php
// Assuming you have a connection to your database
// Replace with your database connection details
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "library_db";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Query to fetch all books
$sql = "SELECT * FROM books";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $books = array();
    while($row = $result->fetch_assoc()) {
        $books[] = array(
            'id' => $row['id'],
            'title' => $row['title'],
            'author' => $row['author'],
            'year' => $row['year'],
            'available' => (bool)$row['available'] // Assuming 'available' is stored as boolean in the database
        );
    }
    echo json_encode(array('success' => true, 'books' => $books));
} else {
    echo json_encode(array('success' => false, 'message' => 'No books found.'));
}

$conn->close();
?>
