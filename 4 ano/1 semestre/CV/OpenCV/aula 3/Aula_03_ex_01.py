# Aula_03_ex_01.py
#
# Mean Filter
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

image = cv2.imread("C:\CV_imagens\\DETI_Ruido.png", cv2.IMREAD_UNCHANGED)

if np.shape(image) == ():
    # Failed Reading
    print("Image file could not be open!")
    exit(-1)

printImageFeatures(image)

cv2.imshow('Orginal', image)

# Average filter 3 x 3
imageAFilter3x3_1 = cv2.blur(image, (3, 3))
cv2.imshow("Average Filter 3 x 3 - 1 Iter", imageAFilter3x3_1)

cv2.waitKey(0)

cv2.destroyAllWindows()
