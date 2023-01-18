<?php
header('Content-Type: application/json; Charset=UTF-8');
include("connection.php");

$nome = $_GET["nome"];
$email = $_GET["email"];
$senha = $_GET["senha"];

/*
$nome = "mario";
$senha = "123459";
$email = "mario@bol.com";
*/


$sql = "SELECT `idUsuario` FROM usuario WHERE email = '$email'";
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
    $sql2 = "INSERT INTO `usuario` (nome, email, senha)
        VALUES ('$nome', '$email', '$senha')";
    $executa2 = mysqli_query($con, $sql2) or die (mysqli_error());

    array_push($saida, array("idUsuario"=>"0"));
    
    echo json_encode($saida);

}

mysqli_close($con);

?>