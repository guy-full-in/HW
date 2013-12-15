import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Properties;

import javax.swing.JFrame;


public class DB {

    private static Connection connection;
	
	public static void connect() {
        try {
            Properties props = new Properties();
            FileInputStream in
                    = new FileInputStream("properties/database.properties");
            props.load(in);
            in.close();
            String drivers = props.getProperty("jdbc.drivers");
            if (drivers != null){
                System.setProperty("jdbc.drivers", drivers);
            }
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");

            connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            
			String sql = "CREATE TABLE IF NOT EXISTS users "
					+ "(login VARCHAR(255) not null, "
					+ " password VARCHAR(32) not null, "
					+ " lastlogintime TIMESTAMP(6) not null,"
					+ " gender VARCHAR(12) not null, " 
					+ " PRIMARY KEY ( login ))";

            statement.execute(sql);
            
        }catch (Exception e){
        	e.printStackTrace();
        }
	}
	
	public static void addUser(User user){
		try{
			String sql = "INSERT INTO users(login, password, lastlogintime, gender) values(?, ?, ?, ?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, user.login);
			ps.setString(2, user.password);
			ps.setTimestamp(3, new Timestamp(user.lastlogintime.getTime()));
			ps.setString(4, user.gender.toString());
			ps.execute();
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public static User getUser(String name){
		try{
			String sql = "SELECT * FROM users WHERE login = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()){
				return null;
			}else{
				User user = new User(rs.getString("login"),rs.getString("password"), Gender.valueOf(rs.getString("gender")), rs.getTimestamp("lastlogintime"));
				return user;
			}
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static void saveUser(User user){
		try{
			String sql = "UPDATE users SET lastlogintime = ? WHERE login = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, new Timestamp(user.lastlogintime.getTime()));
			ps.setString(2, user.login);
			ps.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void close(){
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
