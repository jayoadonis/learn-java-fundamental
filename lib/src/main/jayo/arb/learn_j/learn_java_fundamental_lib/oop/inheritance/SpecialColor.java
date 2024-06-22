package jayo.arb.learn_j.learn_java_fundamental_lib.oop.inheritance;

import jayo.arb.learn_j.learn_java_fundamental_lib.Color;

public class SpecialColor extends Color {
    public SpecialColor( float alpha ) {
        super( 0.5f, 0.5f, 0.8f, alpha );
    }
    public Color getColor() {
        return super.clone();
    }
}
