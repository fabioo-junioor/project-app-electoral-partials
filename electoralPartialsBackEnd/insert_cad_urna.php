<?php
header('Content-Type: application/json; Charset=UTF-8');
include("connection.php");



/*
    if(isset($_GET["numZona"]) && isset($_GET["numSessao"])
        && isset($_GET["totalVotos"]) && isset($_GET["votosBrancos"])
        && isset($_GET["votosNulos"]) && isset($_GET["nomeCandidato"])
        && isset($_GET["emailUser"]) && isset($_GET["nomeCidade"])
        && isset($_GET["regValido"])){
        $numZona = intval($_GET["numZona"]);
        $numSessao = intval($_GET["numSessao"]);
        $totalVotos = intval($_GET["totalVotos"]);
        $votosBrancos = intval($_GET["votosBrancos"]);
        $votosNulos = intval($_GET["votosNulos"]);
        $nomeCandidato = $_GET["nomeCandidato"];
        $emailUser = $_GET["emailUser"];
        $nomeCidade = $_GET["nomeCidade"];
        $regValido = intval($_GET["regValido"]);
        */
        $nomeCandidato = "Ciro Ferreira Gomes";
        $numZona = 20;
        $numSessao = 15;
        $emailUser = "carlos@bol.com";
        $nomeCidade = "Restinga Seca";
        $totalVotos = 100;
        $votosBrancos = 100;
        $votosNulos = 77;
        $regValido = 0;

        if($nomeCandidato == "vazio"){
            $sql = "SELECT totalVotosBrancos, totalVotosNulos FROM dadosurna WHERE numSessao = '$numSessao'
                    AND idUser = (SELECT idUsuario FROM usuario WHERE email = '$emailUser')
                    AND totalVotosBrancos <> 0 AND totalVotosNulos <> 0";
            
            $executa = mysqli_query($con, $sql) or die (mysqli_error());
            
            $saida = array();
            $cont1 = 0;
            
            while($row = mysqli_fetch_array($executa)){
                array_push($saida, array("totalVotosBrancos"=>$row['totalVotosBrancos'], "totalVotosNulos"=>$row['totalVotosNulos']));
                $cont1++;
            
            }
            
            if($cont1 > 0){
                echo json_encode($saida);
                
            }
            
            if($cont1 == 0){
                $sql2 = "INSERT INTO dadosurna (numZona, numSessao, idUser, idCidade, idCandidato, totalVotosCandidato, totalVotosBrancos, totalVotosNulos, registroValido)
                    VALUES ('$numZona', '$numSessao', (SELECT idUsuario FROM usuario WHERE email = '$emailUser'),
                    (SELECT idMunicipio FROM municipio WHERE nome = '$nomeCidade'), 0, 0, $votosNulos, $votosBrancos, '$regValido')";
                    $executa2 = mysqli_query($con, $sql2) or die (mysqli_error($con));
    
                    array_push($saida, array("idDadosUrna"=>"null"));
        
                    echo json_encode($saida);

            }
        }else{
            $sql3 = "SELECT idDadosUrna FROM dadosurna WHERE numSessao = '$numSessao'
                    AND idUser = (SELECT idUsuario FROM usuario WHERE email = '$emailUser')
                    AND idCandidato = (SELECT idCandidato FROM candidatos WHERE nome = '$nomeCandidato')";
            $executa3 = mysqli_query($con, $sql3) or die (mysqli_error());
            
            $saida = array();
            $cont = 0;
            
            while($row = mysqli_fetch_array($executa3)){
                array_push($saida, array("idDadosUrna"=>$row['idDadosUrna']));
                $cont++;
            
            }
            
            if($cont > 0){
                echo json_encode($saida);
                
            
            }else{
                $sql4 = "INSERT INTO dadosurna (numZona, numSessao, idUser, idCidade, idCandidato, totalVotosCandidato, totalVotosBrancos, totalVotosNulos, registroValido)
                        VALUES ('$numZona', '$numSessao', (SELECT idUsuario FROM usuario WHERE email = '$emailUser'),
                            (SELECT idMunicipio FROM municipio WHERE nome = '$nomeCidade'),
                            (SELECT idCandidato FROM candidatos WHERE nome = '$nomeCandidato'), '$totalVotos', 0, 0, '$regValido')";
                $executa4 = mysqli_query($con, $sql4) or die (mysqli_error($con));
            
                array_push($saida, array("idDadosUrna"=>"null"));
                
                echo json_encode($saida);
            
            }

        }
/*
    }else {
        $saida = array();
        array_push($saida, array("dados"=>"error"));
            
        echo json_encode($saida);

    }*/
    mysqli_close($con);
    
?>