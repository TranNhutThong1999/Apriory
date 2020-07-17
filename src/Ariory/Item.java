package Ariory;

public class Item implements Comparable<Item> {
private String name;

public Item(String name) {
	super();
	this.name = name;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

@Override
public String toString() {
	return "Item [name=" + name + "]";
}

@Override
public int compareTo(Item arg0) {
	// TODO Auto-generated method stub
	return  this.getName().compareTo(arg0.getName());
}

}
