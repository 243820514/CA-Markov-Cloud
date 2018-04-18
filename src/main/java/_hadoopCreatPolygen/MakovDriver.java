package _hadoopCreatPolygen;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MakovDriver {

	public static void main(String[] args) throws Exception {
		System.out.print("程序开始");
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "MakovDriver");
		job.setJarByClass(MakovDriver.class);

		job.setJobName("MakovDriver");
		
		//String inPath = "D:/resource/data/tdlybhForGisTools/Markov/T000001dissolve";
		String inPath = "hdfs://172.16.20.250:8020/lee/input/T000001dissolve";
		String outPath = "hdfs://172.16.20.250:8020/lee/output2";

		FileInputFormat.addInputPath(job, new Path(inPath));
		FileOutputFormat.setOutputPath(job, new Path(outPath));
		//设置最大分片为0.5M
        FileInputFormat.setMaxInputSplitSize(job, 1024*102*7);
    	System.out.print("切片");
		job.setMapperClass(MarkovMapper.class);

		job.setReducerClass(MarkovReducer.class);

		job.setMapOutputKeyClass(Text.class);

		job.setMapOutputValueClass(Text.class);
		System.out.println(job.waitForCompletion(true));
		System.out.print("程序结束");

	}
}
