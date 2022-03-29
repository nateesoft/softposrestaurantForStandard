package util;

/**
 *
 * @author nathee
 */
public class ValidateValue {

    public static boolean isNotEmpty(String data) {
        if (data == null) {
            return false;
        }
        if (data.trim().length() == 0 || data.trim().equals("")) {
            return false;
        }
        return !data.trim().equals("null");
    }
    
    public static boolean isEmpty(String data) {
        return !isNotEmpty(data);
    }
}
