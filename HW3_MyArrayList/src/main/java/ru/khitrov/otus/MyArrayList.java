package ru.khitrov.otus;

import java.util.*;
import java.util.function.Consumer;

public class MyArrayList <T> implements List<T>  {


    protected Object[] elementData; // non-private to simplify nested class access

    /**
     * The size of the ArrayList (the number of elements it contains).
     */
    private int size;

    /**
     * Default initial capacity.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Shared empty array instance used for default sized empty instances. We
     * distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when
     * first element is added.
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * The maximum size of array to allocate (unless necessary).
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public MyArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }


    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param  initialCapacity  the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *         is negative
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }
    }



    /**
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     * @throws OutOfMemoryError if minCapacity is less than zero
     */
    private Object[] grow(int minCapacity) {
        return elementData = Arrays.copyOf(elementData,
                newCapacity(minCapacity));
    }

    private Object[] grow() {
        return grow(size + 1);
    }

    /**
     * Returns a capacity at least as large as the given minimum capacity.
     * Returns the current capacity increased by 50% if that suffices.
     * Will not return a capacity greater than MAX_ARRAY_SIZE unless
     * the given minimum capacity is greater than MAX_ARRAY_SIZE.
     *
     * @param minCapacity the desired minimum capacity
     * @throws OutOfMemoryError if minCapacity is less than zero
     */
    private int newCapacity(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity <= 0) {
            if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
                return Math.max(DEFAULT_CAPACITY, minCapacity);
            if (minCapacity < 0) // overflow
                throw new OutOfMemoryError();
            return minCapacity;
        }
        return (newCapacity - MAX_ARRAY_SIZE <= 0)
                ? newCapacity
                : hugeCapacity(minCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE)
                ? Integer.MAX_VALUE
                : MAX_ARRAY_SIZE;
    }




    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
      }

    @Override
    public boolean contains(Object o) {
//        return false;
        throw new RuntimeException();

    }


    @SuppressWarnings("unchecked")
    private class Itr implements ListIterator<T>{

        private int currentIndex = 0;
        int lastRet = -1; // index of last element returned; -1 if no such

        @Override
        public boolean hasNext() {
            return currentIndex != size;
        }

        @Override
        public T next() {
            lastRet = currentIndex;
            return (T) elementData[currentIndex++];
        }

        @Override
        public boolean hasPrevious() {
             return currentIndex != 0;
        }

        @Override
        public T previous() {
            int i = currentIndex - 1;
            if (i < 0)
                throw new NoSuchElementException();

            if (i >= elementData.length)
                throw new ConcurrentModificationException();

            lastRet = currentIndex;
            currentIndex--;
            return (T) elementData[currentIndex];
        }

        @Override
        public int nextIndex() {
            return currentIndex;
        }

        @Override
        public int previousIndex() {
            return currentIndex - 1;
        }

        @Override
        public void remove() {
            throw new RuntimeException();
        }

        @Override
        public void set(T t) {
            try {
                MyArrayList.this.set(lastRet, t);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void add(T t) {
            throw new RuntimeException();
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            Objects.requireNonNull(action);
            int i = currentIndex;

            for (;i < size; i++){
                action.accept(elementAt(elementData, i));
            }

            lastRet = i - 1;
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public Iterator<T> iterator() {
        return (Iterator <T>) new  Itr();
    }


    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
//        return null;
        throw new RuntimeException();

    }


    private void add(T t, Object[] elementData, int s) {
        if (s == elementData.length)
            elementData = grow();
        elementData[s] = t;
        size = s + 1;
    }


    @Override
    public boolean add(T t) {
        add(t, elementData, size);
        return true;
    }

    @Override
    public boolean remove(Object o) {
//        return false;
        throw new RuntimeException();

    }

    @Override
    public boolean containsAll(Collection<?> c) {
//        return false;
        throw new RuntimeException();

    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
//        return false;
        throw new RuntimeException();

    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
//        return false;
        throw new RuntimeException();

    }

    @Override
    public boolean removeAll(Collection<?> c) {
  //      return false;
        throw new RuntimeException();

    }

    @Override
    public boolean retainAll(Collection<?> c) {
//        return false;
        throw new RuntimeException();

    }

    @Override
    public void clear() {
        throw new RuntimeException();
    }

    @SuppressWarnings("unchecked")
     private T elementData(int index) {
        return (T) elementData[index];
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return elementData(index);
    }

    @Override
    public T set(int index, T t) {
        Objects.checkIndex(index, size);
        T oldValue = elementData(index);
        elementData[index] = t;
        return oldValue;

    }

    @Override
    public void add(int index, T element) {
        throw new RuntimeException();
    }

    @Override
    public T remove(int index) {
//        return null;
        throw new RuntimeException();

    }

    @Override
    public int indexOf(Object o) {
 //       return 0;
        throw new RuntimeException();

    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size-1; i >= 0; i--)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = size-1; i >= 0; i--)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }


    @Override
    public ListIterator<T> listIterator() {
        return  new Itr();

    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new RuntimeException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
//        return null;
        throw new RuntimeException();

    }


    @SuppressWarnings("unchecked")
    static <E> E elementAt(Object[] es, int index) {
        return (E) es[index];
    }

    /**
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public void forEach(Consumer<? super T>  action) {
        Objects.requireNonNull(action);

        final Object[] es = elementData;
        final int size = this.size;
        for (int i = 0;  i < size; i++)
            action.accept(elementAt(es, i));
    }



}
