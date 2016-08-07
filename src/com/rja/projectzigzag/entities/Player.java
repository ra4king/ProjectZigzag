package com.rja.projectzigzag.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.ra4king.gameutils.Input;
import com.ra4king.gameutils.gameworld.GameComponent;
import com.ra4king.gameutils.gameworld.GameWorld;

/**
 * @author Roi Atalla
 */
public class Player extends GameComponent {
	private static final double ACCELERATION = 2.5;
	private static final double GRAVITY = 1;
	private static final double BOOSTER_ACCELERATION = 2.5;

	private long time;
	private double velocity;
	
	private Booster booster = new Booster(BOOSTER_ACCELERATION);
	
	private enum DirectionStatus {
		UP, DOWN
	}

	private enum BoostStatus {
		OFF, ON
	}
	
	private DirectionStatus directionStatus = DirectionStatus.UP;
	private BoostStatus boostStatus = BoostStatus.OFF;
	
	public Player() {
		super(0, 0, 20, 60);
	}

	@Override
	public final void init(GameWorld screen) {
		super.init(screen);
		getParent().add(booster);
	}
	
	@Override
	public void show() {
		setX((getParent().getWidth() - getWidth()) / 2.0);
		setY((getParent().getHeight() - getHeight()) / 2.0);
	}
	
	@Override
	public void update(long deltaTime) {
		Input input = getParent().getGame().getInput();
		
		time += deltaTime;
		double fraction = deltaTime / 1e9;

		boostStatus = BoostStatus.OFF;
		if(input.isKeyDown(KeyEvent.VK_UP)) {
			directionStatus = DirectionStatus.UP;
		}
		if(input.isKeyDown(KeyEvent.VK_DOWN)) {
			directionStatus = DirectionStatus.DOWN;
		}
		if(input.isKeyDown(KeyEvent.VK_1)) {
			boostStatus = BoostStatus.ON;
			booster.setEnabled();
			booster.updateFuel(-fraction);
		}

		applyAcceleration(deltaTime);

		if(input.isKeyDown(KeyEvent.VK_RIGHT)) {
			setX(getX() + 2);
		}
		if(input.isKeyDown(KeyEvent.VK_LEFT)) {
			setX(getX() - 2);
		}

		double maxVelocity = getMaxVelocity();
		velocity = velocity > maxVelocity ? maxVelocity : velocity < -maxVelocity ? -maxVelocity: velocity;
		setY(getY() + velocity);
		
		int width = getParent().getGame().getWidth();
		int height = getParent().getGame().getHeight();
		if(getY() < 0 || getY() + getHeight() > height) {
			velocity = 0;
			setY(getY() < 0 ? 0 : getY() + getHeight() > height ? height - getHeight() : getY());
		}
		
		setX(getX() < 0 ? 0 : getX() + getWidth() > width ? width - getWidth() : getX());
		
		getParent().getEntities().stream().filter(entity -> entity instanceof BoostCharge && entity.intersects(this)).forEach(entity ->  {
			BoostCharge boost = (BoostCharge)entity;
			booster.updateFuel(boost.getBoostAmount());
			getParent().remove(boost);
		});
	}

	private void applyAcceleration(long deltaTime){
		double fraction = deltaTime / 1e9;
		velocity += getAcceleration() * fraction;
	}

	private double getAcceleration() {
		double acceleration = 0;
		double boosterAcceleration = 0;
		if(boostStatus == BoostStatus.ON) {
			boosterAcceleration = booster.getAcceleration();
		}
		if(directionStatus == DirectionStatus.UP) {
			acceleration += (ACCELERATION + boosterAcceleration);
		}
		if(directionStatus == DirectionStatus.DOWN) {
			acceleration -= (ACCELERATION + boosterAcceleration);
		}
		acceleration -= GRAVITY;
		return -acceleration;
	}

	private double getMaxVelocity() {
		return Math.sqrt(Math.abs(getAcceleration()));
	}
	
	@Override
	public void draw(Graphics2D g) {
		for(int i = 0; i < 20; i++) {
			int size = (int)Math.round(5.0 + (boostStatus == BoostStatus.ON ? 20.0 : 5.0) * Math.random());
			int x = (int)Math.round(getX() - size * 0.5 + (getWidth()) * Math.random());
			int y;
			double ydist = boostStatus == BoostStatus.ON ? 15.0 : 10.0;
			if(directionStatus == DirectionStatus.UP) {
				y = (int)Math.round(getY() + getHeight() - size * 0.5 + ydist * Math.random());
			} else {
				y = (int)Math.round(getY() - size * 0.5 - ydist * Math.random());
			}

			Color color = Color.ORANGE;
			for(int j = 0; j < (int)Math.round(5 * Math.random()); j++) {
				color = color.darker();
			}

			g.setColor(color);
			g.fillOval(x, y, size, size);
		}
		
		g.setColor(Color.WHITE);
		g.fillRect(getIntX(), getIntY(), getIntWidth(), getIntHeight());

		g.drawString("ACCELERATION: " + String.format("%.02f", getAcceleration()), 5, 25);
		g.drawString("MAX VELOCITY: " + String.format("%.02f", getMaxVelocity()), 5, 35);
		g.drawString("TIME: " + String.format("%.02f", time / 1e9), 5, 45);
	}
}
