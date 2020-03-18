//package A5;
//Agnes Liu
import java.io.*;
import java.util.*;
 public class balloon {
	public static void main(String[]args)throws Exception{
		//long start = System.currentTimeMillis();
		File file = new File ("testBalloons.txt");
		BufferedReader br = new BufferedReader(new FileReader (file));			
		String line = br.readLine();
		int n = Integer.parseInt(line);
		int[]results = new int[n];//set the length of my array for output
		line = br.readLine();//go to 2nd line
		int i = 0;
		while(line!=null&&i<n) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			String str = br.readLine();
			for(String s:str.split(" "))
				list.add(Integer.parseInt(s));
			results[i]=arrowNumber(list);
			i++;
		}
		
		//write to output
		FileWriter fw = new FileWriter("testBalloons_solution.txt");
		BufferedWriter bw = new BufferedWriter (fw);
		for(int j =0;j<results.length;j++) {
			bw.write(Integer.toString(results[j])+"\n");
		}
		bw.close();
		br.close();
		//long end = System.currentTimeMillis();
		//System.out.println(end-start);
	}
	public static int arrowNumber (ArrayList<Integer> input){
		int count = 0;
		for(int i=0; i<input.size();i++) {
			if(input.get(i)!=-1) {
				int x = input.get(i);
				for(int ii=i+1;ii<input.size();ii++) {
					if(input.get(ii)!=-1 && input.get(ii)==x-1) {
						input.set(ii,-1);
						x=x-1;
					}
				}
				count++;
			}
		}
		return count;
	}

 }
