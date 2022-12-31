package me.ElectronicsBoy.wavegame.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import me.ElectronicsBoy.GameEngine.Util.Util;
import me.ElectronicsBoy.GameEngine.entity.Entity;
import me.ElectronicsBoy.GameEngine.entity.EntityType;
import me.ElectronicsBoy.GameEngine.entity.MovementType;
import me.ElectronicsBoy.wavegame.Game;

public class BasicEnemy extends Entity {

	public BasicEnemy() {
		super(16, 16, new Random().nextInt(Util.WIDTH - 50), new Random().nextInt(Util.HEIGHT - 50), EntityType.Enemy, null, MovementType.Bounce, Game.inst.getImg(1, 2, 16, 16));
		velX = 3;
		velY = 3;
	}

	@Override
	public void tick() {
		move();
	}

	@Override
	public void postRender(Graphics g, boolean useTexture) {
		g.drawImage(texture, (int)x, (int)y, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

}
