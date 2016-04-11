package edu.seu.vCampus.client.view.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class MyScrollBarUI extends BasicScrollBarUI {
	Color one, two;

	public MyScrollBarUI() {
		one = Color.WHITE;
		two = Color.LIGHT_GRAY;
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
		JButton button = new JButton("zero button");
		Dimension zeroDim = new Dimension(0, 0);
		button.setPreferredSize(zeroDim);
		button.setMinimumSize(zeroDim);
		button.setMaximumSize(zeroDim);
		return button;
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		JButton button = new JButton("zero button");
		Dimension zeroDim = new Dimension(0, 0);
		button.setPreferredSize(zeroDim);
		button.setMinimumSize(zeroDim);
		button.setMaximumSize(zeroDim);
		return button;
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
		g.setColor(one);
		g.fillRect(0, 0, 20, 130);
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		g.setColor(two);
		g.translate(thumbBounds.x, thumbBounds.y);
		g.fillRect(0, 0, thumbBounds.width, thumbBounds.height);
		g.translate(-thumbBounds.x, -thumbBounds.y);
	}
}
