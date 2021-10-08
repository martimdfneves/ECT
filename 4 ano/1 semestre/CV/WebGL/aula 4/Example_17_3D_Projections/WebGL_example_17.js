//////////////////////////////////////////////////////////////////////////////
//
//  WebGL_example_17_01.js 
//
//  Reading a 3D model from a file.
//
//  Triangle visualization modes.
//
//  Modular code organization.
//
//  J. Madeira - October 2015
//
//////////////////////////////////////////////////////////////////////////////


//----------------------------------------------------------------------------
//
// Global Variables
//

var gl = null; // WebGL context

var shaderProgram = null;

var triangleVertexPositionBuffer = null;

var triangleVertexColorBuffer = null;

// The global transformation parameters

// The translation vector

var tx = 0.0;

var ty = 0.0;

var tz = -1.0;

// The rotation angles in degrees

var angleXX = 0.0;

var angleYY = 0.0;

var angleZZ = 0.0;

// The scaling factors

var sx = 1.0;

var sy = 1.0;

var sz = 1.0;

// NEW - To allow choosing the way of drawing the model triangles

var primitiveType = null;

// NEW - To allow choosing the projection type

var projectionType = 0;

// For storing the vertices defining the triangles

var vertices = [

    // FRONT FACE

    -0.25, -0.25, 0.25,

    0.25, -0.25, 0.25,

    0.25, 0.25, 0.25,


    0.25, 0.25, 0.25,

    -0.25, 0.25, 0.25,

    -0.25, -0.25, 0.25,

    // TOP FACE

    -0.25, 0.25, 0.25,

    0.25, 0.25, 0.25,

    0.25, 0.25, -0.25,


    0.25, 0.25, -0.25,

    -0.25, 0.25, -0.25,

    -0.25, 0.25, 0.25,

    // BOTTOM FACE

    -0.25, -0.25, -0.25,

    0.25, -0.25, -0.25,

    0.25, -0.25, 0.25,


    0.25, -0.25, 0.25,

    -0.25, -0.25, 0.25,

    -0.25, -0.25, -0.25,

    // LEFT FACE

    -0.25, 0.25, 0.25,

    -0.25, -0.25, -0.25,

    -0.25, -0.25, 0.25,


    -0.25, 0.25, 0.25,

    -0.25, 0.25, -0.25,

    -0.25, -0.25, -0.25,

    // RIGHT FACE

    0.25, 0.25, -0.25,

    0.25, -0.25, 0.25,

    0.25, -0.25, -0.25,


    0.25, 0.25, -0.25,

    0.25, 0.25, 0.25,

    0.25, -0.25, 0.25,

    // BACK FACE

    -0.25, 0.25, -0.25,

    0.25, -0.25, -0.25,

    -0.25, -0.25, -0.25,


    -0.25, 0.25, -0.25,

    0.25, 0.25, -0.25,

    0.25, -0.25, -0.25,
];

// And their colour

var colors = [

    // FRONT FACE

    1.00, 0.00, 0.00,

    1.00, 0.00, 0.00,

    1.00, 0.00, 0.00,


    1.00, 1.00, 0.00,

    1.00, 1.00, 0.00,

    1.00, 1.00, 0.00,

    // TOP FACE

    0.00, 0.00, 0.00,

    0.00, 0.00, 0.00,

    0.00, 0.00, 0.00,


    0.50, 0.50, 0.50,

    0.50, 0.50, 0.50,

    0.50, 0.50, 0.50,

    // BOTTOM FACE

    0.00, 1.00, 0.00,

    0.00, 1.00, 0.00,

    0.00, 1.00, 0.00,


    0.00, 1.00, 1.00,

    0.00, 1.00, 1.00,

    0.00, 1.00, 1.00,

    // LEFT FACE

    0.00, 0.00, 1.00,

    0.00, 0.00, 1.00,

    0.00, 0.00, 1.00,


    1.00, 0.00, 1.00,

    1.00, 0.00, 1.00,

    1.00, 0.00, 1.00,

    // RIGHT FACE

    0.25, 0.50, 0.50,

    0.25, 0.50, 0.50,

    0.25, 0.50, 0.50,


    0.50, 0.25, 0.00,

    0.50, 0.25, 0.00,

    0.50, 0.25, 0.00,


    // BACK FACE

    0.25, 0.00, 0.75,

    0.25, 0.00, 0.75,

    0.25, 0.00, 0.75,


    0.50, 0.35, 0.35,

    0.50, 0.35, 0.35,

    0.50, 0.35, 0.35,
];

//----------------------------------------------------------------------------
//
// The WebGL code
//

//----------------------------------------------------------------------------
//
//  Rendering
//

// Handling the Vertex and the Color Buffers

function initBuffers() {

    // Coordinates

    triangleVertexPositionBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, triangleVertexPositionBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertices), gl.STATIC_DRAW);
    triangleVertexPositionBuffer.itemSize = 3;
    triangleVertexPositionBuffer.numItems = vertices.length / 3;

    // Associating to the vertex shader

    gl.vertexAttribPointer(shaderProgram.vertexPositionAttribute,
        triangleVertexPositionBuffer.itemSize,
        gl.FLOAT, false, 0, 0);

    // Colors

    triangleVertexColorBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, triangleVertexColorBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(colors), gl.STATIC_DRAW);
    triangleVertexColorBuffer.itemSize = 3;
    triangleVertexColorBuffer.numItems = colors.length / 3;

    // Associating to the vertex shader

    gl.vertexAttribPointer(shaderProgram.vertexColorAttribute,
        triangleVertexColorBuffer.itemSize,
        gl.FLOAT, false, 0, 0);
}

//----------------------------------------------------------------------------

//  Drawing the 3D scene

function drawScene() {

    var pMatrix;

    // Clearing with the background color

    gl.clear(gl.COLOR_BUFFER_BIT);

    // NEW --- Computing the Projection Matrix

    if (projectionType == 0) {

        // For now, the default orthogonal view volume

        pMatrix = ortho(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0);

        // TO BE DONE !

        // Allow the user to control the size of the view volume
    } else {

        // A standard view volume.

        // Viewer is at (0,0,0)

        // Ensure that the model is "inside" the view volume

        pMatrix = perspective(45, 1, 0.05, 10);

    }

    // Passing the Projection Matrix to apply the current projection

    var pUniform = gl.getUniformLocation(shaderProgram, "uPMatrix");

    gl.uniformMatrix4fv(pUniform, false, new Float32Array(flatten(pMatrix)));

    // Computing the Model-View Matrix

    // Pay attention to the matrix multiplication order!!

    // First transformation ?

    // Last transformation ?

    var mvMatrix = mult(rotationZZMatrix(angleZZ),

        scalingMatrix(sx, sy, sz));

    mvMatrix = mult(rotationYYMatrix(angleYY), mvMatrix);

    mvMatrix = mult(rotationXXMatrix(angleXX), mvMatrix);

    mvMatrix = mult(translationMatrix(tx, ty, tz), mvMatrix);

    // Passing the Model View Matrix to apply the current transformation

    var mvUniform = gl.getUniformLocation(shaderProgram, "uMVMatrix");

    gl.uniformMatrix4fv(mvUniform, false, new Float32Array(flatten(mvMatrix)));

    // Drawing the contents of the vertex buffer

    // primitiveType allows drawing as filled triangles / wireframe / vertices

    if (primitiveType == gl.LINE_LOOP) {

        // To simulate wireframe drawing!

        // No faces are defined! There are no hidden lines!

        // Taking the vertices 3 by 3 and drawing a LINE_LOOP

        var i;

        for (i = 0; i < triangleVertexPositionBuffer.numItems / 3; i++) {

            gl.drawArrays(primitiveType, 3 * i, 3);
        }
    } else {

        gl.drawArrays(primitiveType, 0, triangleVertexPositionBuffer.numItems);

    }
}

//----------------------------------------------------------------------------
//
//  User Interaction
//

function outputInfos() {

}

//----------------------------------------------------------------------------

function setEventListeners() {

    // NEW --- Dropdown list

    var projection = document.getElementById("projection-selection");

    projection.addEventListener("click", function () {

        // Getting the selection

        var p = projection.selectedIndex;

        switch (p) {

            case 0 :
                projectionType = 0;
                break;

            case 1 :
                projectionType = 1;
                break;
        }

        // Rendering

        drawScene();

    });

    // NEW --- Dropdown list

    var list = document.getElementById("rendering-mode-selection");

    list.addEventListener("click", function () {

        // Getting the selection

        var mode = list.selectedIndex;

        switch (mode) {

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

        // Rendering

        drawScene();

    });

    // NEW --- File loading

    // Adapted from:

    // http://stackoverflow.com/questions/23331546/how-to-use-javascript-to-read-local-text-file-and-read-line-by-line

    document.getElementById("file").onchange = function () {

        var file = this.files[0];

        var reader = new FileReader();

        reader.onload = function (progressEvent) {

            // Entire file read as a string

            // The tokens/values in the file

            // Separation between values is 1 or mode whitespaces

            var tokens = this.result.split(/\s\s*/);

            // Array of values; each value is a string

            var numVertices = parseInt(tokens[0]);

            // For every vertex we have 6 floating point values

            var i, j;

            var aux = 1;

            var newVertices = [];

            var newColors = []

            for (i = 0; i < numVertices; i++) {

                for (j = 0; j < 3; j++) {

                    newVertices[3 * i + j] = parseFloat(tokens[aux++]);
                }

                for (j = 0; j < 3; j++) {

                    newColors[3 * i + j] = parseFloat(tokens[aux++]);
                }
            }

            // Assigning to the current model

            vertices = newVertices;

            colors = newColors;

            // Rendering the model just read

            initBuffers();

            // RESET the transformations - NEED AUXILIARY FUNCTION !!

            tx = ty = tz = 0.0;

            angleXX = angleYY = angleZZ = 0.0;

            sx = sy = sz = 1.0;

            drawScene();

            console.log("AFTER RENDERING");
        };

        // Entire file read as a string

        reader.readAsText(file);
    }

    // Button events

    document.getElementById("move-left-button").onclick = function () {

        // Updating

        tx -= 0.25;

        // Render the viewport

        drawScene();
    };

    document.getElementById("move-right-button").onclick = function () {

        // Updating

        tx += 0.25;

        // Render the viewport

        drawScene();
    };

    document.getElementById("move-up-button").onclick = function () {

        // Updating

        ty += 0.25;

        // Render the viewport

        drawScene();
    };

    document.getElementById("move-down-button").onclick = function () {

        // Updating

        ty -= 0.25;

        // Render the viewport

        drawScene();
    };

    document.getElementById("move-front-button").onclick = function () {

        // Updating

        tz += 0.25;

        // Render the viewport

        drawScene();
    };

    document.getElementById("move-back-button").onclick = function () {

        // Updating

        tz -= 0.25;

        // Render the viewport

        drawScene();
    };

    document.getElementById("scale-up-button").onclick = function () {

        // Updating

        sx *= 1.1;

        sy *= 1.1;

        sz *= 1.1;

        // Render the viewport

        drawScene();
    };

    document.getElementById("scale-down-button").onclick = function () {

        // Updating

        sx *= 0.9;

        sy *= 0.9;

        sz *= 0.9;

        // Render the viewport

        drawScene();
    };

    document.getElementById("XX-rotate-CW-button").onclick = function () {

        // Updating

        angleXX -= 15.0;

        // Render the viewport

        drawScene();
    };

    document.getElementById("XX-rotate-CCW-button").onclick = function () {

        // Updating

        angleXX += 15.0;

        // Render the viewport

        drawScene();
    };

    document.getElementById("YY-rotate-CW-button").onclick = function () {

        // Updating

        angleYY -= 15.0;

        // Render the viewport

        drawScene();
    };

    document.getElementById("YY-rotate-CCW-button").onclick = function () {

        // Updating

        angleYY += 15.0;

        // Render the viewport

        drawScene();
    };

    document.getElementById("ZZ-rotate-CW-button").onclick = function () {

        // Updating

        angleZZ -= 15.0;

        // Render the viewport

        drawScene();
    };

    document.getElementById("ZZ-rotate-CCW-button").onclick = function () {

        // Updating

        angleZZ += 15.0;

        // Render the viewport

        drawScene();
    };

    document.getElementById("reset-button").onclick = function () {

        // The initial values

        tx = 0.0;

        ty = 0.0;

        tz = 0.0;

        angleXX = 0.0;

        angleYY = 0.0;

        angleZZ = 0.0;

        sx = 1.0;

        sy = 1.0;

        sz = 1.0;

        // Render the viewport

        drawScene();
    };

    document.getElementById("face-culling-button").onclick = function () {

        if (gl.isEnabled(gl.CULL_FACE)) {
            gl.disable(gl.CULL_FACE);
        } else {
            gl.enable(gl.CULL_FACE);
        }

        // Render the viewport

        drawScene();
    };
}

//----------------------------------------------------------------------------
//
// WebGL Initialization
//

function initWebGL(canvas) {
    try {

        // Create the WebGL context

        // Some browsers still need "experimental-webgl"

        gl = canvas.getContext("webgl") || canvas.getContext("experimental-webgl");

        // DEFAULT: The viewport occupies the whole canvas

        // DEFAULT: The viewport background color is WHITE

        // NEW - Drawing the triangles defining the model

        primitiveType = gl.TRIANGLES;

        // DEFAULT: Face culling is DISABLED

        // Enable FACE CULLING

        gl.enable(gl.CULL_FACE);

        // DEFAULT: The BACK FACE is culled!!

        // The next instruction is not needed...

        gl.cullFace(gl.BACK);

    } catch (e) {
    }
    if (!gl) {
        alert("Could not initialise WebGL, sorry! :-(");
    }
}

//----------------------------------------------------------------------------

function runWebGL() {

    var canvas = document.getElementById("my-canvas");

    initWebGL(canvas);

    shaderProgram = initShaders(gl);

    setEventListeners();

    initBuffers();

    drawScene();

    outputInfos();
}


