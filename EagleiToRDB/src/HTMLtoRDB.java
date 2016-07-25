
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLtoRDB {

	public HTMLtoRDB(){
		
	}
	
	public void convertHTMLtoRDB(DatabaseConnection connection, String tableName, String url){

       Document doc;
        try {
            doc = Jsoup.connect(url).get();
            Element table = doc.select("table").get(0); // gets the first table from the HTML
            Elements rows = table.select("tr");
            Elements ths = rows.select("th");

            // i is just a counter to keep track of the number of rows, just to compare with the MySQL database too see if it matches
        	int i = 0;
            for (Element row : rows) {
            	
                // columnCounter is a counter to keep track of the number of columns in each row, we need ALWAYS to have 3 columns (subject, Predicate, Object)
                // but sometimes there is nothing in the Predicate or Object (although it's really rare)
                // so we will have just two columns. If this happens, we need to make sure we add "" into the table to create a blank
                // statement instead of having a nullPointerException
            	int columnCounter = 0;
            	//
            
            	ArrayList<String> array = new ArrayList<>();
            	//
            	
                Elements tds = row.select("td");
                for (Element td : tds) {
                	columnCounter++;
                	// adds each column to array
                		array.add(td.text());
                }
                
                // To prevent nullPointerException
                if (columnCounter == 1){
                	array.add("");
                	array.add("");
                }
                
                if (columnCounter == 2){
                	array.add("");
                }
               
                i++;
                // for test purposes
                //System.out.println(array.size());
                
                //insert into table
              connection.insert(tableName, array);

            }
            // For test purposes, print the total number of lines
            // It's always going to print the number of lines inserted + 1 (because the first line is the column names)
            System.out.println(i);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

	}
}
