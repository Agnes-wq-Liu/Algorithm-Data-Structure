package assignment3;
//Agnes Liu 260713093
//discussed with Yichuan Zhang, Yuhao Wang, Henri Mourant
public class Building {
	OneBuilding data;
	Building older;
	Building same;
	Building younger;
	
	public Building(OneBuilding data){
		this.data = data;
		this.older = null;
		this.same = null;
		this.younger = null;
	}
	
	public String toString(){
		String result = this.data.toString() + "\n";
		if (this.older != null){
			result += "older than " + this.data.toString() + " :\n";
			result += this.older.toString();
		}
		if (this.same != null){
			result += "same age as " + this.data.toString() + " :\n";
			result += this.same.toString();
		}
		if (this.younger != null){
			result += "younger than " + this.data.toString() + " :\n";
			result += this.younger.toString();
		}
		return result;
	}
	
	public Building addBuilding (OneBuilding b){
		// ADD YOUR CODE HERE
		if(b.yearOfConstruction ==this.data.yearOfConstruction) {
			if(b.height > this.data.height) {//if same year and new building is taller
				OneBuilding temp  = this.data; //store temp version of old root 
				this.data = b; 
				if(this.same ==null) {//update "same" branch with old root
					this.same = new Building(temp);
				}else {
					this.same.addBuilding(temp);
				}
			}else {//update "same" branch with b
				if(this.same == null) {
					this.same = new Building(b);
				}else {
					this.same.addBuilding(b);
				}
			}
		}else if(b.yearOfConstruction < this.data.yearOfConstruction){
			if(this.older == null) {
				this.older = new Building(b);
			}else {
				this.older.addBuilding(b);
			}
		}else {
			if(this.younger ==null) {
				this.younger = new Building(b);
			}else {
				this.younger.addBuilding(b);
			}
		}
		return this; 
	}
	
	public Building addBuildings (Building b){
		// ADD YOUR CODE HERE
		//code for data explicitly and old, same, young by order
		if(b.data !=null) {
			//Building newBuilt = this;
			this.addBuilding(b.data);//here
			if(b.older!=null) {
				if(b.older.data !=null) {
					this.addBuildings(b.older);//here
				}
			}
			if(b.same!=null) {
				if(b.same.data !=null) {
					this.addBuildings(b.same);//
				}
			}
			if(b.younger !=null) {
				if(b.younger.data!=null) {
					this.addBuildings(b.younger);//
				}
			}
		}
		return this;
	}

	public Building removeBuilding (OneBuilding b){
		// ADD YOUR CODE HERE
		if(this.older!=null && b.equals(this.older.data)) {
			if(this.older.older ==null && this.older.same==null&& this.older.younger==null) {
				this.older = null;
			}else {
				this.older = this.older.removeBuilding(b);
			}
			
		}else if(this.same!=null && b.equals(this.same.data)) {
			if(this.same.older ==null && this.same.younger==null && this.same.same ==null) {
				this.same = null;
			}else {
				this.same = this.same.removeBuilding(b);
			}
			
		}else if(this.younger !=null && b.equals(this.younger.data)){
			if(this.younger.older ==null && this.younger.same==null&& this.younger.younger==null) {
				this.younger = null;
			}else {
				this.younger= this.younger.removeBuilding(b);
			}
		
		}else if (b.equals(data)) {
			if(same !=null) {
				this.data = this.same.data;
				this.same = this.same.same;
			}else {
				if(this.older !=null) {
					if(this.younger != null) {
					this.older= this.addBuildings(this.younger);
					this.younger = this.older.younger;
					this.same = this.older.same;
					this.data = this.older.data;
				}else {
					this.data = this.older.data;
					this.younger = this.older.younger;
					this.same = this.older.same;
					this.older = this.older.older;
				}
				}else {
					if(this.younger !=null) {
						this.data = this.younger.data;
						this.same = this.younger.same;
						this.older = this.younger.older;
						this.younger = this.younger.younger;
					}else {
						return null;
					}
				}
			}
		}else {
			if(older !=null) {
				this.older = this.older.removeBuilding(b);
			}
			if(same !=null) {
				this.same = this.same.removeBuilding(b);
			}
			if(younger !=null) {
				this.younger = this.younger.removeBuilding(b);
			}
		}
		return this; 
	}
	
	public int oldest(){
		// ADD YOUR CODE HERE
		OneBuilding oldest = data;
		if(older !=null) {
			oldest = older.data;
			if(older.older !=null) {
				oldest = older.older.data;
			}
		}
		return oldest.yearOfConstruction; 
	}
	
	//helper method: find the max
	public int max(int a, int b) {
		int max = a;
		if(b>max) {
			max = b;
		}
		return max;
	}
	public int highest(){
		// ADD YOUR CODE HERE
		int highest = data.height;
		if(older !=null) {//compare with the older branch 
			if(older.older!=null || older.younger !=null) {
				int olderHighest = older.highest();
				highest = max(data.height,olderHighest);
			}else {
				highest = max(data.height, older.data.height);
			}
		}
		if(younger !=null) {//compare with the younger branch
			if(younger.older!=null || younger.younger !=null) {
				int youngHighest = younger.highest();
				highest = max(highest,youngHighest);
			}else {
				highest = max(highest, younger.data.height);
			}
		}
		return highest; 
	}
	
	public OneBuilding highestFromYear (int year){
		// ADD YOUR CODE HERE
		OneBuilding highOfYr=this.data;
		if(year < this.data.yearOfConstruction) {//the year != year of the whole root
			//case when year is older
			if(this.older!=null) {
				highOfYr = this.older.highestFromYear(year);
			}else {
				return null;
			}
		}
		//case when year is younger
		else if(year > this.data.yearOfConstruction) {
			if(this.younger !=null) {
				highOfYr = this.younger.highestFromYear(year);		
			}else {
				return null;
			}
		}
		return highOfYr; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	
	public int numberFromYears (int yearMin, int yearMax){
		// ADD YOUR CODE HERE
		int number = 0;
		if(yearMin<=yearMax){
			if(this.older !=null) {
				number +=this.older.numberFromYears(yearMin, yearMax);
			}
			if(this.same!=null) {
				number +=this.same.numberFromYears(yearMin, yearMax);
			}
			if(this.younger !=null) {
				number +=this.younger.numberFromYears(yearMin, yearMax);
			}
			if(this.data.yearOfConstruction<=yearMax &&this.data.yearOfConstruction >=yearMin) {
				number+=1;
			}
		}
		return number; // DON'T FORGET TO MODIFY THE RETURN IF NEEDS BE
	}
	//helper method: to add up all cost for repair of a single year
	public int costForAYear(int i) {
		int cost = 0;
		//OneBuilding pointer = this.data;
		if(this.data.yearForRepair == 2018+i) {
			cost+=this.data.costForRepair;
		}
		if(older!=null) {
			cost += older.costForAYear(i);//recursive for all older branches
		}
		if(younger !=null) {
			cost +=younger.costForAYear(i);
		}
		if(same !=null) {
			cost +=same.costForAYear(i);
		}
		return cost;
	}
	public int[] costPlanning (int nbYears){
		// ADD YOUR CODE HERE
		int []array = new int[nbYears];
		for(int i=0;i<nbYears;i++) {
			array[i]=this.costForAYear(i);
			}
		return array; 
	}
	
}
