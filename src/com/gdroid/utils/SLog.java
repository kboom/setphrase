package com.gdroid.utils;

import java.util.EnumMap;
import java.util.HashMap;

import android.util.Log;

/**
 * Provides every class with useful debugging and logging functions. Should be
 * used only in development mode. Cannot be instantiated.
 * 
 * @author kboom
 */
public class SLog {

	private final static String TAG = "System Logger";
	// holds global settings
	private static EnumMap<Parameter, Object> globalParameters;
	// holds default settings
	private static EnumMap<Parameter, Object> defaultParameters;
	// holds settings for each registered class
	private static HashMap<Class, EnumMap<Parameter, Object>> localParameters;

	/*
	 * PUBLIC
	 */
	private static Boolean isActive = false;
	private static Boolean wasTurned = false;

	/**
	 * Use it to separate block where additional computations are needed to
	 * print debug message.
	 * 
	 * @return
	 */
	public static boolean isOn() {
		return isActive;
	}

	/**
	 * Use this in release version. Cannot be called twice.
	 */
	public static void turnOff() {
		Log.w(TAG, "Turning off logger.");
		assert wasTurned == false;
		isActive = false;
		wasTurned = true;
	}

	/**
	 * Use this in development versions. Cannot be called twice.
	 */
	public static void turnOn() {
		Log.w(TAG, "Turning on logger.");
		assert wasTurned == false;

		// init
		globalParameters = new EnumMap<Parameter, Object>(Parameter.class);
		defaultParameters = new EnumMap<Parameter, Object>(Parameter.class);
		localParameters = new HashMap<Class, EnumMap<Parameter, Object>>();

		// set defaults
		defaultParameters.put(Parameter.TAG, "-");
		defaultParameters.put(Parameter.LEVEL, Level.VERBOSE);
		isActive = true;
		wasTurned = true;
	}

	public static void register(Class c) {
		if (!isActive)
			return;
		Log.w(TAG, c.getName() + " is registering for logging");
		localParameters.put(c, new EnumMap<Parameter, Object>(Parameter.class));
	}

	public static void setLevel(Class c, Level l) {
		if (!isActive)
			return;
		setLocalParam(c, Parameter.LEVEL, l);
	}

	public static void setTag(Class c, String tag) {
		if (!isActive)
			return;
		setLocalParam(c, Parameter.TAG, tag);
	}

	public static void i(Object o, String msg) {
		if (!isActive)
			return;
		Class c = o.getClass();
		if (!checkLevel(c, Level.INFO))
			return;
		else
			Log.i(getStringParam(c, Parameter.TAG), msg);
	}

	public static void d(Object o, String msg) {
		if (!isActive)
			return;
		Class c = o.getClass();
		if (!checkLevel(c, Level.DEBUG))
			return;
		else
			Log.d(getStringParam(c, Parameter.TAG), msg);
	}

	public static void w(Object o, String msg) {
		if (!isActive)
			return;
		Class c = o.getClass();
		if (!checkLevel(c, Level.WARN))
			return;
		else
			Log.w(getStringParam(c, Parameter.TAG), msg);
	}

	public static void e(Object o, String msg) {
		if (!isActive)
			return;
		Class c = o.getClass();
		if (!checkLevel(c, Level.ERROR))
			return;
		else
			Log.e(getStringParam(c, Parameter.TAG), msg);
	}

	public static void e(Object o, String msg, Exception ex) {
		if (!isActive)
			return;
		Class c = o.getClass();
		if (!checkLevel(c, Level.ERROR))
			return;
		else
			Log.e(getStringParam(c, Parameter.TAG),
					msg + ": " + ex.getMessage());
	}

	public static void v(Object o, String msg) {
		if (!isActive)
			return;
		Class c = o.getClass();
		if (!checkLevel(c, Level.VERBOSE))
			return;
		else
			Log.v(getStringParam(c, Parameter.TAG), msg);
	}

	private static Class getClassID(Object o) {
		return o.getClass();
	}

	/*
	 * PRIVATE
	 */

	private SLog() {
	}

	private static void setGlobalParam(Parameter p, Object val) {
		if (!isActive)
			return;
		if (globalParameters.containsKey(p))
			globalParameters.remove(p);
		globalParameters.put(p, val);
	}

	private static void setLocalParam(Class c, Parameter p, Object val) {
		if (!isActive)
			return;
		EnumMap<Parameter, Object> params = localParameters.get(c);

		if (params.containsKey(p))
			params.remove(p);
		params.put(p, val);
	}

	private static boolean checkLevel(Class c, Level l) {
		return getLevelParam(c, Parameter.LEVEL).ordinal() >= l.ordinal() ? true
				: false;
	}

	private static Boolean getBoolParam(Class c, Parameter parameter) {
		return (Boolean) getParam(c, parameter);
	}

	private static String getStringParam(Class c, Parameter parameter) {
		return (String) getParam(c, parameter);
	}

	private static Integer getIntParam(Class c, Parameter parameter) {
		return (Integer) getParam(c, parameter);
	}

	private static Level getLevelParam(Class c, Parameter parameter) {
		return (Level) getParam(c, parameter);
	}

	private static Object getParam(Class c, Parameter p) {
		// check if there's such global parameter
		if (globalParameters.containsKey(p))
			return globalParameters.get(p);
		// there is no global parameter, check local
		EnumMap<Parameter, Object> params = localParameters.get(c);
		if (params != null && params.containsKey(p)) {
			return params.get(p);
		}
		// there is no local too, get default
		else
			return defaultParameters.get(p);
	}

	private static enum Parameter {
		LEVEL, TAG
	}

	public static enum Level {
		NONE, ERROR, WARN, INFO, DEBUG, VERBOSE
	}
}
