import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Main {
	public static void main(String[] args) {
		new CalcFrame();
	}
}

class CalcFrame extends JFrame {
    JTextArea display= new JTextArea(14, 20); // Текстовое поле (строк, столбцов)
    JPanel buttonPanel = new JPanel(new GridLayout(1,1)); // Панель с кнопками

    JTextArea displayPath= new JTextArea(1, 20); // Текстовое поле (строк, столбцов)
    JPanel pathPanel = new JPanel(new GridLayout(1,1)); // Панель с кнопками
    
    JButton buttonSave = new JButton("Save");
    
    String path="";
    
    JButton buttonOpen = new JButton("Open");

    CalcFrame() {
        super("Текстовый редактор");           // Заголовок окна
        setBounds(300, 300, 300, 300); // Размеры окна

        /*
         * Обработчики событий нажатия на кнопки
          */
 
        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try{
            		path= displayPath.getText();
            		File file= new File(path);
            		file.delete();
            		RandomAccessFile f = new RandomAccessFile(file, "rwd");
            		f.writeBytes(display.getText());
            		f.close();
                }catch (FileNotFoundException er){
                	System.out.println("FileNotFoundException");
                }catch (IOException er){
                    System.out.println("Some I/O error");
                }
            }
        });
        
        buttonOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	path = displayPath.getText();
            	try{
            		path= displayPath.getText();
            		File file= new File(path);
            		RandomAccessFile f = new RandomAccessFile(file, "rwd");
                    String text="";
                    String line="1";
                    while((line = f.readLine()) != null){
                        text+=line+"\n";
                    }
            		display.setText(text);
            		f.close();
                }catch (FileNotFoundException er){
                	System.out.println("FileNotFoundException");
                }catch (IOException er){
                    System.out.println("Some I/O error");
                }
            }
        });
         /*
         * / Обработчики событий нажатия на кнопки
          */

        setLayout(new BorderLayout());

        // Добавление элементов на форму
        add(display, BorderLayout.NORTH);
        add(buttonPanel,BorderLayout.CENTER);
        add(buttonSave,BorderLayout.SOUTH);

        add(displayPath,BorderLayout.SOUTH);
        add(pathPanel,BorderLayout.EAST);
        add(buttonOpen,BorderLayout.WEST);
        
        // Добавление кнопок на панель
        buttonPanel.add(buttonSave);
        pathPanel.add(buttonOpen);


        setVisible(true);
    }
}