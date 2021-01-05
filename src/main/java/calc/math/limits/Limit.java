package calc.math.limits;

import calc.math.expression.Expression;

/**
 * The Limit class. Implements a math limits and calculate it.
 *
 * @since 0.1
 */
public
class Limit
{

/**
 * Parses a math limit expression and calculates it.
 * <p>
 * In simple math limits just substitute a value instead of variable using the variable name. After
 * this calculate a result of updated expression.
 *
 * @param source a math limit expression.
 * @param variableName variable name (x, n, etc).
 * @param approachTo the limit strives for.
 * @return result of this expression.
 * @throws Exception if an error occurred while parsing this expression or 'Expression.calculate'
 * throws exception.
 */
public static
double calculate(String source, String variableName, double approachTo) throws Exception
{
	String copySource = source.replaceAll(variableName, Double.toString(approachTo));
	double result = Expression.calculate(copySource);
	if(Double.isNaN(result)) {
		// todo: analyze source string and avoid divide by zero
		// todo: l'hopital's rule and other
		return Double.NaN;
	}
	return result;
}
}
