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

    public static void main(String[] args) {
        System.out.println(NumberUtil.showNumber(2000.50));
        System.out.println((int) 2000.50);
    }
}
