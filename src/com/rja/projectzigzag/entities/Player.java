package com.rja.projectzigzag.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.ra4king.gameutils.Input;
import com.ra4king.gameutils.gameworld.GameComponent;

/**
 * @author Roi Atalla
 */
public class Player extends GameComponent {
	private static final double MAX_VELOCITY = 5;
	private static final double ACCELERATION = -3;
	private static final double GRAVITY = 1;
	
	private double velocity;
	
	private List<BoostCharge> boostOwned = new ArrayList<>();
	
	private enum BoostStatus {
		NO_BOOST, BOOST_UP, BOOST_DOWN
	}
	
	private BoostStatus boostStatus = BoostStatus.NO_BOOST;
	
	public Player() {
		super(0, 0, 20, 60);
	}
	
	@Override
	public void show() {
		setX((getParent().getGame().getWidth() - getWidth()) / 2.0);
		setY((getParent().getGame().getHeight() - getHeight()) / 2.0);
	}
	
	@Override
	public void update(long deltaTime) {
		Input input = getParent().getGame().getInput();
		
		double fraction = deltaTime / 1e9;
		
		double acceleration = ACCELERATION;
		acceleration -= boostOwned.stream().mapToDouble(BoostCharge::getBoostValue).sum();
		
		boostStatus = BoostStatus.NO_BOOST;
		if(input.isKeyDown(KeyEvent.VK_UP)) {
			velocity += acceleration * fraction;
			boostStatus = BoostStatus.BOOST_UP;
		}
		if(input.isKeyDown(KeyEvent.VK_DOWN)) {
			velocity -= acceleration * fraction;
			boostStatus = BoostStatus.BOOST_DOWN;
		}

		calculateBoostEffect(deltaTime);

		if(input.isKeyDown(KeyEvent.VK_RIGHT)) {
			setX(getX() + 2);
		}
		if(input.isKeyDown(KeyEvent.VK_LEFT)) {
			setX(getX() - 2);
		}

		velocity = velocity > MAX_VELOCITY ? MAX_VELOCITY : velocity < -MAX_VELOCITY ? -MAX_VELOCITY : velocity;
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
			boostOwned.add(boost);
			getParent().remove(boost);
		});
	}

	private void calculateBoostEffect(long deltaTime){
		double fraction = deltaTime / 1e9;

		if(boostStatus == BoostStatus.BOOST_UP) {
			velocity += ACCELERATION * fraction;
		}
		if(boostStatus == BoostStatus.BOOST_DOWN) {
			velocity -= ACCELERATION * fraction;
		}
		velocity += GRAVITY * fraction;
	}
	
	@Override
	public void draw(Graphics2D g) {
		if(boostStatus != BoostStatus.NO_BOOST) {
			for(int i = 0; i < 20; i++) {
				int size = (int)Math.round(5.0 + 20.0 * Math.random());
				int x = (int)Math.round(getX() - size * 0.5 + (getWidth()) * Math.random());
				int y;
				if(boostStatus == BoostStatus.BOOST_UP) {
					y = (int)Math.round(getY() + getHeight() - size * 0.5 + 15.0 * Math.random());
				} else {
					y = (int)Math.round(getY() - 5.0 - 15.0 * Math.random());
				}
				
				Color color = Color.ORANGE;
				for(int j = 0; j < (int)Math.round(5 * Math.random()); j++) {
					color = color.darker();
				}
				
				g.setColor(color);
				g.fillOval(x, y, size, size);
			}
		}
		
		g.setColor(Color.WHITE);
		g.fillRect(getIntX(), getIntY(), getIntWidth(), getIntHeight());
	}
}
