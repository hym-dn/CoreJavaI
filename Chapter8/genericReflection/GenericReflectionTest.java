package genericReflection;

import java.lang.reflect.*;
import java.util.*;

/**
 * @version 1.10 2007-05-15
 * @author Cay Horstmann
 */
public class GenericReflectionTest
{
	public static void main(String[] args)
	{
		// read class name from command line args or user input
		String name;
		if(args.length>0) name=args[0];
		else
		{
			try(Scanner in=new Scanner(System.in))
			{
				System.out.println("Enter class name (e.g. java.util.Collections): ");
				name=in.next();
			}
		}

		try
		{
			// print generic info for class and public methods
			Class<?> cl=Class.forName(name);
			printClass(cl);
			for(Method m:cl.getDeclaredMethods())
				printMethod(m);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	// 打印类型信息
	public static void printClass(Class<?> cl)
	{
		System.out.print(cl);
		printTypes(cl.getTypeParameters(),"<",", ",">",true); // 获取泛型类型变量，并且打印出来
		Type sc=cl.getGenericSuperclass(); // 获得被声明为这一类型的超类的泛型类型; 如果这个类型是 Object 或不是一个类类型(classtype), 则返回null
		if(sc!=null)
		{
			System.out.println(" extends ");
			printType(sc,false);
		}
		printTypes(cl.getGenericInterfaces()," implements ",", ","",false);
		System.out.println();
	}

	// 打印方法
	public static void printMethod(Method m)
	{
		String name=m.getName();
		System.out.print(Modifier.toString(m.getModifiers()));
		System.out.print(" ");
		printTypes(m.getTypeParameters(),"<",", ",">",true);
		printType(m.getGenericReturnType(),false);
		System.out.print(" ");
		System.out.print(name);
		System.out.print("(");
		printTypes(m.getGenericParameterTypes(),"",", ","",false);
		System.out.println(")");
	}

	// 打印泛型变量
	public static void printTypes(Type[] types,String pre,String sep,String suf,boolean isDefinition)
	{
		if(pre.equals(" extend ")&&Arrays.equals(types,new Type[]{Object.class})) return;
		if(types.length>0) System.out.print(pre);
		for(int i=0;i<types.length;i++)
		{
			if(i>0) System.out.print(sep);
			printType(types[i],isDefinition); // 打印类型
		}
		if(types.length>0) System.out.print(suf);
	}

	// 打印类型
	public static void printType(Type type,boolean isDefinition)
	{
		// 类类型
		if(type instanceof Class)
		{
			Class<?> t=(Class<?>) type;
			System.out.print(t.getName());
		}
		// 类型变量
		else if(type instanceof TypeVariable)
		{
			TypeVariable<?> t=(TypeVariable<?>) type;
			System.out.print(t.getName());
			if(isDefinition)
				printTypes(t.getBounds()," extend "," & ","",false); 
		}
		// 通配符
		else if(type instanceof WildcardType)
		{
			WildcardType t=(WildcardType) type;
			System.out.print("?");
			printTypes(t.getUpperBounds()," extend "," & ","",false);
			printTypes(t.getLowerBounds()," super "," & ","",false);
		}
		// 描述泛型类或接口类
		else if(type instanceof ParameterizedType)
		{
			ParameterizedType t=(ParameterizedType) type;
			Type owner=t.getOwnerType();
			if(owner!=null)
			{
				printType(owner,false);
				System.out.print(".");
			}
			printType(t.getRawType(),false);
			printTypes(t.getActualTypeArguments(),"<",", ",">",false);
		}
		// 描述泛型数组
		else if(type instanceof GenericArrayType)
		{
			GenericArrayType t=(GenericArrayType) type;
			System.out.print("");
			printType(t.getGenericComponentType(),isDefinition);
			System.out.print("[]");
		}
	}

}