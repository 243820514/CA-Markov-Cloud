package _hadoopCreatPolygen;

import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;


public class Test2 {
	static Logger logger = Logger.getLogger(Test2.class.getName());

	public static void main(String[] args) throws InterruptedException {

		logger.debug(" This time is {}"+new Date().toString());
		logger.info(" This time is {}"+new Date().toString() );
		logger.warn(" This time is {}"+new Date().toString() );
		logger.error(" This time is {}"+ new Date().toString());

	}

}
