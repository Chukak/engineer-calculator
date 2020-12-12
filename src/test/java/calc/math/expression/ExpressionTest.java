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
		assertTrue("Throw InvalidCharacterInExpression",
							 calcError instanceof InvalidCharacterInExpression);
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
}
}