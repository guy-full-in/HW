import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldFrom = new JTextField();
	private JTextField textFieldTo = new JTextField();
	JComboBox comboBoxFrom;
	JComboBox comboBoxTo;
	JButton Translete = new JButton("TRANSLETE");
	LinkedList<LinkedList<String>> langs;


	/**
	 * Create the frame.
	 */
	public GUI() {

		langs = API.getLangList();
		
		Iterator<LinkedList<String>> it = langs.iterator();
		int numLang = langs.size();
		String [] fromLangs = new String[numLang];
		String [] langsTo = {""};
		for(int i=0;i<numLang;i++){
			LinkedList<String> list = it.next();
			Iterator<String> its = list.iterator();
			fromLangs[i]=its.next();
			int numTo = list.size();
			langsTo = new String [numTo-1];
			if(i==0){
				for(int j=1;j<numTo;j++){
					langsTo[j-1]=its.next();
					comboBoxTo = new JComboBox(langsTo);
				}
			}
			
		}
		comboBoxFrom = new JComboBox(fromLangs);
	
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		

		comboBoxFrom.setBounds(47, 45, 122, 20);
		contentPane.add(comboBoxFrom);
		
		
		comboBoxTo.setBounds(246, 45, 122, 20);
		contentPane.add(comboBoxTo);
		
		textFieldFrom.setBounds(26, 90, 170, 52);
		contentPane.add(textFieldFrom);
		textFieldFrom.setColumns(10);
		
		
		textFieldTo.setBounds(223, 90, 170, 52);
		contentPane.add(textFieldTo);
		textFieldTo.setColumns(10);
		
		Translete.setBounds(145, 171, 121, 23);
		contentPane.add(Translete);
		
		
		comboBoxFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String value = (String)comboBoxFrom.getSelectedItem();
				Iterator<LinkedList<String>> it = langs.iterator();
				String [] langsTo = {""};
				while(it.hasNext()){
					LinkedList<String> list = it.next();
					Iterator<String> its = list.iterator();
					if(its.next().equals(value)){
						int numTo = list.size();
						langsTo = new String [numTo-1];
						for(int j=1;j<numTo;j++){
							langsTo[j-1]=its.next();
						}
						DefaultComboBoxModel<String> model= new DefaultComboBoxModel<String>(langsTo); 
						comboBoxTo.setModel(model);
					}	
				}
			}
		});

			Translete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String text = textFieldFrom.getText();
					String langFrom = (String)comboBoxFrom.getSelectedItem();
					String langTo = (String)comboBoxTo.getSelectedItem();
					String trans = API.getTranslete(text,langFrom,langTo);
					textFieldTo.setText(trans);
				}
			});
	}
	
    
}
