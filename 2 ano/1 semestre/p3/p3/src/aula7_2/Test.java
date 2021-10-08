package aula7_2;

public class Test {

    public static void main(String args[]) {

        Bitmap bmp = new Bitmap("Figura.bmp");

        bmp.write("Copy.bmp");

        bmp.getBmpRaw("Figura.raw");

        bmp.resize025("Resized.bmp");

        bmp.flipVertical("FlipVertical.bmp");

        bmp.flipHorizontal("FliHorizontal.bmp");
    }
}