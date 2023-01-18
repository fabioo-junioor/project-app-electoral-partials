<?php
header('Content-Type: application/json; Charset=UTF-8');
include("connection.php");


    if(isset($_GET["nomeCand"]) && isset($_GET["siglaCand"]) && isset($_GET["numCand"])){
    
        $nomeCand = $_GET["nomeCand"];
        $siglaCand = $_GET["siglaCand"];
        $numCand = intval($_GET["numCand"]);
    
    /*
        $nomeCand = "bolsonaro";
        $siglaCand = "pl";
        $numCand = 22;
        */
        
        $sql = "SELECT idCandidato FROM candidatos WHERE numero = '$numCand'";
        $executa = mysqli_query($con, $sql) or die (mysqli_error());
        
        $saida = array();
        $cont = 0;
        
        while($row = mysqli_fetch_array($executa)){
            array_push($saida, array("idCandidato"=>$row['idCandidato']));
            $cont++;
        
        }
        
        if($cont > 0){
            echo json_encode($saida);
            
        
        }else{
            $sql2 = "INSERT INTO candidatos (nome, numero, categoria, sigla)
                    VALUES ('$nomeCand', '$numCand', 1, '$siglaCand')";
            $executa2 = mysqli_query($con, $sql2) or die (mysqli_error($con));
        
            array_push($saida, array("idCandidato"=>"null"));
            
            echo json_encode($saida);
        
        }

    }else {
        $saida = array();
        array_push($saida, array("idCandidato"=>"error"));
            
        echo json_encode($saida);

    }
    mysqli_close($con);
    
?>