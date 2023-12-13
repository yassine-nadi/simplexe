package simplexe;

import java.util.Scanner;

public class Tab{

	Scanner scanner = new Scanner(System.in);
	private Matrix pMatrix;
	private Matrix aMatrix;
	private int xNum;
	private int cNum;
	private int aL;
	private int aC;
	private int pivotL;
	private int pivotC;
	
//	public void buildR() {
//		calculatePivotC();
//		float [][]rMatrix = new float [getcNum()][1];
//		for(int i = 0 ; i < getcNum() ; i++)
//			rMatrix[i][0] = getcMatrix().getMatrixValue(i, 0) / getxMatrix().getMatrixValue(i, getPivotC());
//		setrMatrix(new Matrix(rMatrix, getcNum(), 1));
//	}
	
	public Matrix buildA() {
		Matrix oldMatrix = getaMatrix();
		float [][]newMatrix = new float [getaL()][getaC()];
		if(oldMatrix == null) {
			// calculate X
			Thread calculateX = new Thread(() -> {
				for(int i = 0 ; i < getcNum() ; i++)
					for(int j = 0 ; j < getxNum() ; j++) 
						newMatrix[i][j] = getpMatrix().getMatrixValue(i, j);
			});
			calculateX.start();
			// calculate C
			Thread calculateC = new Thread(() -> {
				for(int i = 0 ; i < getcNum() ; i++)
					newMatrix[i][getaC() - 2] = getpMatrix().getMatrixValue(i, getxNum() + 1);
			});
			calculateC.start();
			// calculate Z
			Thread calculateZ = new Thread(() -> {
				for(int j = 0 ; j < getxNum() ; j++)
					newMatrix[getaL() - 1][j] = getpMatrix().getMatrixValue(getcNum(), j);
			});
			calculateZ.start();
			// calculate E
			Thread calculateE = new Thread(() -> {
				for(int i = 0 , j = getxNum() ; i < getcNum() ; i++, j++)
					newMatrix[i][j] = getpMatrix().getMatrixValue(i, getxNum());
			});
			calculateE.start();
			//Calculate R
			Thread calculateR = new Thread(() -> {
				calculatePivotCFromP();
				for(int i = 0 ; i < getcNum() ; i++)
					newMatrix[i][getaC() - 1] = getpMatrix().getMatrixValue(i, getxNum() + 1) / getpMatrix().getMatrixValue(i, getPivotC());
			});
			calculateR.start();
			try {
				calculateX.join();
				calculateC.join();
				calculateZ.join();
				calculateE.join();
				calculateR.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new Matrix(newMatrix, getaL(), getaC());
		}
		else {
			//Calculate pivot line & column
			calculatePivotC();
			calculatePivotL();
			// Update pivot line
			Thread updatePivotLine = new Thread(() -> {
				newMatrix[getPivotL()][getPivotC()] = 1;
				for(int j = 0 ; j < oldMatrix.getC() - 1 ; j++) {
					if(j == getPivotC()) continue;
					float newValue = oldMatrix.getMatrixValue(getPivotL(), j) /
							oldMatrix.getMatrixValue(getPivotL(), getPivotC()); 
					newMatrix[getPivotL()][j] = newValue;
				}
			});
			updatePivotLine.start();
			// Update other cells
			Thread updateOthers = new Thread(() -> {
				for(int i = 0 ; i < oldMatrix.getL(); i++) {
					if(i == getPivotL()) continue;
					for(int j = 0 ; j < oldMatrix.getC() - 1 ; j++) {
						if(j == getPivotC()) continue;
						float newValue = 
								oldMatrix.getMatrixValue(i, j) - 
								(oldMatrix.getMatrixValue(i, getPivotC()) * oldMatrix.getMatrixValue(getPivotL(), j)) / 
								oldMatrix.getMatrixValue(getPivotL(), getPivotC()); 
						newMatrix[i][j] = newValue;
					}
				}
			});
			updateOthers.start();
			try {
				updatePivotLine.join();
				updateOthers.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// calculate PivotC
			float maxValue = newMatrix[getaL() - 1][0];
				int pivotC = 0;
				for(int j = 1  ; j < getaC() ; j++) {
					if(newMatrix[getcNum()][j] > maxValue) {
						maxValue = newMatrix[getcNum()][j];
						pivotC = j;
					}
				}
			//calculate R
			for(int i = 0 ; i < getaL() - 1 ; i++) {
				float newValue = newMatrix[i][getaC() - 2] /
						newMatrix[i][pivotC]; 
				newMatrix[i][getaC() - 1] = newValue;
			}
			
			return new Matrix(newMatrix, getaL(), getaC());
		}
	}
	
	public void calculatePivotCFromP() {
		float maxValue = getpMatrix().getMatrixValue(getcNum(), 0);
		int maxIndex = 0;
		for(int j = 1  ; j < getxNum() ; j++) {
			if(getpMatrix().getMatrixValue(getcNum(), j) > maxValue) {
				maxValue = getpMatrix().getMatrixValue(getcNum(), j);
				maxIndex = j;
			}
		}
		setPivotC(maxIndex);
	}
	
	public void calculatePivotC() {
		float maxValue = getaMatrix().getMatrixValue(getaL() - 1, 0);
		int maxIndex = 0;
		for(int j = 1  ; j < getaMatrix().getC() ; j++) {
			if(getaMatrix().getMatrixValue(getcNum(), j) > maxValue) {
				maxValue = getaMatrix().getMatrixValue(getcNum(), j);
				maxIndex = j;
			}
		}
		setPivotC(maxIndex);
	}

	public void calculatePivotL() {
		float minValue = 0;
		int minIndex = 0;
		for(int i = 0 ; i < getaL() ; i++) {
			if(getaMatrix().getMatrixValue(i, getaC() - 1) > 0) {
				minValue = getaMatrix().getMatrixValue(i, getaC() - 1);
				minIndex = i;
				break;
			}
		}
		for(int i = 0 ; i < getaL() - 1 ; i++) {
			if(getaMatrix().getMatrixValue(i, getaC() - 1) > 0) {
				if(getaMatrix().getMatrixValue(i, getaC() - 1 ) < minValue) {
					minValue = getaMatrix().getMatrixValue(i, getaC() - 1);
					minIndex = i;
				}
			}
		}
		if(minValue == 0)	System.err.println("Error, Can not calculate pivot!");
		else	setPivotL(minIndex);	
	}
	
	// Getters & Setters
	Tab(Matrix pMatrix, int xNum, int cNum){
		setpMatrix(pMatrix);
		setxNum(xNum);
		setcNum(cNum);
		setaL(getcNum() + 1);
		setaC(getxNum() + getcNum() + 2);
	}
	public Matrix getpMatrix() {
		return pMatrix;
	}

	public void setpMatrix(Matrix pMatrix) {
		this.pMatrix = pMatrix;
	}

	public Matrix getaMatrix() {
		return aMatrix;
	}
	
	public void setaMatrix(Matrix aMatrix) {
		this.aMatrix = aMatrix;
	}

	public int getxNum() {
		return xNum;
	}

	public void setxNum(int xNum) {
		this.xNum = xNum;
	}
	
	public int getcNum() {
		return cNum;
	}
	
	public void setcNum(int cNum) {
		this.cNum = cNum;
	}

	public int getaL() {
		return aL;
	}
	
	public void setaL(int aL) {
		this.aL = aL;
	}
	
	public int getaC() {
		return aC;
	}
	public void setaC(int aC) {
		this.aC = aC;
	}
	
	public int getPivotL() {
		return pivotL;
	}

	public void setPivotL(int pivotL) {
		this.pivotL = pivotL;
	}

	public int getPivotC() {
		return pivotC;
	}

	public void setPivotC(int pivotC) {
		this.pivotC = pivotC;
	}
}
