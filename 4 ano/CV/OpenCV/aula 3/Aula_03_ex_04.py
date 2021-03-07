# Aula_03_ex_04.py
#
# Sobel Operator
#
# Paulo Dias - 10/2019


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


###################################
# MAIN

image = cv2.imread("C:\CV_imagens\\Bikesgray.jpg", cv2.IMREAD_UNCHANGED)

if np.shape(image) == ():
    # Failed Reading
    print("Image file could not be open!")
    exit(-1)

printImageFeatures(image)

cv2.imshow('Original', image)

# Sobel Operato3 3 x 3

imageSobel3x3_X = cv2.Sobel(image, cv2.CV_64F, 1, 0, 3)

cv2.namedWindow("Sobel 3 x 3 - X", cv2.WINDOW_AUTOSIZE)
cv2.imshow("Sobel 3 x 3 - X", imageSobel3x3_X)

image8bits = np.uint8(np.absolute(imageSobel3x3_X))
cv2.imshow("8 bits - Sobel 3 x 3 - X", image8bits)

imageSobel3x3_Y = cv2.Sobel(image, cv2.CV_64F, 0, 1, 3)

cv2.namedWindow("Sobel 3 x 3 - Y", cv2.WINDOW_AUTOSIZE)
cv2.imshow("Sobel 3 x 3 - Y", imageSobel3x3_Y)

image8bits = np.uint8(np.absolute(imageSobel3x3_Y))
cv2.imshow("8 bits - Sobel 3 x 3 - Y", image8bits)

cv2.waitKey(0)

cv2.destroyAllWindows()
