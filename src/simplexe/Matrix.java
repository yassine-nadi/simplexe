package simplexe;

import java.util.Scanner;

public class Matrix {
	
	Scanner scanner = new Scanner(System.in);

	private float [][]matrix;
	private int l;
	private int c;
	
	public void printMatrix() {
		for(float[] row : getMatrix()) {
			System.out.print(" | ");
			for(float element : row) {
				if(element >= 0) System.out.print(" ");
				System.out.printf("%.1f", element);
				System.out.print(" | ");
			}
			System.out.println();
		}	
	}
	
	public void placeInMatrix(Matrix matrix, int lPos, int cPos) {
		if(matrix.getL() + lPos <= getL() && matrix.getC() + cPos <= getC()){
			int i = lPos;
			for(float[] row : matrix.getMatrix()) {
				int j = cPos;
				for(float element : row) {
					this.matrix[i][j] = element;
					j++;
				}
				i++;
			}
		}
		else {
			System.err.println("Over flow when placing in the matrix");
			System.exit(1);
		}
	}
	Matrix(float [][]matrix, int l, int c){
		setMatrix(matrix);
		setL(l);
		setC(c);
	}
	
	public int getL() {
		return l;
	}
	public void setL(int l) {
		this.l = l;
	}

	public int getC() {
		return c;
	}
	public void setC(int c) {
		this.c = c;
	}
	
	public float getMatrixValue(int l , int c) {
		return this.matrix[l][c];
	}
	public void setMatrixValue(int l, int c, float value) {
		this.matrix[l][c] = value;
	}

	public float [][] getMatrix() {
		return matrix;
	}
	public void setMatrix(float [][] matrix) {
		this.matrix = matrix;
	}

}
