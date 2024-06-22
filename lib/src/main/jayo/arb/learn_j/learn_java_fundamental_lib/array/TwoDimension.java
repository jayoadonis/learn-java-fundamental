package jayo.arb.learn_j.learn_java_fundamental_lib.array;

import java.util.List;

public abstract class TwoDimension<
        T extends Comparable<? super T>
        >
        implements IDimension
{
    public abstract List<T> getTwoDimension();

    @Override
    public abstract int size();
}
