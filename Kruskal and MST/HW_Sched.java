package A2;
import java.util.*;
//Agnes Liu 260713093
//co-worked with Rica Zhang 260722222 
class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
	int deadline;
	
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline) {
		this.number = number;
		this.weight = weight;
		this.deadline = deadline;
	}
	
	
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 * The way you implement this method will define which order the assignments appear in when you sort.
	 * Return 1 if a1 should appear after a2
	 * Return -1 if a1 should appear before a2
	 * Return 0 if a1 and a2 are equivalent 
	 */
	@Override
	//list of hws: array of weights(relative importance)and ddl not sorted
	//each index represents a single hw to submit, 1hr to complete
	public int compare(Assignment a1, Assignment a2) {
		//YOUR CODE GOES HERE, DONT FORGET TO EDIT THE RETURN STATEMENT
		//decide the order of the 2: 0==equivalent, 1==a2 comes first, -1==a1 comes first
		if(a1.deadline==a2.deadline) {
			if(a1.weight==a2.weight)
				return 0;
			else if(a1.weight<a2.weight)
				return 1;
			else
				return -1;
		}
		else if(a1.deadline<a2.deadline)
			return -1;
		else
			return 1;
	}
}

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int size) {
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to when assignment #i will be completed. output[i] is 0 if assignment #i is never completed.
	 * The homework you complete first will be given an output of 1, the second, 2, etc.
	 */
	//output hwplan with array size equal input array sizes, index represent same hw
	//indicate when you start each hw by 1<=int<=latest ddl, 0 for not planning to finish
	//max sum of weights
	public int[] SelectAssignments() {
		//Use the following command to sort your Assignments: 
		//Collections.sort(Assignments, new Assignment());
		//This will re-order your assignments. The resulting order will depend on how the compare function is implemented
		Collections.sort(Assignments, new Assignment());
		
		//Initializes the homeworkPlan, which you must fill out and output
		int[] homeworkPlan = new int[Assignments.size()];
		//YOUR CODE GOES HERE
		//test for the sorting
		/*for print check
		Assignment []test = new Assignment[Assignments.size()];
		for(int i=0;i<Assignments.size();i++)
			test[i]=Assignments.get(i);
		for(int j =0;j<Assignments.size();j++)
			System.out.println(test[j].weight);
		check terminates here*/
		int lastDDL = lastDeadline;
		for(int i=0;i<lastDDL;i++) {// thru the ArrayList by time
			int bigW = 0;
			int choice = 0;
			for(int j=0;j<Assignments.size();j++) {
				if(Assignments.get(j).deadline==i+1&&Assignments.get(j).weight>bigW) {
					bigW = Assignments.get(j).weight;
					choice = Assignments.get(j).number;
				}
			}
			homeworkPlan[choice]=i+1;
		}
		
		return homeworkPlan;
	}
}
		
