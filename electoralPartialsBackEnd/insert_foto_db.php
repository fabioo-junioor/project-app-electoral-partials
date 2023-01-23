<?php 
    include("connection.php");

    $imstr = $_POST['imstr'];
    $nameFile = $_POST['namefile'];
    $path = "img/";

    $saida = array();
    $saida['kode'] = '000';
    if(file_put_contents($path.$nameFile, base64_decode($imstr)) == false){
        $saida['kode'] = '111';
        echo json_encode($saida);

    }else{
        echo json_encode($saida);
         
    }
?>