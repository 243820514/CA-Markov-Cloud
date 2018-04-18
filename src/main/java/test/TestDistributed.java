package test;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Logger;


/**
 * 测试hadoop的全局共享文件 使用DistributedCached
 * 
 * 大数据技术交流群： 37693216
 * 
 * @author qindongliang
 * 
 ***/
public class TestDistributed {

	static Logger logger = Logger.getLogger(TestDistributed.class.getName());

	private static class FileMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
//		private static readHDFS read;
//		private static String txt;
//		// other static variable
//		static {
//			Configuration conf = new Configuration();
//			String txtFilePath = "hdfs://172.16.20.250:8020/lee/input/T000002dissolve";
//			txt = read.getStringByTXT(txtFilePath, conf);
//			logger.info(txt.substring(0, 10));
//		}

		@Override
		protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			logger.info("mapPrint");
			context.write(new Text("qweqweqwe" ), NullWritable.get());
		}
	}
	private static class FileReduce extends Reducer<Text, NullWritable, Text, NullWritable> {
		@Override
		protected void reduce(Text arg0, Iterable<NullWritable> arg1, Context context)
				throws IOException, InterruptedException {
			logger.info("reducePrint");
			context.write(new Text(arg0.toString().substring(0, 7)),NullWritable.get());
		}
	}
	public static void main(String[] args) throws Exception {
		logger.info("程序开始");
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "TestDistributed");
		job.setJarByClass(TestDistributed.class);
		job.setJobName("TestDistributed");
//		String inPath = "hdfs://172.16.20.250:8020/lee/input/test";
//		String outPath = "hdfs://172.16.20.250:8020/lee/output3";
		String inPath = "hdfs://192.168.128.1:9000/lee/input/test";
		String outPath = "hdfs://192.168.128.1:9000/lee/output3";

		FileInputFormat.addInputPath(job, new Path(inPath));
		FileOutputFormat.setOutputPath(job, new Path(outPath));
		// 设置最大分片为0.5M
		FileInputFormat.setMaxInputSplitSize(job, 1024 * 102 * 7);
		logger.info("切片");
		job.setMapperClass(FileMapper.class);
		job.setReducerClass(FileReduce.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(NullWritable.class);
		System.out.println(job.waitForCompletion(true));
		System.out.print("程序结束");
	}
}
