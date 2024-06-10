package jayo.arb.learn_j.learn_java_fundamental_lib.array;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class DemoOneDimension<T extends Comparable<? super T> & Cloneable > extends OneDimension<T> {

    public DemoOneDimension( T[] oneDimensionArray ) {
        this.oneDimensionArray = oneDimensionArray;
        this.size = oneDimensionArray.length;
        this.capacity = this.size;
    }
    public DemoOneDimension( DemoOneDimension<T> otherDemoOneDimension ) {
        this.oneDimensionArray = (T[]) new Comparable[otherDemoOneDimension.oneDimensionArray.length + 5];
        for( int i = 0; i < otherDemoOneDimension.oneDimensionArray.length; ++i )
            this.oneDimensionArray[i] = otherDemoOneDimension.oneDimensionArray[i];
        this.capacity = this.oneDimensionArray.length;
        this.size = otherDemoOneDimension.oneDimensionArray.length;
    }
    @Override
    public List<T> getOneDimension() {
        final List<T> NEW_ONE_DIMENSION = new ArrayList<>();
        try {
            for (int i = 0; i < this.size; ++i) {
                T item = this.oneDimensionArray[i];
//                NEW_ONE_DIMENSION.add(item);
                NEW_ONE_DIMENSION.add((T)item.getClass().getMethod("clone").invoke(item));
            }
        }
        catch(
                NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                SecurityException e
        ) {
            throw new AssertionError(e);
        }
        finally { }
        return NEW_ONE_DIMENSION;
    }

    public boolean add( T item ) {
        if( this.ensureCapacity() ) {
            this.oneDimensionArray[this.size++] = item;
            return true; //REM: TODO-HERE; properly implement the error handling...
        }
        return false;
    }

    public T remove( int i ) {
        if( i >= 0 && i < this.size ) {
            final T TO_BE_REMOVE_ITEM = this.oneDimensionArray[i];
            for( int j = i; j < this.size; ++j ) {
                this.oneDimensionArray[j] = this.oneDimensionArray[j+1];
            }
            this.size--;
            return TO_BE_REMOVE_ITEM;
        }
        return null;
    }

    private boolean ensureCapacity() {
        if( this.size == this.capacity ) {
            try {
                final T[] NEW_ONE_DIMENSION = (T[]) new Comparable[this.capacity + 5];
                for (int i = 0; i < this.size; ++i)
                    NEW_ONE_DIMENSION[i] = this.oneDimensionArray[i];
                this.capacity = NEW_ONE_DIMENSION.length;
                this.oneDimensionArray = NEW_ONE_DIMENSION;
            }
            catch( Exception e ) {
                return false;
            }
        }
        return true;
    }


    private T[] oneDimensionArray;
    private int capacity;
    private int size;
}
