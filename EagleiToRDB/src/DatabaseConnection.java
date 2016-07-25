import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DatabaseConnection {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	
	public DatabaseConnection() {

	}

	public void connect(String databaseName){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, "root", "password");
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
	
	public void insert(String table, ArrayList<String> column){
		String date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
		
		if (column.size() > 0){
			String query = ("insert into " + table +  " VALUES(" + "'" + column.get(0) + "'" + "," + "'" + column.get(1) + "'" + "," + "'" + column.get(2) + "'" + "," + "'" + date + "'" + ")");
			
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
