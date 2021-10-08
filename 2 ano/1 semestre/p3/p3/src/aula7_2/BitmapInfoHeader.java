package aula7_2;

import java.nio.*;

public class BitmapInfoHeader {

    private static final int size = 40;
    private int width;
    private int height;
    private static final short planes = 1;
    private short bitCount;
    private int compression;
    private int sizeImage;
    private int xPelsPerMeter;
    private int yPelsPerMeter;
    private int clrUsed;
    private int clrImportant;

    public BitmapInfoHeader(int width, int height, short bitCount, int compression, int sizeImage,
                            int xPelsPerMeter, int yPelsPerMeter, int clrUsed, int clrImportant) {

        this.width = width;
        this.height = height;
        this.bitCount = bitCount;
        this.compression = compression;
        this.sizeImage = sizeImage;
        this.xPelsPerMeter = xPelsPerMeter;
        this.yPelsPerMeter = yPelsPerMeter;
        this.clrUsed = clrUsed;
        this.clrImportant = clrImportant;
    }

    public BitmapInfoHeader(byte[] array) {

        ByteBuffer wrapper = ByteBuffer.wrap(array);

        if (wrapper.getInt(0) != size)
            throw new IllegalArgumentException("Error! Invalid size!" + wrapper.getInt(0));

        if (wrapper.getShort(12) != planes)
            throw new IllegalArgumentException("Error! Invalid planes!");

        this.width = wrapper.getInt(4);
        this.height = wrapper.getInt(8);
        this.bitCount = wrapper.getShort(14);
        this.compression = wrapper.getInt(16);
        this.sizeImage = wrapper.getInt(20);
        this.xPelsPerMeter = wrapper.getInt(24);
        this.yPelsPerMeter = wrapper.getInt(28);
        this.clrUsed = wrapper.getInt(32);
        this.clrImportant = wrapper.getInt(36);
    }

    public byte[] getInfoHeader() {

        ByteBuffer wrapper = ByteBuffer.allocate(40);
        wrapper.putInt(size);
        wrapper.putInt(width);
        wrapper.putInt(height);
        wrapper.putShort(planes);
        wrapper.putShort(bitCount);
        wrapper.putInt(compression);
        wrapper.putInt(sizeImage);
        wrapper.putInt(xPelsPerMeter);
        wrapper.putInt(yPelsPerMeter);
        wrapper.putInt(clrUsed);
        wrapper.putInt(clrImportant);
        return wrapper.array();
    }

    public byte[] getInfoHeaderReversed() {

        ByteBuffer wrapper = ByteBuffer.allocate(40);
        wrapper.putInt(Integer.reverseBytes(size));
        wrapper.putInt(Integer.reverseBytes(width));
        wrapper.putInt(Integer.reverseBytes(height));
        wrapper.putShort(Short.reverseBytes(planes));
        wrapper.putShort(Short.reverseBytes(bitCount));
        wrapper.putInt(Integer.reverseBytes(compression));
        wrapper.putInt(Integer.reverseBytes(sizeImage));
        wrapper.putInt(Integer.reverseBytes(xPelsPerMeter));
        wrapper.putInt(Integer.reverseBytes(yPelsPerMeter));
        wrapper.putInt(Integer.reverseBytes(clrUsed));
        wrapper.putInt(Integer.reverseBytes(clrImportant));
        return wrapper.array();
    }

    public int getCompression() {

        return compression;
    }

    public int getSizeRawImage() {

        return sizeImage;
    }

    public void setSizeRawImage(int sizeImage) {

        this.sizeImage = sizeImage;
    }

    public int getWidth() {

        return width;
    }

    public void setWidth(int width) {

        this.width = width;
    }

    public int getHeight() {

        return height;
    }

    public void setHeight(int height) {

        this.height = height;
    }
}