package jayo.arb.learn_j.learn_java_fundamental_lib;

public class Red extends Color {
    public Red() {
        this( 1.0f );
    }
    public Red( float value ) {
        super( value, 0.0f, 0.0f );

        //REM: TODO-HERE: bind it with the gradle build tool such as the 'gradle.properties'.
        //REM: TODO-HERE: ~ this is a sample, it can be remove at any time.
        assert( false ) : "User Error.....";
    }
}
