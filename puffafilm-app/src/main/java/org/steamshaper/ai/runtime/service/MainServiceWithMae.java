package org.steamshaper.ai.runtime.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.steamshaper.ai.prediction.APrediction;
import org.steamshaper.ai.prediction.MultipleApproximationPrediction;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.ai.runtime.AService;

public class MainServiceWithMae extends AService {

	private DataLoader dataInput = null;
	private List<String> outputRatingList =  new ArrayList<String>();
	private List<String> expectedRatingList = new ArrayList<String>();
	private final Logger log = Logger
			.getLogger("outputLogger");
	@Override
	public void run() {
		long y = 0;
		if (getDataInput() != null) {
			APrediction prediction = new MultipleApproximationPrediction();
			Long userOid;
			Long movieOid;
			Long timestamp;
			for (String str : getDataInput().getData()) {
				StringTokenizer st = new StringTokenizer(str, "\t");
				userOid = new Long(st.nextToken());
				movieOid = new Long(st.nextToken());
				Float expected = new Float(st.nextToken());
				timestamp = new Long(st.nextToken());
				Float rating = prediction.getPrediction(userOid, movieOid, timestamp);
				outputRatingList.add(rating.toString());
				Float error = rating-expected;
				expectedRatingList.add(expected.toString()+"\t"+rating.toString()+"\t" + error.toString());
				log.info(expected.toString()+"\t"+rating.toString()+"\t" + error.toString()+"\t"+(y++));
			}
			produceOutput();
			produceMaeOutput();
		} else {
			log.error("Missing test input data!");
		}

	}
	private void produceOutput() {
		String destination = Help.me.getArgAtIndex(1);
		try {
			Help.me.toStoreOutput(destination, outputRatingList);
		} catch (IOException e) {
			System.err.println("Error during output generation @ " + destination);
			log.error("Error during output generation @ " + destination);
		}

	}
	
	private void produceMaeOutput() {
		String destination = Help.me.getArgAtIndex(1).replace(".", "_mae.");
		try {
			Help.me.toStoreOutput(destination, expectedRatingList);
		} catch (IOException e) {
			System.err.println("Error during output generation @ " + destination);
			log.error("Error during output generation @ " + destination);
		}

	}
	public DataLoader getDataInput() {
		return dataInput;
	}
	public void setDataInput(DataLoader dataInput) {
		this.dataInput = dataInput;
	}

}
