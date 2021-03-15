
package datainput;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.zip.GZIPInputStream;


public class GZIPInput implements IDataInput{
    
    private String _encoding = "UTF-8";
    
    private String _inputType = "url"; // Could be "url" or "localfile" 
    
    public void setEncoding(String encoding){
        _encoding = encoding;
    }
    
    public void setInputType(String inputType){
        
        if(inputType.equals("url") || inputType.equals("localfile")) _inputType = inputType;
        
    }
    
    @Override
    public BufferedReader getInputStream(String fileurl) throws IOException{
        
            InputStream fileStream;
            
            if(_inputType.equals("url")){
                fileStream = new URL(fileurl).openStream();
            }
            else { //localfile
                fileStream = new FileInputStream(fileurl);
            }
            
            InputStream gzipStream = new GZIPInputStream(fileStream);
            
            Reader decoder = new InputStreamReader(gzipStream, _encoding);
            BufferedReader buffered = new BufferedReader(decoder);
            
            return buffered;
        
    }
    
    public static void main(String [] args){
        try {
            
            GZIPInput gzipInput = new GZIPInput();
            
            //gzipInput.setInputType("localfile");
            //BufferedReader data = gzipInput.getInputStream("C:/Users/MSI/Downloads/pageviews-20150501-010000.gz");
            
            BufferedReader data = gzipInput.getInputStream("https://dumps.wikimedia.org/other/pageviews/2015/2015-05/pageviews-20150501-010000.gz");
        
            String line = data.readLine();
            
            System.out.println(line);
        
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
