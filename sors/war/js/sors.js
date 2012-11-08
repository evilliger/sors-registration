$(function() { 
   // School select
   $('#school').change(function(){
        $("#class").attr  ('class','');
    });
    
   // Primary event select
   $('#pevent').change(function(){
        $('#pscore').attr('class','');
        show_units("#punits",$("#" + $('#pevent').val()).val());
        $("#sevent").attr  ('class','');
    });

    //Second event  select
    $('#sevent').change(function(){
        $('#sscore').attr('class','');
        show_units("#sunits",$("#" + $('#ssevent').val()).val());
    });
    // Cancel button
    $('#cancel').click(function(){
        r = confirm('Are you sure you want to cancel the editing of this record?')    
        if(r){
            window.location.href="list"
        }
        return false;
    });
});
        
// Logic for properly displayed units
function show_units(id,c){
    if (c === "F" ){
    	$(id).html(" units in feet");
    }else if (c === "M"){
        $(id).html(' units in meters');
    }else if (c === "S"){
        $(id).html(' units in seconds');
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