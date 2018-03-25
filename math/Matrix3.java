package math;

public class Matrix3 {
	private double[][] matrix;

	public Matrix3() {
		matrix = new double[][] { { 1.0, 0.0, 0.0 }, { 0.0, 1.0, 0.0 }, { 0.0, 0.0, 1.0 } };
	}

	public Matrix3(double[][] array) {
		matrix = new double[3][3];
		matrix[0][0] = array[0][0];
		matrix[0][1] = array[0][1];
		matrix[0][2] = array[0][2];

		matrix[1][0] = array[1][0];
		matrix[1][1] = array[1][1];
		matrix[1][2] = array[1][2];

		matrix[2][0] = array[2][0];
		matrix[2][1] = array[2][1];
		matrix[2][2] = array[2][2];
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("M3x3:\n");
		for (int i = 0; i < 3; ++i) {
			if (i != 0)
				sb.append("\n");
			for (int j = 0; j < 3; ++j)
				sb.append(String.format("    %8.2f ", matrix[i][j]));
		}
		return sb.toString();
	}

	public static Point3 times(Matrix3 m, Point3 p) {
		double[] np = new double[3];
		for (int i = 0; i < 3; ++i)
			np[i] = m.matrix[i][0] * p.getX() + m.matrix[i][1] * p.getY() + m.matrix[i][2] * p.getW();
		return new Point3((int) np[0], (int) np[1], (int) np[2]);
	}

	public static Matrix3 times(Matrix3 a, Matrix3 b) {
		double[][] nm = new double[3][3];
		for (int k = 0; k < 3; ++k)
			for (int i = 0; i < 3; ++i)
				for (int j = 0; j < 3; ++j)
					nm[i][j] += a.matrix[i][k] * b.matrix[k][j];
		return new Matrix3(nm);
	}

	public static Matrix3 rotationMatrix(double theta) {
		return new Matrix3(new double[][] { { Math.cos(theta), -Math.sin(theta), 0.0 },
				{ Math.sin(theta), Math.cos(theta), 0.0 }, { 0.0, 0.0, 1.0 } });
	}

	public static Matrix3 scalingMatrix(double sx, double sy) {
		return new Matrix3(new double[][] { { sx, 0.0, 0.0 }, { 0.0, sy, 0.0 }, { 0.0, 0.0, 1.0 } });
	}

	public static Matrix3 translationMatrix(double dx, double dy) {
		return new Matrix3(new double[][] { { 1.0, 0.0, dx }, { 0.0, 1.0, dy }, { 0.0, 0.0, 1.0 } });

	}

}
