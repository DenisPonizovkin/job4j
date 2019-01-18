package ru.job4j.servlets.persistence;

import java.util.Objects;

public class Account {

	private int id;
	private String name;
	private String phone;
	private int seatId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	@Override
	public boolean equals(Object o) {
	    boolean eq = false;
		if (this == o) {
			eq = true;
		} else if (o == null || getClass() != o.getClass()) {
			eq = false;
		} else {
			Account account = (Account) o;
			eq = id == account.id
					&& seatId == account.seatId
					&& Objects.equals(name, account.name)
					&& Objects.equals(phone, account.phone);
		}
		return eq;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, phone, seatId);
	}
}
