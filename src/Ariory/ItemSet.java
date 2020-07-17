package Ariory;

import java.util.TreeSet;

public class ItemSet implements Comparable<ItemSet> {
	private TreeSet<Item> items;
	private int count;

	public ItemSet(TreeSet<Item> items, int count) {
		super();
		this.items = items;
		this.count = count;
	}

	public TreeSet<Item> getItems() {
		return items;
	}

	public void setItems(TreeSet<Item> items) {
		this.items = items;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "ItemSet [items=" + items + ", count=" + count + "]";
	}

	@Override
	public int compareTo(ItemSet a) {
		String name1 = "";
		for (Item item : this.items) {
			name1 += item.getName();
		}
		String name2 = "";
		for (Item item2 : a.getItems()) {
			name2 += item2.getName();
		}
		return name1.compareTo(name2);
	}
	

}
