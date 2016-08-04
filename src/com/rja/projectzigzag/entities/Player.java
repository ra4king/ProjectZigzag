package com.rja.projectzigzag.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.ra4king.gameutils.Entity;
import com.ra4king.gameutils.Input;

/**
 * @author Roi Atalla
 */
public class Player extends Entity {
	private double velocity;
	private static final double ACCELERATION = 20;
	private static final double GRAVITY = -10;
	
	public Player() {
		super(200, 50, 60, 20);
	}
	
	@Override
	public void update(long deltaTime) {
		Input input = getParent().getGame().getInput();
		
		double fraction = deltaTime / 1e9;
		
		if(input.isKeyDown(KeyEvent.VK_RIGHT)) {
			velocity += ACCELERATION * fraction;
		}
		if(input.isKeyDown(KeyEvent.VK_LEFT)) {
			velocity -= ACCELERATION * fraction;
		}
		
		if(input.isKeyDown(KeyEvent.VK_DOWN)) {
			setY(getY() + 2);
		}
		if(input.isKeyDown(KeyEvent.VK_UP)) {
			setY(getY() - 2);
		}
		
		velocity += GRAVITY * fraction;
		setX(getX() + velocity);
		
		int width = getParent().getGame().getWidth();
		int height = getParent().getGame().getHeight();
		if(getX() < 0 || getX() + getWidth() > width) {
			velocity = 0;
			setX(getX() < 0 ? 0 : getX() + getWidth() > width ? width - getWidth() : getX());
		}
		
		setY(getY() < 0 ? 0 : getY() + getHeight() > height ? height - getHeight() : getY());
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(getIntX(), getIntY(), getIntWidth(), getIntHeight());
	}
}
