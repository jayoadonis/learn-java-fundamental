package jayo.arb.learn_j.learn_java_fundamental.lib.test;

import jayo.arb.learn_j.learn_java_fundamental_lib.Blue;
import jayo.arb.learn_j.learn_java_fundamental_lib.Color;
import jayo.arb.learn_j.learn_java_fundamental_lib.Data;
import jayo.arb.learn_j.learn_java_fundamental_lib.Red;
import jayo.arb.learn_j.learn_java_fundamental_lib.swap.Swap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSwap {

    @Test
    public void test000() {
        Color blueColor = new Blue();
        Color redColor = new Red();
        Data<Color> dataColor = new Data<>(
                blueColor, redColor
        );
        Assertions.assertEquals( blueColor, dataColor.x );
        Assertions.assertEquals( redColor, dataColor.y );
        Swap.swapWithTemp( dataColor );
        Assertions.assertEquals( redColor, dataColor.x );
        Assertions.assertEquals( blueColor, dataColor.y );

    }
}
