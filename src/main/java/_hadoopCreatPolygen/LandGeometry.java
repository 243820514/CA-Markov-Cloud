package _hadoopCreatPolygen;

import com.esri.core.geometry.Geometry;

public class LandGeometry {
   private Geometry geometry ;
   private String   landStatus ;

public Geometry getGeometry() {
	return geometry;
}
public String getLandStatus() {
	return landStatus;
}
public void setGeometry(Geometry geometry) {
	this.geometry = geometry;
}
public LandGeometry(Geometry geometry, String landStatus) {
	super();
	this.geometry = geometry;
	this.landStatus = landStatus;

} 
	
}
