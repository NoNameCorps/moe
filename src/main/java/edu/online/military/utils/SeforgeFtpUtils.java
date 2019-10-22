package edu.online.military.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import edu.online.military.login.controller.LoginController;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeforgeFtpUtils {

	private static final Logger log = LoggerFactory.getLogger(SeforgeFtpUtils.class);


	/**
  * 上传文件方法
  *@param url 			FTP服务器IP
  *@param port 			FTP服务器端口号
  *@param username 		FTP用户名
  *@param password 		FTP用户密码
  *@param path 			FTP服务器存放图片子文件夹
  *@param filename	 	上传文件名
  *@param input	上传文件输入流
  *@return 				上传结果
  */
 public static boolean uploadFile(String url, int port, String username,String password, String path, String filename, InputStream input) {
	 // filename:要上传的文件
	 // path :上传的路径
	 // 初始表示上传失败
	 boolean flag = false;
	 // 创建FTPClient对象
	 FTPClient ftp = new FTPClient();
	 try {
		 int reply;
		 // 连接FTP服务器
		 // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
		 System.out.println("------url:"+url+"-----port:"+port);
		 ftp.connect(url, port);
		 // 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
//		 ftp.setControlEncoding("GBK");
		 FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
		 conf.setServerLanguageCode("zh");
		 // 登录ftp
		 ftp.login(username, password);
		 // 看返回的值是不是230，如果是，表示登陆成功
		 reply = ftp.getReplyCode();
		 // 以2开头的返回值就会为真
		 if (!FTPReply.isPositiveCompletion(reply)) {
			 ftp.disconnect();
			 log.error("连接服务器失败");
			 return flag;
		 }
		 log.info("登陆FTP服务器 "+url+" 成功");
		 //ftp.changeWorkingDirectory(path);// 转移到FTP服务器目录

//		 FTPFile[] fs = ftp.listFiles(); // 得到目录的相应文件列表
		 log.info("得到目录的相应文件列表成功");
//		 String filename1 = SeforgeFtpUtils.changeName(filename, fs);   
		 String filename2 = new String(filename.getBytes("GBK"),"ISO-8859-1");
		 String path1 = new String(path.getBytes("GBK"), "ISO-8859-1");
		 // 转到指定上传目录
		 ftp.changeWorkingDirectory(path1);
		 ftp.enterLocalPassiveMode();
		 // 将上传文件存储到指定目录
		 // ftp.appendFile(new String(filename.getBytes("GBK"),"ISO-8859-1"),input);
		 ftp.setFileType(FTP.BINARY_FILE_TYPE); 
		 //如果缺省该句 传输txt正常 但图片和其他格式的文件传输出现乱码 
		 ftp.storeFile(filename2, input);
		 // 关闭输入流
		 input.close();
		 // 退出ftp
		 ftp.logout();
		 // 表示上传成功
		 flag = true;
		 System.out.println("文件 "+filename2+" 上传成功");
	  	} catch (IOException e) {
	  		e.printStackTrace();
	  		} finally {
	  			if (ftp.isConnected()) {
	  				try {
	  					ftp.disconnect();
	  					} catch (IOException ioe) {
	  						ioe.printStackTrace();
	  					}
	  			}
	  		}
	  		return flag;
 }
 /**
  * 删除文件方法
  *@param url 			FTP服务器IP
  *@param port 			FTP服务器端口号
  *@param username 		FTP用户名
  *@param password 		FTP用户密码
  *@param path 			FTP服务器存放图片子文件夹
  *@param filename	 	上传文件名
  *@return 				删除文件结果
  */
 public boolean deleteFile(String url, int port, String username,String password, String path, String filename) {
	 // filename:要上传的文件
	 // path :上传的路径
	 // 初始表示上传失败
	 boolean flag = false;
	 // 创建FTPClient对象
	 FTPClient ftp = new FTPClient();
	 try {
		 int reply;
		 // 连接FTP服务器
		 // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
		 ftp.connect(url, port);
		 // 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
		 ftp.setControlEncoding("GBK");
		 FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
		 conf.setServerLanguageCode("zh");
		 // 登录ftp
		 ftp.login(username, password);
		 // 看返回的值是不是230，如果是，表示登陆成功
		 reply = ftp.getReplyCode();
		 // 以2开头的返回值就会为真
		 if (!FTPReply.isPositiveCompletion(reply)) {
			 ftp.disconnect();
			 System.out.println("连接服务器失败");
			 return flag;
		 }
//		 System.out.println("登陆服务器成功");
		 String filename2 = new String(filename.getBytes("GBK"),"ISO-8859-1");
		 String path1 = new String(path.getBytes("GBK"), "ISO-8859-1");
		 // 转到指定上传目录
		 ftp.changeWorkingDirectory(path1);
		 ftp.enterLocalPassiveMode();
		 // 得到目录的相应文件列表
		 FTPFile[] fs = ftp.listFiles();
		 ftp.deleteFile(filename2);
		 ftp.logout();
		 flag=true;
	 } catch (IOException e) {
		 System.out.println(e);    
  		} finally {
  			if (ftp.isConnected()) {
  				try {
  					ftp.disconnect();
  				} catch (IOException ioe) {
  				}
  			}
  		}
  		return flag;
 }
 
 /**
  * 批量删除文件方法
  *@param url 			FTP服务器IP
  *@param port 			FTP服务器端口号
  *@param username 		FTP用户名
  *@param password 		FTP用户密码
  *@param path 			FTP服务器存放文件子文件夹
  *@param fileList	 	文件名集合
  *@return 				删除文件结果
  */
 public boolean deleteFiles(String url, int port, String username,String password, String path, List<String> fileList) {
	 // filename:要上传的文件
	 // path :上传的路径
	 // 初始表示上传失败
	 boolean flag = false;
	 // 创建FTPClient对象
	 FTPClient ftp = new FTPClient();
	 try {
		 int reply;
		 // 连接FTP服务器
		 // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
		 ftp.connect(url, port);
		 // 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
		 ftp.setControlEncoding("GBK");
		 FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
		 conf.setServerLanguageCode("zh");
		 // 登录ftp
		 ftp.login(username, password);
		 // 看返回的值是不是230，如果是，表示登陆成功
		 reply = ftp.getReplyCode();
		 // 以2开头的返回值就会为真
		 if (!FTPReply.isPositiveCompletion(reply)) {
			 ftp.disconnect();
			 System.out.println("连接服务器失败");
			 return flag;
		 }
		 String path1 = new String(path.getBytes("GBK"), "ISO-8859-1");
		 // 转到指定上传目录
		 ftp.changeWorkingDirectory(path1);
		 //循环待删除的文件列表将文件删除
		 for(int i=0;i<fileList.size();i++){
			 String filename=fileList.get(i);
			 String filename2 = new String(filename.getBytes("GBK"),"ISO-8859-1");
			 ftp.deleteFile(filename2);
		 }
		 ftp.logout();
		 flag=true;
	 } catch (IOException e) {
		 System.out.println(e);    
  		} finally {
  			if (ftp.isConnected()) {
  				try {
  					ftp.disconnect();
  				} catch (IOException ioe) {
  				}
  			}
  		}
  		return flag;
 }

 
 /**
  * 下载程序
  * 
  */
 public static boolean downFile(String ip, int port, String username,String password, String remotePath, String fileName,OutputStream outputStream, HttpServletResponse response) {
	System.out.println("---下载----remotePath:"+remotePath+"---fileName:"+fileName);
	boolean flag = false;
	FTPClient ftp = new FTPClient();
	try {
		int reply;
		ftp.connect(ip, port);
		// 下面三行代码必须要，而且不能改变编码格式
		ftp.setControlEncoding("GBK");
		FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
		conf.setServerLanguageCode("zh");
		// 如果采用默认端口，可以使用ftp.connect(url) 的方式直接连接FTP服务器
		ftp.login(username, password);// 登录
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			return flag;
		}
		System.out.println("登陆成功......");
		ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
		FTPFile[] fs = ftp.listFiles(); // 得到目录的相应文件列表
		for (int i = 0; i < fs.length; i++) {
			FTPFile ff = fs[i];
			if (ff.getName().equals(fileName)) {
				System.out.println("==========ff.getName():"+ff.getName());
				String filename = fileName;
				// 弹出下载对话框的代码
				response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(filename, "utf-8"));
				// 将文件保存到输出流outputStream中
				ftp.retrieveFile(new String(ff.getName().getBytes("GBK"),"ISO-8859-1"), outputStream);
				outputStream.flush();
				outputStream.close();
			}
		}
		ftp.logout();
		flag = true;
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (ftp.isConnected()) {
			try {
				ftp.disconnect();
			} catch (IOException ioe) {
			}
		}
	}
	return flag;
 }
 
 /**
  * 下载程序,不弹出下载对话框
  * 
  */
 public static boolean downFile(String ip, int port, String username,String password, String remotePath, String fileName,OutputStream outputStream) {
		boolean flag = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(ip, port);
			// 下面三行代码必须要，而且不能改变编码格式
			ftp.setControlEncoding("GBK");
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			// 如果采用默认端口，可以使用ftp.connect(url) 的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return flag;
			}
			System.out.println("登陆成功......");
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			ftp.enterLocalPassiveMode();
			FTPFile[] fs = ftp.listFiles(); // 得到目录的相应文件列表
			for (int i = 0; i < fs.length; i++) {
				FTPFile ff = fs[i];
				if (ff.getName().equals(fileName)) {
					String filename = fileName;
					// 将文件保存到输出流outputStream中
					ftp.retrieveFile(new String(ff.getName().getBytes("GBK"),"ISO-8859-1"), outputStream);
					outputStream.flush();
					outputStream.close();
				}
			}
			ftp.logout();
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return flag;
	 }

 // 判断是否有重名方法
 public static boolean isDirExist(String fileName, FTPFile[] fs) {
	 for (int i = 0; i < fs.length; i++) {
		 FTPFile ff = fs[i];
		 if (ff.getName().equals(fileName)) {
			// 如果存在返回 正确信号
			 return true; 
		 }
	 }
	// 如果不存在返回错误信号
	 return false; 
 }

 // 根据重名判断的结果 生成新的文件的名称
 public static String changeName(String filename, FTPFile[] fs){
	 int n = 0;
	 // 创建一个可变的字符串对象 即StringBuffer对象，把filename值付给该对象
	 StringBuffer filename1 = new StringBuffer("");
	 filename1 = filename1.append(filename);
	 System.out.println(filename1);
	 while (isDirExist(filename1.toString(), fs)) {
		 n++;
		 String a = "[" + n + "]";
		 System.out.println("字符串a的值是：" + a);
		// 最后一出现小数点的位置
		 int b = filename1.lastIndexOf(".");
		// 最后一次"["出现的位置
		 int c = filename1.lastIndexOf("[");
		 if (c < 0) {
			 c = b;
		 }
		// 文件的名字
		 StringBuffer name = new StringBuffer(filename1.substring(0, c));
		// 后缀的名称
		 StringBuffer suffix = new StringBuffer(filename1.substring(b + 1));
		 filename1 = name.append(a).append(".").append(suffix);
	 }
	 return filename1.toString();
 	}
}
