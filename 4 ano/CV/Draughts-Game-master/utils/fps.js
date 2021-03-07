var elapsedTime = 0;
var frameCount = 0;
var lastfpsTime = new Date().getTime();;

function countFrames() {
    var now = new Date().getTime();

    frameCount++;
    elapsedTime += (now - lastfpsTime);

    lastfpsTime = now;

    if(elapsedTime >= 1000) {
        fps = frameCount;
        frameCount = 0;
        elapsedTime -= 1000;
        document.getElementById('fps').innerHTML = fps;
    }
}
