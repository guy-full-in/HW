import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;


public class Pomodoro extends Thread{
	String type;
	int time;
	boolean sound;
	JLabel timer;
	JProgressBar progress;
	JButton btnFirst; //don't unlock after end
	JButton btnSecond;
	JLabel inf;
	
	public Pomodoro(String type, int time, boolean sound, JLabel timer, JProgressBar progress, JButton btnFirst, JButton btnSecond, JLabel inf){
		this.type = type;
		this.time = time;
		this.sound = sound;
		this.timer = timer;
		this.progress = progress;
		this.btnFirst = btnFirst;
		this.btnSecond = btnSecond;
		this.inf = inf;
	}
	
	public void run() {
		try{
			btnFirst.setEnabled(false);
			btnSecond.setEnabled(false);
			long startTime = System.currentTimeMillis();
			long longCurrent = System.currentTimeMillis() - startTime;
			progress.setValue(0);
			while(longCurrent < time){
				longCurrent = System.currentTimeMillis() - startTime;
				timer.setText(formatLongDate(longCurrent));
				int value =(int) ((double)longCurrent / (double)time * 100);
				progress.setValue(value);
			}
			
			btnSecond.setEnabled(true);
			
			
			if(type == "work"){
				inf.setText("Пришло время прекратить работать, займитесь отдыхом");
			}else{
				inf.setText("Пришло время прекратить отдыхать, займитесь работой");
			}
			
			
			if(sound){
				Alarm.play();
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public String formatLongDate(long longCurrent){
		Time time = new Time(longCurrent);
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("GTM"));
		String stringTime = format.format(time);
		return stringTime; 
	}
}
