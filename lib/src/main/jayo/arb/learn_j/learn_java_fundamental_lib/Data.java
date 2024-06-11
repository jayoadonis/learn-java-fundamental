package jayo.arb.learn_j.learn_java_fundamental_lib;

@SuppressWarnings("unchecked")
public class Data<T extends Comparable<? super T> & Cloneable > implements Comparable<Data<T>>, Cloneable {
    public Data( T x, T y ) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        String fmt = "[x=%s, y=%s]";
        if( this.x instanceof CharSequence )
            fmt = "[x='%s', y='%s']";
        return String.format( "%s" + fmt, super.toString(), this.x, this.y );
    }
    @Override
    public int compareTo(Data<T> data) {
        //REM: TODO-HERE; is this correct?
        return this.x.compareTo(data.x) + this.y.compareTo(data.y);
    }
//    @Override
//    public Data<T> clone() /*throws CloneNotSupportedException*/ {
//        try {
//            Data<T> clone = (Data<T>) super.clone();
//            //REM: TODO-HERE; copy mutable state here, so the clone can't change the internals of the original
//            return clone;
//        } catch (CloneNotSupportedException e) {
//            throw new AssertionError(e);
//        }
//    }
    @Override
    public Object clone() /*throws CloneNotSupportedException*/ {
        try {
            Data<T> clone = (Data<T>) super.clone();
            //REM: TODO-HERE; copy mutable state here, so the clone can't change the internals of the original
//            clone.x = this.x;
//            clone.y = this.y;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
    public T x;
    public T y;
}
