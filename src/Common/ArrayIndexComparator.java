package Common;

import java.util.Comparator;


/**
 * Created by raven on 09.12.2016.
 */
public class ArrayIndexComparator implements Comparator<Integer>
{
    private final Float[] array;

    public ArrayIndexComparator(Float[] array)
    {
        this.array = array;
    }

    public Integer[] createIndexArray()
    {
        Integer[] indexes = new Integer[array.length];
        for (int i = 0; i < array.length; i++)
        {
            indexes[i] = i; // Autoboxing
        }
        return indexes;
    }

    @Override
    public int compare(Integer index1, Integer index2)
    {
        // Autounbox from Integer to int to use as array indexes
        return array[index1].compareTo(array[index2]);
    }
}
