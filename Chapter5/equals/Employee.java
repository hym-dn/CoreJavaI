package equals;

import java.sql.Date;
import java.time.*;
import java.util.GregorianCalendar;
import java.util.Objects;

class Employee
{
    private String name;
    private double salary;
    private Date hireDay;

    public Employee(String n,double s,int year,int month,int day)
    {
        name=n;
        salary=s;
        GregorianCalendar calendar=new GregorianCalendar(year,month-1,day);
        hireDay=calendar.getTime();
    }

    public String getName()
    {
        return name;
    }

    public double getSalary()
    {
        return salary;
    }

    public Date getHireDay()
    {
        return hireDay;
    }

    public void raiseSalary(double byPercent)
    {
        double raise=salary*byPercent/100;
        salary+=raise;
    }

    public boolean equals(Object otherObject)
    {
        // a quick test to see if the objects are identica
        if(this==otherObject) return true;

        // must return false if the explicit parameter is null
        if(otherObject==null) return false;

        // if the classes don't match,they can't be equal
        if(getClass()!=otherObject.getClass()) return false;
        
        // now we know otherObject is a non-null Employee
        Employee other=(Employee) otherObject;

        // test whether the fields have identical values
        return name.equals(other.name) && salary==other.salary && hireDay.equals(other.hireDay);
    }

    public int hashCode()
    {
        return 7*name.hashCode()+11*new Double(salary).hashCode()+13*hireDay.hashCode();
    }

    public String toString()
    {
        return getClass().getName()+"[name="+name+".salary="+salary+",hireDay="+hireDay+"]";
    }
}