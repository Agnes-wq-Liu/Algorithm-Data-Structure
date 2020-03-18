package assignment2;
//Agnes Liu, 260713093
public class Shelf {
	
	protected int height;
	protected int availableLength;
	protected int totalLength;
	protected Box firstBox;
	protected Box lastBox;

	public Shelf(int height, int totalLength){
		this.height = height;
		this.availableLength = totalLength;
		this.totalLength = totalLength;
		this.firstBox = null;
		this.lastBox = null;
	}
	
	protected void clear(){
		availableLength = totalLength;
		firstBox = null;
		lastBox = null;
	}
	
	public String print(){
		String result = "( " + height + " - " + availableLength + " ) : ";
		Box b = firstBox;
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
	/**
	 * Adds a box on the shelf. Here we assume that the box fits in height and length on the shelf.
	 * @param b
	 */
	public void addBox(Box b){
		//ADD YOUR CODE HERE
		if(b != null && this.availableLength >=b.length && this.height >=b.height) {
			if(lastBox == null) {
				lastBox = b;
				firstBox = b;
				firstBox.previous = null;
				lastBox.next = null;
			}else {
				Box newLast = b;
				lastBox.next = newLast;
				newLast.previous = lastBox;
				this.lastBox = newLast;
			}
			firstBox.previous = null;
			lastBox.next =null;
			this.availableLength=availableLength -b.length;
		}
	}
	
	/**
	 * If the box with the identifier is on the shelf, remove the box from the shelf and return that box.
	 * If not, do not do anything to the Shelf and return null.
	 * @param identifier
	 * @return
	 */
	public Box removeBox(String identifier){
		//ADD YOUR CODE HERE
		
		if(availableLength != totalLength) {//condition: when the shelf is not empty
			Box target = firstBox;
			if(firstBox!=null && !identifier.equals(firstBox.id)) {
				//1:exists firstBox and it's not the first that matches
				target = firstBox.next;
				Box prev = firstBox;
				while(target!=null && !identifier.equals(target.id)) {
					//1-1:there are more than 1 boxes on shelf
					prev = target;//loop forward
					target = target.next;//loop forward
				}
				if(target == null) {
					return null;
				}else {
					if(target == lastBox) {
						prev.next = null;
						this.lastBox = prev;
					}else {
						prev.next = target.next;
						target.next.previous = prev;
					}
					target.next = null;
					target.previous = null;//disconnect the target box
					this.availableLength = availableLength + target.length;
					return target;
				}
			}else if(firstBox !=null && identifier.equals(firstBox.id)){
				//exists firstBox and it is the wanted one
				if(firstBox == lastBox) {
				//if there's only 1 box
					target.next = null;
					target.previous = null;
					this.firstBox = null;
					this.lastBox = null;//set everything on shelf to be null
				}else {
				//if there's more than 1 box
					this.firstBox = target.next;
					this.firstBox.previous = null;
					target.next = null;
					target.previous = null;
				//set its previous as null, disconnect the target
				}
				this.availableLength = availableLength + target.length;
				return target;
			}
			return null;
		}else 
		return null;
	}

}
