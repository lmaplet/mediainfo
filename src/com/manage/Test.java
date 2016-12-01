package com.manage;


import com.manage.util.mediainfo.MediaInfo;
import com.manage.util.mediainfo.MediaInfo.InfoKind;
import com.manage.util.mediainfo.MediaInfo.StreamKind;

public class Test {
	
	/**
	 * 判断对象是否可安全转为为整数
	 * */
	public static boolean isDia(Object obj){
		if (obj == null || (""+obj).trim().equals("")) {
			return false;
		}
		String s = (""+obj).trim();
		s=s.replaceAll("-", "");
		char[] c = s.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i]>'9' || c[i]<'0') {
				return false;
			}
		}
		return true;
	}
	
	public static int getInt(Object obj){
    	if(obj==null) return 0;
   	 if ( isDia(obj) ) {
   		 Integer tmpret = Integer.valueOf(obj.toString().trim());
            return tmpret;
		}
        return 0;
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println((Platform.isWindows()&&Platform.is64Bit())?"mediainfo64":"mediainfo");

		//java调用mediainfo获取到视频音频的参数
        MediaInfo mediaInfo =new MediaInfo();
        //mediaInfo.Open("/mediaSource/大视频/速度与激情5.mpg");
        mediaInfo.Open("F:/DOWNLOAD/mm/火星救援.HD1280超清中英双字.mp4");
        //mediaInfo.Open("\\\\10.168.20.64\\storage\\JMFW\\SCK\\2013\\09\\29\\diyunanjueiif3ae39386b4b423cba01470fd39a52e4.mpg");
        //mediaInfo.Open("\\\\10.168.21.64\\storage\\JMFW\\SCK\\2013\\09\\29\\diyunanjueiif836eebae3c54f0bbc9f222bc6f0e435.mpg");
        //mediaInfo.Open("E:\\Media\\test\\111.jpg");
        mediaInfo.Option("Inform", "General");
        //输出图片信息
//        System.out.println(mediaInfo.Get(StreamKind.Image,0,"Format",InfoKind.Text,InfoKind.Name));
//        System.out.println(mediaInfo.Get(StreamKind.Image,0,"Width",InfoKind.Text,InfoKind.Name));
//        System.out.println(mediaInfo.Get(StreamKind.Image,0,"Height",InfoKind.Text,InfoKind.Name));
//        System.out.println(mediaInfo.Get(StreamKind.Image,0,"Resolution/String",InfoKind.Text,InfoKind.Name));
//        System.out.println(mediaInfo.Get(StreamKind.Image,0,"File Size",InfoKind.Text,InfoKind.Name));
//        mediaInfo.Close();
        
        //全局全局参数
       // System.out.println("文件信息："+mediaInfo.Get(StreamKind.General,0,"Inform",InfoKind.Text,InfoKind.Name));
        System.out.println("文件路径："+mediaInfo.Get(StreamKind.General,0,"CompleteName",InfoKind.Text,InfoKind.Name));
        System.out.println("文件名称："+mediaInfo.Get(StreamKind.General,0,"FileName",InfoKind.Text,InfoKind.Name));
        System.out.println("文件格式	："+mediaInfo.Get(StreamKind.General,0,"FileExtension",InfoKind.Text,InfoKind.Name));
        System.out.println("文件大小	："+mediaInfo.Get(StreamKind.General,0,"FileSize/String4",InfoKind.Text,InfoKind.Name));
        String tmpFileSize ="109951162777600";//mediaInfo.Get(StreamKind.General, 0,"FileSize", InfoKind.Text, InfoKind.Name);
        System.out.println(tmpFileSize);
		long iFileSize=0;
		if (tmpFileSize!=null) {	
			iFileSize=Long.parseLong(tmpFileSize) / 1024 / 1024;
			tmpFileSize=String.valueOf(iFileSize);
		}
        System.out.println("文件大小(M)："+tmpFileSize);
        System.out.println("分辨率	："+mediaInfo.Get(StreamKind.Video,0,"Width",InfoKind.Text,InfoKind.Name)+"*"+mediaInfo.Get(StreamKind.Video,0,"Height",InfoKind.Text,InfoKind.Name));
        System.out.println("文件时长	："+mediaInfo.Get(StreamKind.General,0,"Duration/String3",InfoKind.Text,InfoKind.Name));
        String playtime=mediaInfo.Get(StreamKind.General,0,"Duration/String3",InfoKind.Text,InfoKind.Name);
        	if(!playtime.equals("")){
				/* 01:09:37.888 转化成Sec */
				int tmpduration = 0;
				String[] tmparr = playtime.split("\\.")[0].split("\\:");
				if(tmparr.length==3){
					tmpduration=getInt(tmparr[0])*3600+getInt(tmparr[1])*60+getInt(tmparr[2]);
				}
		        System.out.println("文件时长	(秒)："+tmpduration);

			}
        System.out.println("总码率	："+mediaInfo.Get(StreamKind.General,0,"OverallBitRate/String",InfoKind.Text,InfoKind.Name));
        String tmpBitrate=mediaInfo.Get(StreamKind.General, 0,"OverallBitRate", InfoKind.Text, InfoKind.Name);
		long iBitrate=0;
		float fBitrate = 0;
		 if (tmpBitrate!=null) {
				fBitrate = Float.parseFloat(tmpBitrate) / 1000;
				iBitrate = Math.round(fBitrate);//四舍五入
				tmpBitrate = String.valueOf(iBitrate);
		}
        System.out.println("总码率（Kbps）："+tmpBitrate);
        System.out.println("文件封装格式："+mediaInfo.Get(StreamKind.General,0,"Format",InfoKind.Text,InfoKind.Name));
        System.out.println("------------------------------------------------------------------------------");
        //输出视频信息
        System.out.println("视频信息："+mediaInfo.Get(StreamKind.Video,0,"Inform",InfoKind.Text,InfoKind.Name));
        System.out.println("视频格式："+mediaInfo.Get(StreamKind.Video,0,"Format",InfoKind.Text,InfoKind.Name));
        System.out.println("视频宽："+mediaInfo.Get(StreamKind.Video,0,"Width",InfoKind.Text,InfoKind.Name));
        System.out.println("视频高："+mediaInfo.Get(StreamKind.Video,0,"Height",InfoKind.Text,InfoKind.Name));
        System.out.println("时长："+mediaInfo.Get(StreamKind.Video,0,"Duration/String3",InfoKind.Text,InfoKind.Name));
        System.out.println("码率:"+mediaInfo.Get(StreamKind.Video,0,"BitRate/String",InfoKind.Text,InfoKind.Name));
        System.out.println("码率模式："+mediaInfo.Get(StreamKind.Video,0,"BitRate_Mode/String",InfoKind.Text,InfoKind.Name));
        System.out.println("码率峰值:"+mediaInfo.Get(StreamKind.Video,0,"BitRate_Maximum/String",InfoKind.Text,InfoKind.Name));
        //System.out.println("码率峰值:"+mediaInfo.Get(StreamKind.Video,0,"BitRate_Maximum",InfoKind.Text,InfoKind.Name));
        System.out.println("帧率:"+mediaInfo.Get(StreamKind.Video,0,"FrameRate/String",InfoKind.Text,InfoKind.Name));
        System.out.println("显示比例	："+mediaInfo.Get(StreamKind.Video,0,"DisplayAspectRatio/String",InfoKind.Text,InfoKind.Name));
        System.out.println("文件大小:"+mediaInfo.Get(StreamKind.Video,0,"StreamSize/String",InfoKind.Text,InfoKind.Name));
        System.out.println("------------------------------------------------------------------------------");
        //视频中的音频信息
        System.out.println("音频信息："+mediaInfo.Get(StreamKind.Audio,0,"Inform",InfoKind.Text,InfoKind.Name));
        System.out.println("音频格式："+mediaInfo.Get(StreamKind.Audio,0,"Format",InfoKind.Text,InfoKind.Name));
        System.out.println("音频声道数："+mediaInfo.Get(StreamKind.Audio,0,"Channel(s)/String",InfoKind.Text,InfoKind.Name));
        System.out.println("音频码率："+mediaInfo.Get(StreamKind.Audio,0,"BitRate/String",InfoKind.Text,InfoKind.Name));
        System.out.println("码率模式："+mediaInfo.Get(StreamKind.Audio,0,"BitRate_Mode/String",InfoKind.Text,InfoKind.Name));
        System.out.println("时长："+mediaInfo.Get(StreamKind.Audio,0,"Duration/String3",InfoKind.Text,InfoKind.Name));
        System.out.println("码率:"+mediaInfo.Get(StreamKind.Audio,0,"BitRate/String",InfoKind.Text,InfoKind.Name));
        System.out.println("码率峰值:"+mediaInfo.Get(StreamKind.Audio,0,"BitRate_Maximum/String",InfoKind.Text,InfoKind.Name));
        System.out.println("采样率："+mediaInfo.Get(StreamKind.Audio,0,"SamplingRate/String",InfoKind.Text,InfoKind.Name));

		
		//关闭
        mediaInfo.Close();

        
        System.out.println("HAN MM");
	}
}


