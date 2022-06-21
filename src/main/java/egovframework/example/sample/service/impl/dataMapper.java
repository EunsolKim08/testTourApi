package egovframework.example.sample.service.impl;

import java.util.ArrayList;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.sample.web.Items;

@Mapper("dataMapper")
public interface dataMapper {
	
	public ArrayList<Items> selectData(Items items);

}
