package com.rja.projectzigzag;

import java.awt.Color;
import java.util.Random;

import com.ra4king.gameutils.Game;
import com.ra4king.gameutils.gameworld.GameWorld;
import com.rja.projectzigzag.entities.BackgroundStars;
import com.rja.projectzigzag.entities.BoostCharge;
import com.rja.projectzigzag.entities.Player;

/**
 * @author Roi Atalla
 */
public class GameScreen extends GameWorld {
	private long time = 0;
	private long lastChargeSpawnTime = 0;

	@Override
	public void init(Game game) {
		super.init(game);

		setBackground(Color.BLACK);
		
		add(0, new BackgroundStars());
		add(1, new Player());
		Random rand = Constants.rand;
		for(int i = 0; i < Constants.NUM_COLLECTABLES; i++) {
			add(1, new BoostCharge(rand.nextInt(getWidth()), rand.nextInt(getHeight()), rand.nextInt(5)));
		}
	}
	
	@Override
	public void update(long deltaTime) {
		super.update(deltaTime);
		
		time += deltaTime;
		
		Random rand = Constants.rand;
		if(time - lastChargeSpawnTime > Constants.CHARGE_SPAWN_TIME) {
			add(1, new BoostCharge(rand.nextInt(getWidth()), rand.nextInt(getHeight()), rand.nextInt(5)));
			lastChargeSpawnTime += Constants.CHARGE_SPAWN_TIME;
		}
	}
}
