package CA;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class CaMapper extends Mapper<LongWritable, Text, Text, Text> {

	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String aString = value.toString();
		String[] str = aString.split(" ");
		//从1开始，列从0
		String index = str[0];
		String[] Araster = new String[5836];
		System.arraycopy(str, 1, Araster, 0, 5836);
		String[] Braster = new String[5836];
		System.arraycopy(str, 5837, Braster, 0, 5836);
		String[] Craster = new String[5836];
		System.arraycopy(str, 11673, Craster, 0, 5836);
		String[] slope = new String[5836];
		System.arraycopy(str, 17509, slope, 0, 5836);
		String[] gaosu = new String[5836];
		System.arraycopy(str, 23345, gaosu, 0, 5836);
		String[] sheng = new String[5836];
		System.arraycopy(str, 29181, sheng, 0, 5836);
		String[] xiandian = new String[5836];
		System.arraycopy(str, 35017, xiandian, 0, 5836);
		String[] xiangdian = new String[5836];
		System.arraycopy(str, 40853, xiangdian, 0, 5836);
		String[] subway = new String[5836];
		System.arraycopy(str, 46689, subway, 0, 5836);
		String[] bus = new String[5836];
		System.arraycopy(str, 52525, bus, 0, 5836);
		String[] trian = new String[5836];
		System.arraycopy(str, 58361, trian, 0, 5836);
		String[] busStation = new String[5836];
		System.arraycopy(str, 64197, busStation, 0, 5836);
		double gengdi = 0;
		double jianzhu = 0;
		double senglin = 0;
		double Xiangdian = 0;
	
		
			if (Braster[0].equals("0") != true) {
				// 坡度与土改
				double slope1 = Double.parseDouble(slope[0]);
				if (slope1 >= 25) {
					context.write(new Text(index + " " + 0), new Text(Braster[0]));
				} else {
					// 水体不会改变为其他地类
					if (Braster[1].equals("4")) {
						context.write(new Text(index + " " + 0), new Text("4"));
					} else {
						double xiandian1 = Double.parseDouble(xiandian[0]);
						if (xiandian1 == 255) {
							xiandian1 = 0;
						}
						double xiangdian1 = Double.parseDouble(xiangdian[0]);
						if (xiangdian1 == 255) {
							xiangdian1 = 0;
						}
						double gaosu1 = Double.parseDouble(gaosu[0]);
						if (gaosu1 == 255) {
							gaosu1 = 0;
						}
						double sheng1 = Double.parseDouble(sheng[0]);
						if (sheng1 == 255) {
							sheng1 = 0;
						}
						double subway1 = Double.parseDouble(subway[0]);
						if (subway1 == 255) {
							subway1 = 0;
						}
						double bus1 = Double.parseDouble(bus[0]);
						if (bus1 == 255) {
							bus1 = 0;
						}
						double trian1 = Double.parseDouble(trian[0]);
						if (trian1 == 255) {
							trian1 = 0;
						}
						double busStation1 = Double.parseDouble(busStation[0]);
						if (busStation1 == 255) {
							busStation1 = 0;
						}

						gengdi = (0.0923 * xiandian1 + 0.1239 * xiangdian1 + 0.0485 * gaosu1 + 0.0921 * sheng1+0.0621*subway1+ 0.0721*bus1+0.0423*trian1+0.0623*busStation1)
								* getCAWeight("1", Araster[0], Araster[1], 
										"5", Braster[1], 
										Craster[0], Craster[1]);
						jianzhu =(0.2021 * xiandian1 + 0.1332 * xiangdian1 +0.0461 * gaosu1 + 0.1102 * sheng1+0.1320*subway1+ 0.1110*bus1+0.1333*trian1+0.1321*busStation1)
								* getCAWeight("2", Araster[0], Araster[1], 
										"5", Braster[1], 
										Craster[0], Craster[1]);
						senglin = (0.0103 * xiandian1 + 0.1010 * xiangdian1 + 0.0781 * gaosu1 + 0.0749 * sheng1+0.0133+subway1+0.0513* bus1+0.0201*trian1+0.0203*busStation1)
								* getCAWeight("3", Araster[0], Araster[1], 
										"5", Braster[1], 
										Craster[0], Craster[1]);
					    String CAValue=	getCAValue(jianzhu,gengdi,senglin);
					    //key:行数 列数 元胞状态值 value：转换方向1 评价值1 转换方向2 评价值2 转换方向3 评价值3 
					   // System.out.println(index + " " + 0+" "+Braster[1]+" "+CAValue);
						context.write(new Text(index + " " + 0+" "+Braster[1]), new Text(CAValue));
					}
				}
			}		
		for (int i = 1; i < Araster.length-2; i++) {
			if (Braster[i].equals("0") != true) {
				// 坡度与土改
				double slope1 = Double.parseDouble(slope[i]);
				if (slope1 > 25) {
					context.write(new Text(index + " " + i), new Text(Braster[i]));
				} else {
					// 水体不会改变为其他地类
					if (Braster[i].equals("4")) {
						context.write(new Text(index + " " + i), new Text("4"));
					} else {
						double xiandian1 = Double.parseDouble(xiandian[i]);
						if (xiandian1 == 255) {
							xiandian1 = 0;
						}
						double xiangdian1 = Double.parseDouble(xiangdian[i]);
						if (xiangdian1 == 255) {
							xiangdian1 = 0;
						}
						double gaosu1 = Double.parseDouble(gaosu[i]);
						if (gaosu1 == 255) {
							gaosu1 = 0;
						}
						double sheng1 = Double.parseDouble(sheng[i]);
						if (sheng1 == 255) {
							sheng1 = 0;
						}
						double subway1 = Double.parseDouble(subway[0]);
						if (subway1 == 255) {
							subway1 = 0;
						}
						double bus1 = Double.parseDouble(bus[0]);
						if (bus1 == 255) {
							bus1 = 0;
						}
						double trian1 = Double.parseDouble(trian[0]);
						if (trian1 == 255) {
							trian1 = 0;
						}
						double busStation1 = Double.parseDouble(busStation[0]);
						if (busStation1 == 255) {
							busStation1 = 0;
						}
						gengdi = (0.0923 * xiandian1 + 0.1239 * xiangdian1 + 0.0485 * gaosu1 + 0.0921 * sheng1+0.0621*subway1+ 0.0721*bus1+0.0423*trian1+0.0623*busStation1)
								* getCAWeight("1", Araster[i - 1], Araster[i], Araster[i +1], 
										Braster[i - 1],Braster[i], Braster[i+1], 
										Craster[i - 1], Craster[i ], Craster[i +1]);
						jianzhu = (0.2021 * xiandian1 + 0.1332 * xiangdian1 +0.0461 * gaosu1 + 0.1102 * sheng1+0.1320*subway1+ 0.1110*bus1+0.1333*trian1+0.1321*busStation1)
								* getCAWeight("2", Araster[i - 1], Araster[i], Araster[i +1], 
										Braster[i - 1],Braster[i], Braster[i+1], 
										Craster[i - 1], Craster[i ], Craster[i +1]);
						senglin = (0.0103 * xiandian1 + 0.1010 * xiangdian1 + 0.0781 * gaosu1 + 0.0749 * sheng1+0.0133+subway1+0.0513* bus1+0.0201*trian1+0.0203*busStation1)
								*getCAWeight("3", Araster[i - 1], Araster[i], Araster[i +1], 
										Braster[i - 1],Braster[i], Braster[i+1], 
										Craster[i - 1], Craster[i ], Craster[i +1]);
						String CAValue=	getCAValue(jianzhu,gengdi,senglin);
					//	System.out.println(index + " " + i+" "+Braster[i]+" "+CAValue);
						context.write(new Text(index + " " + i+" "+Braster[i]), new Text(CAValue));
					}
				}
			}
		}
		if (Braster[5835].equals("0") != true) {
			// 坡度与土改
			double slope1 = Double.parseDouble(slope[5835]);
			if (slope1 > 25) {
				context.write(new Text(index + " " + 5835), new Text(Braster[5835]));
			} else {
				// 水体不会改变为其他地类
				if (Braster[5835].equals("4")) {
					context.write(new Text(index + " " + 5835), new Text("4"));
				} else {
					double xiandian1 = Double.parseDouble(xiandian[5835]);
					if (xiandian1 == 255) {
						xiandian1 = 0;
					}
					double xiangdian1 = Double.parseDouble(xiangdian[5835]);
					if (xiangdian1 == 255) {
						xiangdian1 = 0;
					}
					double gaosu1 = Double.parseDouble(gaosu[5835]);
					if (gaosu1 == 255) {
						gaosu1 = 0;
					}
					double sheng1 = Double.parseDouble(sheng[5835]);
					if (sheng1 == 255) {
						sheng1 = 0;
					}
					double subway1 = Double.parseDouble(subway[0]);
					if (subway1 == 255) {
						subway1 = 0;
					}
					double bus1 = Double.parseDouble(bus[0]);
					if (bus1 == 255) {
						bus1 = 0;
					}
					double trian1 = Double.parseDouble(trian[0]);
					if (trian1 == 255) {
						trian1 = 0;
					}
					double busStation1 = Double.parseDouble(busStation[0]);
					if (busStation1 == 255) {
						busStation1 = 0;
					}
					gengdi = (0.0923 * xiandian1 + 0.1239 * xiangdian1 + 0.0485 * gaosu1 + 0.0921 * sheng1+0.0621*subway1+ 0.0721*bus1+0.0423*trian1+0.0623*busStation1)
							*getCAWeight("1", Araster[5834], Araster[5835], 
									Braster[5834], "5", 
									Craster[5834], Craster[5835]);
					jianzhu =(0.2021 * xiandian1 + 0.1332 * xiangdian1 +0.0461 * gaosu1 + 0.1102 * sheng1+0.1320*subway1+ 0.1110*bus1+0.1333*trian1+0.1321*busStation1)
							* getCAWeight("2", Araster[5834], Araster[5835], 
									Braster[5834], "5", 
									Craster[5834], Craster[5835]);
					senglin = (0.0103 * xiandian1 + 0.1010 * xiangdian1 + 0.0781 * gaosu1 + 0.0749 * sheng1+0.0133+subway1+0.0513* bus1+0.0201*trian1+0.0203*busStation1)
							*getCAWeight("3", Araster[0], Araster[1], 
									Braster[5834], "5", 
									Craster[5834], Craster[5835]);
					String CAValue=	getCAValue(jianzhu,gengdi,senglin);
				//	System.out.println(index + " " + 5835+" "+Braster[5835]+" "+CAValue);
					context.write(new Text(index + " " + 5835+" "+Braster[5835]), new Text(CAValue));
				}
			}
		}
	}
	public String getCAValue(double jianzhu,double gengdi,double senglin){
		String CAvalue = "";
		DecimalFormat df = new DecimalFormat("0.0000");		
		String ajianzhu="2"+" "+df.format(jianzhu);
		String bgengdi="1"+" "+df.format(gengdi);
		String csenglin="3"+" "+df.format(senglin);
		// 建筑为第一转换等级，同等评价值下转换优先
		if (jianzhu >= gengdi && jianzhu >= senglin) {
			CAvalue+=ajianzhu+" ";
			if (gengdi >= senglin) {
				CAvalue+=bgengdi+" "+csenglin;
			} else {
				CAvalue+=csenglin+" "+bgengdi;
			}
		}else {
			if (gengdi > jianzhu && gengdi >= senglin) {
				CAvalue+=bgengdi+" ";
				if (jianzhu >= senglin) {
					CAvalue+=ajianzhu+" "+csenglin;
				} else {
					CAvalue+=csenglin+" "+ajianzhu;
				}
			}else {
				if (senglin > jianzhu && senglin > gengdi) {
					CAvalue+=csenglin+" ";
					if (jianzhu >= gengdi) {
						CAvalue+=ajianzhu+" "+bgengdi;
					} else {
						CAvalue+=bgengdi+" "+ajianzhu;
					}
				}
			}			
		}
		
		return CAvalue;
	}
	public double getCAWeight(String landclass, String a1, String a2, String a3, String b1, String b2, String b3,
			String c1, String c2, String c3) {
		double sum = 0;
		
		if (landclass.equals(a1)) {
			sum  +=1;
		}
		if (landclass.equals(a2)) {
			sum  +=1;
		}
		if (landclass.equals(a3)) {
			sum  +=1;
		}
		if (landclass.equals(b1)) {
			sum  +=1;
		}
//		if (landclass .equals(b2)) {
//			sum  +=1;
//		}
		if (landclass .equals(b3)) {
			sum  +=1;
		}
		if (landclass .equals(c1)) {
			sum  +=1;
		}
		if (landclass.equals(c2)) {
			sum  +=1;
		}
		if (landclass .equals(c3)) {
			sum  +=1;
		}
		double value=sum/8;
		return value ;
	}

	public double getCAWeight(String landclass, String a2, String a3, String b2, String b3, String c2, String c3) {
		double sum = 0;

		
		if (landclass.equals(a2)) {
			sum  +=1;
		}
		if (landclass.equals(a3)) {
			sum  +=1;
		}
		
		if (landclass .equals(b2)) {
			sum  +=1;
		}
		if (landclass .equals(b3)) {
			sum  +=1;
		}
		
		if (landclass.equals(c2)) {
			sum  +=1;
		}
		if (landclass .equals(c3)) {
			sum +=1;
		}
		double value=sum/5;
		return value ;
	}
}
