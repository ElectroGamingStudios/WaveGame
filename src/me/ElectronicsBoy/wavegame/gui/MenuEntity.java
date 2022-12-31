package me.ElectronicsBoy.wavegame.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import me.ElectronicsBoy.GameEngine.Util.Util;
import me.ElectronicsBoy.GameEngine.entity.Entity;
import me.ElectronicsBoy.GameEngine.entity.EntityType;
import me.ElectronicsBoy.GameEngine.entity.MovementType;
import me.ElectronicsBoy.wavegame.Main;
import me.ElectronicsBoy.wavegame.entities.TrailEntity;

public class MenuEntity extends Entity {
	private Main main;
	
	public MenuEntity(Main main) {
		super(16, 16, new Random().nextInt(Util.WIDTH), new Random().nextInt(Util.WIDTH), EntityType.None, new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)), MovementType.Bounce, null);
		this.velX = (new Random().nextInt(14) + -7);
	    this.velY = (new Random().nextInt(14) + -7);
	    if (this.velX == 0.0F)
	      this.velX = 1.0F; 
	    if (this.velY == 0.0F)
	      this.velX = 1.0F; 
		this.main = main;
	}

	@Override
	public void tick() {
		super.move();
		main.handler.addObject(new TrailEntity(this, 0.05F, main));
	}

	@Override
	public void postRender(Graphics g, boolean useTexture) {
		g.setColor(getCol());
	    g.fillRect((int)x, (int)y, width, height);
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}

}
