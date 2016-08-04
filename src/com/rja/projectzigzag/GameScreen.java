package com.rja.projectzigzag;

import java.awt.Color;

import com.ra4king.gameutils.Game;
import com.ra4king.gameutils.gameworld.GameWorld;
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
	}
	
	@Override
	public void update(long deltaTime) {
		super.update(deltaTime);
	}
}
