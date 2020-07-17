package Ariory;

import java.util.List;

public class Transaction {
	private List<Item> listItem;

	public Transaction(List<Item> listItem) {
		super();
		this.listItem = listItem;
	}

	public List<Item> getListItem() {
		return listItem;
	}

	public void setListItem(List<Item> listItem) {
		this.listItem = listItem;
	}

	@Override
	public String toString() {
		return "Transaction [listItem=" + listItem + "]";
	}
	
}
