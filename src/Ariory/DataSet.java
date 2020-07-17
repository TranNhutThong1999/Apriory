package Ariory;

import java.util.List;

public class DataSet {
private List<Transaction> transaction;

public DataSet(List<Transaction> transaction) {
	super();
	this.transaction = transaction;
}

public List<Transaction> getTransaction() {
	return transaction;
}

public void setTransaction(List<Transaction> transaction) {
	this.transaction = transaction;
}

}
