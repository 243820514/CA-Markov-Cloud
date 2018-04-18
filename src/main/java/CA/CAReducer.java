package CA;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CAReducer extends Reducer<Text, Text, Text, Text> {

	public void reduce(Text _key, Text values, Context context) throws IOException, InterruptedException {
		// process values
		context.write(new Text(_key), new Text(values));
	}

}
