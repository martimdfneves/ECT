//////////////////////////////////////////////////////////////////////////////
//
//  WebGL_example_19.js 
//
//  Animating one model.
//
//  References: www.learningwebgl.com + E. Angel examples
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

var tz = -2.5;

// The rotation angles in degrees

var angleXX = 0.0;

var angleYY = 0.0;

var angleZZ = 0.0;

// The scaling factors

var sx = 1.0;

var sy = 1.0;

var sz = 1.0;

// NEW - Animation controls

var rotationXX_ON = 1;

var rotationXX_DIR = 1;

var rotationXX_SPEED = 1;

var rotationYY_ON = 1;

var rotationYY_DIR = 1;

var rotationYY_SPEED = 1;

var rotationZZ_ON = 1;

var rotationZZ_DIR = 1;

var rotationZZ_SPEED = 1;

var translationXX_ON = 1;

var translationXX_DIR = 1;

var translationXX_SPEED = 1;

var translationYY_ON = 1;

var translationYY_DIR = 1;

var translationYY_SPEED = 1;

var translationZZ_ON = 1;

var translationZZ_DIR = 1;

var translationZZ_SPEED = 1;
 
// To allow choosing the way of drawing the model triangles

var primitiveType = null;
 
// To allow choosing the projection type

var projectionType = 0;
 
// For storing the vertices defining the triangles

var vertices = [

		// FRONT FACE
		 
		-0.25, -0.25,  0.25,
		 
		 0.25, -0.25,  0.25,
		 
		 0.25,  0.25,  0.25,

		 
		 0.25,  0.25,  0.25,
		 
		-0.25,  0.25,  0.25,
		 
		-0.25, -0.25,  0.25,
		
		// TOP FACE
		
		-0.25,  0.25,  0.25,
		 
		 0.25,  0.25,  0.25,
		 
		 0.25,  0.25, -0.25,

		 
		 0.25,  0.25, -0.25,
		 
		-0.25,  0.25, -0.25,
		 
		-0.25,  0.25,  0.25,
		
		// BOTTOM FACE 
		
		-0.25, -0.25, -0.25,
		 
		 0.25, -0.25, -0.25,
		 
		 0.25, -0.25,  0.25,

		 
		 0.25, -0.25,  0.25,
		 
		-0.25, -0.25,  0.25,
		 
		-0.25, -0.25, -0.25,
		
		// LEFT FACE 
		
		-0.25,  0.25,  0.25,
		 
		-0.25, -0.25, -0.25,

		-0.25, -0.25,  0.25,
		 
		 
		-0.25,  0.25,  0.25,
		 
		-0.25,  0.25, -0.25,
		 
		-0.25, -0.25, -0.25,
		
		// RIGHT FACE 
		
		 0.25,  0.25, -0.25,
		 
		 0.25, -0.25,  0.25,

		 0.25, -0.25, -0.25,
		 
		 
		 0.25,  0.25, -0.25,
		 
		 0.25,  0.25,  0.25,
		 
		 0.25, -0.25,  0.25,
		
		// BACK FACE 
		
		-0.25,  0.25, -0.25,
		 
		 0.25, -0.25, -0.25,

		-0.25, -0.25, -0.25,
		 
		 
		-0.25,  0.25, -0.25,
		 
		 0.25,  0.25, -0.25,
		 
		 0.25, -0.25, -0.25,			 
];

// And their colour

var colors = [

		 // FRONT FACE
		 	
		 1.00,  0.00,  0.00,
		 
		 1.00,  0.00,  0.00,
		 
		 1.00,  0.00,  0.00,

		 	
		 1.00,  1.00,  0.00,
		 
		 1.00,  1.00,  0.00,
		 
		 1.00,  1.00,  0.00,
		 			 
		 // TOP FACE
		 	
		 0.00,  0.00,  0.00,
		 
		 0.00,  0.00,  0.00,
		 
		 0.00,  0.00,  0.00,

		 	
		 0.50,  0.50,  0.50,
		 
		 0.50,  0.50,  0.50,
		 
		 0.50,  0.50,  0.50,
		 			 
		 // BOTTOM FACE
		 	
		 0.00,  1.00,  0.00,
		 
		 0.00,  1.00,  0.00,
		 
		 0.00,  1.00,  0.00,

		 	
		 0.00,  1.00,  1.00,
		 
		 0.00,  1.00,  1.00,
		 
		 0.00,  1.00,  1.00,
		 			 
		 // LEFT FACE
		 	
		 0.00,  0.00,  1.00,
		 
		 0.00,  0.00,  1.00,
		 
		 0.00,  0.00,  1.00,

		 	
		 1.00,  0.00,  1.00,
		 
		 1.00,  0.00,  1.00,
		 
		 1.00,  0.00,  1.00,
		 			 
		 // RIGHT FACE
		 	
		 0.25,  0.50,  0.50,
		 
		 0.25,  0.50,  0.50,
		 
		 0.25,  0.50,  0.50,

		 	
		 0.50,  0.25,  0.00,
		 
		 0.50,  0.25,  0.00,
		 
		 0.50,  0.25,  0.00,
		 			 
		 			 
		 // BACK FACE
		 	
		 0.25,  0.00,  0.75,
		 
		 0.25,  0.00,  0.75,
		 
		 0.25,  0.00,  0.75,

		 	
		 0.50,  0.35,  0.35,
		 
		 0.50,  0.35,  0.35,
		 
		 0.50,  0.35,  0.35,			 			 
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
	
	if( projectionType == 0 ) {
		
		// For now, the default orthogonal view volume
		
		pMatrix = ortho( -1.0, 1.0, -1.0, 1.0, -1.0, 1.0 );
		
		// No need to move the model into the view volume !!
		
		tz = 0;
		
		// TO BE DONE !
		
		// Allow the user to control the size of the view volume
	}
	else {	

		// A standard view volume.
		
		// Viewer is at (0,0,0)
		
		// Ensure that the model is "inside" the view volume
		
		pMatrix = perspective( 45, 1, 0.05, 10 );
		
		if ( tz == 0.0 ) {
			
			tz = -1.5;
		}
	}
	
	// Passing the Projection Matrix to apply the current projection
	
	var pUniform = gl.getUniformLocation(shaderProgram, "uPMatrix");
	
	gl.uniformMatrix4fv(pUniform, false, new Float32Array(flatten(pMatrix)));
	
	// Computing the Model-View Matrix
	
	// Pay attention to the matrix multiplication order!!
	
	// First transformation ?
	
	// Last transformation ?
	
	var mvMatrix = mult( rotationZZMatrix( angleZZ ), 
	
						 scalingMatrix( sx, sy, sz ) );
						 
	mvMatrix = mult( rotationYYMatrix( angleYY ), mvMatrix );
						 
	mvMatrix = mult( rotationXXMatrix( angleXX ), mvMatrix );
						 
	mvMatrix = mult( translationMatrix( tx-0.5, ty, tz ), mvMatrix );
						 
	// Passing the Model View Matrix to apply the current transformation
	
	var mvUniform = gl.getUniformLocation(shaderProgram, "uMVMatrix");
	
	gl.uniformMatrix4fv(mvUniform, false, new Float32Array(flatten(mvMatrix)));
	
	// Drawing the contents of the vertex buffer
	
	// primitiveType allows drawing as filled triangles / wireframe / vertices
	
	if( primitiveType == gl.LINE_LOOP ) {
		
		// To simulate wireframe drawing!
		
		// No faces are defined! There are no hidden lines!
		
		// Taking the vertices 3 by 3 and drawing a LINE_LOOP
		
		var i;
		
		for( i = 0; i < triangleVertexPositionBuffer.numItems / 3; i++ ) {
		
			gl.drawArrays( primitiveType, 3 * i, 3 ); 
		}
	}	
	else {
				
		gl.drawArrays(primitiveType, 0, triangleVertexPositionBuffer.numItems); 
		
	}
	
	//second cube

	var mvMatrix = mult( rotationZZMatrix( angleZZ ), 
	
						 scalingMatrix( sx, sy, sz ) );
						 
	mvMatrix = mult( rotationYYMatrix( -angleYY ), mvMatrix );
						 
	mvMatrix = mult( rotationXXMatrix( -angleXX ), mvMatrix );
						 
	mvMatrix = mult( translationMatrix( tx+0.5, ty, tz ), mvMatrix );
						 
	// Passing the Model View Matrix to apply the current transformation
	
	var mvUniform = gl.getUniformLocation(shaderProgram, "uMVMatrix");
	
	gl.uniformMatrix4fv(mvUniform, false, new Float32Array(flatten(mvMatrix)));
	
	// Drawing the contents of the vertex buffer
	
	// primitiveType allows drawing as filled triangles / wireframe / vertices
	
	if( primitiveType == gl.LINE_LOOP ) {
		
		// To simulate wireframe drawing!
		
		// No faces are defined! There are no hidden lines!
		
		// Taking the vertices 3 by 3 and drawing a LINE_LOOP
		
		var i;
		
		for( i = 0; i < triangleVertexPositionBuffer.numItems / 3; i++ ) {
		
			gl.drawArrays( primitiveType, 3 * i, 3 ); 
		}
	}	
	else {
				
		gl.drawArrays(primitiveType, 0, triangleVertexPositionBuffer.numItems); 
		
	}
}

//----------------------------------------------------------------------------
//
//  NEW --- Animation
//

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
		
		if ( translationXX_ON ) {

			tx += translationXX_DIR * translationXX_SPEED * elapsed / 1000.0;
			
			// To avoid leaving the viewing window
			if (tx >= 0.75 || tx <= -0.75) {
				translationXX_DIR *= -1;
			} 
	
		}

		if ( translationYY_ON ) {

			ty += translationYY_DIR * translationYY_SPEED * elapsed / 1000.0;
			
			// To avoid leaving the viewing window
			if (ty >= 0.75 || ty <= -0.75) {
				translationYY_DIR *= -1;
			} 
	
		}

		if ( translationZZ_ON ) {

			tz += translationZZ_DIR * translationZZ_SPEED * elapsed / 1000.0;
			
			// To avoid leaving the viewing window
			if (tz >= 0.75 || tz <= -0.75) {
				translationZZ_DIR *= -1;
			} 
	
		}
	}
	
	lastTime = timeNow;
}


//----------------------------------------------------------------------------

// Timer

function tick() {
	
	requestAnimFrame(tick);
	
	drawScene();
	
	animate();
}




//----------------------------------------------------------------------------
//
//  User Interaction
//

function outputInfos(){
		
}

//----------------------------------------------------------------------------

function setEventListeners(){
	
	// Dropdown list
	
	var projection = document.getElementById("projection-selection");
	
	projection.addEventListener("click", function(){
				
		// Getting the selection
		
		var p = projection.selectedIndex;
				
		switch(p){
			
			case 0 : projectionType = 0;
				break;
			
			case 1 : projectionType = 1;
				break;
		}  	
	});      

	// Dropdown list
	
	var list = document.getElementById("rendering-mode-selection");
	
	list.addEventListener("click", function(){
				
		// Getting the selection
		
		var mode = list.selectedIndex;
				
		switch(mode){
			
			case 0 : primitiveType = gl.TRIANGLES;
				break;
			
			case 1 : primitiveType = gl.LINE_LOOP;
				break;
			
			case 2 : primitiveType = gl.POINTS;
				break;
		}
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

	document.getElementById("XX-on-off-button-t").onclick = function(){
		
		// Switching on / off
		
		if( translationXX_ON ) {
			
			translationXX_ON = 0;
		}
		else {
			
			translationXX_ON = 1;
		}  
	};

	document.getElementById("XX-direction-button-t").onclick = function(){
		
		// Switching the direction
		
		if( translationXX_DIR == 1 ) {
			
			translationXX_DIR = -1;
		}
		else {
			
			translationXX_DIR = 1;
		}  
	};      

	document.getElementById("XX-slower-button-t").onclick = function(){
		
		translationXX_SPEED *= 0.75;  
	};      

	document.getElementById("XX-faster-button-t").onclick = function(){
		
		translationXX_SPEED *= 1.25;  
	}; 
	
	document.getElementById("YY-on-off-button-t").onclick = function(){
		
		// Switching on / off
		
		if( translationYY_ON ) {
			
			translationYY_ON = 0;
		}
		else {
			
			translationYY_ON = 1;
		}  
	};

	document.getElementById("YY-direction-button-t").onclick = function(){
		
		// Switching the direction
		
		if( translationYY_DIR == 1 ) {
			
			translationYY_DIR = -1;
		}
		else {
			
			translationYY_DIR = 1;
		}  
	};      

	document.getElementById("YY-slower-button-t").onclick = function(){
		
		translationYY_SPEED *= 0.75;  
	};      

	document.getElementById("YY-faster-button-t").onclick = function(){
		
		translationYY_SPEED *= 1.25;  
	};      

	document.getElementById("ZZ-on-off-button-t").onclick = function(){
		
		// Switching on / off
		
		if( translationZZ_ON ) {
			
			translationZZ_ON = 0;
		}
		else {
			
			translationZZ_ON = 1;
		}  
	};

	document.getElementById("ZZ-direction-button-t").onclick = function(){
		
		// Switching the direction
		
		if( translationZZ_DIR == 1 ) {
			
			translationZZ_DIR = -1;
		}
		else {
			
			translationZZ_DIR = 1;
		}  
	};      

	document.getElementById("ZZ-slower-button-t").onclick = function(){
		
		translationZZ_SPEED *= 0.75;  
	};      

	document.getElementById("ZZ-faster-button-t").onclick = function(){
		
		translationZZ_SPEED *= 1.25;  
	}; 

	document.getElementById("reset-button").onclick = function(){
		
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

		rotationXX_ON = 0;
		
		rotationYY_ON = 0;

		rotationZZ_ON = 0;

		rotationXX_DIR = 1;
		
		rotationYY_DIR = 1;

		rotationZZ_DIR = 1;

		rotationXX_SPEED = 1;
		
		rotationYY_SPEED = 1;

		rotationZZ_SPEED = 1;

		translationXX_ON = 0;
		
		translationYY_ON = 0;

		translationZZ_ON = 0;

		translationXX_DIR = 1;
		
		translationYY_DIR = 1;

		translationZZ_DIR = 1;

		translationXX_SPEED = 1;
		
		translationYY_SPEED = 1;

		translationZZ_SPEED = 1;
	};      

	document.getElementById("face-culling-button").onclick = function(){
		
		if( gl.isEnabled( gl.CULL_FACE ) )
		{
			gl.disable( gl.CULL_FACE );
		}
		else
		{
			gl.enable( gl.CULL_FACE );
		}
	};      
}

//----------------------------------------------------------------------------
//
// WebGL Initialization
//

function initWebGL( canvas ) {
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
		
		gl.enable( gl.CULL_FACE );
		
		// DEFAULT: The BACK FACE is culled!!
		
		// The next instruction is not needed...
		
		gl.cullFace( gl.BACK );
		
	} catch (e) {
	}
	if (!gl) {
		alert("Could not initialise WebGL, sorry! :-(");
	}        
}

//----------------------------------------------------------------------------

function runWebGL() {
	
	var canvas = document.getElementById("my-canvas");
	
	initWebGL( canvas );

	shaderProgram = initShaders( gl );
	
	setEventListeners();
	
	initBuffers();
	
	tick();		// NEW --- A timer controls the rendering / animation    

	outputInfos();
}


