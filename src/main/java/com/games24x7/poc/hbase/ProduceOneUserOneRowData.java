package com.games24x7.poc.hbase;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ProduceOneUserOneRowData {

	public static void main(String[] args) {
		int threadCount = 2;
		int totalUsers = 20000000;
		int usersPerThread = (int) Math.ceil((double) totalUsers / threadCount);

		try {
			ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);

			ProduceOneUserOneRowDataThread produceOneUserOneRowDataThread1 = new ProduceOneUserOneRowDataThread(20000000,
					20150000, 1);
			pool.execute(produceOneUserOneRowDataThread1);

//            int startUser = 1;
//            int endUser = startUser + usersPerThread;
//
//            for (int i = 1; i <= threadCount; i++) {
//                ProduceOneUserOneRowDataThread produceOneUserOneRowDataThread = new ProduceOneUserOneRowDataThread(startUser, endUser, i);
//                pool.execute(produceOneUserOneRowDataThread);
//                startUser = endUser + 1;
//                endUser = startUser + usersPerThread;
//            }

			pool.shutdown();
			while (!pool.isTerminated()) {
			}

			System.out.println("Finished all threads");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
