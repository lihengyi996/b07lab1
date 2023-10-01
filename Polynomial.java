import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;

public class Polynomial {
	
	public double[] Coefficients;
	public int[] Exponents;
	
	public Polynomial() {
		this.Coefficients = new double[] {0};
		this.Exponents = new int[] {0};
	}
	
	public Polynomial(double[] coeff, int[] expon) {
		this.Coefficients = coeff.clone();
		this.Exponents = expon.clone();
	}
	
	public Polynomial(File file) throws IOException{
		Scanner scanner = new Scanner(file);
		String equation = scanner.nextLine();
		scanner.close();
		equation = equation.replaceAll("-", "+-");
		String[] terms = equation.split("\\+");
		int j=0;
		double[] coeff = new double[terms.length];
		int[] expon = new int[terms.length];
		for(int i=0; i<terms.length; i++) {
			// e.g. "7.6"
			if(!terms[i].contains("x")) {
				coeff[j] = Double.parseDouble(terms[i]);
				expon[j] = 0;
				j++;
				continue;
			}
			// e.g. "x3" "x"
			else if(terms[i].charAt(0) == 'x') {
				coeff[j] = 1.0;
				String tmp = terms[i].replaceAll("x", "");
				if(tmp.length() == 0) {
					expon[j] = 1;
				} else {
					expon[j] = Integer.parseInt(tmp);
				}
				j++;
				continue;
			}
			// e.g. "-x"
			else if(terms[i].charAt(0) == '-' && terms[i].charAt(1) == 'x' && terms[i].length() == 2){
				coeff[j] = -1.0;
				expon[j] = 1;
				j++;
				continue;
			}
			// e.g. "-x3"
			else if(terms[i].charAt(0) == '-' && terms[i].charAt(1) == 'x') {
				String tmp_str = terms[i].replaceFirst("-", "-1");
				String[] tmp_arr = tmp_str.split("x");
				coeff[j] = Double.parseDouble(tmp_arr[0]);
				expon[j] = Integer.parseInt(tmp_arr[1]);
				j++;
				continue;
			}
			// e.g. "27x"
			else if(terms[i].charAt(terms[i].length() - 1) == 'x') {
				String tmp_str = terms[i].replaceAll("x", "x1");
				String[] tmp_arr = tmp_str.split("x");
				coeff[j] = Double.parseDouble(tmp_arr[0]);
				expon[j] = Integer.parseInt(tmp_arr[1]);
				j++;
				continue;
			}
			// e.g. "35x72"
			else {
				String[] tmp_arr = terms[i].split("x");
				coeff[j] = Double.parseDouble(tmp_arr[0]);
				expon[j] = Integer.parseInt(tmp_arr[1]);
				j++;
				continue;
			}
		}
		
		double[] coeff1 = new double[get_max_expon(expon) + 1];
		for(int i=0; i<coeff1.length; i++) {
			coeff1[i] = 0;
		}
		int[] expon1 = new int[get_max_expon(expon) + 1];
		for(int i=0; i<expon1.length; i++) {
			expon1[i] = i;
		}
		for(int i=0; i<expon.length; i++) {
			coeff1[expon[i]] += coeff[i];
		}
		
		this.Coefficients = coeff1.clone();
		this.Exponents = expon1.clone();
	}
	
	public Polynomial add(Polynomial another) {
		//find the max value of exponents in two arrays
		int max_value1 = get_max_expon(this.Exponents);
		int max_value2 = get_max_expon(another.Exponents);
		int max_value;
		if(max_value1 >= max_value2) max_value = max_value1;
		else max_value = max_value2;
		
		//create a new exponent array to be returned
		int[] new_expon = new int[max_value + 1];
		for(int i=0; i<new_expon.length; i++)
			new_expon[i] = i;
		//create a new coefficient array to be returned
		double[] new_coeff = new double[max_value + 1];
		for(int i=0; i<new_coeff.length; i++)
			new_coeff[i] = 0;
		//modified values in the new coefficient array
		////loop the array that is already stored in the system
		for(int i=0; i<this.Coefficients.length; i++)
			new_coeff[this.Exponents[i]] += this.Coefficients[i];
		////loop another array
		for(int i=0; i<another.Coefficients.length; i++)
			new_coeff[another.Exponents[i]] += another.Coefficients[i];
		//return modified Polynomial
		return new Polynomial(new_coeff, new_expon);
	}
	
	public double evaluate(double x) {
		double result = 0;
		for(int i=0; i<this.Coefficients.length; i++)
			result += this.Coefficients[i] * Math.pow(x, this.Exponents[i]);
		return result;
	}
	
	public boolean hasRoot(double x) {
		return evaluate(x) == 0;
	}
	
	public Polynomial multiply(Polynomial p2) {
		//create a new coefficient array
		double[] new_coeff = new double[this.Coefficients.length * p2.Coefficients.length];
		//initialize it to all zero entries
		for(int i= 0; i<new_coeff.length; i++)
			new_coeff[i] = 0;
		
		//create a new exponent array
		int[] new_expon = new int[this.Coefficients.length * p2.Coefficients.length];
		//initialize it to all zero entries
		for(int i=0; i<new_expon.length; i++) {
			new_expon[i] = 0;
		}
		
		//update elements in new coefficient array
		int i=0;
		for(int j=0; j<this.Coefficients.length; j++) {
			for(int k=0; k<p2.Coefficients.length; k++) {
				new_coeff[i] += this.Coefficients[j] * p2.Coefficients[k];
				i++;
			}
		}
		
		//update elements in new exponent array
		i=0;
		for(int j=0; j<this.Exponents.length; j++) {
			for(int k=0; k<p2.Exponents.length; k++) {
				new_expon[i] += this.Exponents[j] + p2.Exponents[k];
				i++;
			}
		}
		
		//combine the coefficients with the same exponent
		////find the max value
		int max = get_max_expon(new_expon);
		//create a new exponent array
		int[] exponents = new int[max + 1];
		//create a new coefficient array
		double[] coefficients = new double[max + 1];
		//initialize the exponent array
		for(int j=0; j<exponents.length; j++) {
			exponents[j] = j;
		}
		//initialize the coefficient array
		for(int k=0; k<coefficients.length; k++) {
			coefficients[k] = 0;
		}
		//update the values in coefficient array
		for(int m=0; m<new_expon.length; m++) {
			coefficients[new_expon[m]] += new_coeff[m];
		}
		
		return new Polynomial(coefficients, exponents);
	}
	
	public void saveToFile(String file_name) throws IOException{
		double[] c1 = new double[1];
		int[] c2 = new int[1];
		c1[0] = 0.0;
		c2[0] = 0;
		Polynomial p1 = new Polynomial(c1,c2);
		Polynomial p2 = new Polynomial(this.Coefficients, this.Exponents);
		Polynomial p = p2.add(p1);
		this.Coefficients = p.Coefficients;
		this.Exponents = p.Exponents;
		String equation = "";
		for(int i=0; i<this.Coefficients.length; i++) {
			if(this.Coefficients[i] == 0) continue;
			else if(this.Exponents[i] == 0) {
				if(this.Coefficients[i] % 1 == 0) {
					int coeff = (int)this.Coefficients[i];
					equation += String.valueOf(coeff);
				} else {
					equation += String.valueOf(this.Coefficients[i]);
				}
				equation += "+";
			}
			else if(this.Exponents[i] == 1) {
				if(this.Coefficients[i] % 1 == 0 && this.Coefficients[i] != 1 && this.Coefficients[i] != -1) {
					int coeff = (int)this.Coefficients[i];
					equation += String.valueOf(coeff);
				} else if(this.Coefficients[i] != 1 && this.Coefficients[i] != -1){
					equation += String.valueOf(this.Coefficients[i]);
				} else if(this.Coefficients[i] == -1) {
					equation += "-";
				}
				equation += "x";
				equation += "+";
			}
			else {
				if(this.Coefficients[i] % 1 == 0 && this.Coefficients[i] != 1 && this.Coefficients[i] != -1) {
					int coeff = (int)this.Coefficients[i];
					equation += String.valueOf(coeff);
				} else if(this.Coefficients[i] != 1 && this.Coefficients[i] != -1){
					equation += String.valueOf(this.Coefficients[i]);
				} else if(this.Coefficients[i] == -1) {
					equation += "-";
				}
				equation += "x";
				equation += String.valueOf(this.Exponents[i]);
				equation += "+";
			}
		}
		if ((equation != null) && (equation.length() > 0)) {
			equation = equation.substring(0, equation.length() - 1);
		}
		
		equation = equation.replace("+-", "-");
		
		File file = new File(file_name);
		PrintWriter out = new PrintWriter(file);
		out.println(equation);
		out.close();
	}
	
	public int get_max_expon(int[] p) {
		int max = 0;
		for(int i=0; i<p.length; i++)
			if(p[i] >= max) max = p[i];
		return max;
	}

}