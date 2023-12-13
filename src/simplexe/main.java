package simplexe;

import java.util.Scanner;

public class main {
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		
		float [][]pArray = new float [][] {
			{-3, 2, 1, 2},
			{-1, 2, 1, 4},
			{1, 1, 1, 5},
			{1, 2, 0, 0}
		};
		Matrix pMatrix = new Matrix(pArray, 4, 4);
		Problem problem = new Problem(2, 3, 1, pMatrix);
		problem.solveProblem();
		
	}

}
