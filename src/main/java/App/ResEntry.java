package App;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ResEntry implements Collection<ResEntry> {
    List<Float> value = new LinkedList<>();
    List<Integer> key = new LinkedList<>();

    public ResEntry(Object entry) {
        value.add(Float.valueOf(entry.toString().substring(12,15)));
        value.add(Float.valueOf(entry.toString().substring(19,22)));
        value.add(Float.valueOf(entry.toString().substring(26,29)));
        key.add(Integer.valueOf(entry.toString().substring(41,44)));
        key.add(Integer.valueOf(entry.toString().substring(45,48)));

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<ResEntry> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(ResEntry resEntry) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends ResEntry> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }
}
