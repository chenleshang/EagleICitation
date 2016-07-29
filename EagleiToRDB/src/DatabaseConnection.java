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
			connection = DriverManager.getConnection("jdbc:mysql://fling.seas.upenn.edu:3306/" + databaseName, "alawini", "rdf_d8_pa33");
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
	
	
}
