package com.mydomain.poc.hazelcast;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.mydomain.poc.hazelcast.ProduceOneUserOneRowDataThread;

public class ProduceOneUserOneRowData {
	public static void main(String[] args) {
		int threadCount = 1;
		int totalUsers = 2000000;
		int usersPerThread = (int) Math.ceil((double) totalUsers / threadCount);

		try {

			long startEpoch = Instant.now().toEpochMilli();

			ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);

			ProduceOneUserOneRowDataThread produceOneUserOneRowDataThread1 = new ProduceOneUserOneRowDataThread(1000001,
					2000000, 1);
			pool.execute(produceOneUserOneRowDataThread1);

//			int startUser = 1;
//			int endUser = startUser + usersPerThread;
//
//			for (int i = 1; i <= threadCount; i++) {
//				ProduceOneUserOneRowDataThread produceOneUserOneRowDataThread = new ProduceOneUserOneRowDataThread(
//						startUser, endUser, i);
//				pool.execute(produceOneUserOneRowDataThread);
//				
//				startUser = endUser + 1;
//				endUser = startUser + usersPerThread;
//			}

			pool.shutdown();
			while (!pool.isTerminated()) {
			}

			long endEpoch = Instant.now().toEpochMilli();

			System.out.println("Finished all threads. Total Milliseconds: " + (endEpoch - startEpoch));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
