package creatPolygon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.geotools.data.shapefile.dbf.DbaseFileHeader;
import org.geotools.data.shapefile.dbf.DbaseFileReader;
import org.geotools.data.shapefile.files.ShpFiles;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Geometry.Type;

public class ReadDBF {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// DBFToTxT("D:/resource/data/tdlybhForGisTools/T000001.dbf");
		 DBFToTxT("D:/resource/data/tdlybhForGisTools/T000002dissolve.dbf","D:/resource/data/tdlybhForGisTools/T000002dissolvedbf.txt");
		DBFToTxT("D:/resource/data/tdlybhForGisTools/T000001dissolve.dbf","D:/resource/data/tdlybhForGisTools/T000001dissolvedbf.txt");
	}

	// ��dbase�ļ�ת����txt��������������ֶ�
	public static void DBFToTxT(String inputpath,String outputpath) {
		DbaseFileReader reader = null;
		try {
			reader = new DbaseFileReader(new ShpFiles(inputpath), false, Charset.forName("GBK"));
			DbaseFileHeader header = reader.getHeader();
			int numFields = header.getNumFields();
			FileWriter output = new FileWriter(outputpath);
			BufferedWriter bf = new BufferedWriter(output);
			while (reader.hasNext()) {
				try {
					Object[] entry = reader.readEntry();
					String title = header.getFieldName(1);
					Object value = entry[1];
					System.out.println(value);
					bf.write(value + "\r\n");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			bf.flush();
			bf.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				// �ر�
				try {
					reader.close();
				} catch (Exception e) {
				}
			}
		}

	}

	// ��txt�ļ��ж�ȡ���������Ϣ
	public static List<String> readLandStatusFromTxT(String filePath) {
		List<String> landStatus = new ArrayList<String>();
		try {
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// ���ǵ������ʽ
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					// System.out.println(lineTxt);
					landStatus.add(lineTxt);
				}
				read.close();
			} else {
				System.out.println("�Ҳ���ָ�����ļ�");
			}
		} catch (Exception e) {
			System.out.println("��ȡ�ļ����ݳ���");
			e.printStackTrace();
		}
		return landStatus;
	}
}
