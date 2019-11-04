package aula7_2;

public class Pixel {
	
	private byte R;
	private byte G;
	private byte B;
	
	public Pixel(byte B, byte G, byte R) {
		
		this.R = R;
		this.G = G;
		this.B = B;
	}
	
	public Pixel(byte[] arr) {
		
		this.R = arr[2];
		this.G = arr[1];
		this.B = arr[0];
	}
	
	public byte[] getBytes() {
		
		return new byte[]{this.R, this.G, this.B};
	}
	
	public byte[] getBytesReversed() {
		
		return new byte[]{this.B, this.G, this.R};
	}
	
	@Override public Pixel clone() {
		
		return new Pixel(B, G, R);
	}
}