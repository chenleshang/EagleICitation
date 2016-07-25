import java.util.ArrayList;

public class Main {
		
	public static void main(String[] args) {
		// Databses names in MySQL for each college
		String penn_eagleirdb = "penn_eagleirdb";
		String harvard_eagleirdb = "harvard_eagleirdb";
		String howard_eagleirdb = "howard_eagleirdb";
		String dartmouth_eagleirdb = "dartmouth_eagleirdb";
		
		String urlPENN = "https://eagle-i.itmat.upenn.edu/sparqler/sparql?institution=https%3A%2F%2Feagle-i.itmat.upenn.edu&format=text%2Fhtml&query=PREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E+PREFIX+%3A+%3Chttp%3A%2F%2Feagle-i.org%2Font%2Frepo%2F1.0%2F%3E+construct+%7B%3Fs+%3Fp+%3Fo+.+%3Fp+rdfs%3Alabel+%3Fp_label+.+%3Fo+rdfs%3Alabel+%3Fo_label%7D+where+%7Bgraph+%3ANG_Published%7B%3Fs+%3Fp+%3Fo%7D+.+optional%7B%3Fp+rdfs%3Alabel+%3Fp_label+%7D+.+optional%7B+%3Fo+rdfs%3Alabel+%3Fo_label%7D%7D+&run=Run&view=published-resources";
		String urlHARVARD = "https://harvard.eagle-i.net/sparqler/sparql?institution=https%3A%2F%2Fharvard.eagle-i.net&format=text%2Fhtml&query=PREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E+PREFIX+%3A+%3Chttp%3A%2F%2Feagle-i.org%2Font%2Frepo%2F1.0%2F%3E+construct+%7B%3Fs+%3Fp+%3Fo+.+%3Fp+rdfs%3Alabel+%3Fp_label+.+%3Fo+rdfs%3Alabel+%3Fo_label%7D+where+%7Bgraph+%3ANG_Published%7B%3Fs+%3Fp+%3Fo%7D+.+optional%7B%3Fp+rdfs%3Alabel+%3Fp_label+%7D+.+optional%7B+%3Fo+rdfs%3Alabel+%3Fo_label%7D%7D+&run=Run&view=published-resources";
		String urlHOWARD = "https://howard.eagle-i.net/sparqler/sparql?institution=https%3A%2F%2Fhoward.eagle-i.net&format=text%2Fhtml&query=PREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E+PREFIX+%3A+%3Chttp%3A%2F%2Feagle-i.org%2Font%2Frepo%2F1.0%2F%3E+construct+%7B%3Fs+%3Fp+%3Fo+.+%3Fp+rdfs%3Alabel+%3Fp_label+.+%3Fo+rdfs%3Alabel+%3Fo_label%7D+where+%7Bgraph+%3ANG_Published%7B%3Fs+%3Fp+%3Fo%7D+.+optional%7B%3Fp+rdfs%3Alabel+%3Fp_label+%7D+.+optional%7B+%3Fo+rdfs%3Alabel+%3Fo_label%7D%7D+&run=Run&view=published-resources";
		String urlDARTMOUTH = "";
		
		// Table to insert data
		String tableName = "eaglei_resources";
		
		// Uses the class DatabaseConnection to connect to MySQL
		DatabaseConnection connection = new DatabaseConnection();
		
		// Connect to database using database name
		connection.connect(howard_eagleirdb);
		
		// Uses the class HTMLtoRDB to read html and convert to CSV (comma-separated values) and then insert it into MySQL
		HTMLtoRDB htmLtoRDB = new HTMLtoRDB();
		htmLtoRDB.convertHTMLtoRDB(connection, tableName, urlHOWARD);

	}

}