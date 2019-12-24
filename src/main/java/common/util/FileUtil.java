package common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	
	
	/**
	 * Java 1.8 환경에서 작동
	 * java.nio 를 지원 하지 않는 Java 1.7 미만 버전에서는 Stream 처리로 변경 해서 사용 해야 함
	 * */	
	public static HashMap<String,String> fileUpload(MultipartFile file, String root) {				

		HashMap<String, String> map = new HashMap<String, String>();	
		try {
			makeDir(root);
			String oriFileName = file.getOriginalFilename();						
			String ext = oriFileName.substring(oriFileName.lastIndexOf(".")+1);
			String newFileName = System.currentTimeMillis()+"."+ext;
			map.put("newFileName",newFileName);						
			Path filePath = Paths.get(root+"/"+newFileName);
			byte[] bytes =  file.getBytes();
			//6. 파일 정보 저장			
			if(bytes.length>0) {		
				map.put("oriFileName", oriFileName);
				map.put("ext", ext);
				map.put("size", String.valueOf(bytes.length));
				//7. 파일 저장
				Files.write(filePath, bytes);

			}			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return map;		
	}
	
	
	/**
	 *  파일 다운로드
	 * @param transName - 변경된 파일명
	 * @param oriName - 원래 파일명
	 * @param root - 경로
	 * @param resp
	 */
	public static void fileDownload(String transName, String oriName, String root, HttpServletResponse resp) {	
		
		try {
			//파일을 특정 경로에서 가져온다.
			Path path = Paths.get(root+"/"+transName);
			byte[] bytes = Files.readAllBytes(path);

			//파일명 한글깨짐 방지
			if(oriName.equals("")||oriName==null) {
				oriName = transName;
			}

			if(oriName.contains("/")) {
				String[] depth = oriName.split("/");
				oriName = depth[depth.length-1];
			}
			
			String downFile = StringUtil.encodeMsg(oriName);
			System.out.println("file name : "+downFile);
			resp.setContentType("application/octet-stream");
			resp.setHeader("content-Disposition","attachment; filename=\""+downFile+"\"");
		
			//response write
			BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());			
			bos.write(bytes);			
			bos.flush();
			bos.close();				
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
	
	/**
	 * 파일 스트리밍
	 * @param root
	 * @param fileName
	 * @param req 
	 * @param response
	 * @throws IOException 
	 */
	public static void fileStreaming(String root, String fileName, HttpServletRequest req, HttpServletResponse resp) {

		//파일의 특정 위치부터 읽어들일 수 있도록 하기 위해 RandomAccessFile 클래스 가용
		RandomAccessFile rFile = null;
		BufferedOutputStream bos = null;
		try {
			rFile = new RandomAccessFile(root+"/"+fileName, "r");
		
		
		//브라우저에 따라 range 형식이 다른데, 기본 형식은 "bytes= {start}-{end}" 형식이다.
		//range가 null이거나, reqStart가 0이고 end가 없을 경우 전체 요청이다.
		String range = req.getHeader("range");		
		System.out.println("range : "+range);
		long fileSize = rFile.length();//전송 파일의 전체 사이즈
		long start = 0;
		long end = fileSize-1;			
		
		//전송 시작과 종료 값을 만든다.
		if(range != null) {
			String[] parts = range.replace("bytes=","").split("-");				
			start = Long.parseLong(parts[0]);
			if(parts.length>1) {
				end = Long.parseLong(parts[1]);
			}			
		}					
		
		long partSize = (end-start)+1;//전송하려는 사이즈	
				
        /*
        'Content-Range': 'bytes 0-486003670/486003671',//시작-끝/전체크기
        'Accept-Ranges': 'bytes',
        'Content-Length': 483251159,//전송하려는 전체 사이즈
        'Content-Type': 'video/mp4',
        */
		System.out.println("'Content-Type': 'video/mp4'");
		System.out.println("'Content-Range': 'bytes "+start+"-"+end+"/"+fileSize+"'");
		System.out.println("'Accept-Ranges': 'bytes'");
		System.out.println("'Content-Length': "+partSize);
		
		resp.reset();//전송 시작
		resp.setStatus(206);//206 부분 전송, 200  전송 완료
		resp.setContentType("video/mp4");
		resp.setHeader("Content-Range",  "bytes "+start+"-"+end+"/"+fileSize);
		resp.setHeader("Accept-Ranges",  "bytes");
		resp.setHeader("Content-Length", ""+partSize);		

			
		bos = new BufferedOutputStream(resp.getOutputStream());			
		byte[] buff = new byte[1024];	
		/*특정 위치에서 부터 전송 시작*/
		rFile.seek(start);				
		while(partSize>0) {
			int block = buff.length < partSize ? buff.length : (int)partSize;
			int len = rFile.read(buff, 0, block);			
			bos.write(buff,0,len);
			partSize -= block;
			System.out.println("remain size: "+partSize+", pointer: "+rFile.getFilePointer());
		}	
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("자원 반납");
			try {
				bos.flush();
				bos.close();
				rFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		

	}
	
	/*파일 삭제*/
	public static String fileDelete(String root, String delFile) {		
		File file = new File(root+"/"+delFile);
		String msg="이미 삭제된 파일 입니다.";
		if(file.exists()) {//삭제할 파일이 존재 한다면
			file.delete();//파일 삭제
			msg = "파일삭제 성공";
		}		
		return msg;
	}
	
	

	/*폴더 생성*/
	private static void makeDir(String path) {
		System.out.println(path);
		File dir = new File(path);
		if(!dir.isDirectory()){
			System.out.println("폴더 없음! 생성 시작");
			dir.mkdirs();
		}		
	}


	/*특정 공간의 파일 리스트를 가져 온다.*/
	public static ArrayList<String> getFileList(String root) {
		ArrayList<String> fileList = new ArrayList<String>();
		Path path = Paths.get(root);
		DirectoryStream<Path> list = null;
		try {
			list = Files.newDirectoryStream(path);
			for(Path file : list) {
				fileList.add(file.getFileName().toString());
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return fileList;		
	}




	
	
}
