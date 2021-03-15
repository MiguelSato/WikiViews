
package wikidata.viewcount;

public class DumpData {
    
    private String _language;
    
    private String _domain;
    
    private String _pageTitle;
    
    private Integer _viewCount;
    
    private Integer _responseSize;
    
    //dataline format: "aa.b Main_Page 1 0"
    public DumpData(String dataLine) throws Exception{
        
        String [] data = dataLine.split(" ");
        
        if(data.length != 4) throw new Exception("Missing data");
        
        //domain and language
        
        String str =  data[0];
        
        String [] langDomain = str.split("\\.");
        
        _language = langDomain[0];
        
        if(langDomain.length != 2) _domain = "";
        else _domain = langDomain[1];
        
        _domain = domainName(_domain);
        
        //
        
        _pageTitle = data[1];
        
        _viewCount = Integer.parseInt(data[2]);
        
        _responseSize = Integer.parseInt(data[3]);
        
    }
    
    public String getLanguage(){
        return _language;
    }
        
    public String getDomain(){
        return _domain;
    }
    
    public String getPageTitle(){
        return _pageTitle;
    }
    
    public int getViewCount(){
        return _viewCount;
    }
    
    public int getResponseSize(){
        return _responseSize;
    }
    
    //
    private String domainName(String code){
        
        switch(code){
            case "" -> {
                return "wikipedia";
            }
            case "b" -> {
                return "wikibooks";
            }
            case "d" -> {
                return "wiktionary";
            }
            case "f" -> {
                return "foundationwiki"; 
            }
            case "m" -> {
                return "wikimedia"; 
            }
            case "n" -> {
                return "wikinews"; 
            }
            case "q" -> {
                return "wikiquote"; 
            }
            case "s" -> {
                return "wikisource"; 
            }
            case "v" -> {
                return "wikiversity"; 
            }
            case "voy" -> {
                return "wikivoyage"; 
            }
            case "w" -> {
                return "mediawiki"; 
            }
            case "wd" -> {
                return "wikidat"; 
            }
            default -> {
                return "none";
            }
        }
        
    }
    
    public static void main(String [] args){
        try{
            DumpData dump = new DumpData("aa Main_Page 12 0");
            
            System.out.println(dump._language);
            
            System.out.println(dump._domain);
            
            System.out.println(dump._pageTitle);
            
            System.out.println(dump._viewCount);
            
            System.out.println(dump._responseSize);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
    }
    
}
