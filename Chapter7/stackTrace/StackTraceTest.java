package stackTrace;

import java.util.*;

/**
 * A program that displays a trace feature of a recursive method call.
 * @version 1.01 2004-05-10
 * @author Cay Horstmann
 */
public class StackTraceTest
{
    /**
     * Computes the factorial of a number
     * @param n a non-negative interger
     * @return n!=1*2*3*...*n
     */
    public static int factorial(int n)
    {
        System.out.println("factorial(" + n + "):");
        Throwable t=new Throwable();
        StackTraceElement[] frames=t.getStackTrace();
        for(StackTraceElement f:frames)
        {
            System.out.println(f);
        }
        int r;
        if(n<=1) r=1;
        else r=n*factorial(n-1);
        System.out.println("result "+r);
        return r;
    }
    
    public static void main(String[] args)
    {
        Scanner in=new Scanner(System.in);
        System.out.println("Enter n: ");
        int n=in.nextInt();
        factorial(n);
    }
}
