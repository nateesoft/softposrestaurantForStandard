package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author nathee
 */
public class CheckApplication {

    private static final String APP_NAME = "SoftPOSRestaurantForStandart.jar";

    public static void main(String[] args) {
        CheckApplication.isRunning();
    }

    public static void terminateApps() {
        try {
            String command = "cmd /c C:\\Windows\\System32\\wbem\\WMIC.exe process where \"Caption like '%javaw.exe%'\"";
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains(APP_NAME)) {
                    Runtime.getRuntime().exec(command + " Call Terminate");
                }
            }
        } catch (IOException ex) {
        }
    }

    public static boolean isRunning() {
        try {
            String command = "cmd /c C:\\Windows\\System32\\wbem\\WMIC.exe process where \"Caption like '%javaw.exe%'\"";
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            int count = 0;
            while ((line = in.readLine()) != null) {
                if (line.contains(APP_NAME)) {
                    count++;
                }
            }
            if (count > 1) {
                return true;
            }
        } catch (IOException ex) {
        }

        return false;
    }
}
