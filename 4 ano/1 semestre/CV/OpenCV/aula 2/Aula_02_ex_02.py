import cv2

img = cv2.imread("C:\CV_imagens\\lena.jpg", cv2.IMREAD_UNCHANGED)
GRID_SIZE = 20

height, width = img.shape
for x in range(0, width -1, GRID_SIZE):
     cv2.line(img, (x, 0), (x, height), (255, 0, 0), 1, 1)
     cv2.line(img, (0, x), (width, x), (255, 0, 0), 1, 1)

cv2.imshow('Hehe', img)
key = cv2.waitKey(0)