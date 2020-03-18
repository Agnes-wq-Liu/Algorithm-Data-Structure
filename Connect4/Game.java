package Assignment4Game;
//Agnes Liu 260713093
import java.io.*;

public class Game {
	public static int play(InputStreamReader input) {
		BufferedReader keyboard = new BufferedReader(input);
		Configuration c = new Configuration();
		c.print();//added
		int columnPlayed = 3;
		int player;

		// first move for player 1 (played by computer) : in the middle of the grid
		c.addDisk(firstMovePlayer1(), 1);
		c.print();//added
		int nbTurn = 1;

		while (nbTurn < 42) {
			// maximum of turns allowed by the size of the grid
			player = nbTurn % 2 + 1;
			if (player == 2) {
				columnPlayed = getNextMove(keyboard, c, 2);
			}
			if (player == 1) {
				columnPlayed = movePlayer1(columnPlayed, c);
			}
			System.out.println(columnPlayed);
			c.addDisk(columnPlayed, player);
			c.print();//added
			if (c.isWinning(columnPlayed, player)) {
				c.print();
				System.out.println("Congrats to player " + player + " !");
				return (player);
			}
			nbTurn++;
		}
		return -1;
	}

	public static int getNextMove(BufferedReader keyboard, Configuration c, int player) {
		// ADD YOUR CODE HERE
		int x = 0;
		boolean canAdd = false;
		while (!canAdd) {
			try {
				System.out.println("Enter column number for your new move:");
				x = Integer.parseInt(keyboard.readLine());
				while((c.available[x] == 6)|| (x < 0) || (x > 6)) {
					System.out.println("No such position on the configuration. Try again!");
					System.out.println("Enter column number for your new move:");
					x = Integer.parseInt(keyboard.readLine());
				}
				canAdd = true;
			} catch (Exception e) {
				System.out.println("Exception:no such space on board");
			}

		}
		return x; // DON'T FORGET TO CHANGE THE RETURN
	}

	public static int firstMovePlayer1() {
		return 3;
	}

	public static int movePlayer1(int columnPlayed2, Configuration c) {
		// ADD YOUR CODE HERE
		if(c.canWinNextRound(1)!=-1) {
			return c.canWinNextRound(1);//winning move
		}else if(c.canWinTwoTurns(1)!=-1) {
			return c.canWinTwoTurns(1);//if canWinTwoTurns
		}else{
			if(c.available[columnPlayed2]!=6) {
				return columnPlayed2;
			}else {
				int col = columnPlayed2;
				for(int i=1;i<7;i++) {
					if(col-i>=0 && c.available[col-i]<6)
						return col-i;
					else if(col+i<=6 && c.available[col+i]<6)
						return col+i;	
				}
			}
		}
		return 0; // DON'T FORGET TO CHANGE THE RETURN
	}

}
