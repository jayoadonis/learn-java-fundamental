package jayo.arb.learn_j.learn_java_fundamental_lib.array;

import java.util.List;

public abstract class OneDimension<T extends Comparable<? super T>> {

    public abstract List<T> getOneDimension();
    public abstract int size();
}
