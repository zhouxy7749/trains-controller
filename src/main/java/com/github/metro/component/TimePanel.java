package com.github.metro.component;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimePanel extends JPanel {
	private static int ONE_SECOND = 1000;
	private JLabel displayArea;
	private String DEFAULT_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

	public TimePanel() {
		JLabel timeLabel = new JLabel("当前时间: ");
		displayArea = new JLabel();

		configTimeArea();
		this.add(timeLabel);
		this.add(displayArea);
		this.setVisible(true);
	}

	private void configTimeArea() {
		Timer tmr = new Timer();
		tmr.scheduleAtFixedRate(new JLabelTimerTask(), new Date(), ONE_SECOND);
	}

	protected class JLabelTimerTask extends TimerTask {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				DEFAULT_TIME_FORMAT);

		@Override
		public void run() {
			String time = dateFormatter.format(Calendar.getInstance().getTime());
			displayArea.setText(time);
		}
	}
}
