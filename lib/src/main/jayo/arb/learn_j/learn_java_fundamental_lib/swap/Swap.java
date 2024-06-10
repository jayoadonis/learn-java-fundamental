package jayo.arb.learn_j.learn_java_fundamental_lib.swap;

import jayo.arb.learn_j.learn_java_fundamental_lib.Blue;
import jayo.arb.learn_j.learn_java_fundamental_lib.Color;
import jayo.arb.learn_j.learn_java_fundamental_lib.Data;
import org.jetbrains.annotations.NotNull;

public class Swap {
    /**
     * Works on any java data types
     * @param inOutData
     * @param <T>
     */
    public static <T extends Comparable<? super T> & Cloneable >void swapWithTemp(final Data<T> inOutData ) {
        T temp = inOutData.x;
        inOutData.x = inOutData.y;
        inOutData.y = temp;
        return;
    }

//    /**
//     * Only works with some primitive data type such as;
//     * char, byte, short, int, & long
//     * @param inOutData
//     */
//    @SuppressWarnings("unchecked")
//    public static <T extends Long & Cloneable >void swapWithXOR(final Data<T> inOutData ) {
//        //REM: NOTE; There's no temp var;
//        inOutData.x = (T) Long.valueOf( (inOutData.x.longValue() ^ inOutData.y.longValue()) );
//        inOutData.y = (T) Long.valueOf( (inOutData.x.longValue() ^ inOutData.y.longValue()) );
//        inOutData.x = (T) Long.valueOf( (inOutData.x.longValue() ^ inOutData.y.longValue()) );
//        return;
//    }

//    /**
//     * Only works with floating value such as; Double & Float
//     * @param inOutData
//     */
//    @SuppressWarnings("unchecked")
//    public static <T extends Double & Cloneable> void swapWithSumDif(final Data<T> inOutData ) {
//        //REM: NOTE; There's no temp var;
//        inOutData.x = (T)Double.valueOf( ( inOutData.x.doubleValue() + inOutData.y.doubleValue() ) );
//        inOutData.y = (T)Double.valueOf( ( inOutData.x.doubleValue() - inOutData.y.doubleValue() ) );
//        inOutData.x = (T)Double.valueOf( ( inOutData.x.doubleValue() - inOutData.y.doubleValue() ) );
//    }

    public static void execute() {
        final Data<StringX> DATA = new Data<StringX>( new StringX("Xx"),  new StringX("Yyes") );
        final Data<Color> DATA_C = new Data<>( new Blue(), new Color() );
//        final Data<Boolean> DATA_BOOL = new Data<Boolean>( true, false );
//        final Data<Long> DATA_INT = new Data<Long>( 1L, 2L );
//        final Data<Double> DATA_FLOAT = new Data<Double>( 1.1D, 2.1D );
        System.out.println( "> " + DATA );
        Swap.swapWithTemp( DATA );
        System.out.println( "> " + DATA );
        System.out.println( "> " + DATA_C );
        Swap.swapWithTemp( DATA_C );
        System.out.println( "> " + DATA_C );
//        System.out.println( DATA_BOOL );
//        Swap.swapWithTemp( DATA_BOOL );
//        System.out.println( DATA_BOOL );
//        System.out.println( DATA_INT );
//        Swap.swapWithTemp( DATA_INT );
//        System.out.println( DATA_INT );
//        System.out.println( DATA_FLOAT );
//        Swap.swapWithTemp( DATA_FLOAT );
//        System.out.println( DATA_FLOAT );
//        System.out.println("===");
//        System.out.println( DATA_INT );
//        Swap.swapWithXOR( DATA_INT );
//        System.out.println( DATA_INT );
//        System.out.println("===");
//        System.out.println( DATA_FLOAT );
//        Swap.swapWithSumDif( DATA_FLOAT );
//        System.out.println( DATA_FLOAT );
    }


}

class StringX implements Comparable<StringX>, CharSequence, Cloneable {

    public String str;

    public StringX( @NotNull String str ) {
        this.str = str;
    }

    @Override
    public int compareTo(StringX strX) {
        return this.str.compareTo(strX.str);
    }
    @Override
    public int length() {
        return this.str.length();
    }

    @Override
    public char charAt(int i) {
        return this.str.charAt(i);
    }

    @Override
    @NotNull
    public CharSequence subSequence(int i, int i1) {
        return this.str.subSequence(i, i1);
    }

    @Override
    public StringX clone() throws CloneNotSupportedException {
        return (StringX) super.clone();
    }

    @Override
    @NotNull
    public String toString() {
        return this.str;
    }
}
