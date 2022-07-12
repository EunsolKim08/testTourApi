package egovframework.example.sample.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.example.sample.service.impl.dataMapper;
import egovframework.example.sample.service.impl.staticsMapper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.Set;
import java.util.TreeMap;

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
		
		//System.out.println("infoUrl.do");
		//System.out.println(searchValue);
		String info="";
		System.out.println(companyName);
		try {
			info = getInfoService(searchValue,companyName);
			//System.out.println("\n****************");
			//System.out.println("string info: "+info);
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
		//HashMap<String, Object> hmap = new HashMap<String, Object>();
		//hmap.put("sb", sb);
		try {
			System.out.println("result: "+ result);
			NutrientDTO deserializeNu = objectMapper.readValue(result, NutrientDTO.class);
			
			//System.out.println("직렬화: "+deserializeNu.toString());
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
			//	System.out.println((String)jsonObj.get("DESC_KOR"));
			
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
			
		}catch(Exception e) {
			System.out.println("ArrayList직력화중 에러 발생");
		}
		
	//	System.out.println("json: "+ json);
		
		JSONObject obj = new JSONObject(); 
		
	//	System.out.println("nuDa2: "+ nuDa2);
	//	System.out.println("nuDa3: "+ nuDa3);
	//	System.out.println("nuDa4: "+ nuDa4);
	//	System.out.println("cate:" + categories);
		
		//obj.put("categories", "[\r\n" + "  \"1월\",\r\n" + "  \"2월\",\r\n" + "  \"3월\",\r\n"+ "  \"4월\"\r\n"+ "]");
		
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
		//System.out.println("dataEdit 실행");
		
		//String jsonData = (String) editObj.get("jsonData");
	//	System.out.println( "수정 데이터: "+jsonData);
		try {
			decodeVal = URLDecoder.decode(jsonData, "utf-8");
		//	System.out.println("디코딩 문자: "+decodeVal);
		} catch (UnsupportedEncodingException e) {
			System.out.println("디코딩중 에러발생");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		decodeVal = decodeVal.replace("jsonData=", "");
		decodeVal = decodeVal.replace("}],", "}],");
		//decodeVal +=",\"pageNo\":\"1\",\"totalCount\":\"94\",\"numOfRows\":\"10\"}";
		//System.out.println("치환후 decodeVal: "+ decodeVal);
		
		
		System.out.println("**************");
		//System.out.println(decodeVal);
		//System.out.println("1");
		JSONParser parser = new JSONParser();
		//System.out.println("2");
		Object obj = null;
		
		try {
			//obj = parser.parse( strJson );
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

		System.out.println("***searchVal: "+searchVal);
		List<Map<String, Object>> bodyList =  new ArrayList<>();
	
		
		try {
			Body desi = objectMapper.readValue(decodeVal, Body.class);
			//System.out.println(desi);
			
			bodyList = (List<Map<String, Object>>)desi.getItems();
			
		//	System.out.println("bodyList: "+ bodyList);
			//insertList= (List<NutrientDTO>) deserializeNu.getBody().getItems();
			for(int i = 0; i<bodyList.size();i++) {
				LinkedHashMap<String, Object> lmap = (LinkedHashMap<String, Object>) bodyList.get(i);
				//System.out.println("bodyList 각 요소: "+lmap);
				//lmap.get("DESC_KOR");
			}
			
			//System.out.println("아이템 직렬화 제대로 실행");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("아이템 직렬화로 읽기 중 오류");
		}
		
		System.out.println("1");
		
		JSONArray items = (JSONArray)jsonObj.get("items");
		//System.out.println("jsonArray: "+items);
		JSONObject jsonObj2=null ;
		for(int i = 0; i<items.size();i++) {
			jsonObj2 = (JSONObject)items.get(i);
			long index2 = (long) jsonObj2.get("IDX_NU");
			int index3 =(int)index2;
			//System.out.println("index3: "+index3);
			
			Items items2 = new Items();
			System.out.println("2-2");
			System.out.println();
			double nutr1 = Double.parseDouble((String)jsonObj2.get("NUTR_CONT1"));
			
			long nutr2 = (long) jsonObj2.get("NUTR_CONT2");
			//double nutr2 = (double) jsonObj2.get("NUTR_CONT2");
			long nutr3 = (long) jsonObj2.get("NUTR_CONT3");
			long nutr4 = (long) jsonObj2.get("NUTR_CONT4");
			
			items2.setIDX_NU(index3);
			items2.setNUTR_CONT1(nutr1);
			items2.setNUTR_CONT2((double)nutr2);
			items2.setNUTR_CONT3((double)nutr3);
			items2.setNUTR_CONT4((double)nutr4);
			items2.setSERVING_WT((String)jsonObj2.get("SERVING_WT"));
			System.out.println("3");
			dataMapper.updateData(items2);
		}
		Items items1 = new Items();
		items1.setDESC_KOR(searchVal);
		System.out.println("수정완료");
		ArrayList<Items> selectItem =  dataMapper.selectData(items1);
		
		JSONObject editObj = new JSONObject();                
		editObj.put("items", selectItem);
		result = editObj.toJSONString();
		
		return result;
	}
	
	@RequestMapping("/dataEdit2.do")
	@ResponseBody
	public String dataEdit2(@RequestBody String jsonData, String searchValue) throws JsonMappingException, JsonProcessingException  {
		
		String result="";
		//System.out.println("dateEdit2 실행");
		//System.out.println("jsonData는 "+jsonData);
		
		try {
			jsonData = URLDecoder.decode(jsonData, "utf-8");
			System.out.println("디코딩 문자: "+jsonData.toString());
		} catch (UnsupportedEncodingException e) {
			System.out.println("디코딩중 에러발생");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
	
		return result;
	}
	@RequestMapping("/gridUpdateSort.do")
	@ResponseBody
	public String gridUpdateSort(String nutrientCd, String searchWord, String sortFlag) throws JsonMappingException, JsonProcessingException  {
		
		System.out.println("grid Update Sort 실행");
		System.out.println("sortFlag: "+ sortFlag);
		System.out.println("searchWord: " + searchWord);
		System.out.println("nutrientCd: " + nutrientCd);
		String result="0";
	
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		CodeDTO cdto = new CodeDTO();
		CodeDTO rdto = new CodeDTO();
		
		System.out.println("sortFlag: "+ sortFlag);
		rdto = staticsMapper.filterCode(cdto);
		
		// String castId = rdto.getCD_NAME();
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
	
		System.out.println(filterList);
		obj.put("items", filterList);
		
	
		result = obj.toString();
	
		return obj.toJSONString();	
	}
	
	public static String filePath = "C:\\poi_temp";
	public static String fileNm = "poi_making_file_test.xlsx";
	@RequestMapping(value = "/gridExcelDownload.do",
			produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String gridExcelDownload(@RequestBody String dataObjStr) {
		String result = "0";
		String decodeVal="";
		
		try {
			decodeVal += URLDecoder.decode(dataObjStr, "utf-8");
		    System.out.println("디코딩 문자: "+decodeVal);
		} catch (UnsupportedEncodingException e) {
			System.out.println("디코딩중 에러발생");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		decodeVal = decodeVal.replace("jsonData=", "");
		System.out.println("치환: " + decodeVal);
		
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

        // Sheet를 채우기 위한 데이터들을 Map에 저장
        Map<String, Object[]> data = new HashMap<>();
        data.put("0", new Object[]{"식품이름","1회 제공량(g)", "칼로리(kcal)", "탄수화물(g)","단백질(g)","지방(g)"});
        
        for(int i = 0; i<bodyList.size();i++) {
			LinkedHashMap<String, Object> lmap = (LinkedHashMap<String, Object>) bodyList.get(i);
			//System.out.println("bodyList 각 요소: "+lmap);
		//	System.out.println(lmap.get("DESC_KOR"));
			//lmap.get("DESC_KOR");
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
    
		return result;
	}
	
}
