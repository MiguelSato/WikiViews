
package datainput;

import java.io.BufferedReader;
import java.io.IOException;

public interface IDataInput {
    
    public BufferedReader getInputStream(String file) throws IOException;
    
    
}
