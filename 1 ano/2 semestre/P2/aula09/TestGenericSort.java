import static java.lang.System.*;
import java.util.Scanner;
import java.util.Arrays;

import static p2utils.Sorting.*;

public class TestGenericSort {
	
	static final Scanner input = new Scanner(System.in);
	
	public static void main (String[] args) {
    args = readInts(args);
    
    for (int i = 0; i < args.length; i++) {
		args[i]=args[i].toUpperCase();
	}
	

    // Sort the first n elements in the array with our algorithm:
    mergeSort(args, 0, args.length);
    
    // Print the result:
    out.println(Arrays.toString(args));
  }

  static final int BLOCK = 1024;

  // Read integers from the input scanner.
  // Returns the integers in an array.
  public static String[] readInts(String[] input) {
    String[] array = new String[input.length];
    int n = 0;
    while (n<input.length) {
      String value = input[n];
      if (n == array.length) // if array full, enlarge it
        array = Arrays.copyOf(array, array.length+BLOCK);
      // store each integer in the next available position:
      array[n] = value;
      n++;
    }
    array = Arrays.copyOf(array, n);  // readjust length to match n
    return array;
  }
	}


