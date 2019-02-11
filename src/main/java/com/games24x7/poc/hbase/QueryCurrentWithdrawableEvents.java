package com.games24x7.poc.hbase;

import java.io.IOException;
import java.time.Instant;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.filter.LongComparator;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;

public class QueryCurrentWithdrawableEvents {

	private static final String HBASE_CONFIGURATION_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
	private static final String HBASE_CONFIGURATION_ZOOKEEPER_CLIENTPORT = "hbase.zookeeper.property.clientPort";
	private static final String HBASE_CONFIGURATION_CLIENT_SCANNER_CACHING = "hbase.client.scanner.caching";
	private static final String HBASE_CONFIGURATION_RPC_TIMEOUT = "hbase.rpc.timeout";

	private static final String hbaseZookeeeperHost = "localhost";
	private static final String hbaseZookeeeperPort = "2181";
	private static final String hbaseClientScannerCaching = "1000";
	private static final String hbaseRPCTimeout = "60000"; // 60 seconds

	private static final String tablename = "currentwithdrawable";

	private static Connection hbaseConn = null;
	private static Table table = null;
	private static Admin hbaseAdmin = null;

	public static void main(String[] args) {
		connectHbase();

//		scanAllRows();
//		filterOnUserIdUsingScan();
//		filterOnRowKeyUsingGet();
		filterOnRowKeyPrefixUsingScan();
	}

	private static void connectHbase() {
		Configuration hbaseConf = HBaseConfiguration.create();

		hbaseConf.set(HBASE_CONFIGURATION_ZOOKEEPER_QUORUM, hbaseZookeeeperHost);
		hbaseConf.set(HBASE_CONFIGURATION_ZOOKEEPER_CLIENTPORT, hbaseZookeeeperPort);
		hbaseConf.set(HBASE_CONFIGURATION_CLIENT_SCANNER_CACHING, hbaseClientScannerCaching);

		// Increase RPC timeout, in case of a slow computation
//        customConf.setLong("hbase.rpc.timeout", 600000);

		try {
			HBaseAdmin.available(hbaseConf);

			hbaseConn = ConnectionFactory.createConnection(hbaseConf);
			hbaseAdmin = hbaseConn.getAdmin();
			table = hbaseConn.getTable(TableName.valueOf(tablename));

			System.out.println("Master Server Name: " + hbaseAdmin.getMaster());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void filterOnUserIdUsingScan() {
		// Scanning the table.
		Scan scan = new Scan();
		scan.addColumn(Bytes.toBytes("userinfo"), Bytes.toBytes("userId"));
		scan.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("txnAmount"));

		Filter userIdFilter = new SingleColumnValueFilter(Bytes.toBytes("userinfo"), Bytes.toBytes("userId"),
				CompareOp.EQUAL, Bytes.toBytes(228549));

		scan.setFilter(userIdFilter);
		ResultScanner result;
		try {
			long startEpoch = Instant.now().toEpochMilli();
			System.out.println("Profile started: " + startEpoch);

			result = table.getScanner(scan);

			for (Result res : result) {
				String rowKey = Bytes.toString(res.getRow());
				int userId = Bytes.toInt(res.getValue(Bytes.toBytes("userinfo"), Bytes.toBytes("userId")));
				double txnAmount = Bytes
						.toDouble(res.getValue(Bytes.toBytes("transactioninfo"), Bytes.toBytes("txnAmount")));

				System.out.println("RowKey: " + rowKey + " User Id: " + userId + " Txn Amount: " + txnAmount);
			}

			long endEpoch = Instant.now().toEpochMilli();
			System.out.println("Profile ended: " + endEpoch);

			System.out.println("Total Milliseconds: " + (endEpoch - startEpoch));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void scanAllRows() {
		// Scanning the table.
		Scan scan = new Scan();
		scan.addColumn(Bytes.toBytes("userinfo"), Bytes.toBytes("userId"));
		scan.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("txnAmount"));

		ResultScanner scanner = null;

		try {
			scanner = table.getScanner(scan);

			for (Result result : scanner) {
//				System.out.println("Found Row: " + result);
				System.out.println(
						"userId: " + Bytes.toInt(result.getValue(Bytes.toBytes("userinfo"), Bytes.toBytes("userId")))
								+ " txnAmount: " + Bytes.toDouble(
										result.getValue(Bytes.toBytes("transactioninfo"), Bytes.toBytes("txnAmount"))));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}

		}

	}

	private static void filterOnRowKeyPrefixUsingScan() {
		// Scanning the table.
		Scan scan = new Scan();

		scan.setBatch(100000);

		scan.addColumn(Bytes.toBytes("userinfo"), Bytes.toBytes("userId"));
		scan.addColumn(Bytes.toBytes("transactioninfo"), Bytes.toBytes("txnAmount"));

		scan.setRowPrefixFilter(Bytes.toBytes("228549_"));

//        scan.withStopRow("91_");
//        Filter filter = new PrefixFilter(Bytes.toBytes("91_"));
//        scan.setFilter(filter);

		ResultScanner result;
		try {
			long startEpoch = Instant.now().toEpochMilli();
			System.out.println("Profile started: " + startEpoch);

			result = table.getScanner(scan);

			int resultCount = 0;
			for (Result res : result) {
				String rowKey = Bytes.toString(res.getRow());
				int userId = Bytes.toInt(res.getValue(Bytes.toBytes("userinfo"), Bytes.toBytes("userId")));
				double txnAmount = Bytes
						.toDouble(res.getValue(Bytes.toBytes("transactioninfo"), Bytes.toBytes("txnAmount")));

				System.out.println("Count: " + ++resultCount + " RowKey: " + rowKey + " User Id: " + userId
						+ " Txn Amount: " + txnAmount);

                if (userId != 10) {
                    break;
                }

			}

			long endEpoch = Instant.now().toEpochMilli();
			System.out.println("Profile ended: " + endEpoch);

			System.out.println("Total Milliseconds: " + (endEpoch - startEpoch));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void filterOnRowKeyUsingGet() {
		Get get = new Get(Bytes.toBytes("9_1549454232460_4"));

		Result result;
		try {
			long startEpoch = Instant.now().toEpochMilli();
			System.out.println("Profile started: " + startEpoch);

			result = table.get(get);

			byte[] row = result.getRow();
			if (row == null) {
				System.out.println("Did not get any row.");
				return;
			}

			int userId = Bytes.toInt(result.getValue(Bytes.toBytes("userinfo"), Bytes.toBytes("userId")));
			double txnAmount = Bytes
					.toDouble(result.getValue(Bytes.toBytes("transactioninfo"), Bytes.toBytes("txnAmount")));

			System.out.println("User Id: " + userId + " Txn Amount: " + txnAmount);

			long endEpoch = Instant.now().toEpochMilli();
			System.out.println("Profile ended: " + endEpoch);

			System.out.println("Total Milliseconds: " + (endEpoch - startEpoch));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
