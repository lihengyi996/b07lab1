import java.io.File;
import java.io.IOException;

public class Driver {
	public static void main(String [] args) throws IOException {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		System.out.println("********addition operation***********");
		double [] a1 = {2,2,3,8,1,2};
		int [] a2 = {1,1,1,1,1,1};
		Polynomial p1 = new Polynomial(a1, a2);
		
		double [] b1 = {1,1,3};
		int [] b2 = {1,1,2};
		Polynomial p2 = new Polynomial(b1, b2);
		
		Polynomial s=p1.add(p2);
		/*
		double [] c2 = {0,-2,0,0,-9};
		Polynomial p2 = new Polynomial(c2);
		Polynomial s = p1.add(p2);
		*/
		for(int i=0; i<s.Coefficients.length; i++) {
			System.out.println(s.Coefficients[i]);
			System.out.println(s.Exponents[i]);
		}
		System.out.println("length of coeff " + s.Coefficients.length);
		System.out.println("length of expon " + s.Exponents.length);
		System.out.println("s(2) = " + s.evaluate(2));
		
		System.out.println("********multiplication operation***********");
		double[] c1 = {2,3,4,1,1};
		int[] c2 = {3,2,1,0,3};
		Polynomial cp3 = new Polynomial(c1, c2);
		double[] d1 = {5,6,7,8};
		int[] d2 = {0,1,3,0};
		Polynomial cp4 = new Polynomial(d1, d2);
		Polynomial m = cp3.multiply(cp4);
		for(int i=0; i<m.Coefficients.length; i++) {
			System.out.println("coef:"+ m.Coefficients[i]+", expon:" + m.Exponents[i]);
		}
		System.out.println("length of coeff " + m.Coefficients.length);
		System.out.println("length of expon " + m.Exponents.length);
		System.out.println("m(2) = " + m.evaluate(2));
		
		System.out.println("********file test***********");
		File file = new File("C:\\Users\\rippl\\Desktop\\ProjectForJava\\lab1\\b07lab1\\polynomial.txt");
		Polynomial t = new Polynomial(file);
		System.out.println("length of coeff " + t.Coefficients.length);
		System.out.println("length of expon " + t.Exponents.length);
		for(int i=0; i<t.Coefficients.length; i++) {
			System.out.println("coef:"+ t.Coefficients[i]+", expon:" + t.Exponents[i]);
		}
		
		System.out.println("*************save to file*************");
		double[] e1 = {7.6,-3,14,-251,-1,1};
		int[] e2 = {0,1,3,14,1,3};
		Polynomial f = new Polynomial(e1, e2);
		for(int i=0; i<f.Coefficients.length; i++) {
			System.out.println("coeff: " + f.Coefficients[i] + " expon: " + f.Exponents[i]);
		}
		f.saveToFile("new.txt");
	}
}
