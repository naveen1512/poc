package com.mydomain.poc.hbase;

import java.io.IOException;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class ProduceCurrentWithdrawableEventsThread implements Runnable {
	private final String HBASE_CONFIGURATION_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
	private final String HBASE_CONFIGURATION_ZOOKEEPER_CLIENTPORT = "hbase.zookeeper.property.clientPort";

	private final String hbaseZookeeeperHost = "master-slave-0";
	private final String hbaseZookeeeperPort = "2181";

	private final String tablename = "currentwithdrawable";

	private Connection hbaseConn;
	private Table table;
	private Configuration hbaseConf;
	private int threadId;

	public ProduceCurrentWithdrawableEventsThread(int threadNo)
			throws MasterNotRunningException, ZooKeeperConnectionException, IOException {

		this.threadId = threadNo;

		this.hbaseConf = HBaseConfiguration.create();

		this.hbaseConf.set(HBASE_CONFIGURATION_ZOOKEEPER_QUORUM, hbaseZookeeeperHost);
		this.hbaseConf.set(HBASE_CONFIGURATION_ZOOKEEPER_CLIENTPORT, hbaseZookeeeperPort);

//		HBaseAdmin.available(hbaseConf);

		this.hbaseConn = ConnectionFactory.createConnection(hbaseConf);
		this.table = hbaseConn.getTable(TableName.valueOf(tablename));
	}

	public void run() {

		int userIdMax = 1000000;
		int userIdCurr = 228549;//1;
		long rowKeyMax = 200000;//Long.MAX_VALUE;

		long startEpoch = Instant.now().toEpochMilli();

		// Insertion of data
		for (long idx = 0; idx < rowKeyMax; idx++) {
			Random r = new Random();
			String rowKey = userIdCurr + "_" + Instant.now().toEpochMilli() + "_" + this.threadId + "_ab";
			String[] paymentModeOptions = { "UPI", "CreditCard", "DebitCard", "NetBanking", "MobileWallet" };

			Put put = new Put(Bytes.toBytes(rowKey));

			put.addColumn(Bytes.toBytes("userinfo"), Bytes.toBytes("userId"), Bytes.toBytes(userIdCurr));

			put.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("orderId"),
					Bytes.toBytes(RandomStringUtils.randomAlphanumeric(10).toUpperCase()));
			// 10 <= txnAmount < 10000
			put.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("txnAmount"),
					Bytes.toBytes(ThreadLocalRandom.current().nextDouble(10, 10000)));
			// 0 <= paymentMode < 5
			put.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("paymentMode"),
					Bytes.toBytes(r.nextInt(5 - 0) + 0));
			put.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("paymentModeOption"),
					Bytes.toBytes(paymentModeOptions[r.nextInt(paymentModeOptions.length)]));
			// 0 <= channelId < 4
			put.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("channelId"),
					Bytes.toBytes(r.nextInt(4 - 0) + 0));
			// 0.5% payment status 0 and 99.5% payment status 1.
			put.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("status"),
					Bytes.toBytes(idx % 200 == 0 ? 0 : 1));

			// curr_epoch - 1 week <= timestamp < curr_epoch
			Instant instant = Instant.now();
			int noSecondsWeek = 604800;
			int lowerEpoch = (int) (instant.getEpochSecond() - noSecondsWeek);
			int upperEpoch = (int) (instant.getEpochSecond() + 1);
			int timestamp = r.nextInt(upperEpoch - lowerEpoch) + lowerEpoch;
			put.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("timestamp"), Bytes.toBytes(timestamp));

			// 10000 <= amount < 20000
			put.addColumn(Bytes.toBytes("amountinfo"), Bytes.toBytes("amount"),
					Bytes.toBytes(ThreadLocalRandom.current().nextDouble(10000, 20000)));
			// 20000 <= depositAmt < 30000
			put.addColumn(Bytes.toBytes("amountinfo"), Bytes.toBytes("depositAmt"),
					Bytes.toBytes(ThreadLocalRandom.current().nextDouble(20000, 30000)));
			// 10000 <= withdrawAmt < 20000
			put.addColumn(Bytes.toBytes("amountinfo"), Bytes.toBytes("withdrawAmt"),
					Bytes.toBytes(ThreadLocalRandom.current().nextDouble(10000, 20000)));
			// 10000 <= nonWithdrawAmt < 20000
			put.addColumn(Bytes.toBytes("amountinfo"), Bytes.toBytes("nonWithdrawAmt"),
					Bytes.toBytes(ThreadLocalRandom.current().nextDouble(10000, 20000)));

			try {
				this.table.put(put);
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println(" RowKey: " + rowKey + " UserId: " + userIdCurr);

//			userIdCurr++;

			// Reset the userId
			if (userIdCurr == userIdMax) {
				userIdCurr = 1;
			}
		}

		long endEpoch = Instant.now().toEpochMilli();

		System.out.println("Thread ID:" + this.threadId + " Total Milliseconds: " + (endEpoch - startEpoch));

	}

}
