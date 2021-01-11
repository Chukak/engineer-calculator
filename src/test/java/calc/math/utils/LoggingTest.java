package calc.math.utils;

import org.junit.Test;

import java.util.logging.Level;

import static org.junit.Assert.*;

public
class LoggingTest
{
/**
 * Testing logging.
 */
@Test
public
void testLogging()
{
	Logging.setLevel(Level.INFO);
	assertEquals(Logging.getCurrentLevel(), Level.INFO);
}
}