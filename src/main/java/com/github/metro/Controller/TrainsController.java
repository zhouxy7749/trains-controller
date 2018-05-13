package com.github.metro.Controller;

import com.github.metro.util.LogUtils;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.Timer;

import static com.github.metro.util.Display.number;
import static com.github.metro.util.LogUtils.currentTime;
import static com.github.metro.util.LogUtils.getLogs;

public class TrainsController {

	public static final int MAXIMUM_SPEED = 80;
	public static final double ACCELERATION = 0.8;
	public static final double DECELERATION = -0.6;
	public static final int STOPPED_TIME = 300;
	private static TrainsController controller = null;
	private static Map<Integer, String> stations = null;
	private static int currentStation = 0;
	private double remainDistance = 1000;
	private double currentSpeedMetersPerSec = 0;
	private boolean reverseAtOne;
	private boolean reverseAtTwo;
	private boolean emergenceBraking;
	private boolean timerStarted;
	private boolean onHold;
	private boolean enableDoor;
	private boolean enableDoorMode1;
	private boolean enableDoorMode2;
	private boolean stoppedInMiddle;

	public boolean isStopped() {
		return stopped;
	}

	private double stoppedTimeCount = 0;
	private double stoppedTime = STOPPED_TIME;

	static {
		stations = new HashMap<>();
		stations.put(0, "近江站");
		stations.put(1, "婺江路站");
		stations.put(2, "城站站");
		stations.put(3, "定安路站");
		stations.put(4, "龙翔桥站");
		stations.put(5, "凤起路站");
		stations.put(6, "武林广场站");
		stations.put(7, "西湖文化广场站");
		stations.put(8, "打铁关站");
		stations.put(9, "闸弄口站");
	}

	private boolean speedUp = true;
	private boolean speedDown = false;
	private boolean stopped = false;
	private boolean isLostSignal;
	private boolean isLostPower;
	private boolean adjustmentOnLostPower;
	private int enableDoorCount = 0;

	private TrainsController() {
	}

	public String getNextStation() {
		return stations.get(currentStation + 1);
	}

	public void skipCurrent() {
		currentStation++;
	}

	public String getCurrentStation() {
		return stations.get(currentStation);
	}

	public static TrainsController getInstance() {
		if (controller == null) {
			controller = new TrainsController();
		}
		return controller;
	}

	public void autoPilot(JLabel currentSpeed, JLabel remainDistanceLabel,
			JLabel nextStation, JList logList, JLabel brakingDistance) {
		this.timerStarted = true;
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(
				new AutoPilotTimerTask(currentSpeed, remainDistanceLabel, nextStation,
						logList, brakingDistance), new Date(), 1000);
	}

	public double getRemainDistance() {
		return remainDistance;
	}

	public void setRemainDistance(double remainDistance) {
		this.remainDistance = remainDistance;
	}

	public void setLostSignal(boolean lostSignal) {
		this.isLostSignal = lostSignal;
	}

	public boolean isLostSignal() {
		return isLostSignal;
	}

	public void setSpeedUp(boolean speedUp) {
		this.speedUp = speedUp;
	}

	public void setSpeedDown(boolean speedDown) {
		this.speedDown = speedDown;
	}

	public boolean isLostPower() {
		return isLostPower;
	}

	public void setLostPower(boolean lostPower) {
		isLostPower = lostPower;
	}

	public void setAdjustmentOnLostPower(boolean adjustmentOnLostPower) {
		this.adjustmentOnLostPower = adjustmentOnLostPower;
	}

	public boolean isAdjustmentOnLostPower() {
		return adjustmentOnLostPower;
	}

	public void setReverseAtOne(boolean reverseAtOne) {
		this.reverseAtOne = reverseAtOne;
	}

	public boolean isReverseAtOne() {
		return reverseAtOne;
	}

	public void setReverseAtTwo(boolean reverseAtTwo) {
		this.reverseAtTwo = reverseAtTwo;
	}

	public boolean isReverseAtTwo() {
		return reverseAtTwo;
	}

	public void setEmergenceBraking(boolean emergenceBraking) {
		this.emergenceBraking = emergenceBraking;
	}

	public boolean isEmergenceBraking() {
		return emergenceBraking;
	}

	public boolean isTimerStarted() {
		return timerStarted;
	}

	public void setOnHold(boolean onHold) {
		this.onHold = onHold;
	}

	public boolean isOnHold() {
		return onHold;
	}

	public void setEnableDoor(boolean enableDoor) {
		this.enableDoor = enableDoor;
	}

	public void setEnableDoorMode1(boolean enableDoorMode1) {
		this.enableDoorMode1 = enableDoorMode1;
	}

	public void setEnableDoorMode2(boolean enableDoorMode2) {
		this.enableDoorMode2 = enableDoorMode2;
	}

	class AutoPilotTimerTask extends TimerTask {
		private final JLabel currentSpeedLabel;
		private final JLabel remainDistanceLabel;
		private final JLabel nextStation;
		private final JList logList;
		private final JLabel brakingDistanceLabel;

		public AutoPilotTimerTask(JLabel currentSpeedLabel, JLabel remainDistanceLabel,
				JLabel nextStation, JList logList, JLabel brakingDistanceLabel) {

			this.currentSpeedLabel = currentSpeedLabel;
			this.remainDistanceLabel = remainDistanceLabel;
			this.nextStation = nextStation;
			this.logList = logList;
			this.brakingDistanceLabel = brakingDistanceLabel;
		}

		@Override public void run() {
			remainDistanceLabel.setText(number(remainDistance));
			currentSpeedLabel
					.setText(number(toKilometersPerHour(currentSpeedMetersPerSec)));
			brakingDistanceLabel.setText(number(getBrakingDistance()));
			stopTrainIfNeeded();
			enableDoorIfNeeded();

			if (reverseAtOne) reverseAtOne();

			if (reverseAtTwo) reverseAtTwo();

			if (isLostPower) {
				if (adjustmentOnLostPower) {
					updateDistanceOnSpeedUp();
					currentSpeedMetersPerSec = speedUpTo(5.556);
				}
				else {
					updateDistanceOnSpeedDown();
					currentSpeedMetersPerSec = speedDownTo(4.167);
				}
				return;
			}
			if (speedUp
					&& toKilometersPerHour(currentSpeedMetersPerSec) < MAXIMUM_SPEED) {
				stopped = false;
				double tmpSpeed = currentSpeedMetersPerSec + ACCELERATION;
				if (toKilometersPerHour(tmpSpeed) > 80) {
					tmpSpeed = 22.2223;
				}
				currentSpeedMetersPerSec = tmpSpeed;
				updateDistanceOnSpeedUp();
				if (stoppedInMiddle && Math.abs(getBrakingDistance() - remainDistance )< 10) {
					speedUp = false;
					speedDown = true;
				}
			}
			else {
				speedUp = false;
			}

			if (!speedUp && !speedDown) {
				if (currentSpeedMetersPerSec == 0) {
					stoppedTimeCount++;
					if (stoppedTimeCount == stoppedTime) {
						speedUp = true;
						stoppedTimeCount = 0;
						stopped = false;
						getLogs().add(LogUtils.currentTime() + "已等待5分钟");
						getLogs().add(LogUtils.currentTime() + "车门屏蔽门自动关闭");
						getLogs().add(LogUtils.currentTime() + "发车条件满足，列车发车");
					}
				}
				else {
					updateDistanceOnSameSpeed();
					if (remainDistance < 420) {
						speedDown = true;
					}
				}
			}

			if (speedDown && toKilometersPerHour(currentSpeedMetersPerSec) > 0) {
				updateDistanceOnSpeedDown();
				if (remainDistance <= 1) {
					remainDistance = 0;
					currentSpeedMetersPerSec = 0;
				}

				currentSpeedMetersPerSec = (currentSpeedMetersPerSec + DECELERATION);
				if (currentSpeedMetersPerSec <= 0) {
					currentSpeedMetersPerSec = 0;
					stopped = true;
					stoppedInMiddle = false;
					if (remainDistance > 10) {
						stoppedInMiddle = true;
					}

					if (remainDistance > 0 && remainDistance < 10) {
						currentSpeedMetersPerSec = 0.278;
						remainDistance = Math.abs(remainDistance - 1);
					}
					if (remainDistance == 0) {
						remainDistance = 1000;
						currentStation++;
						getLogs().add(LogUtils.currentTime() + "列车已到："
								+ getCurrentStation());
						nextStation.setText(getNextStation());
						getLogs().add(LogUtils.currentTime() + "列车停车，车门自动打开");
					}
				}
			}
			else {
				speedDown = false;
			}

			remainDistanceLabel.setText(number(remainDistance));
			currentSpeedLabel
					.setText(number(toKilometersPerHour(currentSpeedMetersPerSec)));
			logList.setListData(getLogs().toArray());
		}

		private double getBrakingDistance() {
			return currentSpeedMetersPerSec * currentSpeedMetersPerSec / 1.2;
		}

		private void enableDoorIfNeeded() {
			if (enableDoor) {
				enableDoorCount++;
				if (enableDoorMode1 && enableDoorCount == 5) {
					resetAfterEnableDoor(currentTime() + "车门和屏蔽门关闭并锁定");
				}
				if (enableDoorMode2 && enableDoorCount == 8) {
					resetAfterEnableDoor(currentTime() + "向ATS传送告警信息");
				}
			}
		}

		private void resetAfterEnableDoor(String e) {
			getLogs().add(e);
			enableDoor = false;
			setLostSignal(false);
			enableDoorCount = 0;
		}

		private void reverseAtTwo() {
			currentSpeedMetersPerSec = 0.556;
			remainDistance = remainDistance + 2;
			if (remainDistance >= 1000) {
				remainDistance = 1000;
				currentSpeedMetersPerSec = 0;
				getLogs().add(LogUtils.currentTime() + "列车自动停车，车门自动打开");
				stoppedTimeCount = 0;
				reverseAtTwo = false;
			}
		}

		private void reverseAtOne() {
			currentSpeedMetersPerSec = 0.278;
			remainDistance = remainDistance + 1;
			if (remainDistance >= 1000) {
				remainDistance = 1000;
				currentSpeedMetersPerSec = 0;
				getLogs().add(LogUtils.currentTime() + "列车自动停车，车门自动打开");
				stoppedTimeCount = 0;
				reverseAtOne = false;
			}
		}

		private double speedDownTo(double belowValue) {
			return currentSpeedMetersPerSec <= belowValue ? belowValue :
					(currentSpeedMetersPerSec + DECELERATION);
		}

		private double speedUpTo(double upValue) {
			return currentSpeedMetersPerSec >= upValue ? upValue :
					(currentSpeedMetersPerSec + ACCELERATION);
		}

		private void updateDistanceOnSpeedDown() {
			double deltaDistance = currentSpeedMetersPerSec * 1 + DECELERATION * 1 / 2;
			remainDistance = remainDistance - deltaDistance;
		}

		private void updateDistanceOnSameSpeed() {
			double deltaDistance = currentSpeedMetersPerSec * 1;
			remainDistance = remainDistance - deltaDistance;
		}

		private void updateDistanceOnSpeedUp() {
			double deltaDistance = currentSpeedMetersPerSec * 1 + ACCELERATION * 1 / 2;
			remainDistance = remainDistance - deltaDistance;
		}

		private void stopTrainIfNeeded() {
			if (isLostSignal || emergenceBraking) {
				if (stopped) {
					stoppedTime = Integer.MAX_VALUE;
				}
				else {
					speedDown = true;
					speedUp = false;
				}
			}

			if (onHold) {
				stoppedTime = Integer.MAX_VALUE;
			}
			else {
				stoppedTime = STOPPED_TIME;
			}
		}
	}

	private double toKilometersPerHour(double currentSpeedMetersPerSec) {
		return new BigDecimal(currentSpeedMetersPerSec * 3.6)
				.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public String getFinalStation() {
		return stations.get(9);
	}
}
