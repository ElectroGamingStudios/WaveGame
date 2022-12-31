package me.ElectronicsBoy.wavegame.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.ElectronicsBoy.GameEngine.Engine;
import me.ElectronicsBoy.GameEngine.gui.Button;
import me.ElectronicsBoy.GameEngine.gui.ButtonStyle;
import me.ElectronicsBoy.GameEngine.gui.GUIWindow;
import me.ElectronicsBoy.wavegame.Game;

public class GameOverMenu extends GUIWindow {
	public GameOverMenu(Engine engine) {
		super("DEAD", engine);
	}

	public void init() {
		addButton(new Button(210, 350, 200, 64, Color.WHITE, Color.WHITE, ButtonStyle.createStyle(new Font("arial", 1, 30), ButtonStyle.Rect), 270, 390, "Menu", Color.WHITE, () -> {
			engine.sys.setState("MENU");
		}));
	}

	@Override
	public void tickUI() {}

	@Override
	public void renderUI(Graphics g) {
		g.setFont(new Font("arial", 1, 50));
		g.setColor(Color.WHITE);
		g.drawString("You Died", 200, 70);
		
		g.setFont(new Font("arial", 1, 30));
		g.drawString("You died. Score: " + Game.inst.fullScore, 155, 225);
	}
}
