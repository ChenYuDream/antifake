package com.sinosoft.antifake.helpers.kaptcha;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.google.code.kaptcha.GimpyEngine;
import com.google.code.kaptcha.util.Configurable;

public class NoGimpy extends Configurable implements GimpyEngine {

	public BufferedImage getDistortedImage(BufferedImage baseImage) {
		BufferedImage distortedImage = new BufferedImage(baseImage.getWidth(),
		baseImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D graph = (Graphics2D) distortedImage.getGraphics();
		graph.drawImage(baseImage, 0, 0, null, null);
		graph.dispose();
		return distortedImage;
	}
}