package egovframework.example.sample.service.impl;

import java.util.ArrayList;
import java.util.HashMap;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.sample.web.Items;
import egovframework.example.sample.web.CodeDTO;

@Mapper("staticsMapper")
public interface staticsMapper {

	public CodeDTO filtertCode(CodeDTO cdto);
	
	public ArrayList<Items> filterData1 (HashMap<String, Object> map);
	public ArrayList<Items> filterData2 (HashMap<String, Object> map);
	public ArrayList<Items> filterData3 (HashMap<String, Object> map);
	public ArrayList<Items> filterData4 (HashMap<String, Object> map);
}
