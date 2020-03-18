package Assignment4Game;

//Agnes Liu 260713093
public class Configuration {
	public int[][] board;
	public int[] available;
	boolean spaceLeft;

	public Configuration() {
		board = new int[7][6];
		available = new int[7];
		spaceLeft = true;
	}

	public void print() {
		System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 |");
		System.out.println("+---+---+---+---+---+---+---+");
		for (int i = 0; i < 6; i++) {
			System.out.print("|");
			for (int j = 0; j < 7; j++) {
				if (board[j][5 - i] == 0) {
					System.out.print("   |");
				} else {
					System.out.print(" " + board[j][5 - i] + " |");
				}
			}
			System.out.println();
		}
	}

	public void addDisk(int index, int player) {
		// ADD YOUR CODE HERE
		if (index >= 0 && index <= 6) {
			int i = this.available[index];
			if (i < 6) {
				this.available[index]++;
			}
			int count = 0;
			for (int j = 0; j < 7; j++) {
				if (this.available[j] == 6)
					count++;
			}
			if (count == 7)
				this.spaceLeft = false;
			this.board[index][i] = player;
		}

	}

	public boolean isWinning(int lastColumnPlayed, int player) {
		// ADD YOUR CODE HERE
		// first vertical; recall board[column][row]
		int row;
		if (this.available[lastColumnPlayed]==6)
			row = 5;
		else
			row =this.available[lastColumnPlayed]-1;
		while(row >= 0 ) {
			int count = 1;
			if (board[lastColumnPlayed][row] == player) {
				// when found the highest say of this player in this column
				int vertical = 1;
				for (int i = row - 1; i > 0; i--) {
					if (board[lastColumnPlayed][i] == player && board[lastColumnPlayed][i - 1] == player) {
						vertical++;
						if (vertical == 4)
							return true;
					} else {
						vertical = 1;
						break;
					}
				}
				// code for horizontal; multiple cases
				// first cases when lastColumnPlayed is 0 or 6: unidirectional
				if (lastColumnPlayed == 0) {
					for (int i = 1; i < 4; i++) {
						if (board[i][row] == player && board[i - 1][row] == player) {
							count++;
							if (count == 3)
								return true;
						} else {
							count = 1;
							break;
						}
					}
				} else if (lastColumnPlayed == 6) {
					for (int i = 3; i < 6; i++) {
						if (board[i][row] == player && board[i + 1][row] == player) {
							count++;
							if (count == 3)
								return true;

						} else {
							count = 1;
							break;
						}
					}
				}
				// second cases for bi-directional
				else {
					for (int i = lastColumnPlayed - 1; i >= 0; i--) {
						if (board[i][row] == player && board[i + 1][row] == player) {
							count++;
							if (count == 4)
								return true;
						} else if (board[i + 1][row] != player) {
							count = 1;
							break;
						}
					}
				}
			} else {
				for (int i = lastColumnPlayed + 1; i < 7; i++) {
					if (board[i][row] == player && board[i - 1][row] == player) {
						count++;
						if (count == 4)
							return true;
					} else if (board[i - 1][row] != player) {
						count = 1;
						break;
					}
				}
			}
			if (board[lastColumnPlayed][row] == player) {
				if (this.isInDiagonal(lastColumnPlayed, row, player))
					return true;
			}
			// when found the highest say of this player in this column
			// consider diagonal
			row--;
		}
		return false; // DON'T FORGET TO CHANGE THE RETURN
	}

	public boolean isInRow(int i, int j, int player) {
		int count = 1;
		if (i == 0) {
			for (int a = 1; a < 4; a++) {
				if (board[a][j] == player && board[a - 1][j] == player) {
					count++;
					if (count == 3)
						return true;
				}else {
					count = 1;
					break;
				}
			}
		} else if (i == 6) {
			for (int a = 3; a < 6; a++) {
				if (board[a][j] == player && board[a + 1][j] == player) {
					count++;
					if (count == 3)
						return true;
				}else {
					count = 1;
					break;
				}
			}
		}
		// second cases for bi-directional
		else {
			for (int a = i - 1; a >= 0; a--) {
				if (board[a + 1][j] == player && board[a][j] == player)
					count++;
				else if (board[a + 1][j] != player) {
					count = 1;
					break;
				}else {
					break;
				}
			}
			if (count == 4)
				return true;
			else {
				for (int a = i + 1; a < 7; a++) {
					if (board[a][j] == player && board[a - 1][j] == player) {
						count++;
						if (count == 4)
							return true;
					} else if (board[a - 1][j] != player) {
						count = 1;
						break;
					}else {
						break;
					}
				}
			}
		}
		return false;
	}

	public boolean isInCol(int i, int j, int player) {
		int count = 1;
		if (j == 0) {
			for (int a = 1; a < 4; a++) {
				if (board[i][a] == player && board[i][a - 1] == player)
					count++;
				else {
					count = 1;
					break;
				}
			}
			if (count == 3)
				return true;
		} else if (j == 5) {
			for (int a = 3; a < 5; a++) {
				if (board[i][a] == player && board[i][a + 1] == player)
					count++;
				else {
					count = 1;
					break;
				}
			}
			if (count == 3)
				return true;
		}
		// second cases for bi-directional
		else {
			for (int a = j; a > 0; a--) {
				if (board[i][a] == player && board[i][a + 1] == player)
					count++;
				else if (board[i][a + 1] != player) {
					count = 1;
					break;
				}
			}
			if (count == 4)
				return true;
			else {
				for (int a = i + 1; a < 6; a++) {
					if (board[i][a] == player && board[i][a - 1] == player) {
						count++;
						if (count == 4)
							return true;
					} else if (board[i][a - 1] != player) {
						count = 1;
						break;
					}
				}
			}
		}
		return false;
	}

	public boolean isInDiagonal(int i, int j, int player) {
		// i=column,j=row
		int count = 1;// should count be 0 or 1?
		int temp = j;
		if (i == 0) {
			if (j < 3) {
				// when column=0 j<3, only to top right
				for (int row = j; row < 5; row++) {
					if (board[i][row] == player && board[i + 1][row + 1] == player) {
						count++;
						i++;
						if (count == 4)
							return true;
					} else {
						count = 1;
						break;
					}
				}
			} else {
				// when column=0 and row==5,only right down
				for (int row = j; row > 0; row--) {
					if (board[i][row] == player && board[i + 1][row - 1] == player) {
						count++;
						i++;
						if (count == 4)
							return true;
					} else {
						count = 1;
						break;
					}
				}
			}
		} else if (i == 6) {
			if (j < 3) {
				// when column=6 && j<3, only to top left
				for (int row = j; row < 5; row++) {
					if (board[i][row] == player && board[i - 1][row + 1] == player) {
						count++;
						j++;
						if (count == 4)
							return true;
					} else {
						count = 1;
						break;
					}
				}
			} else {
				// when column=6 and row>=3,only left down
				for (int row = j; row > 0; row--) {
					if (board[i][row] == player && board[i - 1][row - 1] == player) {
						count++;
						j++;
						if (count == 4)
							return true;
					} else {
						count = 1;
						break;
					}
				}
			}
		} else {
			// when the column is neither 0 nor 6
			if (j == 0 || j == 5) {
				if (j == 0) {
					if (i == 3) {
						for (int col = i; col < 6; col++) {
							if (board[col][j] == player && board[col + 1][j + 1] == player) {
								count++;
								if (j + 2 <= 5)
									j++;
								else
									break;
								if (count == 4)
									return true;
							} else {
								count = 1;
								break;
							}
						}
						if (count == 1) {
							for (int col = i; col > 0; col--) {
								if (board[col][j] == player && board[col - 1][j + 1] == player) {
									count++;
									if (j + 2 <= 5)
										j++;
									else
										break;
									if (count == 4)
										return true;
								} else {
									count = 1;
									break;
								}
							}
						}
					} else {
						// when row=0 && i<3, only right up
						if (i < 3) {
							for (int col = i; col < 6; col++) {
								if (board[col][j] == player && board[col + 1][j + 1] == player) {
									count++;
									if (j + 2 <= 5)
										j++;
									else
										break;
									if (count == 4)
										return true;
								} else {
									count = 1;
									break;
								}
							}
						} else {
							// when row=0 and i>3,only left up
							for (int col = i; col > 0; col--) {
								if (board[col][j] == player && board[col - 1][j + 1] == player) {
									count++;
									if (j + 2 <= 5)
										j++;
									else
										break;
									if (count == 4)
										return true;
								} else {
									count = 1;
									break;
								}
							}
						}
					}
				} else {
					// when i isn't
					// if j==5
					if (i == 3) {
						for (int col = i; col < 6; col++) {
							if (board[col][j] == player && board[col + 1][j - 1] == player) {
								count++;
								if (j - 2 >= 0)
									j--;
								else
									break;
								if (count == 4)
									return true;
							} else {
								count = 1;
								break;
							}
						}
						if (count == 1) {
							for (int col = i; col > 0; col--) {
								if (board[col][j] == player && board[col - 1][j - 1] == player) {
									count++;
									if (j - 2 >= 0)
										j--;
									else
										break;
									if (count == 4)
										return true;
								} else {
									count = 1;
									break;
								}
							}
						}
					} else {
						// when row=5 && i<3, only right down
						if (i < 3) {
							for (int col = i; col < 6; col++) {
								if (board[col][j] == player && board[col + 1][j - 1] == player) {
									count++;
									if (j - 2 >= 0)
										j--;
									else
										break;
									if (count == 4)
										return true;
								} else {
									count = 1;
									break;
								}
							}
						} else {
							// when row=5 and i>3,only left down
							for (int col = i; col > 0; col--) {
								if (board[col][j] == player && board[col - 1][j - 1] == player) {
									count++;
									if (j - 2 >= 0)
										j--;
									else
										break;
									if (count == 4)
										return true;
								} else {
									count = 1;
									break;
								}
							}
						}
					}
				}
			} else {
				// when i is neither 6 nor 0, j is neither 0 nor 5
				// test for top left
				count = 1;
				for (int x = i; x > 0; x--) {
					if (board[x][j] == player && board[x - 1][j + 1] == player) {
						count++;
						if (j + 2 <= 5)
							j++;
						else
							break;
						if (count == 4)
							return true;
					} else if (board[x][j] != player) {// && board[x + 1][j - 1] == player
							//&& board[x - 1][j + 1] == player) {
						count = 1;
						break;
					}
				}
				// if everything top left doesn't have 4 (including)
				// loop through right bottom
				j = temp;
				for (int x = i; x < 6; x++) {
					if (board[x][j] == player && board[x + 1][j - 1] == player) {
						count++;
						if (j - 2 >= 0)
							j--;
						else
							break;
					} else if (board[x][j] != player) {// && board[x + 1][j - 1] == player
							//&& board[x - 1][j + 1] == player) {
						count = 1;
						break;
					}
				}
				if (count == 4)
					return true;
				else if (count == 1) {
					j = temp;
					// meaning not along left top-right bot direction
					// loop through left bottom
					for (int x = i; x > 0; x--) {
						if (board[x][j] == player && board[x - 1][j - 1] == player) {
							count++;
							if (j - 2 >= 0)
								j--;
							else
								break;
						} else if (board[x][j] != player) {// && board[x + 1][j - 1] == player
								//&& board[x - 1][j + 1] == player) {
							count = 1;
							break;
						}
					}
					if (count == 4)
						return true;

					else {
						// if left bottom not have 4 in total
						// loop through right top
						j = temp;
						for (int x = i; x < 6; x++) {
							if (board[x][j] == player && board[x + 1][j + 1] == player) {
								count++;
								if (j + 2 <= 5)
									j++;
								else
									break;
								if (count == 4)
									return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public int canWinNextRound(int player) {
		// ADD YOUR CODE HERE
		for (int i = 0; i < 7; i++) {
			// if (this.isWinning(i, player))
			// return i;
			if (this.available[i] != 6) {
				int a = this.available[i];
				this.addDisk(i, player);

				// System.out.println("Col: " + this.isInCol(i, a, player));
				// System.out.println("Row: " + this.isInRow(i, a, player));
				// System.out.println("Diagonal: " + this.isInDiagonal(i, a, player));
				boolean colWin = this.isInCol(i, a, player);
				boolean rowWin = this.isInRow(i, a, player);
				boolean diaWin = this.isInDiagonal(i, a, player);
				this.removeDisk(i,player);
				if (colWin|| rowWin || diaWin)
					return i;
			}
		}
		return -1; // DON'T FORGET TO CHANGE THE RETURN
	}

	public void removeDisk(int column, int player) {
		for (int i = 5; i >= 0; i--) {
			if (this.board[column][i] == player) {
				this.board[column][i] = 0;
				this.available[column]--;
				break;
			}
		}
	}

	public int canWinTwoTurns(int player) {
		// ADD YOUR CODE HERE
		if (this.canWinNextRound(player) != -1)
			return this.canWinNextRound(player);
		// win within this round
		for (int i = 0; i < 7; i++) {
			if (this.available[i] != 6) {
				this.addDisk(i, player);// add at a free space
				int col1 = this.canWinNextRound(player);
				if (col1 != -1) {// if canWinNextRound
					int colOther = this.canWinNextRound(3 - player);// which column will the other player win at
					if (colOther == -1) {// if the other can't win
						this.addDisk(col1, 3 - player);// the other will try to block
						int canWin2=this.canWinNextRound(player);
						this.removeDisk(col1, 3 - player);
						this.removeDisk(i, player);
						if (canWin2!=-1) {// if blocked; can still win
							return i;
						}
						//this.removeDisk(col1, 3 - player);
						//this.removeDisk(i, player);
					}
					this.removeDisk(col1, 3 - player);
				}
				this.removeDisk(i, player);
			}
		}
		return -1; // DON'T FORGET TO CHANGE THE RETURN
	}
}
