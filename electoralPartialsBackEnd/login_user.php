<?php
header('Content-Type: application/json; Charset=UTF-8');
include("connection.php");


$email = $_GET["email"];
$senha = $_GET["senha"];


/*
$senha = "12345";
$email = "fabio@bol.com";
*/

$sql = "SELECT `idUsuario` FROM usuario WHERE email = '$email'
    AND senha = '$senha'";
$executa = mysqli_query($con, $sql) or die (mysqli_error());

$saida = array();
$cont = 0;

while($row = mysqli_fetch_array($executa)){
    array_push($saida, array("idUsuario"=>$row['idUsuario']));
    $cont++;

}
if($cont > 0){
    echo json_encode($saida);
    

}else{
    array_push($saida, array("idUsuario"=>"0"));
    
    echo json_encode($saida);

}

mysqli_close($con);

?>