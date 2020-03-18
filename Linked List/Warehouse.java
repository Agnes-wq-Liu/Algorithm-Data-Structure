package assignment2;
//Agnes Liu, 260713093
public class Warehouse {
	protected Shelf[] storage;
	protected int nbShelves;
	public Box toShip;
	public UrgentBox toShipUrgently;
	static String problem = "problem encountered while performing the operation";
	static String noProblem = "operation was successfully carried out";
	
	public Warehouse(int n, int[] heights, int[] lengths){
		this.nbShelves = n;
		this.storage = new Shelf[n];
		for (int i = 0; i < n; i++){
			this.storage[i]= new Shelf(heights[i], lengths[i]);
		}
		this.toShip = null;
		this.toShipUrgently = null;
	}
	
	public String printShipping(){
		Box b = toShip;
		String result = "not urgent : ";
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n" + "should be already gone : ";
		b = toShipUrgently;
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
 	public String print(){
 		String result = "";
		for (int i = 0; i < nbShelves; i++){
			result += i + "-th shelf " + storage[i].print();
		}
		return result;
	}
	
 	public void clear(){
 		toShip = null;
 		toShipUrgently = null;
 		for (int i = 0; i < nbShelves ; i++){
 			storage[i].clear();
 		}
 	}
 	
 	/**
 	 * initiate the merge sort algorithm
 	 */
	public void sort(){
		mergeSort(0, nbShelves -1);
	}
	
	/**
	 * performs the induction step of the merge sort algorithm
	 * @param start
	 * @param end
	 */
	protected void mergeSort(int start, int end){
		//ADD YOUR CODE HERE
		int i = end - start;
		if(start<end && i>1) {
			int mid = (start+end)/2;
			mergeSort(start, mid);
			mergeSort(mid+1, end);
			merge(start, mid, end);
		}
	}
	
	/**
	 * performs the merge part of the merge sort algorithm
	 * @param start
	 * @param mid
	 * @param end
	 */
	protected void merge(int start, int mid, int end){
		//ADD YOUR CODE HERE
		int left = mid-start;
		int right = end-mid;
		Shelf[] L = new Shelf [left+1];
		Shelf [] R = new Shelf [right+1];
		for(int i=0;i<left;i++) {
			L[i]=storage[start +i ];
		}
		for(int j=0;j<right;j++) {
			R[j]=storage[mid + j];
		}
		L[left]=new Shelf (1001,1001);
		R[right]=new Shelf (1001,1001);
		Shelf[]array = new Shelf[end-start];
		int i = 0;
		int j = 0;
		while(i<left && j<right) {
			for(int k=start; k<end; k++) {
				if(L[i].height<=R[j].height && L[i].totalLength <=R[j].totalLength) {
					array[k]=L[i];
					i++;
				}else {
					array[k]=R[j];
					j++;
				}
			}
		}
		for(int m=start;m<end;m++) {
			this.storage[m]=array[m-start];
		}
		
	}
	
	/**
	 * Adds a box is the smallest possible shelf where there is room available.
	 * Here we assume that there is at least one shelf (i.e. nbShelves >0)
	 * @param b
	 * @return problem or noProblem
	 */
	public String addBox (Box b){
		//ADD YOUR CODE HERE
		Shelf smallest = storage[0];
		boolean prob = false;
		if(b != null) {
		for(int i=0;i<nbShelves; i++) {
			int h =storage[i].height;
			int l = storage[i].availableLength;
			if(h>=b.height && l>=b.length) {
				smallest = storage[i];
				for(int j=0;j<nbShelves;j++) {
					int sth = storage[j].height;
					int stl = storage[j].availableLength;
					if(sth>=b.height && sth < h && stl >=b.length) {
						smallest = storage[j];
						i=nbShelves;
						prob = true;
					}
				}
			}
		}
		}
		if(prob==true) {
			smallest.addBox(b);
			return noProblem;
		}else {
			return problem;
		}
	}
	
	/**
	 * Adds a box to its corresponding shipping list and updates all the fields
	 * @param b
	 * @return problem or noProblem
	 */
	public String addToShip (Box b){
		//ADD YOUR CODE HERE
		if(b==null) {
			return problem;
		}else {
			Box ref = new Box(1000, 1000, "box ref");
			ref.next = toShip;
			UrgentBox urgeRef = new UrgentBox(1000, 1000, "urgebox ref");
			urgeRef.next = toShipUrgently;
			if(b instanceof UrgentBox) {
				if(toShipUrgently ==null) {
					urgeRef.next = (UrgentBox)b;
					b.previous = 
					toShipUrgently = (UrgentBox)b;
				}else {
					toShipUrgently.previous = urgeRef;
					urgeRef = (UrgentBox)b;
					b.previous = urgeRef;
					b.next = toShipUrgently;
					toShipUrgently.previous = b;
					this.toShipUrgently = urgeRef;
				}
			}else {
				if(toShip ==null) {
					toShip = b;
				}else {
					toShip.previous = ref;
					ref.next = b;
					b.previous = ref;
					b.next = toShip;
					toShip.previous = b;
					this.toShip = b;
				}
			}
			return noProblem;
		}
	}
	
	/**
	 * Find a box with the identifier (if it exists)
	 * Remove the box from its corresponding shelf
	 * Add it to its corresponding shipping list
	 * @param identifier
	 * @return problem or noProblem
	 */
	public String shipBox (String identifier){
		//ADD YOUR CODE HERE
		String s = problem;
		for(int i=0;i<nbShelves; i++) {
			//loop the shelves
			if(this.storage[i].firstBox!=null) {
				Box m;
					m=storage[i].removeBox(identifier);
					if(m!=null) {
					s = addToShip(m);
					/*try {
						m = null;
					}catch(Exception e) {
						s = problem;
						continue;
					}
					if(m.next != null) {
						m = m.next;
					}*/
				}
		}
		}
			return s;
		//return "";
	}
	
	/**
	 * if there is a better shelf for the box, moves the box to the optimal shelf.
	 * If there are none, do not do anything
	 * @param b
	 * @param position
	 */
	public void moveOneBox (Box b, int position){
		//ADD YOUR CODE HERE
		for(int i=0;i<storage.length;i++) {
			int h = storage[i].height;
			int l = storage[i].availableLength;
			if(h >= b.height && h <storage[position].height && l >=b.length) {
					storage[position].removeBox(b.id);
					storage[i].addBox(b);
					i = storage.length;
			}
		}
	}
	
	/**
	 * reorganize the entire warehouse : start with smaller shelves and first box on each shelf.
	 */

	public void reorganize (){
		//ADD YOUR CODE HERE
		Box target = null;
		for(int j=0; j<storage.length;j++) {
			if(storage[j].firstBox!=null) {
				target = storage[j].firstBox;
				while(target !=null) {
					if(target.next != null) {
						Box next = target.next;
						moveOneBox(target, j);
						target = next;
					}else {
						moveOneBox(target,j);
						target = null;
					}
				}
			}
		}
			
	}


}
