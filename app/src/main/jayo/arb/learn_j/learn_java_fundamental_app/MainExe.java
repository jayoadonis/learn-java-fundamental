package jayo.arb.learn_j.learn_java_fundamental_app;

import jayo.arb.learn_j.learn_java_fundamental_lib.Blue;
import jayo.arb.learn_j.learn_java_fundamental_lib.Color;
import jayo.arb.learn_j.learn_java_fundamental_lib.Data;
import jayo.arb.learn_j.learn_java_fundamental_lib.Red;
import jayo.arb.learn_j.learn_java_fundamental_lib.array.DemoOneDimension;
import jayo.arb.learn_j.learn_java_fundamental_lib.array.OneDimension;
import jayo.arb.learn_j.learn_java_fundamental_lib.swap.Swap;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unchecked")
public class MainExe {

    public static void main(String[] args) throws InterruptedException {
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


        System.out.println("===");

        Color[] colors = new Color[]{
            new Red(), new Blue(), new Color()
        };

        for( Color color : colors )
            System.out.println(color);


        Random random = new Random();
        Data<Color>[] dataCs = new Data[random.nextInt(100_001-50_000)+50_000];
        System.out.println(dataCs.length);
        for( int i = 0; i < dataCs.length; i++ ) {
            dataCs[i] = new Data<>(
                    colors[random.nextInt(3)].clone(),
                    colors[random.nextInt(3)].clone()
            );
        }

        begin = System.nanoTime();
        final int SLEEP_DELTA_MS = 10;

        Thread d1Thread = new Thread(()->{
            Data<Color>[] d1 = new Data[dataCs.length/2];
            System.arraycopy(dataCs, 0, d1, 0, dataCs.length/2);
            OneDimension<Data<Color>> oneD1 = new DemoOneDimension<>(
                    d1
            );
//            System.out.println("<> " + oneD1.size());
//            for( Data<Color> dataC : oneD1.getOneDimension() )
//                System.out.println(dataC);
        });
        Thread d2Thread = new Thread(()->{
//            Data<Color>[] d2 = new Data[dataCs.length/2 + ((dataCs.length % 2 != 0 )? 1 : 0)];
//            System.arraycopy(dataCs, dataCs.length/2, d2, 0, dataCs.length/2 + ((dataCs.length % 2 != 0 )? 1 : 0));

            OneDimension<Data<Color>> oneD2 = new DemoOneDimension<>(
                    Arrays.copyOfRange( dataCs, dataCs.length/2, dataCs.length )
            );
//            try {
//                Thread.sleep( SLEEP_DELTA_MS );
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("<> " + oneD2.size());
//            for( Data<Color> dataC : oneD2.getOneDimension() )
//                System.out.println(dataC);
        });

        d1Thread.start();
        d2Thread.start();
        d1Thread.join();
        d2Thread.join();

        end = System.nanoTime();
        System.out.println("ns: " + ( (end-begin) ) ) ;
        System.out.println("ms: " + ( (end-begin)/1_000_000.0D  ) );
        System.out.println("s: " + ( (end-begin)/1_000_000_000.0D ) );



    }
}
