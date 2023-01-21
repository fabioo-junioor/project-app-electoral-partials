<?php
header('Content-Type: application/json; Charset=UTF-8');
include("connection.php");

$numZona = intval($_GET["numZona"]);
$numSecao = intval($_GET["numSecao"]);
$idUser = intval($_GET["idUser"]);
$nomeCidade = $_GET["nomeCidade"];

//  arrumar consulta
$sql = "SELECT idDadosUrna FROM dadosurna d
        WHERE `d`.numZona = '$numZona'
        AND `d`.numSecao = '$numSecao'
        AND `d`.idUser <> '$idUser'
        AND `d`.idCidade = (SELECT idMunicipio FROM municipio m WHERE `m`.nome = '$nomeCidade')
        AND `d`.registroValido = 1";
$executa = mysqli_query($con, $sql) or die (mysqli_error());

$saida = array();
$cont = 0;

while($row = mysqli_fetch_array($executa)){
    array_push($saida, array("idDadosUrna"=>$row['idDadosUrna']));
    $cont++;

}
if($cont > 0){    
    $saida = converteArrayParaUtf8($saida);
    echo json_encode($saida);
    

}else{
    $sql2 = "UPDATE dadosurna SET registroValido = 1 
            WHERE numZona = '$numZona'
            AND numSecao = '$numSecao'
            AND idUser = '$idUser'
            AND idCidade = (SELECT idMunicipio FROM municipio WHERE nome = '$nomeCidade')
            AND registroValido = 0";
    $executa2 = mysqli_query($con, $sql2) or die (mysqli_error());

    array_push($saida, array("idDadosUrna"=>"0"));
    
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