package me.ElectronicsBoy.wavegame.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import me.ElectronicsBoy.GameEngine.Util.Util;
import me.ElectronicsBoy.GameEngine.entity.Entity;
import me.ElectronicsBoy.GameEngine.entity.EntityType;
import me.ElectronicsBoy.GameEngine.entity.MovementType;
import me.ElectronicsBoy.wavegame.Game;
import me.ElectronicsBoy.wavegame.Main;

public class SmartEnemy extends Entity {
	public PlayerEntity player;
	public Main main;
	
	public SmartEnemy(PlayerEntity player, Main main) {
		super(16, 16, new Random().nextInt(Util.WIDTH - 50), new Random().nextInt(Util.HEIGHT - 50), EntityType.Enemy, Color.PINK, MovementType.None, Game.inst.getImg(1, 2, 16, 16));
		velX = 3;
		velY = 3;
		this.player = player;
		this.main = main;
	}

	@Override
	public void tick() {
		float diffX = x - player.getX() - 8.0F;
	    float diffY = y - player.getY() - 8.0F;
	    float distance = (float)Math.sqrt(((x - player.getX()) * (x - player.getX()) + (y - player.getY()) * (y - player.getY())));
	    velX = (float)(-1.0D / distance * diffX);
	    velY = (float)(-1.0D / distance * diffY);
	    x += velX;
	    y += velY;
		main.handler.addObject(new TrailEntity(this, 0.02F, main)); 
	}

	@Override
	public void postRender(Graphics g, boolean useTexture) {
		g.setColor(getCol());
	    g.fillRect((int)x, (int)y, 16, 16);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

}
