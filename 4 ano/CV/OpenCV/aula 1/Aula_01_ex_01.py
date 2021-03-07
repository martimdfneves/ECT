# Aula_01_ex_01.py
#
# Example: loading and displaying an image with OpenCV
#
# Paulo Dias - 10/2019


# import
import numpy as np
import cv2

#1.2

# Read the image
image = cv2.imread("C:\CV_imagens\\lena.jpg", cv2.IMREAD_UNCHANGED)
image2 = np.copy(image)

if np.shape(image) == ():
    # Failed Reading
    print("Image file could not be opened")
    exit(-1)

# Image characteristics
height, width = image.shape

print("Image Size: (%d,%d)" % (height, width))
#print("Image Channel: (%d)" % (channel))
print("Image Type: %s" % (image.dtype))

for i in range(height):
    for j in range(width):
        if image[i,j] < 128:
            image2[i,j] = 0


# Create a visualization window
# CV_WINDOW_AUTOSIZE : window size will depend on image size
cv2.namedWindow("Display window", cv2.WINDOW_AUTOSIZE)
cv2.namedWindow("Display window2", cv2.WINDOW_AUTOSIZE)

# Show the image
cv2.imshow("Display window", image)
cv2.imshow("Display window2", image2)

# Wait
cv2.waitKey(0)

# Destroy the window -- might be omitted
cv2.destroyWindow("Display window")
cv2.destroyWindow("Display window2")

#1.3

image = cv2.imread("C:\CV_imagens\\deti.jpg", cv2.IMREAD_UNCHANGED)
image2 = cv2.imread("C:\CV_imagens\\deti.bmp", cv2.IMREAD_UNCHANGED)
image3 = image - image2
image4 = cv2.subtract(image, image2)

# Create a visualization window
# CV_WINDOW_AUTOSIZE : window size will depend on image size
cv2.namedWindow("Display window", cv2.WINDOW_AUTOSIZE)
cv2.namedWindow("Display window2", cv2.WINDOW_AUTOSIZE)

# Show the image
cv2.imshow("Display window", image3)
cv2.imshow("Display window2", image4)

# Wait
cv2.waitKey(0)

# Destroy the window -- might be omitted
cv2.destroyWindow("Display window")
cv2.destroyWindow("Display window2")

#1.4


def mouse_handler(event, x, y, flags, params):

    if event == cv2.EVENT_LBUTTONDOWN:

        print("left click")

        cv2.circle(image,(x,y),5,(255,0,0),-1)

        cv2.imshow("Window", image)

image = cv2.imread( "C:\CV_imagens\\deti.jpg", cv2.IMREAD_UNCHANGED );

cv2.imshow( "Window", image );

cv2.setMouseCallback("Window", mouse_handler)

cv2.waitKey( 0 );