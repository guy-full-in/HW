import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Server extends Thread{
	
	int matrix [][];
	JLabel label;
	public volatile boolean progress;
	JButton [][] btn;
	JButton btnAction;
	ServerSocket serverSocket;
	JLabel lblAsk;
	

    public Server(int[][] matrix, JLabel label, JButton [][] btn, ServerSocket serverSocket, JButton btnAction, JLabel lblAsk) {
		this.matrix = matrix;
		this.label = label;
		this.progress = false;
		this.btn = btn;
		this.serverSocket = serverSocket;
		this.btnAction = btnAction;
		this.lblAsk = lblAsk;
	}


	public void run(){
		lblAsk.setText("");
		try{
			Socket socket = serverSocket.accept();
			for(int i =0;i<3;i++){
			   	for(int j = 0;j<3;j++){
			   		btn[i][j].setText("");
			   		btn[i][j].setEnabled(true);
			  	}
			}
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
		    label.setText("Сделайте ход");
		    
		    int result = 0;
		    while(result == 0){
		    	
		    	
		    	progress=false;
		    	while(!progress){}
		    	
		    	boolean dh = true; 
				for(int i =0;i<3 && dh;i++){
				   	for(int j = 0;j<3 && dh;j++){
				   		if(matrix[i][j]==0){
				   			dh = false;
				   		}
				  	}
				}
				
				if(dh){
					result = 2;
				}else{
						
			    	
			    	label.setText("Ожидайте хода соперника");
	
			    	if(matrix[0][0]>0 && matrix[1][1]>0 && matrix[2][2]>0){
			    		result = 1;
			    		winX();
			    	}
			    	
			    	for(int i=0;i<3;i++){
			    		
			    		if(matrix[i][0]>0 && matrix[i][1]>0 && matrix[i][2]>0){
					    	result = 1;
							winX();
			    		}
			    		
			    		if(matrix[0][i]>0 && matrix[1][i]>0 && matrix[2][i]>0){
					    	result = 1;
					    	winX();
			    		}
			    		
			    	}
			    	
			    	if(matrix[0][2]>0 && matrix[1][1]>0 && matrix[2][0]>0){
			    		result = 1;
			    		winX();
			    	}
				}
		    	
		    	dos.writeByte(result);
			    for(int i =0;i<3;i++){
			    	for(int j = 0;j<3;j++){
			    		dos.writeByte(matrix[i][j]);
			    	}
			    }
			    
			    if(result==0){
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
			    }
			    
			    if (result==-1){
			    	winO();
			    }
			    
			    if(result == 2){
			    	label.setText("Ничья");
			    }
		    }

	        dos.close();
	        dis.close();
	        socketOut.close();
		}catch (Exception ex){
			ex.printStackTrace();
			label.setText("Ошибка...");
		}
    }
	
	private void winX(){
		for(int i =0;i<3;i++){
		   	for(int j = 0;j<3;j++){
		   		btn[i][j].setEnabled(false);
		  	}
		}
		label.setText("Вы выйграли");
		lblAsk.setText("Хотите сыграть еще?");
		btnAction.setEnabled(true);
	}
	
	private void winO(){
		for(int i =0;i<3;i++){
		   	for(int j = 0;j<3;j++){
		   		btn[i][j].setEnabled(false);
		  	}
		}
		label.setText("Вы проиграли");
		lblAsk.setText("Хотите сыграть еще?");
		btnAction.setEnabled(true);
	}
}
