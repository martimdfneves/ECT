function setEventListeners( canvas ){
    // From learningwebgl.com
    canvas.onmousedown = handleMouseDown;
    document.onmouseup = handleMouseUp;
    document.onmousemove = handleMouseMove;

    // From learningwebgl.com
    function handleKeyDown(event) {
        currentlyPressedKeys[event.keyCode] = true;
    }

    function handleKeyUp(event) {
        currentlyPressedKeys[event.keyCode] = false;
        blocked[event.keyCode] = false;
    }

    document.onkeydown = handleKeyDown;
    document.onkeyup = handleKeyUp;

    // Dropdown lists
    var projection = document.getElementById("projection-selection");

    projection.addEventListener("click", function(){
        var p = projection.selectedIndex;

        switch(p){
            case 0 : projectionType = 0;
                break;
            case 1 : projectionType = 1;
                break;
        }
    });

    var piece1Selection = document.getElementById("piece1-selection");

    piece1Selection.addEventListener("click", function(){
        // Getting the selection
        var p = piece1Selection.selectedIndex;
        board.changeTeamPieces(true, p);
        initBuffersDraughts();
    });

    var piece2Selection = document.getElementById("piece2-selection");

    piece2Selection.addEventListener("click", function(){
        // Getting the selection
        var p = piece2Selection.selectedIndex;
        board.changeTeamPieces(false, p);
        initBuffersDraughts();
    });

    var rendering = document.getElementById("rendering-mode-selection");

    rendering.addEventListener("click", function () {
        var r = rendering.selectedIndex;

        switch (r) {
            case 0 :
                primitiveType = gl.TRIANGLES;
                break;
            case 1 :
                primitiveType = gl.LINE_LOOP;
                break;
            case 2 :
                primitiveType = gl.POINTS;
                break;
        }
        drawScene();
    });

    // Button events
    document.getElementById("XX-on-off-button").onclick = function(){
        // Switching on / off
        if( rotationXX_ON ) {
            rotationXX_ON = 0;
        }
        else {
            rotationXX_ON = 1;
        }
    };

    document.getElementById("XX-direction-button").onclick = function(){
        // Switching the direction
        if( rotationXX_DIR == 1 ) {
            rotationXX_DIR = -1;
        }
        else {
            rotationXX_DIR = 1;
        }
    };

    document.getElementById("XX-slower-button").onclick = function(){
        rotationXX_SPEED *= 0.75;
    };

    document.getElementById("XX-faster-button").onclick = function(){
        rotationXX_SPEED *= 1.25;
    };

    document.getElementById("YY-on-off-button").onclick = function(){
        // Switching on / off
        if( rotationYY_ON ) {
            rotationYY_ON = 0;
        }
        else {
            rotationYY_ON = 1;
        }
    };

    document.getElementById("YY-direction-button").onclick = function(){
        // Switching the direction
        if( rotationYY_DIR == 1 ) {
            rotationYY_DIR = -1;
        }
        else {
            rotationYY_DIR = 1;
        }
    };

    document.getElementById("YY-slower-button").onclick = function(){
        rotationYY_SPEED *= 0.75;
    };

    document.getElementById("YY-faster-button").onclick = function(){
        rotationYY_SPEED *= 1.25;
    };

    document.getElementById("ZZ-on-off-button").onclick = function(){
        // Switching on / off
        if( rotationZZ_ON ) {
            rotationZZ_ON = 0;
        }
        else {
            rotationZZ_ON = 1;
        }
    };

    document.getElementById("ZZ-direction-button").onclick = function(){
        // Switching the direction
        if( rotationZZ_DIR == 1 ) {
            rotationZZ_DIR = -1;
        }
        else {
            rotationZZ_DIR = 1;
        }
    };

    document.getElementById("ZZ-slower-button").onclick = function(){
        rotationZZ_SPEED *= 0.75;
    };

    document.getElementById("ZZ-faster-button").onclick = function(){
        rotationZZ_SPEED *= 1.25;
    };

    document.getElementById("reset-button").onclick = function(){
        resetGame();
    };

    document.getElementById("reset-view-button").onclick = function(){
        resetView();
    };

    document.getElementById("scale-up-button").onclick = function() {
        sx *= 1.1;
        sy *= 1.1;
        sz *= 1.1;
        drawScene();
    };

    document.getElementById("scale-down-button").onclick = function() {
        sx *= 0.9;
        sy *= 0.9;
        sz *= 0.9;
        drawScene();
    };

    document.getElementById("move-left-button").onclick = function(){
        tx -= 0.25;
        drawScene();
    };

    document.getElementById("move-right-button").onclick = function(){
        tx += 0.25;
        drawScene();
    };

    document.getElementById("move-up-button").onclick = function(){
        ty += 0.25;
        drawScene();
    };

    document.getElementById("move-down-button").onclick = function(){
        ty -= 0.25;
        drawScene();
    };
}

// Handling mouse events
// Adapted from www.learningwebgl.com
var mouseDown = false;
var lastMouseX = null;
var lastMouseY = null;

function handleMouseDown(event) {
    mouseDown = true;
    lastMouseX = event.clientX;
    lastMouseY = event.clientY;
}

function handleMouseUp(event) {
    mouseDown = false;
}

function handleMouseMove(event) {
    if (!mouseDown) {
        return;
    }

    // Rotation angles proportional to cursor displacement
    var newX = event.clientX;
    var newY = event.clientY;
    var deltaX = newX - lastMouseX;

    angleYY += radians( 10 * deltaX  )

    var deltaY = newY - lastMouseY;
    angleXX += radians( 10 * deltaY  )

    lastMouseX = newX
    lastMouseY = newY;
}

// Handling keyboard events
// Adapted from www.learningwebgl.com

var currentlyPressedKeys = {};
var blocked = {};
blocked[27] = false;
blocked[13] = false;
blocked[37] = false;
blocked[38] = false;
blocked[39] = false;
blocked[40] = false;

function unblock(index) {
    blocked[index] = false;
}

function handleKeys() {
    // Left cursor key
    if (currentlyPressedKeys[37]) {
        if(!blocked[37]) {
            blocked[37] = true;
            board.moveOverLeft();
            console.log(board.selectedSlot,board.overSlot);
        }
    }
    // Right cursor key
    if (currentlyPressedKeys[39]) {
        if(!blocked[39]) {
            blocked[39] = true;
            board.moveOverRight();
            console.log(board.selectedSlot,board.overSlot);
        }
    }
    // Up cursor key
    if (currentlyPressedKeys[38]) {
        if(!blocked[38]) {
            blocked[38] = true;
            board.moveOverUp();
            console.log(board.selectedSlot,board.overSlot);
        }
    }
    // Down cursor key
    if (currentlyPressedKeys[40]) {
        if(!blocked[40]) {
            blocked[40] = true;
            board.moveOverDown();
            console.log(board.selectedSlot,board.overSlot);
        }
    }
    // Enter key
    if (currentlyPressedKeys[13]) {
        if(!blocked[13]) {
            blocked[13] = true;
            board.selectSlot();
            console.log(board.selectedSlot,board.overSlot);
        }
    }
    // Esc key
    if (currentlyPressedKeys[27]) {
        if(!blocked[27]) {
            blocked[27] = true;
            board.deselectSlot();
            console.log(board.selectedSlot,board.overSlot);
        }
    }
}