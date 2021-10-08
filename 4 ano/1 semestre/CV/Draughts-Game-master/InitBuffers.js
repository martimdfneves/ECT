// Handling the Buffers
function initBuffers() {
    initBuffersBoard();
    initBuffersSlots();
    initBuffersDraughts();
}

function initBuffersBoard() {
    // Coordinates
    var vertices = board.getVertices();
    var colors = board.getColors();
    var boardVertexIndices = board.getVerticesIndexes();
    var normals = board.getVertexNormals();

    boardVertexPositionBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, boardVertexPositionBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertices), gl.STATIC_DRAW);
    boardVertexPositionBuffer.itemSize = 3;
    boardVertexPositionBuffer.numItems = vertices.length / 3;

    gl.vertexAttribPointer(shaderProgram.vertexPositionAttribute,
        boardVertexPositionBuffer.itemSize,
        gl.FLOAT, false, 0, 0);

    // Colors
    boardVertexColorBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, boardVertexColorBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(colors), gl.STATIC_DRAW);
    boardVertexColorBuffer.itemSize = 3;
    boardVertexColorBuffer.numItems = vertices.length / 3;

    // Vertex Normal Vectors
    boardVertexNormalBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, boardVertexNormalBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(normals), gl.STATIC_DRAW);
    boardVertexNormalBuffer.itemSize = 3;
    boardVertexNormalBuffer.numItems = normals.length / 3;

    // Associating to the vertex shader
    gl.vertexAttribPointer(shaderProgram.vertexNormalAttribute,
        boardVertexNormalBuffer.itemSize,
        gl.FLOAT, false, 0, 0);

    // Vertex indices
    boardVertexIndexBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, boardVertexIndexBuffer);
    gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(boardVertexIndices), gl.STATIC_DRAW);
    boardVertexIndexBuffer.itemSize = 1;
    boardVertexIndexBuffer.numItems = 36;
}

function initBuffersSlots() {
    // Reinitialize buffers
    slotsVertexPositionBuffer = [];
    slotsVertexIndexBuffer = [];
    slotsVertexColorBuffer = [];

    for (var i = 0; i < 8; i++) {			                    // For each line (we have 8 x 8 = 8^2 slots)
        var line = slots[i];

        for (var j = 0; j < line.length; j++) {					// For each column
            // Slot
            var slot = line[j];

            // Coordinates
            var vertices = slot.getVertices();
            var colors = slot.getColors();
            var slotVertexIndices = slot.getVerticesIndexes();

            var slotVertexPositionBuffer = gl.createBuffer();
            gl.bindBuffer(gl.ARRAY_BUFFER, slotVertexPositionBuffer);
            gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertices), gl.STATIC_DRAW);
            slotVertexPositionBuffer.itemSize = 3;
            slotVertexPositionBuffer.numItems = vertices.length / 3;

            // Colors
            var slotVertexColorBuffer = gl.createBuffer();
            gl.bindBuffer(gl.ARRAY_BUFFER, slotVertexColorBuffer);
            gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(colors), gl.STATIC_DRAW);
            slotVertexColorBuffer.itemSize = 3;
            slotVertexColorBuffer.numItems = vertices.length / 3;

            // Vertex indices
            var slotVertexIndexBuffer = gl.createBuffer();
            gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, slotVertexIndexBuffer);
            gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(slotVertexIndices), gl.STATIC_DRAW);
            slotVertexIndexBuffer.itemSize = 1;
            slotVertexIndexBuffer.numItems = 36;

            slotsVertexPositionBuffer.push(slotVertexPositionBuffer);
            slotsVertexColorBuffer.push(slotVertexColorBuffer);
            slotsVertexIndexBuffer.push(slotVertexIndexBuffer);

        }
    }
}

function initBuffersDraughts() {
    draughtsVertexPositionBuffer = [];
    draughtsVertexIndexBuffer = [];
    draughtsVertexColorBuffer = [];

    for (var i = 0; i < 24; i++) {
        // Slot
        var draught = draughts[i];

        // Coordinates
        var vertices = draught.getVertices();
        var colors = draught.getColors();
        var draughtVertexIndices = draught.getVerticesIndexes();

        var draughtVertexPositionBuffer = gl.createBuffer();
        gl.bindBuffer(gl.ARRAY_BUFFER, draughtVertexPositionBuffer);
        gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(vertices), gl.STATIC_DRAW);
        draughtVertexPositionBuffer.itemSize = 3;
        draughtVertexPositionBuffer.numItems = vertices.length / 3;

        // Colors
        var draughtVertexColorBuffer = gl.createBuffer();
        gl.bindBuffer(gl.ARRAY_BUFFER, draughtVertexColorBuffer);
        gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(colors), gl.STATIC_DRAW);
        draughtVertexColorBuffer.itemSize = 3;
        draughtVertexColorBuffer.numItems = vertices.length / 3;

        // Vertex indices
        var draughtVertexIndexBuffer = gl.createBuffer();
        gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, draughtVertexIndexBuffer);
        gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(draughtVertexIndices), gl.STATIC_DRAW);
        draughtVertexIndexBuffer.itemSize = 1;
        draughtVertexIndexBuffer.numItems = 3072;

        // Update draughts info
        draughtsVertexPositionBuffer.push(draughtVertexPositionBuffer);
        draughtsVertexColorBuffer.push(draughtVertexColorBuffer);
        draughtsVertexIndexBuffer.push(draughtVertexIndexBuffer);
    }
}