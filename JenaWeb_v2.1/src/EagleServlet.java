import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.tdb.TDBFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import testJena.TestJena;
import testJena.TestJena.outFormat;


public class EagleServlet extends HttpServlet { 
	/**
	 *
	 */
	
	static Dataset dataset = TDBFactory.createDataset(TestJena.rdfPath);
	static Model model = TestJena.initializeModel(dataset);
	
//	public EagleServlet()
//	{
//		Dataset dataset = TDBFactory.createDataset(TestJena.rdfPath);
//		dataset.begin(ReadWrite.READ);	
//		Model model = TestJena.initializeModel(dataset);
//	}
	
	private static final long serialVersionUID = -4502955968124312019L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException, NullPointerException { 
		
		//get the resource
		String a = "<";
		String b = ">";
		String resource= a.concat(request.getParameter("resource").trim()).concat(b);
	//	System.out.println("Resource-->"+resource);
		//TestJena test = new TestJena();
	//	System.out.println("Citation-->" + TestJena.getCitation(resource, outFormat.JSONFORMAT));
		//build the response/citation
	//	response.setContentType("text/html");
	//	response.getWriter().print("AAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		String citeResult = TestJena.getCitationbyModel(resource, model);
	//	response.getWriter().println(citeResult);
	//	response.getWriter().println(TestJena.getCitation(resource, outFormat.STRINGFORMAT));
	//	citeResult = "{\"name\":[{\"content\":\"Benchmarker for Evaluating the Effectiveness of RNA-Seq Software\"}],\"version\":[],\"developer\":[{\"content\":\"Grant, Gregory R., Ph.D.\"}],\"manufacturer\":[],\"used_by\":[{\"content\":\"Penn Center for Bioinformatics\"}],\"URL\":[{\"content\":\"http://www.cbil.upenn.edu/BEERS/\"}],\"eagle-i_ID\":\"http://eagle-i.itmat.upenn.edu/i/0000013d-4bda-0e4b-ae62-bb6880000000\"}";
				
	
		JSONObject jsObj = new JSONObject(citeResult);
		String parseXML = XML.toString(jsObj);
		
		String xmlCitation = "";
        try 
        {
			xmlCitation = TestJena.convertToXML(citeResult);
		} 
        catch (Exception e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StringBuffer normalCitation = new StringBuffer(); 
		String reg = "(<.*?>)*(.*?)</.*?>";
        Pattern p = Pattern.compile(reg);
        java.util.regex.Matcher m = p.matcher(parseXML);
        while(m.find())
        {
        	String s1 = m.group(2);
            //System.out.println("data-->"+s1);
            normalCitation.append(s1);
            normalCitation.append("; ");
        }
        System.out.println(xmlCitation);
		
		//send normal citation back to web page as response
        String citationResult = normalCitation.toString();
        citationResult = citationResult.substring(0, citationResult.length() - 2);
        citationResult = citationResult.replaceAll("; ;", ";");

        System.out.println(citationResult);
        HttpSession session = request.getSession();
        session.setAttribute("CiteResult", citationResult);


        String escapedXML = StringEscapeUtils.escapeHtml(xmlCitation);
        
        session.setAttribute("XMLResult", xmlCitation);
        response.sendRedirect("showCitation.jsp");
        
	//	response.setContentType("text/html");
	//	response.getWriter().println(citationResult);
	//	response.getWriter().println("<br/><br/> <input type=\"button\" value=\"Close\" onclick=\"window.close()\"> " );
	}
	
	public static void main (String args[]){

	//	TestJena test = new TestJena();
	//	System.out.println(test);
	//	System.out.println("");
		model.close();
		dataset.end();
		System.out.println("Terminated.");
		
		return;
	}
}