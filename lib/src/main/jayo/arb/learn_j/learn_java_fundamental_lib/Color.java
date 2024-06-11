package jayo.arb.learn_j.learn_java_fundamental_lib;

public class Color implements Comparable<Color>, Cloneable{
    public Color() {
        this(0.0f, 0.0f, 0.0f);
    }
    public Color(float red, float green, float blue) {
        this(red, green, blue, 1.0f);
    }
    public Color(float red, float green, float blue, float alpha ) {
        if(
                Float.compare( red, 0.0f) < 0
                || Float.compare( green, 0.0f) < 0
                || Float.compare( blue, 0.0f) < 0
                || Float.compare( alpha, 0.0f) < 0
        ) {
            throw new IllegalArgumentException(
                    String.format(
                            "%s, red=%f, green=%f, blue=%f, alpha=%f",
                            "Some of the value found negative: ",
                            red, green, blue, alpha
                    )
            );
        }
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    @Override
    public int compareTo( Color color ) {
        return Float.compare(
                this.red + this.green + this.blue + this.alpha,
                color.red + color.green + color.blue + color.alpha
        );
    }
    @Override
    public Color clone() /*throws CloneNotSupportedException*/ {
        try {
            Color clone = (Color) super.clone();
            //REM: TODO-HERE; copy mutable state here, so the clone can't change the internals of the original
//            clone.red = this.red;
//            clone.green = this.green;
//            clone.blue = this.blue;
//            clone.alpha = this.alpha;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public String toString() {
        return String.format(
                "%s[r=%.2f, g=%.2f, b=%.2f, a=%.2f]",
                super.toString(),
                this.red, this.green, this.blue, this.alpha
        );
    }
    //REM: TODO-HERE: the guard have been bypass
    protected float red;
    protected float green;
    protected float blue;
    protected float alpha;
}
