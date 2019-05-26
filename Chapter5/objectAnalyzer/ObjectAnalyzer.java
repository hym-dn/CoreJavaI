package objectAnalyzer;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ArrayList;

public class objectAnalyzer
{
    private ArrayList<Object> visited=new ArrayList<>();

    /**
     * Convert an object to a string representation that lists all fields.
     * @param obj an object
     * @return a string with the object's class name and all field names and values.
     */
    public String toString(Object obj)
    {
        /**< 如果出传入实例为空 */
        if(obj==null)
        {
            return "null";
        }
        /**< 如果已经转译过当前实例 */
        if(visited.contains(obj))
        {
            return "...";
        }
        /**< 记录转移过的当前实例 */
        visited.add(obj);
        /**< 获取实例描述类 */
        Class cl=obj.getClass();
        /**< 如果实例为字符串 */
        if(cl==String.class)
        {
            return (String)obj;
        }
        /**< 如果实例为数组 */
        if(cl.isArray())
        {
            String r=cl.getComponentType()+"[]{";
            /**< 遍历数组 */
            for(int i=0;i<Array.getLength(obj);i++)
            {
                /**< 元素以逗号分割 */
                if(i>0)
                {
                    r+=",";
                }
                /**< 取元素 */
                Object val=Array.get(obj,i);
                /**< 内置类型 */
                if(cl.getComponentType().isPrimitive())
                {
                    r+=val;
                }
                /**< 非内置类型 */
                else
                {
                    r+=toString(val);
                }
            }
            /**< 返回数组内容 */
            return r+"}";
        }
        /**< 非数组类型 */
        String r=cl.getName(); // 获取类的名称
        // inspect the fields of this class and all superclasses
        do
        {
            /**< 增加提示符 */
            r+="[";
            /**< 获取类的域 */
            Field[] fields=cl.getDeclaredFields();
            /**< 设置域数组的可访问性 */
            AccessibleObject.setAccessible(fields,true);
            // 遍历域
            // get the names and values of all fields
            for(Field f:fields)
            {
                /**< 如果并非静态域 */
                if(!Modifier.isStatic(f.getModifiers()))
                {
                    /**< 如果并非第一个域 */
                    if(!r.endsWith("["))
                    {
                        r+=",";
                    }
                    /**< 连接域描述字符串 */
                    r+=f.getName()+"=";
                    try
                    {
                        Class t=f.getType();
                        Object val=f.get(obj);
                        if(t.isPrimitive())
                        {
                            r+=val;
                        }
                        else
                        {
                            r+=toString(val);
                        }
                    }
                    catch(Expection e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            /**< 链接结束字符 */
            r+="]";
            /**< 获取超类内容 */
            cl=cl.getSuperclass();
        }
        while(cl!=null);
        /**< 返回 */
        return r;
    }
}