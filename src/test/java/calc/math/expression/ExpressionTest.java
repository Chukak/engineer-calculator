package calc.math.expression;

import org.junit.Test;

import static org.junit.Assert.*;

public
class ExpressionTest
{
static private final double EPSILON = 0.00000001;

/**
 * Testing a simple math expressions.
 */
@Test
public
void testSimpleExpressionsWithSimpleOperators()
{
	try {
		assertEquals("Sum of numbers", 3, Expression.calculate("1 + 2"), EPSILON);
		assertEquals("Difference of numbers", 2, Expression.calculate("5 - 3"), EPSILON);
		assertEquals("Multiply of numbers", 81, Expression.calculate("9 * 9"), EPSILON);
		assertEquals("Division of numbers", 6, Expression.calculate("120 / 20"), EPSILON);
		assertEquals("Math expression with parenthesis №1", 8, Expression.calculate("2 * (1 + 3)"),
								 EPSILON);
		assertEquals("Math expression with parenthesis №2", 3, Expression.calculate("(7 - 1) / 2"),
								 EPSILON);
		assertEquals("Math expression with parenthesis №3", -5,
								 Expression.calculate("-10 + (5 + (5 - 5))"), EPSILON);
		assertEquals("Math expression №4", 20, Expression.calculate("9 * 2 + 2"), EPSILON);
		assertEquals("Math expression with parenthesis №5", 16, Expression.calculate("(512 / 8) / 4"),
								 EPSILON);
		assertEquals("Math expression №6", 12, Expression.calculate("2 * 2 / 1 * 5 / 2 + 3 - 1"),
								 EPSILON);
		assertEquals("Math expression №7", Double.POSITIVE_INFINITY,
								 Expression.calculate("24 * 2 / (8 - 8)"), EPSILON);
		assertEquals("Math expression №8", 0, Expression.calculate(""), EPSILON);
		assertEquals("Math expression with fractional number №9", 74.78358619, // see EPSILON
								 Expression.calculate("(45.65 * 2.9) / 2.1 + 7.74321 - 1.0001 + 5"), EPSILON);
		assertEquals("Math expression №10", 16, Expression.calculate("2 ^ 4"), EPSILON);
		assertEquals("Math expression №11", 1, Expression.calculate("5 % 4"), EPSILON);
		assertEquals("Math expression №12 with sqrt", 2, Expression.calculate("sqrt[2 + 2]"), EPSILON);
		assertEquals("Math expression №13 with sqrt", 5,
								 Expression.calculate("sqrt[(6 + 2 + 3) + 5 + 8 - ((1 + 2) + 6) + 10]"), EPSILON);
		assertEquals("Math expression №14 with sin", 0, Expression.calculate("sin[0]"), EPSILON);
		assertEquals("Math expression №15 with cos", 9, Expression.calculate("8 + cos[0] * 1"),
								 EPSILON);
		assertEquals("Math expression №16 with tg", 1.03553031, Expression.calculate("tg[226]"),
								 EPSILON);
		assertEquals("Math expression №17 with ctg", 0.67450851, Expression.calculate("ctg[56]"),
								 EPSILON);
		assertEquals("Math expression №18 with ln", 4.60517018, Expression.calculate("ln[100]"),
								 EPSILON);
		assertEquals("Math expression №19 with sqrt3", 3, Expression.calculate("sqrt[27, 3]"), EPSILON);
		assertEquals("Math expression №20 with log2", 3, Expression.calculate("log[(2 * 4), 2]"),
								 EPSILON);
		assertEquals("Math expression №21 with math constants", 1, Expression.calculate("sin[PI/2]"),
								 EPSILON);
		assertEquals("Math expression №22 with math constants", 1.64872127,
								 Expression.calculate("sqrt[e^1]"), EPSILON);
	} catch(Exception e) {
		fail("Handled exception: " + e.toString());
	}
}

/**
 * Testing a hard math expressions.
 */
@Test
public
void testHardExpressionsWithSimpleOperators()
{
	try {
		assertEquals("Hard math expression №1", 1184, Expression
										 .calculate("(((8 * 8) + (512 / 2) * 8 / (4 * 2)) + ((-1) * 8 * (256 * (-1)))) / 2"),
								 EPSILON);
		assertEquals("Hard math expression №2", -94, Expression.calculate(
				"(-128 / 2 + (2 - (4 + (8 + (-1 + (-3 * 2) / (-1 + 1 * (5 - 15) / (5125 / 1025) * 2 + (-128) - (1 * (-1) - 1 ) + 128) - 64) / (-1) * -1) + (-5 + 10 - 10 + 18) + (1  +2 + 3 + 4 - 8) - (-64)) + 2 * (-4) / 2))"),
								 EPSILON);
		assertEquals("Hard math expression №3 with sqrt", 122, Expression
				.calculate("127 - sqrt[0 + sqrt[(24 * 25 + 5 * 8) / 8 + 1] + sqrt[sqrt[65536]]]"), EPSILON);
	} catch(Exception e) {
		fail("Handled exception: " + e.toString());
	}
}

@Test
public
void testThrowInvalidCharacterInExpression()
{
	try {
		Expression.calculate("2 / a");
	} catch(Exception calcError) {
		assertTrue("Throw InvalidCharacterInExpression",
							 calcError instanceof InvalidCharacterInExpression);
	}
	try {
		Expression.calculate("2 / 2 $ 3"); // invalid operator
	} catch(Exception calcError) {
		assertTrue("Throw InvalidMathOperator", calcError instanceof InvalidMathOperator);
	}
	try {
		Expression.calculate("sert[4]"); // invalid operator
	} catch(Exception calcError) {
		assertTrue("Throw InvalidMathOperator", calcError instanceof InvalidMathOperator);
	}
	// do not replace math constant, if esin - math function (suppose)
	try {
		Expression.calculate("sqrt[esin ^ 2]");
	} catch(Exception calcError) {
		assertTrue("Throw InvalidMathOperator", calcError instanceof InvalidCharacterInExpression);
	}
}

@Test
public
void testThrowMissedParenthesis()
{
	try {
		Expression.calculate("(2 + 2");
	} catch(Exception calcError) {
		assertTrue("Throw MissedParenthesis", calcError instanceof MissedParenthesis);
	}
	try {
		Expression.calculate("2 + 2)");
	} catch(Exception calcError) {
		assertTrue("Throw MissedParenthesis", calcError instanceof MissedParenthesis);
	}
	try {
		Expression.calculate("(2 + 2 * (1 + (1 + 2 + 3))");
	} catch(Exception calcError) {
		assertTrue("Throw MissedParenthesis", calcError instanceof MissedParenthesis);
	}
	try {
		Expression.calculate("sqrt[9 * 9)");
	} catch(Exception calcError) {
		assertTrue("Throw MissedParenthesis", calcError instanceof MissedParenthesis);
	}
}
}