package assignment1;

//Agnes Liu 260713093
public class Message {
	public String message;
	public int lengthOfMessage;

	public Message (String m){
		message = m;
		lengthOfMessage = m.length();
		this.makeValid();
	}
	
	public Message (String m, boolean b){
		message = m;
		lengthOfMessage = m.length();
	}
	
	/**
	 * makeValid modifies message to remove any character that is not a letter and turn Upper Case into Lower Case
	 */
	//helper methods converting String and Char Array
	public static char[] stringToArray(String s) {
		char[]mArray = s.toCharArray();
		return mArray;
	}
	public static String arrayToString(char[]arr) {
		String str = "";
		for(int i=0;i<arr.length;i++) {
			str = str + arr[i];
		}
		return str;
	}
	public void makeValid(){
		//INSERT YOUR CODE HERE
		char[]mArray = stringToArray(message);
		int a = lengthOfMessage;
		char[]c = new char[a];
		String ll= "abcdefghijklmnopqrstuvwxyz";
		String lC="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String me= "";
		for(int i=0;i<a;i++) {	
			int count=0;
			for(int l=0;l<26;l++){
				if(mArray[i]==ll.charAt(l)||mArray[i]==lC.charAt(l)) {
					c[i] = ll.charAt(l);
					l=26;
				}else {
					count++;
				}
			}
			if(count==26 && i<a) {
				c[i]='#';
			}
		}
		int size=0;
		for(int i=0;i<a;i++) {
			if(c[i]!='#') {
				size++;
			}
		}
		char[]script = new char[size];
		int j=0;
		for(int i=0;i<a;i++) {
			if(j==size) {
				break;
			}else if(c[i]!='#') {
				script[j]=c[i];
				j++;
			}
		}
		for(int i=0;i<size;i++) {
			me=me + script[i];
		}
		this.message=me;
		this.lengthOfMessage = size;
	}
	
	/**
	 * prints the string message
	 */
	public void print(){
		System.out.println(message);
	}
	
	/*
	 * tests if two Messages are equal
	 */
	public boolean equals(Message m){
		if (message.equals(m.message) && lengthOfMessage == m.lengthOfMessage){
			return true;
		}
		return false;
	}
	
	/**
	 * caesarCipher implements the Caesar cipher : it shifts all letter by the number 'key' given as a parameter.
	 * @param key
	 */
	public void caesarCipher(int key){
		// INSERT YOUR CODE HERE
		int s = lengthOfMessage;
		char []codes=new char [s]; 
		String str=message;
		//if key is 0 or a multiple of 26
		if(key==0 || key%26==0) {
			for(int i=0;i<s;i++) {
				codes[i]=message.charAt(i);				
			}
		}
		// if|key|<26
		else if(key>0 && key<26) {
			for(int i=0;i<s;i++) {
				char c=(char)(message.charAt(i)+key);
				if(c > 'z') {
					codes[i] =(char)(c-26);//go on another circle in alphabet
				}else {
					codes[i]=c;
				}
			}
		}
		else if(key<0 && key>-26) {
			for(int i=0;i<s;i++) {
				char d=(char)(message.charAt(i)+(26+key));
				if(d > 'z') {
					codes[i] =(char)(d-26);
				}else {
					codes[i]=d;
				}
			}	
		}
		//if |key|>26 && key is not multiple of 26
		else if(key>26 && key%26!=0) {
			for(int i=0;i<s;i++) {
				char e=(char)(message.charAt(i)+(key%26));
				if(e > 'z') {
					codes[i]=(char)(e-(key/26)*26);
				}else {
					codes[i]=e;
				}
			}
		}
		else if(key<-26 && key%26!=0) {
			for(int i=0;i<s;i++) {
				char f=(char)(message.charAt(i)+26+key%26);
				if(f > 'z') {
					codes[i]=(char)(f+(key/26)*26);
				}else {
					codes[i]=f;
				}
			}
		}
		str = arrayToString(codes);
		this.message = str;
		this.lengthOfMessage = str.length();
	}
	
	public void caesarDecipher(int key){
		this.caesarCipher(- key);
	}
	
	/**
	 * caesarAnalysis breaks the Caesar cipher
	 * you will implement the following algorithm :
	 * - compute how often each letter appear in the message
	 * - compute a shift (key) such that the letter that happens the most was originally an 'e'
	 * - decipher the message using the key you have just computed
	 */
	public void caesarAnalysis(){
		// INSERT YOUR CODE HERE
		int tmp=0;
		int a = lengthOfMessage;
		String s = message;
		char corrE = 'a';//random initiation: letter corresponding to actual 'e'
		int keyShift=0;
		for(int i=0; i<a;i++) {//loop through
			int count=0;
			char compare = s.charAt(i);//start compare with first char
			for(int k= 0; k<a; k++) {
				char now = s.charAt(k);//loop through rest of message
				if(now==compare) {
					count++;   //appeared 1 more time
				}
			}
			if(count>tmp) {
				tmp=count;//upload max frequency
				corrE = compare; //corrE char was originally e 
			}
		}
		keyShift=(int)(corrE-'e');//if <0 shifted backwards, else forward
		caesarDecipher(keyShift);
	}
	
	/**
	 * vigenereCipher implements the Vigenere Cipher : it shifts all letter from message by the corresponding shift in the 'key'
	 * @param key
	 */
	public void vigenereCipher (int[] key){
		// INSERT YOUR CODE HERE
		int la=message.length();
		int lb=key.length;
		char[]array = new char [la];
		String input = message;
		String finalString = "";
			for(int i=0;i<la;i++) {
				int index = (i+lb)%lb;
				array[i]=caesarChar(input.charAt(i), key[index]);
				finalString=finalString + array[i];
			}
		this.message = finalString;
		this.lengthOfMessage = finalString.length();
	}

	/**
	 * vigenereDecipher deciphers the message given the 'key' according to the Vigenere Cipher
	 * @param key
	 */
	public void vigenereDecipher (int[] key){
		// INSERT YOUR CODE HERE
		int la=message.length();
		int lb=key.length;
		char[]array = new char [la];//create array for cipher
		String input = message;
		String finalString = "";
			for(int i=0;i<la;i++) {
				int index = (i+lb)%lb;
				array[i]=caesarChar(input.charAt(i), -key[index]);//perform backwards cipher as vigenere
				finalString=finalString + array[i]; 
			}
		System.out.println(finalString);
		this.message = finalString;
		this.lengthOfMessage = finalString.length();
		
	}
	
	/**
	 * transpositionCipher performs the transition cipher on the message by reorganizing the letters and eventually adding characters
	 * @param key
	 */
	public void transpositionCipher (int key){
		// INSERT YOUR CODE HERE
		this.makeValid();
		int length = this.lengthOfMessage;
		String msg = this.message;
		int row = 1;
		if(length>key) {
			if (length%key!=0) {
				row = length/key + 1;//for a row with blanks 
			}else {
				row = length/key;
			}
		}
		char [][]matrix = new char [key][row];// [key] for array number, [row] for element member
		for(int u=0;u<key;u++) {
			for(int y=0;y<row;y++) {
				if(key*y+u<length) {//index < length of message, get char in message 
					matrix[u][y]=msg.charAt(key*y+u);
				}
				else {
					matrix[u][y]='*';//index > length of message, fill the blanks with *
				}
			}
		}
		String trCiph = "";
		for(int h=0;h<key;h++) {
			String str =arrayToString(matrix[h]);//convert back to string
			trCiph=trCiph + str;
		}
		this.message = trCiph;
		this.lengthOfMessage = trCiph.length();
	}
	
	/**
	 * transpositionDecipher deciphers the message given the 'key'  according to the transition cipher.
	 * @param key
	 */
	public void transpositionDecipher (int key){
		// INSERT YOUR CODE HERE
		int length = this.lengthOfMessage;
		String msg = this.message;
		int row = length/key;
		char [][]matrix = new char [row][key];//switch places to put row and key
		for(int f=0;f<row;f++) {
			for(int w=0;w<key;w++) {
				if(row*w+f<length) {
					matrix[f][w]=msg.charAt(f+w*row);
				}
			}
		}
		String trDCiph = "";
		for(int h=0;h<row;h++) {
			for(int i=0;i<key;i++) {
				if(matrix[h][i]!='*') {
					trDCiph = trDCiph + matrix[h][i];
				}
			}
		}
		this.message = trDCiph;
		this.lengthOfMessage = trDCiph.length();
	}

	public static char caesarChar(char i,int y){//helper that takes only 1 char
		char output = '#';
		if(y==0 || y%26==0) {
			output =i;
		//this is for key==0 or a multiple of 26
		}else if(y>0 && y<26) {
				char c=(char)(i+y);
				if(c > 'z') {
					output=(char)(c-26);//go on another circle in alphabet
				}else {
					output=c;
				}
		}
		else if(y<0 && y>-26) {
				char c=(char)(i+(26+y));
				if(c > 'z') {
					output=(char)(c-26);
				}else {
					output=c;
				}	
		}// if|key|<26
		else if(y>26 && y%26!=0) {
				char c=(char)(i+(y%26));
				if(c > 'z') {
					output=(char)(c-(y/26)*26);
				}else {
					output=c;
				}
		}
		else if(y<-26 && y%26!=0) {
				char c=(char)(i+26+y%26);
				if(c > 'z') {
					output=(char)(c+(y/26)*26);
				}else {
					output=c;
				}
		}//if |key|>26 && key is not multiple of 26	
		return output;
	}
}
