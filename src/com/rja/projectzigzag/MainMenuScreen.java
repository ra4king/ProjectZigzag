package com.rja.projectzigzag;

import java.awt.Graphics2D;

import com.ra4king.gameutils.BasicScreen;

/**
 * @author Roi Atalla
 */
public class MainMenuScreen extends BasicScreen {
	@Override
	public void update(long deltaTime) {
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawString("Hello, world", 50, 50);
	}
}
