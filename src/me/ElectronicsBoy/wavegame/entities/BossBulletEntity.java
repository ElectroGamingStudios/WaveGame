package me.ElectronicsBoy.wavegame.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import me.ElectronicsBoy.GameEngine.Util.Util;
import me.ElectronicsBoy.GameEngine.entity.Entity;
import me.ElectronicsBoy.GameEngine.entity.EntityType;
import me.ElectronicsBoy.GameEngine.entity.MovementType;
import me.ElectronicsBoy.wavegame.Main;

public class BossBulletEntity extends Entity {
	private Main main;
	
	public BossBulletEntity(float x, float y, Main main) {
		super(4, 4, x, y, EntityType.Enemy, Color.green, MovementType.Bounce, null);
		velX = new Random().nextInt(5 - -5) + -5;
		velY = 5;
		this.main = main;
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		if(y >= Util.HEIGHT) main.handler.removeObject(this);
		main.handler.addObject(new TrailEntity(this, 0.04f, main));
	}

	@Override
	public void postRender(Graphics g, boolean useTexture) {
		g.setColor(getCol());
		g.fillRect((int)x, (int)y, width, height);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int) y, 4, 4);
	}

}
