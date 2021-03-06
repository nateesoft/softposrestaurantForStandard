package util;

public class Option {    
    
    public static String[] splitPrice(String PLU){
        String PLUCode = PLU;
        String QTY = "1.00";
        
        if(PLU.contains("*")){
            int indexStar = PLU.indexOf("*");
            if(indexStar>0){
                PLUCode = PLU.substring(indexStar+1, PLUCode.length());
                QTY = PLU.substring(0, PLU.indexOf("*"));
            }
            if(indexStar==0){
                PLUCode = PLU.substring(indexStar+1, PLUCode.length());
                QTY = "1";
            }
        }
        
        return (QTY+","+PLUCode).split(",");
    }
}
