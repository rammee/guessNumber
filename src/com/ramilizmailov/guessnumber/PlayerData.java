package com.ramilizmailov.guessnumber;

import java.io.Serializable;

public class PlayerData implements Comparable<PlayerData>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Integer trials;
	
	public PlayerData(String name, Integer score) {
		super();
		this.name = name;
		this.trials = score;
	}

	@Override
	public int compareTo(PlayerData o) {
		return this.trials.compareTo(o.trials);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTrials() {
		return trials;
	}

	public void setTrials(Integer trials) {
		this.trials = trials;
	}
	
}