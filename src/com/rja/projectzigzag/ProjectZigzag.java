package com.rja.projectzigzag;

import com.ra4king.gameutils.Game;

/**
 * @author Roi Atalla
 */
public class ProjectZigzag extends Game {
	public static void main(String[] args) {
		ProjectZigzag game = new ProjectZigzag();
		game.setupFrame("Project Zigzag", false);
		game.start();
	}
	
	private static final String MAIN_MENU_SCREEN = "MainMenu";
	
	public ProjectZigzag() {
		super(1280, 800);
	}
	
	@Override
	protected void initGame() {
		setScreen(MAIN_MENU_SCREEN, new MainMenuScreen());
	}
}
