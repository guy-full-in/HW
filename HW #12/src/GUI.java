import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class GUI extends JFrame {

	
	int timeCycle;
	int workTime;
	int relaxTime;
	Pomodoro pomodoro;
	
	private JPanel contentPane;
	private JLabel lblCycleLength= new JLabel("Введите длину цикла:");
	private JLabel lblInf = new JLabel("");
	private JLabel lblLogo = new JLabel("PomodorO");
	private JButton btnRelax = new JButton("Отдых");
	private JButton btnWork = new JButton("Работа");
	private JSlider timeRatio = new JSlider();
	private JLabel lblRatio = new JLabel("Распределение времени работы и отдыха:");
	private JCheckBox soundCheck = new JCheckBox("Звук");
	private JTextField hours;
	private JTextField mins;
	private JLabel lblHour = new JLabel("ч");
	private JLabel lblMin = new JLabel("мин");
	private JLabel timer = new JLabel("00:00:00");
	private JProgressBar progress = new JProgressBar();
	private JLabel workPer;
	private JLabel label = new JLabel("Работа:");
	private JLabel label_1 = new JLabel("Отдых:");
	private JLabel relaxPer;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public GUI() throws UnsupportedLookAndFeelException {
		
		super("PomodorO");
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblCycleLength.setBounds(77, 88, 134, 14);
		contentPane.add(lblCycleLength);
		
		
		lblRatio.setHorizontalAlignment(SwingConstants.CENTER);
		lblRatio.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRatio.setBounds(0, 113, 404, 20);
		contentPane.add(lblRatio);
		
		
		timeRatio.setValue(80);
		timeRatio.setBounds(52, 133, 309, 37);
		contentPane.add(timeRatio);
		
		
		btnWork.setBounds(89, 316, 134, 23);
		contentPane.add(btnWork);
		
		
		btnRelax.setBounds(224, 316, 98, 23);
		btnRelax.setEnabled(false);
		contentPane.add(btnRelax);
		
		
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblLogo.setBounds(0, 24, 404, 37);
		contentPane.add(lblLogo);
		
		
		lblInf.setHorizontalAlignment(SwingConstants.CENTER);
		lblInf.setBounds(0, 195, 404, 20);
		contentPane.add(lblInf);
		soundCheck.setSelected(true);
		
		
		soundCheck.setBounds(348, 7, 63, 23);
		contentPane.add(soundCheck);
		
		hours = new JTextField();
		hours.setHorizontalAlignment(SwingConstants.RIGHT);
		hours.setText("0");
		hours.setBounds(208, 85, 35, 20);
		contentPane.add(hours);
		hours.setColumns(10);
		
		mins = new JTextField();
		mins.setText("30");
		mins.setHorizontalAlignment(SwingConstants.RIGHT);
		mins.setBounds(260, 85, 35, 20);
		contentPane.add(mins);
		mins.setColumns(10);
		
		
		lblMin.setBounds(300, 88, 46, 14);
		contentPane.add(lblMin);
		
		lblHour.setBounds(248, 88, 18, 14);
		contentPane.add(lblHour);
		
		timer.setFont(new Font("Tahoma", Font.PLAIN, 35));
		timer.setBounds(138, 217, 138, 37);
		contentPane.add(timer);
		
		progress.setForeground(Color.GREEN);
		progress.setBounds(77, 271, 257, 20);
		contentPane.add(progress);
		
		workPer = new JLabel(String.valueOf(timeRatio.getValue())+"%");
		workPer.setBounds(115, 167, 26, 17);
		contentPane.add(workPer);
		
		label.setBounds(69, 167, 57, 17);
		contentPane.add(label);
		
		label_1.setBounds(275, 167, 46, 17);
		contentPane.add(label_1);
		
		
		relaxPer = new JLabel(String.valueOf(100 - timeRatio.getValue())+"%");
		relaxPer.setBounds(326, 167, 35, 17);
		contentPane.add(relaxPer);
		
        timeRatio.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                workPer.setText(String.valueOf(timeRatio.getValue())+"%");
                relaxPer.setText(String.valueOf(100 - timeRatio.getValue())+"%");
            }
        });
		
		btnWork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInf.setText("");
				timer.setText("00:00:00");
				String currentHours = hours.getText();
				String currentMins = mins.getText();
				if(!currentHours.equals("")){
					timeCycle = Integer.valueOf(currentHours)* 60 * 60 * 1000;
					
					if(!currentMins.equals("")){
						timeCycle = timeCycle + Integer.valueOf(currentMins) * 60 * 1000;
					}

					workTime = timeRatio.getValue() * timeCycle / 100;
					relaxTime = timeCycle - workTime;
					
					boolean sound = soundCheck.isSelected();
					pomodoro = new Pomodoro("work",workTime,sound,timer,progress,btnWork,btnRelax, lblInf);
					pomodoro.start();
					
				}else{
					if(currentMins.equals("")){
						lblInf.setText("Введите длину цикла.");
					}else{
						timeCycle = Integer.valueOf(currentMins) * 60 * 1000;
						
						workTime = timeRatio.getValue() * timeCycle / 100;
						relaxTime = timeCycle - workTime;
						
						boolean sound = soundCheck.isSelected();
						pomodoro = new Pomodoro("work",workTime, sound, timer, progress, btnWork, btnRelax, lblInf);
						pomodoro.start();
					}
				}
			}
		});
		
		
		btnRelax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInf.setText("");
				boolean sound = soundCheck.isSelected();
				pomodoro = new Pomodoro("relax",relaxTime, sound, timer, progress, btnRelax, btnWork, lblInf);
				pomodoro.start();
			}
		});
	}
}
