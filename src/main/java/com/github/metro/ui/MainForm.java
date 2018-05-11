/*
 * Created by JFormDesigner on Tue May 08 22:41:09 CST 2018
 */

package com.github.metro.ui;

import com.github.metro.Controller.VehicleOnBoardController;
import com.github.metro.component.TimePanel;
import com.github.metro.util.Lights;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import static com.github.metro.util.LogUtils.currentTime;
import static com.github.metro.util.LogUtils.getLogs;

/**
 * @author unknown
 */
public class MainForm extends JPanel {
	public MainForm() {
		initComponents();
		label33.setIcon(Lights.off());
		label34.setIcon(Lights.off());
		label35.setIcon(Lights.off());
		label36.setIcon(Lights.off());
		label37.setIcon(Lights.off());
		label38.setIcon(Lights.off());
		label39.setIcon(Lights.off());
		label40.setIcon(Lights.off());
	}

	private void buttonActionPerformed(ActionEvent e) {
		List<String> logs = getLogs();
		logs.add(currentTime() + e.getActionCommand());
		if (e.getActionCommand() == "发车") {
			logs.add(currentTime() + "列车正常发车，ATP防护");
			VehicleOnBoardController controller = VehicleOnBoardController.getInstance();
			controller.start();
			nextStation.setText(controller.getNextStation());
			finalStation.setText(controller.getFinalStation());
			label33.setIcon(Lights.on());
			label36.setIcon(Lights.on());

		}
		if (e.getActionCommand() == "停车") {
			logs.add(currentTime() + "列车正常停车，ATP防护");
			label33.setIcon(Lights.off());
		}

		if (e.getActionCommand() == "打开左侧车门") {
			logs.add(currentTime() + "车载控制器控制左侧车门打开");
		}

		if (e.getActionCommand() == "打开右侧车门") {
			logs.add(currentTime() + "车载控制器控制右侧车门打开");
		}

		if (e.getActionCommand() == "关闭车门") {
			logs.add(currentTime() + "车载控制器控制车门关闭");
		}

		if (e.getActionCommand() == "打开左侧屏蔽门") {
			logs.add(currentTime() + "车载控制器控制左侧屏蔽门打开");
		}

		if (e.getActionCommand() == "打开右侧屏蔽门") {
			logs.add(currentTime() + "车载控制器控制右侧屏蔽门打开");
		}

		if (e.getActionCommand() == "关闭屏蔽门") {
			logs.add(currentTime() + "车载控制器控制屏蔽门关闭");
		}

		logList.setListData(logs.toArray());
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Qings Yo
		controlPanel = new JPanel();
		button1 = new JButton();
		button2 = new JButton();
		button3 = new JButton();
		button4 = new JButton();
		button5 = new JButton();
		button6 = new JButton();
		button7 = new JButton();
		button8 = new JButton();
		button9 = new JButton();
		button10 = new JButton();
		button11 = new JButton();
		label1 = new JLabel();
		timePanel1 = new TimePanel();
		separator1 = new JSeparator();
		button12 = new JButton();
		panel2 = new JPanel();
		label3 = new JLabel();
		label4 = new JLabel();
		label5 = new JLabel();
		label6 = new JLabel();
		label7 = new JLabel();
		label8 = new JLabel();
		label9 = new JLabel();
		label10 = new JLabel();
		label11 = new JLabel();
		label12 = new JLabel();
		label13 = new JLabel();
		nextStation = new JLabel();
		label15 = new JLabel();
		finalStation = new JLabel();
		label17 = new JLabel();
		label18 = new JLabel();
		label19 = new JLabel();
		panel3 = new JPanel();
		label20 = new JLabel();
		label21 = new JLabel();
		label22 = new JLabel();
		label23 = new JLabel();
		label24 = new JLabel();
		label25 = new JLabel();
		label26 = new JLabel();
		label27 = new JLabel();
		label28 = new JLabel();
		label29 = new JLabel();
		label33 = new JLabel();
		label34 = new JLabel();
		label35 = new JLabel();
		label36 = new JLabel();
		label37 = new JLabel();
		label38 = new JLabel();
		label39 = new JLabel();
		label40 = new JLabel();
		scrollPane1 = new JScrollPane();
		logList = new JList();
		label30 = new JLabel();

		//======== this ========

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(
						new javax.swing.border.EmptyBorder(0, 0, 0, 0),
						"JFormDesigner Evaluation",
						javax.swing.border.TitledBorder.CENTER,
						javax.swing.border.TitledBorder.BOTTOM,
						new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
						java.awt.Color.red), getBorder()));
		addPropertyChangeListener(new java.beans.PropertyChangeListener() {
			public void propertyChange(java.beans.PropertyChangeEvent e) {
				if ("border".equals(e.getPropertyName()))
					throw new RuntimeException();
			}
		});

		//======== controlPanel ========
		{

			//---- button1 ----
			button1.setText("\u53d1\u8f66");
			button1.addActionListener(e -> buttonActionPerformed(e));

			//---- button2 ----
			button2.setText("\u505c\u8f66");
			button2.addActionListener(e -> buttonActionPerformed(e));

			//---- button3 ----
			button3.setText("\u6253\u5f00\u5de6\u4fa7\u8f66\u95e8");
			button3.addActionListener(e -> buttonActionPerformed(e));

			//---- button4 ----
			button4.setText("\u6253\u5f00\u53f3\u4fa7\u8f66\u95e8");
			button4.addActionListener(e -> buttonActionPerformed(e));

			//---- button5 ----
			button5.setText("\u5173\u95ed\u8f66\u95e8");
			button5.addActionListener(e -> buttonActionPerformed(e));

			//---- button6 ----
			button6.setText("\u6253\u5f00\u5de6\u4fa7\u5c4f\u853d\u95e8");
			button6.addActionListener(e -> buttonActionPerformed(e));

			//---- button7 ----
			button7.setText("\u6253\u5f00\u53f3\u4fa7\u5c4f\u853d\u95e8");
			button7.addActionListener(e -> buttonActionPerformed(e));

			//---- button8 ----
			button8.setText("\u5173\u95ed\u5c4f\u853d\u95e8");
			button8.addActionListener(e -> buttonActionPerformed(e));

			//---- button9 ----
			button9.setText("\u8df3\u505c");
			button9.addActionListener(e -> buttonActionPerformed(e));

			//---- button10 ----
			button10.setText("\u6263\u8f66");
			button10.addActionListener(e -> buttonActionPerformed(e));

			//---- button11 ----
			button11.setText("\u64a4\u9500\u6263\u8f66");
			button11.addActionListener(e -> buttonActionPerformed(e));

			//---- label1 ----
			label1.setText("\u8f66\u6b21\u53f7: 00001");

			//---- button12 ----
			button12.setText("\u7d27\u6025\u5236\u52a8");
			button12.addActionListener(e -> buttonActionPerformed(e));

			GroupLayout controlPanelLayout = new GroupLayout(controlPanel);
			controlPanel.setLayout(controlPanelLayout);
			controlPanelLayout.setHorizontalGroup(controlPanelLayout.createParallelGroup()
					.addGroup(controlPanelLayout.createSequentialGroup().addGroup(
							controlPanelLayout.createParallelGroup().addGroup(
									controlPanelLayout.createSequentialGroup()
											.addGap(6, 6, 6).addComponent(button9)
											.addPreferredGap(
													LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(button10).addPreferredGap(
											LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(button11).addPreferredGap(
											LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(button12)).addGroup(
									controlPanelLayout.createSequentialGroup()
											.addGap(6, 6, 6).addComponent(button3)
											.addPreferredGap(
													LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(button4).addPreferredGap(
											LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(button5)).addGroup(
									controlPanelLayout.createSequentialGroup()
											.addContainerGap().addComponent(label1)
											.addPreferredGap(
													LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(timePanel1,
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE)).addGroup(
									controlPanelLayout.createSequentialGroup()
											.addGap(6, 6, 6).addComponent(button1)
											.addPreferredGap(
													LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(separator1,
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(
													LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(button2)).addGroup(
									controlPanelLayout.createSequentialGroup()
											.addGap(6, 6, 6).addComponent(button6)
											.addPreferredGap(
													LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(button7).addPreferredGap(
											LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(button8)))
							.addContainerGap(15, Short.MAX_VALUE)));
			controlPanelLayout.linkSize(SwingConstants.HORIZONTAL,
					new Component[] { button3, button4, button5, button6, button7,
							button8 });
			controlPanelLayout.setVerticalGroup(controlPanelLayout.createParallelGroup()
					.addGroup(controlPanelLayout.createSequentialGroup().addGroup(
							controlPanelLayout
									.createParallelGroup(GroupLayout.Alignment.LEADING,
											false)
									.addComponent(timePanel1, GroupLayout.DEFAULT_SIZE,
											GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(label1, GroupLayout.DEFAULT_SIZE,
											GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(controlPanelLayout.createParallelGroup().addGroup(
									controlPanelLayout.createSequentialGroup()
											.addGap(8, 8, 8).addGroup(controlPanelLayout
											.createParallelGroup(
													GroupLayout.Alignment.BASELINE)
											.addComponent(button1).addComponent(button2)))
									.addGroup(controlPanelLayout.createSequentialGroup()
											.addPreferredGap(
													LayoutStyle.ComponentPlacement.RELATED)
											.addComponent(separator1,
													GroupLayout.PREFERRED_SIZE,
													GroupLayout.DEFAULT_SIZE,
													GroupLayout.PREFERRED_SIZE)))
							.addGap(10, 10, 10).addGroup(controlPanelLayout
									.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(button4).addComponent(button5)
									.addComponent(button3))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(controlPanelLayout
									.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(button6).addComponent(button7)
									.addComponent(button8)).addGap(9, 9, 9).addGroup(
									controlPanelLayout.createParallelGroup(
											GroupLayout.Alignment.BASELINE)
											.addComponent(button10).addComponent(button11)
											.addComponent(button9).addComponent(button12))
							.addContainerGap(15, Short.MAX_VALUE)));
		}

		//======== panel2 ========
		{

			//---- label3 ----
			label3.setText("\u76ee\u6807\u901f\u5ea6");

			//---- label4 ----
			label4.setText("80");

			//---- label5 ----
			label5.setText("\u5343\u7c73/\u65f6");

			//---- label6 ----
			label6.setText("\u505c\u8f66\u8ddd\u79bb");

			//---- label7 ----
			label7.setText("1200");

			//---- label8 ----
			label8.setText("\u7c73");

			//---- label9 ----
			label9.setText("\u505c\u8f66\u8ddd\u79bb");

			//---- label10 ----
			label10.setText("\u4e0b\u7ad9\u8ddd\u79bb");

			//---- label11 ----
			label11.setText("3000");

			//---- label12 ----
			label12.setText("\u7c73");

			//---- label13 ----
			label13.setText("\u4e0b\u4e00\u7ad9");

			//---- nextStation ----
			nextStation.setText("\u4e1c\u76f4\u95e8");

			//---- label15 ----
			label15.setText("\u7ec8\u70b9\u7ad9");

			//---- finalStation ----
			finalStation.setText("\u5929\u5b89\u95e8");

			//---- label17 ----
			label17.setText("\u5217\u8f66\u901f\u5ea6");

			//---- label18 ----
			label18.setText("67");

			//---- label19 ----
			label19.setText("\u5343\u7c73/\u65f6");

			GroupLayout panel2Layout = new GroupLayout(panel2);
			panel2.setLayout(panel2Layout);
			panel2Layout.setHorizontalGroup(panel2Layout.createParallelGroup().addGroup(
					panel2Layout.createParallelGroup()
							.addGroup(GroupLayout.Alignment.TRAILING,
									panel2Layout.createSequentialGroup()
											.addContainerGap(210, Short.MAX_VALUE)
											.addComponent(label9)
											.addContainerGap(46, Short.MAX_VALUE)))
					.addGroup(panel2Layout.createSequentialGroup().addGap(24, 24, 24)
							.addGroup(panel2Layout.createParallelGroup()
									.addComponent(label3).addComponent(label6)
									.addComponent(label10).addComponent(label13)
									.addComponent(label15).addComponent(label17))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 97,
									Short.MAX_VALUE).addGroup(
									panel2Layout.createParallelGroup().addGroup(
											panel2Layout.createSequentialGroup().addGroup(
													panel2Layout.createParallelGroup()
															.addComponent(label4)
															.addComponent(label7)
															.addComponent(label11)
															.addComponent(label18))
													.addGap(51, 51, 51).addGroup(
													panel2Layout.createParallelGroup()
															.addComponent(label19)
															.addComponent(label8)
															.addComponent(label12)
															.addComponent(label5)))
											.addComponent(nextStation)
											.addComponent(finalStation))
							.addGap(37, 37, 37)));
			panel2Layout.setVerticalGroup(panel2Layout.createParallelGroup().addGroup(
					panel2Layout.createParallelGroup()
							.addGroup(GroupLayout.Alignment.TRAILING,
									panel2Layout.createSequentialGroup()
											.addContainerGap(142, Short.MAX_VALUE)
											.addComponent(label9,
													GroupLayout.PREFERRED_SIZE, 0,
													GroupLayout.PREFERRED_SIZE)
											.addContainerGap(75, Short.MAX_VALUE)))
					.addGroup(panel2Layout.createSequentialGroup().addGap(28, 28, 28)
							.addGroup(panel2Layout
									.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(label3).addComponent(label5)
									.addComponent(label4, GroupLayout.PREFERRED_SIZE, 16,
											GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(panel2Layout
									.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(label6)
									.addComponent(label8, GroupLayout.PREFERRED_SIZE, 16,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(label7))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(panel2Layout
									.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(label10).addComponent(label12)
									.addComponent(label11))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(panel2Layout
									.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(label13).addComponent(nextStation))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(panel2Layout
									.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(label15).addComponent(finalStation))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(panel2Layout
									.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(label17).addComponent(label19)
									.addComponent(label18))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		}

		//======== panel3 ========
		{

			//---- label20 ----
			label20.setText("\u5217\u8f66\u65b9\u5411");

			//---- label21 ----
			label21.setText("\u5217\u8f66\u9a7e\u9a76\u6a21\u5f0f");

			//---- label22 ----
			label22.setText("FWD");

			//---- label23 ----
			label23.setText("REV");

			//---- label24 ----
			label24.setText("NEUT");

			//---- label25 ----
			label25.setText("ATO");

			//---- label26 ----
			label26.setText("ATP");

			//---- label27 ----
			label27.setText("IATP");

			//---- label28 ----
			label28.setText("RM");

			//---- label29 ----
			label29.setText("ATB");

			GroupLayout panel3Layout = new GroupLayout(panel3);
			panel3.setLayout(panel3Layout);
			panel3Layout.setHorizontalGroup(panel3Layout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING,
							panel3Layout.createSequentialGroup().addGroup(
									panel3Layout.createParallelGroup().addGroup(
											panel3Layout.createSequentialGroup()
													.addGap(6, 6, 6).addComponent(label20)
													.addPreferredGap(
															LayoutStyle.ComponentPlacement.RELATED,
															GroupLayout.DEFAULT_SIZE,
															Short.MAX_VALUE)).addGroup(
											panel3Layout.createSequentialGroup()
													.addContainerGap(
															GroupLayout.DEFAULT_SIZE,
															Short.MAX_VALUE).addGroup(
													panel3Layout.createParallelGroup(
															GroupLayout.Alignment.TRAILING)
															.addGroup(panel3Layout
																	.createSequentialGroup()
																	.addComponent(label35)
																	.addPreferredGap(
																			LayoutStyle.ComponentPlacement.RELATED,
																			GroupLayout.DEFAULT_SIZE,
																			Short.MAX_VALUE)
																	.addComponent(
																			label24))
															.addGroup(panel3Layout
																	.createSequentialGroup()
																	.addComponent(label33)
																	.addPreferredGap(
																			LayoutStyle.ComponentPlacement.RELATED,
																			GroupLayout.DEFAULT_SIZE,
																			Short.MAX_VALUE)
																	.addComponent(
																			label22))
															.addGroup(panel3Layout
																	.createSequentialGroup()
																	.addComponent(label34)
																	.addPreferredGap(
																			LayoutStyle.ComponentPlacement.RELATED,
																			GroupLayout.DEFAULT_SIZE,
																			Short.MAX_VALUE)
																	.addComponent(
																			label23)))
													.addGap(78, 78, 78))).addGroup(
									panel3Layout.createParallelGroup().addGroup(
											panel3Layout.createSequentialGroup().addGroup(
													panel3Layout.createParallelGroup(
															GroupLayout.Alignment.TRAILING)
															.addComponent(label36)
															.addComponent(label37)
															.addComponent(label38)
															.addComponent(label39)
															.addComponent(label40))
													.addPreferredGap(
															LayoutStyle.ComponentPlacement.RELATED)
													.addGroup(panel3Layout
															.createParallelGroup()
															.addComponent(label25)
															.addComponent(label26)
															.addComponent(label27)
															.addComponent(label28)
															.addComponent(label29)))
											.addComponent(label21))));
			panel3Layout.linkSize(SwingConstants.HORIZONTAL,
					new Component[] { label22, label23, label24 });
			panel3Layout.setVerticalGroup(panel3Layout.createParallelGroup().addGroup(
					panel3Layout.createSequentialGroup().addGap(27, 27, 27).addGroup(
							panel3Layout
									.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(label20).addComponent(label21))
							.addGroup(panel3Layout.createParallelGroup().addGroup(
									panel3Layout.createSequentialGroup()
											.addGap(18, 18, 18).addGroup(panel3Layout
											.createParallelGroup(
													GroupLayout.Alignment.BASELINE)
											.addComponent(label25).addComponent(label22)
											.addComponent(label36))).addGroup(
									panel3Layout.createSequentialGroup()
											.addGap(18, 18, 18).addComponent(label33)))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(panel3Layout
									.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(label26).addComponent(label23)
									.addComponent(label34).addComponent(label37))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(panel3Layout
									.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(label27).addComponent(label24)
									.addComponent(label35).addComponent(label38))
							.addGap(12, 12, 12).addGroup(panel3Layout
							.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label28).addComponent(label39))
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addGroup(panel3Layout
									.createParallelGroup(GroupLayout.Alignment.BASELINE)
									.addComponent(label29).addComponent(label40))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		}

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(logList);
		}

		//---- label30 ----
		label30.setText("\u65e5\u5fd7\u8bb0\u5f55");

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup().addGroup(
				layout.createSequentialGroup().addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup().addGap(15, 15, 15)
								.addGroup(layout.createParallelGroup().addGroup(
										layout.createSequentialGroup()
												.addComponent(controlPanel,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(
														LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(panel2,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(
														LayoutStyle.ComponentPlacement.UNRELATED)
												.addComponent(panel3,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
										.addComponent(label30))).addGroup(
								layout.createSequentialGroup().addGap(30, 30, 30)
										.addComponent(scrollPane1,
												GroupLayout.PREFERRED_SIZE, 880,
												GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(45, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup().addGroup(
				layout.createSequentialGroup().addContainerGap().addGroup(
						layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
								.addGroup(GroupLayout.Alignment.LEADING,
										layout.createSequentialGroup().addGap(9, 9, 9)
												.addComponent(controlPanel,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addComponent(panel2, GroupLayout.Alignment.LEADING,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(panel3, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(label30, GroupLayout.PREFERRED_SIZE, 38,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 232,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(130, Short.MAX_VALUE)));
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Qings Yo
	private JPanel controlPanel;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	private JButton button6;
	private JButton button7;
	private JButton button8;
	private JButton button9;
	private JButton button10;
	private JButton button11;
	private JLabel label1;
	private TimePanel timePanel1;
	private JSeparator separator1;
	private JButton button12;
	private JPanel panel2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JLabel label7;
	private JLabel label8;
	private JLabel label9;
	private JLabel label10;
	private JLabel label11;
	private JLabel label12;
	private JLabel label13;
	private JLabel nextStation;
	private JLabel label15;
	private JLabel finalStation;
	private JLabel label17;
	private JLabel label18;
	private JLabel label19;
	private JPanel panel3;
	private JLabel label20;
	private JLabel label21;
	private JLabel label22;
	private JLabel label23;
	private JLabel label24;
	private JLabel label25;
	private JLabel label26;
	private JLabel label27;
	private JLabel label28;
	private JLabel label29;
	private JLabel label33;
	private JLabel label34;
	private JLabel label35;
	private JLabel label36;
	private JLabel label37;
	private JLabel label38;
	private JLabel label39;
	private JLabel label40;
	private JScrollPane scrollPane1;
	private JList logList;
	private JLabel label30;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
