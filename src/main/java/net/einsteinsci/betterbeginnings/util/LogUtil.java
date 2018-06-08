package net.einsteinsci.betterbeginnings.util;

import net.einsteinsci.betterbeginnings.config.BBConfig;
import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

public class LogUtil
{
	public static void logDebug(String text, Object... formatArgs)
	{
		if (BBConfig.debugLogging)
		{
			log(Level.DEBUG, text, formatArgs);
		}
	}

	public static void logDebug(Level level, String text, Object... formatArgs)
	{
		if (BBConfig.debugLogging)
		{
			log(level, text, formatArgs);
		}
	}

	public static void log(Level level, String text, Object... formatArgs)
	{
		FMLLog.log.log(level, text, formatArgs);
	}

	public static void log(String text, Object... formatArgs)
	{
		log(Level.INFO, text, formatArgs);
	}
}
