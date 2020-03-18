//package A3;
//Agnes Liu 260713093
import java.util.*;
public class BellmanFord {
	/**
	 * Utility class. Don't use.
	 */
	public class BellmanFordException extends Exception{
		private static final long serialVersionUID = -4302041380938489291L;
		public BellmanFordException() {super();}
		public BellmanFordException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 * 
	 * Use this to specify a negative cycle has been found 
	 */
	public class NegativeWeightException extends BellmanFordException{
		private static final long serialVersionUID = -7144618211100573822L;
		public NegativeWeightException() {super();}
		public NegativeWeightException(String message) {
			super(message);
		}
	}
	
	/**
	 * Custom exception class for BellmanFord algorithm
	 *
	 * Use this to specify that a path does not exist
	 */
	public class PathDoesNotExistException extends BellmanFordException{
		private static final long serialVersionUID = 547323414762935276L;
		public PathDoesNotExistException() { super();} 
		public PathDoesNotExistException(String message) {
			super(message);
		}
	}
	
    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    BellmanFord(WGraph g, int source) throws BellmanFordException{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         *  
         *  When throwing an exception, choose an appropriate one from the ones given above
         */
        
        /* YOUR CODE GOES HERE */
    	distances = new int [g.getNbNodes()];
    	predecessors = new int [g.getNbNodes()];
    	this.source = source;
    	for(int vertex =0;vertex<g.getNbNodes();vertex++) {
    		distances[vertex]=Integer.MAX_VALUE;
    		predecessors[vertex]=-1;
    	}
    	distances[source]=0;
    	//assign values to distances, assume no paths
    	for(Edge e: g.getEdges()) {
   			int u = e.nodes[0];
   			int v = e.nodes[1];
   			relax(u,v,e.weight);
    	}//update the distances with estimated shortest paths
    	for(Edge e: g.getEdges()) {
    		int u = e.nodes[0];
			int v = e.nodes[1];
    		if(distances[v]>distances[u]+e.weight)
    			throw new NegativeWeightException("Existence of a Negative cycle.");
    	}//if going out from this for loop: meaning no negative cycles found
    	//have distances at each index the shortest path weight
    	this.distances = distances;
    	this.predecessors = predecessors;
    }
    public void relax (int u, int v, int w) {
    	if(distances[v]>distances[u]+w) {
    		distances[v]=distances[u]+w;
    		predecessors[v]=u;
    	}
    }
  
    public int[] shortestPath(int destination) throws BellmanFordException{
        /*Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If no path exists an Exception is thrown
         * Choose appropriate Exception from the ones given 
         */

        /* YOUR CODE GOES HERE (update the return statement as well!) */
    	int p = predecessors[destination];
    	while(p!=source && p>=0) {
    		p=predecessors[p];
    	}
    	if(p!=source)
    		throw new PathDoesNotExistException ("There is no path from the source to this destination.");
    	ArrayList<Integer>temp = new ArrayList<Integer>();
    	temp.add(destination);
    	int pre = predecessors[destination];
    	while(pre!=source) {
    		temp.add(pre);
    		pre = predecessors[pre];
    	}
    	temp.add(source);
    	
    	int[] nodesList = new int[temp.size()];
    	int j=0;
    	for(int k=temp.size()-1;k>=0;k--) {
    		nodesList[k]=temp.get(j);
    		j++;
    	}
    	
     
    	return nodesList;
    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }

   } 
}
