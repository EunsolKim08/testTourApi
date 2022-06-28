package egovframework.example.sample.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
		map.put("castId",castId);
		map.put("DESC_KOR", searchWord);
		
		ArrayList<Items> filterList = null;
		filterList = staticsMapper.filterData(map);
		
		System.out.println( "사이즈: "+filterList.size());
		
		JSONObject obj = new JSONObject(); 
		System.out.println(filterList); //값은 맞게옴.
		obj.put("items", filterList);
		System.out.println("실행");
		
		return obj.toString();
	}

}
