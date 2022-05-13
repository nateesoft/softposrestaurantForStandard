package util;

import java.text.DecimalFormat;

/**
 *
 * @author nathee
 */
public class NumberUtil {

    public static String showNumber(double number) {
        DecimalFormat dec = new DecimalFormat("#,###");
        return dec.format((int) number);
    }

}
