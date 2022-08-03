class Board {

    constructor(boardS = 10.0, boardH = 0.5, slotS = 1.0, slotH = 0.2, margin = 0.001) {
        if (boardS < 10 * slotS) {
            boardS = 10 * slotS;
        }
        if (boardH < slotH) {
            boardH = slotH;
        }
        // Material
        this.materialConstants = materials.GOLD;
        this.kAmbi = this.materialConstants.slice(0, 3);
        this.kDiff = this.materialConstants.slice(3, 6);
        this.kSpec = this.materialConstants.slice(6, 9);
        this.nPhong = this.materialConstants[9];

        // Slots
        this.slotDraughtDic = new Array();
        var baseVal = -3.5 * slotS;
        var x = baseVal;
        var z = baseVal;
        var colorBool = true;
        this.slots = new Array(8);
        for (var i = 0; i < this.slots.length; i++) {
            this.slots[i] = new Array(8);
            for (var j = 0; j < this.slots[i].length; j++) {
                this.slots[i][j] = new Slot(colorBool, x, 0, z, slotS, slotH);
                this.slotDraughtDic[8 * i + j] = null;
                colorBool = !colorBool;
                z++;
            }
            colorBool = !colorBool;
            x++;
            z = baseVal;
        }
        this.selectedSlot = [];
        this.overSlot = null;

        // Draugths
        const blackTeamStartPositions = [[1, 0], [3, 0], [5, 0], [7, 0],
            [0, 1], [2, 1], [4, 1], [6, 1],
            [1, 2], [3, 2], [5, 2], [7, 2]];
        const whiteTeamStartPositions = [[0, 7], [2, 7], [4, 7], [6, 7],
            [1, 6], [3, 6], [5, 6], [7, 6],
            [0, 5], [2, 5], [4, 5], [6, 5]];
        this.draughts = [];

        for (var i = 0; i < blackTeamStartPositions.length; i++) {
            var j = blackTeamStartPositions[i][0];
            var k = blackTeamStartPositions[i][1];
            var coords = this.slots[j][k].getCoords();

            this.draughts[i] = new Draught(false, coords[0], coords[1], coords[2]);
            this.slotDraughtDic[8 * j + k] = this.draughts[i];
        }
        for (var i = 0; i < whiteTeamStartPositions.length; i++) {
            var j = whiteTeamStartPositions[i][0];
            var k = whiteTeamStartPositions[i][1];
            var coords = this.slots[j][k].getCoords();

            this.draughts[i + blackTeamStartPositions.length] = new Draught(true, coords[0], coords[1], coords[2]);
            this.slotDraughtDic[8 * j + k] = this.draughts[i + blackTeamStartPositions.length];
        }

        // Captured Draughts
        this.capturedStack = {true: [], false: []};
        this.capturedStackBaseLocation = {
            true: [3.5 * slotS + (boardS - 8 * slotS) / 2, -margin, 3.5 * slotS],
            false: [-3.5 * slotS - (boardS - 8 * slotS) / 2, -margin, -3.5 * slotS]
        };


        this.vertices = [-boardS / 2, -margin - boardH, -boardS / 2,
            -boardS / 2, -margin - boardH, boardS / 2,
            -boardS / 2, -margin, -boardS / 2,
            -boardS / 2, -margin, boardS / 2,
            boardS / 2, -margin - boardH, -boardS / 2,
            boardS / 2, -margin - boardH, boardS / 2,
            boardS / 2, -margin, -boardS / 2,
            boardS / 2, -margin, boardS / 2];

        this.vertexIndices = [1, 5, 7, 1, 7, 3,	// Front	(1, 3, 5, 7)
            2, 6, 4, 0, 2, 4,	// Back		(0, 2, 4, 6)
            3, 7, 6, 2, 3, 6,	// Top		(2, 3, 6, 7)
            0, 4, 5, 0, 5, 1,	// Bottom	(0, 1, 4, 5)
            4, 6, 5, 5, 6, 7,	// Right	(4, 5, 6, 7)
            0, 1, 3, 0, 3, 2];	// Left		(0, 1, 2, 3)


        // Computing the triangle normal vector for every vertex
        this.normals = [];
        computeVertexNormals(this.vertices, this.normals);

        this.colors = [];
        var length = this.vertices.length;
        for (var i = 0; i < length; i += 9) {
            this.colors.push(86 / 255);
            this.colors.push(33 / 255);
            this.colors.push(11 / 255);

            this.colors.push(137 / 255);
            this.colors.push(57 / 255);
            this.colors.push(11 / 255);

            this.colors.push(150 / 255);
            this.colors.push(66 / 255);
            this.colors.push(11 / 255);
        }

        this.currentTeam = true;

        this.gameOver = false;
    }

    // Getters
    getVertices() {
        return this.vertices;
    }

    getVerticesIndexes() {
        return this.vertexIndices;
    }

    getVertexNormals() {
        return this.normals;
    }

    getSlotVertices() {
        var verticesArray = new Array();
        for (var i = 0; i < slots.length; i++) {
            for (var j = 0; j < slots[i].length; j++) {
                retVal.push(slots[i][j].getVertices());
            }
        }
        return verticesArray;
    }

    getSlots() {
        return this.slots;
    }

    isPlayablePosition(x, z) {
        return (x + z) % 2 == 1;
    }

    getDraughts() {
        return this.draughts;
    }

    getNumberOfDraughts() {
        return this.draughts.length;
    }

    getNumberOfSlots() {
        return this.slots.length * this.slots[0].length;
    }

    getColors() {
        return this.colors;
    }

    getSelectedSlot() {
        return this.selectedSlot;
    }

    getSelectedSlotObject() {
        return this.slots[this.selectedSlot[0]][this.selectedSlot[1]];
    }

    getOverSlot() {
        return this.overSlot;
    }

    // Playing Logic
    isValidPosition(pos) {
        return (pos.length == 2 && pos[0] >= 0 && pos[0] <= 7 && pos[1] >= 0 && pos[1] <= 7);
    }

    isValidPlay(posI, posF, team) {
        console.log(posI);
        console.log(posF);
        console.log(team);
        if (posI[0] == posF[0] && posI[1] == posF[1]) {
            console.log("1");
            return false;
        }
        if (this.isValidPosition(posI) && this.isValidPosition(posF)) {
            if (this.slotDraughtDic[8 * posI[0] + posI[1]].getTeam() == team && this.slotDraughtDic[8 * posF[0] + posF[1]] === null) {
                if (team) {
                    // Advance
                    if (posF[1] == posI[1] - 1) {
                        return (posF[0] == posI[0] - 1 || posF[0] == posI[0] + 1);
                    }
                    // Capture
                    else if (posF[1] == posI[1] - 2) {
                        if (posF[0] == posI[0] - 2) {
                            return this.slotDraughtDic[8 * (posI[0] - 1) + posI[1] - 1].getTeam() == (!team);
                        } else if (posF[0] == posI[0] + 2) {
                            return this.slotDraughtDic[8 * (posI[0] + 1) + posI[1] - 1].getTeam() == (!team);
                        }
                    }
                    return false;
                } else {
                    // Advance
                    if (posF[1] == posI[1] + 1) {
                        return (posF[0] == posI[0] - 1 || posF[0] == posI[0] + 1);
                    }
                    // Capture
                    else if (posF[1] == posI[1] + 2) {
                        if (posF[0] == posI[0] - 2) {
                            return this.slotDraughtDic[8 * (posI[0] - 1) + posI[1] + 1].getTeam() == (!team);
                        } else if (posF[0] == posI[0] + 2) {
                            return this.slotDraughtDic[8 * (posI[0] + 1) + posI[1] + 1].getTeam() == (!team);
                        }
                    }
                    return false;
                }
            }
            return false;
        }
        return false;
    }

    play(posI, posF, team) {
        // Get initial and final slot
        var posFSlot = this.slots[posF[0]][posF[1]];

        // Update draught information
        var draught = this.slotDraughtDic[8 * posI[0] + posI[1]];
        var currentCoords = draught.getCoords();
        var newCoords = posFSlot.getCoords();
        draught.setCoords(newCoords);
        console.log("newCoords= " + newCoords);

        // Compute tx, ty, tz of the draught
        var diffCoords = [];
        diffCoords.push(newCoords[0] - currentCoords[0]);
        diffCoords.push(newCoords[1] - currentCoords[1]);
        diffCoords.push(newCoords[2] - currentCoords[2]);
        draught.setDiffCoords(diffCoords);
        console.log("diffCoords= " + diffCoords);

        // Update slot -> draught dictionary
        this.slotDraughtDic[8 * posF[0] + posF[1]] = this.slotDraughtDic[8 * posI[0] + posI[1]];
        this.slotDraughtDic[8 * posI[0] + posI[1]] = null;

        // Capture
        if (posF[0] == posI[0] - 2 || posF[0] == posI[0] + 2) {
            // Retrieve captured Draught reference
            var capturedDraught = this.slotDraughtDic[4 * (posI[0] + posF[0]) + (posI[1] + posF[1]) / 2];

            // Update draught coordinates
            var capturedCurrentCoords = capturedDraught.getCoords();
            var stackCoords = [this.capturedStackBaseLocation[team][0], this.capturedStackBaseLocation[team][1], this.capturedStackBaseLocation[team][2]];
            console.log(this.capturedStackBaseLocation);
            console.log("stackCoords= " + stackCoords);
            var capturedNewCoords = [this.capturedStackBaseLocation[team][0], this.capturedStackBaseLocation[team][1] + this.capturedStack[team].length * capturedDraught.getHeight(), this.capturedStackBaseLocation[team][2]];
            capturedDraught.setCoords(capturedNewCoords);
            capturedDraught.setDiffCoords([capturedNewCoords[0] - capturedCurrentCoords[0], capturedNewCoords[1] - capturedCurrentCoords[1], capturedNewCoords[2] - capturedCurrentCoords[2]]);
            console.log("capturedNewCoords= " + capturedNewCoords);
            console.log("capturedDiffCoords= " + [capturedNewCoords[0] - capturedCurrentCoords[0], capturedNewCoords[1] - capturedCurrentCoords[1], capturedNewCoords[2] - capturedCurrentCoords[2]]);

            // Insert draught in stack
            this.capturedStack[team].push(capturedDraught);

            // Remove captured
            this.slotDraughtDic[4 * (posI[0] + posF[0]) + (posI[1] + posF[1]) / 2] = null;
        }
        this.currentTeam = !this.currentTeam;
        this.gameOver = this.capturedStack[true].length == 12 || this.capturedStack[false].length == 12;
        // To make visible any changes in the draughts
        initBuffersDraughts();
    }

    // Slots Logic
    moveOverRight() {
        if (this.overSlot === null) {
            this.overSlot = [0, 7];
        } else {
            this.resetOverSlot();
            this.overSlot[0] = (this.overSlot[0] + 1) % 8;
        }

        this.selectOverSlot();
    }

    moveOverLeft() {
        if (this.overSlot === null) {
            this.overSlot = [0, 7];
        } else {
            this.resetOverSlot();
            this.overSlot[0] = (this.overSlot[0] + 7) % 8;
        }

        this.selectOverSlot();
    }

    moveOverDown() {
        if (this.overSlot === null) {
            this.overSlot = [0, 7];
        } else {
            this.resetOverSlot();
            this.overSlot[1] = (this.overSlot[1] + 1) % 8;
        }

        this.selectOverSlot();
    }

    moveOverUp() {
        if (this.overSlot === null) {
            this.overSlot = [0, 7];
        } else {
            this.resetOverSlot();
            this.overSlot[1] = (this.overSlot[1] + 7) % 8;
        }

        this.selectOverSlot();
    }

    selectOverSlot() {
        // obtain the slot
        var x = this.overSlot[0];
        var y = this.overSlot[1];
        var slot = this.slots[x][y];

        // change the color
        slot.setOverColors();
        initBuffersSlots();
    }

    resetOverSlot() {
        if (this.overSlot[0] != this.selectedSlot[0] || this.overSlot[1] != this.selectedSlot[1]) {
            // obtain the slot
            var x = this.overSlot[0];
            var y = this.overSlot[1];
            var slot = this.slots[x][y];

            // change the color
            slot.resetColors();
            initBuffersSlots();
        }
    }

    selectSlot() {
        if (this.overSlot != null) {
            if (this.selectedSlot == 0) {
                // obtain coordinates of the slot
                var x = this.overSlot[0];
                var z = this.overSlot[1];

                // verify if has a draught and if it's the correct team
                if (this.slotDraughtDic[8 * x + z] === null || this.slotDraughtDic[8 * x + z].getTeam() !== this.currentTeam) {
                    return;
                }

                // Select the slot (selected slot = over slot)
                this.selectedSlot = [];
                this.selectedSlot[0] = this.overSlot[0];
                this.selectedSlot[1] = this.overSlot[1];

                // obtain the slot
                var slot = this.slots[x][z];

                // change the slot color
                slot.setSelectedColors();
                initBuffersSlots();
            } else {
                if (this.isValidPlay(this.selectedSlot, this.overSlot, this.currentTeam)) {
                    console.log("Valid play");
                    this.play(this.selectedSlot, this.overSlot, this.currentTeam);
                    this.deselectSlot();
                } else {
                    console.log("Invalid play");
                }
            }
        }
    }

    deselectSlot() {
        // Deselect the slot and change its color
        // obtain the slot
        if (this.selectedSlot == []) {
            return;
        }
        var x = this.selectedSlot[0];
        var y = this.selectedSlot[1];
        var slot = this.slots[x][y];

        // change the color
        slot.resetColors();
        initBuffersSlots();

        // reset the selected slot
        this.selectedSlot = [];
    }

    // Green:	124,252,0
    // Red:		255,69,0

    // Game info
    getGameOver() {
        return this.gameOver;
    }

    getWinningTeam() {
        if (this.capturedStack[true].length == 12) return "Team 1";
        return "Team 2";
    }

    getScores() {
        return [this.capturedStack[true].length, this.capturedStack[false].length]
    }

    changeTeamPieces(team, color) {
        for (var i = 0; i < this.draughts.length; i++) {
            if (this.draughts[i].getTeam() === team) {
                // change color
                this.draughts[i].setColor(color);
            }
        }

    }

    getMaterial() {
        return [this.kAmbi, this.kDiff, this.kSpec, this.nPhong];
    }

}

class Slot {
    // colorBool is boolean for there are two and only two colors
    // x, y and z are idCoord (center of top face)
    // s is side
    // h is height
    constructor(color, x, y = 0.0, z, s = 1.0, h = 0.5) {
        this.colorBool = color;
        this.idCoords = [x, y, z];
        this.vertices = [x - s / 2, y - h, z - s / 2,
            x - s / 2, y - h, z + s / 2,
            x - s / 2, y, z - s / 2,
            x - s / 2, y, z + s / 2,
            x + s / 2, y - h, z - s / 2,
            x + s / 2, y - h, z + s / 2,
            x + s / 2, y, z - s / 2,
            x + s / 2, y, z + s / 2];
        // Index logic:
        //	--,-+,++
        //	++,+-,--
        this.vertexIndices = [1, 5, 7, 1, 7, 3,	// Front	(1, 3, 5, 7)
            2, 6, 4, 0, 2, 4,	// Back		(0, 2, 4, 6)
            3, 7, 6, 2, 3, 6,	// Top		(2, 3, 6, 7)
            0, 4, 5, 0, 5, 1,	// Bottom	(0, 1, 4, 5)
            4, 6, 5, 5, 6, 7,	// Right	(4, 5, 6, 7)
            0, 1, 3, 0, 3, 2];	// Left		(0, 1, 2, 3)

        this.colors = [];
        this.resetColors();

        // green 124 252 0 -> 0.48, 0.98, 0
        // red 255, 69, 0 -> 1, 0.27, 0
    }

    getCoords() {
        return this.idCoords;
    }

    getColorBool() {
        return this.colorBool;
    }

    getVertices() {
        return this.vertices;
    }

    getVerticesIndexes() {
        return this.vertexIndices;
    }

    getColors() {
        return this.colors;
    }

    setSelectedColors() {
        console.log("before" + this.colors);
        for (var i = 0; i < this.colors.length; i += 3) {
            this.colors[i] = 1.0;
            this.colors[i + 1] = 0.27;
            this.colors[i + 2] = 0;
        }
        console.log("after" + this.colors);
    }

    setOverColors() {
        if (this.colors[0] != 1 || this.colors[1] != 0.27 || this.colors[2] != 0) {
            for (var i = 0; i < this.colors.length; i += 3) {
                this.colors[i] = 0.48;
                this.colors[i + 1] = 0.98;
                this.colors[i + 2] = 0;
            }
        }
    }

    resetColors() {
        var color = 0.2;
        if (this.colorBool) {
            color = 0.80;
        }

        var length = this.vertices.length;
        for (var i = 0; i < length; i++) {
            this.colors[i] = color;
        }
    }
}

class Draught {

    constructor(team, x, y, z, r = 0.4, h = 0.5) {
        this.height = h;
        this.team = team;
        this.idCoords = [x, y, z];
        this.setVertices(x, y, z);
        this.vertexIndices = [
            1, 3, 513,
            0, 2, 512,
            0, 1, 3,
            0, 2, 3,
            3, 5, 513,
            2, 4, 512,
            2, 3, 5,
            2, 4, 5,
            5, 7, 513,
            4, 6, 512,
            4, 5, 7,
            4, 6, 7,
            7, 9, 513,
            6, 8, 512,
            6, 7, 9,
            6, 8, 9,
            9, 11, 513,
            8, 10, 512,
            8, 9, 11,
            8, 10, 11,
            11, 13, 513,
            10, 12, 512,
            10, 11, 13,
            10, 12, 13,
            13, 15, 513,
            12, 14, 512,
            12, 13, 15,
            12, 14, 15,
            15, 17, 513,
            14, 16, 512,
            14, 15, 17,
            14, 16, 17,
            17, 19, 513,
            16, 18, 512,
            16, 17, 19,
            16, 18, 19,
            19, 21, 513,
            18, 20, 512,
            18, 19, 21,
            18, 20, 21,
            21, 23, 513,
            20, 22, 512,
            20, 21, 23,
            20, 22, 23,
            23, 25, 513,
            22, 24, 512,
            22, 23, 25,
            22, 24, 25,
            25, 27, 513,
            24, 26, 512,
            24, 25, 27,
            24, 26, 27,
            27, 29, 513,
            26, 28, 512,
            26, 27, 29,
            26, 28, 29,
            29, 31, 513,
            28, 30, 512,
            28, 29, 31,
            28, 30, 31,
            31, 33, 513,
            30, 32, 512,
            30, 31, 33,
            30, 32, 33,
            33, 35, 513,
            32, 34, 512,
            32, 33, 35,
            32, 34, 35,
            35, 37, 513,
            34, 36, 512,
            34, 35, 37,
            34, 36, 37,
            37, 39, 513,
            36, 38, 512,
            36, 37, 39,
            36, 38, 39,
            39, 41, 513,
            38, 40, 512,
            38, 39, 41,
            38, 40, 41,
            41, 43, 513,
            40, 42, 512,
            40, 41, 43,
            40, 42, 43,
            43, 45, 513,
            42, 44, 512,
            42, 43, 45,
            42, 44, 45,
            45, 47, 513,
            44, 46, 512,
            44, 45, 47,
            44, 46, 47,
            47, 49, 513,
            46, 48, 512,
            46, 47, 49,
            46, 48, 49,
            49, 51, 513,
            48, 50, 512,
            48, 49, 51,
            48, 50, 51,
            51, 53, 513,
            50, 52, 512,
            50, 51, 53,
            50, 52, 53,
            53, 55, 513,
            52, 54, 512,
            52, 53, 55,
            52, 54, 55,
            55, 57, 513,
            54, 56, 512,
            54, 55, 57,
            54, 56, 57,
            57, 59, 513,
            56, 58, 512,
            56, 57, 59,
            56, 58, 59,
            59, 61, 513,
            58, 60, 512,
            58, 59, 61,
            58, 60, 61,
            61, 63, 513,
            60, 62, 512,
            60, 61, 63,
            60, 62, 63,
            63, 65, 513,
            62, 64, 512,
            62, 63, 65,
            62, 64, 65,
            65, 67, 513,
            64, 66, 512,
            64, 65, 67,
            64, 66, 67,
            67, 69, 513,
            66, 68, 512,
            66, 67, 69,
            66, 68, 69,
            69, 71, 513,
            68, 70, 512,
            68, 69, 71,
            68, 70, 71,
            71, 73, 513,
            70, 72, 512,
            70, 71, 73,
            70, 72, 73,
            73, 75, 513,
            72, 74, 512,
            72, 73, 75,
            72, 74, 75,
            75, 77, 513,
            74, 76, 512,
            74, 75, 77,
            74, 76, 77,
            77, 79, 513,
            76, 78, 512,
            76, 77, 79,
            76, 78, 79,
            79, 81, 513,
            78, 80, 512,
            78, 79, 81,
            78, 80, 81,
            81, 83, 513,
            80, 82, 512,
            80, 81, 83,
            80, 82, 83,
            83, 85, 513,
            82, 84, 512,
            82, 83, 85,
            82, 84, 85,
            85, 87, 513,
            84, 86, 512,
            84, 85, 87,
            84, 86, 87,
            87, 89, 513,
            86, 88, 512,
            86, 87, 89,
            86, 88, 89,
            89, 91, 513,
            88, 90, 512,
            88, 89, 91,
            88, 90, 91,
            91, 93, 513,
            90, 92, 512,
            90, 91, 93,
            90, 92, 93,
            93, 95, 513,
            92, 94, 512,
            92, 93, 95,
            92, 94, 95,
            95, 97, 513,
            94, 96, 512,
            94, 95, 97,
            94, 96, 97,
            97, 99, 513,
            96, 98, 512,
            96, 97, 99,
            96, 98, 99,
            99, 101, 513,
            98, 100, 512,
            98, 99, 101,
            98, 100, 101,
            101, 103, 513,
            100, 102, 512,
            100, 101, 103,
            100, 102, 103,
            103, 105, 513,
            102, 104, 512,
            102, 103, 105,
            102, 104, 105,
            105, 107, 513,
            104, 106, 512,
            104, 105, 107,
            104, 106, 107,
            107, 109, 513,
            106, 108, 512,
            106, 107, 109,
            106, 108, 109,
            109, 111, 513,
            108, 110, 512,
            108, 109, 111,
            108, 110, 111,
            111, 113, 513,
            110, 112, 512,
            110, 111, 113,
            110, 112, 113,
            113, 115, 513,
            112, 114, 512,
            112, 113, 115,
            112, 114, 115,
            115, 117, 513,
            114, 116, 512,
            114, 115, 117,
            114, 116, 117,
            117, 119, 513,
            116, 118, 512,
            116, 117, 119,
            116, 118, 119,
            119, 121, 513,
            118, 120, 512,
            118, 119, 121,
            118, 120, 121,
            121, 123, 513,
            120, 122, 512,
            120, 121, 123,
            120, 122, 123,
            123, 125, 513,
            122, 124, 512,
            122, 123, 125,
            122, 124, 125,
            125, 127, 513,
            124, 126, 512,
            124, 125, 127,
            124, 126, 127,
            127, 129, 513,
            126, 128, 512,
            126, 127, 129,
            126, 128, 129,
            129, 131, 513,
            128, 130, 512,
            128, 129, 131,
            128, 130, 131,
            131, 133, 513,
            130, 132, 512,
            130, 131, 133,
            130, 132, 133,
            133, 135, 513,
            132, 134, 512,
            132, 133, 135,
            132, 134, 135,
            135, 137, 513,
            134, 136, 512,
            134, 135, 137,
            134, 136, 137,
            137, 139, 513,
            136, 138, 512,
            136, 137, 139,
            136, 138, 139,
            139, 141, 513,
            138, 140, 512,
            138, 139, 141,
            138, 140, 141,
            141, 143, 513,
            140, 142, 512,
            140, 141, 143,
            140, 142, 143,
            143, 145, 513,
            142, 144, 512,
            142, 143, 145,
            142, 144, 145,
            145, 147, 513,
            144, 146, 512,
            144, 145, 147,
            144, 146, 147,
            147, 149, 513,
            146, 148, 512,
            146, 147, 149,
            146, 148, 149,
            149, 151, 513,
            148, 150, 512,
            148, 149, 151,
            148, 150, 151,
            151, 153, 513,
            150, 152, 512,
            150, 151, 153,
            150, 152, 153,
            153, 155, 513,
            152, 154, 512,
            152, 153, 155,
            152, 154, 155,
            155, 157, 513,
            154, 156, 512,
            154, 155, 157,
            154, 156, 157,
            157, 159, 513,
            156, 158, 512,
            156, 157, 159,
            156, 158, 159,
            159, 161, 513,
            158, 160, 512,
            158, 159, 161,
            158, 160, 161,
            161, 163, 513,
            160, 162, 512,
            160, 161, 163,
            160, 162, 163,
            163, 165, 513,
            162, 164, 512,
            162, 163, 165,
            162, 164, 165,
            165, 167, 513,
            164, 166, 512,
            164, 165, 167,
            164, 166, 167,
            167, 169, 513,
            166, 168, 512,
            166, 167, 169,
            166, 168, 169,
            169, 171, 513,
            168, 170, 512,
            168, 169, 171,
            168, 170, 171,
            171, 173, 513,
            170, 172, 512,
            170, 171, 173,
            170, 172, 173,
            173, 175, 513,
            172, 174, 512,
            172, 173, 175,
            172, 174, 175,
            175, 177, 513,
            174, 176, 512,
            174, 175, 177,
            174, 176, 177,
            177, 179, 513,
            176, 178, 512,
            176, 177, 179,
            176, 178, 179,
            179, 181, 513,
            178, 180, 512,
            178, 179, 181,
            178, 180, 181,
            181, 183, 513,
            180, 182, 512,
            180, 181, 183,
            180, 182, 183,
            183, 185, 513,
            182, 184, 512,
            182, 183, 185,
            182, 184, 185,
            185, 187, 513,
            184, 186, 512,
            184, 185, 187,
            184, 186, 187,
            187, 189, 513,
            186, 188, 512,
            186, 187, 189,
            186, 188, 189,
            189, 191, 513,
            188, 190, 512,
            188, 189, 191,
            188, 190, 191,
            191, 193, 513,
            190, 192, 512,
            190, 191, 193,
            190, 192, 193,
            193, 195, 513,
            192, 194, 512,
            192, 193, 195,
            192, 194, 195,
            195, 197, 513,
            194, 196, 512,
            194, 195, 197,
            194, 196, 197,
            197, 199, 513,
            196, 198, 512,
            196, 197, 199,
            196, 198, 199,
            199, 201, 513,
            198, 200, 512,
            198, 199, 201,
            198, 200, 201,
            201, 203, 513,
            200, 202, 512,
            200, 201, 203,
            200, 202, 203,
            203, 205, 513,
            202, 204, 512,
            202, 203, 205,
            202, 204, 205,
            205, 207, 513,
            204, 206, 512,
            204, 205, 207,
            204, 206, 207,
            207, 209, 513,
            206, 208, 512,
            206, 207, 209,
            206, 208, 209,
            209, 211, 513,
            208, 210, 512,
            208, 209, 211,
            208, 210, 211,
            211, 213, 513,
            210, 212, 512,
            210, 211, 213,
            210, 212, 213,
            213, 215, 513,
            212, 214, 512,
            212, 213, 215,
            212, 214, 215,
            215, 217, 513,
            214, 216, 512,
            214, 215, 217,
            214, 216, 217,
            217, 219, 513,
            216, 218, 512,
            216, 217, 219,
            216, 218, 219,
            219, 221, 513,
            218, 220, 512,
            218, 219, 221,
            218, 220, 221,
            221, 223, 513,
            220, 222, 512,
            220, 221, 223,
            220, 222, 223,
            223, 225, 513,
            222, 224, 512,
            222, 223, 225,
            222, 224, 225,
            225, 227, 513,
            224, 226, 512,
            224, 225, 227,
            224, 226, 227,
            227, 229, 513,
            226, 228, 512,
            226, 227, 229,
            226, 228, 229,
            229, 231, 513,
            228, 230, 512,
            228, 229, 231,
            228, 230, 231,
            231, 233, 513,
            230, 232, 512,
            230, 231, 233,
            230, 232, 233,
            233, 235, 513,
            232, 234, 512,
            232, 233, 235,
            232, 234, 235,
            235, 237, 513,
            234, 236, 512,
            234, 235, 237,
            234, 236, 237,
            237, 239, 513,
            236, 238, 512,
            236, 237, 239,
            236, 238, 239,
            239, 241, 513,
            238, 240, 512,
            238, 239, 241,
            238, 240, 241,
            241, 243, 513,
            240, 242, 512,
            240, 241, 243,
            240, 242, 243,
            243, 245, 513,
            242, 244, 512,
            242, 243, 245,
            242, 244, 245,
            245, 247, 513,
            244, 246, 512,
            244, 245, 247,
            244, 246, 247,
            247, 249, 513,
            246, 248, 512,
            246, 247, 249,
            246, 248, 249,
            249, 251, 513,
            248, 250, 512,
            248, 249, 251,
            248, 250, 251,
            251, 253, 513,
            250, 252, 512,
            250, 251, 253,
            250, 252, 253,
            253, 255, 513,
            252, 254, 512,
            252, 253, 255,
            252, 254, 255,
            255, 257, 513,
            254, 256, 512,
            254, 255, 257,
            254, 256, 257,
            257, 259, 513,
            256, 258, 512,
            256, 257, 259,
            256, 258, 259,
            259, 261, 513,
            258, 260, 512,
            258, 259, 261,
            258, 260, 261,
            261, 263, 513,
            260, 262, 512,
            260, 261, 263,
            260, 262, 263,
            263, 265, 513,
            262, 264, 512,
            262, 263, 265,
            262, 264, 265,
            265, 267, 513,
            264, 266, 512,
            264, 265, 267,
            264, 266, 267,
            267, 269, 513,
            266, 268, 512,
            266, 267, 269,
            266, 268, 269,
            269, 271, 513,
            268, 270, 512,
            268, 269, 271,
            268, 270, 271,
            271, 273, 513,
            270, 272, 512,
            270, 271, 273,
            270, 272, 273,
            273, 275, 513,
            272, 274, 512,
            272, 273, 275,
            272, 274, 275,
            275, 277, 513,
            274, 276, 512,
            274, 275, 277,
            274, 276, 277,
            277, 279, 513,
            276, 278, 512,
            276, 277, 279,
            276, 278, 279,
            279, 281, 513,
            278, 280, 512,
            278, 279, 281,
            278, 280, 281,
            281, 283, 513,
            280, 282, 512,
            280, 281, 283,
            280, 282, 283,
            283, 285, 513,
            282, 284, 512,
            282, 283, 285,
            282, 284, 285,
            285, 287, 513,
            284, 286, 512,
            284, 285, 287,
            284, 286, 287,
            287, 289, 513,
            286, 288, 512,
            286, 287, 289,
            286, 288, 289,
            289, 291, 513,
            288, 290, 512,
            288, 289, 291,
            288, 290, 291,
            291, 293, 513,
            290, 292, 512,
            290, 291, 293,
            290, 292, 293,
            293, 295, 513,
            292, 294, 512,
            292, 293, 295,
            292, 294, 295,
            295, 297, 513,
            294, 296, 512,
            294, 295, 297,
            294, 296, 297,
            297, 299, 513,
            296, 298, 512,
            296, 297, 299,
            296, 298, 299,
            299, 301, 513,
            298, 300, 512,
            298, 299, 301,
            298, 300, 301,
            301, 303, 513,
            300, 302, 512,
            300, 301, 303,
            300, 302, 303,
            303, 305, 513,
            302, 304, 512,
            302, 303, 305,
            302, 304, 305,
            305, 307, 513,
            304, 306, 512,
            304, 305, 307,
            304, 306, 307,
            307, 309, 513,
            306, 308, 512,
            306, 307, 309,
            306, 308, 309,
            309, 311, 513,
            308, 310, 512,
            308, 309, 311,
            308, 310, 311,
            311, 313, 513,
            310, 312, 512,
            310, 311, 313,
            310, 312, 313,
            313, 315, 513,
            312, 314, 512,
            312, 313, 315,
            312, 314, 315,
            315, 317, 513,
            314, 316, 512,
            314, 315, 317,
            314, 316, 317,
            317, 319, 513,
            316, 318, 512,
            316, 317, 319,
            316, 318, 319,
            319, 321, 513,
            318, 320, 512,
            318, 319, 321,
            318, 320, 321,
            321, 323, 513,
            320, 322, 512,
            320, 321, 323,
            320, 322, 323,
            323, 325, 513,
            322, 324, 512,
            322, 323, 325,
            322, 324, 325,
            325, 327, 513,
            324, 326, 512,
            324, 325, 327,
            324, 326, 327,
            327, 329, 513,
            326, 328, 512,
            326, 327, 329,
            326, 328, 329,
            329, 331, 513,
            328, 330, 512,
            328, 329, 331,
            328, 330, 331,
            331, 333, 513,
            330, 332, 512,
            330, 331, 333,
            330, 332, 333,
            333, 335, 513,
            332, 334, 512,
            332, 333, 335,
            332, 334, 335,
            335, 337, 513,
            334, 336, 512,
            334, 335, 337,
            334, 336, 337,
            337, 339, 513,
            336, 338, 512,
            336, 337, 339,
            336, 338, 339,
            339, 341, 513,
            338, 340, 512,
            338, 339, 341,
            338, 340, 341,
            341, 343, 513,
            340, 342, 512,
            340, 341, 343,
            340, 342, 343,
            343, 345, 513,
            342, 344, 512,
            342, 343, 345,
            342, 344, 345,
            345, 347, 513,
            344, 346, 512,
            344, 345, 347,
            344, 346, 347,
            347, 349, 513,
            346, 348, 512,
            346, 347, 349,
            346, 348, 349,
            349, 351, 513,
            348, 350, 512,
            348, 349, 351,
            348, 350, 351,
            351, 353, 513,
            350, 352, 512,
            350, 351, 353,
            350, 352, 353,
            353, 355, 513,
            352, 354, 512,
            352, 353, 355,
            352, 354, 355,
            355, 357, 513,
            354, 356, 512,
            354, 355, 357,
            354, 356, 357,
            357, 359, 513,
            356, 358, 512,
            356, 357, 359,
            356, 358, 359,
            359, 361, 513,
            358, 360, 512,
            358, 359, 361,
            358, 360, 361,
            361, 363, 513,
            360, 362, 512,
            360, 361, 363,
            360, 362, 363,
            363, 365, 513,
            362, 364, 512,
            362, 363, 365,
            362, 364, 365,
            365, 367, 513,
            364, 366, 512,
            364, 365, 367,
            364, 366, 367,
            367, 369, 513,
            366, 368, 512,
            366, 367, 369,
            366, 368, 369,
            369, 371, 513,
            368, 370, 512,
            368, 369, 371,
            368, 370, 371,
            371, 373, 513,
            370, 372, 512,
            370, 371, 373,
            370, 372, 373,
            373, 375, 513,
            372, 374, 512,
            372, 373, 375,
            372, 374, 375,
            375, 377, 513,
            374, 376, 512,
            374, 375, 377,
            374, 376, 377,
            377, 379, 513,
            376, 378, 512,
            376, 377, 379,
            376, 378, 379,
            379, 381, 513,
            378, 380, 512,
            378, 379, 381,
            378, 380, 381,
            381, 383, 513,
            380, 382, 512,
            380, 381, 383,
            380, 382, 383,
            383, 385, 513,
            382, 384, 512,
            382, 383, 385,
            382, 384, 385,
            385, 387, 513,
            384, 386, 512,
            384, 385, 387,
            384, 386, 387,
            387, 389, 513,
            386, 388, 512,
            386, 387, 389,
            386, 388, 389,
            389, 391, 513,
            388, 390, 512,
            388, 389, 391,
            388, 390, 391,
            391, 393, 513,
            390, 392, 512,
            390, 391, 393,
            390, 392, 393,
            393, 395, 513,
            392, 394, 512,
            392, 393, 395,
            392, 394, 395,
            395, 397, 513,
            394, 396, 512,
            394, 395, 397,
            394, 396, 397,
            397, 399, 513,
            396, 398, 512,
            396, 397, 399,
            396, 398, 399,
            399, 401, 513,
            398, 400, 512,
            398, 399, 401,
            398, 400, 401,
            401, 403, 513,
            400, 402, 512,
            400, 401, 403,
            400, 402, 403,
            403, 405, 513,
            402, 404, 512,
            402, 403, 405,
            402, 404, 405,
            405, 407, 513,
            404, 406, 512,
            404, 405, 407,
            404, 406, 407,
            407, 409, 513,
            406, 408, 512,
            406, 407, 409,
            406, 408, 409,
            409, 411, 513,
            408, 410, 512,
            408, 409, 411,
            408, 410, 411,
            411, 413, 513,
            410, 412, 512,
            410, 411, 413,
            410, 412, 413,
            413, 415, 513,
            412, 414, 512,
            412, 413, 415,
            412, 414, 415,
            415, 417, 513,
            414, 416, 512,
            414, 415, 417,
            414, 416, 417,
            417, 419, 513,
            416, 418, 512,
            416, 417, 419,
            416, 418, 419,
            419, 421, 513,
            418, 420, 512,
            418, 419, 421,
            418, 420, 421,
            421, 423, 513,
            420, 422, 512,
            420, 421, 423,
            420, 422, 423,
            423, 425, 513,
            422, 424, 512,
            422, 423, 425,
            422, 424, 425,
            425, 427, 513,
            424, 426, 512,
            424, 425, 427,
            424, 426, 427,
            427, 429, 513,
            426, 428, 512,
            426, 427, 429,
            426, 428, 429,
            429, 431, 513,
            428, 430, 512,
            428, 429, 431,
            428, 430, 431,
            431, 433, 513,
            430, 432, 512,
            430, 431, 433,
            430, 432, 433,
            433, 435, 513,
            432, 434, 512,
            432, 433, 435,
            432, 434, 435,
            435, 437, 513,
            434, 436, 512,
            434, 435, 437,
            434, 436, 437,
            437, 439, 513,
            436, 438, 512,
            436, 437, 439,
            436, 438, 439,
            439, 441, 513,
            438, 440, 512,
            438, 439, 441,
            438, 440, 441,
            441, 443, 513,
            440, 442, 512,
            440, 441, 443,
            440, 442, 443,
            443, 445, 513,
            442, 444, 512,
            442, 443, 445,
            442, 444, 445,
            445, 447, 513,
            444, 446, 512,
            444, 445, 447,
            444, 446, 447,
            447, 449, 513,
            446, 448, 512,
            446, 447, 449,
            446, 448, 449,
            449, 451, 513,
            448, 450, 512,
            448, 449, 451,
            448, 450, 451,
            451, 453, 513,
            450, 452, 512,
            450, 451, 453,
            450, 452, 453,
            453, 455, 513,
            452, 454, 512,
            452, 453, 455,
            452, 454, 455,
            455, 457, 513,
            454, 456, 512,
            454, 455, 457,
            454, 456, 457,
            457, 459, 513,
            456, 458, 512,
            456, 457, 459,
            456, 458, 459,
            459, 461, 513,
            458, 460, 512,
            458, 459, 461,
            458, 460, 461,
            461, 463, 513,
            460, 462, 512,
            460, 461, 463,
            460, 462, 463,
            463, 465, 513,
            462, 464, 512,
            462, 463, 465,
            462, 464, 465,
            465, 467, 513,
            464, 466, 512,
            464, 465, 467,
            464, 466, 467,
            467, 469, 513,
            466, 468, 512,
            466, 467, 469,
            466, 468, 469,
            469, 471, 513,
            468, 470, 512,
            468, 469, 471,
            468, 470, 471,
            471, 473, 513,
            470, 472, 512,
            470, 471, 473,
            470, 472, 473,
            473, 475, 513,
            472, 474, 512,
            472, 473, 475,
            472, 474, 475,
            475, 477, 513,
            474, 476, 512,
            474, 475, 477,
            474, 476, 477,
            477, 479, 513,
            476, 478, 512,
            476, 477, 479,
            476, 478, 479,
            479, 481, 513,
            478, 480, 512,
            478, 479, 481,
            478, 480, 481,
            481, 483, 513,
            480, 482, 512,
            480, 481, 483,
            480, 482, 483,
            483, 485, 513,
            482, 484, 512,
            482, 483, 485,
            482, 484, 485,
            485, 487, 513,
            484, 486, 512,
            484, 485, 487,
            484, 486, 487,
            487, 489, 513,
            486, 488, 512,
            486, 487, 489,
            486, 488, 489,
            489, 491, 513,
            488, 490, 512,
            488, 489, 491,
            488, 490, 491,
            491, 493, 513,
            490, 492, 512,
            490, 491, 493,
            490, 492, 493,
            493, 495, 513,
            492, 494, 512,
            492, 493, 495,
            492, 494, 495,
            495, 497, 513,
            494, 496, 512,
            494, 495, 497,
            494, 496, 497,
            497, 499, 513,
            496, 498, 512,
            496, 497, 499,
            496, 498, 499,
            499, 501, 513,
            498, 500, 512,
            498, 499, 501,
            498, 500, 501,
            501, 503, 513,
            500, 502, 512,
            500, 501, 503,
            500, 502, 503,
            503, 505, 513,
            502, 504, 512,
            502, 503, 505,
            502, 504, 505,
            505, 507, 513,
            504, 506, 512,
            504, 505, 507,
            504, 506, 507,
            507, 509, 513,
            506, 508, 512,
            506, 507, 509,
            506, 508, 509,
            509, 511, 513,
            508, 510, 512,
            508, 509, 511,
            508, 510, 511,
            511, 1, 513,
            510, 0, 512,
            510, 511, 1,
            510, 0, 1,
        ];

        this.colors = [];

        this.setColor(0);

        this.diffCoords = [0, 0, 0];
    }

    setVertices(x, y, z, r = 0.4, h = 0.5) {

        this.vertices = [x + r * 1.0, y + 0, z + r * 0.0,
            x + r * 1.0, y + h, z + r * 0.0,
            x + r * 0.9996988186962042, y + 0, z + r * 0.024541228522912288,
            x + r * 0.9996988186962042, y + h, z + r * 0.024541228522912288,
            x + r * 0.9987954562051724, y + 0, z + r * 0.049067674327418015,
            x + r * 0.9987954562051724, y + h, z + r * 0.049067674327418015,
            x + r * 0.9972904566786902, y + 0, z + r * 0.07356456359966743,
            x + r * 0.9972904566786902, y + h, z + r * 0.07356456359966743,
            x + r * 0.9951847266721969, y + 0, z + r * 0.0980171403295606,
            x + r * 0.9951847266721969, y + h, z + r * 0.0980171403295606,
            x + r * 0.99247953459871, y + 0, z + r * 0.1224106751992162,
            x + r * 0.99247953459871, y + h, z + r * 0.1224106751992162,
            x + r * 0.989176509964781, y + 0, z + r * 0.14673047445536175,
            x + r * 0.989176509964781, y + h, z + r * 0.14673047445536175,
            x + r * 0.9852776423889412, y + 0, z + r * 0.17096188876030122,
            x + r * 0.9852776423889412, y + h, z + r * 0.17096188876030122,
            x + r * 0.9807852804032304, y + 0, z + r * 0.19509032201612825,
            x + r * 0.9807852804032304, y + h, z + r * 0.19509032201612825,
            x + r * 0.9757021300385286, y + 0, z + r * 0.2191012401568698,
            x + r * 0.9757021300385286, y + h, z + r * 0.2191012401568698,
            x + r * 0.970031253194544, y + 0, z + r * 0.24298017990326387,
            x + r * 0.970031253194544, y + h, z + r * 0.24298017990326387,
            x + r * 0.9637760657954398, y + 0, z + r * 0.26671275747489837,
            x + r * 0.9637760657954398, y + h, z + r * 0.26671275747489837,
            x + r * 0.9569403357322088, y + 0, z + r * 0.29028467725446233,
            x + r * 0.9569403357322088, y + h, z + r * 0.29028467725446233,
            x + r * 0.9495281805930367, y + 0, z + r * 0.3136817403988915,
            x + r * 0.9495281805930367, y + h, z + r * 0.3136817403988915,
            x + r * 0.9415440651830208, y + 0, z + r * 0.33688985339222005,
            x + r * 0.9415440651830208, y + h, z + r * 0.33688985339222005,
            x + r * 0.932992798834739, y + 0, z + r * 0.3598950365349881,
            x + r * 0.932992798834739, y + h, z + r * 0.3598950365349881,
            x + r * 0.9238795325112867, y + 0, z + r * 0.3826834323650898,
            x + r * 0.9238795325112867, y + h, z + r * 0.3826834323650898,
            x + r * 0.9142097557035307, y + 0, z + r * 0.40524131400498986,
            x + r * 0.9142097557035307, y + h, z + r * 0.40524131400498986,
            x + r * 0.9039892931234433, y + 0, z + r * 0.4275550934302821,
            x + r * 0.9039892931234433, y + h, z + r * 0.4275550934302821,
            x + r * 0.8932243011955153, y + 0, z + r * 0.44961132965460654,
            x + r * 0.8932243011955153, y + h, z + r * 0.44961132965460654,
            x + r * 0.881921264348355, y + 0, z + r * 0.47139673682599764,
            x + r * 0.881921264348355, y + h, z + r * 0.47139673682599764,
            x + r * 0.8700869911087115, y + 0, z + r * 0.49289819222978404,
            x + r * 0.8700869911087115, y + h, z + r * 0.49289819222978404,
            x + r * 0.8577286100002721, y + 0, z + r * 0.5141027441932217,
            x + r * 0.8577286100002721, y + h, z + r * 0.5141027441932217,
            x + r * 0.8448535652497071, y + 0, z + r * 0.5349976198870972,
            x + r * 0.8448535652497071, y + h, z + r * 0.5349976198870972,
            x + r * 0.8314696123025452, y + 0, z + r * 0.5555702330196022,
            x + r * 0.8314696123025452, y + h, z + r * 0.5555702330196022,
            x + r * 0.8175848131515837, y + 0, z + r * 0.5758081914178453,
            x + r * 0.8175848131515837, y + h, z + r * 0.5758081914178453,
            x + r * 0.8032075314806449, y + 0, z + r * 0.5956993044924334,
            x + r * 0.8032075314806449, y + h, z + r * 0.5956993044924334,
            x + r * 0.7883464276266063, y + 0, z + r * 0.6152315905806268,
            x + r * 0.7883464276266063, y + h, z + r * 0.6152315905806268,
            x + r * 0.773010453362737, y + 0, z + r * 0.6343932841636455,
            x + r * 0.773010453362737, y + h, z + r * 0.6343932841636455,
            x + r * 0.7572088465064846, y + 0, z + r * 0.6531728429537768,
            x + r * 0.7572088465064846, y + h, z + r * 0.6531728429537768,
            x + r * 0.7409511253549591, y + 0, z + r * 0.6715589548470183,
            x + r * 0.7409511253549591, y + h, z + r * 0.6715589548470183,
            x + r * 0.724247082951467, y + 0, z + r * 0.6895405447370668,
            x + r * 0.724247082951467, y + h, z + r * 0.6895405447370668,
            x + r * 0.7071067811865476, y + 0, z + r * 0.7071067811865475,
            x + r * 0.7071067811865476, y + h, z + r * 0.7071067811865475,
            x + r * 0.6895405447370669, y + 0, z + r * 0.7242470829514669,
            x + r * 0.6895405447370669, y + h, z + r * 0.7242470829514669,
            x + r * 0.6715589548470183, y + 0, z + r * 0.7409511253549591,
            x + r * 0.6715589548470183, y + h, z + r * 0.7409511253549591,
            x + r * 0.6531728429537768, y + 0, z + r * 0.7572088465064845,
            x + r * 0.6531728429537768, y + h, z + r * 0.7572088465064845,
            x + r * 0.6343932841636455, y + 0, z + r * 0.773010453362737,
            x + r * 0.6343932841636455, y + h, z + r * 0.773010453362737,
            x + r * 0.6152315905806268, y + 0, z + r * 0.7883464276266062,
            x + r * 0.6152315905806268, y + h, z + r * 0.7883464276266062,
            x + r * 0.5956993044924335, y + 0, z + r * 0.8032075314806448,
            x + r * 0.5956993044924335, y + h, z + r * 0.8032075314806448,
            x + r * 0.5758081914178453, y + 0, z + r * 0.8175848131515837,
            x + r * 0.5758081914178453, y + h, z + r * 0.8175848131515837,
            x + r * 0.5555702330196023, y + 0, z + r * 0.8314696123025452,
            x + r * 0.5555702330196023, y + h, z + r * 0.8314696123025452,
            x + r * 0.5349976198870973, y + 0, z + r * 0.844853565249707,
            x + r * 0.5349976198870973, y + h, z + r * 0.844853565249707,
            x + r * 0.5141027441932217, y + 0, z + r * 0.8577286100002721,
            x + r * 0.5141027441932217, y + h, z + r * 0.8577286100002721,
            x + r * 0.4928981922297841, y + 0, z + r * 0.8700869911087113,
            x + r * 0.4928981922297841, y + h, z + r * 0.8700869911087113,
            x + r * 0.4713967368259978, y + 0, z + r * 0.8819212643483549,
            x + r * 0.4713967368259978, y + h, z + r * 0.8819212643483549,
            x + r * 0.4496113296546066, y + 0, z + r * 0.8932243011955153,
            x + r * 0.4496113296546066, y + h, z + r * 0.8932243011955153,
            x + r * 0.4275550934302822, y + 0, z + r * 0.9039892931234433,
            x + r * 0.4275550934302822, y + h, z + r * 0.9039892931234433,
            x + r * 0.40524131400498986, y + 0, z + r * 0.9142097557035307,
            x + r * 0.40524131400498986, y + h, z + r * 0.9142097557035307,
            x + r * 0.38268343236508984, y + 0, z + r * 0.9238795325112867,
            x + r * 0.38268343236508984, y + h, z + r * 0.9238795325112867,
            x + r * 0.3598950365349883, y + 0, z + r * 0.9329927988347388,
            x + r * 0.3598950365349883, y + h, z + r * 0.9329927988347388,
            x + r * 0.33688985339222005, y + 0, z + r * 0.9415440651830208,
            x + r * 0.33688985339222005, y + h, z + r * 0.9415440651830208,
            x + r * 0.3136817403988916, y + 0, z + r * 0.9495281805930367,
            x + r * 0.3136817403988916, y + h, z + r * 0.9495281805930367,
            x + r * 0.29028467725446233, y + 0, z + r * 0.9569403357322089,
            x + r * 0.29028467725446233, y + h, z + r * 0.9569403357322089,
            x + r * 0.2667127574748984, y + 0, z + r * 0.9637760657954398,
            x + r * 0.2667127574748984, y + h, z + r * 0.9637760657954398,
            x + r * 0.24298017990326398, y + 0, z + r * 0.970031253194544,
            x + r * 0.24298017990326398, y + h, z + r * 0.970031253194544,
            x + r * 0.21910124015686977, y + 0, z + r * 0.9757021300385286,
            x + r * 0.21910124015686977, y + h, z + r * 0.9757021300385286,
            x + r * 0.19509032201612833, y + 0, z + r * 0.9807852804032304,
            x + r * 0.19509032201612833, y + h, z + r * 0.9807852804032304,
            x + r * 0.17096188876030136, y + 0, z + r * 0.9852776423889412,
            x + r * 0.17096188876030136, y + h, z + r * 0.9852776423889412,
            x + r * 0.14673047445536175, y + 0, z + r * 0.989176509964781,
            x + r * 0.14673047445536175, y + h, z + r * 0.989176509964781,
            x + r * 0.12241067519921628, y + 0, z + r * 0.99247953459871,
            x + r * 0.12241067519921628, y + h, z + r * 0.99247953459871,
            x + r * 0.09801714032956077, y + 0, z + r * 0.9951847266721968,
            x + r * 0.09801714032956077, y + h, z + r * 0.9951847266721968,
            x + r * 0.07356456359966745, y + 0, z + r * 0.9972904566786902,
            x + r * 0.07356456359966745, y + h, z + r * 0.9972904566786902,
            x + r * 0.049067674327418126, y + 0, z + r * 0.9987954562051724,
            x + r * 0.049067674327418126, y + h, z + r * 0.9987954562051724,
            x + r * 0.024541228522912264, y + 0, z + r * 0.9996988186962042,
            x + r * 0.024541228522912264, y + h, z + r * 0.9996988186962042,
            x + r * 6.123233995736766e-17, y + 0, z + r * 1.0,
            x + r * 6.123233995736766e-17, y + h, z + r * 1.0,
            x + r * -0.024541228522912142, y + 0, z + r * 0.9996988186962042,
            x + r * -0.024541228522912142, y + h, z + r * 0.9996988186962042,
            x + r * -0.04906767432741801, y + 0, z + r * 0.9987954562051724,
            x + r * -0.04906767432741801, y + h, z + r * 0.9987954562051724,
            x + r * -0.07356456359966733, y + 0, z + r * 0.9972904566786902,
            x + r * -0.07356456359966733, y + h, z + r * 0.9972904566786902,
            x + r * -0.09801714032956065, y + 0, z + r * 0.9951847266721969,
            x + r * -0.09801714032956065, y + h, z + r * 0.9951847266721969,
            x + r * -0.12241067519921615, y + 0, z + r * 0.99247953459871,
            x + r * -0.12241067519921615, y + h, z + r * 0.99247953459871,
            x + r * -0.14673047445536164, y + 0, z + r * 0.989176509964781,
            x + r * -0.14673047445536164, y + h, z + r * 0.989176509964781,
            x + r * -0.17096188876030124, y + 0, z + r * 0.9852776423889412,
            x + r * -0.17096188876030124, y + h, z + r * 0.9852776423889412,
            x + r * -0.1950903220161282, y + 0, z + r * 0.9807852804032304,
            x + r * -0.1950903220161282, y + h, z + r * 0.9807852804032304,
            x + r * -0.21910124015686966, y + 0, z + r * 0.9757021300385286,
            x + r * -0.21910124015686966, y + h, z + r * 0.9757021300385286,
            x + r * -0.24298017990326387, y + 0, z + r * 0.970031253194544,
            x + r * -0.24298017990326387, y + h, z + r * 0.970031253194544,
            x + r * -0.2667127574748983, y + 0, z + r * 0.9637760657954398,
            x + r * -0.2667127574748983, y + h, z + r * 0.9637760657954398,
            x + r * -0.29028467725446216, y + 0, z + r * 0.9569403357322089,
            x + r * -0.29028467725446216, y + h, z + r * 0.9569403357322089,
            x + r * -0.3136817403988914, y + 0, z + r * 0.9495281805930367,
            x + r * -0.3136817403988914, y + h, z + r * 0.9495281805930367,
            x + r * -0.33688985339221994, y + 0, z + r * 0.9415440651830208,
            x + r * -0.33688985339221994, y + h, z + r * 0.9415440651830208,
            x + r * -0.35989503653498817, y + 0, z + r * 0.9329927988347388,
            x + r * -0.35989503653498817, y + h, z + r * 0.9329927988347388,
            x + r * -0.3826834323650897, y + 0, z + r * 0.9238795325112867,
            x + r * -0.3826834323650897, y + h, z + r * 0.9238795325112867,
            x + r * -0.40524131400498975, y + 0, z + r * 0.9142097557035307,
            x + r * -0.40524131400498975, y + h, z + r * 0.9142097557035307,
            x + r * -0.42755509343028186, y + 0, z + r * 0.9039892931234434,
            x + r * -0.42755509343028186, y + h, z + r * 0.9039892931234434,
            x + r * -0.4496113296546067, y + 0, z + r * 0.8932243011955152,
            x + r * -0.4496113296546067, y + h, z + r * 0.8932243011955152,
            x + r * -0.4713967368259977, y + 0, z + r * 0.881921264348355,
            x + r * -0.4713967368259977, y + h, z + r * 0.881921264348355,
            x + r * -0.492898192229784, y + 0, z + r * 0.8700869911087115,
            x + r * -0.492898192229784, y + h, z + r * 0.8700869911087115,
            x + r * -0.5141027441932217, y + 0, z + r * 0.8577286100002721,
            x + r * -0.5141027441932217, y + h, z + r * 0.8577286100002721,
            x + r * -0.534997619887097, y + 0, z + r * 0.8448535652497072,
            x + r * -0.534997619887097, y + h, z + r * 0.8448535652497072,
            x + r * -0.555570233019602, y + 0, z + r * 0.8314696123025455,
            x + r * -0.555570233019602, y + h, z + r * 0.8314696123025455,
            x + r * -0.5758081914178453, y + 0, z + r * 0.8175848131515837,
            x + r * -0.5758081914178453, y + h, z + r * 0.8175848131515837,
            x + r * -0.5956993044924334, y + 0, z + r * 0.8032075314806449,
            x + r * -0.5956993044924334, y + h, z + r * 0.8032075314806449,
            x + r * -0.6152315905806267, y + 0, z + r * 0.7883464276266063,
            x + r * -0.6152315905806267, y + h, z + r * 0.7883464276266063,
            x + r * -0.6343932841636454, y + 0, z + r * 0.7730104533627371,
            x + r * -0.6343932841636454, y + h, z + r * 0.7730104533627371,
            x + r * -0.6531728429537765, y + 0, z + r * 0.7572088465064847,
            x + r * -0.6531728429537765, y + h, z + r * 0.7572088465064847,
            x + r * -0.6715589548470184, y + 0, z + r * 0.740951125354959,
            x + r * -0.6715589548470184, y + h, z + r * 0.740951125354959,
            x + r * -0.6895405447370669, y + 0, z + r * 0.7242470829514669,
            x + r * -0.6895405447370669, y + h, z + r * 0.7242470829514669,
            x + r * -0.7071067811865475, y + 0, z + r * 0.7071067811865476,
            x + r * -0.7071067811865475, y + h, z + r * 0.7071067811865476,
            x + r * -0.7242470829514668, y + 0, z + r * 0.689540544737067,
            x + r * -0.7242470829514668, y + h, z + r * 0.689540544737067,
            x + r * -0.7409511253549589, y + 0, z + r * 0.6715589548470186,
            x + r * -0.7409511253549589, y + h, z + r * 0.6715589548470186,
            x + r * -0.7572088465064846, y + 0, z + r * 0.6531728429537766,
            x + r * -0.7572088465064846, y + h, z + r * 0.6531728429537766,
            x + r * -0.773010453362737, y + 0, z + r * 0.6343932841636455,
            x + r * -0.773010453362737, y + h, z + r * 0.6343932841636455,
            x + r * -0.7883464276266062, y + 0, z + r * 0.6152315905806269,
            x + r * -0.7883464276266062, y + h, z + r * 0.6152315905806269,
            x + r * -0.8032075314806448, y + 0, z + r * 0.5956993044924335,
            x + r * -0.8032075314806448, y + h, z + r * 0.5956993044924335,
            x + r * -0.8175848131515836, y + 0, z + r * 0.5758081914178454,
            x + r * -0.8175848131515836, y + h, z + r * 0.5758081914178454,
            x + r * -0.8314696123025453, y + 0, z + r * 0.5555702330196022,
            x + r * -0.8314696123025453, y + h, z + r * 0.5555702330196022,
            x + r * -0.8448535652497071, y + 0, z + r * 0.5349976198870972,
            x + r * -0.8448535652497071, y + h, z + r * 0.5349976198870972,
            x + r * -0.857728610000272, y + 0, z + r * 0.5141027441932218,
            x + r * -0.857728610000272, y + h, z + r * 0.5141027441932218,
            x + r * -0.8700869911087113, y + 0, z + r * 0.49289819222978415,
            x + r * -0.8700869911087113, y + h, z + r * 0.49289819222978415,
            x + r * -0.8819212643483549, y + 0, z + r * 0.47139673682599786,
            x + r * -0.8819212643483549, y + h, z + r * 0.47139673682599786,
            x + r * -0.8932243011955152, y + 0, z + r * 0.4496113296546069,
            x + r * -0.8932243011955152, y + h, z + r * 0.4496113296546069,
            x + r * -0.9039892931234433, y + 0, z + r * 0.42755509343028203,
            x + r * -0.9039892931234433, y + h, z + r * 0.42755509343028203,
            x + r * -0.9142097557035307, y + 0, z + r * 0.4052413140049899,
            x + r * -0.9142097557035307, y + h, z + r * 0.4052413140049899,
            x + r * -0.9238795325112867, y + 0, z + r * 0.3826834323650899,
            x + r * -0.9238795325112867, y + h, z + r * 0.3826834323650899,
            x + r * -0.9329927988347388, y + 0, z + r * 0.35989503653498833,
            x + r * -0.9329927988347388, y + h, z + r * 0.35989503653498833,
            x + r * -0.9415440651830207, y + 0, z + r * 0.33688985339222033,
            x + r * -0.9415440651830207, y + h, z + r * 0.33688985339222033,
            x + r * -0.9495281805930367, y + 0, z + r * 0.3136817403988914,
            x + r * -0.9495281805930367, y + h, z + r * 0.3136817403988914,
            x + r * -0.9569403357322088, y + 0, z + r * 0.2902846772544624,
            x + r * -0.9569403357322088, y + h, z + r * 0.2902846772544624,
            x + r * -0.9637760657954398, y + 0, z + r * 0.2667127574748985,
            x + r * -0.9637760657954398, y + h, z + r * 0.2667127574748985,
            x + r * -0.970031253194544, y + 0, z + r * 0.24298017990326407,
            x + r * -0.970031253194544, y + h, z + r * 0.24298017990326407,
            x + r * -0.9757021300385285, y + 0, z + r * 0.21910124015687005,
            x + r * -0.9757021300385285, y + h, z + r * 0.21910124015687005,
            x + r * -0.9807852804032304, y + 0, z + r * 0.1950903220161286,
            x + r * -0.9807852804032304, y + h, z + r * 0.1950903220161286,
            x + r * -0.9852776423889412, y + 0, z + r * 0.17096188876030122,
            x + r * -0.9852776423889412, y + h, z + r * 0.17096188876030122,
            x + r * -0.989176509964781, y + 0, z + r * 0.1467304744553618,
            x + r * -0.989176509964781, y + h, z + r * 0.1467304744553618,
            x + r * -0.99247953459871, y + 0, z + r * 0.12241067519921635,
            x + r * -0.99247953459871, y + h, z + r * 0.12241067519921635,
            x + r * -0.9951847266721968, y + 0, z + r * 0.09801714032956083,
            x + r * -0.9951847266721968, y + h, z + r * 0.09801714032956083,
            x + r * -0.9972904566786902, y + 0, z + r * 0.07356456359966773,
            x + r * -0.9972904566786902, y + h, z + r * 0.07356456359966773,
            x + r * -0.9987954562051724, y + 0, z + r * 0.049067674327417966,
            x + r * -0.9987954562051724, y + h, z + r * 0.049067674327417966,
            x + r * -0.9996988186962042, y + 0, z + r * 0.024541228522912326,
            x + r * -0.9996988186962042, y + h, z + r * 0.024541228522912326,
            x + r * -1.0, y + 0, z + r * 1.2246467991473532e-16,
            x + r * -1.0, y + h, z + r * 1.2246467991473532e-16,
            x + r * -0.9996988186962042, y + 0, z + r * -0.02454122852291208,
            x + r * -0.9996988186962042, y + h, z + r * -0.02454122852291208,
            x + r * -0.9987954562051724, y + 0, z + r * -0.049067674327417724,
            x + r * -0.9987954562051724, y + h, z + r * -0.049067674327417724,
            x + r * -0.9972904566786902, y + 0, z + r * -0.0735645635996675,
            x + r * -0.9972904566786902, y + h, z + r * -0.0735645635996675,
            x + r * -0.9951847266721969, y + 0, z + r * -0.09801714032956059,
            x + r * -0.9951847266721969, y + h, z + r * -0.09801714032956059,
            x + r * -0.99247953459871, y + 0, z + r * -0.1224106751992161,
            x + r * -0.99247953459871, y + h, z + r * -0.1224106751992161,
            x + r * -0.989176509964781, y + 0, z + r * -0.14673047445536158,
            x + r * -0.989176509964781, y + h, z + r * -0.14673047445536158,
            x + r * -0.9852776423889413, y + 0, z + r * -0.17096188876030097,
            x + r * -0.9852776423889413, y + h, z + r * -0.17096188876030097,
            x + r * -0.9807852804032304, y + 0, z + r * -0.19509032201612836,
            x + r * -0.9807852804032304, y + h, z + r * -0.19509032201612836,
            x + r * -0.9757021300385286, y + 0, z + r * -0.2191012401568698,
            x + r * -0.9757021300385286, y + h, z + r * -0.2191012401568698,
            x + r * -0.970031253194544, y + 0, z + r * -0.24298017990326382,
            x + r * -0.970031253194544, y + h, z + r * -0.24298017990326382,
            x + r * -0.96377606579544, y + 0, z + r * -0.26671275747489825,
            x + r * -0.96377606579544, y + h, z + r * -0.26671275747489825,
            x + r * -0.9569403357322089, y + 0, z + r * -0.2902846772544621,
            x + r * -0.9569403357322089, y + h, z + r * -0.2902846772544621,
            x + r * -0.9495281805930368, y + 0, z + r * -0.3136817403988912,
            x + r * -0.9495281805930368, y + h, z + r * -0.3136817403988912,
            x + r * -0.9415440651830208, y + 0, z + r * -0.3368898533922201,
            x + r * -0.9415440651830208, y + h, z + r * -0.3368898533922201,
            x + r * -0.932992798834739, y + 0, z + r * -0.3598950365349881,
            x + r * -0.932992798834739, y + h, z + r * -0.3598950365349881,
            x + r * -0.9238795325112868, y + 0, z + r * -0.38268343236508967,
            x + r * -0.9238795325112868, y + h, z + r * -0.38268343236508967,
            x + r * -0.9142097557035307, y + 0, z + r * -0.4052413140049897,
            x + r * -0.9142097557035307, y + h, z + r * -0.4052413140049897,
            x + r * -0.9039892931234434, y + 0, z + r * -0.4275550934302818,
            x + r * -0.9039892931234434, y + h, z + r * -0.4275550934302818,
            x + r * -0.8932243011955153, y + 0, z + r * -0.44961132965460665,
            x + r * -0.8932243011955153, y + h, z + r * -0.44961132965460665,
            x + r * -0.881921264348355, y + 0, z + r * -0.47139673682599764,
            x + r * -0.881921264348355, y + h, z + r * -0.47139673682599764,
            x + r * -0.8700869911087115, y + 0, z + r * -0.4928981922297839,
            x + r * -0.8700869911087115, y + h, z + r * -0.4928981922297839,
            x + r * -0.8577286100002721, y + 0, z + r * -0.5141027441932216,
            x + r * -0.8577286100002721, y + h, z + r * -0.5141027441932216,
            x + r * -0.8448535652497072, y + 0, z + r * -0.5349976198870969,
            x + r * -0.8448535652497072, y + h, z + r * -0.5349976198870969,
            x + r * -0.8314696123025455, y + 0, z + r * -0.555570233019602,
            x + r * -0.8314696123025455, y + h, z + r * -0.555570233019602,
            x + r * -0.8175848131515837, y + 0, z + r * -0.5758081914178453,
            x + r * -0.8175848131515837, y + h, z + r * -0.5758081914178453,
            x + r * -0.8032075314806449, y + 0, z + r * -0.5956993044924332,
            x + r * -0.8032075314806449, y + h, z + r * -0.5956993044924332,
            x + r * -0.7883464276266063, y + 0, z + r * -0.6152315905806267,
            x + r * -0.7883464276266063, y + h, z + r * -0.6152315905806267,
            x + r * -0.7730104533627371, y + 0, z + r * -0.6343932841636453,
            x + r * -0.7730104533627371, y + h, z + r * -0.6343932841636453,
            x + r * -0.7572088465064848, y + 0, z + r * -0.6531728429537765,
            x + r * -0.7572088465064848, y + h, z + r * -0.6531728429537765,
            x + r * -0.7409511253549591, y + 0, z + r * -0.6715589548470184,
            x + r * -0.7409511253549591, y + h, z + r * -0.6715589548470184,
            x + r * -0.724247082951467, y + 0, z + r * -0.6895405447370668,
            x + r * -0.724247082951467, y + h, z + r * -0.6895405447370668,
            x + r * -0.7071067811865477, y + 0, z + r * -0.7071067811865475,
            x + r * -0.7071067811865477, y + h, z + r * -0.7071067811865475,
            x + r * -0.689540544737067, y + 0, z + r * -0.7242470829514668,
            x + r * -0.689540544737067, y + h, z + r * -0.7242470829514668,
            x + r * -0.6715589548470187, y + 0, z + r * -0.7409511253549589,
            x + r * -0.6715589548470187, y + h, z + r * -0.7409511253549589,
            x + r * -0.6531728429537771, y + 0, z + r * -0.7572088465064842,
            x + r * -0.6531728429537771, y + h, z + r * -0.7572088465064842,
            x + r * -0.6343932841636459, y + 0, z + r * -0.7730104533627367,
            x + r * -0.6343932841636459, y + h, z + r * -0.7730104533627367,
            x + r * -0.6152315905806273, y + 0, z + r * -0.7883464276266059,
            x + r * -0.6152315905806273, y + h, z + r * -0.7883464276266059,
            x + r * -0.5956993044924331, y + 0, z + r * -0.803207531480645,
            x + r * -0.5956993044924331, y + h, z + r * -0.803207531480645,
            x + r * -0.5758081914178452, y + 0, z + r * -0.8175848131515838,
            x + r * -0.5758081914178452, y + h, z + r * -0.8175848131515838,
            x + r * -0.5555702330196022, y + 0, z + r * -0.8314696123025452,
            x + r * -0.5555702330196022, y + h, z + r * -0.8314696123025452,
            x + r * -0.5349976198870973, y + 0, z + r * -0.844853565249707,
            x + r * -0.5349976198870973, y + h, z + r * -0.844853565249707,
            x + r * -0.5141027441932218, y + 0, z + r * -0.857728610000272,
            x + r * -0.5141027441932218, y + h, z + r * -0.857728610000272,
            x + r * -0.4928981922297842, y + 0, z + r * -0.8700869911087113,
            x + r * -0.4928981922297842, y + h, z + r * -0.8700869911087113,
            x + r * -0.47139673682599786, y + 0, z + r * -0.8819212643483549,
            x + r * -0.47139673682599786, y + h, z + r * -0.8819212643483549,
            x + r * -0.44961132965460693, y + 0, z + r * -0.8932243011955152,
            x + r * -0.44961132965460693, y + h, z + r * -0.8932243011955152,
            x + r * -0.4275550934302825, y + 0, z + r * -0.9039892931234431,
            x + r * -0.4275550934302825, y + h, z + r * -0.9039892931234431,
            x + r * -0.40524131400499036, y + 0, z + r * -0.9142097557035305,
            x + r * -0.40524131400499036, y + h, z + r * -0.9142097557035305,
            x + r * -0.38268343236509034, y + 0, z + r * -0.9238795325112865,
            x + r * -0.38268343236509034, y + h, z + r * -0.9238795325112865,
            x + r * -0.35989503653498794, y + 0, z + r * -0.932992798834739,
            x + r * -0.35989503653498794, y + h, z + r * -0.932992798834739,
            x + r * -0.33688985339221994, y + 0, z + r * -0.9415440651830208,
            x + r * -0.33688985339221994, y + h, z + r * -0.9415440651830208,
            x + r * -0.31368174039889146, y + 0, z + r * -0.9495281805930367,
            x + r * -0.31368174039889146, y + h, z + r * -0.9495281805930367,
            x + r * -0.29028467725446244, y + 0, z + r * -0.9569403357322088,
            x + r * -0.29028467725446244, y + h, z + r * -0.9569403357322088,
            x + r * -0.26671275747489853, y + 0, z + r * -0.9637760657954398,
            x + r * -0.26671275747489853, y + h, z + r * -0.9637760657954398,
            x + r * -0.24298017990326412, y + 0, z + r * -0.970031253194544,
            x + r * -0.24298017990326412, y + h, z + r * -0.970031253194544,
            x + r * -0.2191012401568701, y + 0, z + r * -0.9757021300385285,
            x + r * -0.2191012401568701, y + h, z + r * -0.9757021300385285,
            x + r * -0.19509032201612866, y + 0, z + r * -0.9807852804032303,
            x + r * -0.19509032201612866, y + h, z + r * -0.9807852804032303,
            x + r * -0.1709618887603017, y + 0, z + r * -0.9852776423889411,
            x + r * -0.1709618887603017, y + h, z + r * -0.9852776423889411,
            x + r * -0.1467304744553623, y + 0, z + r * -0.9891765099647809,
            x + r * -0.1467304744553623, y + h, z + r * -0.9891765099647809,
            x + r * -0.12241067519921596, y + 0, z + r * -0.9924795345987101,
            x + r * -0.12241067519921596, y + h, z + r * -0.9924795345987101,
            x + r * -0.09801714032956045, y + 0, z + r * -0.9951847266721969,
            x + r * -0.09801714032956045, y + h, z + r * -0.9951847266721969,
            x + r * -0.07356456359966736, y + 0, z + r * -0.9972904566786902,
            x + r * -0.07356456359966736, y + h, z + r * -0.9972904566786902,
            x + r * -0.04906767432741803, y + 0, z + r * -0.9987954562051724,
            x + r * -0.04906767432741803, y + h, z + r * -0.9987954562051724,
            x + r * -0.02454122852291239, y + 0, z + r * -0.9996988186962042,
            x + r * -0.02454122852291239, y + h, z + r * -0.9996988186962042,
            x + r * -1.8369701987210297e-16, y + 0, z + r * -1.0,
            x + r * -1.8369701987210297e-16, y + h, z + r * -1.0,
            x + r * 0.02454122852291202, y + 0, z + r * -0.9996988186962042,
            x + r * 0.02454122852291202, y + h, z + r * -0.9996988186962042,
            x + r * 0.04906767432741766, y + 0, z + r * -0.9987954562051724,
            x + r * 0.04906767432741766, y + h, z + r * -0.9987954562051724,
            x + r * 0.07356456359966698, y + 0, z + r * -0.9972904566786902,
            x + r * 0.07356456359966698, y + h, z + r * -0.9972904566786902,
            x + r * 0.09801714032956009, y + 0, z + r * -0.9951847266721969,
            x + r * 0.09801714032956009, y + h, z + r * -0.9951847266721969,
            x + r * 0.1224106751992156, y + 0, z + r * -0.9924795345987101,
            x + r * 0.1224106751992156, y + h, z + r * -0.9924795345987101,
            x + r * 0.14673047445536194, y + 0, z + r * -0.9891765099647809,
            x + r * 0.14673047445536194, y + h, z + r * -0.9891765099647809,
            x + r * 0.17096188876030133, y + 0, z + r * -0.9852776423889412,
            x + r * 0.17096188876030133, y + h, z + r * -0.9852776423889412,
            x + r * 0.1950903220161283, y + 0, z + r * -0.9807852804032304,
            x + r * 0.1950903220161283, y + h, z + r * -0.9807852804032304,
            x + r * 0.21910124015686974, y + 0, z + r * -0.9757021300385286,
            x + r * 0.21910124015686974, y + h, z + r * -0.9757021300385286,
            x + r * 0.24298017990326376, y + 0, z + r * -0.970031253194544,
            x + r * 0.24298017990326376, y + h, z + r * -0.970031253194544,
            x + r * 0.2667127574748982, y + 0, z + r * -0.96377606579544,
            x + r * 0.2667127574748982, y + h, z + r * -0.96377606579544,
            x + r * 0.29028467725446205, y + 0, z + r * -0.9569403357322089,
            x + r * 0.29028467725446205, y + h, z + r * -0.9569403357322089,
            x + r * 0.31368174039889113, y + 0, z + r * -0.9495281805930368,
            x + r * 0.31368174039889113, y + h, z + r * -0.9495281805930368,
            x + r * 0.3368898533922196, y + 0, z + r * -0.9415440651830209,
            x + r * 0.3368898533922196, y + h, z + r * -0.9415440651830209,
            x + r * 0.3598950365349876, y + 0, z + r * -0.9329927988347391,
            x + r * 0.3598950365349876, y + h, z + r * -0.9329927988347391,
            x + r * 0.38268343236509, y + 0, z + r * -0.9238795325112866,
            x + r * 0.38268343236509, y + h, z + r * -0.9238795325112866,
            x + r * 0.40524131400499, y + 0, z + r * -0.9142097557035306,
            x + r * 0.40524131400499, y + h, z + r * -0.9142097557035306,
            x + r * 0.42755509343028214, y + 0, z + r * -0.9039892931234433,
            x + r * 0.42755509343028214, y + h, z + r * -0.9039892931234433,
            x + r * 0.4496113296546066, y + 0, z + r * -0.8932243011955153,
            x + r * 0.4496113296546066, y + h, z + r * -0.8932243011955153,
            x + r * 0.4713967368259976, y + 0, z + r * -0.881921264348355,
            x + r * 0.4713967368259976, y + h, z + r * -0.881921264348355,
            x + r * 0.49289819222978387, y + 0, z + r * -0.8700869911087115,
            x + r * 0.49289819222978387, y + h, z + r * -0.8700869911087115,
            x + r * 0.5141027441932216, y + 0, z + r * -0.8577286100002722,
            x + r * 0.5141027441932216, y + h, z + r * -0.8577286100002722,
            x + r * 0.5349976198870969, y + 0, z + r * -0.8448535652497072,
            x + r * 0.5349976198870969, y + h, z + r * -0.8448535652497072,
            x + r * 0.5555702330196018, y + 0, z + r * -0.8314696123025455,
            x + r * 0.5555702330196018, y + h, z + r * -0.8314696123025455,
            x + r * 0.5758081914178449, y + 0, z + r * -0.817584813151584,
            x + r * 0.5758081914178449, y + h, z + r * -0.817584813151584,
            x + r * 0.5956993044924329, y + 0, z + r * -0.8032075314806453,
            x + r * 0.5956993044924329, y + h, z + r * -0.8032075314806453,
            x + r * 0.615231590580627, y + 0, z + r * -0.7883464276266061,
            x + r * 0.615231590580627, y + h, z + r * -0.7883464276266061,
            x + r * 0.6343932841636456, y + 0, z + r * -0.7730104533627369,
            x + r * 0.6343932841636456, y + h, z + r * -0.7730104533627369,
            x + r * 0.6531728429537768, y + 0, z + r * -0.7572088465064846,
            x + r * 0.6531728429537768, y + h, z + r * -0.7572088465064846,
            x + r * 0.6715589548470183, y + 0, z + r * -0.7409511253549591,
            x + r * 0.6715589548470183, y + h, z + r * -0.7409511253549591,
            x + r * 0.6895405447370668, y + 0, z + r * -0.724247082951467,
            x + r * 0.6895405447370668, y + h, z + r * -0.724247082951467,
            x + r * 0.7071067811865474, y + 0, z + r * -0.7071067811865477,
            x + r * 0.7071067811865474, y + h, z + r * -0.7071067811865477,
            x + r * 0.7242470829514667, y + 0, z + r * -0.6895405447370672,
            x + r * 0.7242470829514667, y + h, z + r * -0.6895405447370672,
            x + r * 0.7409511253549589, y + 0, z + r * -0.6715589548470187,
            x + r * 0.7409511253549589, y + h, z + r * -0.6715589548470187,
            x + r * 0.7572088465064842, y + 0, z + r * -0.6531728429537771,
            x + r * 0.7572088465064842, y + h, z + r * -0.6531728429537771,
            x + r * 0.7730104533627367, y + 0, z + r * -0.6343932841636459,
            x + r * 0.7730104533627367, y + h, z + r * -0.6343932841636459,
            x + r * 0.7883464276266059, y + 0, z + r * -0.6152315905806274,
            x + r * 0.7883464276266059, y + h, z + r * -0.6152315905806274,
            x + r * 0.803207531480645, y + 0, z + r * -0.5956993044924332,
            x + r * 0.803207531480645, y + h, z + r * -0.5956993044924332,
            x + r * 0.8175848131515837, y + 0, z + r * -0.5758081914178452,
            x + r * 0.8175848131515837, y + h, z + r * -0.5758081914178452,
            x + r * 0.8314696123025452, y + 0, z + r * -0.5555702330196022,
            x + r * 0.8314696123025452, y + h, z + r * -0.5555702330196022,
            x + r * 0.844853565249707, y + 0, z + r * -0.5349976198870973,
            x + r * 0.844853565249707, y + h, z + r * -0.5349976198870973,
            x + r * 0.857728610000272, y + 0, z + r * -0.5141027441932219,
            x + r * 0.857728610000272, y + h, z + r * -0.5141027441932219,
            x + r * 0.8700869911087113, y + 0, z + r * -0.49289819222978426,
            x + r * 0.8700869911087113, y + h, z + r * -0.49289819222978426,
            x + r * 0.8819212643483548, y + 0, z + r * -0.4713967368259979,
            x + r * 0.8819212643483548, y + h, z + r * -0.4713967368259979,
            x + r * 0.8932243011955151, y + 0, z + r * -0.449611329654607,
            x + r * 0.8932243011955151, y + h, z + r * -0.449611329654607,
            x + r * 0.9039892931234431, y + 0, z + r * -0.42755509343028253,
            x + r * 0.9039892931234431, y + h, z + r * -0.42755509343028253,
            x + r * 0.9142097557035305, y + 0, z + r * -0.4052413140049904,
            x + r * 0.9142097557035305, y + h, z + r * -0.4052413140049904,
            x + r * 0.9238795325112865, y + 0, z + r * -0.3826834323650904,
            x + r * 0.9238795325112865, y + h, z + r * -0.3826834323650904,
            x + r * 0.932992798834739, y + 0, z + r * -0.359895036534988,
            x + r * 0.932992798834739, y + h, z + r * -0.359895036534988,
            x + r * 0.9415440651830208, y + 0, z + r * -0.33688985339222,
            x + r * 0.9415440651830208, y + h, z + r * -0.33688985339222,
            x + r * 0.9495281805930367, y + 0, z + r * -0.3136817403988915,
            x + r * 0.9495281805930367, y + h, z + r * -0.3136817403988915,
            x + r * 0.9569403357322088, y + 0, z + r * -0.2902846772544625,
            x + r * 0.9569403357322088, y + h, z + r * -0.2902846772544625,
            x + r * 0.9637760657954398, y + 0, z + r * -0.2667127574748986,
            x + r * 0.9637760657954398, y + h, z + r * -0.2667127574748986,
            x + r * 0.970031253194544, y + 0, z + r * -0.24298017990326418,
            x + r * 0.970031253194544, y + h, z + r * -0.24298017990326418,
            x + r * 0.9757021300385285, y + 0, z + r * -0.21910124015687016,
            x + r * 0.9757021300385285, y + h, z + r * -0.21910124015687016,
            x + r * 0.9807852804032303, y + 0, z + r * -0.19509032201612872,
            x + r * 0.9807852804032303, y + h, z + r * -0.19509032201612872,
            x + r * 0.9852776423889411, y + 0, z + r * -0.17096188876030177,
            x + r * 0.9852776423889411, y + h, z + r * -0.17096188876030177,
            x + r * 0.9891765099647809, y + 0, z + r * -0.1467304744553624,
            x + r * 0.9891765099647809, y + h, z + r * -0.1467304744553624,
            x + r * 0.99247953459871, y + 0, z + r * -0.12241067519921603,
            x + r * 0.99247953459871, y + h, z + r * -0.12241067519921603,
            x + r * 0.9951847266721969, y + 0, z + r * -0.0980171403295605,
            x + r * 0.9951847266721969, y + h, z + r * -0.0980171403295605,
            x + r * 0.9972904566786902, y + 0, z + r * -0.07356456359966741,
            x + r * 0.9972904566786902, y + h, z + r * -0.07356456359966741,
            x + r * 0.9987954562051724, y + 0, z + r * -0.04906767432741809,
            x + r * 0.9987954562051724, y + h, z + r * -0.04906767432741809,
            x + r * 0.9996988186962042, y + 0, z + r * -0.024541228522912448,
            x + r * 0.9996988186962042, y + h, z + r * -0.024541228522912448,
            x + r * 0, y + 0, z + r * 0,
            x + r * 0, y + h, z + r * 0];
    }

    getCoords() {
        return this.idCoords;
    }

    getTeam() {
        return this.team;
    }

    getVertices() {
        return this.vertices;
    }

    getVerticesIndexes() {
        return this.vertexIndices;
    }

    getColors() {
        return this.colors;
    }

    getDiffCoords(coords) {
        return this.diffCoords;
    }

    getHeight() {
        return this.height;
    }

    setCoords(newCoords) {
        this.idCoords = newCoords;
        this.setVertices(newCoords[0], newCoords[1], newCoords[2]);
    }

    setDiffCoords(coords) {
        this.diffCoords = coords;
    }

    // Colours
    setColor(colorCode) {
        if (this.team) {
            switch (colorCode) {
                case 1:
                    // Color 0 Gray
                    this.setColorDraught([127, 127, 127], [127, 127, 127], [127, 127, 127]);
                    break;
                default:
                    // Color 1 Red
                    this.setColorDraught([192, 30, 34], [192, 30, 34], [192, 30, 34]);
                    break;
            }
        } else {
            switch (colorCode) {
                case 1:
                    // Color 0 Green
                    this.setColorDraught([0, 255, 0], [0, 255, 0], [0, 255, 0]);
                    break;
                default:
                    // Color 1 Black
                    this.setColorDraught([0, 0, 0], [0, 0, 0], [0, 0, 0]);
                    break;
            }
        }
    }

    setColorDraught(color1, color2, color3) {
        this.colors = [];

        var length = this.vertices.length;
        for (var i = 0; i < length; i += 9) {
            this.colors.push(color1[0] / 255);
            this.colors.push(color1[1] / 255);
            this.colors.push(color1[2] / 255);

            this.colors.push(color2[0] / 255);
            this.colors.push(color2[1] / 255);
            this.colors.push(color2[2] / 255);

            this.colors.push(color3[0] / 255);
            this.colors.push(color3[1] / 255);
            this.colors.push(color3[2] / 255);
        }
    }
}

const materials = {
    BRONZE: [0.21, 0.13, 0.05, 0.71, 0.43, 0.18, 0.39, 0.27, 0.17, 25.6],
    POLISHED_BRONZE: [0.25, 0.15, 0.06, 0.40, 0.24, 0.10, 0.77, 0.46, 0.20, 76.8],
    COPPER: [0.19, 0.07, 0.02, 0.70, 0.27, 0.08, 0.26, 0.14, 0.08, 12.8],
    POLISHED_COPPER: [0.23, 0.08, 0.03, 0.55, 0.21, 0.07, 0.58, 0.22, 0.07, 51.2],
    CHROMIUM: [0.25, 0.25, 0.25, 0.40, 0.40, 0.40, 0.77, 0.77, 0.77, 76.8],
    BRASS: [0.33, 0.22, 0.03, 0.78, 0.57, 0.11, 0.99, 0.94, 0.81, 27.9],
    GOLD: [0.25, 0.20, 0.07, 0.75, 0.60, 0.23, 0.63, 0.56, 0.37, 51.2],
    POLISHED_GOLD: [0.25, 0.22, 0.06, 0.35, 0.31, 0.09, 0.80, 0.73, 0.21, 83.2],
    POLISHED_SILVER: [0.23, 0.23, 0.23, 0.28, 0.28, 0.28, 0.77, 0.77, 0.77, 89.6],
    RED_PLASTIC: [0.30, 0.00, 0.00, 0.60, 0.00, 0.00, 0.80, 0.60, 0.60, 32.0],
    SHINY_BLUE: [0.00, 0.00, 0.50, 0.00, 0.00, 1.00, 1.00, 1.00, 1.00, 125.0],
    GRAY: [0.10, 0.10, 0.10, 0.50, 0.50, 0.50, 0.70, 0.70, 0.70, 1.0]
};