// Animation --- Updating transformation parameters
var lastTime = 0;

function animate() {
    var timeNow = new Date().getTime();

    if( lastTime != 0 ) {
        var elapsed = timeNow - lastTime;

        if( rotationXX_ON ) {
            angleXX += rotationXX_DIR * rotationXX_SPEED * (90 * elapsed) / 1000.0;
        }

        if( rotationYY_ON ) {
            angleYY += rotationYY_DIR * rotationYY_SPEED * (90 * elapsed) / 1000.0;
        }

        if( rotationZZ_ON ) {
            angleZZ += rotationZZ_DIR * rotationZZ_SPEED * (90 * elapsed) / 1000.0;
        }
    }
    lastTime = timeNow;
}

// Timer
function tick() {
    requestAnimFrame(tick);
    
    handleKeys();
    drawScene();
    animate();
}
