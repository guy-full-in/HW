import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class FormFrame extends JFrame {

	private JPanel contentPane = new JPanel();;
	private JTextField loginField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JButton btnLogin = new JButton("Login");
	private JLabel lblInf = new JLabel(" ");
	
	
	public FormFrame() {
		
		super("Login");
		
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String login = loginField.getText();
				if (validLogin(login)) {
					User user = (User) openUser(login);
					if (user == null) {
						String password = passwordField.getText();
						if (validPass(password)) {
							user = new User(login, password);
							saveUser(user);
							String inf="Sign up is done. Your last login tames: " + user.getDate();
							lblInf.setText(inf);
						} else {
							lblInf.setText("Not valid password. Please, retry.");
						}
					} else {
						String password = passwordField.getText();
						if (validPass(password)) {
							if (user.password.equals(password)) {
								String inf="Login is done. Your last login time : " + user.getDate();
								lblInf.setText(inf);
								user.lastlogintime = new Date();
								saveUser(user);
							} else {
								lblInf.setText("Wrong password. Please, retry.");
							}
						} else {
							lblInf.setText("Not valid password. Please, retry.");
						}
					}

				} else {
					lblInf.setText("Not valid login. Please, retry.");
				}
			}
		});
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblLogin.setBounds(179, 24, 78, 32);
		contentPane.add(lblLogin);
		
		
		loginField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		loginField.setBounds(127, 56, 177, 25);
		contentPane.add(loginField);
		loginField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password: ");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblPassword.setBounds(167, 90, 110, 32);
		contentPane.add(lblPassword);
		
		
		passwordField.setBounds(126, 123, 177, 23);
		contentPane.add(passwordField);
		
		btnLogin.setBounds(170, 161, 89, 23);
		contentPane.add(btnLogin);
		
		lblInf.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInf.setBounds(45, 227, 425, 25);
		contentPane.add(lblInf);
		
        setVisible(true);
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
            file.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
	
	public static Object openUser(String login){
        try{
            FileInputStream file = new FileInputStream("src/users/" + login);
            ObjectInputStream object = new ObjectInputStream(file);
            Object user=object.readObject();
            object.close();
            file.close();
            return user;
        }catch (IOException e){
        	System.out.println(e.getMessage());
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
