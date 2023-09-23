
public class Polynomial {
	
	public double[] Coefficients;
	
	public Polynomial() {
		this.Coefficients = new double[] {0};
	}
	
	public Polynomial(double[] arr) {
		this.Coefficients = arr.clone();
	}
	
	public Polynomial add(Polynomial another) {
		int new_length = Math.max(this.Coefficients.length, another.Coefficients.length);
		double[] new_arr = new double[new_length];
		for(int i=0; i<this.Coefficients.length; i++) {
			new_arr[i] += this.Coefficients[i];
		}
		for(int i=0; i<another.Coefficients.length; i++) {
			new_arr[i] += another.Coefficients[i];
		}
		Polynomial new_poly = new Polynomial(new_arr);
		return new_poly;
		
	}
	
	public double evaluate(double x) {
		double result = 0;
		double exponent = 0;
		for(int i=0; i<this.Coefficients.length; i++) {
			result += this.Coefficients[i] * Math.pow(x, exponent);
			exponent += 1.0;
		}
		return result;
	}
	
	public boolean hasRoot(double x) {
		return evaluate(x) == 0;
	}
}
