package calc.math.limits;

import org.junit.Test;

import static org.junit.Assert.*;

public
class LimitTest
{
static private final double EPSILON = 0.00000001;

/**
 * Testing simple math limits.
 */
@Test
public
void testSimpleMathLimits()
{
	try {
		assertEquals("Simple math limit №1", -3,
								 Limit.calculate("(2 * (x ^ 2) - 3 * x - 5) / (x + 1)", "x", 1), EPSILON);
		assertEquals("Simple math limit №2", Double.NEGATIVE_INFINITY,
								 Limit.calculate("1 - x", "x", Double.POSITIVE_INFINITY), EPSILON);
		assertEquals("Simple math limit №3", 0.54030230, Limit.calculate("cos[var]", "var", 1),
								 EPSILON);
	} catch(Exception e) {
		fail("Handled exception: " + e.toString());
	}
}
}