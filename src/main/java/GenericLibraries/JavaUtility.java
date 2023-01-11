package GenericLibraries;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * This class contains all java reusable methods
 * @author Qspider Basavanagudi
 *
 */

public class JavaUtility {
 /**
  * This method is used to generate Random Number
  * @param limit
  * @return
  */
 
	
	public int generateRandomNumber(int limit) {
		Random random=new Random();
		return random.nextInt(limit);
	}
	/**
	 * This method is used to get currentTime
	 * @return
	 */
	
	public String currentTime() {
		
		Date date= new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("DD_MM_hh_mm_ss");
		String actualDate=sdf.format(date);
		return actualDate;
		}
	
}
