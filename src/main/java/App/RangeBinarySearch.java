package App;

import java.util.Comparator;

public class RangeBinarySearch {
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
