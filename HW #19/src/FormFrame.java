import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
        private JComboBox<Gender> gender = new JComboBox<Gender>(new Gender[]{null,Gender.WOMAN,Gender.MAN});
        
        
    	public static void main(String[] args) {
    		EventQueue.invokeLater(new Runnable() {
    			public void run() {
    				try {
    					FormFrame frame = new FormFrame();
    					frame.setVisible(true);
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    			}
    		});
    	}
        
        public FormFrame() {
                
                super("Login");
                
                
                btnLogin.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                        	
                        		DB.connect();
                        		
                                String login = loginField.getText();
                                if (validLogin(login)) {
                                        User user = DB.getUser(login);
                                        if (user == null) {
                                                String password = passwordField.getText();
                                                if (validPass(password)) {
                                                        if(gender.getSelectedItem()!=null){
                                                                user = new User(login, getMD5(password), (Gender)gender.getSelectedItem(), new Date());
                                                                DB.addUser(user);
                                                                String inf="<html>Sign up is done.<br>Your last login tames: " + user.getDate() +"<br>Gender: "+ user.getGender()+"</html>";
                                                                lblInf.setText(inf);
                                                        }else{
                                                                lblInf.setText("You don't select gender");
                                                        }
                                                } else {
                                                        lblInf.setText("Not valid password. Please, retry.");
                                                }
                                        } else {
                                                String password = passwordField.getText();
                                                if (validPass(password)) {
                                                        if (user.password.equals(getMD5(password))) {
                                                                if(gender.getSelectedItem()!=null){
                                                                        if(gender.getSelectedItem()==user.gender){
                                                                                String inf="<html>Login is done.<br>Your last login tames: " + user.getDate() +"<br>Gender: "+ user.getGender()+"</html>";
                                                                                lblInf.setText(inf);
                                                                                user.lastlogintime = new Date();
                                                                                DB.saveUser(user);
                                                                        }else{
                                                                                lblInf.setText("Wrong gender. Please, retry.");
                                                                        }
                                                                }else{
                                                                        lblInf.setText("You don't select gender");
                                                                }
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
                                
                                DB.close();
                        }
                });
                
                
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setBounds(100, 100, 450, 350);
                
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
                
                btnLogin.setBounds(168, 193, 89, 23);
                contentPane.add(btnLogin);
                
                lblInf.setFont(new Font("Tahoma", Font.PLAIN, 15));
                lblInf.setBounds(45, 227, 354, 73);
                contentPane.add(lblInf);
                
                gender.setBounds(167, 162, 90, 20);
                contentPane.add(gender);
                
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
        
    public static String getMD5(String inputString) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(inputString.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}