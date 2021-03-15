
package wikidata.viewcount;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;


public class ViewCounter {
    
    private final HashMap <String, Integer> _domainViews;
    
    private final HashMap <String, Integer> _tittleViews;
    
    public ViewCounter(){
        _domainViews = new HashMap<>();
        _tittleViews = new HashMap<>();
    } 
    
    public HashMap <String, Integer> getDomainViews(){
        return _domainViews;
    }
    
    public HashMap <String, Integer> getTittleViews(){
        return _tittleViews;
    }
    
    
    public void countViews(BufferedReader reader) throws IOException{
        
        String line;
                
        while((line = reader.readLine()) != null){
            try{
                DumpData data = new DumpData(line);
                
                if(data.getDomain().equals("none")) continue;
                
                //Domain + language
                
                String langDomain = data.getLanguage() + "-" + data.getDomain();
                
                if(_domainViews.containsKey(langDomain)){
                    
                    int viewCount = _domainViews.get(langDomain);
                    
                    viewCount += data.getViewCount();
                    
                    _domainViews.put(langDomain, viewCount);
                    
                }
                else{
                    int viewCount = data.getViewCount();
                    _domainViews.put(langDomain, viewCount);
                }
                
                
                //Title
                
                String title = data.getPageTitle();
                
                if(_tittleViews.containsKey(title)){
                    int viewCount = _tittleViews.get(title);
                    
                    viewCount += data.getViewCount();
                    
                    _tittleViews.put(title, viewCount);
                }
                else{
                    int viewCount = data.getViewCount();
                    _tittleViews.put(title, viewCount);
                }
                
            }
            catch(Exception ex){
                continue;
            }
        }
        
    }
    
    
}
