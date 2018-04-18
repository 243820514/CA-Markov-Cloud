package creatPolygon;

import com.esri.core.geometry.Geometry;

public class LandGeometry {
   private Geometry geometry ;
   private String   landStatus1 ;
   private String   landStatus2 ;
   private String   area;
public Geometry getGeometry() {
	return geometry;
}
public void setGeometry(Geometry geometry) {
	this.geometry = geometry;
}
public String getLandStatus1() {
	return landStatus1;
}
public void setLandStatus1(String landStatus1) {
	this.landStatus1 = landStatus1;
}
public String getLandStatus2() {
	return landStatus2;
}
public void setLandStatus2(String landStatus2) {
	this.landStatus2 = landStatus2;
}
public String getArea() {
	return area;
}
public void setArea(String area) {
	this.area = area;
}
public LandGeometry(Geometry geometry, String landStatus1, String landStatus2, String area) {
	super();
	this.geometry = geometry;
	this.landStatus1 = landStatus1;
	this.landStatus2 = landStatus2;
	this.area = area;
} 
	
}
