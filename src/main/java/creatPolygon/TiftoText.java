package creatPolygon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.Driver;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconstConstants;

public class TiftoText {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		CAData("F:/leeShuang/test/06modify.txt","F:/leeShuang/test/06modifyCA.txt");
		// ReadRasterToTxt("F:/leeShuang/test/xiang11cliptif2.tif","F:/leeShuang/test/xiang11cliptif2.txt");
//		 TwoFileToOneFile("F:/leeShuang/test/4.txt",
//		 "F:/leeShuang/test/xiang11cliptif2.txt",
//		 "F:/leeShuang/test/5.txt");
		TwoFileToOneFile2( "D:/resource/data/cloudData/CAData", "D:/resource/data/cloudData/CADataX10");
	}
	public static void TwoFileToOneFile2(String filePath1, String outputPath) {
		try { 
			String encoding = "GBK";
			File file1 = new File(filePath1);
	

			FileWriter output;
			output = new FileWriter(outputPath);
			BufferedWriter bf = new BufferedWriter(output);

			if (file1.isFile() && file1.exists() ) { // 判断文件是否存在
				InputStreamReader read1 = new InputStreamReader(new FileInputStream(file1), encoding);// 考虑到编码格式
				
				BufferedReader bufferedReader1 = new BufferedReader(read1);
			
				String lineTxt1 = null;
			
				int index = 0;
				while ((lineTxt1 = bufferedReader1.readLine()) != null) {	
					for (int i = 0; i <10; i++) {
						bf.write(lineTxt1 );
						bf.write("\r\n");
					}
				index++;
				System.out.println(index);
				}
							
				bf.flush();
				bf.close();
				read1.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		System.out.println("over");
	}
    //将影像txt文件的三行合并，以便进行邻域分析
	public static void CAData(String filePath1, String outputPath) {
		try {
			String encoding = "GBK";
			File file1 = new File(filePath1);
			FileWriter output;
			output = new FileWriter(outputPath);
			BufferedWriter bf = new BufferedWriter(output);

			if (file1.isFile() && file1.exists()) { // 判断文件是否存在
				InputStreamReader read1 = new InputStreamReader(new FileInputStream(file1), encoding);// 考虑到编码格式

				BufferedReader bufferedReader1 = new BufferedReader(read1);

				String lineTxt1 = null;
				String A = "";
				String B = "";
				String C = "";
				String A1 = "";

				A = bufferedReader1.readLine() + "";
				B = bufferedReader1.readLine() + "";
				C = bufferedReader1.readLine() + "";
				for (int i = 0; i < 5836; i++) {
					A1 += "0 ";
				}
				//第一行
				bf.write("1 " + A1 + A + B);
				bf.write("\r\n");
				//第二行
				bf.write("2 " + A + B + C);
				bf.write("\r\n");
				int index = 2;
				while ((lineTxt1 = bufferedReader1.readLine()) != null) {
					index++;
					A = B;
					B = C;
					C = lineTxt1 + "";
					bf.write(index + " " + A + B + C);
					bf.write("\r\n");
				}
				A = B;
				B = C;
			   //最后一行
				bf.write("2973 " + A + B + A1);
				bf.flush();
				bf.close();
				read1.close();

			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		System.out.println("over");
	}

	public static void TwoFileToOneFile(String filePath1, String filePath2, String outputPath) {
		try {
			String encoding = "GBK";
			File file1 = new File(filePath1);
			File file2 = new File(filePath2);

			FileWriter output;
			output = new FileWriter(outputPath);
			BufferedWriter bf = new BufferedWriter(output);

			if (file1.isFile() && file1.exists() && file2.isFile() && file2.exists()) { // 判断文件是否存在
				InputStreamReader read1 = new InputStreamReader(new FileInputStream(file1), encoding);// 考虑到编码格式
				InputStreamReader read2 = new InputStreamReader(new FileInputStream(file2), encoding);// 考虑到编码格式
				BufferedReader bufferedReader1 = new BufferedReader(read1);
				BufferedReader bufferedReader2 = new BufferedReader(read2);
				String lineTxt1 = null;
				String lineTxt2 = null;
				while ((lineTxt1 = bufferedReader1.readLine()) != null) {
					lineTxt2 = bufferedReader2.readLine();
					bf.write(lineTxt1 + lineTxt2);
					bf.write("\r\n");
				}
				bf.flush();
				bf.close();
				read1.close();
				read2.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		System.out.println("over");
	}

	public static void ReadRasterToTxt(String inputPath, String outputPath) {
		gdal.AllRegister();
		Dataset hDataset = gdal.Open(inputPath, gdalconstConstants.GA_Update);
		if (hDataset == null) {
			System.err.println("GDALOpen failed - " + gdal.GetLastErrorNo());
			System.err.println(gdal.GetLastErrorMsg());
			System.exit(1);
		}
		Driver hDriver = hDataset.GetDriver();
		System.out.println("Driver: " + hDriver.getShortName() + "/" + hDriver.getLongName());
		int iXSize = hDataset.getRasterXSize();
		int iYSize = hDataset.getRasterYSize();
		System.out.println("Size is" + iXSize + "," + iYSize);
		Band band = hDataset.GetRasterBand(1);
		band.FlushCache();
		hDataset.FlushCache();
		int buf[] = new int[iXSize];
		FileWriter output;
		try {
			output = new FileWriter(outputPath);
			BufferedWriter bf = new BufferedWriter(output);
			for (int i = 0; i < iYSize; i++) {
				band.ReadRaster(0, i, iXSize, 1, buf);//
				for (int j = 0; j < iXSize; j++)
					bf.write(buf[j] + " ");
				// cellValue.add(buf[j] + " ");
				// System.out.print(buf[j] + " ");
				// cellValue.add("\r\n");
				bf.write("\r\n");
				// System.out.println("\n");
			}
			bf.flush();
			bf.close();
			hDataset.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("over");
	}

}
