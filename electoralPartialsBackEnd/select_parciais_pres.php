<?php
header('Content-Type: application/json; Charset=UTF-8');
include("connection.php");

if(isset($_GET["op"]) == "1"){
    $sql = "SELECT sum(totalVotosCandidato) AS total, (SELECT nome FROM candidatos c WHERE idCandidato = `d`.idCandidato) AS nome 
            FROM dadosurna d
            WHERE `d`.registroValido = 1 AND `d`.idCandidato <> 0
            GROUP BY `d`.idCandidato
            ORDER BY total DESC";
    
    $executa = mysqli_query($con, $sql) or die (mysqli_error());
    
    $saida = array();
    $cont = 0;
    
    while($row = mysqli_fetch_array($executa)){
        array_push($saida, array("total"=>$row['total'], "nome"=>$row['nome']));
        $cont++;
    
    }
    if($cont > 0){
        echo json_encode($saida);
        
    
    }else{
        array_push($saida, array("total"=>"0", "nome"=>"null"));
        
        echo json_encode($saida);
    
    }
}
mysqli_close($con);

?>