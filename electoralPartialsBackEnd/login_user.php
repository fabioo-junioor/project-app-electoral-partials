<?php
header('Content-Type: application/json; Charset=UTF-8');
include("connection.php");


$email = $_GET["email"];
$senha = $_GET["senha"];


$sql = "SELECT idUsuario, `admin` FROM usuario WHERE email = '$email'
    AND senha = '$senha'";
$executa = mysqli_query($con, $sql) or die (mysqli_error());

$saida = array();
$cont = 0;

while($row = mysqli_fetch_array($executa)){
    array_push($saida, array("idUsuario"=>$row['idUsuario'], "admin"=>$row['admin']));
    $cont++;

}
if($cont > 0){
    echo json_encode($saida);
    

}else{
    array_push($saida, array("idUsuario"=>"0", "admin"=>"0"));
    
    echo json_encode($saida);

}

mysqli_close($con);

?>