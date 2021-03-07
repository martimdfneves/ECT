//  Drawing the 3D scene
var pos_Viewer = [ 0.0, 0.0, 0.0, 1.0 ];
function drawScene() {
    var pMatrix;
    var mvMatrix = mat4();

    // Clearing with the background color
    gl.clear(gl.COLOR_BUFFER_BIT);

    // NEW --- Computing the Projection Matrix
    if( projectionType == 0 ) {
        // For now, the default orthogonal view volume
        pMatrix = ortho( -1.0, 1.0, -1.0, 1.0, -1.0, 1.0 );
        tz = 0;
    }
    else {
        // A standard view volume
        // Viewer is at (0,0,0)
        // Ensure that the model is "inside" the view volume
        pMatrix = perspective( 45, 1, 0.05, 10 );
        tz = -2.25;

        pos_Viewer[0] = pos_Viewer[1] = pos_Viewer[2] = 0.0;

        pos_Viewer[3] = 1.0;
    }

    // Passing the Projection Matrix to apply the current projection
    var pUniform = gl.getUniformLocation(shaderProgram, "uPMatrix");
    gl.uniformMatrix4fv(pUniform, false, new Float32Array(flatten(pMatrix)));

    // Passing the viewer position to the vertex shader
    gl.uniform4fv( gl.getUniformLocation(shaderProgram, "viewerPosition"),
        flatten(pos_Viewer) );

    // Instantiating the models
    // Board Model
    drawModel( boardVertexPositionBuffer,
        boardVertexColorBuffer,
        boardVertexIndexBuffer,
        angleXX, angleYY, angleZZ,
        sx, sy, sz,
        tx, ty, tz,
        mvMatrix,
        primitiveType,
        false);

    // Slots Models
    for (var i = 0; i < slotsVertexPositionBuffer.length; i++) {
        drawModel(  slotsVertexPositionBuffer[i],
            slotsVertexColorBuffer[i],
            slotsVertexIndexBuffer[i],
            angleXX, angleYY, angleZZ,
            sx, sy, sz,
            tx, ty, tz,
            mvMatrix,
            primitiveType,
            false);
    }

    // Draughts Models
    for (var j = 0; j < draughtsVertexPositionBuffer.length; j++) {
        drawModel(  draughtsVertexPositionBuffer[j],
            draughtsVertexColorBuffer[j],
            draughtsVertexIndexBuffer[j],
            angleXX, angleYY, angleZZ,
            sx, sy, sz,
            tx , ty , tz,
            mvMatrix,
            primitiveType,
            false);
    }

    outputInfos();
}
