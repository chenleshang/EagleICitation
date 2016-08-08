import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibatis.common.jdbc.ScriptRunner;
import com.mysql.jdbc.CallableStatement;

public class DatabaseConnection {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	public DatabaseConnection() {

	}

	public void connect(String databaseName){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
//			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, "root", "password");
			connection = DriverManager.getConnection("jdbc:mysql://fling.seas.upenn.edu:3306/" + databaseName, "USER_NAME[EMAIL ME TO GET IT]", "PASSWORD[EMAIL ME TO GET IT]");
			statement = connection.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void select() {
		String query = "select * from eaglei_resources";
		try {
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()){
				String subject = resultSet.getString("subject");
				String predicate = resultSet.getString("predicate");
				String object = resultSet.getString("object");
			}

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void callProcedure(String query){
		try {
			System.out.println("query: " + query);
			CallableStatement stmt = (CallableStatement) connection.prepareCall(query);
			stmt.executeQuery();
			System.out.println("query: successful executed.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public void insert(String table, ArrayList<String> column){
		String date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		String type = "ONTOLOGY";
		if (column.size() > 0){
			//
			// If subject has eagle-i and /i/ then it's an eagle id, otherwise it's ontology
			String url = column.get(0);
			
			if (url.contains("eagle-i") && url.contains("/i/")){
				type = "EAGLEID";
			}
			
			String query = ("insert into " + table +  " VALUES(" + "'" + column.get(0) + "'" + "," + "'" + column.get(1) + "'" + "," + "'" + column.get(2) + "'" + "," + "'" + date + "'" + "," + "'" + type + "'" + ")");
			
			// Used to fix phrases like "University's teacher or Ones' dinner". If not fixing this we would have error while trying to insert into MySQL
			query = query.replace("'s", "''s");
			query = query.replace("s' ", "s''");
			
			
			System.out.println(query);
			try {
				statement.executeUpdate(query);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// If any error occurs, stop the program so we can read it on the console and solve it.
				   System.exit(1);
			}
		}

	}
	
	public void prepareDatabaseCreation(String databaseName){
		// Creates a file .sql
		
		PrintWriter writer;
		try {
			writer = new PrintWriter("the-file-name.sql", "UTF-8");
			writer.println("The first line");
			writer.println("The second line");
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void runScript(String institution){
		
		Path path = Paths.get("script.sql");
		Charset charset = StandardCharsets.UTF_8;

		String content;
		try {
			
			content = new String(Files.readAllBytes(path), charset);
			content = content.replaceAll("pre_table_name_", institution + "_");
			Files.write(path, content.getBytes(charset));
			
			
			//System.out.println(connection);
			
			ScriptRunner runner=new ScriptRunner(connection, false, false);
			InputStreamReader reader = new InputStreamReader(new FileInputStream("script.sql"));
			runner.runScript(reader);
			reader.close();
			//co.close();

			
			//After running change the variables again in script.sql
			content = new String(Files.readAllBytes(path), charset);
			content = content.replaceAll(institution + "_", "pre_table_name_");
			Files.write(path, content.getBytes(charset));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}
	
	
	public void createStoreProcedure(String institution){
		  Statement stmt =null;

	            try {
					stmt = connection.createStatement();
					
					stmt.execute("DROP PROCEDURE IF EXISTS " + institution + "_populate_eaglei_validtime_V2;");
					stmt.execute("DROP PROCEDURE IF EXISTS " + institution + "_populate_eaglei_changes_log_triple;");
					stmt.execute("DROP PROCEDURE IF EXISTS " + institution + "_populate_eaglei_changes_log_resource;");

					  stmt.execute(
//							 " DELIMITER $$"
//					 		+ 
							 " CREATE PROCEDURE " + institution + "_populate_eaglei_validtime_V2()\n"
//					 		+ " DETERMINISTIC"
					 		+ " BEGIN\n"
					 		+ " DECLARE tripleID int default 0;\n"
					 		+ " DECLARE countID int;\n"
					 		+ " DECLARE temp_subject TEXT;\n"
					 		+ " DECLARE temp_predicate TEXT;\n"
					 		+ " DECLARE temp_object TEXT;\n"
					 		+ " DECLARE temp_type VARCHAR(10);\n"
					 		+ " DECLARE temp_version_date VARCHAR(8);\n"
					 		+ " DECLARE lastVersion_date VARCHAR(8);\n"
					 		+ " DECLARE done INT DEFAULT 0;\n"
					 		
					 		+ " DECLARE cur CURSOR FOR\n"
					 		+ " SELECT subject, predicate, object, type, version_date FROM " + institution + "_temp_eaglei_resources;\n"
					 		
					 		+ " BEGIN \n"
					 		+ " SET countID =(SELECT count(id) from "+ institution +"_eaglei_global_vars);\n" 
					 		+ " SET lastVersion_date =(SELECT last_version From "+ institution +"_eaglei_global_vars);\n"
					 		+ " IF(countID = 0 or countID is NULL) THEN\n"
					 		+ " INSERT INTO " + institution + "_eaglei_global_vars VALUES (1,' ',' ');\n"
					 		+ " END IF;\n"
					 		+ " END;\n"
					 		+ " OPEN cur;\n"
					 		+ " BEGIN\n"
					 		+ " DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;\n"
					 		+ " read_loop: LOOP\n"
					 		+ " FETCH cur INTO  temp_subject, temp_predicate, temp_object, temp_type, temp_version_date;\n"
					 		+ " IF done THEN\n"
					 		+ " LEAVE read_loop;\n"
					 		+ " END IF;\n"
					 		+ " SET tripleID=0;\n" 
					 		+ " SET tripleID=(SELECT id FROM "+ institution +"_eaglei_resources" 
					 		+ " WHERE subject = temp_subject AND predicate = temp_predicate AND object = temp_object);\n"
					 		+ " IF (tripleID = 0 or tripleID is NULL) THEN\n" 
					 		+ " INSERT INTO "+ institution +"_eaglei_resources (subject, predicate, object, type) VALUES (temp_subject, temp_predicate, temp_object, temp_type);\n" 
					 		+ " SET tripleID=(SELECT id FROM "+ institution +"_eaglei_resources WHERE subject = temp_subject AND predicate = temp_predicate AND object = temp_object);\n" 
					 		+ " INSERT INTO "+ institution +"_eaglei_validtime values (tripleID, temp_version_date, temp_version_date);\n" 
					 		+ " ELSEIF (EXISTS(SELECT * FROM "+ institution +"_eaglei_resources r join "+ institution +"_eaglei_validtime t on r.id=t.id WHERE r.id=tripleID and t.end_version<temp_version_date AND t.end_version != lastVersion_date)) THEN\n" 
					 		+ " INSERT INTO "+ institution +"_eaglei_validtime values (tripleID, temp_version_date, temp_version_date);\n" 
					 		+ " ELSE UPDATE "+ institution +"_eaglei_validtime SET end_version = temp_version_date WHERE id = tripleID;\n" 
					 		+ " END IF;\n" 
					 		+ " END LOOP;\n" 
					 		+ " BEGIN \n" 
					 		+ " UPDATE "+ institution +"_eaglei_global_vars g SET g.today = temp_version_date WHERE g.id = '1';\n" 
					 		+ " END;\n" 
					 		+ " END;\n" 
					 		+ " CLOSE cur;\n" 
					 		+ " END\n"
//					 		+ " $$"
//					 		+ " DELIMITER ;"
					 		+ " ");
					 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            try {
					stmt = connection.createStatement();
					stmt.execute(
//							+ " DELIMITER $$"
							  " CREATE PROCEDURE " + institution + "_populate_eaglei_changes_log_triple()"
//							+ " DETERMINISTIC"
							+ " BEGIN"
							+ " DECLARE tripleID int;"
							+ " DECLARE tripleStartVersion VARCHAR(8);"
							+ " DECLARE done INT DEFAULT 0;"
							+ " DECLARE done2 INT DEFAULT 0;"
							+ " DECLARE cur CURSOR FOR"
							+ " SELECT v.start_version, v.id FROM " + institution + "_eaglei_validtime v"
							+ " WHERE v.start_version = (SELECT today FROM " + institution + "_eaglei_global_vars where id = 1)"
							+ " AND v.id NOT IN (SELECT t.id FROM " + institution + "_eaglei_changes_log_triple t"
							+ " WHERE t.operation = 'ADDED'"
							+ " AND t.date_log_generated = (SELECT today FROM " + institution + "_eaglei_global_vars where id = 1));"
							+ " DECLARE cur2 CURSOR FOR"
							+ " SELECT DISTINCT v.id FROM " + institution + "_eaglei_validtime v"
							+ " WHERE v.end_version < (SELECT today FROM " + institution + "_eaglei_global_vars where id = 1)"
							+ " AND v.id NOT IN (SELECT t.id FROM " + institution + "_eaglei_changes_log_triple t"
							+ " WHERE t.operation = 'DELETED'"
							+ " AND t.date_log_generated = (SELECT last_version FROM " + institution + "_eaglei_global_vars where id = 1));"
							+ " OPEN cur;"
							+ " BEGIN"
							+ " DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;"
							+ " read_loop: LOOP"
							+ " FETCH cur INTO tripleStartVersion, tripleID;"
							+ " IF done THEN"
							+ " LEAVE read_loop;"
							+ " END IF;"
							+ " INSERT INTO " + institution + "_eaglei_changes_log_triple VALUES (tripleStartVersion, tripleID, (SELECT r.type FROM " + institution + "_eaglei_resources r where r.id = tripleID), 'ADDED');"
							+ " END LOOP;"
							+ " END;"
							+ " CLOSE cur;"
							
							+ " OPEN cur2;"
							+ " BEGIN"
							+ " DECLARE CONTINUE HANDLER FOR NOT FOUND SET done2 = 1;"
							+ " read_loop: LOOP"
							+ " FETCH cur2 INTO tripleID;"
							+ " IF done2 THEN"
							+ " LEAVE read_loop;"
							+ " END IF;"
							
							+ " INSERT INTO " + institution + "_eaglei_changes_log_triple VALUES ((SELECT today FROM " + institution + "_eaglei_global_vars where id = 1), tripleID, (SELECT r.type FROM " + institution + "_eaglei_resources r where r.id = tripleID), 'DELETED');"
							+ " END LOOP;"
							+ " END;"
							+ " CLOSE cur2;"
							+ " END;"
//							+ "$$"
//							+ "DELIMITER ;"
							+ " ");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	            try {
					stmt.execute(
//							+ "DELIMITER $$"
							  " CREATE PROCEDURE " + institution + "_populate_eaglei_changes_log_resource()"
//							+ " DETERMINISTIC"
							+ " BEGIN"
							+ " DECLARE tripleID int;"
							+ " DECLARE eagle_id TEXT;"
							+ " DECLARE subject TEXT;"
							+ " DECLARE done INT DEFAULT 0;"
							+ " DECLARE done2 INT DEFAULT 0; "
							+ " DECLARE cur CURSOR FOR"
							+ " SELECT DISTINCT r.subject FROM " + institution + "_eaglei_resources r"
							+ " WHERE r.id IN (SELECT t.id FROM " + institution + "_eaglei_changes_log_triple t WHERE t.operation = 'DELETED'"
							+ " AND t.date_log_generated = (SELECT g.today FROM " + institution + "_eaglei_global_vars g WHERE g.id = 1))"
							+ " AND r.type = 'EAGLEID';"
							+ " DECLARE cur2 CURSOR FOR"
							+ " SELECT DISTINCT r.subject FROM " + institution + "_eaglei_resources r"
							+ " WHERE r.id IN (SELECT t.id FROM " + institution + "_eaglei_changes_log_triple t WHERE t.operation = 'ADDED'"
							+ " AND t.date_log_generated = (SELECT g.today FROM " + institution + "_eaglei_global_vars g WHERE g.id = 1))"
							+ " AND r.type = 'EAGLEID';"
							
							+ " OPEN cur;"
							+ " BEGIN"
							+ " DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;"
							+ " read_loop: LOOP"
							+ " FETCH cur INTO eagle_id;"
							+ " IF done THEN"
							+ " LEAVE read_loop;"
							+ " END IF;"
							+ " IF ("
							+ " (SELECT COUNT(t.id)"
							+ " FROM " + institution + "_eaglei_validtime t"
							+ " WHERE t.end_version = (SELECT g.today FROM " + institution + "_eaglei_global_vars g WHERE g.id = 1)"
							+ " AND t.id IN ( SELECT r.id FROM " + institution + "_eaglei_resources r WHERE r.subject = eagle_id)) = 0"
							+ " ) THEN"
							+ " INSERT INTO " + institution + "_eaglei_changes_log_resource VALUES ((SELECT g.today FROM " + institution + "_eaglei_global_vars g WHERE g.id = 1), eagle_id, 'DELETED');"
							+ " END IF;"
							+ " END LOOP;"
							+ " END;"
							+ " CLOSE cur;"
							
							+ " OPEN cur2;"
							+ " BEGIN"
							+ " DECLARE CONTINUE HANDLER FOR NOT FOUND SET done2 = 1;"
							
							+ " read_loop: LOOP"
							+ " FETCH cur2 INTO eagle_id;"
							+ " IF done2 THEN"
							+ " LEAVE read_loop;"
							+ " END IF;"
							+ " IF ("
							+ " (SELECT COUNT(t.id)"
							+ " FROM " + institution + "_eaglei_validtime t"
							+ " WHERE t.start_version < (SELECT g.today FROM " + institution + "_eaglei_global_vars g WHERE g.id = 1)"
							+ " AND t.end_version = (SELECT g.today FROM " + institution + "_eaglei_global_vars g WHERE g.id = 1)"
							+ " AND t.id IN ( SELECT r.id FROM " + institution + "_eaglei_resources r WHERE r.subject = eagle_id)) = 0"
							+ " ) THEN "
							+ " INSERT INTO " + institution + "_eaglei_changes_log_resource VALUES ((SELECT g.today FROM " + institution + "_eaglei_global_vars g WHERE g.id = 1), eagle_id, 'ADDED');"
							+ " END IF;"
							+ " END LOOP;"
							+ " END;"
							+ " CLOSE cur2;"
							
							+ " DELETE FROM " + institution + "_temp_eaglei_resources;"
							+ " BEGIN"
							+ " UPDATE " + institution + "_eaglei_global_vars g"
							+ " SET g.last_version = g.today"
							+ " WHERE g.id = 1;"
							+ " END;"
							+ " END;"
							);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	           
		  
		
	}
	
	
	
	
}
