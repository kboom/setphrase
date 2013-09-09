/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdroid.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author kboom
 */
public abstract class SmartRunnable implements Runnable {

	private Thread carrier;
	private STATE currentState;
	private int sleepTimeIn_ms;

	enum STATE {
		RUNNING, STOPPING, WAITING, STOPPED
	}

	public SmartRunnable() {
		carrier = new Thread(this);
	}

	public void stop() {
		currentState = STATE.STOPPING;
	}

	@Override
	public void run() {
		onPreRun();
		// should not be run again before closed
		if (!carrier.isAlive())
			return;

		carrier.start();
		currentState = STATE.RUNNING;

		while (currentState == STATE.RUNNING) {
			doWhenRun();
			currentState = STATE.WAITING;
			try {
				Thread.sleep(sleepTimeIn_ms);
			} catch (InterruptedException ex) {
				Logger.getLogger(SmartRunnable.class.getName()).log(
						Level.SEVERE, null, ex);
			} finally {
				currentState = STATE.RUNNING;
			}
		}

		currentState = STATE.STOPPED;
		onPostRun();
	}

	// algorithm pattern (hooks)
	public abstract void doWhenRun();

	public void onPreRun() {
	}

	public void onPostRun() {
	}

	final public STATE getCurrentState() {
		final STATE s = currentState;
		return s;
	}
}
