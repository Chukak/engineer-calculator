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
 * Coefficient, which used in approximation to limit.
 */
public static double EPSILON_COEFFICIENT = 0.000000001;
/**
 * Precision of approximation to limit.
 */
public static double APPROXIMATION_PRECISION = 10;

/**
 * Math limits types.
 */
private
enum LimitType
{
	BELOW,
	ABOVE
}

/**
 * Parses a math limit expression and calculates it.
 * <p>
 * In simple math limits just substitute a value instead of variable using the variable name. After
 * this calculate a result of updated expression. To calculating limit using a numerical
 * approximation of a limit. But, at the moment not all math limits calculated correctly.
 *
 * @param source a math limit expression.
 * @param variableName variable name (x, n, etc).
 * @param approachTo the limit strives for.
 * @return result of this expression.
 * @throws Exception if an error occurred while parsing this expression or 'Expression.calculate'
 * throws exception.
 */
public static
double calculate(String source, String variableName, double approachTo, double epsilon) throws
																																												Exception
{
	double limitFromBelow = getLimitFrom(LimitType.BELOW, source, variableName, approachTo);
	double limitFromAbove = getLimitFrom(LimitType.ABOVE, source, variableName, approachTo);
	if(equals(Math.abs(limitFromBelow), Math.abs(limitFromAbove), epsilon)) { return limitFromAbove; }
	return Double.NaN;
}

/**
 * Calculates limit from above or below, using the 'Expression' class.
 *
 * @param type limit type.
 * @param source a math expression.
 * @param variableName variable name (x, n, etc).
 * @param approachTo limit strives for.
 * @return result of calculation this math limit.
 * @throws Exception if an error occurred while parsing this expression or 'Expression.calculate'
 * throws exception.
 */
private static
double getLimitFrom(
		LimitType type, String source, String variableName, double approachTo) throws Exception
{
	int precision = 0;
	for(double d = approachTo; precision < APPROXIMATION_PRECISION;
		/* increment precision */ ++precision) {
		String copySource = source.replaceAll(variableName, Double.toString(d));
		double result = Expression.calculate(copySource);

		if(!Double.isNaN(result)) {
			return result;
		}

		// todo: limits approach to infinity
		// todo: perfect (?) limit
		// todo: 1 ^ infinity

		switch(type) {
		case ABOVE:
			d = d + EPSILON_COEFFICIENT;
			break;
		case BELOW:
			d = d - EPSILON_COEFFICIENT;
			break;
		}
	}
	return Double.NaN;
}

/**
 * Compares two math limits.
 *
 * @param limitFromBelow limit from below, not NaN.
 * @param limitFromAbove limit from above, not NaN.
 * @param epsilon epsilon.
 * @return result of comparison two limits.
 */
private static
boolean equals(double limitFromBelow, double limitFromAbove, double epsilon)
{
	return limitFromAbove == limitFromBelow || Math.abs(limitFromBelow - limitFromAbove) < epsilon;
}
}
