package Markov;

import java.util.Calendar;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MarkovDriver {
	static Calendar Cld = Calendar.getInstance();
	static int YY = Cld.get(Calendar.YEAR) ;
	static int MM = Cld.get(Calendar.MONTH)+1;
	static int DD = Cld.get(Calendar.DATE);
	static int HH = Cld.get(Calendar.HOUR_OF_DAY);
	static int mm = Cld.get(Calendar.MINUTE);
	static int SS = Cld.get(Calendar.SECOND);
	static int MI = Cld.get(Calendar.MILLISECOND);  
	public static void main(String[] args) throws Exception {
		String startTime=YY + "/" + MM + "/" + DD + "-" + HH + ":" + mm + ":" + SS + ":" + MI;
		System.out.print("程序开始");
		
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "MakovDriver");
		job.setJarByClass(MarkovDriver.class);

		job.setJobName("MarkovDriver");
		
	//	String inPath = "D:/resource/data/cloudData/modifyMarkov";
	//	String outPath = "D:/resource/data/cloudData/output/markov";
		String inPath = "hdfs://192.168.128.1:9000/home/modifyMarkov";
		String outPath = "hdfs://192.168.128.1:9000/home/output/Markov";
//		String inPath = "hdfs://172.16.20.250:8020/lee/input/T000001dissolve";
//		String outPath = "hdfs://172.16.20.250:8020/lee/output2";

		FileInputFormat.addInputPath(job, new Path(inPath));
		FileOutputFormat.setOutputPath(job, new Path(outPath));
		//设置最大分片为0.5M
      //  FileInputFormat.setMaxInputSplitSize(job, 1024*102*7);
    	System.out.print("切片");
		job.setMapperClass(MarkovMapper.class);
		job.setCombinerClass(MarkovReducer.class);
		job.setReducerClass(MarkovReducer.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		System.out.println(job.waitForCompletion(true));
		System.out.print("程序结束"+" "+YY + "/" + MM + "/" + DD + "-" + HH + ":" + mm + ":" + SS + ":" + MI);
        System.out.println(startTime);
	}
}
