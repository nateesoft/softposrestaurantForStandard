package directory_utility;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *
 * @author root
 */
public class DirectoryUtility {
    
    public boolean createDir(File file) throws IOException{
        boolean success = file.mkdir();
        //System.out.println(file.getCanonicalPath() + " Create. ==> " + success);
        return success;
    }
    
    public boolean deleteDir(File file) throws IOException{
        boolean success = file.delete();
        return success;
    }
    
     public File getFileAndCreateDir(String pathFile) throws Exception {
        StringTokenizer st = new StringTokenizer(pathFile.trim(),"/");
        String path = "";
        int countToken = st.countTokens();        
        for(int i=0; st.hasMoreTokens(); i++){            
            if (i < countToken-1) {
                path += "/" + st.nextToken();
                File f = new File(path);
                if (!f.exists()) 
                    createDir(f);                
            }else{
                st.nextToken();
            }
        }               
        File file = new File(pathFile);        
        return file;
    }
}
