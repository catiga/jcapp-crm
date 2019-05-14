package com.jeancoder.crm.entry

import javax.swing.JApplet
import javax.swing.JLabel

class GApplet extends JApplet {

	public void init() {
		println 'test';
		this.getContentPane().add(new JLabel('test success'));
	}
}
