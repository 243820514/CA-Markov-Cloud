package test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
public class readHDFS {

	public static String getStringByTXT(String txtFilePath, Configuration conf)
	{

		StringBuffer buffer = new StringBuffer();
		FSDataInputStream fsr = null;
		BufferedReader bufferedReader = null;
		String line = null;
		String lineText=null;
		try
		{
			FileSystem fs = FileSystem.get(URI.create(txtFilePath),conf);
			fsr = fs.open(new Path(txtFilePath));
			bufferedReader = new BufferedReader(new InputStreamReader(fsr));		
			while ((line = bufferedReader.readLine()) != null)
			{
				lineText+=line.substring(0,10);	
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (bufferedReader != null)
			{
				try
				{
					bufferedReader.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return lineText;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Configuration conf = new Configuration();
		String txtFilePath = "hdfs://172.16.20.250:8020/lee/input/test";
		String mbline = getStringByTXT(txtFilePath, conf);
		System.out.println(mbline);
	}

}
