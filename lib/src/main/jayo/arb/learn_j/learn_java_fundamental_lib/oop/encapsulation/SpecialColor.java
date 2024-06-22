package jayo.arb.learn_j.learn_java_fundamental_lib.oop.encapsulation;

import java.util.Comparator;

public class SpecialColor {
    public SpecialColor(
            float red, float green, float blue, float alpha
    ) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    public void setRed( float red ) {
        if( !isValidFloat( red, 0.0f, Float::compare) )
            return;
        this.red = red;
    }
    public void setGreen( float green ) {
        if( !isValidFloat( green, 0.0f, Float::compare) )
            return;
        this.green = green;
    }
    public void setBlue( float blue ) {
        if( !isValidFloat( blue, 0.0f, Float::compare) )
            return;
        this.blue = blue;
    }
    public void setAlpha( float alpha ) {
        if( !isValidFloat( alpha, 0.0f, Float::compare) )
            return;
        this.alpha = alpha;
    }

    /**
     *
     * @param target t >= baseline >= incremental infinity
     * @param baseLine the minimum offset ( baseLine >= incremental infinity )
     * @param cmp func(Float, Float)I such as {@code Float.compare(float x, float y)I}
     * @return boolean TRUE if valid float (target >= baseLine)
     * @param <T> T extends Number
     */
    public <T extends Number > boolean isValidFloat(
            T target, T baseLine,
            Comparator<Float> cmp
    ) {
        if( cmp.compare(target.floatValue(), baseLine.floatValue()) < 0 ) {
            assert( false ) : "Invalid negative float value";
            return false;
        }
        return true;
    }
    private float red;
    private float green;
    private float blue;
    private float alpha;
}
