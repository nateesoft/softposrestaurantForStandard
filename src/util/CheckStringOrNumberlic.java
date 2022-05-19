/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Dell-Softpos
 */
public class CheckStringOrNumberlic {

    public boolean CheckStringOrNumberlic(String text) {
        boolean numeric = true;
        try {
            Double.parseDouble(text);
        } catch (NumberFormatException e) {
            numeric = false;
        }

        return numeric;
    }
}
