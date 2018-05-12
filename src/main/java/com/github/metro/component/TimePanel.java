package com.github.metro.component;

import javax.swing.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.github.metro.util.LogUtils.currentTime;

public class TimePanel extends JPanel {
	private static int ONE_SECOND = 1000;
	private JLabel displayArea;

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

		@Override
		public void run() {
			displayArea.setText(currentTime());
		}
	}
}
