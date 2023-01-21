<?php
header('Content-Type: application/json; Charset=UTF-8');
include("connection.php");

$numZona = intval($_GET['numZona']);
$numSecao = intval($_GET['numSecao']);
$nomeCidade = $_GET['nomeCidade'];

$sql = "SELECT idUser, idCandidato, idCidade, numSecao, (SELECT nome FROM candidatos c WHERE `c`.idCandidato = `d`.idCandidato) AS nome, totalVotosCandidato, totalVotosBrancos, totalVotosNulos
        FROM dadosurna d
        WHERE registroValido = 0
        and idCidade = (SELECT idMunicipio FROM municipio WHERE nome = '$nomeCidade')
        and numZona = '$numZona'
        and numSecao = '$numSecao'
        ORDER BY idUser, nome DESC";

$executa = mysqli_query($con, $sql) or die (mysqli_error());

$saida = array();
$cont = 0;

while($row = mysqli_fetch_array($executa)){
    array_push($saida, array("user"=>$row['idUser'],
                            "idCidade"=>$row['idCidade'],
                            "secao"=>$row['numSecao'],
                            "idCandidato"=>$row['idCandidato'],
                            "nomeC"=>$row['nome'],
                            "votos"=>$row['totalVotosCandidato'],
                            "brancos"=>$row['totalVotosBrancos'],
                            "nulos"=>$row['totalVotosNulos']));
    $cont++;

}
if($cont > 0){
    $saida = converteArrayParaUtf8($saida);
    echo json_encode($saida);
    

}else{
    array_push($saida, array("user"=>"null",
                            "idCidade"=>"null",
                            "secao"=>"null",
                            "idCandidato"=>"null",
                            "nomeC"=>"null",
                            "votos"=>"null",
                            "brancos"=>"null",
                            "nulos"=>"null"));
    
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