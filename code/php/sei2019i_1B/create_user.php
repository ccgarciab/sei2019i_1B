<?php
include 'connection.php';

$id=$_POST['id'];
$name=$_POST['name'];
$password=$_POST['password'];
$sql="INSERT INTO user VALUES('$id','$name','$password')";
$result = mysqli_query($connection,$sql) or die (mysqli_error());

if($result){

    echo "";
}
else{

    echo "error";
}

mysqli_close($connection);

?>
