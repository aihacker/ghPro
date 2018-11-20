

    function check(){
        if($('.check_box').eq(0).checked == true){
            $('#secRelevantColsOpt').val(1);
        }else{
            $('#secRelevantColsOpt').val(0);
        }

        if($('.check_box').eq(1).checked == true){
            $('#isEnable').val(1);
        }else{
            $('#isEnable').val(0);
        }


    }




