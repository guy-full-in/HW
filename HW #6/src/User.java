import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

class User implements Serializable{
	String login, password;
	Date lastlogintime;
	
	User(String login, String password){
		this.login = login;
		this.password = password;
		this.lastlogintime = new Date();
	}

	public String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm dd.MM.yyyy");
        return dateFormat.format(lastlogintime);
	}
}