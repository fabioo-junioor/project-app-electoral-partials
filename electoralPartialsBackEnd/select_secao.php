<?php
header('Content-Type: application/json; Charset=UTF-8');
include("connection.php");

$numZona = intval($_GET['numZona']);

$sql = "SELECT distinct(numSecao) FROM dadosUrna
        WHERE numZona = '$numZona'
        AND idCandidato <> 0      
        AND registroValido = 0
        ORDER BY numSecao";

$executa = mysqli_query($con, $sql) or die (mysqli_error());

$saida = array();
$cont = 0;

while($row = mysqli_fetch_array($executa)){
    array_push($saida, array("numSecao"=>$row['numSecao']));
    $cont++;

}
if($cont > 0){
    $saida = converteArrayParaUtf8($saida);
    echo json_encode($saida);
    

}else{
    array_push($saida, array("numSecao"=>"Sem secoes para esta zona"));
    
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