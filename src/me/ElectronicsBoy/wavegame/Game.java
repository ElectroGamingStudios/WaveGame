package me.ElectronicsBoy.wavegame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.ElectronicsBoy.GameEngine.Engine;
import me.ElectronicsBoy.GameEngine.SpriteSheet.BufferedImageLoader;
import me.ElectronicsBoy.GameEngine.SpriteSheet.SpriteSheet;
import me.ElectronicsBoy.GameEngine.gui.GUIWindow;
import me.ElectronicsBoy.GameEngine.gui.HUDPostTick;
import me.ElectronicsBoy.wavegame.entities.BasicEnemy;
import me.ElectronicsBoy.wavegame.entities.BossEntity;
import me.ElectronicsBoy.wavegame.entities.FastEnemy;
import me.ElectronicsBoy.wavegame.entities.PlayerEntity;
import me.ElectronicsBoy.wavegame.entities.SmartEnemy;
import me.ElectronicsBoy.wavegame.save.Save;

public class Game extends GUIWindow implements HUDPostTick {
	private static boolean gameStart = false;
	public static Game inst;
	private SpriteSheet spriteSheet = null;
	
	public int levelsTillBoss = 0;
	public int levelsReset = 0;
	public int scoreKeep = 0;
	public int reset = 0;
	public int level = 1;
	public int fullScore = 0;
	public PlayerEntity player;
	public boolean hudReady = false;
	
	public int afterScoreLevel = 100;
	public boolean bossFight;
	
	public BossEntity boss;
	public Save save;
	public Main main;
	
	public boolean useSave = true;
	public boolean inited = false;
	
	public Game(Engine engine) {
		super("PLAY", engine);
		inst = this;
		save = new Save((Main)engine);
		this.main = (Main) engine;
	}

	@Override
	public void init() {
		spriteSheet = new SpriteSheet(new BufferedImageLoader().loadImage("/ss.png"));
	}

	@Override
	public void tickUI() {
		if(gameStart) {
			inited = true;
			((Main)engine).handler.clearAll();
			try {
				if(useSave) {
					save.read().forEach((e) -> { ((Main)engine).handler.addObject(e); });
					hudReady = true;
				}else {
					((Main)engine).handler.addObject(new BasicEnemy());
					player = new PlayerEntity(((Main)engine));
					((Main)engine).handler.addObject(player);
				}
			} catch (Exception e) {
				if(save.binfile.exists()) save.binfile.delete();
				((Main)engine).handler.addObject(new BasicEnemy());
				player = new PlayerEntity(((Main)engine));
				((Main)engine).handler.addObject(player);
				e.printStackTrace();
			}
			gameStart = false;
			if(!hudReady) {
				hud.addRenderValue("Level", "0");
				hud.addRenderValue("Score", "0");
				hudReady = true;
			}
		}
		
		if(engine.sys.getState().equals("PLAY")) {
			hud.updateValue(2, Integer.toString(scoreKeep++));
			fullScore++;
			if(scoreKeep >= afterScoreLevel) {
				scoreKeep = 0;
				hud.updateValue(1, Integer.toString(level++));
				levelsReset++;
				levelsTillBoss++;
				if(levelsTillBoss == 10) {
					bossFight = true;
					levelsTillBoss = 0;
					levelsReset = 0;
					reset = 1;
					((Main)engine).handler.clearEnemies();
					((Main)engine).handler.addObject(boss = new BossEntity(((Main)engine)));
					afterScoreLevel += 50;
				}
				
				if(levelsReset == 4 && reset == 1) {
					bossFight = false;
					((Main)engine).handler.clearEnemies();
					((Main)engine).handler.addObject(new BasicEnemy());
				}else if(levelsReset == 4 && reset == 0) 
					((Main)engine).handler.addObject(new BasicEnemy());
				else if(levelsReset == 6)
					((Main)engine).handler.addObject(new FastEnemy());
				else if(levelsReset == 8)
					((Main)engine).handler.addObject(new SmartEnemy(player, ((Main)engine)));
			}
			if(hud.health == 0) {
				((Main)engine).handler.clearAll();
				levelsTillBoss = 0;
				levelsReset = 0;
				scoreKeep = 0;
				reset = 0;
				level = 1;
				afterScoreLevel = 100;
				hud.health = 100;
				hud.updateValue(1, "0");
				hud.updateValue(2, "0");
				engine.sys.setState("DEAD");
			}
		}
		
		hud.tick(this);
	}

	@Override
	public void renderUI(Graphics g) {
		hud.render(g);
	}
	
	public static void start() {
		gameStart = true;
	}

	public BufferedImage getImg(int i, int j, int k, int l) {
		return spriteSheet.grabImage(i, j, k, l);
	}

	@Override
	public void postHUDTick() {}
	public void reset() {
		levelsTillBoss = 0;
		levelsReset = 0;
		scoreKeep = 0;
		reset = 0;
		level = 1;
		fullScore = 0;
		player = null;
		hudReady = false;
		
		afterScoreLevel = 100;
		bossFight = false;
		
		boss = null;
		hud.health = 100;
		hud.removeAll();
		
//		inited = false;
	}
}
