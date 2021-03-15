
package wikidata;

import wikidata.viewcount.ViewCounter;
import datainput.GZIPInput;
import datainput.IDataInput;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reporter {
    
    //CONSTANTS
    private static final int NUM_HOURS = 5; 
    
    private static final String DATA_LOCATION = "https://dumps.wikimedia.org/other/pageviews/";
    
    //
    
    IDataInput _dataInput;
    
    List<HashMap> _hourDomainViews;
    List<HashMap> _hourTittleViews;
    
    public Reporter(){
        _dataInput = new GZIPInput();
        
        _hourDomainViews = new ArrayList<>();
        _hourTittleViews = new ArrayList<>();
        
    }
    
    public void report(){
        
        List<String> files = getFilesUrl(NUM_HOURS);
        
        System.out.println("Processing files: ");
        //For each hour
        for(String file : files){
            
            System.out.println(file);
            
            try{
                BufferedReader reader = _dataInput.getInputStream(file);
                
                ViewCounter viewCounter = new ViewCounter();
                viewCounter.countViews(reader);
                
                _hourDomainViews.add(viewCounter.getDomainViews());
                _hourTittleViews.add(viewCounter.getTittleViews());
                
            }
            catch(IOException ex){
                
                HashMap emptyMap = new HashMap();
                _hourDomainViews.add(emptyMap);
                _hourTittleViews.add(emptyMap);
                
            }
        }
        
        System.out.println();
        
        printMaxDomain();
        
        printMaxTittle();
        
    }
    
    private List<String> getFilesUrl(int fileNumber){
        
        List<String> list = new ArrayList<>();
        
        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC);
        
        for(int i = 0; i < fileNumber; i ++){
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
            
            String year = formatter.format(localDateTime);
            
            formatter = DateTimeFormatter.ofPattern("MM");
            String month = formatter.format(localDateTime);
            
            formatter = DateTimeFormatter.ofPattern("dd");
            String day = formatter.format(localDateTime);
            
            formatter = DateTimeFormatter.ofPattern("HH");
            String hour = formatter.format(localDateTime);
            
            String url = year + "/" + year + "-" + month + "/pageviews-" + year + month + day + "-" + hour + "0000.gz"; 
            
            url = DATA_LOCATION + url;
            
            list.add(url);
            
            localDateTime = localDateTime.minusHours(1);
        }
        
        return list;
    }
    
    private void printMaxDomain(){
        
        System.out.printf("%-20s %-20s %-20s %s\n", "Period", "Language", "Domain", "ViewCount");
        
        System.out.println("");
        
        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH a");
        
        for(HashMap<String, Integer> domainViews: _hourDomainViews){
            
            if(domainViews.isEmpty()){
                String hour = formatter.format(localDateTime);
                System.out.printf("%-20s %-20s %-20s %d\n", hour, "no data", "no data", 0);
                localDateTime = localDateTime.minusHours(1);
                continue;
            }
            
            int maxView = 0;
            String maxDomain = "";
            
            for(Map.Entry<String, Integer> entry : domainViews.entrySet()){
                
                int views = entry.getValue();
                
                if(views > maxView){
                    maxView = views;
                    
                    maxDomain = entry.getKey();
                }
                
            }
            
            String hour = formatter.format(localDateTime);
            
            String [] lanDomain = maxDomain.split("-");
            
            System.out.printf("%-20s %-20s %-20s %d\n", hour, lanDomain[0], lanDomain[1], maxView);
            
            localDateTime = localDateTime.minusHours(1);
            
        }
        
        System.out.println("");
        
    }
    
    private void printMaxTittle(){
        
        System.out.printf("%-20s %-20s %s\n", "Period", "Page", "ViewCount");
        
        System.out.println("");
        
        LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH a");
        
        
        for(HashMap<String, Integer> tittleViews: _hourTittleViews){
            
            if(tittleViews.isEmpty()){
                String hour = formatter.format(localDateTime);
                System.out.printf("%-20s %-20s %d\n", hour, "no data", 0);
                localDateTime = localDateTime.minusHours(1);
                continue;
            }
            
            int maxView = 0;
            String maxTittle = "";
            
            for(Map.Entry<String, Integer> entry : tittleViews.entrySet()){
                
                int views = entry.getValue();
                
                if(views > maxView){
                    maxView = views;
                    
                    maxTittle = entry.getKey();
                }
                
            }
            
            String hour = formatter.format(localDateTime);
            
            System.out.printf("%-20s %-20s %d\n", hour, maxTittle, maxView);
            
            localDateTime = localDateTime.minusHours(1);
            
        }
        
        System.out.println("");
    }
    
}
