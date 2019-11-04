package aula9_1;

import java.io.*;
import java.util.*;

public class ScannerAbeirense implements Iterator<String>, Closeable {
	
	private static Scanner sc;
	
	public ScannerAbeirense(InputStream arg) {
		
		sc=new Scanner(arg);
	}
	
	@Override
	public void close() throws IOException {
		
		sc.close();
	}
	
	@Override
	public boolean hasNext() {
		
		sc.hasNext();
		return false;
	}
	
	@Override
	public String next() {
		
		String next=sc.next();
		next=next.replace('v','b');
		next=next.replace('V','B');
		return next;
	}
	
public String nextLine() {
		
		String nextLine=sc.nextLine();
		nextLine=nextLine.replace('v','b');
		nextLine=nextLine.replace('V','B');
		return nextLine;
	}
}