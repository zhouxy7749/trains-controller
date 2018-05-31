/*
 * Created by JFormDesigner on Tue May 08 22:41:09 CST 2018
 */

package com.github.metro.ui;

import javax.swing.border.*;
import com.github.metro.Controller.TrainsController;
import com.github.metro.component.TimePanel;
import com.github.metro.util.Lights;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import static com.github.metro.util.Display.number;
import static com.github.metro.util.LogUtils.currentTime;
import static com.github.metro.util.LogUtils.getLogs;

/**
 * @author unknown
 */
public class MainForm extends JPanel {
	public MainForm() {
		initComponents();
		fwdLabel.setIcon(Lights.off());
		revLabel.setIcon(Lights.off());
		neutLabel.setIcon(Lights.off());
		atoLabel.setIcon(Lights.off());
		atpLabel.setIcon(Lights.off());
		iatpLabel.setIcon(Lights.off());
		rmLabel.setIcon(Lights.off());
		atbLabel.setIcon(Lights.off());
	}

	private void buttonActionPerformed(ActionEvent e) {
		List<String> logs = getLogs();
				TrainsController controller = TrainsController.getInstance();
		if (e.getActionCommand() == "发车") {
			if (controller.isEmergenceBraking() || controller.isOnHold() ||
					controller.isLostSignal() || controller.isLostPower()) {
				logs.add(currentTime() + "无效指令");
			}
			else {
				logs.add(currentTime() + "发车条件满足，列车发车");
				if (!controller.isTimerStarted()) {
					controller.autoPilot(currentSpeedLabel, remainDistanceLabel,
							nextStation, logList, brakingDistance);
				}
				controller.setSpeedUp(true);
				controller.setSpeedDown(false);
				finalStation.setText(controller.getFinalStation());
				nextStation.setText(controller.getNextStation());
				fwdLabel.setIcon(Lights.on());
				atoLabel.setIcon(Lights.on());
				rmLabel.setIcon(Lights.off());
				controller.setRMmode(false);
				atpLabel.setIcon(Lights.off());
				revLabel.setIcon(Lights.off());
			}
		}

		if (e.getActionCommand() == "车门状态信号丢失") {
			logs.add(currentTime() + "启动紧急制动，并向ATS发送告警信息");
			controller.setLostSignal(true);
		}

		if (e.getActionCommand() == "开门使能信号解除1") {
			if (controller.isLostSignal()) {
				controller.setEnableDoorMode1(true);
			} else {
				logs.add(currentTime() + "无效指令");
			}
		}

		if (e.getActionCommand() == "开门使能信号解除2") {
			if (controller.isLostSignal()) {
				controller.setEnableDoorMode2(true);
			} else {
				logs.add(currentTime() + "无效指令");
			}
		}

		if (e.getActionCommand() == "紧急制动") {
			logs.add(currentTime() + "启动紧急制动，并向ATS发送告警信息");
			controller.setEmergenceBraking(true);
		}

		if (e.getActionCommand() == "列车紧急制动复位") {
			logs.add(currentTime() + "紧急制动已复位");
			controller.setEmergenceBraking(false);
		}

		if (e.getActionCommand() == "车载控制器失去电源") {
			if (controller.isLostSignal() || controller.isStopped()) {
				logs.add(currentTime() + "无效指令");
			} else {
				controller.setLostPower(true);
				getLogs().add(currentTime() + "人工驾驶模式，最高速度为17km/h");
				controller.setAdjustmentOnLostPower(false);
				rmLabel.setIcon(Lights.on());
				controller.setRMmode(true);
				atoLabel.setIcon(Lights.off());
			}
		}

		if (e.getActionCommand() == "轮径校准") {
			if (controller.isLostPower()) {
				logs.add(currentTime() + "轮径校准，最高速度为20km/h");
				controller.setAdjustmentOnLostPower(true);
			} else {
				logs.add(currentTime() + "无效指令");
			}
		}

		if (e.getActionCommand() == "对位停车控制失效") {
			if (controller.isStopped()) {
				logs.add(currentTime() + "列车越过停车点5米");
				controller.setRemainDistance(995);
			}
			else {
				logs.add(currentTime() + "无效指令");
			}
		}

		if (e.getActionCommand() == "倒车1") {
			if (controller.isStopped()) {
				logs.add(currentTime() + "列车以1km/h速度倒车");
				revLabel.setIcon(Lights.on());
				fwdLabel.setIcon(Lights.off());
				atpLabel.setIcon(Lights.on());
				atoLabel.setIcon(Lights.off());
				controller.setReverseAtOne(true);
				controller.setReverseAtTwo(false);
			}
			else {
				logs.add(currentTime() + "无效指令");
			}
		}

		if (e.getActionCommand() == "倒车2") {
			if (controller.isStopped()) {
				logs.add(currentTime() + "列车以2km/h速度倒车");
				logs.add(currentTime() + "缓行速度超过允许速度，紧急制动");
				revLabel.setIcon(Lights.on());
				fwdLabel.setIcon(Lights.off());
				atpLabel.setIcon(Lights.on());
				atoLabel.setIcon(Lights.off());
				controller.setReverseAtOne(false);
				controller.setReverseAtTwo(true);
				controller.setEmergenceBraking(true);
			}
			else {
				logs.add(currentTime() + "无效指令");
			}
		}

		if (e.getActionCommand() == "停车") {
			if (controller.isLostPower()) {
				controller.setSpeedUp(false);
				controller.setSpeedDown(true);
				controller.setLostPower(false);
				rmLabel.setIcon(Lights.off());
				controller.setRMmode(false);
			} else {
				logs.add(currentTime() + "无效指令");
			}
		}

		if (e.getActionCommand() == "扣车") {
			controller.setOnHold(true);
			if (controller.isStopped()) {
				logs.add(currentTime() + "站内停车");
			}
			else {
				logs.add(currentTime() + "行至下一站停车");
			}
		}

		if (e.getActionCommand() == "撤销扣车") {
			if (controller.isOnHold()) {
				controller.setOnHold(false);
				logs.add(currentTime() + "已撤销扣车，列车将会正常启动");
			} else {
				logs.add(currentTime() + "无效指令");
			}
		}

		if (e.getActionCommand() == "跳停") {

			if (controller.isEmergenceBraking() || controller.isOnHold() ||
					controller.isLostSignal() || controller.isLostPower()) {
				logs.add(currentTime() + "无效指令");
			} else {
				double remainDistance = controller.getRemainDistance();
				if (remainDistance > 100 && !controller.isStopped()) {
					controller.skipCurrent();
					controller.setRemainDistance(remainDistance  + 1000);
					logs.add(currentTime() + "列车跳停一站，至下站再停车");
					controller.setSpeedUp(true);
					controller.setSpeedDown(false);
					remainDistanceLabel.setText(number(controller.getRemainDistance()));
					nextStation.setText(controller.getNextStation());
				} else {
					logs.add(currentTime() + "列车停站，忽略跳停");
				}
			}
		}

		if (e.getActionCommand() == "打开左侧车门") {
			logs.add(currentTime() + "车载控制器控制左侧车门打开");
		}

		if (e.getActionCommand() == "打开右侧车门") {
			logs.add(currentTime() + "车载控制器控制右侧车门打开");
		}

		if (e.getActionCommand() == "关闭车门") {
			logs.add(currentTime() + "收到开门使能信号解除");
			controller.setEnableDoor(true);
		}

		if (e.getActionCommand() == "打开左侧屏蔽门") {
			if (controller.isRMmode()) {
				logs.add(currentTime() + "车载控制器控制左侧屏蔽门打开");
			} else {
				logs.add(currentTime() + "无效指令");
			}
		}

		if (e.getActionCommand() == "打开右侧屏蔽门") {
			if (controller.isRMmode()) {
				logs.add(currentTime() + "车载控制器控制右侧屏蔽门打开");
			} else {
				logs.add(currentTime() + "无效指令");
			}
		}

		if (e.getActionCommand() == "关闭屏蔽门") {
			logs.add(currentTime() + "车载控制器控制屏蔽门关闭");
		}

		logList.setListData(logs.toArray());
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - qs
		controlPanel = new JPanel();
		label1 = new JLabel();
		timePanel1 = new TimePanel();
		separator1 = new JSeparator();
		panel1 = new JPanel();
		button1 = new JButton();
		button2 = new JButton();
		panel4 = new JPanel();
		button12 = new JButton();
		lostPowerBtn2 = new JButton();
		panel5 = new JPanel();
		button3 = new JButton();
		button4 = new JButton();
		button6 = new JButton();
		button7 = new JButton();
		button5 = new JButton();
		panel6 = new JPanel();
		button10 = new JButton();
		button11 = new JButton();
		button9 = new JButton();
		panel7 = new JPanel();
		button16 = new JButton();
		button17 = new JButton();
		button18 = new JButton();
		panel8 = new JPanel();
		lostPowerBtn = new JButton();
		button15 = new JButton();
		panel9 = new JPanel();
		lostSingalBtn = new JButton();
		button13 = new JButton();
		button14 = new JButton();
		scrollPane1 = new JScrollPane();
		logList = new JList();
		label30 = new JLabel();
		panel2 = new JPanel();
		label6 = new JLabel();
		brakingDistance = new JLabel();
		label8 = new JLabel();
		label9 = new JLabel();
		label10 = new JLabel();
		remainDistanceLabel = new JLabel();
		label12 = new JLabel();
		label13 = new JLabel();
		nextStation = new JLabel();
		label15 = new JLabel();
		finalStation = new JLabel();
		label17 = new JLabel();
		currentSpeedLabel = new JLabel();
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
		fwdLabel = new JLabel();
		revLabel = new JLabel();
		neutLabel = new JLabel();
		atoLabel = new JLabel();
		atpLabel = new JLabel();
		iatpLabel = new JLabel();
		rmLabel = new JLabel();
		atbLabel = new JLabel();

		//======== this ========

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


		//======== controlPanel ========
		{

			//---- label1 ----
			label1.setText("\u8f66\u6b21\u53f7: 00001");

			//======== panel1 ========
			{
				panel1.setBorder(new BevelBorder(BevelBorder.LOWERED));

				//---- button1 ----
				button1.setText("\u53d1\u8f66");
				button1.setForeground(new Color(0, 170, 91));
				button1.setFont(new Font("Open Sans", Font.BOLD, 13));
				button1.addActionListener(e -> buttonActionPerformed(e));

				//---- button2 ----
				button2.setText("\u505c\u8f66");
				button2.setForeground(new Color(0, 170, 91));
				button2.setFont(new Font("Open Sans", Font.BOLD, 13));
				button2.addActionListener(e -> buttonActionPerformed(e));

				GroupLayout panel1Layout = new GroupLayout(panel1);
				panel1.setLayout(panel1Layout);
				panel1Layout.setHorizontalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addGap(22, 22, 22)
							.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(button2)
								.addComponent(button1))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
				panel1Layout.setVerticalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(button1)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button2)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
			}

			//======== panel4 ========
			{
				panel4.setBorder(new BevelBorder(BevelBorder.LOWERED));

				//---- button12 ----
				button12.setText("\u7d27\u6025\u5236\u52a8");
				button12.setForeground(new Color(106, 37, 106));
				button12.setFont(new Font("Open Sans", Font.BOLD, 13));
				button12.addActionListener(e -> buttonActionPerformed(e));

				//---- lostPowerBtn2 ----
				lostPowerBtn2.setText("\u5217\u8f66\u7d27\u6025\u5236\u52a8\u590d\u4f4d");
				lostPowerBtn2.setForeground(new Color(106, 37, 106));
				lostPowerBtn2.setFont(new Font("Open Sans", Font.BOLD, 13));
				lostPowerBtn2.addActionListener(e -> buttonActionPerformed(e));

				GroupLayout panel4Layout = new GroupLayout(panel4);
				panel4.setLayout(panel4Layout);
				panel4Layout.setHorizontalGroup(
					panel4Layout.createParallelGroup()
						.addGroup(panel4Layout.createSequentialGroup()
							.addContainerGap(19, Short.MAX_VALUE)
							.addGroup(panel4Layout.createParallelGroup()
								.addGroup(GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
									.addComponent(lostPowerBtn2)
									.addGap(17, 17, 17))
								.addGroup(GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
									.addComponent(button12)
									.addGap(41, 41, 41))))
				);
				panel4Layout.setVerticalGroup(
					panel4Layout.createParallelGroup()
						.addGroup(panel4Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(button12)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(lostPowerBtn2)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
			}

			//======== panel5 ========
			{
				panel5.setBorder(new BevelBorder(BevelBorder.LOWERED));

				//---- button3 ----
				button3.setText("\u6253\u5f00\u5de6\u4fa7\u8f66\u95e8");
				button3.setForeground(new Color(239, 95, 167));
				button3.setFont(new Font("Open Sans", Font.BOLD, 13));
				button3.addActionListener(e -> buttonActionPerformed(e));

				//---- button4 ----
				button4.setText("\u6253\u5f00\u53f3\u4fa7\u8f66\u95e8");
				button4.setForeground(new Color(239, 95, 167));
				button4.setFont(new Font("Open Sans", Font.BOLD, 13));
				button4.addActionListener(e -> buttonActionPerformed(e));

				//---- button6 ----
				button6.setText("\u6253\u5f00\u5de6\u4fa7\u5c4f\u853d\u95e8");
				button6.setForeground(new Color(239, 95, 167));
				button6.setFont(new Font("Open Sans", Font.BOLD, 13));
				button6.addActionListener(e -> buttonActionPerformed(e));

				//---- button7 ----
				button7.setText("\u6253\u5f00\u53f3\u4fa7\u5c4f\u853d\u95e8");
				button7.setForeground(new Color(239, 95, 167));
				button7.setFont(new Font("Open Sans", Font.BOLD, 13));
				button7.addActionListener(e -> buttonActionPerformed(e));

				//---- button5 ----
				button5.setText("\u5173\u95ed\u8f66\u95e8");
				button5.setForeground(new Color(239, 95, 167));
				button5.setFont(new Font("Open Sans", Font.BOLD, 13));
				button5.addActionListener(e -> buttonActionPerformed(e));

				GroupLayout panel5Layout = new GroupLayout(panel5);
				panel5.setLayout(panel5Layout);
				panel5Layout.setHorizontalGroup(
					panel5Layout.createParallelGroup()
						.addGroup(panel5Layout.createSequentialGroup()
							.addContainerGap(15, Short.MAX_VALUE)
							.addGroup(panel5Layout.createParallelGroup()
								.addGroup(panel5Layout.createParallelGroup(GroupLayout.Alignment.CENTER)
									.addComponent(button5)
									.addComponent(button4))
								.addComponent(button3, GroupLayout.Alignment.CENTER))
							.addContainerGap(17, Short.MAX_VALUE))
						.addGroup(panel5Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(button6)
							.addGap(0, 0, Short.MAX_VALUE))
						.addGroup(panel5Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(button7)
							.addContainerGap(13, Short.MAX_VALUE))
				);
				panel5Layout.setVerticalGroup(
					panel5Layout.createParallelGroup()
						.addGroup(panel5Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(button3)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(button4)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(button6)
							.addGap(12, 12, 12)
							.addComponent(button7)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(button5)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
			}

			//======== panel6 ========
			{
				panel6.setBorder(new BevelBorder(BevelBorder.LOWERED));

				//---- button10 ----
				button10.setText("\u6263\u8f66");
				button10.setForeground(new Color(0, 162, 155));
				button10.setFont(new Font("Open Sans", Font.BOLD, 13));
				button10.addActionListener(e -> buttonActionPerformed(e));

				//---- button11 ----
				button11.setText("\u64a4\u9500\u6263\u8f66");
				button11.setForeground(new Color(0, 162, 155));
				button11.setFont(new Font("Open Sans", Font.BOLD, 13));
				button11.addActionListener(e -> buttonActionPerformed(e));

				//---- button9 ----
				button9.setText("\u8df3\u505c");
				button9.setForeground(new Color(0, 162, 155));
				button9.setFont(new Font("Open Sans", Font.BOLD, 13));
				button9.addActionListener(e -> buttonActionPerformed(e));

				GroupLayout panel6Layout = new GroupLayout(panel6);
				panel6.setLayout(panel6Layout);
				panel6Layout.setHorizontalGroup(
					panel6Layout.createParallelGroup()
						.addGroup(panel6Layout.createSequentialGroup()
							.addGap(10, 10, 10)
							.addGroup(panel6Layout.createParallelGroup(GroupLayout.Alignment.CENTER)
								.addComponent(button10)
								.addComponent(button11)
								.addComponent(button9))
							.addGap(0, 23, Short.MAX_VALUE))
				);
				panel6Layout.setVerticalGroup(
					panel6Layout.createParallelGroup()
						.addGroup(panel6Layout.createSequentialGroup()
							.addComponent(button10)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button11)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button9)
							.addContainerGap(10, Short.MAX_VALUE))
				);
			}

			//======== panel7 ========
			{
				panel7.setBorder(new BevelBorder(BevelBorder.LOWERED));

				//---- button16 ----
				button16.setText("\u5bf9\u4f4d\u505c\u8f66\u63a7\u5236\u5931\u6548");
				button16.setForeground(new Color(49, 188, 205));
				button16.setFont(new Font("Open Sans", Font.BOLD, 13));
				button16.addActionListener(e -> buttonActionPerformed(e));

				//---- button17 ----
				button17.setText("\u5012\u8f661");
				button17.setForeground(new Color(49, 188, 205));
				button17.setFont(new Font("Open Sans", Font.BOLD, 13));
				button17.addActionListener(e -> buttonActionPerformed(e));

				//---- button18 ----
				button18.setText("\u5012\u8f662");
				button18.setForeground(new Color(49, 188, 205));
				button18.setFont(new Font("Open Sans", Font.BOLD, 13));
				button18.addActionListener(e -> buttonActionPerformed(e));

				GroupLayout panel7Layout = new GroupLayout(panel7);
				panel7.setLayout(panel7Layout);
				panel7Layout.setHorizontalGroup(
					panel7Layout.createParallelGroup()
						.addGroup(panel7Layout.createSequentialGroup()
							.addGroup(panel7Layout.createParallelGroup()
								.addGroup(panel7Layout.createSequentialGroup()
									.addGap(49, 49, 49)
									.addGroup(panel7Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
										.addComponent(button18)
										.addComponent(button17)))
								.addGroup(panel7Layout.createSequentialGroup()
									.addGap(16, 16, 16)
									.addComponent(button16)))
							.addContainerGap(20, Short.MAX_VALUE))
				);
				panel7Layout.setVerticalGroup(
					panel7Layout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, panel7Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(button16)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button17)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button18)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
			}

			//======== panel8 ========
			{
				panel8.setBorder(new BevelBorder(BevelBorder.LOWERED));

				//---- lostPowerBtn ----
				lostPowerBtn.setText("\u8f66\u8f7d\u63a7\u5236\u5668\u5931\u53bb\u7535\u6e90");
				lostPowerBtn.setForeground(new Color(49, 188, 205));
				lostPowerBtn.setFont(new Font("Open Sans", Font.BOLD, 13));
				lostPowerBtn.addActionListener(e -> buttonActionPerformed(e));

				//---- button15 ----
				button15.setText("\u8f6e\u5f84\u6821\u51c6");
				button15.setForeground(new Color(49, 188, 205));
				button15.setFont(new Font("Open Sans", Font.BOLD, 13));
				button15.addActionListener(e -> buttonActionPerformed(e));

				GroupLayout panel8Layout = new GroupLayout(panel8);
				panel8.setLayout(panel8Layout);
				panel8Layout.setHorizontalGroup(
					panel8Layout.createParallelGroup()
						.addGroup(panel8Layout.createSequentialGroup()
							.addComponent(lostPowerBtn)
							.addGap(0, 0, Short.MAX_VALUE))
						.addGroup(panel8Layout.createSequentialGroup()
							.addGap(28, 28, 28)
							.addComponent(button15)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
				panel8Layout.setVerticalGroup(
					panel8Layout.createParallelGroup()
						.addGroup(panel8Layout.createSequentialGroup()
							.addContainerGap(12, Short.MAX_VALUE)
							.addComponent(lostPowerBtn)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button15))
				);
			}

			//======== panel9 ========
			{
				panel9.setBorder(new BevelBorder(BevelBorder.LOWERED));

				//---- lostSingalBtn ----
				lostSingalBtn.setText("\u8f66\u95e8\u72b6\u6001\u4fe1\u53f7\u4e22\u5931");
				lostSingalBtn.setForeground(new Color(49, 188, 205));
				lostSingalBtn.setFont(new Font("Open Sans", Font.BOLD, 13));
				lostSingalBtn.addActionListener(e -> buttonActionPerformed(e));

				//---- button13 ----
				button13.setText("\u5f00\u95e8\u4f7f\u80fd\u4fe1\u53f7\u89e3\u96641");
				button13.setForeground(new Color(49, 188, 205));
				button13.setFont(new Font("Open Sans", Font.BOLD, 13));
				button13.addActionListener(e -> buttonActionPerformed(e));

				//---- button14 ----
				button14.setText("\u5f00\u95e8\u4f7f\u80fd\u4fe1\u53f7\u89e3\u96642");
				button14.setForeground(new Color(49, 188, 205));
				button14.setFont(new Font("Open Sans", Font.BOLD, 13));
				button14.addActionListener(e -> buttonActionPerformed(e));

				GroupLayout panel9Layout = new GroupLayout(panel9);
				panel9.setLayout(panel9Layout);
				panel9Layout.setHorizontalGroup(
					panel9Layout.createParallelGroup()
						.addGroup(panel9Layout.createSequentialGroup()
							.addGroup(panel9Layout.createParallelGroup()
								.addComponent(lostSingalBtn)
								.addGroup(panel9Layout.createSequentialGroup()
									.addComponent(button13)
									.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
									.addComponent(button14)))
							.addGap(0, 6, Short.MAX_VALUE))
				);
				panel9Layout.setVerticalGroup(
					panel9Layout.createParallelGroup()
						.addGroup(panel9Layout.createSequentialGroup()
							.addContainerGap(12, Short.MAX_VALUE)
							.addComponent(lostSingalBtn)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(panel9Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(button13)
								.addComponent(button14)))
				);
			}

			GroupLayout controlPanelLayout = new GroupLayout(controlPanel);
			controlPanel.setLayout(controlPanelLayout);
			controlPanelLayout.setHorizontalGroup(
				controlPanelLayout.createParallelGroup()
					.addGroup(controlPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(controlPanelLayout.createParallelGroup()
							.addGroup(controlPanelLayout.createSequentialGroup()
								.addComponent(label1)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(timePanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(controlPanelLayout.createSequentialGroup()
								.addGap(6, 6, 6)
								.addGroup(controlPanelLayout.createParallelGroup()
									.addGroup(controlPanelLayout.createSequentialGroup()
										.addGroup(controlPanelLayout.createParallelGroup()
											.addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(panel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(panel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
											.addComponent(panel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
									.addGroup(controlPanelLayout.createSequentialGroup()
										.addComponent(panel8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(panel9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(separator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(24, 24, 24))))
			);
			controlPanelLayout.setVerticalGroup(
				controlPanelLayout.createParallelGroup()
					.addGroup(controlPanelLayout.createSequentialGroup()
						.addGroup(controlPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
							.addComponent(timePanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(label1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(controlPanelLayout.createParallelGroup()
							.addGroup(controlPanelLayout.createSequentialGroup()
								.addGroup(controlPanelLayout.createParallelGroup()
									.addGroup(controlPanelLayout.createSequentialGroup()
										.addGap(2, 2, 2)
										.addComponent(panel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addComponent(separator1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(panel6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addComponent(panel5, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
							.addGroup(controlPanelLayout.createSequentialGroup()
								.addComponent(panel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(panel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(controlPanelLayout.createParallelGroup()
							.addComponent(panel8, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(panel9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap())
			);
		}

		//======== scrollPane1 ========
		{
			scrollPane1.setViewportView(logList);
		}

		//---- label30 ----
		label30.setText("\u65e5\u5fd7\u8bb0\u5f55");

		//======== panel2 ========
		{

			//---- label6 ----
			label6.setText("\u505c\u8f66\u8ddd\u79bb");

			//---- brakingDistance ----
			brakingDistance.setText("0");

			//---- label8 ----
			label8.setText("\u7c73");

			//---- label9 ----
			label9.setText("\u505c\u8f66\u8ddd\u79bb");

			//---- label10 ----
			label10.setText("\u4e0b\u7ad9\u8ddd\u79bb");

			//---- remainDistanceLabel ----
			remainDistanceLabel.setText("1000");

			//---- label12 ----
			label12.setText("\u7c73");

			//---- label13 ----
			label13.setText("\u4e0b\u4e00\u7ad9");

			//---- nextStation ----
			nextStation.setText("\u5a7a\u6c5f\u8def\u7ad9");

			//---- label15 ----
			label15.setText("\u7ec8\u70b9\u7ad9");

			//---- finalStation ----
			finalStation.setText("\u95f8\u5f04\u53e3\u7ad9");

			//---- label17 ----
			label17.setText("\u5217\u8f66\u901f\u5ea6");

			//---- currentSpeedLabel ----
			currentSpeedLabel.setText("0.0");

			//---- label19 ----
			label19.setText("\u5343\u7c73/\u65f6");

			GroupLayout panel2Layout = new GroupLayout(panel2);
			panel2.setLayout(panel2Layout);
			panel2Layout.setHorizontalGroup(
				panel2Layout.createParallelGroup()
					.addGroup(panel2Layout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
							.addContainerGap(238, Short.MAX_VALUE)
							.addComponent(label9)
							.addContainerGap(113, Short.MAX_VALUE)))
					.addGroup(panel2Layout.createSequentialGroup()
						.addGap(24, 24, 24)
						.addGroup(panel2Layout.createParallelGroup()
							.addComponent(label6)
							.addComponent(label10)
							.addComponent(label13)
							.addComponent(label15)
							.addComponent(label17))
						.addGap(97, 97, 97)
						.addGroup(panel2Layout.createParallelGroup()
							.addGroup(panel2Layout.createSequentialGroup()
								.addGroup(panel2Layout.createParallelGroup()
									.addComponent(brakingDistance)
									.addComponent(remainDistanceLabel)
									.addComponent(currentSpeedLabel))
								.addGap(51, 51, 51)
								.addGroup(panel2Layout.createParallelGroup()
									.addComponent(label19)
									.addComponent(label8)
									.addComponent(label12)))
							.addComponent(nextStation)
							.addComponent(finalStation))
						.addContainerGap(106, Short.MAX_VALUE))
			);
			panel2Layout.setVerticalGroup(
				panel2Layout.createParallelGroup()
					.addGroup(panel2Layout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
							.addContainerGap(142, Short.MAX_VALUE)
							.addComponent(label9, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(73, Short.MAX_VALUE)))
					.addGroup(panel2Layout.createSequentialGroup()
						.addGap(56, 56, 56)
						.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label6)
							.addComponent(label8, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
							.addComponent(brakingDistance))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label10)
							.addComponent(label12)
							.addComponent(remainDistanceLabel))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label13)
							.addComponent(nextStation))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label15)
							.addComponent(finalStation))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label17)
							.addComponent(label19)
							.addComponent(currentSpeedLabel))
						.addContainerGap(31, Short.MAX_VALUE))
			);
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
			panel3Layout.setHorizontalGroup(
				panel3Layout.createParallelGroup()
					.addGroup(panel3Layout.createSequentialGroup()
						.addGap(32, 32, 32)
						.addGroup(panel3Layout.createParallelGroup()
							.addGroup(panel3Layout.createSequentialGroup()
								.addComponent(neutLabel)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(label24))
							.addGroup(panel3Layout.createSequentialGroup()
								.addComponent(revLabel)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(label23))
							.addComponent(label20)
							.addGroup(panel3Layout.createSequentialGroup()
								.addComponent(fwdLabel)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(label22, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(panel3Layout.createParallelGroup()
							.addGroup(panel3Layout.createSequentialGroup()
								.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
									.addComponent(atpLabel)
									.addComponent(iatpLabel)
									.addComponent(rmLabel)
									.addComponent(atbLabel)
									.addComponent(atoLabel))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(panel3Layout.createParallelGroup()
									.addComponent(label27)
									.addComponent(label28)
									.addComponent(label29)
									.addComponent(label26)
									.addComponent(label25)))
							.addComponent(label21))
						.addContainerGap(59, Short.MAX_VALUE))
			);
			panel3Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {label23, label24});
			panel3Layout.setVerticalGroup(
				panel3Layout.createParallelGroup()
					.addGroup(panel3Layout.createSequentialGroup()
						.addGap(33, 33, 33)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label20)
							.addComponent(label21))
						.addGap(28, 28, 28)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(fwdLabel)
							.addComponent(label22)
							.addComponent(atoLabel)
							.addComponent(label25))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(panel3Layout.createParallelGroup()
							.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(revLabel)
								.addComponent(atpLabel)
								.addComponent(label23, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
							.addComponent(label26))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label27)
							.addComponent(neutLabel)
							.addComponent(iatpLabel)
							.addComponent(label24))
						.addGap(12, 12, 12)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label28)
							.addComponent(rmLabel))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(panel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(label29)
							.addComponent(atbLabel))
						.addContainerGap(147, Short.MAX_VALUE))
			);
		}

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
						.addComponent(controlPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(label30))
						.addGroup(layout.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 491, GroupLayout.PREFERRED_SIZE)
							.addGap(74, 74, 74)))
					.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
							.addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
							.addComponent(panel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(80, 80, 80))))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addGroup(layout.createSequentialGroup()
							.addComponent(panel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(33, 33, 33)
							.addComponent(panel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createSequentialGroup()
							.addComponent(controlPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(label30, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(23, Short.MAX_VALUE))
		);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - qs
	private JPanel controlPanel;
	private JLabel label1;
	private TimePanel timePanel1;
	private JSeparator separator1;
	private JPanel panel1;
	private JButton button1;
	private JButton button2;
	private JPanel panel4;
	private JButton button12;
	private JButton lostPowerBtn2;
	private JPanel panel5;
	private JButton button3;
	private JButton button4;
	private JButton button6;
	private JButton button7;
	private JButton button5;
	private JPanel panel6;
	private JButton button10;
	private JButton button11;
	private JButton button9;
	private JPanel panel7;
	private JButton button16;
	private JButton button17;
	private JButton button18;
	private JPanel panel8;
	private JButton lostPowerBtn;
	private JButton button15;
	private JPanel panel9;
	private JButton lostSingalBtn;
	private JButton button13;
	private JButton button14;
	private JScrollPane scrollPane1;
	private JList logList;
	private JLabel label30;
	private JPanel panel2;
	private JLabel label6;
	private JLabel brakingDistance;
	private JLabel label8;
	private JLabel label9;
	private JLabel label10;
	private JLabel remainDistanceLabel;
	private JLabel label12;
	private JLabel label13;
	private JLabel nextStation;
	private JLabel label15;
	private JLabel finalStation;
	private JLabel label17;
	private JLabel currentSpeedLabel;
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
	private JLabel fwdLabel;
	private JLabel revLabel;
	private JLabel neutLabel;
	private JLabel atoLabel;
	private JLabel atpLabel;
	private JLabel iatpLabel;
	private JLabel rmLabel;
	private JLabel atbLabel;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
