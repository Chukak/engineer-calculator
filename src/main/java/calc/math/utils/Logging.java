package calc.math.utils;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * The Logging class implements message logging.
 */
public
class Logging
{
/**
 * The logger.
 */
private static final Logger logger = Logger.getLogger(Logging.class.getName());

/**
 * Prints information messages (java.util.logging.Level.INFO).
 *
 * @param cls source object.
 * @param msg text.
 */
public static
void info(Object cls, String msg)
{
	logger.log(Level.INFO, getClassName(cls) + msg);
}

/**
 * Prints warning messages (java.util.logging.Level.WARNING).
 *
 * @param cls source object.
 * @param msg text.
 */
public static
void warning(Object cls, String msg)
{
	logger.log(Level.WARNING, getClassName(cls) + msg);
}

/**
 * Prints text (java.util.logging.Level.ALL).
 *
 * @param msg text.
 */
public static
void print(String msg)
{
	logger.log(Level.ALL, msg);
}

/**
 * @param cls source object
 * @return Gets name of the source object.
 */
private static
String getClassName(Object cls)
{
	if(cls != null) {
		return cls.getClass().getName() + ": ";
	}
	return "";
}

/**
 * Sets a logging level.
 *
 * @param level level.
 */
public static
void setLevel(Level level)
{
	logger.setLevel(level);
}

/**
 * @return The current logging level.
 */
public static
Level getCurrentLevel()
{
	return logger.getLevel();
}
}
