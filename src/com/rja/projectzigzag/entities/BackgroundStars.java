package com.rja.projectzigzag.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.ra4king.gameutils.gameworld.GameComponent;
import com.ra4king.gameutils.gameworld.GameWorld;
import com.rja.projectzigzag.Constants;

/**
 * @author Roi Atalla
 */
public class BackgroundStars extends GameComponent {
	private List<Point2D.Double> stars;
	
	@Override
	public void init(GameWorld gameWorld) {
		super.init(gameWorld);
		
		stars = new ArrayList<>();
		Random rand = Constants.rand;
		for(int i = 0; i < 100; i++) {
			stars.add(new Point2D.Double(rand.nextInt(getParent().getWidth()), rand.nextInt(getParent().getHeight())));
		}
	}
	
	@Override
	public void update(long deltaTime) {
		stars.forEach(star -> {
			double screenY = star.y + getY();
			Random rand = Constants.rand;
			if(screenY < 0) {
				star.x = rand.nextInt(getParent().getWidth());
				star.y += getParent().getHeight();
			} else if(screenY >= getParent().getHeight()) {
				star.x = rand.nextInt(getParent().getWidth());
				star.y -= getParent().getHeight();
			}
		});
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		stars.forEach(star -> {
			double screenY = star.y + getY();
			g.fillOval((int)Math.round(star.x), (int)Math.round(screenY), 2, 2);
		});
	}
}
