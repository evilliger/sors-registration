$(function() { 
   // School select
   $('#school').change(function(){
        $("#class").attr  ('class','');
    });
    
   // Primary event select
   $('#pevent').change(function(){
        $('#pscore').attr('class','');
        show_units('#punits', $('#pevent').val());
        $("#sevent").attr  ('class','');
    });

    //Second event  select
    $('#sevent').change(function(){
        $('#sscore').attr('class','');
        show_units('#sunits', $('#sevent').val());
    });
    // Cancel button
    $('#cancel').click(function(){
        r = confirm('Are you sure you want to cancel the editing of this record?')    
        if(r){
            window.location.href="/athlete/list"
        }
        return false;
    });
});
        
// Logic for properly displayed units
function show_units(id,num){
    if (num == 1 ){
        $(id).html(' units in seconds');
    }else if (num == 11 || 14){
        $(id).html(' units in meters');
    }

}

$(document).ready(function() {
    $("#footer").pinFooter("relative"); 
    $(window).resize(function() {
        $("#footer").pinFooter();
    });
    if($("#content").height() < $(window).height()-200) {
      $("#content").css("height", ($(window).height()-200) + "px");
    }  
    $("#ath_table").tablesorter({sortList: [[0,0]]}); 
});