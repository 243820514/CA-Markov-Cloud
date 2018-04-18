package Markov;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MarkovMapper extends Mapper<LongWritable, Text, Text,IntWritable> {

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String aString = value.toString();
		String str[] = aString.split(" ");
		int length=str.length/2;
		for (int i = 0; i < length; i++) {
            String txt= str[i]+"-"+str[length+i];
            context.write(new Text(txt), new IntWritable(1));
		}
	}
}
