// Drawing the model
function drawModel( modelVertexPositionBuffer,
                    modelVertexColorBuffer,
                    modelVertexIndexBuffer,
                    angleXX, angleYY, angleZZ,
                    sx, sy, sz,
                    tx, ty, tz,
                    mvMatrix,
                    primitiveType,
                    hasIllumination) {

    // Pay attention to transformation order !!
    mvMatrix = mult( mvMatrix, translationMatrix( tx, ty, tz ) );
    mvMatrix = mult( mvMatrix, rotationZZMatrix( angleZZ ) );
    mvMatrix = mult( mvMatrix, rotationYYMatrix( angleYY ) );
    mvMatrix = mult( mvMatrix, rotationXXMatrix( angleXX ) );
    mvMatrix = mult( mvMatrix, scalingMatrix( sx, sy, sz ) );

    // Passing the Model View Matrix to apply the current transformation
    var mvUniform = gl.getUniformLocation(shaderProgram, "uMVMatrix");
    gl.uniformMatrix4fv(mvUniform, false, new Float32Array(flatten(mvMatrix)));

    // Passing the buffers
    gl.bindBuffer(gl.ARRAY_BUFFER, modelVertexPositionBuffer);
    gl.vertexAttribPointer(shaderProgram.vertexPositionAttribute, modelVertexPositionBuffer.itemSize, gl.FLOAT, false, 0, 0);

    // Colors or illumination
    if (hasIllumination) {
        drawModelIllumination();
    }
    else {
        gl.bindBuffer(gl.ARRAY_BUFFER, modelVertexColorBuffer);
        gl.vertexAttribPointer(shaderProgram.vertexColorAttribute, modelVertexColorBuffer.itemSize, gl.FLOAT, false, 0, 0);
    }
    // The vertex indices
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, modelVertexIndexBuffer);

    // Drawing the triangles
    gl.drawElements(primitiveType, modelVertexIndexBuffer.numItems, gl.UNSIGNED_SHORT, 0);
}