package calc.math.limits;

import org.junit.Test;

import static org.junit.Assert.*;

public
class LimitTest
{
static private final double EPSILON = 0.00000001;
static private final double EPSILON_TO_COMPARE = 0.00001;

/**
 * Testing simple math limits.
 */
@Test
public
void testSimpleMathLimits()
{
	try {
		assertEquals("Simple math limit №1", -3,
								 Limit.calculate("(2 * (x ^ 2) - 3 * x - 5) / (x + 1)", "x", 1, EPSILON_TO_COMPARE),
								 EPSILON);
		assertEquals("Simple math limit №2", Double.NEGATIVE_INFINITY,
								 Limit.calculate("1 - x", "x", Double.POSITIVE_INFINITY, EPSILON_TO_COMPARE),
								 EPSILON);
		assertEquals("Simple math limit №3", 0.54030230,
								 Limit.calculate("cos[var]", "var", 1, EPSILON_TO_COMPARE), EPSILON);
		assertEquals("Simple math limit №4", -6.99999988, Limit
				.calculate("(2 * (x ^ 2) - 3 * x - 5) / (x + 1)", "x", -1, EPSILON_TO_COMPARE), EPSILON);
		assertEquals("Simple math limit №5", 0,
								 Limit.calculate("1/x", "x", Double.POSITIVE_INFINITY, EPSILON_TO_COMPARE),
								 EPSILON);
	} catch(Exception e) {
		fail("Handled exception: " + e.toString());
	}
}

/**
 * Testing hard math limits.
 */
@Test
public
void testHardMathLimits()
{
	try {
		assertEquals("Hard math limit №1", -0.3, Limit
										 .calculate("(sqrt[x + 6] - sqrt[10 * x - 21]) / (5 * x - 15)", "x", 3, EPSILON_TO_COMPARE),
								 EPSILON);
		assertEquals("Hard math limit №2", -12, Limit
				.calculate("(x ^ 2 + x - 2) / (sqrt[x + 6] - 2)", "x", -2, EPSILON_TO_COMPARE), EPSILON);
		assertEquals("Hard math limit №3", -0.6666, Limit
				.calculate("(sqrt[(x / 4), 3] - 0.5) / (sqrt[0.5 + x] - sqrt[2 * x])", "x", 0.5,
									 EPSILON_TO_COMPARE), 0.0001);
		assertEquals("Hard math limit №4", 1.25, Limit
										 .calculate("(e ^ (2 * x) - e ^ (-3 * x)) / (sin[4 * x])", "x", 0, EPSILON_TO_COMPARE),
								 EPSILON);
		assertEquals("Hard math limit №5", -0.49999998, // 0.5
								 Limit.calculate("ln[1 - sin[x]] / (2 * x)", "x", 0, EPSILON_TO_COMPARE), EPSILON);
		assertEquals("Hard math limit №6", 0, Limit
				.calculate("(sqrt[8 + (3 * x) + (x ^ 2), 3] - 2) / sqrt[(x ^ 2) + (x ^ 3), 3]", "x", 0,
									 EPSILON_TO_COMPARE), 0.001 /* double precision */);

		/* todo:
		assertEquals("Hard math limit №", 0, // 0.5
								 Limit.calculate("cos[2 * x] ^ (1 / (ln[1 + sin[x] ^ 1]))", "x", 0,
																 EPSILON_TO_COMPARE), EPSILON);*/

		/* todo:
				assertEquals("Hard math limit №", 0, Limit
						.calculate("sqrt[x ^ 3 - 3 * (x ^ 2), 3] - x", "x", Double.POSITIVE_INFINITY,
											 EPSILON_TO_COMPARE), EPSILON); */
	} catch(Exception e) {
		fail("Handled exception: " + e.toString());
	}
}
}