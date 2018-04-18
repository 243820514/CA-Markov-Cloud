package CA;

import java.util.Calendar;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import Markov.MarkovReducer;

public class CaDriver {
	static Calendar Cld = Calendar.getInstance();
	static int YY = Cld.get(Calendar.YEAR);
	static int MM = Cld.get(Calendar.MONTH) + 1;
	static int DD = Cld.get(Calendar.DATE);
	static int HH = Cld.get(Calendar.HOUR_OF_DAY);
	static int mm = Cld.get(Calendar.MINUTE);
	static int SS = Cld.get(Calendar.SECOND);
	static int MI = Cld.get(Calendar.MILLISECOND);
	// 由整型而来,因此格式不加0,如 2016/5/5-1:1:32:694

	public static void main(String[] args) throws Exception {
		System.out.print("程序开始" + " " + YY + "/" + MM + "/" + DD + "-" + HH + ":" + mm + ":" + SS + ":" + MI);
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "CaDriver");
		job.setJarByClass(CaDriver.class);

		job.setJobName("CaDriver");
		//String inPath = "hdfs://192.168.128.1:9000/home/CADataX3";
		//String outPath = "hdfs://192.168.128.1:9000/home/output/CAData3";
		 String inPath = "D:/resource/data/cloudData/CAData";
	     String outPath = "D:/resource/data/cloudData/output/CAData";

		FileInputFormat.addInputPath(job, new Path(inPath));
		FileOutputFormat.setOutputPath(job, new Path(outPath));
		// 设置最大分片为0.5M
		// FileInputFormat.setMaxInputSplitSize(job, 1024*102*7);
		System.out.print("切片");
		job.setMapperClass(CaMapper.class);
		//job.setCombinerClass(CAReducer.class);
	//	job.setReducerClass(CAReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		System.out.println(job.waitForCompletion(true));
		System.out.print("程序结束" + " " + YY + "/" + MM + "/" + DD + "-" + HH + ":" + mm + ":" + SS + ":" + MI);

	}
}
