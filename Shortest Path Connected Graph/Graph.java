package Assignment4Graph;
//Agnes Liu 260713093
public class Graph {

	boolean[][] adjacency;
	int nbNodes;
	
	public Graph (int nb){
		this.nbNodes = nb;
		this.adjacency = new boolean [nb][nb];
		for (int i = 0; i < nb; i++){
			for (int j = 0; j < nb; j++){
				this.adjacency[i][j] = false;
			}
		}
	}
	
	public void addEdge (int i, int j){
		// ADD YOUR CODE HERE
		this.adjacency[i][j] = true;
		this.adjacency[j][i] = true;
	}
	
	public void removeEdge (int i, int j){
		// ADD YOUR CODE HERE
		this.adjacency[i][j] = false;
		this.adjacency[j][i] = false;
	}
	//add and remove: setting the edge b/w to be symmetrically true
	
	public int nbEdges(){
		// ADD YOUR CODE HERE
		int count = 0;
		for(int i =0; i<this.nbNodes;i++) {
			for(int j = i; j< this.nbNodes;j++) {
				if(this.adjacency[i][j]) {
					count ++;
				}
			}
		}
		return count; // DON'T FORGET TO CHANGE THE RETURN
	}
	public int dfs(int j, int start, boolean[]visited) {
		int p = 0;//initializes path length
		for(int i = 0;i<nbNodes;i++) {//test for every vertex
			if (i!=start && this.adjacency[i][j]&&(!visited[i])) {//if i is an unvisited vertex connect to j
				if(this.adjacency[i][start]) {//if i is connected to start
					p++;//updates path length
					return p;//path found
				}
				else {//if i is not connected to start
					visited[i]=true;//set as visited
					int x = dfs(i,start,visited);//look for a path from i to start
					p = p+x;//updates the path length
				}
			}
		}
		return p;
	}
	public int path(int j, int end, boolean[]visited) {
		int shortest = 1+nbNodes;
		int p = 0;//initializes path length
		if(this.adjacency[j][end]) {
			return 1;
		}
		for(int i = 0;i<nbNodes;i++) {
			if (this.adjacency[i][j]&&(!visited[i])) {
				if(i==end) {
				p++;
				return p;//test for every vertex
				}
				else  {
					p=1;
					visited[i]=true;//set as visited
					int x = path(i,end,visited);//look for a path from i to start
					p = p+x;//updates the path length
					if(shortest>p) shortest = p;
				}
			}
		}
		return shortest;
	}
	public boolean cycle(int start){
		// ADD YOUR CODE HERE
		boolean []visited = new boolean [nbNodes];
		for(int j=0;j<this.nbNodes;j++) {
			if(j!=start) {
				if(this.adjacency[start][j]==true) {
					visited[j]=true;
					if(dfs(j,start,visited)>0)
						return true;
				}
			}
		}
		return false; // DON'T FORGET TO CHANGE THE RETURN
	}
	
	public int shortestPath(int start, int end){
		// ADD YOUR CODE HERE
		int shortest = 1+nbNodes; //initializes path length
		//first the case when they're adjacent
		if(this.adjacency[start][end]==true) {
			return 1;
		}else{
			boolean []visited = new boolean [nbNodes];
			visited[start]=true;
			for(int i=0;i<this.nbNodes;i++) {
				//loop through all other nodes adjacent to start 
				if(this.adjacency[start][i] && start!=i && end!=i) {
					//if i is connected to start
					if(this.adjacency[i][end])
						return 2;
					else {
					visited[i]=true;
					int path = path(i,end,visited)+1;
						if(shortest>path)
							shortest = path;
					}
				}
			}
			if(shortest!=1)
				return shortest;
			
			//return nbNodes+1;
		}
		return nbNodes+1; // DON'T FORGET TO CHANGE THE RETURN
	}
}
