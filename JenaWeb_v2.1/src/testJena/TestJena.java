package testJena;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.resultset.ResultsFormat;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.util.FileManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;


public class TestJena 
{
	/**
	 * TestJena 2.1 0524
	 * Changed XML display. 
	 * TestJena 2.0 0515
	 * Full Version of Data Citation. 
	 * Removed Normal Citation Form.
	 * Only output JSON Format. 
	 * This part contains the path of compact RDF data file
	 * and a certain path for Jena to populate the file into 
	 * a real database directory. 
	 * We must set proper path in Windows/Linux/OSX as they 
	 * have different file system. 
	 */
	static String rdfPath = "/Users/natanportilho/Documents/PROJECT_CITATION/DATA_1.0_0416/Output";
	static String upennData = "/Users/natanportilho/Documents/PROJECT_CITATION/DATA_1.0_0416/upennDB.ttl";
	static String globalData = "/Users/natanportilho/Documents/PROJECT_CITATION/DATA_1.0_0416/globalDB.ttl";
	static String howUData = "/Users/natanportilho/Documents/PROJECT_CITATION/DATA_1.0_0416/howUDB.ttl";
	static String dartmouthData = "/Users/natanportilho/Documents/PROJECT_CITATION/DATA_1.0_0416/dartmouthDB.ttl";
	static String harvardData = "/Users/natanportilho/Documents/PROJECT_CITATION/DATA_1.0_0416/harvardDB.ttl";
	static String eroData = "/Users/natanportilho/Documents/PROJECT_CITATION/DATA_1.0_0416/ero.owl";
	static String upennName = "upennDB";
	static String globalName = "globalDB";
	static String howUName = "howUDB";
	static String eroName = "eroOntology";
	static String dartmouthName = "dartmouthDB";
	static String harvardName = "harvardDB";
	static String eroTestPath = "/Users/natanportilho/Documents/PROJECT_CITATION/DATA_1.0_0416/EROTEST";
	public enum outFormat{JSONFORMAT, STRINGFORMAT};
	
 //	private static String baseURI = "http://eagle-i.itmat.upenn.edu/i/";
//	private static String uri = "00000138-900e-3eb8-9cd7-d7e280000000";
//	private static String hasManufacturer = "<http://purl.obolibrary.org/obo/ERO_0000034>";
	// To keep track of the type of resource (to be used in biblatex method)
	static String type;
	
	/**
	 * When running for the first time, get rid of the comment of line: 
	 * Dataset dataset and the populateTDB to populate the RDF file. 
	 * When running for the second time, you have the choice of comment 
	 * out these lines. You also have the option of not comment on them. 
	 * @param args
	 */
	public static void main(String [] args)
	{

		
		Dataset dataset = TDBFactory.createDataset(rdfPath);
//		
//		populateTDB((Dataset)dataset, eroName, eroData);
//		populateTDB((Dataset)dataset, upennName, upennData);
//		populateTDB((Dataset)dataset, globalName, globalData);
//		populateTDB((Dataset)dataset, howUName, howUData);
//		populateTDB((Dataset)dataset, dartmouthName, dartmouthData);
//		populateTDB((Dataset)dataset, harvardName, harvardData);
		
		
	//The following lines are the test of judging the type of different reagents. 
		
		//dataset.begin(ReadWrite.READ);
		Model model = initializeModel(dataset);
//
//		
//		String pstm = "SELECT * \n"
//				+ "WHERE { ?node rdfs:label  \"reagent library\"	."
//				+ "?node rdfs:subClassOf+ ?parentNodes .\n"
//				+ "OPTIONAL{ ?parentNodes owl:someValuesFrom ?property } } ";
//			
//		ParameterizedSparqlString pss = testQuery(pstm);
//		Query query = QueryFactory.create(pss.toString());
//		QueryExecution qexec = QueryExecutionFactory.create(query, model);
//		ResultSet results = qexec.execSelect();
//
//		String outStr = ResultSetFormatter.asText(results);
//		System.out.println(outStr);
//		if(outStr.indexOf("http://purl.obolibrary.org/obo/OBI_0100026") != -1)
//			System.out.println("Is Organism!");
//		else if(outStr.indexOf("http://purl.obolibrary.org/obo/OBI_0000086") != -1)
//			System.out.println("Is one kind of Reagent");
//		else System.out.println("Is NOT Organism!");
//		model.close();
		
//		dataset.end();
		
		String soft1 = "<http://eagle-i.itmat.upenn.edu/i/00000138-81cd-dbef-9cd7-d7e280000000>";
		String soft2 ="<http://eagle-i.itmat.upenn.edu/i/0000013a-3178-df7b-01af-beb880000000>";
		String soft3 = "<http://eagle-i.itmat.upenn.edu/i/0000013d-4bda-0e4b-ae62-bb6880000000>";
		String bioSpec01 = "<http://eagle-i.itmat.upenn.edu/i/00000141-eb2e-7473-91c7-0c6080000000>";
		String database01= "<http://eagle-i.itmat.upenn.edu/i/00000140-785f-15cf-da89-3b9280000000>";
		String humanStudy01 = "<http://howard.eagle-i.net/i/0000013f-cf8f-a69d-6d89-79ed80000000>";
		String humanStudy02 = "<http://dartmouth.eagle-i.net/i/00000136-f445-a761-051b-e88580000000>";
		String instrument01 = "<http://dartmouth.eagle-i.net/i/0000012a-24fd-06ad-d8a1-249280000005>";
		String organism01 = "<http://eagle-i.itmat.upenn.edu/i/0000014e-6914-eb85-5109-12c780000000>";
		String protocol01 = "<http://dartmouth.eagle-i.net/i/0000014d-96ba-6f2f-b267-717180000000>";
		String reagent01 = "<http://harvard.eagle-i.net/i/0000012c-79f7-41b6-c437-ff0b80000000>";
		String reagent02 = "<http://harvard.eagle-i.net/i/0000012c-32e7-ea18-f249-7ef280000000>";
		String reagent03 = "<http://eagle-i.itmat.upenn.edu/i/00000138-8199-29fd-9cd7-d7e280000000>";
		String plasmid01 = "<http://eagle-i.itmat.upenn.edu/i/0000014d-fdf3-bac8-5109-12c780000000>";
		String plasmid02 = "<http://eagle-i.itmat.upenn.edu/i/00000138-8199-29fd-9cd7-d7e280000000>";
		String service01 = "<http://eagle-i.itmat.upenn.edu/i/0000013b-864e-da3d-83a0-df0880000000>";
		String researchOppo01 = "<http://dartmouth.eagle-i.net/i/0000012d-72ea-422c-4bd3-f7c180000000>";
		
		String test = "<http://harvard.eagle-i.net/i/0000012a­25bf­e274­f5ed­943080000000>";
		//
		String soft4 = "<http://eagle-i.itmat.upenn.edu/i/00000138-b992-b274-9cd7-d7e280000000>";
		
		String eID = humanStudy01;//;researchOppo01; 
		

		String citeResult = "{\"citation\":[{\"name\":[\"RNA-Seq Unified Mapper\"]},{\"developerName\":[\"Grant, Gregory R., Ph.D.\",\"DeLaurentis, Michael\",\"Pizarro, Angel\"]},{\"usedBy\":[\"University of Pennsylvania\"]},{\"URL\":[\"https://github.com/PGFI/rum\",\"http://www.cbil.upenn.edu/RUM/userguide.php\"]},{\"eagle-i_ID\":[\"http://eagle-i.itmat.upenn.edu/i/0000013a-3178-df7b-01af-beb880000000\"]}]}";
		citeResult = getCitationbyModel(eID, model);
		System.out.println(citeResult);
		//--- natan test
		try {
			convertToBiblatex(citeResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
			try {
				convertToRIS(citeResult);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		
		
		
		//-- end natan test
		
	//----	Actually converts the query to XML, just necessary to run on the first time
		String pstm = "SELECT * \n"
				+ "WHERE { ?node a ?categoryNode} ";
	
		ParameterizedSparqlString pss = testQuery(pstm);
		Query query = QueryFactory.create(pss.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet results = qexec.execSelect();

		String outStr = ResultSetFormatter.asText(results);
	    //System.out.println(pstm + " \n" + outStr);
		try 
		{
			System.out.println(convertToXML(citeResult));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		//---------
		model.close();
//		
		dataset.end();
		//checkReliability();
		
		
	}
	
	public static String getCitation(String eID)
	{
		Dataset dataset = TDBFactory.createDataset(rdfPath);
		dataset.begin(ReadWrite.READ);	
		Model model = initializeModel(dataset);	
		String citation = getCitationbyModel(eID, model);
		model.close();
		return citation;
	}
	
	public static String getCitationbyModel(String eID, Model model)
	{
	String pstm = "SELECT * \n"
	+ "WHERE { " + eID + " a ?categoryNode . \n"
	+ "?categoryNode rdfs:subClassOf* ?parentNodes .\n"
	+ "OPTIONAL{ ?parentNodes owl:someValuesFrom ?property } \n"
	+ "OPTIONAL{ ?parentNodes owl:equivalentClass ?equClass1. "
	+ "?equClass1 owl:intersectionOf ?equClass2."
	+ "?equClass2 rdf:first ?equClass3."
	+ "?equClass3 rdfs:subClassOf* ?parentNodes2 } } ";

	ParameterizedSparqlString pss = testQuery(pstm);
	Query query = QueryFactory.create(pss.toString());
	QueryExecution qexec = QueryExecutionFactory.create(query, model);
	ResultSet results = qexec.execSelect();

	String outStr = ResultSetFormatter.asText(results);
	//System.out.println(pstm + " \n" + outStr);

	//Software
	if(outStr.indexOf("<http://purl.obolibrary.org/obo/ERO_0000071>") != -1)
	{
		type = "software";
	String tempOutStr = getSoftwareCitation(eID,model);
	return tempOutStr;
	}
	//BioSpec
	else if(outStr.indexOf("<http://purl.obolibrary.org/obo/ERO_0000020>") != -1)
	{
	String tempOutStr = getBioSpecCitation(eID,model);
	return tempOutStr;
	}
	//Database
	else if(outStr.indexOf("<http://purl.obolibrary.org/obo/ERO_0001716>") != -1)
	{
	String tempOutStr = getSoftwareCitation(eID,model);
	return tempOutStr;
	}
	//Human Study
	//Various types, how to determine? 
	else if(outStr.indexOf("<http://purl.obolibrary.org/obo/ERO_0000004>") != -1)
	{
	String tempOutStr = getInstrumentCitation(eID,model);
	return tempOutStr;
	}
	//instrument
	//Are we going to use manufacturer? (Too many)
	else if(outStr.indexOf("<http://purl.obolibrary.org/obo/ERO_0000015>") != -1)
	{
	String tempOutStr = getHumanStudyCitation(eID,model); 
	return tempOutStr;
	}
	//Organism or Virus
	else if(outStr.indexOf("<http://purl.obolibrary.org/obo/OBI_0100026>") != -1)
	{
	String tempOutStr = getOrganismCitation(eID,model);
	return tempOutStr;
	}
	//Protocol
	else if(outStr.indexOf("<http://purl.obolibrary.org/obo/OBI_0000272>") != -1)
	{
	String tempOutStr = getProtocolCitation(eID,model);
	return tempOutStr;
	}
	//Serious problem with reagent: many parallel reagent types in Eagle-i ontology. 
	//How to specify different kinds of reagent? 
	//Reagent
	//use property_roles
	else if(outStr.indexOf("<http://purl.obolibrary.org/obo/OBI_0000086>") != -1 ||
	outStr.indexOf("<http://purl.obolibrary.org/obo/ERO_0000006>") != -1 || 
	outStr.indexOf("<http://purl.obolibrary.org/obo/ERO_0000285>") != -1 )
	{
	String tempOutStr = getReagentCitation(eID,model); 
	return tempOutStr;
	}
	//Research Oppotunity
	else if(outStr.indexOf("<http://purl.obolibrary.org/obo/ERO_0000595>") != -1)
	{
	String tempOutStr = getResearchOppotunityCitation(eID,model);
	return tempOutStr;
	}
	//Service
	//Ignore citation of service fee
	else if(outStr.indexOf("<http://purl.obolibrary.org/obo/ERO_0000005>") != -1)
	{
	String tempOutStr = getServiceCitation(eID,model); 
	return tempOutStr;
	}
	else
	{
	return "{\"citation\": [{\"error\":[\"No Citation Schema or Data Available! Check your input argument. "
	+ "An eagle-i ID is like: http://eagle-i.itmat.upenn.edu/i/0000013a-3178-df7b-01af-beb880000000\"]}]}";
	}
	}
	
	static Dataset populateTDB(Dataset ds, String name, String dataPath) 
	{
		Model tdb = ds.getNamedModel(name);

		// read the input file
		FileManager.get().readModel(tdb, dataPath);

		return ds;
	}
	
	public static Model initializeModel(Dataset ds)
	{
		// load the default model
		Model model = ModelFactory.createDefaultModel();

		// get all the named graphs in the TDB
		Iterator<String> graphNames = ds.listNames();
		while (graphNames.hasNext()) 
		{
			String graphName = graphNames.next();
			model.add(ds.getNamedModel(graphName));
		}
		
		return model;
	}
	
	private static ParameterizedSparqlString testQuery(String pstm) 
	{

		// prepared statement initialization
		ParameterizedSparqlString pss = new ParameterizedSparqlString();

		// set the prefixes
		pss.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
		pss.setNsPrefix("dc", "http://purl.org/dc/terms/");
		pss.setNsPrefix("obo", "http://purl.obolibrary.org/obo/");
		pss.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
		pss.setNsPrefix("owl", "http://www.w3.org/2002/07/owl#");
		pss.setNsPrefix("foaf", "http://xmlns.com/foaf/0.1/");
		pss.setNsPrefix("", "http://eagle-i.org/ont/repo/1.0/");
		pss.setNsPrefix("sesame", "http://www.openrdf.org/schema/sesame#");
		pss.setNsPrefix("fn", "http://www.w3.org/2005/xpath-functions#");
		
		pss.setCommandText(pstm);
		
		return pss;

	}
	

	private static String GetSerializedOutputFromQueryResult(ResultSet qresults)
	{
		String resultText= ResultSetFormatter.asText(qresults);
	//	ResultSetFormatter.outputAsJSON(qresults);

		Pattern pattern = Pattern.compile("\\\"(.*?)\\\"");
		Matcher matcher = pattern.matcher(resultText);
		String outputText ="";
		while(matcher.find())
		{
			outputText += matcher.group()+"; ";
		}
		outputText=outputText.replaceAll("\"", "");
		
		
		return outputText;
	}
	
	private static String GetSerializedOutputFromQueryResult(ResultSet qresults, String eID)
	{
		String outputText ="";
//		ResultSetFormatter.output(qresults);
//		ResultSetFormatter.outputAsJSON(qresults);
//		String resultText1 = ResultSetFormatter.asText(qresults);
//		String resultText1 = ResultSetFormatter.asXMLString(qresults);
//		System.out.println(resultText1);
		Map<String, List<String>> answer = new LinkedHashMap<String, List<String>>();
		List<String> queryTitle = qresults.getResultVars();
		
		while(qresults.hasNext())
		{
			QuerySolution soln = qresults.next();
			Iterator<String> names = soln.varNames();

			while (names.hasNext())
			{
				String var = names.next();
				List<String> thisRow = new ArrayList<String>();
				if(qresults.getRowNumber() > 1)
				{
					thisRow = answer.get(var);
					Iterator<String> iter = thisRow.iterator();
					int listContainsItem = 0;
					while(iter.hasNext())
					{
						if(iter.next().equals(soln.get(var).toString())) 
							listContainsItem = 1;
						
					}
					if(listContainsItem == 0)
						thisRow.add(soln.get(var).toString());
				}
				else
				{
					thisRow.add(soln.get(var).toString());
				}
				answer.put(var, thisRow);
			}

		}
//		System.out.println(answer);
		
		Map<String, List<String>> eachItem = new LinkedHashMap<String, List<String>>();
		List<Map<String, List<String>>> finalCitation = new ArrayList<Map<String, List<String>>>();
		for(int i = 0; i < queryTitle.size(); i ++)
		{
	//		System.out.println(queryTitle.get(i));
			if(answer.containsKey(queryTitle.get(i)))
			{
				eachItem = new LinkedHashMap<String, List<String>>();
				eachItem.put(queryTitle.get(i), answer.get(queryTitle.get(i)));
				finalCitation.add(eachItem);
			}
		}
		eachItem = new LinkedHashMap<String, List<String>>();
		List<String> eIDList = new ArrayList<String>();
		eIDList.add(eID.substring(1,eID.length() - 1));
		eachItem.put("eagle-i_ID", eIDList);
		finalCitation.add(eachItem);
	//	System.out.println(finalCitation);
		JSONArray content = new JSONArray(finalCitation);
		JSONObject citeInJSON = new JSONObject();
		citeInJSON.put("citation", content);
		
		return citeInJSON.toString();
	}
	
	private static String getSoftwareCitation(String eID, Model model)
	{
		ResultSet qresultName = queryGeneralInfo(model, eID, "?name ?version ?manuName ?developerName ?usedBy ?URL");
		String serialOutput = GetSerializedOutputFromQueryResult(qresultName,  eID);
		return serialOutput; 
	}
	
	private static String getBioSpecCitation(String eID, Model model)
	{
		ResultSet qresultName = queryGeneralInfo(model, eID, "?name ?usedBy ?URL");
		String serialOutput = GetSerializedOutputFromQueryResult(qresultName,  eID);
		return serialOutput; 
	}
	
	private static String getHumanStudyCitation(String eID, Model model)
	{
		ResultSet qresultName = queryGeneralInfo(model, eID, "?name ?topicName ?performedBy ?subjectArea ?resourceType ?URL");
		String serialOutput = GetSerializedOutputFromQueryResult(qresultName,  eID);
		return serialOutput; 
	}
	
	private static String getInstrumentCitation(String eID, Model model)
	{
		ResultSet qresultName = queryGeneralInfo(model, eID, "?name ?modelNumber ?location ?resourceType ?URL");
		String serialOutput = GetSerializedOutputFromQueryResult(qresultName,  eID);
		return serialOutput; 
	}
	
	private static String getOrganismCitation(String eID, Model model)
	{
		ResultSet qresultName = queryGeneralInfo(model, eID, "?name ?location ?resourceType");
		String serialOutput = GetSerializedOutputFromQueryResult(qresultName,  eID);
		return serialOutput; 
	}
	
	private static String getProtocolCitation(String eID, Model model)
	{
		ResultSet qresultName = queryGeneralInfo(model, eID, "?name ?usedBy ?author ?resourceType");
		String serialOutput = GetSerializedOutputFromQueryResult(qresultName,  eID);
		return serialOutput; 
	}
	
	private static String getReagentCitation(String eID, Model model)
	{
		ResultSet qresultName = queryGeneralInfo(model, eID, "?name ?location ?resourceType ?URL");
		String serialOutput = GetSerializedOutputFromQueryResult(qresultName,  eID);
		return serialOutput; 
	}
	
	private static String getResearchOppotunityCitation(String eID, Model model)
	{
		ResultSet qresultName = queryGeneralInfo(model, eID, "?name ?resourceType ?researchProvider ?URL");
		String serialOutput = GetSerializedOutputFromQueryResult(qresultName,  eID);
		return serialOutput; 
	}
	
	private static String getServiceCitation(String eID, Model model)
	{
		ResultSet qresultName = queryGeneralInfo(model, eID, "?name ?resourceType ?serviceProvider ?URL");
		String serialOutput = GetSerializedOutputFromQueryResult(qresultName,  eID);
		return serialOutput; 
	}
	
	private static ResultSet queryGeneralInfo(Model model, String eID, String Item)
	{
		String pstm = "SELECT DISTINCT " + Item + " \n"
				+ "WHERE	{	" +eID +" rdfs:label ?name; \n"
				+ "OPTIONAL {"+eID+" <http://purl.obolibrary.org/obo/ERO_0000480> ?URL} \n"
				+ "OPTIONAL {"+eID+" a ?resourceTypeID. \n"
					+ "?resourceTypeID rdfs:label ?resourceType} \n"
				+ "OPTIONAL{"+eID+" <http://purl.obolibrary.org/obo/ERO_0000034> ?manuId. "
					+ "?manuId rdfs:label ?manuName} \n"
				+ "OPTIONAL{ "+eID+" <http://purl.obolibrary.org/obo/ERO_0000072> ?version } \n"
				+ "OPTIONAL{ "+eID+" <http://purl.obolibrary.org/obo/ERO_0001521> ?performedByID. \n"
					+ " ?performedByID rdfs:label ?performedBy } \n"
				+ "OPTIONAL{"+eID+" <http://purl.obolibrary.org/obo/ERO_0000070> ?usedByID . "
					+ "?usedByID rdfs:label ?usedBy} \n"
				+ "OPTIONAL{ "+eID+" <http://eagle-i.org/ont/app/1.0/has_subject_area> ?subjectAreaID. \n"
					+ "?subjectAreaID rdfs:label ?subjectArea } \n"
				+ "OPTIONAL{ "+eID+" <http://purl.obolibrary.org/obo/ERO_0000234>  ?topicNameID. \n"
					+ "?topicNameID rdfs:label ?topicName } \n"
				+ "OPTIONAL{ "+eID+" <http://purl.obolibrary.org/obo/RO_0001025>  ?locationID. \n"
					+ "?locationID rdfs:label ?location } \n"
				+ "OPTIONAL{ "+eID+" <http://purl.obolibrary.org/obo/ERO_0000232>  ?authorID. \n"
					+ "?authorID rdfs:label ?author } \n"
				+ "OPTIONAL{ "+eID+" <http://eagle-i.org/ont/app/1.0/research_opportunity_provided_by> ?researchProviderID. \n"
					+ "?researchProviderID rdfs:label ?researchProvider } \n"
				+ "OPTIONAL{ "+eID+" <http://purl.obolibrary.org/obo/ERO_0000390> ?serviceProviderID. \n"
					+ "?serviceProviderID rdfs:label ?serviceProvider } \n"
				+ "OPTIONAL{ "+eID+" <http://purl.obolibrary.org/obo/ERO_0000050>  ?modelNumber} \n"
				+ "OPTIONAL{"+eID+" <http://purl.obolibrary.org/obo/ERO_0001719> ?developerID. "
					+ "?developerID rdfs:label ?developerName} }";
		
		ParameterizedSparqlString pss = testQuery(pstm);
		
	//	System.out.println(pss);
		
		Query query = QueryFactory.create(pss.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet qresults = qexec.execSelect();
		return qresults;
	}

	public static String convertToBiblatex(String jsonStr) throws Exception 
	{
		String biblaTexEntry = "";
		JSONObject jsObj = new JSONObject(jsonStr);
		JSONArray jsArray = jsObj.getJSONArray("citation");

		System.out.println("");
		
		String citationKey = jsArray.getJSONObject(0).getJSONArray("name").toString();
		citationKey = citationKey.replace("[", "");
		citationKey = citationKey.replace("]", "");
		citationKey = citationKey.replace("\"", ""); // Get rid of "
		citationKey = citationKey.replace(" ", "");
		citationKey = citationKey.replace("(", "");
		citationKey = citationKey.replace(")", "");
		citationKey = citationKey.replace("-", "");
		citationKey = citationKey.replace("_", "");
		citationKey = citationKey.toLowerCase();
		//keySet(); gets the name (TAG) from the object
		
		/*We are keeping track of the type because if the type is "software" we are going to create the BibTex with
		 * entry type @software. When the type is not "software" we are going to use @customA*/
		if (type == "software"){
			biblaTexEntry = "@software{" +  citationKey + ",";
		}else{
			biblaTexEntry = "@customa{" +  citationKey + ",";
		}
		
		for (int i = 0; i < jsArray.length(); i++){
			// Example:
			// TAG = name
			// content = RNA-Seq Unified Mapper
			// finalLine = name = {RNA-Seq Unified Mapper},
			

			// Gets the TAGS, they will be [name], [title], [url], etc.
				String TAG = jsArray.getJSONObject(i).keySet().toString();
				TAG = TAG.replace("[", "");
				TAG = TAG.replace("]", "");
				
				//Gets the contante whithin the TAG
				String content = jsArray.getJSONObject(i).getJSONArray(TAG).toString();
				content = content.replace("\",\"", " and "); // Bibtex use and instead of commas to separe names
				content = content.replace("[", "");
				content = content.replace("]", "");
				content = content.replace("\"", "");
				
				/* Many tags are comming with names that are not supported by standard BibTex, we are changing
				 * these tags names so they will work and also make more sense*/
				switch (TAG) {
				case "name":
					TAG = "title";
					break;
				case "developerName":
					TAG = "author";
					break;
				case "manuName":
					TAG = "author";
					break;			
				case "performedBy":
					TAG = "author";
					break;	
				case "usedBy":
					TAG = "organization";
					break;
				case "location":
					TAG = "organization";
					break;
				case "researchProvider":
					TAG = "organization";
					break;
				case "serviceProvider":
					TAG = "organization";
					break;
				case "URL":
					TAG = "howpublished";
					break;
				case "eagle-i_ID":
					TAG = "url";
					break;
				case "resourceType":
					TAG = "type";
					break;
				default:
					break;
				}
				
				// The final BibTex line
				String finalLine = TAG + " = {" + content + "},";
				
				// This is necessary to BiblaTex work
				if (TAG == "howpublished"){
					finalLine = TAG + " = \"" + "\\url{" + content + "}\",";
				}
				
				biblaTexEntry = biblaTexEntry + "\n" + finalLine;

		}

		
		biblaTexEntry = biblaTexEntry + "\n}";
		System.out.println(biblaTexEntry);
		System.out.println(" ");

		// We will return biblaTexEntry
		return null;
		
	}
	///RIS
	// string parsing is a good approach to create BibTex document
	public static void convertToRIS(String jsonStr) throws Exception 
	{	
		// first tag that has to be above every RIS entry 
		System.out.println("\n"+"TY"+ "  - " + "ELEC");
		// array stores the two letter tags used in RIS 
		String[][] abbrevation = {{"name","TI"},{"developerName","A1"},{"usedBy", "DP"},{"URL","UR"},{"eagle-i_ID","ID"}};
		
		JSONObject jsObj = new JSONObject(jsonStr);
		JSONArray jsArray = jsObj.getJSONArray("citation");
		
		for (int i = 0; i < jsArray.length(); i++){
			// getting the key for each part of the data (example: name) 
			String Key = jsArray.getJSONObject(i).keySet().toString();
		    Key = Key.replace("[","");
		    Key = Key.replace("]","");
		    //for loop checks the double array created above for the key 
			 for(int l = 0; l < abbrevation.length; l++){
				 //if key is found in the array then the content from the JSONArray is retrieved (example: ["RNA-Seq Unified Mapper"]);
				 if (abbrevation[l][0].equals(Key)){
					 String[] subject = (jsArray.getJSONObject(i).getJSONArray(Key).toString()).split("\",\"");
					 // loop goes through the content array 
				     for(int j = 0; j < subject.length; j++){
				    	 if(j>0){
				    		 abbrevation[l][1] = abbrevation[l][1].replace((abbrevation[l][1]).charAt(1),Integer.toString(j+1).charAt(0));
				    	 }
				    	 	System.out.print(abbrevation[l][1]+"  - ");
				    	 	subject[j] = subject[j].replace("[\"", "");
				    	 	subject[j] = subject[j].replace("\"]", "");
				        	System.out.println(subject[j]);
				     }
				 }
			 }
	      
			}
		System.out.println("ER"+ "  - ");
	}

	
	//
	
	
	
	
	
	//--
	public static String convertToXML(String jsonStr) throws Exception 
	{
		//TODO: 
		JSONObject jsObj = new JSONObject(jsonStr);
		JSONArray jsArray = jsObj.getJSONArray("citation");
		JSONObject eachNode = new JSONObject();
		
		
		///TEST NATAN///
		//jsArray.put(eachNode);
		
		// Changin field names so they will many data cite schema
		for (int i = 0; i < jsArray.length(); i++){
			System.out.println(jsArray.getJSONObject(i));
			
			switch (jsArray.getJSONObject(i).keySet().toString()) {
			case "[name]":
				jsArray.getJSONObject(i).put("title", jsArray.getJSONObject(i).remove("name"));
				break;
			case "[manuName]":
				jsArray.getJSONObject(i).put("creators", jsArray.getJSONObject(i).remove("manuName"));
				break;
			case "[performedBy]":
				jsArray.getJSONObject(i).put("creators", jsArray.getJSONObject(i).remove("performedBy"));
				break;

			default:
				break;
			}
			
			
		}
		
		///END TEST NATAN/
		
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

		Element citationRoot = doc.createElement("citation");
		
		for(int i = 0; i < jsArray.length(); i ++)
		{
			eachNode = jsArray.getJSONObject(i);
			String thisKey = eachNode.keys().next().toString();
			Element nodeElement = doc.createElement(thisKey);
			JSONArray tempArray = eachNode.getJSONArray(thisKey);
			if(tempArray.length() <= 1)//<name>
			{
				String tempContent = tempArray.getString(0);
				Text nodeContent = doc.createTextNode(tempContent);
				nodeElement.appendChild(nodeContent);
			}
			else//<names/>
			{
				nodeElement = doc.createElement(thisKey+"s");
				for(int j = 0; j < tempArray.length(); j ++)
				{
					String tempContent = tempArray.getString(j);
					Element tempElement = doc.createElement(thisKey);
					Text nodeContent = doc.createTextNode(tempContent);
					tempElement.appendChild(nodeContent);
					nodeElement.appendChild(tempElement);
				}
			}

			citationRoot.appendChild(nodeElement);
		}

        
        Element root = doc.createElement("font");
        Element nameElement = doc.createElement("name");
        Text nameValue = doc.createTextNode("san");
        Element sizeElement = doc.createElement("size");
        sizeElement.setAttribute("unit", "px");
        Text sizeValue = doc.createTextNode("14");
       
        doc.appendChild(citationRoot);
        root.appendChild(nameElement);
        nameElement.appendChild(nameValue);
        root.appendChild(sizeElement);
        sizeElement.appendChild(sizeValue);
        
        TransformerFactory tf = TransformerFactory.newInstance();
        tf.setAttribute("indent-number", new Integer(2));
        Transformer t= tf.newTransformer();
        
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        t.setOutputProperty(OutputKeys.INDENT,"yes");
        t.setOutputProperty(OutputKeys.METHOD, "xml");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        
//        FileOutputStream out = new FileOutputStream(file);
//        StreamResult xmlResult = new StreamResult(out);
//        t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(new File("f:/text.xml"))));
        t.transform(new DOMSource(doc), new StreamResult(bos));

		return bos.toString();
	}
	
	public static String jsonToCitaton(String jsonCitation)
	{
		JSONObject jsObj = new JSONObject(jsonCitation);
		String parseXML = XML.toString(jsObj);
		
		String xmlCitation = "";
        try 
        {
			xmlCitation = TestJena.convertToXML(jsonCitation);
		} 
        catch (Exception e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuffer normalCitation = new StringBuffer(); 
		String reg = "(<.*?>)*(.*?)</.*?>";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(parseXML);
        while(m.find())
        {
        	String s1 = m.group(2);
            //System.out.println("data-->"+s1);
            normalCitation.append(s1);
            normalCitation.append("; ");
        }
 //       System.out.println(xmlCitation);
		
		//send normal citation back to web page as response
        String citationResult = normalCitation.toString();
        citationResult = citationResult.substring(0, citationResult.length() - 2);
  //      citationResult = citationResult.replaceAll("; ;", ";");
        return citationResult;
	}
	
	public static boolean checkReliability1()
	{
		Dataset dataset = TDBFactory.createDataset(rdfPath);
		Model model = ModelFactory.createDefaultModel();
		Model upennModel = ModelFactory.createDefaultModel(); 


		model.add(dataset.getNamedModel(upennName));
		model.add(dataset.getNamedModel(eroName));
		model.add(dataset.getNamedModel(globalName));
		
		upennModel.add(dataset.getNamedModel(upennName));
		
		String pstm = "SELECT ?eID ?typeName \n"
				+ "WHERE {?eID :hasWorkflowState :WFS_Published;"
				+ "a ?typeNode. ?typeNode rdfs:label ?typeName}";

		ParameterizedSparqlString pss = testQuery(pstm);
		Query query = QueryFactory.create(pss.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, upennModel);
		ResultSet qresults = qexec.execSelect();

	//	String outStr = ResultSetFormatter.asText(qresults);

		
		String checkResult = "";
		while(qresults.hasNext())
		{
			QuerySolution soln = qresults.next();
			Iterator<String> names = soln.varNames();
			
			String normalCitation = "";
			while (names.hasNext())
			{
				
				String var = names.next();
				String tempEID = "";
		//		System.out.println(tempEID);
				if(var.equals("eID"))
				{
					tempEID = "<"+ soln.get(var).toString().trim()+">";
					normalCitation = jsonToCitaton(getCitationbyModel(tempEID, model));
					checkResult += String.format("%-75s", tempEID) + "|\t";
				}
				else
				{
					int strLength = soln.get(var).toString().length();
					checkResult += String.format("%-30s", 
							soln.get(var).toString().substring(0, Integer.min(27, strLength))) + "|\t";
				}
			}
			checkResult += normalCitation + "\r\n";
		}

		System.out.println(checkResult);
		try 
		{
			String filePath = "test.txt";
			File file1 = new File(filePath);
			if (file1.exists()) 
			{  
	          //  System.out.println("Exist");  
	        } 
			else 
	        {  
	          //  System.out.println("Not Exist");  
				file1.createNewFile();
	
	        } 
			BufferedWriter output = new BufferedWriter(new FileWriter(file1));  
	        
			output.write(checkResult);
	        output.close(); 
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean checkReliability()
	{
	Dataset dataset = TDBFactory.createDataset(rdfPath);
	Model model = ModelFactory.createDefaultModel();
	Model upennModel = ModelFactory.createDefaultModel(); 


	model.add(dataset.getNamedModel(upennName));
	model.add(dataset.getNamedModel(eroName));
	model.add(dataset.getNamedModel(globalName));

	upennModel.add(dataset.getNamedModel(upennName));

	String pstm = "SELECT ?eID ?typeName \n"
	+ "WHERE {?eID :hasWorkflowState :WFS_Published;"
	+ "a ?typeNode. ?typeNode rdfs:label ?typeName}";

	ParameterizedSparqlString pss = testQuery(pstm);
	Query query = QueryFactory.create(pss.toString());
	QueryExecution qexec = QueryExecutionFactory.create(query, upennModel);
	ResultSet qresults = qexec.execSelect();

	// String outStr = ResultSetFormatter.asText(qresults);


	String checkResult = "";
	while(qresults.hasNext())
	{
	QuerySolution soln = qresults.next();
	Iterator<String> names = soln.varNames();

	String normalCitation = "";
	boolean noCitation = false;
	while (names.hasNext())
	{

	String var = names.next();
	String tempEID = "";
	// System.out.println(tempEID);
	if(var.equals("eID"))
	{
	tempEID = "<"+ soln.get(var).toString().trim()+">";
	normalCitation = jsonToCitaton(getCitationbyModel(tempEID, model));
	if(normalCitation.contains("No Citation"))
	noCitation = true;
	else noCitation = false;
	if(noCitation) checkResult += String.format("%-75s", tempEID) + "|\t";
	}
	else
	{
	int strLength = soln.get(var).toString().length();
	if(noCitation) checkResult += String.format("%-30s", 
	soln.get(var).toString().substring(0, Integer.min(27, strLength))) + "|\t";
	}
	}
	if(noCitation) checkResult += normalCitation + "\r\n";
	}

	System.out.println(checkResult);
	try 
	{
	String filePath = "test.txt";
	File file1 = new File(filePath);
	if (file1.exists()) 
	{  
	          //  System.out.println("Exist");  
	        } 
	else 
	        {  
	          //  System.out.println("Not Exist");  
	file1.createNewFile();

	        } 
	BufferedWriter output = new BufferedWriter(new FileWriter(file1));  
	        
	output.write(checkResult);
	        output.close(); 
	} 
	catch (IOException e) 
	{
	e.printStackTrace();
	}
	return false;
	}
}


