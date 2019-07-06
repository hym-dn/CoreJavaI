package lambda;

import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

/**
 * This program demonstrates the use of lambda expressions.
 * @version 1.0 2015-05-12
 * @author Cay Horstmann
 */
public class LambdaTest
{
    public static void main(String[] args)
    {
        String[] planets=new String[]{"Mercury","Venus","Earth","Mars",
            "Jupiter","Saturn","Uranus","Neptune"};
        System.out.println(Arrays.toString(planets));
        System.out.println("Sorted in dictionary order:");
        Arrays.sort(planets);
        System.out.println(Arrays.toString(planets));
        System.out.println("Sorted by length:");
        Arrays.sort(planets,(first,second)->first.length()-second.length()); /**< 能够推导出Lambda参数类型，在此可以略去参数类型 */
        System.out.println(Arrays.toString(planets));

        Timer t=new Timer(1000,event->System.out.println("The time is "+new Date())); /**< 只有一个参数，并且能够推导类型，则可以省略小括号 */
        t.start();

        // keep program running until user select "OK"
        JOptionPane.showMessageDialog(null,"Quit program?");
        System.exit(0);
    }
}