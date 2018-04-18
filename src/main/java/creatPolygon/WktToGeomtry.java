package creatPolygon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.Geometry.Type;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class WktToGeomtry {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Geometry> geometrys1 = new ArrayList<Geometry>();
		List<Geometry> geometrys2 = new ArrayList<Geometry>();
		geometrys1 = WktToGoemtrys("D:/resource/data/tdlybhForGisTools/T000001dissolve.txt");
		geometrys2 = WktToGoemtrys("D:/resource/data/tdlybhForGisTools/T000002dissolve.txt");
		SpatialReference spatialReference = WktFromPrjForCreatReference(
				"D:/resource/data/tdlybhForGisTools/T000002prj.txt");
		IntersectGeometrys("D:/resource/data/tdlybhForGisTools/T000001dissolvedbf.txt",
				"D:/resource/data/tdlybhForGisTools/T000002dissolvedbf.txt", geometrys1, geometrys2, spatialReference,
				"D:/resource/data/tdlybhForGisTools/T1T2matrix.txt");
	}

	// 叠加分析，返回叠加后结果与属性值,写矩阵进txt
	public static List<LandGeometry> IntersectGeometrys(String DBFinputPath1, String DBFinputPath2,
			List<Geometry> geometrys1, List<Geometry> geometrys2, SpatialReference spatialReference,
			String matrixOutpath) {
		ReadDBF readDBF = new ReadDBF();
		List<String> geomtry1dbf = readDBF.readLandStatusFromTxT(DBFinputPath1);
		List<String> geomtry2dbf = readDBF.readLandStatusFromTxT(DBFinputPath2);
		List<LandGeometry> LandGeometrys = new ArrayList<LandGeometry>();
		List<String> matrixs = new ArrayList<String>();
		for (Geometry geometry1 : geometrys1) {
			for (Geometry geometry2 : geometrys2) {
				Geometry intersectGeometry = GeometryEngine.intersect(geometry1, geometry2, spatialReference);
				System.out.println(intersectGeometry);
				if (!intersectGeometry.isEmpty()) {
					LandGeometry landGeometry = new LandGeometry(intersectGeometry,
							geomtry1dbf.get(geometrys1.indexOf(geometry1)),
							geomtry2dbf.get(geometrys2.indexOf(geometry2)),
							Double.toString(intersectGeometry.calculateArea2D()));
					String matrix = "初" + geomtry1dbf.get(geometrys1.indexOf(geometry1)) + "   末"
							+ geomtry2dbf.get(geometrys2.indexOf(geometry2)) + "   转换面积"
							+ Double.toString(intersectGeometry.calculateArea2D()) + "   转换率"
							+ Tools.formatDouble(intersectGeometry.calculateArea2D() / geometry1.calculateArea2D());
					System.out.println(matrix);
					matrixs.add(matrix);
					matrixs.add("\r\n");
					LandGeometrys.add(landGeometry);
				}
			}
		}
		Tools.TxtWriter(matrixs, matrixOutpath);
		return LandGeometrys;
	}

	// 读取prj文件，创建参考系
	public static SpatialReference WktFromPrjForCreatReference(String filePath) {
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
	public static List<Geometry> WktToGoemtrys(String filePath) {
		List<Geometry> geometrys1 = new ArrayList<Geometry>();
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// ���ǵ������ʽ
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					Geometry g = GeometryEngine.geometryFromWkt(lineTxt, 0, Type.Polygon);
					// System.out.println(g.calculateArea2D());
					geometrys1.add(g);
				}
				read.close();

			} else {
				System.out.println("�Ҳ���ָ�����ļ�");
			}
		} catch (Exception e) {
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}
		return geometrys1;
	}
}
