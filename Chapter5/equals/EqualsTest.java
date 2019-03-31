package equals;

/**
 * This program demonstrates the equals method.
 * @version 1.12 2012-01-26
 * @author Cay Horstmann
 */
public class EqualsTest
{
    public static void main(String[] args)
    {
        Employee alice1=new Employee("Alice Adams",75000,1987,12,15);
        Employee alice2=alice1;
        Employee alice3=new Employee("Alice Adams",75000,1987,12,15);
        Employee bob=new Employee("Bob Brandson",50000,1989,10,1);
    }
}