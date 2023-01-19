<?php
header('Content-Type: application/json; Charset=UTF-8');
include("connection.php");

$email = $_GET["email"];
$op = intval($_GET['op']);

$sql = "SELECT idUsuario FROM usuario 
        WHERE email = '$email'
        AND `admin` = '$op'";
$executa = mysqli_query($con, $sql) or die (mysqli_error());

$saida = array();
$cont = 0;

while($row = mysqli_fetch_array($executa)){
    array_push($saida, array("idUsuario"=>$row['idUsuario']));
    $cont++;

}
if($cont > 0){
    $saida = converteArrayParaUtf8($saida);
    echo json_encode($saida);
    

}else{
    $sql2 = "UPDATE usuario SET `admin` = '$op' WHERE email = '$email'";
    $executa2 = mysqli_query($con, $sql2) or die (mysqli_error());

    array_push($saida, array("idUsuario"=>"0"));

    $saida = converteArrayParaUtf8($saida);
    echo json_encode($saida);

}

function converteArrayParaUtf8($saida){
    array_walk_recursive($saida, function(&$item,$key){
         if (!mb_detect_encoding($item, 'utf-8', true)) {
                $item = utf8_encode($item);
            }
    });
    return $saida;
}

mysqli_close($con);

?>