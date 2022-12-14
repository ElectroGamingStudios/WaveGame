package me.ElectronicsBoy.wavegame.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.ElectronicsBoy.GameEngine.Engine;
import me.ElectronicsBoy.GameEngine.gui.Button;
import me.ElectronicsBoy.GameEngine.gui.ButtonStyle;
import me.ElectronicsBoy.GameEngine.gui.GUIWindow;
import me.ElectronicsBoy.wavegame.Game;

public class PauseMenu extends GUIWindow {

	public PauseMenu(Engine engine) {
		super("PAUSE", engine);
	}

	@Override
	public void init() {
		addButton(new Button(190, 350, 230, 64, Color.WHITE, Color.WHITE, ButtonStyle.createStyle(new Font("arial", 1, 30), ButtonStyle.Rect), 190, 390, "Save and Quit", Color.WHITE, () -> {
			try {
				Game.inst.save.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Game.inst.reset();
			engine.sys.setState("MENU");
		}));
		addButton(new Button(190, 260, 230, 64, Color.WHITE, Color.WHITE, ButtonStyle.createStyle(new Font("arial", 1, 30), ButtonStyle.Rect), 260, 300, "Save", Color.WHITE, () -> {
			try {
				Game.inst.save.save();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}));
		addButton(new Button(190, 170, 230, 64, Color.WHITE, Color.WHITE, ButtonStyle.createStyle(new Font("arial", 1, 30), ButtonStyle.Rect), 260, 210, "Quit", Color.WHITE, () -> {
			Game.inst.reset();
			engine.sys.setState("MENU");
		}));
		addButton(new Button(190, 80, 230, 64, Color.WHITE, Color.WHITE, ButtonStyle.createStyle(new Font("arial", 1, 30), ButtonStyle.Rect), 230, 120, "Continue", Color.WHITE, () -> {
			engine.sys.setState("PLAY");
		}));
	}

	public void tickUI() {}
	public void renderUI(Graphics g) {
		g.setFont(new Font("arial", 1, 50));
		g.setColor(Color.RED);
		g.drawString("P", 220, 70);
		g.setColor(Color.BLUE);
		g.drawString("A", 250, 70);
		g.setColor(Color.CYAN);
		g.drawString("U", 280, 70);
		g.setColor(Color.CYAN);
		g.drawString("S", 320, 70);
		g.setColor(Color.GREEN);
		g.drawString("E", 350, 70);
	}
}
