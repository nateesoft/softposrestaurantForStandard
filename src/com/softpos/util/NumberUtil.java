package com.softpos.util;

import java.text.DecimalFormat;

public class NumberUtil {

    static DecimalFormat dec1 = new DecimalFormat("#,##0.00");
    static DecimalFormat dec3 = new DecimalFormat("#,##0.000");
    static DecimalFormat dec4 = new DecimalFormat("#,##0.0000");
    static DecimalFormat dec5 = new DecimalFormat("#,##0.00000");

    public static String showDouble2(float obj) {
        return dec1.format(obj);
    }

    public static String showDouble2(double obj) {
        return dec1.format(obj);
    }

    public static String showDouble2(String str) {
        try {
            Double d = Double.parseDouble(str);
            return dec1.format(d);
        } catch (NumberFormatException e) {
            return "0.00";
        }
    }

    public static String showDouble3(float obj) {
        return dec3.format(obj);
    }

    public static String showDouble3(double obj) {
        return dec3.format(obj);
    }

    public static String showDouble3(String str) {
        try {
            Double d = Double.parseDouble(str);
            return dec3.format(d);
        } catch (Exception e) {
            return "0.000";
        }
    }

    public static String showDouble4(float obj) {
        return dec4.format(obj);
    }

    public static String showDouble4(double obj) {
        return dec4.format(obj);
    }

    public static String showDouble4(String str) {
        try {
            Double d = Double.parseDouble(str);
            return dec4.format(d);
        } catch (Exception e) {
            return "0.00";
        }
    }

    public static String showDouble5(float obj) {
        return dec5.format(obj);
    }

    public static String showDouble5(double obj) {
        return dec5.format(obj);
    }

    public static String showDouble5(String str) {
        try {
            Double d = Double.parseDouble(str);
            return dec5.format(d);
        } catch (Exception e) {
            return "0.00";
        }
    }
    
    public static String showNumber(double number) {
        DecimalFormat dec = new DecimalFormat("#,###");
        return dec.format((int) number);
    }

    public static String format(double data) {
        DecimalFormat df1 = new DecimalFormat("#,##0.00");

        return df1.format(data);
    }

    public static int TO_INT(double data) {
        return (int) data;
    }

    public static double UP_DOWN_25(double d) {
        DecimalFormat fm = new DecimalFormat("#.00");
        String money = "" + fm.format(d);
        if (money.contains(".")) {
            String m = money.replace('.', ',');
            int multi = 1;
            if (m.contains("-")) {
                multi = -1;
            }
            String[] data = m.split(",");
            if (data.length > 1) {
                if (data[1].length() > 2) {
                    data[1] = data[1].substring(0, 2);
                }
                double num1 = Double.parseDouble(data[1]);
                if (num1 < 10) {
                    num1 *= 10;
                }
                double num2 = Double.parseDouble(data[0]);
                if (num1 > 100) {
                    num1 = num1 / 10;
                }
                if (num1 >= 88 && num1 < 100) {
                    return num2 + 1.00 * multi;
                } else if (num1 >= 63 && num1 <= 87) {
                    return num2 + 0.75 * multi;
                } else if (num1 >= 38 && num1 <= 62) {
                    return num2 + 0.50 * multi;
                } else if (num1 >= 13 && num1 <= 37) {
                    return num2 + 0.25 * multi;
                } else if (num1 >= 0 && num1 <= 12) {
                    return num2;
                } else {
                    return num2;
                }

            } else {
                return 0;
            }
        } else {
            double total = 0.00;
            try {
                total = Double.parseDouble(money);
            } catch (NumberFormatException e) {

            }

            return total;
        }
    }

    public static double UP_DOWN_25NewTotal(double d) {
        String money = "" + d;
        if (money.contains(".")) {
            String m = money.replace('.', ',');
            int multi = 1;
            if (m.contains("-")) {
                multi = -1;
            }
            String[] data = m.split(",");
            if (data.length > 1) {
                if (data[1].length() > 2) {
                    data[1] = data[1].substring(0, 2);
                }
                double num1 = Double.parseDouble(data[1]);
                if (num1 < 10) {
                    num1 *= 10;
                }
                double num2 = Double.parseDouble(data[0]);
                if (num1 > 100) {
                    num1 = num1 / 10;
                }
                if (num1 >= 88 && num1 < 100) {
                    return num2 + 1.00 * multi;
                } else if (num1 >= 63 && num1 <= 87) {
                    return num2 + 0.75 * multi;
                } else if (num1 >= 38 && num1 <= 62) {
                    return num2 + 0.50 * multi;
                } else if (num1 >= 13 && num1 <= 37) {
                    return num2 + 0.25 * multi;
                } else if (num1 >= 0 && num1 <= 12) {
                    return num2;
                } else if (num1 >= 75) {
                    return num1 = 0;
                } else if (num1 >= 50) {
                    return num1 = 75;
                } else if (num1 >= 25) {
                    return num1 = 50;
                } else if (num1 >= 0 && num1 < 25) {
                    return num1 = 25;
                } else {
                    return num2;
                }
            } else {
                return 0;
            }
        } else {
            double total = 0.00;
            try {
                total = Double.parseDouble(money);
            } catch (NumberFormatException e) {

            }

            return total;
        }
    }

    public static int UP_DOWN_NATURAL_BAHT(double d) {
        return (int) Math.round(d);
    }

    public static double DOWN_BAHT(double d) {
        return Math.floor(d);
    }

    public static double UP_BAHT(double d) {
        return Math.ceil(d);
    }

    public String number(double num) {
        DecimalFormat df = new DecimalFormat("#,###,###,###.00");
        String textReturn;
        String numText = df.format(num);
        String numTextDecimal = df.format(num);
        numText = numText.replace(",", "");
        numText = numText.substring(0, numText.lastIndexOf("."));
        char[] result = numText.replaceAll("\\W", numText).toCharArray();
        String a = "";
        String b = "";

        String aa;
        String bb = "";
        String decimalText = "";
        int round = result.length;

        numTextDecimal = numTextDecimal.replace(",", "");
        try {
            decimalText = numTextDecimal.substring(numTextDecimal.lastIndexOf(".") + 1);
        } catch (Exception e) {

        }
        if (!decimalText.equals("00")) {
            int roundDecimal = 3;
            char[] resultDecimal = decimalText.toCharArray();
            //หาค่าจากทศนิยม

            for (char c : resultDecimal) {
                roundDecimal--;
                aa = changeNumToText("" + c, roundDecimal + "");
                if (aa.equals("หนึ่งสิบ")) {
                    aa = "สิบ";
                }
                bb += aa;
            }
        } else {
            bb = "ศูนย์";
        }

        //หาค่าจากจำนวนเต็ม
        for (char c : result) {
            if (round > 0) {
                System.out.println("ตำแหน่ง " + round + " / " + a);
                a = changeNumToText("" + c, round + "");
                if (round != 0) {
                    if (round == 2 && a.equals("หนึ่งสิบ")) {
                        a = "สิบ";
                    }
                    b += a;
                }
                round--;
            }
        }
        if (decimalText.equals("00")) {
            textReturn = b + "บาทถ้วน";
        } else {
            textReturn = b + "บาท" + bb + "สตางค์";
        }

        return textReturn;
    }

    private String changeNumToText(String numberText, String digit) {

        switch (digit) {
            case "1":
                digit = "";
                break;
            case "2":
                digit = "สิบ";
                break;
            case "3":
                digit = "ร้อย";
                break;
            case "4":
                digit = "พัน";
                break;
            case "5":
                digit = "หมื่น";
                break;
            case "6":
                digit = "แสน";
                break;
            case "7":
                digit = "ล้าน";
                break;
            case "8":
                digit = "สิบ";
                break;
            case "9":
                digit = "ร้อย";
                break;
            case "10":
                digit = "พัน";
                break;
        }
        switch (numberText) {
            case "0":
                numberText = "";
                break;
            case "1":
                if (digit.equals("")) {
                    numberText = "เอ็ด";
                } else {
                    numberText = "หนึ่ง";
                }

                break;
            case "2":
                if (digit.equals("สิบ")) {
                    numberText = "ยี่";
                } else {
                    numberText = "สอง";
                }

                break;
            case "3":
                numberText = "สาม";
                break;
            case "4":
                numberText = "สี่";
                break;
            case "5":
                numberText = "ห้า";
                break;
            case "6":
                numberText = "หก";
                break;
            case "7":
                numberText = "เจ็ด";
                break;
            case "8":
                numberText = "แปด";
                break;
            case "9":
                numberText = "เก้า";
                break;
            case ".":
                numberText = "จุด";
                break;
        }
        if (numberText.equals("จุด")) {
            digit = "";
        }
        return numberText + digit;
    }


}
