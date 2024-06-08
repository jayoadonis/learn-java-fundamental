package jayo.arb.learn_j.learn_java_fundamental.lib.swap;

public class Swap {
    /**
     * Works on any java data types
     * @param inOutData
     * @param <T>
     */
    public static <T extends Comparable<? super T>>void swapWithTemp(final Data<T> inOutData ) {
        T temp = inOutData.x;
        inOutData.x = inOutData.y;
        inOutData.y = temp;
        return;
    }

    /**
     * Only works with some primitive data type such as;
     * char, byte, short, int, & long
     * @param inOutData
     */
    public static void swapWithXOR(final Data<Long> inOutData ) {
        //REM: NOTE; There's no temp var;
        inOutData.x ^= inOutData.y;
        inOutData.y ^= inOutData.x;
        inOutData.x ^= inOutData.y;
        return;
    }

    /**
     * Only works with floating value such as; Double & Float
     * @param inOutData
     */
    public static void swapWithSumDif(final Data<Double> inOutData ) {
        //REM: NOTE; There's no temp var;
        inOutData.x += inOutData.y;
        inOutData.y = inOutData.x - inOutData.y;
        inOutData.x -= inOutData.y;
    }

    public static void execute() {
        final Data<String> DATA = new Data<String>( "Xx",  "Yy" );
        final Data<Boolean> DATA_BOOL = new Data<Boolean>( true, false );
        final Data<Long> DATA_INT = new Data<Long>( 1L, 2L );
        final Data<Double> DATA_FLOAT = new Data<Double>( 1.1D, 2.1D );
        System.out.println( DATA );
        Swap.swapWithTemp( DATA );
        System.out.println( DATA );
        System.out.println( DATA_BOOL );
        Swap.swapWithTemp( DATA_BOOL );
        System.out.println( DATA_BOOL );
        System.out.println( DATA_INT );
        Swap.swapWithTemp( DATA_INT );
        System.out.println( DATA_INT );
        System.out.println( DATA_FLOAT );
        Swap.swapWithTemp( DATA_FLOAT );
        System.out.println( DATA_FLOAT );
        System.out.println("===");
        System.out.println( DATA_INT );
        Swap.swapWithXOR( DATA_INT );
        System.out.println( DATA_INT );
        System.out.println("===");
        System.out.println( DATA_FLOAT );
        Swap.swapWithSumDif( DATA_FLOAT );
        System.out.println( DATA_FLOAT );
    }
}
