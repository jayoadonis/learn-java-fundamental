package jayo.arb.learn_j.learn_java_fundamental_app;

import jayo.arb.learn_j.learn_java_fundamental_lib.Blue;
import jayo.arb.learn_j.learn_java_fundamental_lib.Color;
import jayo.arb.learn_j.learn_java_fundamental_lib.Data;
import jayo.arb.learn_j.learn_java_fundamental_lib.Red;
import jayo.arb.learn_j.learn_java_fundamental_lib.array.DemoOneDimension;
import jayo.arb.learn_j.learn_java_fundamental_lib.array.OneDimension;
import jayo.arb.learn_j.learn_java_fundamental_lib.swap.Swap;

import java.util.List;

@SuppressWarnings("unchecked")
public class MainExe {

    public static void main(String[] args) {
        System.out.println("Hi there from: learn-java-fundamental");
        long begin = System.nanoTime();
        OneDimension<Color> x = new DemoOneDimension<>(new Color[]{ new Blue(), new Color() });
        OneDimension<Data<Color>> y = new DemoOneDimension<Data<Color>>(
                new Data[]{
                        new Data<Color>(x.getOneDimension().get(0), x.getOneDimension().get(1)),
                        new Data<Color>( new Blue(), new Blue())
                }
        );
        ((DemoOneDimension)x).add(new Red());
        List<Color> items0 = x.getOneDimension();
        for( int i = 0; i < items0.size(); ++i )
            System.out.println( items0.get(i) );
        List<Data<Color>> items = y.getOneDimension();
        for( int i = 0; i < items.size(); ++i ) {
            System.out.println( items.get(i) );
        }
        long end = System.nanoTime();
        System.out.println( "ns: " + (end-begin));
        System.out.println( "ms: " + (end-begin)/1_000_000.0D);
        System.out.println( "s: " + (end-begin)/1_000_000_000.0D);

        System.out.println("===");

        begin = System.nanoTime();
        Swap.execute();
        end = System.nanoTime();
        System.out.println( "ns: " + (end-begin));
        System.out.println( "ms: " + (end-begin)/1_000_000.0D);
        System.out.println( "s: " + (end-begin)/1_000_000_000.0D);


    }
}
