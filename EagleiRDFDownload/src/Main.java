import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Main {
	static ArrayList<RDFdatabase> arrayDatabases;
	private static FileOutputStream file;
	
	public static void main(String[] args) throws IOException {
		
		arrayDatabases = new ArrayList<>();
		
		URL pennURL = new URL("https://eagle-i.itmat.upenn.edu/sparqler/sparql?institution=https%3A%2F%2Feagle-i.itmat.upenn.edu&format=application%2Fx-turtle&query=PREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E+PREFIX+%3A+%3Chttp%3A%2F%2Feagle-i.org%2Font%2Frepo%2F1.0%2F%3E+construct+%7B%3Fs+%3Fp+%3Fo+.+%3Fp+rdfs%3Alabel+%3Fp_label+.+%3Fo+rdfs%3Alabel+%3Fo_label%7D+where+%7Bgraph+%3ANG_Published%7B%3Fs+%3Fp+%3Fo%7D+.+optional%7B%3Fp+rdfs%3Alabel+%3Fp_label+%7D+.+optional%7B+%3Fo+rdfs%3Alabel+%3Fo_label%7D%7D+&run=Run&view=published-resources");
		URL harvardURL = new URL("https://harvard.eagle-i.net/sparqler/sparql?institution=https%3A%2F%2Fharvard.eagle-i.net&format=application%2Fx-turtle&query=PREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E+PREFIX+%3A+%3Chttp%3A%2F%2Feagle-i.org%2Font%2Frepo%2F1.0%2F%3E+construct+%7B%3Fs+%3Fp+%3Fo+.+%3Fp+rdfs%3Alabel+%3Fp_label+.+%3Fo+rdfs%3Alabel+%3Fo_label%7D+where+%7Bgraph+%3ANG_Published%7B%3Fs+%3Fp+%3Fo%7D+.+optional%7B%3Fp+rdfs%3Alabel+%3Fp_label+%7D+.+optional%7B+%3Fo+rdfs%3Alabel+%3Fo_label%7D%7D+&run=Run&view=published-resources");
		URL dartmouthURL = new URL("https://dartmouth.eagle-i.net/sparqler/sparql?institution=https%3A%2F%2Fdartmouth.eagle-i.net&format=application%2Fx-turtle&query=PREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E+PREFIX+%3A+%3Chttp%3A%2F%2Feagle-i.org%2Font%2Frepo%2F1.0%2F%3E+construct+%7B%3Fs+%3Fp+%3Fo+.+%3Fp+rdfs%3Alabel+%3Fp_label+.+%3Fo+rdfs%3Alabel+%3Fo_label%7D+where+%7Bgraph+%3ANG_Published%7B%3Fs+%3Fp+%3Fo%7D+.+optional%7B%3Fp+rdfs%3Alabel+%3Fp_label+%7D+.+optional%7B+%3Fo+rdfs%3Alabel+%3Fo_label%7D%7D+&run=Run&view=published-resources");
		URL howardURL = new URL("https://howard.eagle-i.net/sparqler/sparql?institution=https%3A%2F%2Fhoward.eagle-i.net&format=application%2Fx-turtle&query=PREFIX+rdfs%3A+%3Chttp%3A%2F%2Fwww.w3.org%2F2000%2F01%2Frdf-schema%23%3E+PREFIX+%3A+%3Chttp%3A%2F%2Feagle-i.org%2Font%2Frepo%2F1.0%2F%3E+construct+%7B%3Fs+%3Fp+%3Fo+.+%3Fp+rdfs%3Alabel+%3Fp_label+.+%3Fo+rdfs%3Alabel+%3Fo_label%7D+where+%7Bgraph+%3ANG_Published%7B%3Fs+%3Fp+%3Fo%7D+.+optional%7B%3Fp+rdfs%3Alabel+%3Fp_label+%7D+.+optional%7B+%3Fo+rdfs%3Alabel+%3Fo_label%7D%7D+&run=Run&view=published-resources");
		
		RDFdatabase penn = new RDFdatabase(pennURL, "penn");
		RDFdatabase harvard = new RDFdatabase(harvardURL, "harvard");
		RDFdatabase dartmouth = new RDFdatabase(dartmouthURL, "dartmouth");
		RDFdatabase howard = new RDFdatabase(howardURL, "howard");
		
		arrayDatabases.add(penn);
		arrayDatabases.add(harvard);
		arrayDatabases.add(dartmouth);
		arrayDatabases.add(howard);
		String date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		for (int i = 0; i< arrayDatabases.size(); i++){
			ReadableByteChannel rbc = Channels.newChannel(arrayDatabases.get(i).URL.openStream());
			file = new FileOutputStream("/Users/natanportilho/Documents/download/" + arrayDatabases.get(i).university.toUpperCase() + date + ".ttl");
			//file = new FileOutputStream(arrayDatabases.get(i).university.toUpperCase() + date + ".ttl");
			//("/path/to/file.txt");
			file.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			System.out.println(arrayDatabases.get(i).university.toUpperCase() + date + ".html" + " downloaded.");
		}		
	}
	

}


