package org.steamshaper.ai.prediction.ratestatistics;

import org.apache.log4j.Logger;
import org.steamshaper.ai.puffafilm.util.Help;
import org.steamshaper.ai.runtime.service.MaeTest;
import org.steamshaper.puffafilm.ai.node.GNUser;
import org.steamshaper.puffafilm.ai.repository.GNUserRepository;

public class UserRateFilter {

	Logger log = Logger.getLogger("rateSwitch");
	int[] rateFreq = new int[11];
	GNUser user = null;

	public UserRateFilter(GNUser user) {
		GNUserRepository gnuRepo = Help.me.getContext().getBean(
				GNUserRepository.class);
		for (float i = 0; i <= 5f; i += 0.5F) {
			Integer count = gnuRepo.howManyForRate(user, i);
			count = count != null ? count : 0;
			addRate(count, i);

		}
	}

	public void addRate(int rateFreq, float forRate) {
		int index = (int) (2 * forRate) - 1;
		index = index < 0 ? 0 : index;
		this.rateFreq[index] = rateFreq;
	}

	public int allRate() {
		int acc = 0;
		for (int i : rateFreq) {
			acc += i;
		}

		return acc;
	}

	public float probabilityForRate(float forRate) {
		int index = (int) (2 * forRate) - 1;
		return ((float) this.rateFreq[index]) / ((float) allRate());
	}

	public float rateOnProbability(float rate, float onRange, int userRatesCount) {
		float validRate = getValidRate(rate);
		int index = (int) (2 * validRate);
		index = index < 0 ? 0 : index;
		int intOnRange = (int) (2 * onRange);

		int minRange = index - intOnRange;
		minRange = minRange < 0 ? 0 : minRange;
		int maxRange = index + intOnRange;
		maxRange = maxRange > 10 ? 10 : maxRange;
		float distFactor = 2;
		int bestIndex = index;
		int bestValue = rateFreq[index];
		boolean isZeroFreq = false;
		if(rateFreq[index]==0){
			rateFreq[index]++;
			isZeroFreq =  true;
			log.debug("Is Zero Freq\t"+isZeroFreq);
		}

		for (int i = intOnRange; i > 0; i--) {
			// Lower index

			if (index - i > 0) { // Check lower array bound
				if (rateFreq[index - i] > bestValue
						&& ((float) rateFreq[index - i])
								/ ((float) rateFreq[index]) > (distFactor * Math
								.exp(1 + i))) {
					log.debug("-"+i+"-"+(index-i)+"\t"+rateFreq[index - i]+">"+ bestValue+" = "+(rateFreq[index - i] > bestValue)+"\t ratio\t"+rateFreq[index - i]
							+"/"+rateFreq[index]+"="+(((float) rateFreq[index - i])
							/ ((float) rateFreq[index]))+">"+(distFactor * Math
									.exp(1 + i)));
					bestIndex = index - i;
					bestValue = rateFreq[index - i];
				}
			}

			if (index + i < 11) { //Check upper bound
				if (rateFreq[index + i] > bestValue
						&& ((float) rateFreq[index + i])
								/ ((float) rateFreq[index]) > (distFactor * Math
								.exp(1 + i))) {
					log.debug("+"+i+"-"+(index+i)+"\t"+rateFreq[index + i]+">"+ bestValue+" = "+(rateFreq[index + i] > bestValue)+"\t ratio\t"+rateFreq[index + i]
							+"/"+rateFreq[index]+"="+(((float) rateFreq[index + i])
							/ ((float) rateFreq[index]))+">"+(distFactor * Math
									.exp(1 + i)));
					bestIndex = index + i;
					bestValue = rateFreq[index + i];
				}
			}
		}

		float output = (bestIndex) / 2F;

		if (index != bestIndex) {
			log.info(rate + "\t" + output + "\t" + MaeTest.expected);
			System.err.println("Rate switched from [" + validRate + "@"
					+ rateFreq[index] + "] to [" + output + "@"
					+ rateFreq[bestIndex] + "] rateFactor "
					+ (userRatesCount / 11));
		}
		return output;
	}

	public float getValidRate(float rate) {
		int integerPart = (int) rate;
		int mantissa = (int) ((rate - integerPart) * 100);
		float validRate = 0F;
		if (mantissa > 25 && mantissa <= 75) {
			validRate = 0.5F;

		} else if (mantissa > 75) {
			validRate = 1F;
		}
		validRate += integerPart;
		if (validRate > 5F) {
			validRate = 5F;
		} else if (validRate < 0F) {
			validRate = 0F;
		}
		return validRate;
	}

	public void dumpStatistics(Logger log) {
		for (int i = 0; i < rateFreq.length; i++) {
			log.info(((float) i) / 2 + "\t" + rateFreq[i]);
		}
	}
	public void dumpStatistics(double rate) {
		log.error("--------------------------------------");
		log.error("expected\t"+MaeTest.expected+"predicted\t"+rate);
		for (int i = 0; i < rateFreq.length; i++) {
			log.info(((float) i) / 2 + "\t" + rateFreq[i]);
		}
	}
}
