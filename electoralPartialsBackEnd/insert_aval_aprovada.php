<?php
header('Content-Type: application/json; Charset=UTF-8');
include("connection.php");

$numZona = intval($_GET["numZona"]);
$numSecao = intval($_GET["numSecao"]);
$idUser = intval($_GET["idUser"]);
$nomeCidade = $_GET["nomeCidade"];



$sql = "SELECT idDadosUrna FROM dadosurna d
        WHERE numZona = '$numZona'
        AND numSecao = '$numSecao'
        AND idUser = '$idUser'
        AND idCidade = (SELECT idMunicipio FROM municipio WHERE nome = '$nomeCidade')
        AND registroValido = 0";
$executa = mysqli_query($con, $sql) or die (mysqli_error());

$saida = array();
$cont = 0;

while($row = mysqli_fetch_array($executa)){
    //array_push($saida, array("idDadosUrna"=>$row['idDadosUrna']));
    $cont++;

}
if($cont > 0){
    $sql2 = "SELECT idDadosUrna FROM dadosurna d
            WHERE `d`.numZona = '$numZona'
            AND `d`.numSecao = '$numSecao'
            AND `d`.idUser <> '$idUser'
            AND `d`.idCidade = (SELECT idMunicipio FROM municipio m WHERE `m`.nome = '$nomeCidade')
            AND `d`.registroValido = 1";
    $executa2 = mysqli_query($con, $sql2) or die (mysqli_error());
    
    //$saida = array();
    $cont1 = 0;
    
    while($row = mysqli_fetch_array($executa2)){
        array_push($saida, array("idDadosUrna"=>$row['idDadosUrna']));
        $cont1++;
    
    }
    if($cont1 > 0){    
        $saida = converteArrayParaUtf8($saida);
        echo json_encode($saida);
        
    
    }else{
        $sql3 = "UPDATE dadosurna SET registroValido = 1 
                WHERE numZona = '$numZona'
                AND numSecao = '$numSecao'
                AND idUser = '$idUser'
                AND idCidade = (SELECT idMunicipio FROM municipio WHERE nome = '$nomeCidade')
                AND registroValido = 0";
        $executa3 = mysqli_query($con, $sql3) or die (mysqli_error());
    
        array_push($saida, array("idDadosUrna"=>"0"));
        
        $saida = converteArrayParaUtf8($saida);
        echo json_encode($saida);
    
    }

}else{
    array_push($saida, array("idDadosUrna"=>"null"));
        
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