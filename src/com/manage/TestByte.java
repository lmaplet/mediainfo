package com.manage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.List;

import javacommon.util.SafeUtils;

import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;

//ReadFromFile
public class TestByte {
	private static String hexStr =  "0123456789ABCDEF";
	private static String[] binaryArray =   
    {"0000","0001","0010","0011",  
    "0100","0101","0110","0111",  
    "1000","1001","1010","1011",  
    "1100","1101","1110","1111"};
	
	    /**
	     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
	     */
	    public static void readFileByBytes(String fileName) {
	        File file = new File(fileName);
	        InputStream in = null;
	        try {
	            System.out.println("以字节为单位读取文件内容，一次读一个字节：");
	            // 一次读一个字节
	            in = new FileInputStream(file);
	            int tempbyte;
	            while ((tempbyte = in.read()) != -1) {
	            	//in.re
	                System.out.write(tempbyte);
	            }
	            in.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return;
	        }
	        try {
	            System.out.println("以字节为单位读取文件内容，一次读多个字节：");
	            // 一次读多个字节
	            byte[] tempbytes = new byte[100];
	            int byteread = 0;
	            in = new FileInputStream(fileName);
	            showAvailableBytes(in);
	            // 读入多个字节到字节数组中，byteread为一次读入的字节数
	            while ((byteread = in.read(tempbytes)) != -1) {
	                System.out.write(tempbytes, 0, byteread);
	            }
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        } finally {
	            if (in != null) {
	                try {
	                    in.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	    }

	    /**
	     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	     */
	    public static void readFileByChars(String fileName) {
	        File file = new File(fileName);
	        Reader reader = null;
	        try {
	            System.out.println("以字符为单位读取文件内容，一次读一个字节：");
	            // 一次读一个字符
	            reader = new InputStreamReader(new FileInputStream(file));
	            int tempchar;
	            while ((tempchar = reader.read()) != -1) {
	                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
	                // 但如果这两个字符分开显示时，会换两次行。
	                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
	                if (((char) tempchar) != '\r') {
	                    System.out.print((char) tempchar);
	                }
	            }
	            reader.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        try {
	            System.out.println("以字符为单位读取文件内容，一次读多个字节：");
	            // 一次读多个字符
	            char[] tempchars = new char[30];
	            int charread = 0;
	            reader = new InputStreamReader(new FileInputStream(fileName));
	            // 读入多个字符到字符数组中，charread为一次读取字符数
	            while ((charread = reader.read(tempchars)) != -1) {
	                // 同样屏蔽掉\r不显示
	                if ((charread == tempchars.length)
	                        && (tempchars[tempchars.length - 1] != '\r')) {
	                    System.out.print(tempchars);
	                } else {
	                    for (int i = 0; i < charread; i++) {
	                        if (tempchars[i] == '\r') {
	                            continue;
	                        } else {
	                            System.out.print(tempchars[i]);
	                        }
	                    }
	                }
	            }

	        } catch (Exception e1) {
	            e1.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	    }

	    /**
	     * 以行为单位读取文件，常用于读面向行的格式化文件
	     */
	    public static void readFileByLines(String fileName) {
	        File file = new File(fileName);
	        BufferedReader reader = null;
	        try {
	            System.out.println("以行为单位读取文件内容，一次读一整行：");
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            int line = 1;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString = reader.readLine()) != null) {
	                // 显示行号
	                System.out.println("line " + line + ": " + tempString);
	                line++;
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	    }

	    /**
	     * 随机读取文件内容
	     */
	    public static void readFileByRandomAccess(String fileName) {
	        RandomAccessFile randomFile = null;
	        try {
	            System.out.println("随机读取一段文件内容：");
	            // 打开一个随机访问文件流，按只读方式
	            randomFile = new RandomAccessFile(fileName, "r");
	            // 文件长度，字节数
	            long fileLength = randomFile.length();
	            // 读文件的起始位置
	            int beginIndex = (fileLength > 4) ? 4 : 0;
	            // 将读文件的开始位置移到beginIndex位置。
	            randomFile.seek(beginIndex);
	            byte[] bytes = new byte[10];
	            int byteread = 0;
	            // 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
	            // 将一次读取的字节数赋给byteread
	            while ((byteread = randomFile.read(bytes)) != -1) {
	                System.out.write(bytes, 0, byteread);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (randomFile != null) {
	                try {
	                    randomFile.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	    }

	    /**
	     * 显示输入流中还剩的字节数
	     * @throws InterruptedException 
	     */
	    private static void showAvailableBytes(InputStream in) throws InterruptedException {
	        try {
	            System.out.println("当前字节输入流中的字节数为:" + in.available());
	            //Thread.sleep(1000);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    public static byte[] image2byte(String path){
	        byte[] data = null;
	        FileInputStream fileinput = null;
	        InputStream input = null;
	        try {
	          fileinput = new FileInputStream(new File(path));
	          input = new FileInputStream(new File(path));
	          BufferedInputStream bs = new BufferedInputStream(fileinput);
	          ByteArrayOutputStream output = new ByteArrayOutputStream();
	          byte[] buf = new byte[1024];
	          int numBytesRead = 0;
	          while ((numBytesRead = bs.read(buf)) != -1) {
	          output.write(buf, 0, numBytesRead);
	          }
	          data = output.toByteArray();
	          output.close();
	          input.close();
	        }
	        catch (FileNotFoundException ex1) {
	          ex1.printStackTrace();
	        }
	        catch (IOException ex1) {
	          ex1.printStackTrace();
	        }
	        return data;
	      }
	      //byte数组到图片
	      public void byte2image(byte[] data,String path){
	        if(data.length<3||path.equals("")) return;
	        try{
	        FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
	        imageOutput.write(data, 0, data.length);
	        imageOutput.close();
	        System.out.println("Make Picture success,Please find image in " + path);
	        } catch(Exception ex) {
	          System.out.println("Exception: " + ex);
	          ex.printStackTrace();
	        }
	      }
	      //byte数组到16进制字符串
	      public static String byte2string(byte[] data){
	        if(data==null||data.length<1) return "0x";
	        if(data.length>200000) return "0x";
	        StringBuffer sb = new StringBuffer();
	        int buf[] = new int[data.length];
	        //byte数组转化成十进制
	        for(int k=0;k<data.length;k++){
	          buf[k] = data[k]<0?(data[k]+256):(data[k]);
	        }
	        //十进制转化成十六进制
	        for(int k=0;k<buf.length;k++){
	          if(buf[k]<16) sb.append("0"+Integer.toHexString(buf[k]));
	          else sb.append(Integer.toHexString(buf[k]));
	        }
	        return "0x"+sb.toString().toUpperCase();

	      } 
	      
	      
	      /** 
	       *  
	       * @param str 
	       * @return 转换为二进制字符串 
	       */  
	      public static String bytes2BinaryStr(byte[] bArray){  
	            
	          String outStr = "";  
	          int pos = 0;  
	          for(byte b:bArray){  
	              //高四位  
	              pos = (b&0xF0)>>4;  
	              outStr+=binaryArray[pos];  
	              //低四位  
	              pos=b&0x0F;  
	              outStr+=binaryArray[pos];  
	          }  
	          return outStr;  
	            
	      }  
	      /** 
	       *  
	       * @param bytes 
	       * @return 将二进制转换为十六进制字符输出 
	       */  
	      public static String BinaryToHexString(byte[] bytes){  
	            
	          String result = "";  
	          String hex = "";  
	          for(int i=0;i<bytes.length;i++){  
	              //字节高4位  
	              hex = String.valueOf(hexStr.charAt((bytes[i]&0xF0)>>4));  
	              //字节低4位  
	              hex += String.valueOf(hexStr.charAt(bytes[i]&0x0F));  
	              result +=hex;  
	          }  
	          return result;  
	      } 
	      
	   public static byte[] readByte(String path){
		        byte[] data = null;
		        //File file =new File(path);  
		        FileInputStream fileinput = null;
		        try {
		          fileinput = new FileInputStream(path);
		          BufferedInputStream bs = new BufferedInputStream(fileinput);
		          ByteArrayOutputStream output = new ByteArrayOutputStream();
		          //StringBuilder strBer = new StringBuilder();
		          String rootPath = "E:/data/ts/out/";
		          File file = new File(rootPath);
		          if (!file.exists()) {
		        	  file.mkdirs();
				  }
		          String filePath =(new StringBuilder(rootPath)).append(SafeUtils.getCurrentTimeStr("yyyyMMddHHmmssS")).append("_").append(SafeUtils.getString(SafeUtils.getNextId())).append(".ts").toString();

		          BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));  
		          byte[] buf = new byte[4];
		          byte[] tsBufBody = new byte[184];
		          byte[] tsBufOption = new byte[167];
		          byte[] tsBufOptionNext = new byte[1];
		          int numBytesRead = 0;
		          String tmpByteToOx = "";
		          String tmpByteToOx2 = "";
		          while ((numBytesRead = bs.read(buf)) != -1) {  
		          tmpByteToOx = BinaryToHexString(buf);
		          //System.out.println(tmpByteToOx);
		          if (tmpByteToOx!=null ) {
		        	  if (tmpByteToOx.startsWith("24") && tmpByteToOx.endsWith("24")) {
						  //$开头跳过，读取下4个字节
					  }else if (tmpByteToOx.startsWith("47")) {
		        		  bos.write(buf);//写入输出文件
			        	  if (bs.read(tsBufBody) != -1) {//读取后面184字节
//				        	  tmpByteToOx = BinaryToHexString(tsBufBody);
//				        	  if (tmpByteToOx.contains("52545350")) {
//								System.out.println("middle:"+tmpByteToOx);
//							  }
				        	  bos.write(tsBufBody);
						  }
					  }else if (tmpByteToOx.startsWith("52545350")) {//处理option
//						  if (bs.read(tsBufOption) !=-1) {//跳过option,168(167)字节
//							  tmpByteToOx = BinaryToHexString(tsBufOption);
//						  }
						  //System.out.println("option:"+tmpByteToOx+","+tmpByteToOx2);
						  while (bs.read(tsBufOptionNext) !=-1) {//寻址以“0D0A0D0A”为结尾的为结束符,由于option字节数不固定,需要逐一读取下一个字节
							  tmpByteToOx = BinaryToHexString(tsBufOptionNext);
							  if (tmpByteToOx!=null && tmpByteToOx.startsWith("0D")) {
								  if (bs.read(tsBufOptionNext) !=-1) {
									  tmpByteToOx = BinaryToHexString(tsBufOptionNext);
									  if (tmpByteToOx!=null && tmpByteToOx.startsWith("0A")) {
										 if (bs.read(tsBufOptionNext) !=-1) {
											 tmpByteToOx = BinaryToHexString(tsBufOptionNext);
											 if (tmpByteToOx!=null && tmpByteToOx.startsWith("0D")) {
												if (bs.read(tsBufOptionNext) !=-1) {
													tmpByteToOx = BinaryToHexString(tsBufOptionNext);
													if (tmpByteToOx!=null && tmpByteToOx.startsWith("0A")) {
														break;
													}
												}
											}
										}
									  }
								 }
							  }
						  }
					  }//else if (!tmpByteToOx.startsWith("24") && !tmpByteToOx.startsWith("52545350")) {
//						   bos.write(buf);
//					  }
				  }
		         }
//		          while ((numBytesRead = bs.read()) != -1) {  
//		        	  output.write(numBytesRead);  
//		          System.out.println(BinaryToHexString(output.toByteArray()));
//		          } 
		          //data = output.toByteArray();
		          bos.close();
		          output.close();
		          fileinput.close();
		        }
		        catch (Exception ex) {
		          System.out.println("ERROR:"+ex+ex.getMessage());
		        }
		        return data;
		      }	
	   
	   public static byte[] readOneByte(String path){
	        byte[] data = null;
	        File file =new File(path);  
	        FileInputStream fileinput = null;
	        try {
	          fileinput = new FileInputStream(file);
	          BufferedInputStream bs = new BufferedInputStream(fileinput);

	          byte[] buf = new byte[1];
	          int numBytesRead = 0;
	          String tmpByteToOx = "";
	          while ((numBytesRead = bs.read(buf)) != -1) {  
	
	          }
//	          while ((numBytesRead = bs.read()) != -1) {  
//	        	  output.write(numBytesRead);  
//	          System.out.println(BinaryToHexString(output.toByteArray()));
//	          } 
	          //data = output.toByteArray();
	          fileinput.close();
	        }
	        catch (Exception ex) {
	          System.out.println("ERROR:"+ex+ex.getMessage());
	        }
	        return data;
	      }	
	   
	   
public static byte[] readAllByte(String path) {
	File file =new File(path);  
    long len = file.length();  
    byte[] bytes = new byte[(int)len];   
	try {
		FileInputStream fileinput = new FileInputStream(new File(path));
        BufferedInputStream bis = new BufferedInputStream(fileinput);
        bis.read(bytes);
        bis.close();
        fileinput.close();
	} catch (Exception ex) {
		ex.printStackTrace();
	}
	return bytes;
}
	   

public static byte[] readTsByte(String path){
	
	  byte[] data = null;
	  FileInputStream fileinput = null;
	  try {
	        fileinput = new FileInputStream(new File(path));
	        BufferedInputStream bs = new BufferedInputStream(fileinput);
	        ByteArrayOutputStream output = new ByteArrayOutputStream();
	        byte[] buf = new byte[1];
	        int numBytesRead = 0;
	        String tmpByteToOx = "";
	        while ((numBytesRead = bs.read(buf)) != -1) {
	          output = new ByteArrayOutputStream();
		      tmpByteToOx = BinaryToHexString(output.toByteArray());
		      if (tmpByteToOx!=null && tmpByteToOx.equals("24")) {
		    	  buf = new byte[5];
		    	  bs.skip(4);
		    	  if ((numBytesRead = bs.read(buf)) != -1) {
		    		  output = new ByteArrayOutputStream();
				      tmpByteToOx = BinaryToHexString(output.toByteArray());
		    		  if (tmpByteToOx!=null && tmpByteToOx.equals("47")) {
		    			  byte[] buf2 = new byte[188];
					}
		    		  
				}
			}
		     buf = new byte[1];
	       }
	          data = output.toByteArray();
	          output.close();
	          fileinput.close();
	     }
	     catch (Exception ex) {
	          ex.printStackTrace();
	     }
	  return data;
}


	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String fileName = "E:/data/PKzqb0k1gy74xjr5iafv.ts";
			System.out.println("begin:"+SafeUtils.getCurrentTimeStr("yyyy-MM-dd HH:mm:ss"));
			//byte[] data = readByte(fileName);
			System.out.println("end:"+SafeUtils.getCurrentTimeStr("yyyy-MM-dd HH:mm:ss"));
		} catch (Exception ex) {
			System.out.println(ex+ex.getMessage());
		}

	}

}
