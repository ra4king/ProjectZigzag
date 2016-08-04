package com.rja.projectzigzag;

import com.ra4king.gameutils.Game;
import com.ra4king.gameutils.gui.Button;
import com.ra4king.gameutils.gui.MenuPage;
import com.ra4king.gameutils.gui.Menus;

/**
 * @author Roi Atalla
 */
public class ProjectZigzag extends Game {
	public static void main(String[] args) {
		ProjectZigzag game = new ProjectZigzag();
		game.setupFrame("Project Zigzag", false);
		game.start();
	}
	
	public static final String MENUS_SCREEN = "Menus";
	public static final String MAIN_MENU_SCREEN = "MainMenu";
	public static final String GAME_SCREEN = "Game";
	
	public ProjectZigzag() {
		super(1280, 800);
	}
	
	@Override
	protected void initGame() {
		MenuPage mainMenu = new MenuPage();
		mainMenu.add(new Button("Play!", 12, getWidth() / 2, getHeight() / 2, 5, 5, true, button -> {
			setScreen(GAME_SCREEN);
		}));
		
		Menus menus = new Menus();
		menus.addPage(MAIN_MENU_SCREEN, mainMenu);
		
		setScreen(MENUS_SCREEN, menus);
		addScreen(GAME_SCREEN, new GameScreen());
	}
}
