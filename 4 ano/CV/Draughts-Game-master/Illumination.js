// Point Light Source Features
// Directional
var pos_Light_Source = [ 0.0, 0.0, 1.0, 1.0 ];

// White light
var int_Light_Source = [ 1.0, 1.0, 1.0 ];

// Low ambient illumination
var ambient_Illumination = [ 0.3, 0.3, 0.3 ];

function drawModelIllumination () {
    // Model Material Features
    var material = board.getMaterial(); // this.kAmbi, this.kDiff, this.kSpec, this.nPhong
    var kAmbi = material[0];            // Ambient coef.
    var kDiff = material[1];            // Diffuse coef.
    var kSpec = material[2];            // Specular coef.
    var nPhong = material[3];           // Phong coef.

    var ambientProduct = mult( kAmbi, ambient_Illumination );
    var diffuseProduct = mult( kDiff, int_Light_Source );
    var specularProduct = mult( kSpec, int_Light_Source );

    // Associating the data to the vertex shader
    // Vertex Coordinates and Vertex Normal Vectors
    initBuffersBoard();

    // Partial illumination terms and shininess Phong coefficient
    gl.uniform3fv( gl.getUniformLocation(shaderProgram, "ambientProduct"),
        flatten(ambientProduct) );

    gl.uniform3fv( gl.getUniformLocation(shaderProgram, "diffuseProduct"),
        flatten(diffuseProduct) );

    gl.uniform3fv( gl.getUniformLocation(shaderProgram, "specularProduct"),
        flatten(specularProduct) );

    gl.uniform1f( gl.getUniformLocation(shaderProgram, "shininess"),
        nPhong );

    //Position of the Light Source
    gl.uniform4fv( gl.getUniformLocation(shaderProgram, "lightPosition"),
        flatten(pos_Light_Source) );
}

