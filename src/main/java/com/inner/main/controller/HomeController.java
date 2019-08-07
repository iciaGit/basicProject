package com.inner.main.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.inner.main.service.HomeService;
import com.inner.main.vo.MemberVO;

import egovframework.com.util.FileUtil;
import egovframework.com.util.ParameterUtil;
import egovframework.com.util.PropertiesUtil;


@Controller
public class HomeController {
	
	@Autowired HomeService service;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	String root = PropertiesUtil.getProperty("globals", "Globals.filePath");
	
	//	request 규칙
	//일반요청 : /*.do
	//ajax 요청 : /*.ajax
	//페이지 이동 요청 : /*.go  	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model) throws Exception{				
		logger.info("main page GO");
		return "home";
	}
	
	
	//리스트 불러오기
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest request, Model model) {				
		model.addAttribute("list", service.getMemberList());
		return "home";
	}	
	
	
	@RequestMapping(value = "/propRead.do", method = RequestMethod.GET)
	public String propRead(HttpServletRequest request, Model model) throws Exception{		
		String url = PropertiesUtil.getProperty("globals", "Globals.url");		//프로퍼티 파일 읽어오기
		logger.info("property - Globlas.url : "+url);
		return "home";
	}
	
	
	@RequestMapping(value = "/propWrite.do", method = RequestMethod.GET)
	public String propWrite(HttpServletRequest request, Model model) throws Exception{	
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("msg001", "message 0001");
		map.put("msg002", "message 0002");
		map.put("msg003", "message 0003");
		PropertiesUtil.setProperty("user",map);		//프로퍼티 파일 생성
		return "home";
	}
	
	
	@RequestMapping(value = "/insertVO.do", method=RequestMethod.POST)
	public String insertVO(HttpServletRequest request, MemberVO voParam ,Model model) throws Exception{	
		logger.info("VO 형태로 파라메터 받아 보기");
		ParameterUtil.getVoValues(voParam);
		return "home";
	}
	
	
	//ajax 로 데이터 받기
	@RequestMapping(value = "/list.ajax", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody HashMap<String,Object> listAjax(HttpServletRequest request) throws Exception{	
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", service.getMemberList());

		return map;
	}
	
	
	//json 형태로 파라메터 받기
	@RequestMapping(value = {"/ajaxInsert.ajax","/memberInsert.ajax"}, method = {RequestMethod.GET, RequestMethod.POST})	
	public @ResponseBody HashMap<String, Object> authorGrant(HttpServletRequest request, 
			@RequestBody HashMap<String, Object> param) throws Exception{				
		HashMap<String, Object> pmap = (HashMap<String, Object>) param.get("values");
		String name = (String) pmap.get("name");
		System.out.println("name : "+name);
		ArrayList<Integer> numList = (ArrayList<Integer>) pmap.get("num");
		for(int num: numList) {
			System.out.println("number : "+num);
		}				
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("msg", "success");
		return map;
	}
	
	
	//페이지 이동
	@RequestMapping(value= "/fileHandlerPage.go",method = RequestMethod.GET)
	public String fileHandlerPage(HttpServletRequest request, Model model) throws Exception {			
		System.out.println("파일 페이지로 이동");
		model.addAttribute("fileList", service.getFilList(root));
		return "fileHandlerPage";
	}		

	
	//파일 업로드
	@RequestMapping(value= {"/photoUpload.do","/fileUpload.do"},method = RequestMethod.POST)
	public String fileUpload(MultipartFile file, HttpServletRequest request) throws Exception {			
		HashMap<String, String> map = FileUtil.fileUpload(file, root);
		ParameterUtil.getMapValues(map);//map 안의 파라메터 값 확인		
		return "redirect:/fileHandlerPage.go";
	}
	
	
	//파일 다운로드
	@RequestMapping(value= {"/photoDownload.do","/fileDownload.do"}, method = {RequestMethod.GET, RequestMethod.POST})
	public void fileDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {					
		String fileName = request.getParameter("fileName");
		System.out.println("DOWN LOAD FILE : "+fileName);				
		FileUtil.fileDownload(fileName, "", root, response);
	}
	
	
	//파일 삭제
	@RequestMapping(value= {"/photoDelete.do","/fileDelete.do"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String fileDelete(HttpServletRequest request, HttpServletResponse response) throws Exception {					
		String fileName = request.getParameter("fileName");
		System.out.println("DOWN LOAD FILE : "+fileName);		
		FileUtil.fileDelete(root, fileName);
		return "redirect:/fileHandlerPage.go";
	}
	

	
}
