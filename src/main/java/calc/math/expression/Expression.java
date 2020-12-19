package calc.math.expression;

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
 * The InvalidCharacterInExpression class implements an invalid character error in expression. This
 * class inherits from Exception.
 *
 * @since 0.1
 */
class InvalidCharacterInExpression extends Exception
{
/**
 * The InvalidCharacterInExpression class constructor.
 *
 * @param ch an invalid character.
 * @since 0.1
 */
public
InvalidCharacterInExpression(char ch)
{
	super("Character '" + ch + "' is not numeric type.");
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
	return calculateSubExpression(
			checkParenthesis(checkMathFunctions(expression.replaceAll("\\s", ""))));
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
 * @throws InvalidCharacterInExpression if any not numeric character or invalid operator is found
 * while parsing this expression.
 */
private static
double calculateSubExpression(String source) throws InvalidCharacterInExpression,
																										InvalidMathOperator
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
 * @throws InvalidCharacterInExpression if not numeric character is found while parsing this
 * expression.
 */
private static
NextNumberObject getNextNumber(
		String source, int index) throws InvalidCharacterInExpression
{
	//! a sign before number
	String sign = "";
	StringBuilder numberBuilder = new StringBuilder();

	while(index < source.length()) {
		char ch = source.charAt(index);
		//! integer ot fractional number
		if(Character.isDigit(ch) || ch == '.') {
			numberBuilder.append(sign);
			sign = "";
			numberBuilder.append(ch);
		} else if(ch == '-' && index + 1 < source.length() && numberBuilder.length() == 0) {
			//! stores sign
			numberBuilder.setLength(0);
			sign = String.valueOf(ch);
		} else if(Character.isAlphabetic(ch)) {
			throw new InvalidCharacterInExpression(ch);
		} else { break; }

		++index; // increment index
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

		String subExpression = source.substring(cachedIndex + 1, endFunctionIndex);
		double result =
				applyMathFunction(func.toString(), calculateSubExpression(checkParenthesis(subExpression)));
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
 * @return result of applying this math functions to the passed value.
 * @throws InvalidMathOperator if a math functions is invalid/
 */
private static
double applyMathFunction(String mathFunction, double value) throws InvalidMathOperator
{
	double result;
	switch(mathFunction) {
	case "sqrt":
		result = Math.sqrt(value);
		break;
	default:
		throw new InvalidMathOperator(mathFunction);
	}
	return result;
}
}
