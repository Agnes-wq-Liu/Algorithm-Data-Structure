//package A5;
//Agnes Liu
import java.io.*;
import java.util.*;
public class islands {

	public static void main(String[]args) throws Exception{
		//open an input file with a fixed name & write to a txt file
		//long start = System.currentTimeMillis();
		File file = new File ("testIslands.txt");
		BufferedReader br = new BufferedReader(new FileReader (file));			
		String line = br.readLine();
		int n = Integer.parseInt(line);
		int[]results = new int[n];//set the length of my array for output
		line = br.readLine();//go to 2nd line
		int i = 0;//iterating the problem
		int totalRow = 0;
		int sizeOfRow = 0;
		while(line!=null&&i<n) {
			char[][]theMap = new char[totalRow][sizeOfRow];
			int bits = 0;
			if(line.startsWith("#")||line.startsWith("-")) {
				int p =0;
				while(p<totalRow) {
					for(int k=0;k<sizeOfRow;k++) {
						theMap[p][k]=line.charAt(k);
						if(line.charAt(k)=='-')
							bits++;
					}
					line=br.readLine();
					p++;
				}//generated char[][] for 1 question
				
				results[i]= numberOfIslands(theMap,totalRow,sizeOfRow,bits);
				i++;
			}else {
				String[] numbers= line.split(" ");
				totalRow =Integer.parseInt(numbers[0]);
				sizeOfRow = Integer.parseInt(numbers[1]);
				line=br.readLine();//to next row when it is the map
			}
		}
				
		//write to output
		FileWriter fw = new FileWriter("testIslands_solution.txt");
		BufferedWriter bw = new BufferedWriter (fw);
		for(int j =0;j<results.length;j++) {
		bw.write(Integer.toString(results[j])+"\n");
		}
		bw.close();
		br.close();
		//long end = System.currentTimeMillis();
		//System.out.println(end-start);
		
	}
	public static int numberOfIslands(char[][] mca, int r,int s,int bits) {
		for(int i =0;i<r;i++) {
			for(int j=0;j<s;j++) {
				bits = adjacent(mca,r,s,bits,i,j);
			}
		}
		return bits;
	}
	public static int adjacent(char[][] mca, int r,int s, int bits,int i, int j) {
		if(mca[i][j]=='-') {
			mca[i][j]='@';//root: set to be discovered
			if(i+1<r && mca[i+1][j]=='-') {//found a neighbor down
				bits--;
				bits = adjacent(mca,r,s,bits,i+1,j);//traverse neighbor 
			}
			if(j+1<s&&mca[i][j+1]=='-') {
				bits--;
				bits = adjacent(mca,r,s,bits,i,j+1);//right
			}
			if(i-1>=0 && mca[i-1][j]=='-') {
				bits--;
				bits = adjacent(mca,r,s,bits,i-1,j);//up
			}
			if(j-1>=0&&mca[i][j-1]=='-') {
				bits--;
				bits = adjacent(mca,r,s,bits,i,j-1);//left
			}
		}
		return bits;
	}
}
