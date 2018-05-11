package com.github.metro.Controller;

import java.util.HashMap;
import java.util.Map;

public class VehicleOnBoardController {

	private static VehicleOnBoardController controller = null;
	private static Map<Integer, String> stations = null;
	private static int currentStation = 0;

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

	private VehicleOnBoardController() {
	}

	public String getNextStation() {
		return stations.get(currentStation + 1);
	}

	public static VehicleOnBoardController getInstance() {
		if (controller == null) {
			controller = new VehicleOnBoardController();
		}
		return controller;
	}

	public void start() {
		currentStation++;

	}

	public String getFinalStation() {
		return stations.get(9);
	}
}
