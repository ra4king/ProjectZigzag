package com.rja.projectzigzag;

import java.awt.Color;
import java.util.Random;

import com.ra4king.gameutils.Game;
import com.ra4king.gameutils.gameworld.GameWorld;
import com.rja.projectzigzag.entities.BoostCharge;
import com.rja.projectzigzag.entities.Player;

/**
 * @author Roi Atalla
 */
public class GameScreen extends GameWorld {
	private static final int NUM_COLLECTABLES = 14;

	@Override
	public void init(Game game) {
		super.init(game);

		setBackground(Color.BLACK);
		add(new Player());
		Random rand = new Random();
		for(int i = 0; i < NUM_COLLECTABLES; i++) {
			add(new BoostCharge(rand.nextInt(getWidth()), rand.nextInt(getHeight()), rand.nextInt(5)));
		}
	}
	
	@Override
	public void update(long deltaTime) {
		super.update(deltaTime);
	}
}
