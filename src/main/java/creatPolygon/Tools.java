package creatPolygon;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class Tools {

	public static void TxtWriter(List<String> content, String outputPath) {
		FileWriter output;
		try {
			output = new FileWriter(outputPath);
			BufferedWriter bf = new BufferedWriter(output);
			for (String string : content) {
				bf.write(string );
				
			}
			bf.flush();
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
//求百分比
	public static String percnet(double d, double e) {
		double p = d / e;
		DecimalFormat nf = (DecimalFormat) NumberFormat.getPercentInstance();
		nf.applyPattern("00%"); // 00表示小数点2位
		nf.setMaximumFractionDigits(2); // 2表示精确到小数点后2位
		return nf.format(p);
	}
//保留小数点后4位
	public static   String formatDouble(double d) {
		NumberFormat nf = NumberFormat.getNumberInstance();

		// 保留4位小数
		nf.setMaximumFractionDigits(4);

		// 如果不需要四舍五入，可以使用RoundingMode.DOWN
		nf.setRoundingMode(RoundingMode.UP);

		return nf.format(d);
	}
}