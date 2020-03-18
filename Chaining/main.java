package A1;

import A1.Chaining.*;
import A1.Open_Addressing.*;

import static A1.main.power2;

import java.io.*;
import java.util.*;
//Agnes Liu 260713093
//discussed with Rica Zhang, 260722222
public class main {

    /**
     * Calculate 2^w
     */
    public static int power2(int w) {
        return (int) Math.pow(2, w);
    }

    /**
     * Uniformly generate a random integer between min and max, excluding both
     */
    public static int generateRandom(int min, int max, int seed) {
        Random generator = new Random();
        //if the seed is equal or above 0, we use the input seed, otherwise not.
        if (seed >= 0) {
            generator.setSeed(seed);
        }
        int i = generator.nextInt(max - min - 1);
        return i + min + 1;
    }

    /**
     * export CSV file
     */
    public static void generateCSVOutputFile(String filePathName, ArrayList<Double> alphaList, ArrayList<Double> avColListChain, ArrayList<Double> avColListProbe) {
        File file = new File(filePathName);
        FileWriter fw;
        try {
            fw = new FileWriter(file);
            int len = alphaList.size();
            fw.append("Alpha");
            for (int i = 0; i < len; i++) {
                fw.append("," + alphaList.get(i));
            }
            fw.append('\n');
            fw.append("Chain");
            for (int i = 0; i < len; i++) {
                fw.append("," + avColListChain.get(i));
            }
            fw.append('\n');
            fw.append("Open Addressing");
            for (int i = 0; i < len; i++) {
                fw.append(", " + avColListProbe.get(i));
            }
            fw.append('\n');
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        /*===========PART 1 : Experimenting with n===================*/
        //Initializing the three arraylists that will go into the output 
        ArrayList<Double> alphaList = new ArrayList<Double>();
        ArrayList<Double> avColListChain = new ArrayList<Double>();
        ArrayList<Double> avColListProbe = new ArrayList<Double>();

        //Keys to insert into both hash tables
        int[] keysToInsert = {164, 127, 481, 132, 467, 160, 205, 186, 107, 179,
            955, 533, 858, 906, 207, 810, 110, 159, 484, 62, 387, 436, 761, 507,
            832, 881, 181, 784, 84, 133, 458, 36};

        //values of n to test for in the experiment
        int[] nList = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32};
        //value of w to use for the experiment on n
        int w = 10;
     
        //initializing lists here in case the program re-initiate every time in the for-loop?
        for (int n : nList) {
            //initializing two hash tables with a seed
            Chaining MyChainTable = new Chaining(w, 137);
            Open_Addressing MyProbeTable = new Open_Addressing(w, 137);
           
            /*Use the hash tables to compute the average number of 
                        collisions over keys keysToInsert, for each value of n. 
                        The format of the three arraylists to fill is as follows:
                        
                        alphaList = arraylist of all tested alphas 
                                   (corresponding to each tested n)
                        avColListChain = average number of collisions for each
                                         Chain experiment 
                                         (make sure the order matches alphaList)
                        avColListProbe =  average number of collisions for each
                                         Linear Probe experiment
                                           (make sure the order matches)
                        The CSV file will output the result which you can visualize
             */
            double alpha = (double)n/(double)MyChainTable.m;//setting alpha in double
            alphaList.add(alpha);
            double countC = 0.0;
            double countP= 0.0;
            //setting collision counts for each hash table;
            for(int j=0;j<n;j++) {
            	countC += (double)MyChainTable.insertKey(keysToInsert[j]);
            	countP += (double)MyProbeTable.insertKey(keysToInsert[j]);
            }//inserting first n keys, add the collisions counted before
            //System.out.println("countC "+ countC + "countP " + countP);
            double avC = countC/(double)n;
            double avP = countP/(double)n;
            //System.out.println("avC "+ avC + "avP " + avP);
            avColListChain.add(avC);
            avColListProbe.add(avP);
            //ADD YOUR CODE HEREE
        }
        //System.out.println("avColListChain " + avColListChain);
        //System.out.println("avColListProbe " + avColListProbe);
        generateCSVOutputFile("n_comparison.csv", alphaList, avColListChain, avColListProbe);

        /*===========    PART 2 : Test removeKey  ===================*/
 /* In this exercise, you apply your removeKey method on an example.
        Make sure you use the same seed, 137, as you did in part 1. You will
        be penalized if you don't use the same seed.
         */
        //Please not the output CSV will be slightly wrong; ignore the labels.
        ArrayList<Double> removeCollisions = new ArrayList<Double>();
        ArrayList<Double> removeIndex = new ArrayList<Double>();
        int[] keysToRemove = {6, 8, 164, 180, 127, 3, 481, 132, 4, 467, 5, 160,
            205, 186, 107, 179};
      
        //ADD YOUR CODE HERE
        Open_Addressing newOpenAddressing = new Open_Addressing(w,137);
        for(int j=0;j<16;j++) {
        	int count = newOpenAddressing.insertKey(keysToInsert[j]);
        }//created new OA hash table and inserted first 16 elements in keysToInsert
        System.out.println(Arrays.toString(newOpenAddressing.Table));
        for (int j=0;j<16;j++) {
        	double col = (double)newOpenAddressing.removeKey(keysToRemove[j]); 
        	removeCollisions.add(col);//remove the key, add the # of collisions
        	double index = (double)j;
        	removeIndex.add(index);
        	}
        System.out.println("removeCollisions" + removeCollisions);
        System.out.println("removeIndex" + removeIndex);
        generateCSVOutputFile("remove_collisions.csv", removeIndex, removeCollisions, removeCollisions);

        /*===========PART 3 : Experimenting with w===================*/

 /*In this exercise, the hash tables are random with no seed. You choose 
                values for the constant, then vary w and observe your results.
         */
        //generating random hash tables with no seed can be done by sending -1
        //as the seed. You can read the generateRandom method for detail.
        //randomNumber = generateRandom(0,55,-1);
        //Chaining MyChainTable = new Chaining(w, -1);
        //Open_Addressing MyProbeTable = new Open_Addressing(w, -1);
        //Lists to fill for the output CSV, exactly the same as in Task 1.
        ArrayList<Double> alphaList2 = new ArrayList<Double>();
        ArrayList<Double> avColListChain2 = new ArrayList<Double>();
        ArrayList<Double> avColListProbe2 = new ArrayList<Double>();

        //ADD YOUR CODE HERE
        //choose my own w
        int myW[]={8,10,12,14,16,18};
        int ws = myW.length;
        int []randomKey = new int[20];
        int ks = randomKey.length;
        for(int j=0;j<ks;j++) {
        	randomKey[j]=generateRandom(0,100,-1);
        }
        
        for(int j=0; j<ws;j++) {
        	//for each w value, do 10 simulations
        	int myM = power2((int)(myW[j]- 1)/2 + 1);//number of slots for this w value
        	double alpha2 = (double)ks/(double)myM;
        	alphaList2.add(alpha2);
        	double colC=0.0;
        	double colP=0.0;
        	for(int jj=0;jj<10;jj++) {
        		//each simulation, add all random keys, calculate average collisions
        		//in each table
        		Chaining MyChainTable = new Chaining(myW[j], -1);
            	Open_Addressing MyProbeTable = new Open_Addressing(myW[j], -1);
            	for(int k=0;k<ks;k++) {
        		colC+=(double)MyChainTable.insertKey(randomKey[k]);
        		colP+=(double)MyProbeTable.insertKey(randomKey[k]);
            	}
        	}
           	double avColC=colC/(double)ks/10.0;
            double avColP=colP/(double)ks/10.0;
        	avColListChain2.add(avColC);
        	avColListProbe2.add(avColP);
        }
        generateCSVOutputFile("w_comparison.csv", alphaList2, avColListChain2, avColListProbe2);

    }

}
