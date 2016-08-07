package com.rja.projectzigzag.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.ra4king.gameutils.Input;
import com.ra4king.gameutils.gameworld.GameComponent;
import com.ra4king.gameutils.gameworld.GameWorld;
import com.rja.projectzigzag.Constants;

/**
 * @author Roi Atalla
 */
public class Player extends GameComponent {
	private static final double ACCELERATION = 2.5;
	private static final double BOOSTER_ACCELERATION = 2.5;

	private double deathProgress;
	private long time;
	private double lifeForce = 10;
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
		super(0, 0, 8, 20);
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
		if(deathProgress == 0) {
			Input input = getParent().getGame().getInput();

			time += deltaTime;
			double fraction = deltaTime / 1e9;

			lifeForce -= fraction;
			if(lifeForce < 0) {
				lifeForce = 0;
				deathProgress++;
			}

			boostStatus = BoostStatus.OFF;
			if(input.isKeyDown(KeyEvent.VK_UP)) {
				directionStatus = DirectionStatus.UP;
			}
			if(input.isKeyDown(KeyEvent.VK_DOWN)) {
				directionStatus = DirectionStatus.DOWN;
			}
			if(input.isKeyDown(KeyEvent.VK_1)) {
				if(booster.hasFuel()) {
					boostStatus = BoostStatus.ON;
					booster.setEnabled();
					booster.updateFuel(-fraction);
				}
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

			getParent().registerCollision(this, BoostCharge.class, (boost) ->  {
				booster.updateFuel(boost.getBoostAmount());
				lifeForce++;
				getParent().remove(boost);
			});

			if(getY() < Constants.OFFSET_LIMIT) {
				double delta = Constants.OFFSET_LIMIT - getY();
				getParent().getEntities().forEach(entity -> entity.setY(entity.getY() + delta));
			} else if(getY() > getParent().getHeight() - Constants.OFFSET_LIMIT) {
				double delta = getParent().getHeight() - Constants.OFFSET_LIMIT - getY();
				getParent().getEntities().forEach(entity -> entity.setY(entity.getY() + delta));
			}
		} else {
			deathProgress++;
		}
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
		acceleration -= Constants.GRAVITY;
		return -acceleration;
	}

	private double getMaxVelocity() {
		return Math.sqrt(Math.abs(getAcceleration()));
	}
	
	@Override
	public void draw(Graphics2D g) {
		if (deathProgress == 0) {
			for (int i = 0; i < 20; i++) {
				int size = (int) Math.round(5.0 + (boostStatus == BoostStatus.ON ? 20.0 : 5.0) * Math.random());
				int x = (int) Math.round(getX() - size * 0.5 + (getWidth()) * Math.random());
				int y;
				double ydist = boostStatus == BoostStatus.ON ? 15.0 : 10.0;
				if (directionStatus == DirectionStatus.UP) {
					y = (int) Math.round(getY() + getHeight() - size * 0.5 + ydist * Math.random());
				} else {
					y = (int) Math.round(getY() - size * 0.5 - ydist * Math.random());
				}

				Color color = Color.ORANGE;
				for (int j = 0; j < (int) Math.round(5 * Math.random()); j++) {
					color = color.darker();
				}

				g.setColor(color);
				g.fillOval(x, y, size, size);
			}

			g.setColor(Color.WHITE);
			g.fillRect(getIntX(), getIntY(), getIntWidth(), getIntHeight());
		} else if(deathProgress < 100) {
			for (int i = 0; i < 20; i++) {
				int size = (int) Math.round(5.0 + (-Math.abs((deathProgress / 10) - 5) + 5) * 3 *Math.random());
				int x = (int) (getX() + size * Math.random());
				int y = (int) (getY() + size * Math.random());

				Color color = Color.ORANGE;
				for (int j = 0; j < (int) Math.round(5 * Math.random()); j++) {
					color = color.darker();
				}

				g.setColor(color);
				g.fillOval(x, y, size, size);
			}
		}
		g.setColor(Color.WHITE);
		g.drawString("LIFE FORCE: " + String.format("%.02f", lifeForce), 5, 15);
		g.drawString("ACCELERATION: " + String.format("%.02f", getAcceleration()), 5, 35);
		g.drawString("MAX VELOCITY: " + String.format("%.02f", getMaxVelocity()), 5, 45);
		g.drawString("TIME: " + String.format("%.02f", time / 1e9), 5, 55);
	}
}
