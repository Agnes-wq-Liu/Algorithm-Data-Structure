//package A5;
//Agnes Liu 260713093
import java.io.*;
public class mancala {
	
	public static void main(String[]args) throws Exception{
	//open an input file with a fixed name & write to a txt file
		//long start = System.currentTimeMillis();
		File file = new File ("testMancala.txt");
		BufferedReader br = new BufferedReader(new FileReader (file));			
		String line = br.readLine();
		int n = Integer.parseInt(line);
		int[]results = new int[n];//set the length of my array for output
		/*System.out.println("the correct answer is");
		System.out.println("0: 0 0 0 0 0 0 0 0 0 0 0 0");
		System.out.println("1: 0 0 0 0 0 0 1 0 0 0 0 0");
		System.out.println("1: 0 0 0 0 0 0 0 1 0 1 1 0");
		System.out.println("1: 0 0 0 0 0 0 1 1 0 0 1 1");
		System.out.println("5: 1 0 0 1 0 1 0 0 0 1 1 1");
		System.out.println("3: 1 1 0 1 1 1 1 1 1 1 1 1");
		System.out.println("************************************");*/
		int i = 0;
		while(line!=null&&i<n) {
			int[]ThisP = new int[12];
			int thisT = 0;//integer for original pebbles
			String str = br.readLine();
			for(String s:str.split(" ")) {
				ThisP[i]=Integer.parseInt(s);
				if(ThisP[i]==1)
					thisT++;
			}
			results[i]=remainingPebble(ThisP,thisT);
			//System.out.println(results[i]+": "+str);
			i++;
		}
		
		//write to output
		FileWriter fw = new FileWriter("testMancala_solution.txt");
		BufferedWriter bw = new BufferedWriter (fw);
		for(int j =0;j<results.length;j++) {
			bw.write(Integer.toString(results[j])+"\n");
		}
		bw.close();
		br.close();
		//long end = System.currentTimeMillis();
		//System.out.println(end-start);
	}
	public static int remainingPebble(int[]w, int total) { 
		//receive an array of fullness for the board (length=12)
		int result = total;
		for(int i=0;i<12;i++) {
			if(w[i]==0) {
				if(i-2>=0&&i+2>=0&&w[i-1]==1&&w[i-2]==1&&w[i+2]==1&&w[i+1]==1) {
					//when it's 11011
					int left = 0;
					int right = 0;
					for(int j=0;j<i;j++) {
						if(w[j]==1)
							left++;
					}
					for(int k=11;k>i;k--) {
						if(w[k]==1)
							right++;
					}
					if(left>right) {
						w[i-2]=0;
						w[i-1]=0;
						w[i]=1;
						result--;
					}else {
						w[i+2]=0;
						w[i+1]=0;
						w[i]=1;
						result--;
					}
				}else if(i+3<12&&w[i+1]==1&&w[i+2]==1&&w[i+3]==0) {
					//when w[i] is the first 0 in 0110
					int left = 0;
					int right = 0;
					for(int j=0;j<i;j++) {
						if(w[j]==1)
							left++;
					}
					for(int k=11;k>i+3;k--) {
						if(w[k]==1)
							right++;
					}
					if(left<right) {
						w[i-2]=0;
						w[i-1]=0;
						w[i]=1;
						result--;
					}else {
						w[i+2]=0;
						w[i+1]=0;
						w[i]=1;
						result--;
					}
				}else if(i-3>0&&w[i-1]==1&&w[i-2]==1&&w[i-3]==0) {
					//when w[i] is the last 0 in 0110
					int left = 0;
					int right = 0;
					for(int j=0;j<i-3;j++) {
						if(w[j]==1)
							left++;
					}
					for(int k=11;k>i;k--) {
						if(w[k]==1)
							right++;
					}
					if(left<right) {
						w[i-2]=0;
						w[i-1]=0;
						w[i]=1;
						result--;
					}else {
						w[i+2]=0;
						w[i+1]=0;
						w[i]=1;
						result--;
					}
				}else if(i-2>=0&&i+2<12&&w[i-1]==1&&w[i-2]==1&&w[i+2]==0&&w[i+1]==0) {
					//when the zero is middle of 11000
					w[i-2]=0;
					w[i-1]=0;
					w[i]=1;
					result--;
				}else if(i-2>=0&&i+2<12&&w[i+1]==1&&w[i+2]==1&&w[i-2]==0&&w[i-1]==0) {
					//when the zero is middle of 00011
					w[i+2]=0;
					w[i+1]=0;
					w[i]=1;
					result--;
				}else if(i-2>=0&&i+2<12&&w[i+1]==1&&w[i+2]==1&&w[i-2]==0&&w[i-1]==1) {
					//when the zero is middle of 01011
					w[i+2]=0;
					w[i+1]=0;
					w[i]=1;
					result--;
				}else if(i-2>=0&&i+2<12&&w[i-1]==1&&w[i-2]==1&&w[i+2]==0&&w[i+1]==1) {
					//when the zero is middle of 11010
					w[i-2]=0;
					w[i-1]=0;
					w[i]=1;
					result--;
				}else if(i-2>=0&&w[i-1]==1&&w[i-2]==1) {
					//the zero is at 1 end, 110
					w[i-2]=0;
					w[i-1]=0;
					w[i]=1;
					result--;
				}else if(i+2<12&&w[i+1]==1&&w[i+2]==1) {
					//the zero is at 1 end, 011
					w[i+2]=0;
					w[i+1]=0;
					w[i]=1;
					result--;
				}
			}
		}
		return result;
	}
}
