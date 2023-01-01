package me.ElectronicsBoy.wavegame.save;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import me.ElectronicsBoy.GameEngine.binary.BinaryReader;
import me.ElectronicsBoy.GameEngine.binary.BinaryWriter;
import me.ElectronicsBoy.GameEngine.entity.Entity;
import me.ElectronicsBoy.wavegame.Game;
import me.ElectronicsBoy.wavegame.Main;
import me.ElectronicsBoy.wavegame.entities.BossEntity;
import me.ElectronicsBoy.wavegame.entities.PlayerEntity;
import me.ElectronicsBoy.wavegame.entities.TrailEntity;

public class Save {
	private Main main;
	public File binfile = new File("./res/save.bin");
	
	public Save(Main main) {
		this.main = main;
	}
	
	public void save() throws Exception {
		if(!binfile.exists()) binfile.createNewFile();
		BinaryWriter writer = new BinaryWriter(binfile);
		writer.writeBoolean(main.game.bossFight);
		if(main.game.bossFight) {
			writer.writeInt((int) main.game.boss.getX());
			writer.writeInt((int) main.game.boss.getY());
			writer.writeInt((int) main.game.boss.getVelX());
			writer.writeInt((int) main.game.boss.getVelY());
			writer.writeInt(main.game.boss.timer);
			writer.writeInt(main.game.boss.timer2);
		}
		writer.writeInt(Game.inst.level);
		writer.writeInt(Game.inst.levelsReset);
		writer.writeInt(Game.inst.levelsTillBoss);
		writer.writeInt(Game.inst.reset);
		writer.writeInt(Game.inst.scoreKeep);
		writer.writeInt(Game.inst.fullScore);
		writer.writeInt(Game.inst.afterScoreLevel);
		writer.writeInt((int) main.hud.health);
		for(Entity e : main.entitis) {
			if(!(e instanceof BossEntity)) {
				writer.writeString(e.getClass().getName());
				writer.writeInt((int) e.getX());
				writer.writeInt((int) e.getY());
				writer.writeInt((int) e.getVelX());
				writer.writeInt((int) e.getVelY());
				if(e instanceof TrailEntity) {
					writer.writeInt((int) ((TrailEntity)e).alpha);
					writer.writeInt((int) ((TrailEntity)e).life);
					writer.writeInt(e.getCol().getRGB());
				}
			}
		}
	}
	public List<Entity> read() throws Exception {
		List<Entity> entities = new ArrayList<Entity>();
		if(binfile.exists()) {
			BinaryReader reader = new BinaryReader(binfile);
			if(reader.readBoolean()) {
				BossEntity boss = new BossEntity(main);
				boss.setX(reader.readInt());
				boss.setY(reader.readInt());
				boss.setVelX(reader.readInt());
				boss.setVelY(reader.readInt());
				boss.timer = reader.readInt();
				boss.timer2 = reader.readInt();
				entities.add(boss);
				Game.inst.boss = boss;
				Game.inst.bossFight = true;
			}
			Game.inst.level = reader.readInt();
			Game.inst.levelsReset = reader.readInt();
			Game.inst.levelsTillBoss = reader.readInt();
			Game.inst.reset = reader.readInt();
			Game.inst.scoreKeep = reader.readInt();
			Game.inst.fullScore = reader.readInt();
			Game.inst.afterScoreLevel = reader.readInt();
			main.hud.health = reader.readInt();
			while(reader.available()) {
				Entity e;
				String s = reader.readString();
				System.out.println(s);
				switch(s) {
				case "me.ElectronicsBoy.wavegame.entities.PlayerEntity":
					e = new PlayerEntity(main);
					e.setX(reader.readInt());
					e.setY(reader.readInt());
					e.setVelX(reader.readInt());
					e.setVelY(reader.readInt());
					Game.inst.player = (PlayerEntity) e;
					entities.add(e);
					break;
				case "me.ElectronicsBoy.wavegame.entities.TrailEntity":
					e = new TrailEntity(null, 0, main);
					e.setX(reader.readInt());
					e.setY(reader.readInt());
					e.setVelX(reader.readInt());
					e.setVelY(reader.readInt());
					((TrailEntity)e).alpha = reader.readInt();
					((TrailEntity)e).life = reader.readInt();
					e.setCol(new Color(reader.readInt()));
					entities.add(e);
					break;
				default:
					e = instantiate(s, Entity.class);
					e.setX(reader.readInt());
					e.setY(reader.readInt());
					e.setVelX(reader.readInt());
					e.setVelY(reader.readInt());
					entities.add(e);
				}
			}
			return entities;
		}else {
			binfile.createNewFile();
			throw new FileNotFoundException();
		}
	}

	public <T> T instantiate(final String className, final Class<T> type){
	    try{
	        return type.cast(Class.forName(className).getConstructor().newInstance());
	    } catch(Exception e){
	        throw new IllegalStateException(e);
	    }
	}
}
