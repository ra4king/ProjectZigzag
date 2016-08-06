package com.rja.projectzigzag.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.ra4king.gameutils.gameworld.GameComponent;

/**
 * @author Roi Atalla
 */
public class BoostCharge extends GameComponent {
	private double boostValue;
	
	public BoostCharge(double x, double y, double boostValue) {
		super(x, y, 15, 15);
		this.boostValue = boostValue;
	}
	
	public double getBoostValue() {
		return boostValue;
	}
	
	@Override
	public void update(long deltaTime) {
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.YELLOW);
		g.fillRect(getIntX(), getIntY(), getIntWidth(), getIntHeight());
	}
}
