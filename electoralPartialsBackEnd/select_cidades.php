<?php
header('Content-Type: application/json; Charset=UTF-8');
include("connection.php");

$nomeEstado = $_GET['nomeEstado'];
//$nomeEstado = "para";

$sql = "SELECT * FROM municipio WHERE idEstado = (
    SELECT id FROM estado WHERE nome = '$nomeEstado'
)";
$executa = mysqli_query($con, $sql) or die (mysqli_error());
$saida = array();

while($row = mysqli_fetch_array($executa)){
    array_push($saida, array("idEstado"=>$row['idEstado'], "nome"=>$row['nome'], ));

}

$saida = converteArrayParaUtf8($saida);

//echo json_encode($saida, JSON_UNESCAPED_SLASHES);
echo json_encode($saida);

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