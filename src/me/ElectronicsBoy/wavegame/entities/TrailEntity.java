package me.ElectronicsBoy.wavegame.entities;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import me.ElectronicsBoy.GameEngine.entity.Entity;
import me.ElectronicsBoy.GameEngine.entity.EntityType;
import me.ElectronicsBoy.GameEngine.entity.MovementType;
import me.ElectronicsBoy.wavegame.Main;

public class TrailEntity extends Entity {
	private float alpha = 1;
	
	private float life;
	
	private Main main;
	
	public TrailEntity(Entity mainEntity, float life, Main main) {
		super(mainEntity.getWidth(), mainEntity.getHeight(), mainEntity.getX(), mainEntity.getY(), EntityType.None, mainEntity.getCol(), MovementType.None, null);
		this.main = main;
		this.life = life;
	}

	public void tick() {
		if(alpha > life)
			alpha = alpha - (life - 0.000000000000000000001f);
		else
			main.handler.removeObject(this);
	}
	public void postRender(Graphics g, boolean useTexture) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(makeTransparent(alpha));
		
		g2d.setColor(getCol());
		g2d.fillRect((int)x, (int)y, width, height);
		
		g2d.setComposite(makeTransparent(1));
	}
	private AlphaComposite makeTransparent(float alpha) {
		int type = AlphaComposite.SRC_OVER;
		return(AlphaComposite.getInstance(type, alpha));
	}
	public Rectangle getBounds() {
		return null;
	}

}
