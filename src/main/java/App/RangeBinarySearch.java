package App;

import java.util.Comparator;

/**
 * This class is used to find the first instance of a key inside an Array
 * @author Lukas Wigren
 * @version 1.0
 * @since 2022-10-05
 */
public class RangeBinarySearch {
    /**
     * Looks for the first instance of the key in the array, using comparator
     * @param a the Array with to look for the key in
     * @param key   the key to look for
     * @param comparator    the comparator used
     * @param <T>   the class type
     * @return  the index of the first matching key
     */
    public static<T> int firstIndexOf(T[] a, T key, Comparator<T> comparator) {
        int low = 0;
        int high = a.length - 1;
        int result = -1;
        while(low <= high) {
            int mid = (low+high)/2;
            if (comparator.compare(key, a[mid]) == 0) {
                result = mid;
                high = mid-1;
            } else if (comparator.compare(key, a[mid]) < 0) {
                high = mid-1;
            } else {
                low = mid+1;
            }
        }
        return result;
    }

    /**
     * Looks for the last instance of the key in the array, using comparator
     * @param a the Array with to look for the key in
     * @param key   the key to look for
     * @param comparator    the comparator used
     * @param <T>   the class type
     * @return  the index of the last matching key
     */
    public static<T> int lastIndexOf(T[] a, T key, Comparator<T> comparator) {
        int low = 0;
        int high = a.length -1;
        int result = -1;
        while(low <= high) {
            int mid = (low+high)/2;
            if (comparator.compare(key, a[mid]) == 0) {
                result = mid;
                low = mid+1;
            } else if (comparator.compare(key, a[mid]) > 0) {
                low = mid+1;
            } else {
                high = mid-1;
            }
        }
        return result;
    }
}
