package creatPolygon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.hdfs.server.namenode.web.resources.NamenodeWebHdfsMethods;
import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.Driver;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconstConstants;

import com.sun.javafx.collections.MappingChange.Map;
import com.vividsolutions.jts.operation.valid.IndexedNestedRingTester;

public class GDALTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SetWeight();
		String fileName_tif = "D:/resource/data/tdlybhForGisTools/RasterBack/t000002raster.tif";
		List<String> cellValue = ReadRaster(fileName_tif);		
		//ModifyCellValue(ReadRasterToArry(fileName_tif),fileName_tif);
		String outputPath2 = "D:/resource/data/tdlybhForGisTools/RasterBack/t2raster2.txt";
		// 将值矩阵写进txt
		Tools.TxtWriter(cellValue, outputPath2);		
	}

	static double[][] Weight = new double[6][256];

	private static void SetWeight() {
		// map.put("11", 0.6801);
				Weight[1][1] = 0.6801;
				// map.put("13", 0.2225);
				Weight[1][2] = 0;
				Weight[1][3] = 0.2225;
				// map.put("14", 0.0939);
				Weight[1][4] = 0.0037;
				// map.put("15", 0.013);
				Weight[1][5] = 0.0939;
				
				// map.put("23", 0.9871);
				Weight[2][1] = 0;
				Weight[2][2] = 0;
				Weight[2][3] = 0.013;
				// map.put("25", 0.0998);
				Weight[2][4] = 0;
				Weight[2][5] = 0.9871;
				// map.put("31", 0.0001);
				Weight[3][1] = 0.0998;
				// map.put("32", 0.607);
				Weight[3][2] = 0.0001;
				// map.put("33", 0.6801);
				Weight[3][3] = 0.607;
				// map.put("34", 0.6801);
				Weight[3][4] = 0.0461;
				// map.put("35", 0.6801);
				Weight[3][5] = 0.2472;
				// map.put("41", 0.6801);
				Weight[4][1] = 0.0009;
				// map.put("43", 0.6801);
				Weight[4][2] = 0;
				Weight[4][3] = 0.0677;
				// map.put("44", 0.6801);
				Weight[4][4] = 0.8285;
				// map.put("45", 0.6801);
				Weight[4][5] = 0.1031;
				// map.put("51", 0.6801);
				Weight[5][1] = 0.0039;
				Weight[5][2] = 0;
				// map.put("53", 0.6801);
				Weight[5][3] = 0.0799;
				// map.put("54", 0.6801);
				Weight[5][4] = 0.2481;
				// map.put("55", 0.6801);
				Weight[5][5] = 0.6683;
				//				
				Weight[1][255] = 0;
				Weight[2][255] = 0;
				Weight[3][255] = 0;
				Weight[4][255] = 0;
				Weight[5][255] = 0;
//		// map.put("11", 0.6801);
//		Weight[0][0] = 0.6801;
//		// map.put("13", 0.2225);
//		Weight[0][1] = 0;
//		Weight[0][2] = 0.2225;
//		// map.put("14", 0.0939);
//		Weight[0][3] = 0.0037;
//		// map.put("15", 0.013);
//		Weight[0][4] = 0.0939;
//		
//		// map.put("23", 0.9871);
//		Weight[1][0] = 0;
//		Weight[1][1] = 0;
//		Weight[1][2] = 0.013;
//		// map.put("25", 0.0998);
//		Weight[1][3] = 0;
//		Weight[1][4] = 0.9871;
//		// map.put("31", 0.0001);
//		Weight[2][0] = 0.0998;
//		// map.put("32", 0.607);
//		Weight[2][1] = 0.0001;
//		// map.put("33", 0.6801);
//		Weight[2][2] = 0.607;
//		// map.put("34", 0.6801);
//		Weight[2][3] = 0.0461;
//		// map.put("35", 0.6801);
//		Weight[2][4] = 0.2472;
//		// map.put("41", 0.6801);
//		Weight[3][0] = 0.0009;
//		// map.put("43", 0.6801);
//		Weight[3][1] = 0;
//		Weight[3][2] = 0.0677;
//		// map.put("44", 0.6801);
//		Weight[3][3] = 0.8285;
//		// map.put("45", 0.6801);
//		Weight[3][4] = 0.1031;
//		// map.put("51", 0.6801);
//		Weight[4][0] = 0.0039;
//		Weight[4][1] = 0;
//		// map.put("53", 0.6801);
//		Weight[4][2] = 0.0799;
//		// map.put("54", 0.6801);
//		Weight[4][3] = 0.2481;
//		// map.put("55", 0.6801);
//		Weight[4][4] = 0.6683;
//		//
//		Weight[0][255] = 0;
//		Weight[1][255] = 0;
//		Weight[2][255] = 0;
//		Weight[3][255] = 0;
//		Weight[4][255] = 0;
	}

	public static void ModifyCellValue(int[][] arry, String inputPath) {
		gdal.AllRegister();
		Dataset hDataset = gdal.Open(inputPath, gdalconstConstants.GA_Update);
		if (hDataset == null) {
			System.err.println("GDALOpen failed - " + gdal.GetLastErrorNo());
			System.err.println(gdal.GetLastErrorMsg());
			System.exit(1);
		}
		
		int rows = arry.length;// 行数
		int columns = arry[0].length;// 列数
		int cellvaluesBuf[]=new int[rows*columns];
		
		int changeToCellValue11;
		if (arry[0][0]==255) {
			changeToCellValue11=255;
		}else {
			// 计算第一行第一列
			int[][] cellvalues11 = new int[2][2];
			double WeightValues11[] = new double[4];		
			cellvalues11[0][0] = arry[0][0];// 中心像元
			cellvalues11[0][1] = arry[0][1];
			cellvalues11[1][0] = arry[1][0];
			cellvalues11[1][1] = arry[1][1];
			WeightValues11[0] = Weight[cellvalues11[0][0]][cellvalues11[0][0]];
			WeightValues11[1] = Weight[cellvalues11[0][0]][cellvalues11[0][1]];
			WeightValues11[2] = Weight[cellvalues11[0][0]][cellvalues11[1][0]];
			WeightValues11[3] = Weight[cellvalues11[0][0]][cellvalues11[1][1]];
			int cellLocation11 = GetValue(WeightValues11);
			int x11 = cellLocation11 / 2;
			int y11 = cellLocation11 % 2;
			changeToCellValue11 = cellvalues11[x11][y11];
		}
		cellvaluesBuf[0]=changeToCellValue11;
		//System.out.println(0+"--"+cellvaluesBuf[0]);
		//System.out.println("计算第一行的第二列至倒数第二列");

		// 计算第一行的第二列至倒数第二列
		for (int i = 1; i < columns - 1; i++) {		
			int changeToCellValue;
			int[][] cellvalues = new int[3][3];
			if ( arry[0][i]==255) {
				changeToCellValue=255;
			}else {
				// 该3X3元胞内所有像元的值
				cellvalues[0][0] = 0;			   cellvalues[0][1] = 0;			cellvalues[0][2] = 0;
				cellvalues[1][0] = arry[0][i - 1]; cellvalues[1][1] = arry[0][i];   cellvalues[1][2] = arry[0][i + 1];
				cellvalues[2][0] = arry[1][i - 1]; cellvalues[2][1] = arry[1][i];	cellvalues[2][2] = arry[1][i + 1];	
//				System.out.println(cellvalues[1][0]);
//				System.out.println(cellvalues[1][1]);
//				System.out.println(cellvalues[1][2]);
//				System.out.println(cellvalues[2][0]);
//				System.out.println(cellvalues[2][1]);
//				System.out.println(cellvalues[2][2]);
				double WeightValues[] = new double[9];
				// 元胞中心像元转换为元胞其他像元的概率——即权值
				WeightValues[0] = Weight[cellvalues[1][1]][cellvalues[0][0]];
				WeightValues[1] = Weight[cellvalues[1][1]][cellvalues[0][1]];
				WeightValues[2] = Weight[cellvalues[1][1]][cellvalues[0][2]];
				WeightValues[3] = Weight[cellvalues[1][1]][cellvalues[1][0]];
				WeightValues[4] = Weight[cellvalues[1][1]][cellvalues[1][1]];
				WeightValues[5] = Weight[cellvalues[1][1]][cellvalues[1][2]];
				WeightValues[6] = Weight[cellvalues[1][1]][cellvalues[2][0]];
				WeightValues[7] = Weight[cellvalues[1][1]][cellvalues[2][1]];
				WeightValues[8] = Weight[cellvalues[1][1]][cellvalues[2][2]];
				int cellLocation = GetValue(WeightValues);
				// 确定应转换成的像元值的像元的位置
				int x = cellLocation / 3;
				int y = cellLocation % 3;
				// 得到应转换成的像元值
				changeToCellValue = cellvalues[x][y];
			}
			cellvaluesBuf[i]=changeToCellValue11;
//			System.out.println(i+"--"+cellvaluesBuf[i]);
		}
//		System.out.println("计算第一行最后一列");
		int changeToCellValueZH;
		if (arry[0][columns-1]==255) {
			changeToCellValueZH=255;
		}else {
			// 计算第一行最后一列
			int[][] cellvaluesZH = new int[2][2];
			double WeightValuesZH[] = new double[4];
			cellvaluesZH[0][0] = arry[0][columns-2]; cellvaluesZH[0][1] = arry[0][columns-1];// 中心像元
			cellvaluesZH[1][0] = arry[1][columns-2]; cellvaluesZH[1][1] = arry[1][columns-1];
			WeightValuesZH[0] = Weight[cellvaluesZH[0][0]][cellvaluesZH[0][0]];
			WeightValuesZH[1] = Weight[cellvaluesZH[0][0]][cellvaluesZH[0][1]];
			WeightValuesZH[2] = Weight[cellvaluesZH[0][0]][cellvaluesZH[1][0]];
			WeightValuesZH[3] = Weight[cellvaluesZH[0][0]][cellvaluesZH[1][1]];
			int cellLocationZH = GetValue(WeightValuesZH);
			int xZH = cellLocationZH / 2;
			int yZH = cellLocationZH % 2;
			changeToCellValueZH = cellvaluesZH[xZH][yZH];
		}	
		cellvaluesBuf[columns-1]=changeToCellValueZH;
//		System.out.println((columns-1)+"--"+cellvaluesBuf[columns-1]);
//		
//		System.out.println("计算第二行至倒数第二行的第一列");		
		// 计算第二行至倒数第二行
		for (int j = 1; j < rows - 1; j++) {
			//计算第一列
			int changeToCellValueN1 ;
			if (arry[j][0]==255) {
				changeToCellValueN1=255;
			}else {
				int[][] cellvaluesN1 = new int[3][3];
				double WeightValuesN1[] = new double[9];
				cellvaluesN1[0][0] = 0;	cellvaluesN1[0][1] = arry[j - 1][0]; cellvaluesN1[0][2] = arry[j - 1][1];
				cellvaluesN1[1][0] = 0;	cellvaluesN1[1][1] = arry[j][0];     cellvaluesN1[1][2] = arry[j][1];
			    cellvaluesN1[2][0] = 0;	cellvaluesN1[2][1] = arry[j + 1][0]; cellvaluesN1[2][2] = arry[j + 1][1];
			    
				WeightValuesN1[0] = Weight[cellvaluesN1[1][1]][cellvaluesN1[0][0]];
				WeightValuesN1[1] = Weight[cellvaluesN1[1][1]][cellvaluesN1[0][1]];
				WeightValuesN1[2] = Weight[cellvaluesN1[1][1]][cellvaluesN1[0][2]];
				WeightValuesN1[3] = Weight[cellvaluesN1[1][1]][cellvaluesN1[1][0]];
				WeightValuesN1[4] = Weight[cellvaluesN1[1][1]][cellvaluesN1[1][1]];
				WeightValuesN1[5] = Weight[cellvaluesN1[1][1]][cellvaluesN1[1][2]];
				WeightValuesN1[6] = Weight[cellvaluesN1[1][1]][cellvaluesN1[2][0]];
				WeightValuesN1[7] = Weight[cellvaluesN1[1][1]][cellvaluesN1[2][1]];
				WeightValuesN1[8] = Weight[cellvaluesN1[1][1]][cellvaluesN1[2][2]];
				
				int cellLocationN1 = GetValue(WeightValuesN1);
				int xN1 = cellLocationN1 / 3;
				int yN1 = cellLocationN1 % 3;
			    changeToCellValueN1 = cellvaluesN1[xN1][yN1];				
			}
			cellvaluesBuf[(columns*j+1)-1]=changeToCellValueN1;
//			System.out.println(((columns*j+1)-1)+"--"+cellvaluesBuf[(columns*j+1)-1]);
//			
//			System.out.println("计算第二行至倒数第二行的计算第二列至倒数第二列");			
   			//计算第二列至倒数第二列
			for (int i = 1; i < columns - 1; i++) {
				int changeToCellValue;
				if (arry[j][i]==255) {
					changeToCellValue=255;
				}else {
					int[][] cellvalues = new int[3][3];		
					double WeightValues[] = new double[9];
					// 该3X3元胞内所有像元的值
					cellvalues[0][0] = arry[j - 1][i - 1]; cellvalues[0][1] = arry[j - 1][i]; cellvalues[0][2] = arry[j - 1][i + 1];
					cellvalues[1][0] = arry[j][i - 1];     cellvalues[1][1] = arry[j][i];     cellvalues[1][2] = arry[j][i + 1];
					cellvalues[2][0] = arry[j + 1][i - 1]; cellvalues[2][1] = arry[j + 1][i]; cellvalues[2][2] = arry[j + 1][i + 1];
					// 元胞中心像元转换为元胞其他像元的概率——即权值
					WeightValues[0] = Weight[cellvalues[1][1]][cellvalues[0][0]];
					WeightValues[1] = Weight[cellvalues[1][1]][cellvalues[0][1]];
					WeightValues[2] = Weight[cellvalues[1][1]][cellvalues[0][2]];
					WeightValues[3] = Weight[cellvalues[1][1]][cellvalues[1][0]];
					WeightValues[4] = Weight[cellvalues[1][1]][cellvalues[1][1]];
					WeightValues[5] = Weight[cellvalues[1][1]][cellvalues[1][2]];
					WeightValues[6] = Weight[cellvalues[1][1]][cellvalues[2][0]];
					WeightValues[7] = Weight[cellvalues[1][1]][cellvalues[2][1]];
					WeightValues[8] = Weight[cellvalues[1][1]][cellvalues[2][2]];
					int cellLocation = GetValue(WeightValues);
					// 确定应转换成的像元值的像元的位置
					int x = cellLocation / 3;
					int y = cellLocation % 3;
					// 得到应转换成的像元值
					changeToCellValue = cellvalues[x][y];					
				}	
				cellvaluesBuf[(columns*j+1+i)-1]=changeToCellValue;
//				System.out.println(((columns*j+1+i)-1)+"--"+cellvaluesBuf[(columns*j+1+i)-1]);
			}
//			System.out.println("计算第二行至倒数第二行的最后一列计算");
			
			// 计算第二行至倒数第二行的最后一列计算
			int changeToCellValueNZH;
			if (arry[j][columns-1]==255) {
				changeToCellValueNZH=255;
			}else {				
				int[][] cellvaluesNZH = new int[3][3];
				double WeightValuesNZH[] = new double[9];			
				cellvaluesNZH[0][0] = arry[j - 1][columns-2]; cellvaluesNZH[0][1] = arry[j - 1][columns-1]; cellvaluesNZH[0][2] = 0;
				cellvaluesNZH[1][0] = arry[j][columns-2];	  cellvaluesNZH[1][1] = arry[j][columns-1];     cellvaluesNZH[1][2] = 0;
				cellvaluesNZH[2][0] = arry[j + 1][columns-2]; cellvaluesNZH[2][1] = arry[j + 1][columns-1]; cellvaluesNZH[2][2] = 0;	    
			    WeightValuesNZH[0] = Weight[cellvaluesNZH[1][1]][cellvaluesNZH[0][0]];
			    WeightValuesNZH[1] = Weight[cellvaluesNZH[1][1]][cellvaluesNZH[0][1]];
			    WeightValuesNZH[2] = Weight[cellvaluesNZH[1][1]][cellvaluesNZH[0][2]];
			    WeightValuesNZH[3] = Weight[cellvaluesNZH[1][1]][cellvaluesNZH[1][0]];
			    WeightValuesNZH[4] = Weight[cellvaluesNZH[1][1]][cellvaluesNZH[1][1]];
			    WeightValuesNZH[5] = Weight[cellvaluesNZH[1][1]][cellvaluesNZH[1][2]];
			    WeightValuesNZH[6] = Weight[cellvaluesNZH[1][1]][cellvaluesNZH[2][0]];
			    WeightValuesNZH[7] = Weight[cellvaluesNZH[1][1]][cellvaluesNZH[2][1]];
			    WeightValuesNZH[8] = Weight[cellvaluesNZH[1][1]][cellvaluesNZH[2][2]];	    
				int cellLocationNZH= GetValue(WeightValuesNZH);
				int xNZH = cellLocationNZH / 3;
				int yNZH = cellLocationNZH % 3;
				changeToCellValueNZH = cellvaluesNZH[xNZH][yNZH];
			}
			cellvaluesBuf[columns*(j+1)-1]=changeToCellValueNZH;		
//			System.out.println((columns*(j+1)-1)+"--"+cellvaluesBuf[columns*(j+1)-1]);
		}
		
//		System.out.println("计算最后一行的第一列");
		int changeToCellValueZHH;
		if (arry[rows-1][0]==255) {
			changeToCellValueZHH=255;
		}else {
			// 计算最后一行的第一列
			int[][] cellvaluesZHH = new int[2][2];
			double WeightValuesZHH[] = new double[4];		
			cellvaluesZHH[0][0] = arry[rows-2][0];
			cellvaluesZHH[0][1] = arry[rows-2][1];
			cellvaluesZHH[1][0] = arry[rows-1][0];// 中心像元
			cellvaluesZHH[1][1] = arry[rows-1][1];		
			WeightValuesZHH[0] = Weight[cellvaluesZHH[0][0]][cellvaluesZHH[0][0]];
			WeightValuesZHH[1] = Weight[cellvaluesZHH[0][0]][cellvaluesZHH[0][1]];
			WeightValuesZHH[2] = Weight[cellvaluesZHH[0][0]][cellvaluesZHH[1][0]];
			WeightValuesZHH[3] = Weight[cellvaluesZHH[0][0]][cellvaluesZHH[1][1]];		
			int cellLocationNZHH = GetValue(WeightValuesZHH);
			int xZHH = cellLocationNZHH / 2;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
			int yZHH = cellLocationNZHH % 2;
			changeToCellValueZHH = cellvaluesZHH[xZHH][yZHH];
		}
		cellvaluesBuf[(columns*(rows-1)+1)-1]=changeToCellValueZHH;		
//		System.out.println(((columns*(rows-1)+1)-1)+"--"+cellvaluesBuf[(columns*(rows-1)+1)-1]);
//		System.out.println("计算最后一行的第二列至倒数第二列");
		
		// 计算最后一行的第二列至倒数第二列
		for (int i = 1; i < columns - 1; i++) {	
			int changeToCellValue;
			if (arry[rows-1][i]==255) {
				changeToCellValue=255;
			}else {
				int[][] cellvalues = new int[3][3];
				double WeightValues[] = new double[9];
				// 该3X3元胞内所有像元的值
				cellvalues[0][0] = arry[rows-2][i - 1]; cellvalues[0][1] = arry[rows-2][i ];cellvalues[0][2] = arry[rows-2][i + 1];
				cellvalues[1][0] = arry[rows-1][i - 1]; cellvalues[1][1] = arry[rows-1][i]; cellvalues[1][2] = arry[rows-1][i + 1];
				cellvalues[2][0] = 0;                   cellvalues[2][1] = 0;	            cellvalues[2][2] = 0;		
				// 元胞中心像元转换为元胞其他像元的概率——即权值
				WeightValues[0] = Weight[cellvalues[1][1]][cellvalues[0][0]];
				WeightValues[1] = Weight[cellvalues[1][1]][cellvalues[0][1]];
				WeightValues[2] = Weight[cellvalues[1][1]][cellvalues[0][2]];
				WeightValues[3] = Weight[cellvalues[1][1]][cellvalues[1][0]];
				WeightValues[4] = Weight[cellvalues[1][1]][cellvalues[1][1]];
				WeightValues[5] = Weight[cellvalues[1][1]][cellvalues[1][2]];
				WeightValues[6] = Weight[cellvalues[1][1]][cellvalues[2][0]];
				WeightValues[7] = Weight[cellvalues[1][1]][cellvalues[2][1]];
				WeightValues[8] = Weight[cellvalues[1][1]][cellvalues[2][2]];
				int cellLocation = GetValue(WeightValues);
				// 确定应转换成的像元值的像元的位置
				int x = cellLocation / 3;
				int y = cellLocation % 3;
				// 得到应转换成的像元值
				changeToCellValue = cellvalues[x][y];
			}
			cellvaluesBuf[(columns*(rows-1)+1+i)-1]=changeToCellValue;	
//			System.out.println(((columns*(rows-1)+1+i)-1)+"--"+cellvaluesBuf[(columns*(rows-1)+1+i)-1]);
		}
		
//		System.out.println("计算最后一行的最后一列");
		int changeToCellValueZHZL ;
		if (arry[rows-1][columns-1]==255) {
			changeToCellValueZHZL=255;
		}else {
			// 计算最后一行的最后一列
			int[][] cellvaluesZHZL = new int[2][2];
			double WeightValuesZHZL[] = new double[4];
			cellvaluesZHZL[0][0] = arry[rows-2][columns-2];
			cellvaluesZHZL[0][1] = arry[rows-2][columns-1];
			cellvaluesZHZL[1][0] = arry[rows-1][columns-2];
			cellvaluesZHZL[1][1] = arry[rows-1][columns-1];// 中心像元
			WeightValuesZHZL[0] = Weight[cellvaluesZHZL[0][0]][cellvaluesZHZL[0][0]];
			WeightValuesZHZL[1] = Weight[cellvaluesZHZL[0][0]][cellvaluesZHZL[0][1]];
			WeightValuesZHZL[2] = Weight[cellvaluesZHZL[0][0]][cellvaluesZHZL[1][0]];
			WeightValuesZHZL[3] = Weight[cellvaluesZHZL[0][0]][cellvaluesZHZL[1][1]];
			int cellLocationZHZL = GetValue(WeightValuesZHZL);
			int xZHZL = cellLocationZHZL / 2;
			int yZHZL = cellLocationZHZL % 2;
			changeToCellValueZHZL= cellvaluesZHZL[xZHZL][yZHZL];
		}
		cellvaluesBuf[(columns*rows)-1]=changeToCellValueZHZL;		
//		System.out.println(((columns*rows)-1)+"--"+cellvaluesBuf[(columns*rows)-1]);
						
		Band band = hDataset.GetRasterBand(1);
		band.WriteRaster(0, 0,  columns, rows,cellvaluesBuf);
		band.FlushCache();
		hDataset.FlushCache();
		hDataset.delete();
	}
	
	// 得到概率和最大的值的序号
	public static int GetValue(double[] WeightValues) {
		
		HashMap<Double, Double> hs = new HashMap<Double, Double>();
		for (int i = 0; i < WeightValues.length; i++) {
			// WeightValues值相同的像元的个数
			double count = 1;
			if (hs.get(WeightValues[i]) != null) {
				count = hs.get(WeightValues[i]) + 1;
			}
			hs.put(WeightValues[i], count);
		}
		// System.out.println(hs);
		// System.out.print("重复的有:");
		// for (Double key : hs.keySet()) {
		// if (hs.get(key)!=null&hs.get(key)>1) {
		// System.out.print(key+" ");
		// }
		// }
		// double[] weightSum=new double[hs.size()];
		int index=0;
		double keyIndex = 0;
		for (Double key : hs.keySet()) {
			double maxValue = 0;
			// double maxKey;
			
			if (key * hs.get(key) > maxValue) {
				// 概率和的最大值，即转换为该像元的总概率，最大的
				maxValue = key * hs.get(key);
				// 单个的概率
				// maxKey=key;
				// 得到该键值对的序号
				keyIndex = key;
			}
		}
		for (int i = 0; i < WeightValues.length; i++) {
			if(keyIndex==WeightValues[i]){
				index=i;
				break;
			}
		}
		return index;
	}

	public static int[][] GetArrRaster(String inputPath) {
		gdal.AllRegister();
		Dataset hDataset = gdal.Open(inputPath, gdalconstConstants.GA_Update);
		if (hDataset == null) {
			System.err.println("GDALOpen failed - " + gdal.GetLastErrorNo());
			System.err.println(gdal.GetLastErrorMsg());
			System.exit(1);
		}
		Driver hDriver = hDataset.GetDriver();
		// System.out.println("Driver: " + hDriver.getShortName() + "/" +
		// hDriver.getLongName());
		int iXSize = hDataset.getRasterXSize();
		int iYSize = hDataset.getRasterYSize();
		// System.out.println("Size is" + iXSize + "," + iYSize);
		Band band = hDataset.GetRasterBand(1);
		int buf[] = new int[iXSize];
		int ArryRaster[][] = new int[iYSize][iXSize];

		for (int i = 0; i < iYSize; i++) {
			band.ReadRaster(0, i, iXSize, 1, buf);//
			for (int j = 0; j < iXSize; j++) {
				ArryRaster[i][j] = buf[j];
			}
		}
		hDataset.delete();
		return ArryRaster;
	}

	public static List<String> ReadRaster(String inputPath) {
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
//		int buf1[] = new int[1];
//		buf1[0] = 1;
//		band.WriteRaster(0, 0, 1, 1, buf1);
		band.FlushCache();
		hDataset.FlushCache();
		int buf[] = new int[iXSize];
		List<String> cellValue = new ArrayList<String>();

		for (int i = 0; i < iYSize; i++) {
			band.ReadRaster(0, i, iXSize, 1, buf);//
			for (int j = 0; j < iXSize; j++)

				cellValue.add(buf[j] + " ");
			// System.out.print(buf[j] + " ");
			cellValue.add("\r\n");
			// System.out.println("\n");
		}

		hDataset.delete();
		return cellValue;

	}
	
	public static int[][] ReadRasterToArry(String inputPath) {
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
		int[][] cellValues=new int[iYSize][iXSize];
		System.out.println("Size is" + iXSize + "," + iYSize);
		Band band = hDataset.GetRasterBand(1);
		
		band.FlushCache();
		hDataset.FlushCache();
		int buf[] = new int[iXSize];
		for (int i = 0; i < iYSize; i++) {
			band.ReadRaster(0, i, iXSize, 1, buf);
			for (int j = 0; j < iXSize; j++)
			{
				cellValues[i][j]=buf[j];
			}				
		}
		hDataset.delete();
		return cellValues;
	}
}
