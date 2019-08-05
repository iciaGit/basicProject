package com.inner.main.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.inner.main.dao.MemberInter;
import com.inner.main.vo.MemberVO;

import common.util.file.FileUtil;


@Service
public class HomeService {
	
	@Autowired MemberInter memberInter;

	/*
	READ UNCOMMITTED 다른 트랜잭션에서 COMMIT되지 않은 데이터들도 읽어올 수 있는 level
	READ COMMITTED 다른 트랜잭션에서 COMMIT 된 데이터만 읽어올 수 있는 level	
	REPEATABLE READ 조회중인 데이터를 다른 트랜잭션에서 Update 하지 못하게 막아주는 level
	SERIALIZABLE 한 트랜잭션에서 SELECT 쿼리를 실행하면 그 트랜잭션이  COMMIT 되기 전까지 다른 트랜잭션에서는 CRUD 작업 불가 
	*/
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public ArrayList<MemberVO> getMemberList() {
		return memberInter.getMemberList();		
	}

	//특정 폴더의 파일을 모두 가져 온다.
	public ArrayList<String> getFilList(String root) {		
		return FileUtil.getFileList(root);
	}

}
