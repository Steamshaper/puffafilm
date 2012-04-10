package org.steamshaper.ai.runtime.service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.steamshaper.ai.prediction.APrediction;
import org.steamshaper.ai.prediction.RatesBasedPrediction;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.ai.runtime.AService;
import org.steamshaper.puffafilm.ai.repository.GNMovieRepository;
import org.steamshaper.puffafilm.ai.repository.GNUserRepository;

public class MaeTest extends AService {

	private DataLoader dataInput = null;

	private List<Float> predictedRateList = new ArrayList<Float>();
	private List<Float> expextedRateList = new ArrayList<Float>();
	public static float expected = 0;

	@Override
	public void run() {
		GNUserRepository gnuRepo = Help.me.getContext().getBean(
				GNUserRepository.class);
		GNMovieRepository gnmRepo = Help.me.getContext().getBean(
				GNMovieRepository.class);

		if (getDataInput() != null) {
			APrediction prediction = new RatesBasedPrediction();
			Long userOid;
			Long movieOid;
			Long timestamp;
			Float expected;
			Float rating;
			int[] errors = new int[20];
			int errorZero = 10;
			int errorIndex;
			int round=1;
			for (String str : getDataInput().getData()) {
				StringTokenizer st = new StringTokenizer(str, "\t");
				userOid = new Long(st.nextToken());
				movieOid = new Long(st.nextToken());
				expected = new Float(st.nextToken());
				timestamp = new Long(st.nextToken());
				MaeTest.expected = expected;

				rating = prediction.getPrediction(userOid, movieOid,timestamp);
				predictedRateList.add(rating);
				expextedRateList.add(expected);

				float error = rating - expected;

				errors[(errorZero + (int) (error * 2))]++;
					System.err.println("Riga: " + round +"\terror "
							+ (expected - rating)
							+ " rate "
							+ rating
							+ " expected "
							+ expected
							+ " user [Oid ["
							+ userOid
							+ "] ID ["
							+ gnuRepo.findByPropertyValue("oid", userOid)
									.getId()
							+ "]] movie [Oid ["
							+ movieOid
							+ "] ID ["
							+ gnmRepo.findByPropertyValue("oid", movieOid)
									.getId() + "]]");
					round++;
			}
			for(int i =0;i<errors.length;i++){
				System.err.println(((i-errorZero)/2F) +"\t"+errors[i]);
			}
		} else {
			log.error("Missing test input data!");
		}

		System.err.println("MAE\t"
				+ calculateMAE(expextedRateList, predictedRateList));
	}

	public DataLoader getDataInput() {
		return dataInput;
	}

	public void setDataInput(DataLoader dataInput) {
		this.dataInput = dataInput;
	}

	private float calculateMAE(List<Float> expected, List<Float> actual) {
		float mae = 0;

		for (int i = 0; i < expected.size(); i++) {
			mae += Math.abs(actual.get(i) - expected.get(i));
		}

		mae /= expected.size();

		return mae;
	}

}
