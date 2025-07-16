<?php
// Database connection parameters
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

// Get title parameter from request
$title = isset($_GET['title']) ? $_GET['title'] : '';

// Prepare response array
$response = array();

// Check if title is provided
if (!empty($title)) {
    // First, check if the book exists and its current availability status
    $checkSql = "SELECT available FROM books WHERE title = ?";
    $checkStmt = $conn->prepare($checkSql);
    $checkStmt->bind_param("s", $title);
    $checkStmt->execute();
    $checkResult = $checkStmt->get_result();

    if ($checkResult->num_rows > 0) {
        $row = $checkResult->fetch_assoc();
        if ($row['available'] == 0) { // Check if the book is currently NOT available (i.e., borrowed)
            // SQL to update book availability to 1 (available) and set return_date
            $sql = "UPDATE books SET available = 1, return_date = CURDATE() WHERE title = ?";
            $stmt = $conn->prepare($sql);
            $stmt->bind_param("s", $title);

            // Execute query
            if ($stmt->execute()) {
                $response["success"] = true;
                $response["message"] = "Book '$title' returned successfully.";
            } else {
                $response["success"] = false;
                $response["message"] = "Failed to return book: " . $stmt->error;
            }
            $stmt->close();
        } else {
            // Book is already available (status is 1), so it cannot be "returned"
            $response["success"] = false;
            $response["message"] = "Book '$title' is already available and cannot be returned.";
        }
    } else {
        // Book not found in the database
        $response["success"] = false;
        $response["message"] = "Book '$title' not found.";
    }
    $checkStmt->close();
} else {
    // Title parameter was not provided in the request
    $response["success"] = false;
    $response["message"] = "Title parameter is required.";
}

// Close connection
$conn->close();

// Return JSON response
header('Content-Type: application/json');
echo json_encode($response);
?>