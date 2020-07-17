package Ariory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Ariory {
	private TreeSet<ItemSet> L;
	private DataSet D;
	private int minsupport;

	public Ariory(TreeSet<ItemSet> l, DataSet d, int minsupport) {
		super();
		L = l;
		D = d;
		this.minsupport = minsupport;
	}

	public void built() {
		TreeSet<ItemSet> L1 = find_frequent_1_Itemset();

		L.addAll(L1);
		TreeSet<ItemSet> Lk_1 = L;
		int k = 1;
		while (Lk_1.size() > 0) {
			k++;
			// tìm Ck và độ hổ trợ của nó
			TreeSet<ItemSet> Ck = aprory_gen(Lk_1, k);
			// dem so độ hổ trợ của từng ItemSet
			for (Transaction t : D.getTransaction()) {
				TreeSet<ItemSet> Ct = subset(Ck, t);
				for (ItemSet itemSet : Ct) {
					itemSet.setCount(itemSet.getCount() + 1);
				}
			}
			// tìm Lk thong qua minsup
			TreeSet<ItemSet> Lk = findLargerThanMinsupport(Ck);
			// thêm Lk vào L
			L.addAll(Lk);
			Lk_1 = Lk;

		}
		toString(L);
	}

	private void toString(Set<ItemSet> Lk) {
		for (ItemSet i : Lk) {
			for (Item as : i.getItems()) {
				System.out.println(as);
			}
			System.out.println("Độ hổ trợ:" + i.getCount());
			System.out.println("----------------------");
		}
	}

	private TreeSet<ItemSet> subset(TreeSet<ItemSet> Ck, Transaction t) {
		// TODO Auto-generated method stub
		TreeSet<ItemSet> result = new TreeSet<ItemSet>();
		for (ItemSet itemSet : Ck) {
			if (t.getListItem().containsAll(itemSet.getItems())) {
				result.add(itemSet);
			}
		}
		return result;
	}

	private TreeSet<ItemSet> aprory_gen(TreeSet<ItemSet> Lk_1, int k) {
		// TODO Auto-generated method stub
		// tạo Set để add item
		TreeSet<ItemSet> temp = new TreeSet<ItemSet>();
		// duyệt 2 vong for
		for (ItemSet i : Lk_1) {
			for (ItemSet j : Lk_1) {
				if (!i.getItems().containsAll(j.getItems())) {// loại bỏ các itemSet có item giống i
					TreeSet<Item> treeClone = new TreeSet<Item>(i.getItems());
					//treeClone.addAll(i.getItems());
					if (k == 2) {
						treeClone.addAll(j.getItems());
						ItemSet is = new ItemSet(treeClone, 0);
						if (!has_infrequent_subset(Lk_1, is)) {// kiểm tra itemSet có chửa phần tử con không phổ
							temp.add(is);// add vào temp
						}
					} else {
						TreeSet<Item> treeI = new TreeSet<Item>(i.getItems());
						TreeSet<Item> treeJ = new TreeSet<Item>(j.getItems());
						Item item_last_i =treeI.pollLast();
						Item item_last_j = treeJ.pollLast();

						if (treeI.containsAll(treeJ)) {
							treeClone.add(item_last_i);
							treeClone.add(item_last_j);
							ItemSet is = new ItemSet(treeClone, 0);
							if (!has_infrequent_subset(Lk_1, is)) {// kiểm tra itemSet có chửa phần tử con không phổ
																	// biến hay ko
								temp.add(is);// add vào temp
							}
						}
					}
				}
			}

		}
		return temp;
	}
//					tree.addAll(i.getItems());// add các item của ItemSet i vào TreeSet<Item> tree
//					a: for (Item j_index : j.getItems()) {// duyêt lần lượt các item của j
//						TreeSet<Item> treeClone = new TreeSet<Item>(tree);// tạo 1 TreeSet<Item> treeclone mới copy từ
//																			// TreeSet<Item> tree
//						treeClone.add(j_index);// add item vào TreeSet<Item> treeclone
//						if (treeClone.size() == k) {// nếu size của treeClone bằng K
//							if (!temp.isEmpty()) {
//								// kiểm tra các ItemSet có item đã có trong temp thì bỏ qua
//								for (ItemSet z : temp) {
//									if (z.getItems().containsAll(treeClone)) {
//										continue a;
//									}
//								}
//							}
//							// tạo mới 1 itemSet
//							ItemSet is = new ItemSet(treeClone, 0);
//							if (!has_infrequent_subset(Lk_1, is)) {// kiểm tra itemSet có chửa phần tử con không phổ
//																	// biến hay ko
//								temp.add(is);// add vào temp
//							}
//						}

	// đếm số lần xuất hiện cua itemSet trong transaction
//		for (Transaction d : D.getTransaction()) {
//			for (ItemSet is : temp) {
//				if (d.getListItem().containsAll(is.getItems())) {
//					is.setCount(is.getCount() + 1);
//				}
//			}
//		}

	private boolean has_infrequent_subset(Set<ItemSet> Lk_1, ItemSet itemset) {
		boolean check = true;
		for (Item i : itemset.getItems()) {// duyệt từng phần tử item trong itemSet
			check = true;
			TreeSet<Item> listClone = new TreeSet<Item>(itemset.getItems());// copy 1 TreeSet listClone từ itemset
			listClone.remove(i); // xóa item i cua listClone
			for (ItemSet itemL : Lk_1) { // duyet lk_1
				if (itemL.getItems().containsAll(listClone)) {// nếu Lk_1 có chứa thì check = false và break
					check = false;
					break;
				}
			}
			if (check == true) {
				return check;
			}
		}
		return check;

	}

	private TreeSet<ItemSet> findLargerThanMinsupport(TreeSet<ItemSet> c) {
		TreeSet<ItemSet> result = new TreeSet<ItemSet>();
		for (ItemSet i : c) {
			if (i.getCount() >= minsupport) {
				result.add(i);
			}
		}
		return result;
	}

	private TreeSet<ItemSet> find_frequent_1_Itemset() {
		// TODO Auto-generated method stub
//		TreeSet<ItemSet> L1 = new TreeSet<ItemSet>();
//		for(Transaction t :D.getTransaction()) {
//			for(Item i: t.getListItem());
//			if()
//		}
		
		Map<Item, Integer> map = new HashMap<Item, Integer>();
		for (Transaction t : D.getTransaction()) {
			for (Item i : t.getListItem()) {
				if (map.containsKey(i)) {
					map.put(i, map.get(i) + 1);
				} else {
					map.put(i, 1);
				}
			}
		}
		TreeSet<ItemSet> L1 = new TreeSet<ItemSet>();
		for (Map.Entry<Item, Integer> entry : map.entrySet()) {
			if (entry.getValue() >= minsupport) {
				TreeSet<Item> tree = new TreeSet<Item>();
				tree.add(entry.getKey());
				ItemSet item = new ItemSet(tree, entry.getValue());
				L1.add(item);
			}
		}
//		for(ItemSet i : L1) {
//			System.out.println(i.toString());
//		}
		return L1;
	}

	public static void main(String[] args) {

		Item i1 = new Item("A");
		Item i2 = new Item("B");
		Item i3 = new Item("C");
		Item i4 = new Item("D");
		Item i5 = new Item("E");

		List<Item> li1 = new ArrayList<Item>();
		li1.add(i1);
		li1.add(i3);
		List<Item> li2 = new ArrayList<Item>();
		li2.add(i1);
		li2.add(i2);
		li2.add(i3);
		li2.add(i4);
		li2.add(i5);
		List<Item> li3 = new ArrayList<Item>();
		li3.add(i3);
		li3.add(i4);
		li3.add(i5);
		List<Item> li4 = new ArrayList<Item>();
		li4.add(i1);
		li4.add(i2);
		li4.add(i3);
		List<Item> li5 = new ArrayList<Item>();
		li5.add(i4);
		li5.add(i5);
		List<Item> li6 = new ArrayList<Item>();
		li6.add(i1);
		li6.add(i3);
		li6.add(i4);

		List<Item> li7 = new ArrayList<Item>();
		li7.add(i1);
		li7.add(i3);
		li7.add(i4);

		List<Item> li8 = new ArrayList<Item>();
		li8.add(i3);
		li8.add(i5);

		List<Item> li9 = new ArrayList<Item>();
		li9.add(i2);
		li9.add(i4);

		List<Item> li10 = new ArrayList<Item>();
		li10.add(i1);
		li10.add(i2);
		li10.add(i3);

		Transaction t1 = new Transaction(li1);
		Transaction t2 = new Transaction(li2);
		Transaction t3 = new Transaction(li3);
		Transaction t4 = new Transaction(li4);
		Transaction t5 = new Transaction(li5);
		Transaction t6 = new Transaction(li6);
		Transaction t7 = new Transaction(li7);
		Transaction t8 = new Transaction(li8);
		Transaction t9 = new Transaction(li9);
		Transaction t10 = new Transaction(li10);

		List<Transaction> lt = new ArrayList<Transaction>();
		lt.add(t1);
		lt.add(t2);
		lt.add(t3);
		lt.add(t4);
		lt.add(t5);
		lt.add(t6);
		lt.add(t7);
		lt.add(t8);
		lt.add(t9);
		lt.add(t10);
		DataSet d = new DataSet(lt);

		TreeSet<ItemSet> l = new TreeSet<ItemSet>();
		Ariory a = new Ariory(l, d, 3);
		System.out.println("minsupport: " + 3);
		System.out.println();
		a.built();
//		TreeSet<ItemSet> Ck = new TreeSet<ItemSet>();
//
//		TreeSet<Item> tree = new TreeSet<Item>();
//		tree.add(new Item("A"));
//		Ck.add(new ItemSet(tree, 0));
////		TreeSet<Integer> set2 = new TreeSet<Integer>();
////		set2.add(set.first());
//
//		TreeSet<ItemSet> Ct = new TreeSet<ItemSet>();
//		TreeSet<Item> tree2 = new TreeSet<Item>();
//		tree.add(new Item("B"));
//		Ct.add(new ItemSet(tree, 0));
//		
//		for (ItemSet itemSet : Ck) {
//			Ct.add(itemSet);
//		}
//		for (ItemSet itemSet : Ct) {
//			itemSet.setCount(10);
//		}
//		System.out.println();
	}
}
