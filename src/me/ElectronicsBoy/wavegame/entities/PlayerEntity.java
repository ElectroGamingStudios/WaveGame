package me.ElectronicsBoy.wavegame.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import me.ElectronicsBoy.GameEngine.Util.Util;
import me.ElectronicsBoy.GameEngine.entity.Entity;
import me.ElectronicsBoy.GameEngine.entity.EntityType;
import me.ElectronicsBoy.GameEngine.entity.MovementType;
import me.ElectronicsBoy.wavegame.Main;

public class PlayerEntity extends Entity {
	private Main main;
	
	public PlayerEntity(Main main) {
		super(32, 32, (Util.HEIGHT / 2) - 32, (Util.WIDTH / 2) - 32, EntityType.Player, Color.YELLOW, MovementType.WASD, null);
		setupMove();
		velX = 5;
		velY = 5;
		this.main = main;
	}

	@Override
	public void tick() {
		move();
		main.handler.addObject(new TrailEntity(this, 0.05F, main));
		
		for(Entity e : main.handler.object)
			if(e.getType() == EntityType.Enemy)
				if(e.getBounds().intersects(this.getBounds()))
					if(e instanceof BossBulletEntity)
						main.hud.health -= 4;
					else if(e instanceof BossBulletEntity)
						main.hud.health -= 16;
					else
						main.hud.health -= 2;
	}

	@Override
	public void postRender(Graphics g, boolean useTexture) {
		g.setColor(col);
		g.fillRect((int)x, (int)y, width, height);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}

}
