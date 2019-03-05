package com.games24x7.poc.hbase;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.CompareOperator;
//import org.apache.hadoop.hbase.CompareOperator;
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
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.coprocessor.ColumnInterpreter;
import org.apache.hadoop.hbase.filter.ColumnRangeFilter;
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
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class QueryOneUserOneRowData {

	private static final String HBASE_CONFIGURATION_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
	private static final String HBASE_CONFIGURATION_ZOOKEEPER_CLIENTPORT = "hbase.zookeeper.property.clientPort";
	private static final String HBASE_CONFIGURATION_CLIENT_SCANNER_CACHING = "hbase.client.scanner.caching";
	private static final String HBASE_CONFIGURATION_RPC_TIMEOUT = "hbase.rpc.timeout";
	private static final String HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD = "hbase.client.scanner.timeout.period";

	private static final String hbaseZookeeeperHost = "master-slave-0";
	private static final String hbaseZookeeeperPort = "2181";
	private static final long hbaseClientScannerCaching = 1000;
	private static final long hbaseRPCTimeout = 2400000; // default is 60 seconds
	private static final long hbaseClientScannerTimeoutPeriod = 2400000;

	private static final String tablename = "userattribute";

	private static Connection hbaseConn = null;
	private static Table table = null;
	private static Admin hbaseAdmin = null;
	private static AggregationClient aggregationClient = null;

	public static void main(String[] args) throws InterruptedException {
		Logger.getRootLogger().setLevel(Level.DEBUG);

		connectHbase();

//		scanAllRows();
//		TimeUnit.SECONDS.sleep(30);

//		rangeQuery();
//		TimeUnit.SECONDS.sleep(30);

		countAggregation();
//		TimeUnit.SECONDS.sleep(30);

//		multiThreadedFilterOnRowKeyPrefixUsingScan();
//		TimeUnit.SECONDS.sleep(30);
	}

	private static void connectHbase() {
		Configuration hbaseConf = HBaseConfiguration.create();

		hbaseConf.set(HBASE_CONFIGURATION_ZOOKEEPER_QUORUM, hbaseZookeeeperHost);
		hbaseConf.set(HBASE_CONFIGURATION_ZOOKEEPER_CLIENTPORT, hbaseZookeeeperPort);
		hbaseConf.setLong(HBASE_CONFIGURATION_CLIENT_SCANNER_CACHING, hbaseClientScannerCaching);
		hbaseConf.setLong(HBASE_CONFIGURATION_RPC_TIMEOUT, hbaseRPCTimeout);
		hbaseConf.setLong(HBASE_CLIENT_SCANNER_TIMEOUT_PERIOD, hbaseClientScannerTimeoutPeriod);

//		System.out.println("hbase.rpc.timeout - " + hbaseConf.get("hbase.rpc.timeout"));
//		System.out.println(
//				"hbase.client.scanner.timeout.period - " + hbaseConf.get("hbase.client.scanner.timeout.period"));

		try {
//			HBaseAdmin.available(hbaseConf);

			hbaseConn = ConnectionFactory.createConnection(hbaseConf);
			hbaseAdmin = hbaseConn.getAdmin();
			table = hbaseConn.getTable(TableName.valueOf(tablename));

			aggregationClient = new AggregationClient(hbaseConf);

//			System.out.println("Master Server Name: " + hbaseAdmin.getMaster());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void scanAllRows() {
		// Scanning the table.
		Scan scan = new Scan();

		scan.setBatch(100000);

		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("name"));
		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("state"));
		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("lcount"));
		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("frstCshGmTblSz"));
		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("key4"));
		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("key5"));
		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("key13"));
		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("key19"));
		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("key16"));

		ResultScanner scanner = null;

		long startEpoch = Instant.now().toEpochMilli();

		try {
			scanner = table.getScanner(scan);

			for (Result result : scanner) {

				System.out.println("id: " + Bytes.toString(result.getRow()) + " name: "
						+ Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("name"))) + " State: "
						+ Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("state"))) + " lcount: "
						+ Bytes.toInt(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("lcount")))
						+ " frstCshGmTblSz: "
						+ Bytes.toInt(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("frstCshGmTblSz"))) + " "
						+ Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("key4"))) + " "
						+ Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("key5"))) + " "
						+ Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("key13"))) + " "
						+ Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("key19"))) + " "
						+ Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("key16"))));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}

		}

		long endEpoch = Instant.now().toEpochMilli();

		System.out.println("Full scan without filter -");
		System.out.println(
				"Thread " + Thread.currentThread().getId() + " Total Milliseconds: " + (endEpoch - startEpoch));

	}

	private static void rangeQuery() {
		// Scanning the table.
		Scan scan = new Scan();

//		scan.setBatch(10000);

		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("lcount"));
		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("frstCshGmTblSz"));
		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("key4"));
		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("key5"));
		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("key13"));
		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("key19"));
		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("key16"));

		FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);

		SingleColumnValueFilter filter1 = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("lcount"),
				CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(0));
		filter1.setFilterIfMissing(true);
		filterList.addFilter(filter1);

		SingleColumnValueFilter filter2 = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("lcount"),
				CompareOp.LESS_OR_EQUAL, Bytes.toBytes(50));
		filter2.setFilterIfMissing(true);
		filterList.addFilter(filter2);

		SingleColumnValueFilter filter3 = new SingleColumnValueFilter(Bytes.toBytes("cf"),
				Bytes.toBytes("frstCshGmTblSz"), CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(1));
		filter3.setFilterIfMissing(true);
		filterList.addFilter(filter3);

		SingleColumnValueFilter filter4 = new SingleColumnValueFilter(Bytes.toBytes("cf"),
				Bytes.toBytes("frstCshGmTblSz"), CompareOp.LESS_OR_EQUAL, Bytes.toBytes(6));
		filter4.setFilterIfMissing(true);
		filterList.addFilter(filter4);

		SingleColumnValueFilter filter5 = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("key4"),
				CompareOp.EQUAL, Bytes.toBytes("value4"));
		filter5.setFilterIfMissing(true);
		filterList.addFilter(filter5);

		SingleColumnValueFilter filter6 = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("key5"),
				CompareOp.EQUAL, Bytes.toBytes("value5"));
		filter6.setFilterIfMissing(true);
		filterList.addFilter(filter6);

		SingleColumnValueFilter filter7 = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("key13"),
				CompareOp.EQUAL, Bytes.toBytes("value13"));
		filter7.setFilterIfMissing(true);
		filterList.addFilter(filter7);

		SingleColumnValueFilter filter8 = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("key19"),
				CompareOp.EQUAL, Bytes.toBytes("value19"));
		filter8.setFilterIfMissing(true);
		filterList.addFilter(filter8);

		SingleColumnValueFilter filter9 = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("key16"),
				CompareOp.EQUAL, Bytes.toBytes("value16"));
		filter9.setFilterIfMissing(true);
		filterList.addFilter(filter9);

		scan.setFilter(filterList);

		ResultScanner scanner = null;

		long startEpoch = Instant.now().toEpochMilli();
		int count = 0;

		try {
			scanner = table.getScanner(scan);

			for (Result result : scanner) {
				count++;

//				System.out.println(Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("lcount"))));

//				System.out.println("id: " + Bytes.toString(result.getRow()) + " lcount: "
//						+ Bytes.toInt(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("lcount")))
//						+ " frstCshGmTblSz: "
//						+ Bytes.toInt(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("frstCshGmTblSz"))) + " "
//						+ Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("key4"))) + " "
//						+ Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("key5"))) + " "
//						+ Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("key13"))) + " "
//						+ Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("key19"))) + " "
//						+ Bytes.toString(result.getValue(Bytes.toBytes("cf"), Bytes.toBytes("key16"))));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
			}

		}

		long endEpoch = Instant.now().toEpochMilli();

		System.out.println("Range based filter -");
		System.out.println("Thread " + Thread.currentThread().getId() + " Total Milliseconds: "
				+ (endEpoch - startEpoch) + " Count: " + count);

	}

	private static void countAggregation() {
		Scan scan = new Scan();

		scan.addFamily(Bytes.toBytes("cf"));
//		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("frstCshGmTblSz"));
//		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("key4"));
//		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("key5"));
//		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("key13"));
//		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("key19"));
//		scan.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("key16"));

		FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);

		SingleColumnValueFilter filter1 = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("lcount"),
				CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(0));
		filter1.setFilterIfMissing(true);
		filterList.addFilter(filter1);

		SingleColumnValueFilter filter2 = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("lcount"),
				CompareOp.LESS_OR_EQUAL, Bytes.toBytes(10));
		filter2.setFilterIfMissing(true);
		filterList.addFilter(filter2);

		SingleColumnValueFilter filter3 = new SingleColumnValueFilter(Bytes.toBytes("cf"),
				Bytes.toBytes("frstCshGmTblSz"), CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(1));
		filter3.setFilterIfMissing(true);
		filterList.addFilter(filter3);

		SingleColumnValueFilter filter4 = new SingleColumnValueFilter(Bytes.toBytes("cf"),
				Bytes.toBytes("frstCshGmTblSz"), CompareOp.LESS_OR_EQUAL, Bytes.toBytes(6));
		filter4.setFilterIfMissing(true);
		filterList.addFilter(filter4);

		SingleColumnValueFilter filter5 = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("key4"),
				CompareOp.EQUAL, Bytes.toBytes("value4"));
		filter5.setFilterIfMissing(true);
		filterList.addFilter(filter5);

		SingleColumnValueFilter filter6 = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("key5"),
				CompareOp.EQUAL, Bytes.toBytes("value5"));
		filter6.setFilterIfMissing(true);
		filterList.addFilter(filter6);

		SingleColumnValueFilter filter7 = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("key13"),
				CompareOp.EQUAL, Bytes.toBytes("value13"));
		filter7.setFilterIfMissing(true);
		filterList.addFilter(filter7);

		SingleColumnValueFilter filter8 = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("key19"),
				CompareOp.EQUAL, Bytes.toBytes("value19"));
		filter8.setFilterIfMissing(true);
		filterList.addFilter(filter8);

		SingleColumnValueFilter filter9 = new SingleColumnValueFilter(Bytes.toBytes("cf"), Bytes.toBytes("key16"),
				CompareOp.EQUAL, Bytes.toBytes("value16"));
		filter9.setFilterIfMissing(true);
		filterList.addFilter(filter9);

		scan.setFilter(filterList);

		try {
			System.out.println("Starting the aggregation query.");
			long startEpoch = Instant.now().toEpochMilli();

			ColumnInterpreter ci = new LongColumnInterpreter();

			long rowCount = aggregationClient.rowCount(table, ci, scan);

			long endEpoch = Instant.now().toEpochMilli();

			System.out.println("Count aggregation -");
			System.out.println(
					"Thread " + Thread.currentThread().getId() + " Total Milliseconds: " + (endEpoch - startEpoch));
			System.out.println("Total row count: " + rowCount);

		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	private static void multiThreadedFilterOnRowKeyPrefixUsingScan() {
		int threadCount = 2;

		try {
			ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);

			for (int i = 1; i <= threadCount; i++) {
				pool.execute(new Thread() {
					@Override
					public void run() {

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

}
