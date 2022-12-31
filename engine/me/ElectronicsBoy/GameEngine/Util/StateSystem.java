package me.ElectronicsBoy.GameEngine.Util;

import me.ElectronicsBoy.GameEngine.Engine;

public class StateSystem {
	private String state = "INIT";
	
	private Engine engine;
	
	public StateSystem(Engine engine) {
		this.engine = engine;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String newState) {
		state = newState;
		engine.onStateChange();
	}
}
