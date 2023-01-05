package me.ElectronicsBoy.wavegame.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.ElectronicsBoy.GameEngine.Engine;
import me.ElectronicsBoy.GameEngine.gui.Button;
import me.ElectronicsBoy.GameEngine.gui.ButtonStyle;
import me.ElectronicsBoy.GameEngine.gui.GUIWindow;
import me.ElectronicsBoy.wavegame.Game;

public class SaveMenu extends GUIWindow {
	public SaveMenu(Engine engine) {
		super("PLAYMENU", engine);
	}

	@Override
	public void init() {
		addButton(new Button(190, 170, 230, 64, Color.WHITE, Color.WHITE, ButtonStyle.createStyle(new Font("arial", 1, 30), ButtonStyle.Rect), 230, 210, "Use save", Color.WHITE, () -> {
			Game.inst.useSave = true;
			engine.sys.setState("PLAY");
		}));
		addButton(new Button(190, 260, 230, 64, Color.WHITE, Color.WHITE, ButtonStyle.createStyle(new Font("arial", 1, 30), ButtonStyle.Rect), 190, 300, "Don't use Save", Color.WHITE, () -> {
			Game.inst.useSave = false;
			engine.sys.setState("PLAY");
		}));
		addButton(new Button(190, 350, 230, 64, Color.WHITE, Color.WHITE, ButtonStyle.createStyle(new Font("arial", 1, 30), ButtonStyle.Rect), 205, 390, "Delete Save", Color.WHITE, () -> {
			Game.inst.save.binfile.delete();
			Game.inst.useSave = false;
			engine.sys.setState("PLAY");
		}));
	}

	@Override
	public void tickUI() {
		if(!Game.inst.save.binfile.exists()) {
			Game.inst.useSave = false;
			engine.sys.setState("PLAY");
		}
	}

	@Override
	public void renderUI(Graphics g) {
		g.setFont(new Font("arial", 1, 50));
		g.setColor(Color.RED);
		g.drawString("S", 240, 70);
		g.setColor(Color.BLUE);
		g.drawString("A", 270, 70);
		g.setColor(Color.CYAN);
		g.drawString("V", 305 - 10, 70);
		g.setColor(Color.CYAN);
		g.drawString("E", 335 - 10, 70);
	}
}
