package A2;
import java.util.*;
//Agnes Liu 260713093
//co-worked with Rica Zhang 260722222 
public class Kruskal {
    public static WGraph kruskal(WGraph g){

        /* Fill this method (The statement return null is here only to compile) */
    	//1. start with everything to be disjoint: create disjoint set with n=nb nodes
    	DisjointSets p = new DisjointSets (g.getNbNodes());
    	//make a new WGraph called MST
    	WGraph MST = new WGraph ();//create an empty WGraph
    	ArrayList<Edge> sorted = g.listOfEdgesSorted();//sorted array-list of edges of g
    	for(Edge e: sorted) {//if edge exists    			
    		if(IsSafe(p,e))//e is safe to add
    			MST.addEdge(e);//after added: merge the partitions as well
    			int n1 = e.nodes[0];
    			int n2 = e.nodes[1];
    			p.union(n1, n2);
    	}
        return MST;
    } 
    public static Boolean IsSafe(DisjointSets p, Edge e){

        /* Fill this method (The statement return 0 is here only to compile) */
      	//examine the roots of the nodes
    	int i = p.find(e.nodes[0]);
    	int j = p.find(e.nodes[1]);
    	if(i==j)
    		return false;//already connected:not safe
    	else
    		return true;//case2: not connected: safe
    }
    
    public static void main(String[] args){
        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 

}
