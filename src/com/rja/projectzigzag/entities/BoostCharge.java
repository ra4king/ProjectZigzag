package com.rja.projectzigzag.entities;

import java.awt.Color;
import java.awt.Graphics2D;

import com.ra4king.gameutils.gameworld.GameComponent;

/**
 * @author Roi Atalla
 */
public class BoostCharge extends GameComponent {
	private double boostAmount;
	
	public BoostCharge(double x, double y, double boostAmount) {
		super(x, y, 15, 15);
		this.boostAmount = boostAmount;
	}
	
	public double getBoostAmount() {
		return boostAmount;
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
