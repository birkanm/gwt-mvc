package com.googlecode.gwtmvc.client;
/**
 * Allow to mask an element.<br />
 * For exemple during a loading action.<br />
 * A maskable element will show a loading picture or change its style when it 'mask' 
 */
public interface Maskable {

	/**
	 * mask
	 */
	public void mask();
	
	/**
	 * unmask
	 */
	public void unmask();
	
}
