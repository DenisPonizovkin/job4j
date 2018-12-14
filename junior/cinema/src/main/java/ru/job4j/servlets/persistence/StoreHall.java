package ru.job4j.servlets.persistence;

public interface StoreHall extends Store<Seat> {

	public Seat findSeatByRowNumber(int row, int number);
	
}
