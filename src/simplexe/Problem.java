package simplexe;

import java.util.ArrayList;
import java.util.Scanner;

public class Problem {
	private int xNum;
	private int cNum;
	private int type;
	private Matrix pMatrix;
	private ArrayList<Tab> tabs;
	
	public Problem(int xNum, int cNum, int type, Matrix pMatrix) {
		setxNum(xNum);
		setcNum(cNum);
		setType(type);
		setpMatrix(pMatrix);
	}
	public void solveProblem() {
		ArrayList<Tab> tabs = new ArrayList<>();
		System.out.println("Problem matrix");
		getpMatrix().printMatrix();
		System.out.println("Tables");
		tabs.add(new Tab(getpMatrix(), getxNum(), getcNum()));
		tabs.get(0).setaMatrix(tabs.get(0).buildA());
		System.out.println("Table number : 0");
		System.out.println(" Pivot : ( " + tabs.get(0).getPivotL() + " ; " + tabs.get(0).getPivotC() + " )");
		tabs.get(0).getaMatrix().printMatrix();
		for(int index = 1 ; ; index++) {
			Matrix oldMatrix = tabs.get( index - 1).getaMatrix();
			
			tabs.add(new Tab(getpMatrix(), getxNum(), getcNum()));
			tabs.get(index).setaMatrix(tabs.get(index - 1).buildA());
			int cpt = 0;
			for(int j = 0 ; j < tabs.get(index).getaMatrix().getC() ; j++)
				if(tabs.get(index).getaMatrix().getMatrixValue(oldMatrix.getL() - 1, j) > 0) cpt++; 
			if(cpt == 0) {
				for(int i = 0 ; i < getcNum(); i++) {
					tabs.get(index).getaMatrix().setMatrixValue(i, tabs.get(index).getaC() - 1, 0);
				}
				System.out.println("Tableau finale : ");
				System.out.println(" Pivot : ( " + tabs.get(index - 1).getPivotL() + " ; " + tabs.get(index - 1).getPivotC() + " )");
				tabs.get(index).getaMatrix().printMatrix();
				System.out.println("La solution optimale est : Z = " +
				(-tabs.get(index).getaMatrix().getMatrixValue(tabs.get(index).getaL() - 1, tabs.get(index).getaC() -  2)));
				break;
			}
			else {
				System.out.print("Table number : " + index);
				System.out.println(" Pivot : ( " + tabs.get(index - 1).getPivotL() + " ; " + tabs.get(index - 1).getPivotC() + " )");
				tabs.get(index).getaMatrix().printMatrix();
			}

		}
	}
	
	// Getters & Setters
	
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
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Matrix getpMatrix() {
		return pMatrix;
	}
	public void setpMatrix(Matrix pMatrix) {
		this.pMatrix = pMatrix;
	}

	public ArrayList<Tab> getTabs() {
		return tabs;
	}

	public void setTabs(ArrayList<Tab> tabs) {
		this.tabs = tabs;
	}
}
