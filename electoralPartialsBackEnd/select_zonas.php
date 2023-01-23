<?php
header('Content-Type: application/json; Charset=UTF-8');
include("connection.php");

$nomeCidade = $_GET['nomeCidade'];
//$nomeEstado = "para";

$sql = "SELECT distinct(numZona) FROM dadosUrna WHERE idCidade = (
        SELECT idMunicipio FROM municipio WHERE nome = '$nomeCidade')
        AND registroValido = 0
        ORDER BY numZona";
$executa = mysqli_query($con, $sql) or die (mysqli_error());

$saida = array();
$cont = 0;

while($row = mysqli_fetch_array($executa)){
    array_push($saida, array("numZona"=>$row['numZona']));
    $cont++;

}
if($cont > 0){
    $saida = converteArrayParaUtf8($saida);
    echo json_encode($saida);
    

}else{
    array_push($saida, array("numZona"=>"Sem zonas para esta cidade"));
    
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