//package A3;
//Agnes Liu 260713093
import java.io.*;
import java.util.*;




public class FordFulkerson {

	
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		// YOUR CODE GOES HERE
		//stack is the array-list to store my output
		char[] mark = new char[graph.getNbNodes()];
		for(int i=0;i<graph.getNbNodes();i++) {
			mark[i] = 'n';//create char array representing not/discovered/visited by n/d/v
		}
		//now traverse from source vertex
		int s = source;
		int d = destination;
		if(mark[s]=='n') {
			Stack = helperDFS(s,d,mark, Stack,graph);
		}
		return Stack;
	}
	
	public static ArrayList<Integer> helperDFS(int i,int des,char[] mark, ArrayList<Integer> Stack, WGraph graph) {
		mark[i]='v';
		Stack.add(i);
		for(Edge e: graph.getEdges()) {//looping thru all edges 
			//see if any nodes adjacent to source 
			for(int j=0;j<graph.getNbNodes();j++) {
				if(((e.nodes[0]==i && e.nodes[1]==j)||(e.nodes[1]==i && e.nodes[0]==j))&&e.weight!=0) {
					if (mark[j]=='n') {
						Stack = helperDFS(j,des,mark,Stack,graph);
					}
				}
			}
		}
		if(mark[des]!='v')
			Stack = new ArrayList<Integer>();
		return Stack;
	}
	public static boolean edgeInPath(Edge e, ArrayList<Integer> path) {
		int head = e.nodes[0];
		int tail = e.nodes[1];
		for(int i=0;i<path.size();i++) {
			if(head==(int)path.get(i)) {
				for(int j = 0;j<path.size();j++) {
					if(tail==(int)path.get(j)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public static boolean isForward(Edge e, ArrayList<Integer> path) {
		while(edgeInPath(e,path)) {
			int head = e.nodes[0];
			int tail = e.nodes[1];
			for(int i =0;i<path.size();i++) {
				if(path.get(i)==head) {
					for(int j=0;j<path.size();j++){
						if(path.get(j)==tail) {
							if(i<j)
								return true;
						}
					}
				}
			}
		}
		return false;
	}
	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "260713093"; //Please initialize this variable with your McGill ID
		int maxFlow = 0;
				//YOUR CODE GOES HERE
		WGraph graphR = new WGraph(graph);
		WGraph graphF = new WGraph(graph);
		for(Edge e: graph.getEdges()) {
			graph.setEdge(e.nodes[0],e.nodes[1], 0);
		}
		while(pathDFS(source, destination,graph).size()>=1) {//when it returns more than empty
			ArrayList<Integer> path = pathDFS(source, destination,graph);
			int bn = Integer.MAX_VALUE;
			// Find the bottleneck
			for(int i = 0; i < path.size()-1; i++){
				Edge e = graphR.getEdge(path.get(i),path.get(i+1));

				if(e.weight!=0 &&e.weight<bn){
					bn = e.weight;
				}
			}
			// Augment the path
			for(int i = 0; i < path.size() - 1; i++){
				Integer n1 = path.get(i);
				Integer n2 = path.get(i + 1);
				Edge e1 = graph.getEdge(n1, n2);
				if(e1.weight!=0){
					graph.setEdge(n1, n2, e1.weight + bn);
				}
				else{
					graph.setEdge(n1, n2, e1.weight - bn);
				}
			}
			//update my residual graph
			for(int i=0; i<path.size()-1; i++){
				Integer n1 = path.get(i);
				Integer n2 = path.get(i + 1);
				Edge edge = graph.getEdge(n1, n2);
				Edge w = graphF.getEdge(n1, n2);
				if(edge.weight <= w.weight){
					graphR.setEdge(n1, n2, w.weight - edge.weight);
				} else if (edge.weight > 0) {
					Edge residualEdge = graphR.getEdge(n1, n2);
					if(residualEdge == null){
						Edge backEdge = new Edge(n1, n2, edge.weight);
						graphR.addEdge(backEdge);
					}
					else{
						graphR.setEdge(n2, n1, edge.weight);
					}
				}
			}
			maxFlow += bn;
			bn = Integer.MAX_VALUE;
		}
		
		
		answer += maxFlow + "\n" + graph.toString();	
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	}
	
	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesn't exists, then create it
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 //ArrayList<Integer> Stack = pathDFS(g.getSource(), g.getDestination(),g);
		 //System.out.println(Stack.toString());
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}

