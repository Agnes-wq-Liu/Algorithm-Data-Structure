package A1;

import static A1.main.*;
//Agnes Liu 260713093
//discussed with Rica Zhang, 260722222
public class Open_Addressing {

    public int m; // number of SLOTS AVAILABLE
    public int A; // the default random number
    int w;
    int r;
    public int[] Table;

    //Constructor for the class. sets up the data structure for you
    protected Open_Addressing(int w, int seed) {

        this.w = w;
        this.r = (int) (w - 1) / 2 + 1;
        this.m = power2(r);
        this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
        this.Table = new int[m];
        //empty slots are initalized as -1, since all keys are positive
        for (int i = 0; i < m; i++) {
            Table[i] = -1;
        }

    }

    /**
     * Implements the hash function g(k)
     */
    public int probe(int key, int i) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
    	int hash=0;
    	hash=((this.A*key)%power2(this.w))>>(this.w-this.r);
    	int g= (hash+i)%power2(this.r);
        return g;
    }

    /**
     * Checks if slot n is empty
     */
    public boolean isSlotEmpty(int hashValue) {
        return Table[hashValue] == -1;
    }

    /**
     * Inserts key k into hash table. Returns the number of collisions
     * encountered
     */
    public int insertKey(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
    	int count =0;
    	for(int i=0;i<m;i++) {
    		int g = probe(key,i);
    		if(isSlotEmpty(g)) { 
    			Table[g]=key;
    			return count;
    		}
    		else
    			count++;
    	}
    	while(count<m)
    		return count;
    	return m;//if all slots full
    }

    /**
     * Removes key k from hash table. Returns the number of collisions
     * encountered
     */
    public int removeKey(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
    	int count =0;
    	int hash=((this.A*key)%power2(this.w))>>(this.w-this.r);
    	if(this.Table[hash]!=key) {
    		count++;//collision 1 with index hash
    		for(int i=0;i<m;i++) {
        		int g = probe(key,i);//get the linear probing of this key
        		if(Table[g]==key) { //found by probing(case 1)
        			Table[g]=-1;
        			return count;
        		}else 
        			count++;//entering next probing (case2)
    		}
    	}else {
    		Table[hash]=-1;//found by chaining
    	}
    	
        return count;
    }
    /*public int getIndex(int key) {
    	int index =((this.A*key)%power2(this.w))>>(this.w-this.r);
    		if(isSlotEmpty(index))
    			return index;//check if hash correspond to right index
    		else{
    			for(int i=0;i<m;i++) {
    			int g = probe(key,i);
        			if(this.Table[g]==key) { 
        				index = g;
        				return index;
        				//if found the key when probing, return the index
        			}//else keep looping
        		}
    		}
        		
    	return -1;//case for not found
    }*/

}
