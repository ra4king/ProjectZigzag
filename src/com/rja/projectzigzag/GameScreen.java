package com.rja.projectzigzag;

import java.awt.Color;

import com.ra4king.gameutils.Game;
import com.ra4king.gameutils.gameworld.GameWorld;
import com.rja.projectzigzag.entities.BoostCharge;
import com.rja.projectzigzag.entities.Player;

/**
 * @author Roi Atalla
 */
public class GameScreen extends GameWorld {
	@Override
	public void init(Game game) {
		super.init(game);
		
		setBackground(Color.BLACK);
		add(new Player());
		add(new BoostCharge(50, 50, 10));
		add(new BoostCharge(200, 10, 5));
		add(new BoostCharge(400, 100, 10));
	}
	
	@Override
	public void update(long deltaTime) {
		super.update(deltaTime);
	}
}
