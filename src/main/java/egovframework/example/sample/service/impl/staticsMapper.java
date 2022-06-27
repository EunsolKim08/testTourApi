package egovframework.example.sample.service.impl;

import java.util.ArrayList;

import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

import egovframework.example.sample.web.Items;
import egovframework.example.sample.web.CodeDTO;

@Mapper("staticsMapper")
public interface staticsMapper {

	public ArrayList<Items> filtertData(CodeDTO cdto);
}
