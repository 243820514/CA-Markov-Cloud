package _hadoopCreatPolygen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.Geometry.Type;
import _hadoopCreatPolygen.LandGeometry;





public class Tools {
	public String getfile(String filePath) {
		String s = null;
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// ���ǵ������ʽ
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					s = lineTxt;
				}
				read.close();

			} else {
				System.out.println("失败");
			}
		} catch (Exception e) {
			System.out.println("失败");
			e.printStackTrace();
		}
		return s;
	}
	// 叠加分析，返回叠加后结果与属性值
//		public  List<LandGeometry> IntersectGeometrys(String DBFinputPath1, String DBFinputPath2,
//				Geometry geometry1, List<Geometry> geometrys2, SpatialReference spatialReference,
//				String matrixOutpath) {
//			ReadDBF readDBF = new ReadDBF();
//			List<String> geomtry1dbf = readDBF.readLandStatusFromTxT(DBFinputPath1);
//			List<String> geomtry2dbf = readDBF.readLandStatusFromTxT(DBFinputPath2);
//			List<LandGeometry> LandGeometrys = new ArrayList<LandGeometry>();
//			List<String> matrixs = new ArrayList<String>();
//			
//				for (Geometry geometry2 : geometrys2) {
//					Geometry intersectGeometry = GeometryEngine.intersect(geometry1, geometry2, spatialReference);
//					if (!intersectGeometry.isEmpty()) {
//						LandGeometry landGeometry = new LandGeometry(intersectGeometry,
//								geomtry1dbf.get(geometrys1.indexOf(geometry1)),
//								geomtry2dbf.get(geometrys2.indexOf(geometry2)),
//								Double.toString(intersectGeometry.calculateArea2D()));
//						String matrix = "初" + geomtry1dbf.get(geometrys1.indexOf(geometry1)) + "   末"
//								+ geomtry2dbf.get(geometrys2.indexOf(geometry2)) + "   转换面积"
//								+ Double.toString(intersectGeometry.calculateArea2D()) + "   转换率"
//								+ Tools.formatDouble(intersectGeometry.calculateArea2D() / geometry1.calculateArea2D());
//						System.out.println(matrix);
//						mtrixs.add(matrix);
//						matrixs.add("\r\n");
//						LandGeometrys.add(landGeometry);
//					}
//				}
//			}
		
		
	// 读取prj文件，创建参考系
	public  SpatialReference HDFSWktFromPrjForCreatReference(String filePath) {
		SpatialReference spatialReference = null;
		Configuration conf = new Configuration();
		StringBuffer buffer = new StringBuffer();
		FSDataInputStream fsr = null;
		BufferedReader bufferedReader = null;
		String lineTxt = null;
		try
		{
			FileSystem fs = FileSystem.get(URI.create(filePath),conf);
			fsr = fs.open(new Path(filePath));
			bufferedReader = new BufferedReader(new InputStreamReader(fsr));		
			while ((lineTxt = bufferedReader.readLine()) != null)
			{
				spatialReference = SpatialReference.create(lineTxt);				
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
		return spatialReference;
	}
		// 读取prj文件，创建参考系
		public  SpatialReference WktFromPrjForCreatReference(String filePath) {
			SpatialReference spatialReference = null;
			try {
				String encoding = "GBK";
				File file = new File(filePath);
				if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
					InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// ���ǵ������ʽ
					BufferedReader bufferedReader = new BufferedReader(read);
					String lineTxt = null;
					while ((lineTxt = bufferedReader.readLine()) != null) {
						// System.out.println(lineTxt);
						spatialReference = SpatialReference.create(lineTxt);
					}
					read.close();

				} else {
					System.out.println("�Ҳ���ָ�����ļ�");
				}
			} catch (Exception e) {
				System.out.println("��ȡ�ļ����ݳ���");
				e.printStackTrace();
			}
			return spatialReference;
		}
	/**
	 * 功能：Java读取txt文件的内容 步骤：1：先获得文件句柄 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
	 * 3：读取到输入流后，需要读取生成字节流 4：一行一行的输出。readline()。 备注：需要考虑的是异常情况
	 * 读取WKT文件的过程中，构建Geometry，且存入Geometry，list
	 * 
	 * @param filePath
	 */
	public List<LandGeometry> WktToGoemtrys(String filePath) {
		List<LandGeometry> geometrys = new ArrayList<LandGeometry>();
		
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// ���ǵ������ʽ
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					Geometry g = GeometryEngine.geometryFromWkt(lineTxt.substring(1), 0, Type.Polygon);
					
					LandGeometry geometry=new LandGeometry(g,lineTxt.substring(0, 1));
					// System.out.println(g.calculateArea2D());
					geometrys.add(geometry);
				}
				read.close();
			} else {
				System.out.println("wkt文件读取失败");
			}
		} catch (Exception e) {
			System.out.println("wkt文件读取失败");
			e.printStackTrace();
		}
		return geometrys;
	}
	public List<LandGeometry> HDFSWktToGoemtrys(String filePath) {
		Configuration conf = new Configuration();
		List<LandGeometry> geometrys = new ArrayList<LandGeometry>();	
		StringBuffer buffer = new StringBuffer();
		FSDataInputStream fsr = null;
		BufferedReader bufferedReader = null;
		String lineTxt = null;
		try
		{
			FileSystem fs = FileSystem.get(URI.create(filePath),conf);
			fsr = fs.open(new Path(filePath));
			bufferedReader = new BufferedReader(new InputStreamReader(fsr));		
			while ((lineTxt = bufferedReader.readLine()) != null)
			{
				// System.out.println(lineTxt);
				Geometry g = GeometryEngine.geometryFromWkt(lineTxt.substring(1), 0, Type.Polygon);			
				LandGeometry geometry=new LandGeometry(g,lineTxt.substring(0, 1));
				// System.out.println(g.calculateArea2D());
				geometrys.add(geometry);				
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
		return geometrys;
	}
	
	
}
