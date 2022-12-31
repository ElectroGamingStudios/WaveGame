package me.ElectronicsBoy.wavegame;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import me.ElectronicsBoy.GameEngine.Engine;
import me.ElectronicsBoy.GameEngine.GameStartupStage;
import me.ElectronicsBoy.GameEngine.entity.Entity;
import me.ElectronicsBoy.GameEngine.entity.Handler;
import me.ElectronicsBoy.GameEngine.gui.HUD;
import me.ElectronicsBoy.wavegame.entities.TrailEntity;
import me.ElectronicsBoy.wavegame.gui.GameOverMenu;
import me.ElectronicsBoy.wavegame.gui.HelpMenu;
import me.ElectronicsBoy.wavegame.gui.MainMenu;
import me.ElectronicsBoy.wavegame.gui.MenuEntity;
import me.ElectronicsBoy.wavegame.gui.PauseMenu;

public class Main extends Engine {
	private static final long serialVersionUID = -4318258672134694410L;
	
	public HUD hud;
	public Handler handler;
	public Game game;
	public List<Entity> entitis = new ArrayList<Entity>();

	private boolean renderPartical = true;
	private String lastState = "";
	private boolean alreadyDone = false;
	
	public Main() {
		super.run(this, "Wave Game", 60);
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
	public void init() {
		hud = new HUD(true);
		handler = new Handler();
		
		super.addWindow(new MainMenu(this));
		super.addWindow(new HelpMenu(this));
		super.addWindow(new GameOverMenu(this));
		super.addWindow(game = new Game(this));
		super.addWindow(new PauseMenu(this));
		
		super.initWindows(hud, null);
		
		super.stage = GameStartupStage.RUN;
		super.sys.setState("MENU");
	}
	
	public void postTick() {
		if(sys.getState().equals("MENU") || sys.getState().equals("HELP") || sys.getState().equals("DEAD") || sys.getState().equals("PAUSE")) {
			if(renderPartical == true && lastState == sys.getState()) {
				handler.clearAll();
				for (int i = 0; i < 20; i ++) 
					handler.addObject(new MenuEntity(this));
				renderPartical = false;
			}else if(renderPartical == false && lastState != sys.getState()) {
				renderPartical = true;
			}
			lastState = sys.getState();
		}
		handler.postTick();
	}
	public void postRender(Graphics g) {
		handler.postRender(g, false);
	}
	public void postHUDTick() {}
	public void messageReceivedClient(String message) {}
	public void messageReceivedServer(String message) {}
	public void onStateChange() {
		handler.object.removeIf((Entity a) -> a instanceof MenuEntity);
		handler.object.removeIf((Entity a) -> a instanceof TrailEntity);
		if(sys.getState().equals("PAUSE")) {
			handler.object.forEach((e) -> entitis.add(e));
			handler.clearAll();
			alreadyDone = false;
		}
		if(sys.getState().equals("PLAY") && (sys.getLastState() == null) ? false : sys.getLastState().equals("PAUSE") && !alreadyDone) {
			handler.clearAll();
			entitis.forEach((e) -> handler.addObject(e));
			entitis.removeIf((Entity a) -> true);
			alreadyDone = true;
		}
	}
	public void onESC() {
		if(sys.getState().equals("PLAY")) sys.setState("PAUSE");
		else if(sys.getState().equals("PAUSE")) sys.setState("PLAY");
	}
}
