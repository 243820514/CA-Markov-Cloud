package _hadoopCreatPolygen;

import java.io.IOException;

import java.util.List;



import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.Geometry.Type;


public class MarkovMapper extends Mapper<LongWritable, Text, Text, Text> {
	
	
    static Tools tools=new Tools();  
    private static List<LandGeometry> geometrys;
    private static SpatialReference spatialReference;
    //other static variable
    static{
//    	temp1 = tools.getfile("D:/resource/data/tdlybhForGisTools/Markov/test");
//         temp2 = tools.getfile("hdfs://172.16.20.250:8020/lee/input/test");
//    	geometrys = tools.WktToGoemtrys("D:/resource/data/tdlybhForGisTools/T000002dissolve.txt");	
//    	SpatialReference spatialReference = tools.WktFromPrjForCreatReference("D:/resource/data/tdlybhForGisTools/T000002prj.txt");
    	System.out.println("全局");
    	geometrys = tools.HDFSWktToGoemtrys("hdfs://172.16.20.250:8020/lee/input/T000002dissolve");	
    	SpatialReference spatialReference = tools.HDFSWktFromPrjForCreatReference("hdfs://172.16.20.250:8020/lee/input/T000002prj");
    }
   
	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {	
		String aString=ivalue.toString().substring(1);
		Geometry geometry1 = GeometryEngine.geometryFromWkt(ivalue.toString().substring(1), 0, Type.Polygon);
		String landClasss=ivalue.toString().substring(0, 1);
		//每个geometry与全局的geometrys进行循环计算
		for (LandGeometry geometry : geometrys) {
			Geometry intersectGeometry = GeometryEngine.intersect(geometry1, geometry.getGeometry(), spatialReference);
			context.write(new Text(landClasss+"to"+geometry.getLandStatus()), new Text( Double.toString(intersectGeometry.calculateArea2D())));
		}		
	}
}
