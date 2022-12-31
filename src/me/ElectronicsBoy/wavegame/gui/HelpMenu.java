package me.ElectronicsBoy.wavegame.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.ElectronicsBoy.GameEngine.Engine;
import me.ElectronicsBoy.GameEngine.gui.Button;
import me.ElectronicsBoy.GameEngine.gui.ButtonStyle;
import me.ElectronicsBoy.GameEngine.gui.GUIWindow;

public class HelpMenu extends GUIWindow {
	
	public HelpMenu(Engine engine) {
		super("HELP", engine);
	}

	public void init() {
		addButton(new Button(210, 350, 200, 64, Color.WHITE, Color.WHITE, ButtonStyle.createStyle(new Font("arial", 1, 30), ButtonStyle.Rect), 270, 390, "Back", Color.WHITE, () -> {
			engine.sys.setState("MENU");
		}));
	}
	public void tickUI() {}
	public void renderUI(Graphics g) {
		g.setFont(new Font("arial", 1, 50));
		g.setColor(Color.WHITE);
		g.drawString("Help", 240, 70);
		
		g.setFont(new Font("arial", 1, 30));
		g.drawString("Use WASD keys to move player", 100, 225);
		g.drawString("And Dodge Enemies", 190, 275);
	}
}
