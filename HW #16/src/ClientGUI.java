import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class ClientGUI extends JFrame {

	private JPanel contentPane;
	private JLabel label = new JLabel("");
	private JButton [][] btn = new JButton[3][3];
	int [][] matrix = new int[3][3];
	Client client;
	JButton btnAction = new JButton("Да");
	JLabel lblAsk = new JLabel("");


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI frame = new ClientGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public ClientGUI() {
		super("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 335, 335);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btn[0][0] = new JButton("");
		btn[0][0].setBounds(103, 73, 40, 40);
		btn[0][0].setMargin(new Insets(5,5,5,5));
		contentPane.add(btn[0][0]);
		
		btn[0][1] = new JButton("");
		btn[0][1].setBounds(142, 73, 40, 40);
		btn[0][1].setMargin(new Insets(5,5,5,5));
		contentPane.add(btn[0][1]);
		
		btn[0][2] = new JButton("");
		btn[0][2].setBounds(181, 73, 40, 40);
		btn[0][2].setMargin(new Insets(5,5,5,5));
		contentPane.add(btn[0][2]);
		
		btn[1][0] = new JButton("");
		btn[1][0].setBounds(103, 113, 40, 40);
		btn[1][0].setMargin(new Insets(5,5,5,5));
		contentPane.add(btn[1][0]);
		
		btn[1][1] = new JButton("");
		btn[1][1].setBounds(142, 113, 40, 40);
		btn[1][1].setMargin(new Insets(5,5,5,5));
		contentPane.add(btn[1][1]);
		
		btn[1][2] = new JButton("");
		btn[1][2].setBounds(181, 113, 40, 40);
		btn[1][2].setMargin(new Insets(5,5,5,5));
		contentPane.add(btn[1][2]);
		
		btn[2][0] = new JButton("");
		btn[2][0].setBounds(103, 153, 40, 40);
		btn[2][0].setMargin(new Insets(5,5,5,5));
		contentPane.add(btn[2][0]);
		
		btn[2][1] = new JButton("");
		btn[2][1].setBounds(142, 153, 40, 40);
		btn[2][1].setMargin(new Insets(5,5,5,5));
		contentPane.add(btn[2][1]);
		
		btn[2][2] = new JButton("");
		btn[2][2].setBounds(181, 153, 40, 40);
		btn[2][2].setMargin(new Insets(5,5,5,5));
		contentPane.add(btn[2][2]);
		
		btnAction.setBounds(120, 215, 89, 23);
		btnAction.setEnabled(false);
		contentPane.add(btnAction);
		
		
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 250, 320, 14);
		contentPane.add(label);
		
		lblAsk.setHorizontalAlignment(SwingConstants.CENTER);
		lblAsk.setBounds(0, 200, 320, 14);
		contentPane.add(lblAsk);
		
		for(int i =0;i<3;i++){
		   	for(int j = 0;j<3;j++){
		   		btn[i][j].setEnabled(false);
		  	}
		}
		
		client = new Client(matrix, label, btn, btnAction, lblAsk);
		client.start();

		btnAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					btnAction.setEnabled(false);
					client.join();
					label.setText("Ожидайте ответа второго игрока");	
					client = new Client(matrix,label,btn,btnAction,lblAsk);
					client.start();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btn[0][0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!client.progress){
					btn[0][0].setEnabled(false);
					btn[0][0].setText("o");
					matrix[0][0]=-1;
					client.progress = true;
				}
			}
		});
		
		btn[0][1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!client.progress){
					btn[0][1].setEnabled(false);
					btn[0][1].setText("o");
					matrix[0][1]=-1;
					client.progress = true;
				}
			}
		});
		
		btn[0][2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!client.progress){
					btn[0][2].setEnabled(false);
					btn[0][2].setText("o");
					matrix[0][2]=-1;
					client.progress = true;
				}
			}
		});
		
		
		btn[1][0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!client.progress){
					btn[1][0].setEnabled(false);
					btn[1][0].setText("o");
					matrix[1][0]=-1;
					client.progress = true;
				}
			}
		});
		
		
		btn[1][1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!client.progress){
					btn[1][1].setEnabled(false);
					btn[1][1].setText("o");
					matrix[1][1]=-1;
					client.progress = true;
				}
			}
		});
		
		
		btn[1][2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!client.progress){
					btn[1][2].setEnabled(false);
					btn[1][2].setText("o");
					matrix[1][2]=-1;
					client.progress = true;
				}
			}
		});
		
		
		btn[2][0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!client.progress){
					btn[2][0].setEnabled(false);
					btn[2][0].setText("o");
					matrix[2][0]=-1;
					client.progress = true;
				}
			}
		});
		
		
		btn[2][1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!client.progress){
					btn[2][1].setEnabled(false);
					btn[2][1].setText("");
					matrix[2][1]=-1;
					client.progress = true;
				}
			}
		});
		
		btn[2][2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!client.progress){
					btn[2][2].setEnabled(false);
					btn[2][2].setText("o");
					matrix[2][2]=-1;
					client.progress = true;
				}
			}
		});
	}
}
