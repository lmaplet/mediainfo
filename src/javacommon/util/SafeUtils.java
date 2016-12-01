package javacommon.util;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class SafeUtils {
	
	public static String getString(Object obj){
        String tmpret="";
        if(obj!=null)
            tmpret = obj.toString();
        return tmpret.trim();
    }
	
    /**
  	 * 判断对象是否可安全转为为整数
  	 * */
  	public static boolean isDia(Object obj){
  		boolean result = false;
  		if (obj == null || (""+obj).trim().equals("")) {
  			return false;
  		}
  		String s = ""+obj;
  		char[] c = s.toCharArray();
  		for (int i = 0; i < c.length; i++) {
  			if (c[i]>'9' || c[i]<'0') {
  				return false;
  			}
  		}
  		return true;
  	}
	
	public static Long getLong(Object obj){
        if(obj==null) return null;
        if ( isDia(obj) ) {
       	 Long tmpret = Long.valueOf(obj.toString().trim());
            return tmpret;
		}
        return null;
    }
	
	   public static String getCurrentTimeStr(String format){
	        String tmpret = "";
	        try{
	            Calendar ca = Calendar.getInstance();
	            SimpleDateFormat sdf = new SimpleDateFormat(format);
	            tmpret = sdf.format(ca.getTime());
	        }
	        catch(Exception ex){
	            System.out.println("getCurrentTimeStr:"+ex.getMessage());
	        }
	        return tmpret;
	    }
	   
	   private static AtomicInteger idIntger = new AtomicInteger(100);
		private static Date startDate = safeGetDate("20130910000000");
		public static long getNextId() {
			if (idIntger.get() >= 900) {
				idIntger.set(100);
			}
			return  getLong((System.currentTimeMillis()-startDate.getTime())+ "" +getLocalIPFlag()+ ""+ idIntger.getAndIncrement());
		}
		
		public static Date safeGetDate(String dateformat) {
			dateformat = dateformat.replaceAll("[^0-9]", "");
			if (dateformat.replaceAll("[0-9]*", "").length() == 0) {
				if (dateformat.length() == 8) {
					return getSqlDate(dateformat, "yyyyMMdd");
				}
				if (dateformat.length() == 14) {
					return getSqlDate(dateformat, "yyyyMMddHHmmss");
				}
				if (dateformat.length() == 12) {
					return getSqlDate(dateformat, "yyyyMMddHHmm");
				}
				if (dateformat.length() == 6) {
					return getSqlDate(dateformat, "HHmmss");
				}
			}
			return null;
		}
		
		static String LocalIPFlag="";
		public static String getLocalIPFlag(){
			if (LocalIPFlag.length()>0) { return LocalIPFlag; }
			try {
				InetAddress[] inets=InetAddress.getAllByName(InetAddress.getLocalHost().getHostName());
				for (int i = 0; i < inets.length; i++) {
					int flag=inets[i].getAddress()[3]<<0  &  0xff ;
					if ( flag>1 ) {
						 LocalIPFlag="000"+flag;
						 LocalIPFlag=LocalIPFlag.substring(LocalIPFlag.length()-3, LocalIPFlag.length());
					}
				}
			} catch (Exception e) {
				System.err.println(e);
			}
			return LocalIPFlag;
		}
		public static Date getSqlDate(Object obj, String format) {
			java.util.Date tmpdate = null;
			if ((format == null) || (format.equals("")))
				format = "yyyy-MM-dd HH:mm:ss";
			java.text.SimpleDateFormat s = new java.text.SimpleDateFormat(format);
			if (obj != null) {
				try {
					if (obj.toString().startsWith("1-")) {
						return tmpdate;
					}
					tmpdate = new java.util.Date(s.parse(obj.toString().trim())
							.getTime());
				} catch (Exception ex) {
					System.err.println(ex);
				}
			}
			return tmpdate;
		}
		public static void main(String[] args) {
			System.out.println("1");
		}
}
