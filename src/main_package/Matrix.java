package main_package;

import java.math.BigDecimal;
import java.util.Random;
import java.util.function.Function;

public class Matrix {
	
	public int rows, cols;
	
	public double[][] data;
	
	public Random rand = new Random();
	public Matrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		
		data = new double[cols][rows];
		
		for (int i = 0; i < rows; i++) {
			for ( int j = 0; j < cols; j++ ) {
				data[j][i] = 0;
			}
		}
	}
	
	public void display() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.print(data[j][i] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static Matrix fromArray(double[] array) {
		Matrix result = new Matrix(array.length, 1);
		
		for (int i = 0; i < array.length; i++) {
			result.data[0][i] = array[i];
		}
		
		return result;
	}
	
	public double[] toArray() {
		double[] result = new double[rows * cols];
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				result[i * cols + j] = data[j][i];
			}
		}
		
		return result;
	}
	
	public void randomize() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				data[j][i] = rand.nextDouble() * 2d - 1d;
			}
		}
	}
	
	public static Matrix subtract(Matrix a, Matrix b) {
		if (a.rows != b.rows || a.cols != b.cols) {
			System.out.println("Columns or rows of A does not match columns or rows of B!");
			return null;
		}
		Matrix result = new Matrix(a.rows, a.cols);
		for (int i = 0; i < a.rows; i++) {
			for (int j = 0; j < a.cols; j++) {
				result.data[j][i] = a.data[j][i] - b.data[j][i];
			}
		}
		return result;
	}
	
	public void add(Object n) {
		if (n instanceof Matrix) {
			Matrix m = (Matrix)n;
			
			if (m.rows != rows || m.cols != cols) {
				System.out.println("Columns or rows of A does not match columns or rows of B!");
			} else {
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						BigDecimal this_data_bd = new BigDecimal(data[j][i]);
						BigDecimal m_data_bd = new BigDecimal(m.data[j][i]);
						//data[j][i] += m.data[j][i];
						data[j][i] = this_data_bd.add(m_data_bd).doubleValue();
					}
				}
			}
		} else {
			BigDecimal m = new BigDecimal((double)n);
			
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					BigDecimal this_data_bd = new BigDecimal(data[j][i]);
					data[j][i] = this_data_bd.add(m).doubleValue();
				}
			}
		}
	}
	
	public static Matrix multiply(Matrix a, Matrix b) {
		if(a.cols != b.rows) {
			System.out.println("Columns of A does not match rows of B!");
			return null;
		}
		Matrix result = new Matrix(a.rows, b.cols);
		
		for (int i = 0; i < result.rows; i++) {
			for (int j = 0; j < result.cols; j++) {
				double sum = 0;
				for (int k = 0; k < a.cols; k++) {
					sum += a.data[k][i] * b.data[j][k];
					//System.out.println(sum);
				}
				result.data[j][i] = sum;
			}
		}
		return result;
	}
	
	public Matrix multiply(Object n) {
		if (n instanceof Matrix) {
			Matrix m = (Matrix)n;
			if (m.rows != rows || m.cols != cols) {
				System.out.println("Columns of A does not match rows of B!");
				return null;
			}
			
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					data[j][i] *= m.data[j][i];
				}
			}
			return null;
		} else {
			BigDecimal m = new BigDecimal((double)n);
			Matrix result = new Matrix(rows, cols);
			
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					BigDecimal this_data_bd = new BigDecimal(data[j][i]);
					//data[j][i] *= (double)n;
					result.data[j][i] = this_data_bd.multiply(m).doubleValue();
				}
			}
			return result;
		}
	}
	
	public static Matrix transpose(Matrix m) {
		Matrix result = new Matrix(m.cols, m.rows);
		
		for (int i = 0; i < m.rows; i++) {
			for (int j = 0; j < m.cols; j++) {
				result.data[i][j] = m.data[j][i];
			}
		}
		
		return result;
	}
	
	public static Matrix map(Matrix m, Function<Double, Double> fn) {
		Matrix result = new Matrix(m.rows, m.cols);
		for (int i = 0; i < result.rows; i++) {
			for (int j = 0; j < result.cols; j++) {
				result.data[j][i] = fn.apply(m.data[j][i]);
			}
		}
		return result;
	}
}
