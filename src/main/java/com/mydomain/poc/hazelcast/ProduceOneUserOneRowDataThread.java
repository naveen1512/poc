package com.mydomain.poc.hazelcast;

import java.time.Instant;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.mydomain.poc.hazelcast.model.User;

public class ProduceOneUserOneRowDataThread implements Runnable {

	private int startUserId;
	private int endUserId;
	private int threadNo;

	private HazelcastInstance client = null;
	private IMap<String, User> imap = null;

	public ProduceOneUserOneRowDataThread(int startUserId, int endUserId, int threadNo) {
		this.startUserId = startUserId;
		this.endUserId = endUserId;
		this.threadNo = threadNo;

		ClientConfig clientConfig = new ClientConfig();
		ClientNetworkConfig networkConfig = clientConfig.getNetworkConfig();

		networkConfig.addAddress("master-slave-0");
		clientConfig.setNetworkConfig(networkConfig);

		client = HazelcastClient.newHazelcastClient(clientConfig);
		imap = client.getMap("user");
	}

	@Override
	public void run() {
		try {
			long startEpoch = Instant.now().toEpochMilli();
			int count = this.endUserId - this.startUserId + 1;

			for (; this.startUserId <= this.endUserId; this.startUserId++) {
				User user = new User(this.startUserId);
				imap.setAsync("" + this.startUserId, user);
				
//				if (count % 10000 == 0) {
//					System.out.println("Sleeping for 10 seconds.");
//					Thread.sleep(10000);
//				}
				
				System.out.println("ThreadNo: " + this.threadNo + " UserId: " + this.startUserId);
			}

			long endEpoch = Instant.now().toEpochMilli();

			System.out.println("Thread No:" + this.threadNo + " Total insert: " + count + " Total Milliseconds: "
					+ (endEpoch - startEpoch));

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			this.shutdownConnection();
		}
	}
	
	private void shutdownConnection() {
		client.shutdown();
	}
}
