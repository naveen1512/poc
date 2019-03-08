package com.mydomain.poc.hbase;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ProduceCurrentWithdrawableEventsCopy {
	public static void main(String[] args) {
		int threadCount = 4;

		try {
			ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);

			for (int i = 1; i <= threadCount; i++) {
				ProduceCurrentWithdrawableEventsThread produceCurrentWithdrawableEventsThread = new ProduceCurrentWithdrawableEventsThread(
						i);
				pool.execute(produceCurrentWithdrawableEventsThread);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
