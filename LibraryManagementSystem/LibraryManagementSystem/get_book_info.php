<?php
// Assuming your database connection setup is handled elsewhere
$dbHost = 'localhost';
$dbUsername = 'your_username';
$dbPassword = 'your_password';
$dbName = 'your_database';

// Create connection
$conn = new mysqli($dbHost, $dbUsername, $dbPassword, $dbName);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Retrieve GET data
if (isset($_GET['title'])) {
    $title = $_GET['title'];

    // Prepare SQL statement
    $sql = "SELECT id, title, author, year, available FROM books WHERE title = ?";
    
    // Use prepared statement to prevent SQL injection
    if ($stmt = $conn->prepare($sql)) {
        // Bind parameters
        $stmt->bind_param("s", $title);
        
        // Execute statement
        $stmt->execute();
        
        // Bind result variables
        $stmt->bind_result($id, $title, $author, $year, $available);
        
        // Fetch values
        if ($stmt->fetch()) {
            // Create array to hold book information
            $bookInfo = array(
                "id" => $id,
                "title" => $title,
                "author" => $author,
                "year" => $year,
                "available" => (bool)$available // Convert to boolean
            );

            // Return JSON response
            echo json_encode(array("success" => true, "book" => $bookInfo));
        } else {
            echo json_encode(array("success" => false, "message" => "Book not found."));
        }

        // Close statement
        $stmt->close();
    } else {
        echo json_encode(array("success" => false, "message" => "Failed to prepare statement."));
    }
} else {
    echo json_encode(array("success" => false, "message" => "Title parameter is missing."));
}

// Close connection
$conn->close();
?>
