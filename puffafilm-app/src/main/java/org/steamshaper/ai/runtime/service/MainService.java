package org.steamshaper.ai.runtime.service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.steamshaper.ai.prediction.APrediction;
import org.steamshaper.ai.prediction.MultipleApproximationPrediction;
import org.steamshaper.ai.runtime.AService;

public class MainService extends AService {

	private DataLoader dataInput = null;
	private List<String> outputRatingList =  new ArrayList<String>();

	@Override
	public void run() {
		if (getDataInput() != null) {
			APrediction prediction = new MultipleApproximationPrediction();
			Long userOid;
			Long movieOid;
			Long timestamp;
			for (String str : getDataInput().getData()) {
				StringTokenizer st = new StringTokenizer(str, "\t");
				userOid = new Long(st.nextToken());
				movieOid = new Long(st.nextToken());
				timestamp = new Long(st.nextToken());
				outputRatingList.add(prediction.getPrediction(userOid, movieOid, timestamp).toString());
			}
		} else {
			log.error("Missing test input data!");
		}

	}
	public DataLoader getDataInput() {
		return dataInput;
	}
	public void setDataInput(DataLoader dataInput) {
		this.dataInput = dataInput;
	}

}
