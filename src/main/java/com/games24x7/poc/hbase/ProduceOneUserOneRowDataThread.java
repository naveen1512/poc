package com.games24x7.poc.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.time.Instant;
import java.util.Random;

public class ProduceOneUserOneRowDataThread implements Runnable {
	private final String HBASE_CONFIGURATION_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
	private final String HBASE_CONFIGURATION_ZOOKEEPER_CLIENTPORT = "hbase.zookeeper.property.clientPort";

	private final String hbaseZookeeeperHost = "10.14.24.70";
	private final String hbaseZookeeeperPort = "2181";

	private final String tablename = "userattribute";

	private Connection hbaseConn;
	private Table table;
	private Configuration hbaseConf;

	private int startUser;
	private int endUser;
	private int threadId;

	public static String[] STATE = { "KARNATKA", "TAMILNADU", "MAHARASTRA", "DEHLI", "RAJASTHAN", "BIHAR", "TELANGANA",
			"ANDHRAPRADESH" };
	public static int[] CHANNEL = { 1, 2, 3, 4 };
	public static String[] OS = { "Android", "iOS" };
	public static int[] YOB = { 1982, 1983, 1984, 1985, 1986, 1987, 1988 };

	public ProduceOneUserOneRowDataThread(int startUser, int endUser, int threadNo)
			throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
		this.startUser = startUser;
		this.endUser = endUser;
		this.threadId = threadNo;

		this.hbaseConf = HBaseConfiguration.create();

		this.hbaseConf.set(HBASE_CONFIGURATION_ZOOKEEPER_QUORUM, hbaseZookeeeperHost);
		this.hbaseConf.set(HBASE_CONFIGURATION_ZOOKEEPER_CLIENTPORT, hbaseZookeeeperPort);

//		HBaseAdmin.available(hbaseConf);

		this.hbaseConn = ConnectionFactory.createConnection(hbaseConf);
		this.table = hbaseConn.getTable(TableName.valueOf(tablename));
	}

	public void run() {
		long startEpoch = Instant.now().toEpochMilli();

		for (; this.startUser <= this.endUser; this.startUser++) {
			String rowKey = "" + this.startUser;

			Put put = new Put(Bytes.toBytes(rowKey));

			put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("name"), Bytes.toBytes("User " + rowKey));
			put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("email"), Bytes.toBytes("User" + rowKey + "@example.com"));
			put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("lid"), Bytes.toBytes("User" + rowKey));
			put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("lcount"), Bytes.toBytes(new Random().nextInt(100)));
			put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("frstCshGmTblSz"), Bytes.toBytes(new Random().nextInt(6)));
			put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("isIdVrfd"), Bytes.toBytes("NEW"));
			put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("isRptPlyr"), Bytes.toBytes(false));
			put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("isTest"), Bytes.toBytes(false));
			put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("state"),
					Bytes.toBytes(STATE[new Random().nextInt(STATE.length)]));
			put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("geolocStae"),
					Bytes.toBytes(STATE[new Random().nextInt(STATE.length)]));
			put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("chnlOfRgstrtn"),
					Bytes.toBytes(CHANNEL[new Random().nextInt(CHANNEL.length)]));
			put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("osReg"),
					Bytes.toBytes(OS[new Random().nextInt(OS.length)]));
			put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("yob"),
					Bytes.toBytes(YOB[new Random().nextInt(YOB.length)]));
			put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("scrnSizeLstSsn"), Bytes.toBytes("5.5"));
			put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("macp"), Bytes.toBytes(false));

			for (int i = 0; i < 75; i++) {
				put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("key" + i), Bytes.toBytes("value" + i));
			}

			try {
				this.table.put(put);
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("RowKey: " + rowKey);
		}

		long endEpoch = Instant.now().toEpochMilli();

		System.out.println("Thread ID:" + this.threadId + " Total Milliseconds: " + (endEpoch - startEpoch));

	}

	public static long getRandom(int max) {
		return (long) (Math.random() * max);
	}

}
