package me.ElectronicsBoy.wavegame.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.ElectronicsBoy.GameEngine.Engine;
import me.ElectronicsBoy.GameEngine.gui.Button;
import me.ElectronicsBoy.GameEngine.gui.ButtonStyle;
import me.ElectronicsBoy.GameEngine.gui.GUIWindow;
import me.ElectronicsBoy.wavegame.Game;

public class MainMenu extends GUIWindow {
	
	public MainMenu(Engine engine) {
		super("MENU", engine);
	}
	
	public void init() {
		addButton(new Button(210, 150, 200, 64, Color.BLUE, Color.BLUE, ButtonStyle.createStyle(new Font("arial", 1, 30), ButtonStyle.FullRect), 270, 190, "Play", Color.WHITE, () -> {
			engine.sys.setState("PLAYMENU");
			Game.start();
		}));
		addButton(new Button(210, 250, 200, 64, Color.CYAN, Color.CYAN, ButtonStyle.createStyle(new Font("arial", 1, 30), ButtonStyle.FullRect), 270, 290, "Help", Color.BLACK, () -> {
			engine.sys.setState("HELP");
		}));
	}
	public void tickUI() {}
	public void renderUI(Graphics g) {
		g.setFont(new Font("arial", 1, 50));
		g.setColor(Color.RED);
		g.drawString("W", 240, 70);
		g.setColor(Color.BLUE);
		g.drawString("A", 280, 70);
		g.setColor(Color.CYAN);
		g.drawString("V", 305, 70);
		g.setColor(Color.CYAN);
		g.drawString("E", 335, 70);
	}	
}
