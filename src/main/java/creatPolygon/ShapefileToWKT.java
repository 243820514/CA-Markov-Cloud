package creatPolygon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Geometry.Type;
import com.esri.core.geometry.GeometryEngine;

public class ShapefileToWKT {
	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		File file = new File("D:/resource/data/tdlybhForGisTools/T000001.shp");
//		try {
//			FileInputStream input = new FileInputStream(file);		
//			List<String>  list=new ArrayList<String>();
//			int index=0;
//			// �������ļ�ͷ
//			readNum(input, 100);
//			while (input.available() > 0) {
//				byte[] getGeometryBytes = getGeometryBytes(input);
//				Geometry g = GeometryEngine.geometryFromEsriShape(getGeometryBytes, Type.Polygon);
//				String wkt=GeometryEngine.geometryToWkt(g,0);
//				list.add(wkt);							
//			}
	//	StringWriter("D:/resource/data/tdlybhForGisTools/T000002dissolve.shp","D:/resource/data/tdlybhForGisTools/T000002dissolve.txt");	
		StringWriter("D:/resource/data/tdlybhForGisTools/T000001dissolve.shp","D:/resource/data/tdlybhForGisTools/T000001dissolve.txt");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	//从shapefile文件中得到geometry，将geometry转换为WKT并且写入txt文件
    private static void StringWriter(String inputPath,String outputPath){
    	
    	try {
    		File file = new File(inputPath);
    		FileInputStream input = new FileInputStream(file);		
			List<String>  list=new ArrayList<String>();
			int index=0;
			// �������ļ�ͷ
			readNum(input, 100);
			while (input.available() > 0) {
				byte[] getGeometryBytes = getGeometryBytes(input);
				Geometry g = GeometryEngine.geometryFromEsriShape(getGeometryBytes, Type.Polygon);
				System.out.println(g.calculateArea2D());
				String wkt=GeometryEngine.geometryToWkt(g,0);
				list.add(wkt);							
			}
			
    		FileWriter output = new FileWriter(outputPath);
    		BufferedWriter bf = new BufferedWriter(output);
    		for (String l : list) {
    		bf.write(l + "\r\n");
    
    		 }
    		bf.flush();// �˴��ܹؼ��������д����䣬�ǲ��ܴӻ�����д���ļ����
    		} catch (FileNotFoundException e) {
    		e.printStackTrace();
    		} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		}

    }
	//�Ķ�sha�ļ�����ȡ����geometry���ֽ�����
	private static byte[] getGeometryBytes(FileInputStream input) {
		byte[] head8 = new byte[8];
		head8 = read8(input);// ��¼ͷ�ļ�

		byte[] shapeType4 = new byte[4];
		shapeType4 = read4(input);

		byte[] box32 = new byte[32];
		box32 = readBox(input);
		// byte[] b=new byte[44];
		// b= readNum(input, 44);//����NumPartsλ��

		byte[] numParts4 = read4(input);
		byte[] numPoints4 = read4(input);
		int numParts = bytesToInt(numParts4, 0);
		int numPoints = bytesToInt(numPoints4, 0);
		// int numParts = read4Little(input);
		// int numPoints = read4Little(input);

		byte[] parts = new byte[numParts * 4];
		parts = readNum(input, numParts * 4);
		byte[] points = new byte[numPoints * 16];
		points = readNum(input, numPoints * 16);

		byte[] geomtreyBytes = new byte[4 + 32 + 4 + 4 + numParts * 4 + numPoints * 16];
		int index = 0;
		for (int j = 0; j < shapeType4.length; j++) {
			geomtreyBytes[index] = shapeType4[j];
			index++;
		}
		for (int j = 0; j < box32.length; j++) {
			geomtreyBytes[index] = box32[j];
			index++;
		}
		for (int j = 0; j < numParts4.length; j++) {
			geomtreyBytes[index] = numParts4[j];
			index++;
		}
		for (int j = 0; j < numPoints4.length; j++) {
			geomtreyBytes[index] = numPoints4[j];
			index++;
		}
		for (int j = 0; j < parts.length; j++) {
			geomtreyBytes[index] = parts[j];
			index++;
		}
		for (int j = 0; j < points.length; j++) {
			geomtreyBytes[index] = points[j];
			index++;
		}

		return geomtreyBytes;
		// int[] parts = new int[numParts];
		// for(int i = 0; i < numParts; i++)
		// parts[i] = read4Little(input);
		//
		// Point[] points = new Point[numPoints];
		// for(int i = 0; i < numPoints; i++) {
		// points[i] = new Point();
		// points[i].setX(read8(input));
		// points[i].setY(read8(input));
		// }

		// TODO Auto-generated method stub

	}

	private static byte[] read4(FileInputStream input) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			try {
				b[i] = (byte) input.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}

	private static byte[] readNum(FileInputStream input, int num) {
		byte[] b = new byte[num];
		for (int i = 0; i < num; i++) {
			try {
				b[i] = (byte) input.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}

	private static byte[] readBox(FileInputStream input) {
		byte[] b = new byte[32];
		for (int i = 0; i < 32; i++) {
			try {
				b[i] = (byte) input.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}

	private static int read4Big(FileInputStream input) {
		return bytesToInt2(read4(input), 0);
	}

	private static int read4Little(FileInputStream input) {
		return bytesToInt(read4(input), 0);
	}

	private static byte[] read8(FileInputStream input) {
		byte[] b = new byte[8];
		for (int i = 0; i < 8; i++) {
			try {
				b[i] = (byte) input.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;

	}

	/**
	 * ��λ������byte������ȡint��ֵ
	 * 
	 * @param src
	 *            byte����
	 * @param offset
	 *            ������ĵ�offsetλ��ʼ
	 * @return int��ֵ
	 */
	public static int bytesToInt(byte[] src, int offset) {
		int value;
		value = (int) ((src[offset] & 0xFF) | ((src[offset + 1] & 0xFF) << 8) | ((src[offset + 2] & 0xFF) << 16)
				| ((src[offset + 3] & 0xFF) << 24));
		return value;
	}

	/**
	 * ��λ������byte������ȡint��ֵ
	 */
	public static int bytesToInt2(byte[] src, int offset) {
		int value;
		value = (int) (((src[offset] & 0xFF) << 24) | ((src[offset + 1] & 0xFF) << 16) | ((src[offset + 2] & 0xFF) << 8)
				| (src[offset + 3] & 0xFF));
		return value;
	}

	/**
	 * ͨ��byte����ȡ��Double
	 *
	 * @param bb
	 * @param index
	 * @return
	 */
	public static double bytesToDouble(byte[] b, int index) {
		long l;
		l = b[0];
		l &= 0xff;
		l |= ((long) b[1] << 8);
		l &= 0xffff;
		l |= ((long) b[2] << 16);
		l &= 0xffffff;
		l |= ((long) b[3] << 24);
		l &= 0xffffffffl;
		l |= ((long) b[4] << 32);
		l &= 0xffffffffffl;
		l |= ((long) b[5] << 40);
		l &= 0xffffffffffffl;
		l |= ((long) b[6] << 48);
		l &= 0xffffffffffffffl;
		l |= ((long) b[7] << 56);
		return Double.longBitsToDouble(l);
	}

}
