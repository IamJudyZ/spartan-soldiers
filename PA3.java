import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

class Spartan {
	String name;
	long score;
	int position;
	
	public Spartan(String n, int s, int p) {
		this.name = n;
		this.score = s;
		this.position = p;
	}
}

class minHeap {
	Spartan[] heap;
	int numOfElements;
	int size;
	
	public minHeap(int size) {
		this.heap = new Spartan[size];
		this.numOfElements = 0;
		this.size = size;
	}
	
	Spartan parent(Spartan soldier) {
		if (soldier.position <= 1) {
			return soldier;
		}
		return (heap[soldier.position/2-1]);
	}
	
	Spartan leftChild(Spartan soldier) {
		if (soldier.position*2 < size) {
			return (heap[soldier.position*2-1]);
		}
		return null;
	}
	
	Spartan rightChild(Spartan soldier) {
		if (soldier.position*2+1 < size) {
			return (heap[soldier.position*2]);
		}
		return null;
	}
	
	boolean isLeaf(Spartan soldier) {
		Spartan left = leftChild(soldier);
		Spartan right = rightChild(soldier);
		if (left == null && right == null) {
			return true;
		}
		return false;
	}
	
	void insert(Spartan soldier) {
		if (numOfElements >= size) {
			return;
		}
		heap[numOfElements] = soldier;
		numOfElements++;
		soldier.position = numOfElements;
		
		bubbleUp(soldier);
		
	}
	
	void bubbleUp(Spartan soldier) {
		if (numOfElements > 1) {
			Spartan current = soldier;
			Spartan currentParent = parent(current);
			
			while (current.score < currentParent.score) {
				swap(current, currentParent);
				currentParent = parent(current);
			}
		}
	}
	
	void swap(Spartan one, Spartan two) {
		Spartan obj = one;
		int temp = one.position;

		heap[one.position-1] = two;
		one.position = two.position;
		
		heap[two.position-1] = obj;
		two.position = temp;
		
		
		
	}
	
	void heapify(Spartan node) {
		if (node == null) {
			return;
		}
		
		if (isLeaf(node)) {
			return;
		}
		
		Spartan left = leftChild(node);
		Spartan right = rightChild(node);
		
		if (left != null && right == null) {
			if (node.score > left.score) {
				swap(node, left);
			}
		}
		
		else {
			if (node.score > left.score || node.score > right.score) {
				if (left.score <= right.score) {
					swap(node, left);
					heapify(node);
				}
				else {
					swap(node, right);
					heapify(node);
				}
			}
		}						
		
	}
	
	Spartan pop() {
		if (this.heap[0] != null) {
			Spartan least = this.heap[0];
			if (this.numOfElements > 0) {
				this.heap[0] = this.heap[numOfElements-1];
				this.heap[numOfElements-1] = null;
				this.heap[0].position = 1;
				heapify(this.heap[0]);
			}
			else {
				this.heap[0] = null;
			}
			this.numOfElements--;
			least.position = -1;
			return least;
		}
		return null;
	}
		
	
}

public class PA3 {
	
	static BufferedWriter output;

	public static void main(String[] args) throws Exception {
		HashMap<String, Spartan> map = new HashMap<String, Spartan>();
		Scanner scn = new Scanner(System.in);
		
		int size = Integer.parseInt(scn.nextLine());
		minHeap h = new minHeap(size);

		for (int i = 0; i<size; i++) {
			String[] user = scn.nextLine().split(" ");
			Spartan newSpartan = new Spartan(user[0], Integer.parseInt(user[1]), i+1);
			map.put(user[0], newSpartan);
			h.insert(newSpartan);
		}
		
		
		int lines = Integer.parseInt(scn.nextLine());
		
		for (int i = 0; i<lines; i++) {
			String[] user = scn.nextLine().split(" ");
			int first = Integer.parseInt(user[0]);
			if (first == 1) {
				Spartan soldier = map.get(user[1]);
				soldier.score += Integer.parseInt(user[2]);
				h.heapify(h.heap[soldier.position-1]);
				
			}
			else if (first == 2) {
				long k = Long.parseLong(user[1]);
				boolean keepGoing = true;
				while (keepGoing) {
					if (h.heap[0].score < k) {
						Spartan smallest = h.pop();
						map.remove(smallest.name);
					}
					else {
						keepGoing = false;
					}
				}
				
				
				System.out.println(h.numOfElements);
			}
			
		}
		
		
		
		
		
		scn.close();
		
	}
}
