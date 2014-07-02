package game.util;

/**
 * Created by semyon on 02.07.14.
 */
public class Utils {

    public static int floorDiv(final int x, final int y) {
        int r = x / y;
        if ((x ^ y) < 0 && (r * y != x)) {
            r--;
        }
        return r;
    }

    public static long floorDiv(final long x, final long y) {
        long r = x / y;
        if ((x ^ y) < 0 && (r * y != x)) {
            r--;
        }
        return r;
    }

}
