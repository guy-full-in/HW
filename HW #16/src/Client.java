
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Client extends Thread{
	
	int matrix [][];
	JLabel label;
	public volatile boolean progress;
	JButton [][] btn;
	JButton btnAction;
	JLabel lblAsk;

    public Client(int[][] matrix, JLabel label, JButton[][] btn, JButton btnAction, JLabel lblAsk) {
		super();
		this.matrix = matrix;
		this.label = label;
		this.progress = false;
		this.btn = btn;
		this.btnAction = btnAction;
		this.lblAsk = lblAsk;
	}



	public void run(){
		lblAsk.setText("");
		boolean serverStart = false;
		while(!serverStart){
			try{
				Socket socket = new Socket("localhost", 9981);
				for(int i =0;i<3;i++){
				   	for(int j = 0;j<3;j++){
				   		btn[i][j].setText("");
				   		btn[i][j].setEnabled(true);
				  	}
				}	
		        serverStart = true;
			    OutputStream socketOut = socket.getOutputStream();
		        DataOutputStream dos = new DataOutputStream (socketOut);
		        InputStream socketIn = socket.getInputStream();
		        DataInputStream dis = new DataInputStream(socketIn);
		        
			    for(int i =0;i<3;i++){
			    	for(int j = 0;j<3;j++){
			    		matrix[i][j]=0;
			    	}
			    }
			    label.setEnabled(false);
			    
			    int result = 0;
			    while(result == 0){
			    	
			    	progress = true;
			    	
			    	label.setText("Ожидайте хода соперника");			    
				    result = (int)dis.readByte();
				    for(int i =0;i<3;i++){
				    	for(int j = 0;j<3;j++){
				    		int temp = (int)dis.readByte();
				    		if(temp>0){
				    			btn[i][j].setEnabled(false);
				    			btn[i][j].setText("x");
				    			matrix[i][j] = temp;
				    		}else if(temp<0){
				    			btn[i][j].setEnabled(false);
				    			btn[i][j].setText("o");
				    			matrix[i][j] = temp;
				    		}
				    	}
				    }
				    
				    if (result == 1){
				    	winX();
				    }else if(result == 11){
				    	label.setText("Ничья");
			    	}else{   
					    label.setText("Сделайте ход");	
					    progress = false;
					    
				    	while(!progress){}
				    	
				    	if(matrix[0][0]<0 && matrix[1][1]<0 && matrix[2][2]<0){
				    		result = -1;
				    		winO();
				    	}
				    	
				    	for(int i=0;i<3;i++){
				    		
				    		if(matrix[i][0]<0 && matrix[i][1]<0 && matrix[i][2]<0){
						    	result = -1;
								winO();
				    		}
				    		
				    		if(matrix[0][i]<0 && matrix[1][i]<0 && matrix[2][i]<0){
						    	result = -1;
						    	winO();
				    		}
				    		
				    	}
				    	
				    	if(matrix[0][2]<0 && matrix[1][1]<0 && matrix[2][0]<0){
				    		result = -1;
				    		winO();
				    	}
				    	
				    	progress=false;
				    	
				    	if(result!=0){
					    	label.setText("Ход сделан");	
				    	}
				    	
				    	dos.writeByte(result);
					    for(int i =0;i<3;i++){
					    	for(int j = 0;j<3;j++){
					    		dos.writeByte(matrix[i][j]);
					    	}
					    }
				    }
				    
			    }
	
		        dos.close();
		        dis.close();
		        socketOut.close();
		        socket.close();
			}catch (Exception ex){
				label.setText("Ошибка: сервер не запущен");
			}
		}
    }
	
	private void winX(){
		for(int i =0;i<3;i++){
		   	for(int j = 0;j<3;j++){
		   		btn[i][j].setEnabled(false);
		  	}
		}
		label.setText("Вы проиграли");
		lblAsk.setText("Хотите сыграть еще?");
		btnAction.setEnabled(true);
	}
	
	private void winO(){
		for(int i =0;i<3;i++){
		   	for(int j = 0;j<3;j++){
		   		btn[i][j].setEnabled(false);
		  	}
		}
		label.setText("Вы выйграли");
		lblAsk.setText("Хотите сыграть еще?");
		btnAction.setEnabled(true);
	}
}