package com.shop.sellergoods.controller;

import com.shop.entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class UploadController {
	
	
	@RequestMapping("/uploadFile")
	public Result uploadFile(MultipartFile file){
		try {
			//设置虚拟的映射路径 ---> D:/file
			String path="D:\\ProgramDate\\Java\\tempData\\file";
			String url = "";
			if (file!=null && file.getSize()>0) {
				file.transferTo(new File(path, file.getOriginalFilename()));
				url = "http://localhost:8989/shopping-sellergoods/upload/"+file.getOriginalFilename();
			}	
			return new Result(true, url);
		} catch (IOException e) {
			e.printStackTrace();
			return new Result(false, "上传失败");
		}
		
	}
	
//	@Value("${FILE_SERVER_URL}")
//	private String file_server_url;
//	
//	@RequestMapping("/uploadFile")
//	public Result uploadFile(MultipartFile file){
//		
//		try {
//			// 获得文件名:
//			String fileName = file.getOriginalFilename();
//			// 获得文件的扩展名:
//			String extName = fileName.substring( fileName.lastIndexOf(".")+1 );
//			// 创建工具类
//			FastDFSClient client = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");
//			
//			String path = client.uploadFile(file.getBytes(), extName); // group1/M00/
//			
//			String url = file_server_url + path;
//			
//			return new Result(true, url);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new Result(false, "上传失败！");
//		}
//		
//	}
	
}
