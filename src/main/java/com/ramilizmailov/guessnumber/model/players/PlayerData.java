package com.ramilizmailov.guessnumber.model.players;

import java.io.Serializable;
import java.util.Objects;

public class PlayerData implements Comparable<PlayerData>, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Integer efforts;
	
	public PlayerData(String name, Integer score) {
		super();
		Objects.requireNonNull(name);
		Objects.requireNonNull(score);
		name = name.trim();
		if (name.length() == 0) {
			throw new IllegalArgumentException("Name should not be empty");
		}
		this.name = name;
		this.efforts = score;
	}

	@Override
	public int compareTo(PlayerData o) {
		return this.efforts.compareTo(o.efforts);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getEfforts() {
		return efforts;
	}

	public void setEfforts(Integer efforts) {
		this.efforts = efforts;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		PlayerData that = (PlayerData) o;

		if (!name.toLowerCase().equals(that.name.toLowerCase())) return false;
		return efforts.equals(that.efforts);

	}

	@Override
	public int hashCode() {
		int result = name.toLowerCase().hashCode();
		result = 31 * result + efforts.hashCode();
		return result;
	}
}