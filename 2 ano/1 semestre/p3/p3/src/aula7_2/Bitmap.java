package aula7_2;

import java.nio.*;
import java.io.*;

public class Bitmap {
	
	private BitmapFileHeader bitmapFileHeader;
	private BitmapInfoHeader bitmapInfoHeader;
	private Pixel[][] pixelData; 				
	
	public Bitmap(BitmapFileHeader bitmapFileHeader, BitmapInfoHeader bitmapInfoHeader, Pixel[][] newpixelData) {
		
		this.bitmapFileHeader = bitmapFileHeader;
		this.bitmapInfoHeader = bitmapInfoHeader;
		this.pixelData = newpixelData;
	}

	public Bitmap(String bitmapfile) {
	
		try(RandomAccessFile file = new RandomAccessFile(bitmapfile, "rw")) {

			ByteBuffer fileHeader = ByteBuffer.allocate(14);
			
			fileHeader.putShort(Short.reverseBytes(file.readShort()));
			fileHeader.putInt(Integer.reverseBytes(file.readInt()));
			fileHeader.putShort(Short.reverseBytes(file.readShort()));
			fileHeader.putShort(Short.reverseBytes(file.readShort()));
			fileHeader.putInt(Integer.reverseBytes(file.readInt()));
			
			bitmapFileHeader = new BitmapFileHeader(fileHeader.array());

			ByteBuffer infoHeader = ByteBuffer.allocate(40);
			
			int i;
			for(i=0; i<3; i++)
				infoHeader.putInt(Integer.reverseBytes(file.readInt()));
			
			infoHeader.putShort(Short.reverseBytes(file.readShort()));
			infoHeader.putShort(Short.reverseBytes(file.readShort()));
			
			for(i=0; i<6; i++)
				infoHeader.putInt(Integer.reverseBytes(file.readInt()));
			
			bitmapInfoHeader = new BitmapInfoHeader(infoHeader.array());
			
			byte[] data = new byte[Math.abs(bitmapInfoHeader.getHeight()*bitmapInfoHeader.getWidth()*3)];
			file.read(data);

			pixelData = new Pixel[Math.abs(bitmapInfoHeader.getHeight())][Math.abs(bitmapInfoHeader.getWidth())];
			
			int t = 0;
			int j;
			for(i = 0; i < pixelData.length; i++)
				for(j = 0; j < pixelData[i].length; j++)
					pixelData[i][j] = new Pixel(data[t++], data[t++], data[t++]);
			
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void write(String dst) {
		
		try (RandomAccessFile file = new RandomAccessFile(dst, "rw")) {
			
			file.write(bitmapFileHeader.getFileHeaderReversed());
			file.write(bitmapInfoHeader.getInfoHeaderReversed());

			int i, j;
			for(i = 0; i < pixelData.length; i++)
				for(j = 0; j < pixelData[i].length; j++)
					if(pixelData[i][j]!=null)
						file.write(pixelData[i][j].getBytesReversed());
			
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void resize025(String dst) {
		
		Bitmap resized = new Bitmap(new BitmapFileHeader(bitmapFileHeader.getFileHeader()), 
				new BitmapInfoHeader(bitmapInfoHeader.getInfoHeader()), pixelData.clone());
		
		resized.bitmapInfoHeader.setWidth((int) (resized.bitmapInfoHeader.getWidth()*0.5));
		resized.bitmapInfoHeader.setHeight((int) (resized.bitmapInfoHeader.getHeight()*0.5));
		int proportion = 2;
		
		Pixel[][] newPixelData = new Pixel[Math.abs(resized.bitmapInfoHeader.getHeight())][Math.abs(resized.bitmapInfoHeader.getWidth())];
		int ci = 1;
		int cj = 1;
		int i, j, ik, jk;
		for(ik = 0, i = 0; i < pixelData.length && ik < newPixelData.length; i++, cj = 1) {
			
			if(ci == proportion) {
				
				ci = 1;
				continue;
			}
			else {
				
				for(jk = 0, j = 0; j < pixelData[i].length && jk < newPixelData[ik].length; j++) {
					
	    			if(cj==proportion)
	    				cj = 1;
	    			else {
	    				
	    				newPixelData[ik][jk] = resized.pixelData[i][j];
	    				cj++;
	    				jk++;
	    			}
	    		}
				ik++;
				ci++;
			}
		}
		
		resized.bitmapFileHeader.setSize(Math.abs(resized.bitmapInfoHeader.getWidth()*3*resized.bitmapInfoHeader.getHeight()));
		resized.pixelData = newPixelData;
		resized.write(dst);
	}

	public void flipHorizontal(String dst) {
		
		Bitmap fliped = new Bitmap(new BitmapFileHeader(bitmapFileHeader.getFileHeader()), 
				new BitmapInfoHeader(bitmapInfoHeader.getInfoHeader()), pixelData.clone());
		Pixel[][] newPixelData = new Pixel[Math.abs(fliped.bitmapInfoHeader.getHeight())][Math.abs(fliped.bitmapInfoHeader.getWidth())];
		
		int i, j, ik;
		for(i = pixelData.length-1, ik = 0; i >= 0; i--, ik++)
			for(j = 0; j < pixelData[i].length; j++)
				newPixelData[ik][j] = pixelData[i][j].clone();
		
		fliped.pixelData = newPixelData;
		fliped.write(dst);
	
	}

	public void flipVertical(String dst) {
		
		Bitmap fliped = new Bitmap(new BitmapFileHeader(bitmapFileHeader.getFileHeader()), 
				new BitmapInfoHeader(bitmapInfoHeader.getInfoHeader()), pixelData.clone());
		
		Pixel[][] newPixelData = new Pixel[Math.abs(fliped.bitmapInfoHeader.getHeight())][Math.abs(fliped.bitmapInfoHeader.getWidth())];
		
		int i, j, jk;
		for(i = 0; i < newPixelData.length; i++)
			for(j = newPixelData[i].length-1, jk = 0; j >= 0; j--, jk++)
				newPixelData[i][j] = pixelData[i][jk].clone();
		
		fliped.pixelData = newPixelData;
		fliped.write(dst);
	}

	public void getBmpRaw(String dst) {
		
		try (RandomAccessFile file = new RandomAccessFile(dst, "rw")) {
			
			int i, j;
			for(i = 0; i < pixelData.length; i++)
				for(j = 0; j < pixelData[i].length; j++)
					file.write(pixelData[i][j].getBytes()); 
			
			file.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}