package simplexe;

import java.util.Scanner;

public class main {
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		Matrix pMatrix = new Matrix(new float[][] = 
			{{1, 2, 3, 4},
			{1, 2, 3, 4},
			{1, 2, 3, 4},
			{1, 2, 3, 4}}
		, 4, 4);
		Problem problem = new Problem(2, 4, 1, pMatrix);
		problem.solveProblem();
		
	}

}
