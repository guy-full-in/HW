import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		System.out.print("Hello! Enter your login: ");
		
		boolean r = false;
		while(!r){
			String login = sc.nextLine();
			if(validLogin(login)){
				r = true;
				User user = (User) openUser(login);
				if(user == null){
					System.out.print("Please, enter password for your account: ");
					
					boolean f = false;
					while (!f){
						String password = sc.nextLine();
						if(validPass(password)){
							f = true;
							user = new User(login, password);
							saveUser(user);
							System.out.print("Sign up is done. Your last login tames: ");
							user.getDate();
						}else{
							System.out.println("Not valid password. Please, retry: ");
						}
					}
				}else{
					System.out.println("Enter password for your account: ");
					
					boolean f = false;
					while (!f){
						String password = sc.nextLine();
						if(validPass(password)){
							if (user.password.equals(password)){
								f = true;
								System.out.print("Login is done. Your last login time : ");
								user.getDate();
								user.lastlogintime = new Date();
								saveUser(user);
							}else{
								System.out.println("Wrong password. Please, retry: ");
							}
						}else{
							System.out.println("Not valid password. Please, retry: ");
						}
					}
				}
				
			}else{
				System.out.println("Not valid login. Please, retry: ");
			}
		}
	}
	
	
	private static boolean validPass(String password) {
		Pattern pattern = Pattern.compile(".{3,40}");
		Matcher matcher = pattern.matcher(password);
		
		if(matcher.matches()){
			return true;
		}
		return false;
	}

	private static boolean validLogin(String login) {
		Pattern pattern = Pattern.compile("[a-zA-Z0-9]{2,10}");
		Matcher matcher = pattern.matcher(login);
		
		if(matcher.matches()){
			return true;
		}
		return false;
	}
	
	public static void saveUser(User new_user){
        try{
            FileOutputStream file = new FileOutputStream("src/users/" + new_user.login);
            ObjectOutputStream output = new ObjectOutputStream(file);

            output.writeObject((Object)new_user);
            output.flush();
            output.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
	
	public static Object openUser(String login){
        try{
            FileInputStream file = new FileInputStream("src/users/" + login);
            ObjectInputStream object = new ObjectInputStream(file);

            return object.readObject();
        }catch (IOException e){
            System.out.println("You don't sign up.");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}

class User implements Serializable{
	String login, password;
	Date lastlogintime;
	
	User(String login, String password){
		this.login = login;
		this.password = password;
		this.lastlogintime = new Date();
	}

	public void getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm dd.MM.yyyy");
        System.out.println(dateFormat.format(lastlogintime));
	}
}