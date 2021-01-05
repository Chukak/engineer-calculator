package calc.math.expression;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The MissedParenthesis class implements the missing parenthesis error. This class inherits from
 * Exception.
 *
 * @since 0.1
 */
class MissedParenthesis extends Exception
{
/**
 * The MissedParenthesis class constructor.
 *
 * @param expression an math expression.
 * @since 0.1
 */
public
MissedParenthesis(String expression)
{
	super("Closed parenthesis not found in '" + expression + "'.");
}
}

/**
 * The InvalidStringInExpression class implements an invalid string (variable name, infinity, etc)
 * error in expression. This class inherits from Exception.
 *
 * @since 0.1
 */
class InvalidStringInExpression extends Exception
{
/**
 * The InvalidStringInExpression class constructor.
 *
 * @param s an invalid string.
 * @since 0.1
 */
public
InvalidStringInExpression(String s)
{
	super("String '" + s + "' is not numeric type or not allowed.");
}
}

/**
 * The InvalidMathOperator class implements an invalid math operator error in expression. This class
 * inherits from Exception.
 *
 * @since 0.1
 */
class InvalidMathOperator extends Exception
{
/**
 * The InvalidMathOperator class constructor.
 *
 * @param operator an invalid operator
 * @since 0.1
 */
public
InvalidMathOperator(String operator) { super("Invalid math operator '" + operator + "'."); }
}

/**
 * The Expression class. Gets input expression as a String type. Parses this expression and
 * calculates result of a parsed expression.
 *
 * @since 0.1
 */
public
class Expression
{
/**
 * Priorities of math operators.
 */
private
enum OperatorPriority
{
	High, //! (*, /, etc...)
	Low //! (+, -, etc...)
}


public static final double PI = Math.PI; //! PI, math constant
public static final double E = Math.E; //! E, math constant

private static final HashMap<String, Double> CONSTANTS = new HashMap<>()
{{
	put("PI", PI);
	put("e", E);
}};

private static final String[] ALLOWED_STRINGS = { "Infinity", //! Positive infinity
																									"-Infinity" //! Negative infinity
};

/**
 * The NextNumberObject class. Stores a result from method getNextNumber.
 */
private final static
class NextNumberObject
{
	public int Index; //! Index
	public String Number; //! Number

	public
	NextNumberObject(int index, String number)
	{
		Index = index;
		Number = number;
	}
}

/**
 * The MathOperatorObject class. Stores a result from method getOperator.
 */
private final static
class MathOperatorObject
{
	public int Index; //! Index
	public String Operator; //! Operator

	public
	MathOperatorObject(int index, String operator)
	{
		Index = index;
		Operator = operator;
	}
}

/**
 * Parses a math expression and calculates result it's result.
 *
 * @param expression a math expression.
 * @return result of this expression.
 * @throws Exception if an error occurred while parsing this expression.
 */
public static
double calculate(String expression) throws Exception
{
	expression = checkMathConstants(expression.replaceAll("\\s", ""));
	return calculateSubExpression(checkParenthesis(checkMathFunctions(expression)));
}

/**
 * Searches parenthesis in an math expression and parses substring within those parentheses.
 *
 * @param source a math expression.
 * @return an expression without any parentheses.
 * @throws MissedParenthesis if a closed parenthesis not found in this expression.
 * @throws Exception if a another error occurred.
 */
private static
String checkParenthesis(String source) throws Exception
{
	int firstParenthesisIndex, secondParenthesisIndex;
	//! looking for the closed parenthesis
	while((secondParenthesisIndex = source.indexOf(')')) != -1) {
		//! looking for the open parenthesis, using index of the found closed parenthesis
		if((firstParenthesisIndex = source.lastIndexOf('(', secondParenthesisIndex)) == -1) {
			throw new MissedParenthesis(source);
		}

		//! calculate result of expression within those parentheses
		String subExpression = source.substring(firstParenthesisIndex + 1, secondParenthesisIndex);
		double calculatedParenthesis = calculateSubExpression(subExpression);
		//! concatenate expressions
		source = source.substring(0, firstParenthesisIndex) // before open parenthesis
				+ calculatedParenthesis +                      // calculated expression
				source.substring(secondParenthesisIndex + 1);   // after closed parenthesis
	}

	//! checks opened parenthesis, if closed parenthesis not found
	if(source.indexOf('(') != -1) {
		throw new MissedParenthesis(source);
	}

	return source;
}

/**
 * Calculates an math expression without any parenthesis.
 *
 * @param source an expression math.
 * @return result of this expression.
 * @throws InvalidStringInExpression if any not allowed string or invalid operator is found while
 * parsing this expression.
 */
private static
double calculateSubExpression(String source) throws InvalidStringInExpression, InvalidMathOperator
{
	int index = 0; // current index in expression
	double result;
	{
		//! gets a next number in expression
		NextNumberObject nextNumberObj = getNextNumber(source, index);
		//! stores a new index and number
		index = nextNumberObj.Index;
		//! parses a first number
		result = Double.parseDouble(nextNumberObj.Number);
	}

	//! parses an each character in expression
	while(index < source.length()) {
		double currentValue;
		//! gets next character
		String operator;
		{
			MathOperatorObject mathOperatorObj = getOperator(source, index);
			index = mathOperatorObj.Index;
			operator = mathOperatorObj.Operator;
		}
		{
			//! increment index
			NextNumberObject nextNumberObj = getNextNumber(source, index + 1);
			index = nextNumberObj.Index;
			//! parses a second number
			currentValue = Double.parseDouble(nextNumberObj.Number);
		}

		/*
		 * Now, we will parse the next operator and his priority. If the next operator has a high priority,
		 * calculate the current value and the next value primarily.
		 *
		 * */
		int nextIndex = index;
		while(nextIndex < source.length()) {
			String nextOperator;
			{
				MathOperatorObject mathOperatorObj = getOperator(source, nextIndex);
				nextIndex = mathOperatorObj.Index;
				nextOperator = mathOperatorObj.Operator;
			}
			if(getOperatorPriority(nextOperator) == OperatorPriority.Low) {
				//! if priority os Low, break this loop and calculate the current value.
				break;
			}

			NextNumberObject nextNumberObj =
					getNextNumber(source, nextIndex + 1); // increment the next index
			nextIndex = nextNumberObj.Index;
			//! parses a second number
			double nextValue = Double.parseDouble(nextNumberObj.Number);
			//! updates the current value
			currentValue = applyOperator(currentValue, nextValue, nextOperator);
		}
		index = nextIndex;

		result = applyOperator(result, currentValue, operator);
	}
	return result;
}

/**
 * Applies an operator to passed values.
 *
 * @param a the first value.
 * @param b the second value.
 * @param operator a math operator.
 * @return result of applying this operator to two values.
 * @throws InvalidMathOperator if operator is invalid.
 */
private static
double applyOperator(double a, double b, String operator) throws InvalidMathOperator
{
	//! checks an operator
	switch(operator) {
	case "+":
		a += b;
		break;
	case "-":
		a -= b;
		break;
	case "/":
		a /= b;
		break;
	case "*":
		a *= b;
		break;
	case "%":
		a %= b;
		break;
	case "^":
		a = Math.pow(a, b);
		break;
	default:
		// todo: other operators (sqrt, etc...)
		throw new InvalidMathOperator(operator);
	}
	return a;
}

/**
 * Gets the next number.
 *
 * @param source an expression.
 * @param index current index.
 * @return NextNumberObject, which contains the new index and the next number.
 * @throws InvalidStringInExpression if not numeric character is found while parsing this
 * expression.
 */
private static
NextNumberObject getNextNumber(
		String source, int index) throws InvalidStringInExpression
{

	StringBuilder numberBuilder = new StringBuilder();
	boolean hasAlphabetic = false;

	while(index < source.length()) {
		char ch = source.charAt(index);
		//! integer ot fractional number
		if(Character.isDigit(ch) || ch == '.') {
			numberBuilder.append(ch);
		} else if(ch == '-' && index + 1 < source.length() && numberBuilder.length() == 0) {
			//! a sign before number
			numberBuilder.setLength(0);
			numberBuilder.append(ch);
		} else if(Character.isAlphabetic(ch)) {
			numberBuilder.append(ch);
			hasAlphabetic = true;
		} else { break; }

		++index; // increment index
	}

	// check if string is allowed in expression
	if(hasAlphabetic && Arrays.stream(ALLOWED_STRINGS)
														.noneMatch(x -> x.equals(numberBuilder.toString()))) {
		throw new InvalidStringInExpression(numberBuilder.toString());
	}

	//! avoid a java.Exception when Double.parseDouble("")
	// todo: we really need it?
	if(numberBuilder.length() == 0) {
		numberBuilder.append(0);
	}
	return new NextNumberObject(index, numberBuilder.toString());
}

/**
 * Gets the math operator after index.
 *
 * @param source an expression
 * @param index current index
 * @return MathOperatorObject object, which contains the new index and a math operator.
 */
private static
MathOperatorObject getOperator(String source, int index)
{
	StringBuilder builderOperator = new StringBuilder();
	char nextChar = source.charAt(index);

	if(builderOperator.length() == 0 && nextChar != Character.MIN_VALUE) {
		builderOperator.append(nextChar);
	}

	return new MathOperatorObject(index, builderOperator.toString());
}

/**
 * Check operator priority.
 *
 * @param operator operator.
 * @return operator priority.
 */
private static
OperatorPriority getOperatorPriority(String operator)
{
	switch(operator) {
	case "*":
	case "/":
	case "^":
	case "%":
		return OperatorPriority.High;
	case "+":
	case "-":
		// see return statement
	}
	return OperatorPriority.Low;
}

/**
 * Searches parenthesis ('[]') for any math functions and parses substring within those parenthesis
 * and applies a math function to the parsed substring.
 *
 * @param source a math expression.
 * @return an expression without any math functions.
 * @throws MissedParenthesis if a closed parenthesis not found in this expression.
 * @throws Exception if a another error occurred.
 */
private static
String checkMathFunctions(String source) throws Exception
{
	int startFunctionIndex, endFunctionIndex;
	//! looking for the closed parenthesis, only '['
	while((endFunctionIndex = source.indexOf(']')) != -1) {
		if((startFunctionIndex = source.lastIndexOf('[', endFunctionIndex)) == -1) {
			throw new MissedParenthesis(source);
		}

		int cachedIndex = startFunctionIndex; // store index
		StringBuilder func = new StringBuilder();
		for(int i = cachedIndex - 1; // from open parenthesis
				i >= 0; // to the beginning of the string
				--i, --startFunctionIndex // decrement index, because we need skip math function when concatenate expression
		) {
			char ch = source.charAt(i);
			if(Character.isAlphabetic(ch)) {
				func.insert(0, ch);
			} else { break; }
		}

		String funcParams = "";
		int startParamsFunctionIndex; // index of the first ',', the first parameter
		if((startParamsFunctionIndex = source.indexOf(',', startFunctionIndex)) != -1) {
			funcParams = source.substring(startParamsFunctionIndex + 1, endFunctionIndex);
		} else {
			startParamsFunctionIndex = endFunctionIndex; // no parameters
		}

		// get only expression without parameters
		String subExpression = source.substring(cachedIndex + 1, startParamsFunctionIndex);

		double result =
				applyMathFunction(func.toString(), calculateSubExpression(checkParenthesis(subExpression)),
													funcParams);
		source =
				source.substring(0, startFunctionIndex) + result + source.substring(endFunctionIndex + 1);
	}

	if(source.indexOf('[') != -1) {
		throw new MissedParenthesis(source);
	}

	return source;
}

/**
 * Applies a math functions to the passed value.
 *
 * @param mathFunction a math function.
 * @param value the passed value.
 * @param funcParam parameters fot this math function
 * @return result of applying this math functions to the passed value.
 * @throws InvalidMathOperator if a math functions is invalid/
 */
private static
double applyMathFunction(String mathFunction, double value, String funcParam) throws
																																							InvalidMathOperator
{
	double result;
	String[] params = {};
	if(!funcParam.isEmpty()) {
		params = funcParam.split(",", -1);
	}

	switch(mathFunction) {
	case "sin":
	case "cos":
	case "tg":
	case "ctg":
		// replace degree to radians
		if(params.length >= 1 && params[0].equals("deg") /* degrees */) {
			value = Math.toRadians(value);
		}
	}

	switch(mathFunction) {
	case "sqrt":
		double nth = 2.0;
		if(params.length >= 1) {
			nth = Double.parseDouble(params[0]);
		}

		result = Math.pow(value, 1.0 / nth); // sqrt = v ^ (1 / n), right?
		break;
	case "sin":
		result = Math.sin(value);
		break;
	case "cos":
		result = Math.cos(value);
		break;
	case "tg":
		result = Math.tan(value);
		break;
	case "ctg":
		result = 1.0 / Math.tan(value);
		break;
	case "ln":
		result = Math.log(value);
		break;
	case "log":
		double logBase = 10.0; // base 10 by default
		if(params.length >= 1) {
			logBase = Double.parseDouble(params[0]);
		}

		result = Math.log(value) / Math.log(logBase);
		break;
	default:
		throw new InvalidMathOperator(mathFunction);
	}
	return result;
}

/**
 * Searches math constants and replace them.
 *
 * @param source a math expression
 * @return an expression with values of math constants.
 */
private static
String checkMathConstants(String source)
{
	for(Map.Entry<String, Double> entry : CONSTANTS.entrySet()) {
		String key = entry.getKey();
		Double value = entry.getValue();
		// if there is a math func, which contains any character similar a math constant, do not replace it!
		String regex = "(?<![a-zA-Z])" + key + "(?![a-zA-Z])";
		source = source.replaceAll(regex, value.toString());
	}
	return source;
}
}
