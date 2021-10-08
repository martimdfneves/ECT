
# Aula_02_ex_04.py
#
# Histogram visualization with openCV
#
# Paulo Dias - 10/2019
#
# Some modifications - J. Madeira - 01/2021


import cv2
import numpy as np
import sys

##########################
# Print Image Features

def printImageFeatures(image):
    if len(image.shape) == 2:
        height, width = image.shape
        nchannels = 1
    else:
        height, width, nchannels = image.shape

    print("Image Height:", height)
    print("Image Width:", width)
    print("Number of channels:", nchannels)
    print("Number of elements:", image.size)


####################
# Compute Histogram

def compute_histogram(image, histSize, histRange):
    # Compute the histogram of a gray-level image
    hist_item = cv2.calcHist([image], [0], None, [histSize], histRange)
    return hist_item


##########################################
# Drawing with openCV
# Create an image to display the histogram

def histogram2image(hist, histSize, histImageWidth, histImageHeight, color):

    # A gray-level image to display the histogram
    histImage = np.zeros((histImageWidth, histImageHeight, 1), np.uint8)

    # Width of each histogram bar
    binWidth = int(np.ceil(histImageWidth * 1.0 / histSize))

    # Normalize values to [0, histImageHeight]
    cv2.normalize(hist, hist, 0, histImageHeight, cv2.NORM_MINMAX)

    # Draw the bars of the nomrmalized histogram
    for i in range(histSize):
        cv2.rectangle(histImage, (i * binWidth, 0),
                      ((i + 1) * binWidth, hist[i]), color, -1)

    # ATTENTION : Y coordinate upside down
    histImage = np.flipud(histImage)

    return histImage


###################################
# MAIN

# Read the image
image = cv2.imread("C:\CV_imagens\\lena.jpg", cv2.IMREAD_UNCHANGED)

if np.shape(image) == ():
    # Failed Reading
    print("Image file could not be open!")
    exit(-1)

# Gray-Level ?
if len(image.shape) > 2:
    print("The loaded image is NOT a GRAY-LEVEL image !")
    exit(-1)

# Display the image
cv2.namedWindow("Original Image")
cv2.imshow("Original Image", image)

# Print some image features
printImageFeatures(image)

# COMPUTE THE HISTOGRAM
# Size
histSize = 256	 # 1 bar for each intensity level from 0 to 255
# Intensity Range
histRange = [0, 256]
hist = compute_histogram(image, histSize, histRange)

# DISPLAY THE HISTOGRAM USING OPENCV
# GRAY-LEVEL IMAGE
histImageWidth = 512
histImageHeight = 512
color = (255)   # Histogram bars are white
histImage = histogram2image(
    hist, histSize, histImageWidth, histImageHeight, color)

cv2.imshow("Histogram", histImage)

cv2.waitKey(0)