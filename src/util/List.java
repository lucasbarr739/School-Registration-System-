package util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic list backed by a growable array.
 * Initial capacity is 4 and grows by 4 when full.
 * This class is used as the base class for StudentList and Schedule.
 */
public class List<E> implements Iterable<E> {
    protected Object[] objects;
    protected int size;

    public List() {
        this.objects = new Object[4];
        this.size = 0;
    }

    /** Find the index of an object using equals(). */
    private int find(E e) {
        if (e == null) return -1;
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            E cur = (E) objects[i];
            if (e.equals(cur)) return i;
        }
        return -1;
    }

    /** Grow backing array by 4. */
    private void grow() {
        Object[] tmp = new Object[objects.length + 4];
        for (int i = 0; i < size; i++) tmp[i] = objects[i];
        objects = tmp;
    }

    public boolean contains(E e) {
        return find(e) != -1;
    }

    public boolean add(E e) {
        if (e == null) return false;
        if (contains(e)) return false;
        if (size == objects.length) grow();
        objects[size++] = e;
        return true;
    }

    public boolean remove(E e) {
        int idx = find(e);
        if (idx == -1) return false;
        for (int i = idx; i < size - 1; i++) {
            objects[i] = objects[i + 1];
        }
        objects[size - 1] = null;
        size--;
        return true;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int indexOf(E e) {
        return find(e);
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (E) objects[index];
    }

    public void set(int index, E e) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        objects[index] = e;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<E> {
        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            @SuppressWarnings("unchecked")
            E val = (E) objects[cursor++];
            return val;
        }
    }
}
