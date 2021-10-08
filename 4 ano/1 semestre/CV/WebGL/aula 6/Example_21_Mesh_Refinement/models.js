//////////////////////////////////////////////////////////////////////////////
//
//  Functions for processing triangle mesh models
//
//  J. Madeira - Oct. 2015
//
//////////////////////////////////////////////////////////////////////////////

//----------------------------------------------------------------------------
//
//  Recursive triangle subdivision, using the midpoints of edges
//

function recSubdivisionMidPoint(v1, v2, v3,
                                c1, c2, c3,
                                coordsArray,
                                colorsArray,
                                recursionDepth) {

    // Recursive midpoint subdivision of one triangle

    if (recursionDepth == 0) {

        // Storing coordinates and colors in the destination arrays

        coordsArray.push(v1[0], v1[1], v1[2]);

        coordsArray.push(v2[0], v2[1], v2[2]);

        coordsArray.push(v3[0], v3[1], v3[2]);

        colorsArray.push(c1[0], c1[1], c1[2]);

        colorsArray.push(c2[0], c2[1], c2[2]);

        colorsArray.push(c3[0], c3[1], c3[2]);
    } else {

        // Compute the midpoints and proceed recursively

        var mid12 = computeMidPoint(v1, v2);

        var mid23 = computeMidPoint(v2, v3);

        var mid31 = computeMidPoint(v3, v1);

        // Colors are also averaged

        var c12 = computeMidPoint(c1, c2);

        var c23 = computeMidPoint(c2, c3);

        var c31 = computeMidPoint(c3, c1);

        // 4 recursive calls 

        recSubdivisionMidPoint(v1, mid12, mid31, c1, c12, c31,
            coordsArray, colorsArray, recursionDepth - 1);

        recSubdivisionMidPoint(v2, mid23, mid12, c2, c23, c12,
            coordsArray, colorsArray, recursionDepth - 1);

        recSubdivisionMidPoint(v3, mid31, mid23, c3, c31, c23,
            coordsArray, colorsArray, recursionDepth - 1);

        recSubdivisionMidPoint(mid12, mid23, mid31, c12, c23, c31,
            coordsArray, colorsArray, recursionDepth - 1);
    }
}

//----------------------------------------------------------------------------

function midPointRefinement(coordsArray,
                            colorsArray,
                            recursionDepth) {

    // Mesh refinement - Higher-level function

    // Each triangle is subdivided into 4 smaller ones - Lower-level recursive function

    // Vertices are duplicated, whenever needed !!

    // recursionDepth controls the final number of triangles and vertices

    var origArrayLength = coordsArray.length;

    // Copying

    var origCoords = coordsArray.slice();

    var origColors = colorsArray.slice();

    // Clearing the arrays

    coordsArray.splice(0, origArrayLength);

    colorsArray.splice(0, origArrayLength);

    var origIndex;

    // Each triangle is recursively subdivided into 4 triangles

    // Iterate through the original triangular faces

    for (origIndex = 0; origIndex < origArrayLength; origIndex += 9) {
        /* Call the recursive subdivision function */

        recSubdivisionMidPoint(origCoords.slice(origIndex, origIndex + 3),
            origCoords.slice(origIndex + 3, origIndex + 6),
            origCoords.slice(origIndex + 6, origIndex + 9),
            origColors.slice(origIndex, origIndex + 3),
            origColors.slice(origIndex + 3, origIndex + 6),
            origColors.slice(origIndex + 6, origIndex + 9),
            coordsArray,
            colorsArray,
            recursionDepth);
    }
}

//----------------------------------------------------------------------------
////  Recursive triangle subdivision, using the triangle centroid
//

function recSubdivisionCentroid(v1, v2, v3,
                                c1, c2, c3,
                                coordsArray,
                                colorsArray,
                                recursionDepth) {

    // Recursive centroid subdivision of one triangle

    if (recursionDepth == 0) {

        // Storing coordinates and colors in the destination arrays

        coordsArray.push(v1[0], v1[1], v1[2]);

        coordsArray.push(v2[0], v2[1], v2[2]);

        coordsArray.push(v3[0], v3[1], v3[2]);

        colorsArray.push(c1[0], c1[1], c1[2]);

        colorsArray.push(c2[0], c2[1], c2[2]);

        colorsArray.push(c3[0], c3[1], c3[2]);
    } else {

        var centroid = computeCentroid(v1, v2, v3);

        var centroidColor = computeCentroid(c1, c2, c3);


        // 3 recursive calls 

        recSubdivisionCentroid(v1, v2, centroid, c1, c2, centroidColor,
            coordsArray, colorsArray, recursionDepth - 1);

        recSubdivisionCentroid(v2, v3, centroid, c2, c3, centroidColor,
            coordsArray, colorsArray, recursionDepth - 1);

        recSubdivisionCentroid(v1, centroid, v3, c1, centroidColor, c3,
            coordsArray, colorsArray, recursionDepth - 1);

    }

}

//----------------------------------------------------------------------------

function centroidRefinement(coordsArray,
                            colorsArray,
                            recursionDepth) {

    // Mesh refinement - Higher-level function

    // Each triangle is subdivided into 3 smaller ones - Lower-level recursive function

    // Vertices are duplicated, whenever needed !!

    // recursionDepth controls the final number of triangles and vertices

    var origArrayLength = coordsArray.length;

    // Copying

    var origCoords = coordsArray.slice();

    var origColors = colorsArray.slice();

    // Clearing the arrays

    coordsArray.splice(0, origArrayLength);

    colorsArray.splice(0, origArrayLength);

    var origIndex;

    // Each triangle is recursively subdivided into 3 triangles

    // Iterate through the original triangular faces

    for (origIndex = 0; origIndex < origArrayLength; origIndex += 9)			// for each triangle
    {
        /* Call the recursive subdivision function */

        recSubdivisionCentroid(origCoords.slice(origIndex, origIndex + 3),
            origCoords.slice(origIndex + 3, origIndex + 6),
            origCoords.slice(origIndex + 6, origIndex + 9),
            origColors.slice(origIndex, origIndex + 3),
            origColors.slice(origIndex + 3, origIndex + 6),
            origColors.slice(origIndex + 6, origIndex + 9),
            coordsArray,
            colorsArray,
            recursionDepth);
    }
}

//----------------------------------------------------------------------------
//
//  Moving vertices to the spherical surface of radius 1
//

function moveToSphericalSurface(coordsArray) {

    // Each vertex is moved to the spherical surface of radius 1
    // and centered at (0,0,0)

    // This is done by handling each vertex as if it were a prosition vector,
    // and normalizing

    var origArrayLength = coordsArray.length;

    var origIndex;
    for (origIndex = 0; origIndex < origArrayLength; origIndex += 3) {
        var v = coordsArray.slice(origIndex, origIndex + 3);
        normalize(v);

        // Copy to the original coordsArray
        for (var i = 0; i < 3; i++) {
            coordsArray[origIndex + i] = v[i];
        }
    }

}
