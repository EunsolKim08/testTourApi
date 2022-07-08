package egovframework.example.sample.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.sample.web.Items;
import egovframework.example.sample.web.NutrientDTO;

@Mapper("dataMapper")
public interface dataMapper {
	
	public ArrayList<Items> selectData(Items items);
	
	public void insertData(List<NutrientDTO> list);
	
	public void updateData(Items items);
	
	public ArrayList<Items> gridSortData (HashMap<String, Object> map);

}
