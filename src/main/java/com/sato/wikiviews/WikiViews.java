
package com.sato.wikiviews;

import wikidata.Reporter;

public class WikiViews {
    
    public static void main(String [] args){
        try{
            Reporter reporter = new Reporter();
            reporter.report();
            
            System.out.println("Press enter to continue...");
            System.in.read();
            
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
    }
    
}
