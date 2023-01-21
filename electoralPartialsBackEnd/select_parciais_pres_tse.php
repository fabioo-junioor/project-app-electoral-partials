<?php
header('Content-Type: application/json; Charset=UTF-8');
include("connection2.php");

if(isset($_GET["op"]) == "1"){
    $sql = "SELECT sum(qt_votos) AS total, NM_VOTAVEL AS nome FROM votacao_secao_tse
    GROUP BY nome
    ORDER BY total DESC
    LIMIT 3";
    
    $executa = mysqli_query($con2, $sql) or die (mysqli_error());
    
    $saida = array();
    $cont = 0;
    
    while($row = mysqli_fetch_array($executa)){
        array_push($saida, array("total"=>$row['total'], "nome"=>$row['nome']));
        $cont++;
    
    }
    if($cont > 0){
        $saida = converteArrayParaUtf8($saida);
        echo json_encode($saida);
        
    
    }else{
        array_push($saida, array("total"=>"0", "nome"=>"null"));
        
        echo json_encode($saida);
    
    }
}
function converteArrayParaUtf8($saida){
    array_walk_recursive($saida, function(&$item,$key){
         if (!mb_detect_encoding($item, 'utf-8', true)) {
                $item = utf8_encode($item);
            }
    });
    return $saida;
}
mysqli_close($con2);

?>