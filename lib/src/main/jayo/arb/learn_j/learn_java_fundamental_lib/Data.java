package jayo.arb.learn_j.learn_java_fundamental_lib;

public class Data<T extends Comparable<? super T>> {
    public Data( T x, T y ) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        String fmt = "[x=%s, y=%s]";
        if( this.x instanceof CharSequence )
            fmt = "[x='%s', y='%s']";
        return String.format( fmt, this.x, this.y );
    }
    public T x;
    public T y;
}
