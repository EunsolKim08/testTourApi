package egovframework.example.sample.web;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;

import egovframework.example.sample.service.impl.dataMapper;
import egovframework.example.sample.service.impl.staticsMapper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

@Controller
public class testController {
	@Resource(name = "dataMapper")
	dataMapper dataMapper;
	
	@Resource(name = "staticsMapper")
	staticsMapper staticsMapper;
	
	@RequestMapping(value = "/testNutrient.do")
	public ModelAndView testNutrient() {
		
		/*페이지 설정*/
		ModelAndView mv = new ModelAndView();
		mv.setViewName("test/testNutrient");
		
		return mv;
	}
	
	@RequestMapping("/infoUrl.do")
	@ResponseBody
	public String searchTourInfo(String searchValue,  String companyName) {
	
		String info="";
		System.out.println(companyName);
		try {
			info = getInfoService(searchValue,companyName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return info;
	}
	
	@RequestMapping("/testcall.do")
	@ResponseBody
	public String testcall(String name, String ggg) {
		ObjectMapper mapper = new ObjectMapper();
		
		HashMap<String, String> hmap = new HashMap<String, String>();
		hmap.put("id", "edsk");
		hmap.put("company", "korea");
		
		String json = null;
		try {
			json = mapper.writeValueAsString(hmap);
		} catch (JsonProcessingException e) {
			System.out.println(">> JsonProcessingException" + e.getLocalizedMessage());
		}
		
		return json;
	}
	
	public String getInfoService(String searchValue, String company) throws Exception {
	  
	    StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Tqi6GXzISVFFUWsPcky9vgkUk4M2XhuAByFsXN5adVLBkRL8ZLTVI1qQ%2Bzo3PVJeCXI5%2FZfhvuEPEFYjH4F0mg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("desc_kor","UTF-8") + "=" + URLEncoder.encode(searchValue, "UTF-8")); /*식품이름*/
      //  urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
       // urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답데이터 형식(xml/json) Default: xml*/
         
        if(company != "") {
	    	urlBuilder.append("&" + URLEncoder.encode("animal_plant","UTF-8") + "=" + URLEncoder.encode(company, "UTF-8"));
	    }
        
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
        
        System.out.println("");
        String result = sb.toString();       
        
        return result;
    }
	
	/*java로 json Parsing 도전해보기*/
	@RequestMapping("/jsonParsing.do")
	@ResponseBody
	public String jsonParsing(String searchValue, String companyName) throws UnsupportedEncodingException, IOException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(companyName);
		String result="1";
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1"); /*URL*/
	    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Tqi6GXzISVFFUWsPcky9vgkUk4M2XhuAByFsXN5adVLBkRL8ZLTVI1qQ%2Bzo3PVJeCXI5%2FZfhvuEPEFYjH4F0mg%3D%3D"); /*Service Key*/
	    urlBuilder.append("&" + URLEncoder.encode("desc_kor","UTF-8") + "=" + URLEncoder.encode(searchValue, "UTF-8")); /*식품이름*/
	    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	    // urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
	    urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답데이터 형식(xml/json) Default: xml*/
	         
	    if(companyName != "") {
	    	urlBuilder.append("&" + URLEncoder.encode("animal_plant","UTF-8") + "=" + URLEncoder.encode(companyName, "UTF-8"));
	    }
	     URL url = new URL(urlBuilder.toString());
	     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	     conn.setRequestMethod("GET");
	     conn.setRequestProperty("Content-type", "application/json");
	     System.out.println("Response code: " + conn.getResponseCode());
	     BufferedReader rd;
	      if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	          rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      } else {
	          rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	      }
	     StringBuilder sb = new StringBuilder();
	      String line;
	      while ((line = rd.readLine()) != null) {
	          sb.append(line);
	      }
		result = sb.toString();
		
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
		JSONObject body = (JSONObject)jsonObject.get("body");
		JSONArray items = (JSONArray)body.get("items");
		JSONObject jsonObj=null ;

		for(int i = 0; i<items.size();i++) {
			jsonObj = (JSONObject)items.get(i);
			System.out.println((String)jsonObj.get("DESC_KOR"));
		}
		//그냥 jsonArray값인 return itmes라고하면 error가 뜸과함께 정상적인 items의 값이 return되지 않는다.
		return items.toJSONString();
	}
	
	
	@RequestMapping("/testGrid.do")
	@ResponseBody
	public ModelAndView testGrid() {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("test/testGrid");
		
		return mv;
	}

	@RequestMapping("/jsonParsing2.do")
	@ResponseBody
	public String jsonParsing2() throws UnsupportedEncodingException, IOException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		String result="1";
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1"); /*URL*/
	    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Tqi6GXzISVFFUWsPcky9vgkUk4M2XhuAByFsXN5adVLBkRL8ZLTVI1qQ%2Bzo3PVJeCXI5%2FZfhvuEPEFYjH4F0mg%3D%3D"); /*Service Key*/
	    urlBuilder.append("&" + URLEncoder.encode("desc_kor","UTF-8") + "=" + URLEncoder.encode("초콜릿", "UTF-8")); /*식품이름*/
	    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	    // urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
	    urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답데이터 형식(xml/json) Default: xml*/
	    
	    
	     URL url = new URL(urlBuilder.toString());
	     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	     conn.setRequestMethod("GET");
	     conn.setRequestProperty("Content-type", "application/json");
	     System.out.println("Response code: " + conn.getResponseCode());
	     BufferedReader rd;
	      if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	          rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      } else {
	          rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	      }
	      StringBuilder sb = new StringBuilder();
	  	System.out.println("1");
	      String line;
	      while ((line = rd.readLine()) != null) {
	          sb.append(line);
	      }
		System.out.println("2");
		result = sb.toString();
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
		JSONObject body = (JSONObject)jsonObject.get("body");
		JSONArray items = (JSONArray)body.get("items");
		JSONObject jsonObj=null ;
		
		for(int i = 0; i<items.size();i++) {
			jsonObj = (JSONObject)items.get(i);
			System.out.println((String)jsonObj.get("DESC_KOR"));
		}
		//그냥 jsonArray값인 return itmes라고하면 error가 뜸과함께 정상적인 items의 값이 return되지 않는다.
		return items.toJSONString();
	}
	
	/*
	 	jsonParsing 방법3. jackson과 json-data bind를이용한 object-mapper방법. 
	 */
	@RequestMapping("/mapJsonParsing3.do")
	@ResponseBody
	public String mapJsonParsing3(String searchValue, String companyName) throws IOException {
		
		String result="";
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1"); /*URL*/
	    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Tqi6GXzISVFFUWsPcky9vgkUk4M2XhuAByFsXN5adVLBkRL8ZLTVI1qQ%2Bzo3PVJeCXI5%2FZfhvuEPEFYjH4F0mg%3D%3D"); /*Service Key*/
	    urlBuilder.append("&" + URLEncoder.encode("desc_kor","UTF-8") + "=" + URLEncoder.encode(searchValue, "UTF-8")); /*식품이름*/
	    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
	    // urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
	    urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답데이터 형식(xml/json) Default: xml*/
	    
	    if(companyName != "") {
	    	urlBuilder.append("&" + URLEncoder.encode("animal_plant","UTF-8") + "=" + URLEncoder.encode(companyName, "UTF-8"));
	    }
	     
	    URL url = new URL(urlBuilder.toString());
	     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	     conn.setRequestMethod("GET");
	     conn.setRequestProperty("Content-type", "application/json");
	     System.out.println("Response code: " + conn.getResponseCode());
	     BufferedReader rd;
	      if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	          rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      } else {
	          rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	      }
	     StringBuilder sb = new StringBuilder();
	     String line;
	      while ((line = rd.readLine()) != null) {
	          sb.append(line);
	      }
	    System.out.println("sb: "+ sb); // 이거 자체가 파싱하는 객체인듯..
		result = sb.toString();	
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		 System.out.println("3");
		NutrientDTO nutrientDto = null;
	
		String fResult="";
		
		try {
			System.out.println("result: "+ result);
			NutrientDTO deserializeNu = objectMapper.readValue(result, NutrientDTO.class);
			
			fResult = mapper.writeValueAsString(deserializeNu);
			System.out.println("fResult: "+ fResult);
			NutrientDTO innerClassPersonDto = objectMapper.readValue(result, NutrientDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	
		
		return fResult;
	 
	}
	@RequestMapping("/dataSearch.do")
	@ResponseBody
	public String dataSearch(String searchValue, String companyName) throws IOException {
		String result="1";
		String concat="";
		int insertFlag=0;
		String fResult="1";
		
		ArrayList<Items> selectItem = null;
		List<Items> list;
		Items items = new Items();
		
		items.setDESC_KOR(searchValue);
		System.out.println(items.getDESC_KOR());
		
		selectItem = dataMapper.selectData(items);

		if(selectItem.size()<1) {
			insertFlag = 0;
			
			ObjectMapper mapper = new ObjectMapper();
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1"); /*URL*/
		    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Tqi6GXzISVFFUWsPcky9vgkUk4M2XhuAByFsXN5adVLBkRL8ZLTVI1qQ%2Bzo3PVJeCXI5%2FZfhvuEPEFYjH4F0mg%3D%3D"); /*Service Key*/
		    urlBuilder.append("&" + URLEncoder.encode("desc_kor","UTF-8") + "=" + URLEncoder.encode(searchValue, "UTF-8")); /*식품이름*/
		  //  urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
		    urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*한 페이지 결과 수*/
		    urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*응답데이터 형식(xml/json) Default: xml*/
		    
		    if(companyName != "") {
		    	urlBuilder.append("&" + URLEncoder.encode("animal_plant","UTF-8") + "=" + URLEncoder.encode(companyName, "UTF-8"));
		    }
		     
		    URL url = new URL(urlBuilder.toString());
		     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		     conn.setRequestMethod("GET");
		     conn.setRequestProperty("Content-type", "application/json");
		     System.out.println("Response code: " + conn.getResponseCode());
		     BufferedReader rd;
		      if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
		          rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		      } else {
		          rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		      }
		     StringBuilder sb = new StringBuilder();
		     String line;
		      while ((line = rd.readLine()) != null) {
		          sb.append(line);
		      }
			result = sb.toString();	
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			NutrientDTO nutrientDto = null;
			List<NutrientDTO> insertList =  new ArrayList<>();
			
			try {
				NutrientDTO deserializeNu = objectMapper.readValue(result, NutrientDTO.class);
				insertList= (List<NutrientDTO>) deserializeNu.getBody().getItems();
				fResult = mapper.writeValueAsString(deserializeNu);
				System.out.println("조회 x시 fResult: "+ fResult);
				if(insertFlag == 0) {
					dataMapper.insertData(insertList);
				}
			
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			JSONObject obj = new JSONObject();    
			selectItem = dataMapper.selectData(items);
			obj.put("items", selectItem);
			
			fResult= obj.toJSONString();
		}else {
			System.out.println("데이터 조회결과 O.");
			insertFlag = 1;
			JSONObject obj = new JSONObject();                
			obj.put("items", selectItem);
			System.out.println("데이터 셀렉트: "+selectItem);
			fResult= obj.toJSONString();
		}
		
		return fResult;
	}
	
	@RequestMapping("/getChartData.do")
	@ResponseBody
	public String getChartData(String searchValue, String companyName) throws IOException{
		
		String json="";
		String categories="";
		String nuDa2="[";
		String nuDa3="[";
		String nuDa4="[";
		
		ArrayList<Items> selectItem = null;
		Items items = new Items();
		
		items.setDESC_KOR(searchValue);
		System.out.println(items.getDESC_KOR());
		
		selectItem = dataMapper.selectData(items);
		System.out.println("조회완료");
		
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		//map = dataMapper.selectData(items);
			
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		
		JSONArray jsonArr = new JSONArray();
		JSONParser jsonParser = new JSONParser();
	
		try {
	
			json = objectMapper.writeValueAsString(selectItem);
			
			System.out.println("json: "+json);
			
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
			
				String desc = (String)jsonObj.get("DESC_KOR");
				
				categories += (String)jsonObj.get("DESC_KOR");
				nuDa2 += jsonObj.get("NUTR_CONT2");
				nuDa3 += jsonObj.get("NUTR_CONT3");
				nuDa4 += jsonObj.get("NUTR_CONT4");				
				
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
			
		}catch(Exception e) {
			System.out.println("ArrayList직력화중 에러 발생");
		}
		
		JSONObject obj = new JSONObject(); 
		
		obj.put("categories", categories);
		obj.put("nuDa2", nuDa2);
		obj.put("nuDa3", nuDa3);
		obj.put("nuDa4", nuDa4);
		String result="";
		result= obj.toJSONString();
		
		return result;
	}
	

	@RequestMapping(value = "/dataEdit.do",
	produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String dataEdit(@RequestBody String jsonData) throws JsonMappingException, JsonProcessingException  {
		
		System.out.println("데이터 수정 중");
		
		String result="1";
		String json="";
		String decodeVal = "{\"items\":\r\n";
		
		try {
			decodeVal = URLDecoder.decode(jsonData, "utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("디코딩중 에러발생");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		decodeVal = decodeVal.replace("jsonData=", "");
		decodeVal = decodeVal.replace("}],", "}],");
	
		JSONParser parser = new JSONParser();
		Object obj = null;
		
		try {
			obj = parser.parse( decodeVal );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("parser.pare 실행중 오류");
			e.printStackTrace();
		}
		
		System.out.println("****"+obj.toString());
		//System.out.println("3");
		JSONObject jsonObj = (JSONObject) obj;
		JSONArray jarray = new JSONArray();
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		String searchVal = jsonObj.get("param").toString();
		String sortFlag = jsonObj.get("sortFlag").toString();

		List<Map<String, Object>> bodyList =  new ArrayList<>();
	
		
		try {
			Body desi = objectMapper.readValue(decodeVal, Body.class);
			
			bodyList = (List<Map<String, Object>>)desi.getItems();
			
			for(int i = 0; i<bodyList.size();i++) {
				LinkedHashMap<String, Object> lmap = (LinkedHashMap<String, Object>) bodyList.get(i);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("아이템 직렬화로 읽기 중 오류");
		}
		
		JSONArray items = (JSONArray)jsonObj.get("items");
		//System.out.println("jsonArray: "+items);
		JSONObject jsonObj2=null ;
		for(int i = 0; i<items.size();i++) {
			jsonObj2 = (JSONObject)items.get(i);
			long index2 = (long) jsonObj2.get("IDX_NU");
			int index3 =(int)index2;
		
			
			Items items2 = new Items();
	
			double nutr1 = Double.parseDouble((String)jsonObj2.get("NUTR_CONT1"));
		
			String nutr2 = String.valueOf(jsonObj2.get("NUTR_CONT2"));
			String nutr3 = String.valueOf(jsonObj2.get("NUTR_CONT3"));
			String nutr4 = String.valueOf(jsonObj2.get("NUTR_CONT4"));
			
			items2.setIDX_NU(index3);
			items2.setNUTR_CONT1(nutr1);
			items2.setNUTR_CONT2(Double.parseDouble(nutr2));
			items2.setNUTR_CONT3(Double.parseDouble(nutr3));
			items2.setNUTR_CONT4(Double.parseDouble(nutr4));
			items2.setSERVING_WT((String)jsonObj2.get("SERVING_WT"));
			
			dataMapper.updateData(items2);
		}
		Items items1 = new Items();
		items1.setDESC_KOR(searchVal);
		System.out.println("수정완료");
		ArrayList<Items> selectItem =null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(sortFlag.equals("DESC") || sortFlag.equals("ASC")) {
			map.put("DESC_KOR", searchVal);
			map.put("SORT_FLAG", sortFlag);
			selectItem = staticsMapper.filterSortData(map);
		}
		else {
			selectItem =  dataMapper.selectData(items1);
		}
		
		JSONObject editObj = new JSONObject();                
		editObj.put("items", selectItem);
		result = editObj.toJSONString();
		
		return result;
	}
	
	
	@RequestMapping("/gridUpdateSort.do")
	@ResponseBody
	public String gridUpdateSort(String nutrientCd, String searchWord, String sortFlag) throws JsonMappingException, JsonProcessingException  {
		
		String result="0";
	
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		CodeDTO cdto = new CodeDTO();
		CodeDTO rdto = new CodeDTO();
		
		System.out.println("sortFlag: "+ sortFlag);
		rdto = staticsMapper.filterCode(cdto);
		
		String castId = nutrientCd;
	    map.put("CAST_ID",castId);
	    map.put("DESC_KOR", searchWord);
	    map.put("SORT_FLAG", sortFlag);
		
		/* @cnt = 0 */
		staticsMapper.setCntZero();
		/* 기준에 따라 SORT_NU 부여*/
		staticsMapper.updateSort(map);
		
		result= "1";
		
		ArrayList<Items> filterList = null;
		
		
		filterList= staticsMapper.filterSortData(map);
		System.out.println("SELECT 매퍼 실행");
		JSONObject obj = new JSONObject(); 
	
		obj.put("items", filterList);
		
	
		result = obj.toString();
	
		return obj.toJSONString();	
	}
	
	/*원본 파일이 생성되는 폴더 경로*/
	public static String filePath = "C:\\poi_temp";
	/*원본 파일 이름*/
	public static String fileNm = "poiData.xlsx";
	
	
	/*파일 저장 랜덤 이름을 만든다.*/
	public String randomNm() {
		String randomName = "";
		
		randomName = UUID.randomUUID().toString()+".xlsx";;
		
		return randomName;
	}
	
	@RequestMapping(value = "/gridExcelDownload.do",
			produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String gridExcelDownload(@RequestBody String dataObjStr, HttpServletRequest request,HttpServletResponse response, ModelMap model) throws Exception {
		String result = "0";
		
		/*poi 엑셀 만들기 api가 호출될때 마다 고유이름 생성*/
		fileNm = randomNm();
		makeGridExcel(dataObjStr);
		
		JSONObject obj = new JSONObject();
		obj.put("result", 200);
		obj.put("fileNm", fileNm);
		
		
		result = obj.toJSONString();
		return result;
	}
	
	public void makeGridExcel(String dataObjStr) {	
	String decodeVal="";
		
		try {
			decodeVal += URLDecoder.decode(dataObjStr, "utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("디코딩중 에러발생");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		decodeVal = decodeVal.replace("jsonData=", "");
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		List<Map<String, Object>> bodyList =  new ArrayList<>();
		
		try {
			Body desi = objectMapper.readValue(decodeVal, Body.class);
			
			bodyList = (List<Map<String, Object>>)desi.getItems();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("아이템 직렬화로 읽기 중 오류");
		}
		
		// 빈 Workbook 생성
        XSSFWorkbook workbook = new XSSFWorkbook();

        // 빈 Sheet를 생성
        XSSFSheet sheet = workbook.createSheet("Nutrient data");
        XSSFCellStyle xstyle = workbook.createCellStyle();

        // Sheet를 채우기 위한 데이터들을 Map에 저장
        Map<String, Object[]> data = new HashMap<>();
        data.put("0", new Object[]{"식품이름","1회 제공량(g)", "칼로리(kcal)", "탄수화물(g)","단백질(g)","지방(g)"});
        
        for(int i = 0; i<bodyList.size();i++) {
			LinkedHashMap<String, Object> lmap = (LinkedHashMap<String, Object>) bodyList.get(i);
			String num="";
			num = String.valueOf(i+1);
			
			String DESC_KOR =  String.valueOf(lmap.get("DESC_KOR"));
			String SERVING_WT =  String.valueOf(lmap.get("SERVING_WT"));
			String NUTR_CONT1 =  String.valueOf(lmap.get("NUTR_CONT1"));
			String NUTR_CONT2 =  String.valueOf(lmap.get("NUTR_CONT2"));
			String NUTR_CONT3 =  String.valueOf(lmap.get("NUTR_CONT3"));
			String NUTR_CONT4 =  String.valueOf(lmap.get("NUTR_CONT4"));
			
			//num을 키값으로 Object 배열을 집어넣는다.
			data.put(num, new Object[]{DESC_KOR,SERVING_WT, NUTR_CONT1, NUTR_CONT2, NUTR_CONT3, NUTR_CONT4});
		}
    
      
        int rownum = 0;

        //cell의 0번줄을 headr로.. 다른부분은 data값 가져옴.
       for(int i = 0; i<= bodyList.size(); i++) {
    	   String num =String.valueOf(i);
    	   Row row = sheet.createRow(i);
    	   
    	   for(int j = 0; j<6;j++) {
    		   Cell cell = row.createCell(j);
    		   cell.setCellValue((String)data.get(num)[j]);
    	   }
       }
        
        try {
            FileOutputStream out = new FileOutputStream(new File(filePath, fileNm));
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	@RequestMapping(value = "/downloadFile.do")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, ModelMap model)  throws Exception{
		File uFile = new File(filePath,fileNm);
		
		int fSize = (int)uFile.length();
		
		if(fSize > 0) {
			 try {
		        	String path = filePath+ "\\"+fileNm; // 경로에 접근할 때 역슬래시('\') 사용
		        	// C:\\poi_temp
		        	File file = new File(path);
		        	response.setHeader("Content-Disposition", "attachment;filename=" + file.getName()); // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더
		        	FileInputStream fileInputStream = new FileInputStream(path); // 파일 읽어오기 
		        	OutputStream out = response.getOutputStream();
		        	
		        	int read = 0;
		                byte[] buffer = new byte[1024];
		                while ((read = fileInputStream.read(buffer)) != -1) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을 파일이 없음
		                    out.write(buffer, 0, read);
		                }
		            out.close();
		        } catch (Exception e) {
		            throw new Exception("다운로드 실행 중 에러");
		        }
		}
		
	}
	/*파일을 업로드하는 path지정*/
	private static final String fileUploadPath="c:/jsonFile/";
	@RequestMapping(value = "/uploadFile.do")
	@ResponseBody
	public String uploadFile(MultipartHttpServletRequest mtfRequest) {	
		String result="0";
		int uploadFlag = 0;
	
		List<MultipartFile> fileList = mtfRequest.getFiles("file");
	
		String safeFile="";
		String ranFilename= "";
		int i=1;
		JSONObject obj = new JSONObject();
		boolean flag=false;
		/*파일 서버에 올리기*/
		for (MultipartFile mf : fileList)
		{
			String originFileName = mf.getOriginalFilename(); //원본 파일 명 
		 	long fileSize = mf.getSize(); // 파일 사이즈
		 	
			 ranFilename=  System.currentTimeMillis() + originFileName;
			 safeFile =fileUploadPath + ranFilename;
			 List<NutrientDTO> insertList = new ArrayList<NutrientDTO>();
			 try {
				 
	             mf.transferTo(new File(safeFile));
	    		 JSONObject  readFile = readJsonFile(ranFilename);
	    		 
	    		 flag = isJson(readFile.toString());
	    		 
	    		 if(flag== false) {
	    			 break;
	    		 }
	    	//	 System.out.println("readFile: "+readFile);
	             obj.put("items"+i, readFile.get("items"));
	             insertList = (List<NutrientDTO>)readFile.get("items");
	             dataMapper.insertData(insertList);
	         	 i++;
	       
	         } catch (IllegalStateException e) {
	        	 System.out.println("파일업로드 오류1");
	             // TODO Auto-generated catch block
	             e.printStackTrace();
	         } catch (IOException e) {
	             // TODO Auto-generated catch block
	        	 System.out.println("파일업로드 오류2");
	             e.printStackTrace();
	         }
		  }			 
		
		if(flag != false) {
			result= obj.toJSONString();
		}else {
			result="UploadFalse";
		}
		
		
		return result;
	}
	
	
	public JSONObject readJsonFile(String fileName) {
		JSONParser parser = new JSONParser();
		String result="";
		JSONObject jsonObject=null;

		try {
			FileReader reader = new FileReader("c:/jsonFile/"+ fileName);
			Object obj = parser.parse(reader);
			jsonObject = (JSONObject) obj;
			
			reader.close();
			
			result=jsonObject.toString();
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
		return jsonObject;
		
	}
	
	@RequestMapping(value = "/multipartUpload.do")
	@ResponseBody
	public String multipartUpload(MultipartHttpServletRequest request ) throws Exception {
	    System.out.println("dddd");
	 //   MultipartHttpServletRequest request = null;
	    List<MultipartFile> fileList = request.getFiles("file2");
	    System.out.println("dd222d");
	    System.out.println("파일리스트: "+fileList);
	   // String path = application.getRealPath("[경로]");
	    
	    File fileDir = new File(fileUploadPath);
	    
	    if (!fileDir.exists()) {
	    	fileDir.mkdirs();
	    }
	    
	    long time = System.currentTimeMillis();
	    
	    for (MultipartFile mf : fileList) {
	    	System.out.println("3333");
	    	String originFileName = mf.getOriginalFilename(); // 원본 파일 명
	        String saveFileName = String.format("%d_%s", time, originFileName);
	        System.out.println(originFileName);
	        try {
	        	// 파일생성
	            mf.transferTo(new File(fileUploadPath, saveFileName));    		
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	   }
		return "200";
	}
	
	public boolean isJson(String json) {
		boolean result = false;
		String exp ="{\r\n"
				+ "  \"param\": \"쿠키\",\r\n"
				+ "  \"items\": [\r\n"
				+ "    {\r\n"
				+ "      \"IDX_NU\": 2341,\r\n"
				+ "      \"NUTR_CONT4\": 32.1,\r\n"
				+ "      \"ANIMAL_PLANT\": \"\",\r\n"
				+ "      \"SORT_NU\": 76,\r\n"
				+ "      \"SERVING_WT\": \"100\",\r\n"
				+ "      \"NUTR_CONT1\": \"548\",\r\n"
				+ "      \"NUTR_CONT2\": 55.8,\r\n"
				+ "      \"NUTR_CONT3\": 7.8,\r\n"
				+ "      \"sort\": 0,\r\n"
				+ "      \"DESC_KOR\": \"쿠키,다크초콜릿마카다미아\",\r\n"
				+ "      \"_attributes\": {\r\n"
				+ "        \"checkDisabled\": false,\r\n"
				+ "        \"rowNum\": 1,\r\n"
				+ "        \"checked\": false,\r\n"
				+ "        \"disabled\": false,\r\n"
				+ "        \"className\": {\r\n"
				+ "          \"column\": {},\r\n"
				+ "          \"row\": []\r\n"
				+ "        }\r\n"
				+ "      },\r\n"
				+ "      \"rowKey\": 0\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"sortFlag\": \"DESC\"\r\n"
				+ "}}";
		
		JSONParser jsonParser = new JSONParser();
		
		try {
			//JSONObject jsonObject = (JSONObject)jsonParser.parse(json);
			JSONObject jsonObject = (JSONObject)jsonParser.parse(json);
			result = true;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("json형식 확인 중 오류발생");
		}
		//JSONObject jsonObject = (JSONObject)jsonParser.parse(json);
		
		return result;
	}
	
	@RequestMapping(value = "/getSaveData.do")
	@ResponseBody
	public String getSaveData(@RequestBody String jsonData) {
		String result="1";
		JSONParser jsonParser = new JSONParser();
		try {
			/*json Data 가공*/
			jsonData = URLDecoder.decode(jsonData, "utf-8");
			//jsonData = jsonData.replace("jsonData=", "");
			
			JSONObject jsonObject = (JSONObject)jsonParser.parse(jsonData);
			JSONArray deletedRows = (JSONArray)jsonObject.get("deletedRows");
			JSONArray createdRows = (JSONArray)jsonObject.get("createdRows");
			
			JSONObject jsonObj=null ;
			Items items = new Items();
			
			if(deletedRows.size() >0) {
				for(int i = 0; i<deletedRows.size() ;i ++) {
					jsonObj = (JSONObject)deletedRows.get(i);
					items.setIDX_NU(Integer.parseInt(jsonObj.get("IDX_NU").toString()));
					dataMapper.deleteData(items);
					
				}
			}
			
			if(createdRows.size() > 0) {
				/*삽입 메서드 실행*/
				List<NutrientDTO> insertList =  new ArrayList<>();
				insertList = (List<NutrientDTO>)createdRows;
				dataMapper.insertData(insertList);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value = "/getSaveData2.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getSaveData2(@RequestBody String obj) {
		
		String result="1";
	
		System.out.println("실행222: "+ obj);
		try {
			obj = URLDecoder.decode(obj, "utf-8");
			System.out.println(obj);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	@RequestMapping(value = "/getSaveData3.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getSaveData3(@RequestBody Product test) {
		
		String result="1";
	
		System.out.println("실행333: "+ test);
	
		
		return result;
	}
	
}
