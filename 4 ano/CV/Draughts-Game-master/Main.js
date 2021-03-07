//////////////////////////////////////////////////////////////////////////////
//
//  Main.js
//
//  Adapted J. Madeira - November 2015
//
//////////////////////////////////////////////////////////////////////////////

// Global Variables
// WebGL context 
var gl = null;
var shaderProgram = null;

// Game Entities 
var board	 = new Board();
var slots 	 = board.getSlots();
var draughts = board.getDraughts();

// Buffers 
var boardVertexPositionBuffer = null;		// Board
var boardVertexIndexBuffer = null;
var boardVertexColorBuffer = null;
var boardVertexNormalBuffer = null;

var slotsVertexPositionBuffer = [];			// Slots
var slotsVertexIndexBuffer =  [];
var slotsVertexColorBuffer = [];

var draughtsVertexPositionBuffer = [];		// Draughts
var draughtsVertexIndexBuffer = [];
var draughtsVertexColorBuffer = [];

// The global transformation parameters 
// The translation vector
var tx = 0.0;
var ty = 0.0;
var tz = 0.0;

// The rotation angles in degrees
var angleXX = 40;
var angleYY = 0.0;
var angleZZ = 0.0;

// The scaling factors
var sx = 0.117;
var sy = 0.117;
var sz = 0.117;

// Animation controls
var rotationXX_ON = 0;
var rotationXX_DIR = 1;
var rotationXX_SPEED = 1;

var rotationYY_ON = 0;
var rotationYY_DIR = 1;
var rotationYY_SPEED = 1;
 
var rotationZZ_ON = 0;
var rotationZZ_DIR = 1;
var rotationZZ_SPEED = 1;

var primitiveType = null;	// To allow choosing the way of drawing the model triangles
var projectionType = 1;		// To allow choosing the projection type

function resetView() {
	// The initial values
	tx = 0.0;
	ty = 0.0;
	tz = 0.0;

	angleXX = 40;
	angleYY = 0.0;
	angleZZ = 0.0;

	sx = 0.117;
	sy = 0.117;
	sz = 0.117;

	rotationXX_ON = 0;
	rotationXX_DIR = 1;
	rotationXX_SPEED = 1;

	rotationYY_ON = 0;
	rotationYY_DIR = 1;
	rotationYY_SPEED = 1;

	rotationZZ_ON = 0;
	rotationZZ_DIR = 1;
	rotationZZ_SPEED = 1;

	projectionType = 1;		// To allow choosing the projection type
}

function resetGame() {
	console.log("Reset game");

	gl = null;
	shaderProgram = null;

	board	 = new Board();
	slots 	 = board.getSlots();
	draughts = board.getDraughts();

	boardVertexPositionBuffer = null;		// Board
	boardVertexIndexBuffer = null;
	boardVertexColorBuffer = null;
	boardVertexNormalBuffer = null;

	slotsVertexPositionBuffer = [];			// Slots
	slotsVertexIndexBuffer =  [];
	slotsVertexColorBuffer = [];

	draughtsVertexPositionBuffer = [];		// Draughts
	draughtsVertexIndexBuffer = [];
	draughtsVertexColorBuffer = [];

	resetView();

	var canvas = document.getElementById("my-canvas");
	initWebGL( canvas );
	shaderProgram = initShaders( gl );
	setEventListeners( canvas );
	initBuffers();
}

// User Interaction
function outputInfos() {
	// FPS
	countFrames();

	// Current team
	if (board.currentTeam) {
		document.getElementById('current-team').innerHTML = "Team 1";
	}
	else {
		document.getElementById('current-team').innerHTML = "Team 2";
	}

	if (board.getGameOver()) {
		alert("Game Over! Congratulations to " + board.getWinningTeam());
		resetGame();
	}

	var scores = board.getScores();
	document.getElementById("team1-score").innerText = scores[0];
	document.getElementById("team2-score").innerText = scores[1];
}

// WebGL
function initWebGL( canvas ) {
	try {
		// Create the WebGL context
		// Some browsers still need "experimental-webgl"
		gl = canvas.getContext("webgl") || canvas.getContext("experimental-webgl");

		primitiveType = gl.TRIANGLES;

		// Enable FACE CULLING and DEPTH TEST
		gl.enable( gl.DEPTH_TEST );

	} catch (e) {
	}
	if (!gl) {
		alert("Could not initialise WebGL, sorry! :-(");
	}        
}

function runWebGL() {
	var canvas = document.getElementById("my-canvas");
	
	initWebGL( canvas );
	shaderProgram = initShaders( gl );
	setEventListeners( canvas );
	
	initBuffers();
	tick();		// A timer controls the rendering / animation   
	outputInfos();
}