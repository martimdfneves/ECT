package aula7_2;

import java.nio.*;

public class BitmapFileHeader {

    private static final short type = 19778;
    private int size;
    private static final short reserved1 = 0;
    private static final short reserved2 = 0;
    private int offBits;

    public BitmapFileHeader(int size, int offBits) {

        this.size = size;
        this.offBits = offBits;
    }

    public BitmapFileHeader(byte[] array) {

        ByteBuffer wrapper = ByteBuffer.wrap(array);

        if (wrapper.getShort(0) != type)
            throw new IllegalArgumentException("Type is invalid!");

        this.size = wrapper.getInt(2);
        this.offBits = wrapper.getInt(10);
    }

    public byte[] getFileHeader() {

        ByteBuffer wrapper = ByteBuffer.allocate(14);
        wrapper.putShort(type);
        wrapper.putInt(size);
        wrapper.putShort(reserved1);
        wrapper.putShort(reserved2);
        wrapper.putInt(offBits);
        return wrapper.array();
    }

    public byte[] getFileHeaderReversed() {

        ByteBuffer wrapper = ByteBuffer.allocate(14);
        wrapper.putShort(Short.reverseBytes(type));
        wrapper.putInt(Integer.reverseBytes(size));
        wrapper.putShort(Short.reverseBytes(reserved1));
        wrapper.putShort(Short.reverseBytes(reserved2));
        wrapper.putInt(Integer.reverseBytes(offBits));
        return wrapper.array();
    }

    public int getSize() {

        return this.size;
    }

    public void setSize(int size) {

        this.size = size;
    }

    public int getoffBits() {

        return this.offBits;
    }
}