package org.steamshaper.ai.runtime.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.ai.runtime.AService;

public class Condition extends AService {

	String conditionQuestion = "?";
	private boolean conditionDefault = false;
	private boolean condition = false;
	private String interactiveModeArg ="interactive";
	@Override
	public void run() {
		System.err.println(conditionQuestion + " Accepted y/n/empty=[" + conditionDefault+"]");
		System.err.flush();
		if(!Help.me.existArgForName(interactiveModeArg)){
			condition=conditionDefault;
			System.err.println("Interactive mode disabled -> Condition: ["+condition+"] add interactive on command line args");
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String results = "empty";
		while (!"y".equalsIgnoreCase(results) && !"n".equalsIgnoreCase(results)
				&& !"".equalsIgnoreCase(results)) {
			try {
				results = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if ("y".equalsIgnoreCase(results)) {
			condition = true;
		}
		if ("n".equalsIgnoreCase(results)) {
			condition = false;
		}
		if ("".equalsIgnoreCase(results)) {
			condition = conditionDefault;
		}
		System.err.println("Condition: ["+condition+"]");
		System.err.flush();
	}

	public boolean isConditionTrue() {
		return condition;
	}

	public void setConditionQuestion(String conditionQuestion) {
		this.conditionQuestion = conditionQuestion;
	}

	public void setConditionDefault(boolean conditionDefault) {
		this.conditionDefault = conditionDefault;
	}

}
