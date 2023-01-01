package me.ElectronicsBoy.wavegame.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import me.ElectronicsBoy.GameEngine.Util.Util;
import me.ElectronicsBoy.GameEngine.entity.Entity;
import me.ElectronicsBoy.GameEngine.entity.EntityType;
import me.ElectronicsBoy.GameEngine.entity.MovementType;
import me.ElectronicsBoy.wavegame.Game;
import me.ElectronicsBoy.wavegame.Main;

public class BossEntity extends Entity {
	public int timer = 80;
	public int timer2 = 50;
	
	private Main main;
	
	public BossEntity(Main main) {
		super(64, 64, (Util.WIDTH / 2) - 48, -120, EntityType.Enemy, null, MovementType.None, Game.inst.getImg(2, 1, 96, 96));
		velX = 0;
		velY = 1.5f;
		this.main = main;
	}

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		if(timer <= 0) velY = 0;
		else timer--;
		
		if(timer <= 0) timer2--;
		if(timer2 <= 0)
		{
			if(velX == 0) velX = 2;
			
			if(velX > 0)
				velX += 0.01f;
			else if(velX < 0)
				velX -= 0.01f;
			
			velX = Util.ClampUtil.clamp(velX,  -10, 10);
			
			int spawn = new Random().nextInt(10);
			if(spawn == 0) main.handler.addObject(new BossBulletEntity((int)x + 48, (int)y + 48, main));
		}
		
		if(x <= 0 || x >= Util.WIDTH - 96)velX *= -1;
	}

	@Override
	public void postRender(Graphics g, boolean useTexture) {
		g.drawImage(texture, (int)x, (int)y, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int) y, 96, 96);
	}

}
