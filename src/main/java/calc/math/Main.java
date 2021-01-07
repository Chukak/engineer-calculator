package calc.math;

import calc.math.expression.Expression;

import java.util.Scanner;

public
class Main
{
public static
void main(String[] args)
{
	System.out.println("Math expression:");
	Scanner cin = new Scanner(System.in);
	try {
		System.out.println("Result: " + Expression.calculate(cin.nextLine()));
	} catch(Exception e) {
		System.out.println("Handled exception: " + e.toString());
	}
}
}
