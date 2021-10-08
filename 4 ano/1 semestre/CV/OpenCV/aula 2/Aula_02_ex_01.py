# Aula_02_ex_01.py
#
# Drawing of 2D primitives and user interaction
#
# Paulo Dias - 10/2019
#
# Modified - 01/2021 - Joaquim Madeira


import cv2

import numpy as np


def mouse_gray(event, x, y, flags, params):
    global choice
    if event == cv2.EVENT_LBUTTONDOWN:
        if choice == 49:
            cv2.line(gray_image, (x - 20, y - 20), (x + 20, y + 20), (255))
            cv2.imshow("GRAY", gray_image)
        elif choice == 50:
            cv2.circle(gray_image, (x, y), 25, (255), cv2.FILLED)
            cv2.imshow("GRAY", gray_image)
        elif choice == 51:
            cv2.rectangle(gray_image, (x - 10, y - 10),
                          (x + 10, y + 10), (255), cv2.FILLED)
            cv2.imshow("GRAY", gray_image)
        elif choice == 52:
            penta = np.array([[[x-20,y],[x,y+20],[x+20,y],[x+15,y-20],[x-15,y-20]]], np.int32)
            cv2.polylines(gray_image, [penta], True, (255,120,255),3)
            cv2.imshow("GRAY", gray_image)


def mouse_rgb(event, x, y, flags, params):
    global choice
    if event == cv2.EVENT_LBUTTONDOWN:
        if choice == 49:
            cv2.line(rgb_image, (x - 20, y + 20),
                     (x + 20, y - 20), (0, 0, 255))
            cv2.imshow("RGB", rgb_image)
        elif choice == 50:
            cv2.circle(rgb_image, (x, y), 25, (255, 0, 0))
            cv2.imshow("RGB", rgb_image)
        elif choice == 51:
            cv2.rectangle(rgb_image, (x - 10, y - 10),
                          (x + 10, y + 10), (0, 255, 0))
            cv2.imshow("RGB", rgb_image)


# Create two black images
gray_image = np.zeros((512, 256, 1))
rgb_image = np.zeros((512, 256, 3))

# Display windows
cv2.namedWindow("GRAY", cv2.WINDOW_AUTOSIZE)
cv2.namedWindow("RGB", cv2.WINDOW_AUTOSIZE)

# Display and print features
cv2.imshow("GRAY", gray_image)
print("Gray level image")
print("Number of rows :", gray_image.shape[0])
print("Number of columns :", gray_image.shape[1])
print("Number of channels :", gray_image.shape[2])
print("Number of elements :", gray_image.size)

cv2.imshow("RGB", rgb_image)
print("RGB image")
print("Number of rows :", rgb_image.shape[0])
print("Number of columns :", rgb_image.shape[1])
print("Number of channels :", rgb_image.shape[2])
print("Number of elements :", rgb_image.size)

# Callbacks
cv2.setMouseCallback("GRAY", mouse_gray)
cv2.setMouseCallback("RGB", mouse_rgb)

#  Menu and keyboard input

choice = 49  # Default

while True:
    print("\n")
    print("1 -- Line Segment")
    print("2 -- Circle")
    print("3 -- Square")
    print("Q -- Quit")

    choice = cv2.waitKey(0)

    if choice == 81 or choice == 113:
        break
    elif choice == 49:
        print("LINE SEGMENT : select MIDPOINT with LEFT MOUSE BUTTON")
    elif choice == 50:
        print("CIRCLE : select CENTER with LEFT MOUSE BUTTON")
    elif choice == 51:
        print("SQUARE : select CENTER with LEFT MOUSE BUTTON")

cv2.destroyAllWindows()
