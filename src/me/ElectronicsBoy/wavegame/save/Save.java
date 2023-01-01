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
			writer.writeFloat(main.game.boss.getX());
			writer.writeFloat(main.game.boss.getY());
			writer.writeFloat(main.game.boss.getVelX());
			writer.writeFloat(main.game.boss.getVelY());
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
		writer.writeFloat(main.hud.health);
		for(Entity e : main.entitis) {
			if(!(e instanceof BossEntity)) {
				System.out.println(e.getClass().getName());
				writer.writeString(e.getClass().getName());
				writer.writeFloat(e.getX());
				writer.writeFloat(e.getY());
				writer.writeFloat(e.getVelX());
				writer.writeFloat(e.getVelY());
				if(e instanceof TrailEntity) {
					writer.writeFloat(((TrailEntity)e).alpha);
					writer.writeFloat(((TrailEntity)e).life);
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
				boss.setX(reader.readFloat());
				boss.setY(reader.readFloat());
				boss.setVelX(reader.readFloat());
				boss.setVelY(reader.readFloat());
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
			main.hud.health = reader.readFloat();
			main.hud.updateValue(1, Integer.toString(Game.inst.level));
			
			while(reader.available()) {
				Entity e;
				String s = reader.readString();
				System.out.println(s);
				switch(s) {
				case "me.ElectronicsBoy.wavegame.entities.PlayerEntity":
					e = new PlayerEntity(main);
					e.setX(reader.readFloat());
					e.setY(reader.readFloat());
					e.setVelX(reader.readFloat());
					e.setVelY(reader.readFloat());
					Game.inst.player = (PlayerEntity) e;
					entities.add(e);
					break;
				case "me.ElectronicsBoy.wavegame.entities.TrailEntity":
					e = new TrailEntity(main);
					e.setX(reader.readFloat());
					e.setY(reader.readFloat());
					e.setVelX(reader.readFloat());
					e.setVelY(reader.readFloat());
					((TrailEntity)e).alpha = reader.readFloat();
					((TrailEntity)e).life = reader.readFloat();
					e.setCol(new Color(reader.readInt()));
					entities.add(e);
					break;
				default:
					e = instantiate(s, Entity.class);
					e.setX(reader.readFloat());
					e.setY(reader.readFloat());
					e.setVelX(reader.readFloat());
					e.setVelY(reader.readFloat());
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
