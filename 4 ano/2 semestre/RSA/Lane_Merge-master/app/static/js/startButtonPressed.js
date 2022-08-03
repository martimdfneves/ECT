var count = 0;

// To count the times that the start button was pressed
function startPressed() {
    var data = "Start Pressed";
    count++; 
    $.post('/update_start_state', {"msg": data, "count": count});
}