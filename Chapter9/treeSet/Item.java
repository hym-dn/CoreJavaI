package treeSet;

import java.util.*;

/**
 * An item with a description and a part number.
 */
public class Item implements Comparable<Item>
{
	private String description;
	private int partNumber;

    /**
     * Constructs an item.
     *
     * @param aDescription
     * the item's description * Iparam aPartNumber
     * the item's part number V
	 */
	public Item(String aDescription,int aPartNumber)
	{
		description=aDescription;
		partNumber=aPartNumber;
	}

	/*
	 * Gets the description of this item. 
	 *
     * ©return the descriptionV
	 */
	public String getDescription()
	{
		return description;
	}
	
	public String toString()
	{
		return "[description="+description+", partNumber="+partNumber+"]";
	}

	public int hashCode()
	{
		return Objects.hash(description,partNumber);
	}

	public int compareTo(Item other)
	{
		int diff=Integer.compare(partNumber,other.partNumber);
		return diff!=0?diff:description.compareTo(other.description);
	}
}