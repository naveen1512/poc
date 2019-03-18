package com.mydomain.poc.hazelcast;

import java.time.Instant;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.aggregation.Aggregation;
import com.hazelcast.mapreduce.aggregation.Aggregations;
import com.hazelcast.mapreduce.aggregation.Supplier;
import com.hazelcast.query.PagingPredicate;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;
import com.hazelcast.query.SqlPredicate;
import com.mydomain.poc.hazelcast.model.User;

public class QueryOneUserOneRowData {
	private static HazelcastInstance client = null;
	private static IMap<String, User> imap = null;

	public static void main(String[] args) {
		connectDB();

		try {
//			scanAllRows();
//			filterQuery();
//			Thread.sleep(60000);
//			filterQuery_1();
//			Thread.sleep(60000);
//			filterQuery_2();
//			multiThreadedRangeQuery();
//			Thread.sleep(60000);
			userCount();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			shutdownConnection();
		}
	}

	private static void connectDB() {
		ClientConfig clientConfig = new ClientConfig();
		ClientNetworkConfig networkConfig = clientConfig.getNetworkConfig();

		networkConfig.addAddress("master-slave-0");
		clientConfig.setNetworkConfig(networkConfig);

		client = HazelcastClient.newHazelcastClient(clientConfig);
		imap = client.getMap("user");
	}

	private static void scanAllRows() {
		long startEpoch = Instant.now().toEpochMilli();
		int count = 0;
		PagingPredicate<String, User> pagingPredicate = new PagingPredicate<>(100);

		do {
			Collection<User> values = imap.values(pagingPredicate);

			if (values.size() == 0) {
				break;
			}

			for (User user : values) {
				count++;
				System.out.println(user);
			}

			pagingPredicate.nextPage();
		} while (true);

		long endEpoch = Instant.now().toEpochMilli();
		System.out.println("Thread Id: " + Thread.currentThread().getId() + " Total size: " + count
				+ " Total Milliseconds: " + (endEpoch - startEpoch));
	}

	private static void filterQuery() {
		long startEpoch = Instant.now().toEpochMilli();

		SqlPredicate sqlPredicate = new SqlPredicate(
				"(lcount BETWEEN 0 AND 4) AND (frstCshGmTblSz BETWEEN 1 AND 3) AND (key4 = value4 AND key5 = value5 AND key13 = value13 AND key19 = value19 AND key16 = value16)");

		Collection<User> users = imap.values(sqlPredicate);
		long queryEpoch = Instant.now().toEpochMilli();

		for (User user : users) {
			System.out.println(user.getLcount() + " " + user.getFrstCshGmTblSz());
		}

		long endEpoch = Instant.now().toEpochMilli();
		System.out.println("Thread Id: " + Thread.currentThread().getId() + " Total size: " + users.size()
				+ " Total Milliseconds to query: " + (queryEpoch - startEpoch)
				+ " Total Milliseconds to query & print: " + (endEpoch - startEpoch));
	}

	private static void filterQuery_1() {
		long startEpoch = Instant.now().toEpochMilli();

		SqlPredicate sqlPredicate = new SqlPredicate(
				"(lcount BETWEEN 0 AND 9) AND (frstCshGmTblSz BETWEEN 1 AND 3) AND (key4 = value4 AND key5 = value5 AND key13 = value13 AND key19 = value19 AND key16 = value16)");

		Collection<User> users = imap.values(sqlPredicate);
		long queryEpoch = Instant.now().toEpochMilli();

		for (User user : users) {
			System.out.println(user.getLcount() + " " + user.getFrstCshGmTblSz());
		}

		long endEpoch = Instant.now().toEpochMilli();
		System.out.println("Thread Id: " + Thread.currentThread().getId() + " Total size: " + users.size()
				+ " Total Milliseconds to query: " + (queryEpoch - startEpoch)
				+ " Total Milliseconds to query & print: " + (endEpoch - startEpoch));
	}

	private static void filterQuery_2() {
		long startEpoch = Instant.now().toEpochMilli();

		SqlPredicate sqlPredicate = new SqlPredicate(
				"(lcount BETWEEN 0 AND 19) AND (frstCshGmTblSz BETWEEN 1 AND 3) AND (key11 = value11 AND key5 = value5 AND key13 = value13 AND key19 = value19 AND key16 = value16)");

		Collection<User> users = imap.values(sqlPredicate);
		long queryEpoch = Instant.now().toEpochMilli();

		for (User user : users) {
			System.out.println(user.getLcount() + " " + user.getFrstCshGmTblSz());
		}

		long endEpoch = Instant.now().toEpochMilli();
		System.out.println("Thread Id: " + Thread.currentThread().getId() + " Total size: " + users.size()
				+ " Total Milliseconds to query: " + (queryEpoch - startEpoch)
				+ " Total Milliseconds to query & print: " + (endEpoch - startEpoch));
	}

	private static void multiThreadedRangeQuery() {
		int threadCount = 7;

		try {
			ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);

			for (int i = 1; i <= threadCount; i++) {
				pool.execute(new Thread() {
					@Override
					public void run() {
						filterQuery_2();
					}
				});
			}

			pool.shutdown();
			while (!pool.isTerminated()) {
			}

			System.out.println("Finished all threads");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void userCount() {
		long startEpoch = Instant.now().toEpochMilli();

		SqlPredicate sqlPredicate = new SqlPredicate(
				"(lcount BETWEEN 0 AND 19) AND (frstCshGmTblSz BETWEEN 1 AND 3) AND (key4 = value4 AND key5 = value5 AND key13 = value13 AND key19 = value19 AND key16 = value16)");

		long userCount = imap.aggregate(Supplier.all(), Aggregations.count());

//		for (User user : users) {
//			System.out.println(user.getLcount() + " " + user.getFrstCshGmTblSz());
//		}

		long endEpoch = Instant.now().toEpochMilli();
		System.out.println("Thread Id: " + Thread.currentThread().getId() + " Total user count: " + userCount
				+ " Total Milliseconds: " + (endEpoch - startEpoch));
	}

	private static void shutdownConnection() {
		client.shutdown();
	}
}
