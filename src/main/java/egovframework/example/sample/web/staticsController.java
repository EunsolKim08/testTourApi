package egovframework.example.sample.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.example.sample.service.impl.staticsMapper;

@Controller
public class staticsController {
	
	
	@Resource(name = "staticsMapper")
	staticsMapper staticsMapper;
	
	@RequestMapping(value = "/staticsNutrient.do")
	public ModelAndView staticsNutrient() {
		
		/*페이지 설정*/
		ModelAndView mv = new ModelAndView();
		mv.setViewName("test/staticsNutrient");
		
		return mv;
	}
	
	@RequestMapping(value = "/filterSearch.do",
			produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String filterSearch(String  groupCd, String nutrientCd, String searchWord) {
		String result="1";
		
		System.out.println("그룹코드: "+ groupCd+", 필수선택 코드1: "+nutrientCd +"필수선택 코드2: " +searchWord);
		
		CodeDTO cdto = new CodeDTO();
		CodeDTO rdto = new CodeDTO();
		
		cdto.setG_CD(groupCd);
		cdto.setCD(nutrientCd);
		
		rdto = staticsMapper.filtertCode(cdto);
		
		System.out.println(rdto.getCD_NAME());
		String castId = rdto.getCD_NAME();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("CAST_ID",castId);
		map.put("DESC_KOR", searchWord);
		
		ArrayList<Items> filterList = null;
		
		
		filterList= staticsMapper.filterData(map);
		JSONObject obj = new JSONObject(); 
		
		String convertResult="";		
		
		obj.put("items", filterList);
		System.out.println(obj);
		convertResult = obj.toString();
		System.out.println("convertResult확인: "+convertResult);
		
		System.out.println("실행");
		
		return obj.toJSONString();
	}
	
	@RequestMapping(value = "/filterChart.do",
			produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String filterChart(String  groupCd, String nutrientCd, String searchWord) {
		
		String result="1";
		String json="";
		String categories="";
		String nuDa2="[";
		String nuDa3="[";
		String nuDa4="[";
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		JSONArray jsonArr = new JSONArray();
		JSONParser jsonParser = new JSONParser();
		
		
		System.out.println("차트 그룹코드: "+ groupCd+", 필수선택 코드1: "+nutrientCd +"필수선택 코드2: " +searchWord);
		
		CodeDTO cdto = new CodeDTO();
		CodeDTO rdto = new CodeDTO();
		
		cdto.setG_CD(groupCd);
		cdto.setCD(nutrientCd);
		
		rdto = staticsMapper.filtertCode(cdto);
		
		System.out.println(rdto.getCD_NAME());
		String castId = rdto.getCD_NAME();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("CAST_ID",castId);
		map.put("DESC_KOR", searchWord);
		
		rdto = staticsMapper.filtertCode(cdto);
		
		ArrayList<Items> filterList = null;
		
		filterList= staticsMapper.filterData(map);
		
		 try { 
			 json = objectMapper.writeValueAsString(filterList);
			 System.out.println("char Json: "+ json); 
			 jsonArr = (JSONArray)jsonParser.parse(json);
				
				JSONObject jsonObj=null ;
				
				int cFlag = 0;
				
				if(jsonArr.size()>10) {
					cFlag = 10;
				}else {
					cFlag = jsonArr.size();
				}
				
				for(int i = 0; i<cFlag;i++) {
					jsonObj = (JSONObject)jsonArr.get(i);
					System.out.println((String)jsonObj.get("DESC_KOR"));
				
					String desc = (String)jsonObj.get("DESC_KOR");
					//categories += " \"\r" + desc +"\"\r" ;
					categories += (String)jsonObj.get("DESC_KOR");
					nuDa2 += jsonObj.get("NUTR_CONT2");
					nuDa3 += jsonObj.get("NUTR_CONT3");
					nuDa4 += jsonObj.get("NUTR_CONT4");
					//nuDa2 += (String)jsonObj.get("NUTR_CONT2");
					//nuDa3 += (String)jsonObj.get("NUTR_CONT3");
					//nuDa4 += (String)jsonObj.get("NUTR_CONT4");
					
					
					if(i < cFlag-1) {
						nuDa2+=",";
						nuDa3+=",";
						nuDa4+=",";
						categories +="/";
					}
				}
				categories +="";
				nuDa2 +="]";
				nuDa3 +="]";
				nuDa4 +="]";
		}
		 catch(Exception e) {
			 System.out.println("직렬화중 예외 발생"); 
		 e.printStackTrace(); }
		 
		 
		JSONObject obj = new JSONObject(); 
		obj.put("categories", categories);
		obj.put("nuDa2", nuDa2);
		obj.put("nuDa3", nuDa3);
		obj.put("nuDa4", nuDa4);
		result= obj.toJSONString();
		
		return result;
		
	}
	

}
