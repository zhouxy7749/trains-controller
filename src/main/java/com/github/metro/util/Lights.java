package com.github.metro.util;

import javax.swing.*;
import java.awt.*;

public class Lights {
	public static ImageIcon on(){
		return getImageIcon("/img/on-green-light.png");
	}

	public static ImageIcon off(){
		return getImageIcon("/img/off-green-light.png");
	}

	private static ImageIcon getImageIcon(String fileName) {
		ImageIcon originalIcon = new ImageIcon(Lights.class.getResource(fileName));
		Image originalImage = originalIcon.getImage();
		Image newImg = originalImage.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		return new ImageIcon(newImg);
	}
}
