package org.steamshaper.ai.runtime.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.steamshaper.ai.runtime.AService;

public class Pause extends AService {

	public void run() {
		System.out.println("Pause ... press any button to resume :");
			BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
			try {
				br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	}

}
