public class AllDivisors
{
  public AllDivisors() {}
  
  public static void main(String[] paramArrayOfString) {
    if (paramArrayOfString.length != 1)
    {
      System.err.println("Usage: java -ea AllDivisors <NUM>");
      System.exit(1);
    }
    int i = 0;
    try
    {
      i = Integer.parseInt(paramArrayOfString[0]);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      System.err.println("ERROR: argument " + paramArrayOfString[0] + " is not an integer number!");
      System.exit(2);
    }
    if (i < 1)
    {
      System.err.println("ERROR: argument " + paramArrayOfString[0] + " is not a positive number!");
      System.exit(3);
    }
    writeDivisors("", i);
  }
  


  static void writeDivisors(String paramString, int paramInt)
  {
    assert (paramString != null);
    assert (paramInt > 0);
    
    System.out.println(paramString + paramInt);
    for (int i = paramInt - 1; i >= 2; i--)
    {
      if (paramInt % i == 0) {
        writeDivisors(paramString + "   ", i);
      }
    }
  }
}
