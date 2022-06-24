package egovframework.example.sample.web;

import javax.annotation.Resource;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.example.sample.service.impl.dataMapper;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;

@Controller
public class testController {
	@Resource(name = "dataMapper")
	dataMapper dataMapper;
	
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
		System.out.println("조회완료");

		if(selectItem.size()<1) {
			System.out.println("데이터 조회결과 X.");
			insertFlag = 0;
			
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
		    //System.out.println("sb: "+ sb); // 이거 자체가 파싱하는 객체인듯..
			result = sb.toString();	
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			NutrientDTO nutrientDto = null;
			List<NutrientDTO> insertList =  new ArrayList<>();
			
			try {
				
				NutrientDTO deserializeNu = objectMapper.readValue(result, NutrientDTO.class);
				insertList= (List<NutrientDTO>) deserializeNu.getBody().getItems();
				//deserializeNu.getBody().getItems();
				//System.out.println("직렬화: "+deserializeNu.toString());
				System.out.println("********LIST  확인*******");
				for(int i = 0;i<insertList.size();i++) {
					System.out.println(insertList.get(i));
				}
				fResult = mapper.writeValueAsString(deserializeNu);
				System.out.println("조회 x시 fResult: "+ fResult);
				if(insertFlag == 0) {
					//dataMapper.insertData((List<NutrientDTO>) deserializeNu);
					System.out.println("데이터 삽입 실행 시작");
					dataMapper.insertData(insertList);
					System.out.println("데이터 삽입 실행 완료");
				}
				//System.out.println("fResult: "+ fResult);
				NutrientDTO innerClassPersonDto = objectMapper.readValue(result, NutrientDTO.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			JSONObject obj = new JSONObject();    
			selectItem = dataMapper.selectData(items);
			obj.put("items", selectItem);
			System.out.println("json obj: "+obj);
			
			fResult= obj.toJSONString();
		}else {
			System.out.println("데이터 조회결과 O.");
			System.out.println("result: "+ result);
			insertFlag = 1;
			JSONObject obj = new JSONObject();                
			//System.out.println(selectItem);
			
			//System.out.println(selectItem.get(0));
			obj.put("items", selectItem);
			System.out.println("json obj: "+obj);
			
			fResult= obj.toJSONString();
			
		}
		
		return fResult;
		//return result;
	}
	
	@RequestMapping("/getChartData.do")
	@ResponseBody
	public String getChartData(String searchValue, String companyName) throws IOException{
		
		String result="1";
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
			
			for(int i = 0; i<jsonArr.size();i++) {
				jsonObj = (JSONObject)jsonArr.get(i);
				System.out.println((String)jsonObj.get("DESC_KOR"));
			
				String desc = (String)jsonObj.get("DESC_KOR");
				//categories += " \"\r" + desc +"\"\r" ;
				categories += (String)jsonObj.get("DESC_KOR");
				nuDa2 += (String)jsonObj.get("NUTR_CONT2");
				nuDa3 += (String)jsonObj.get("NUTR_CONT3");
				nuDa4 += (String)jsonObj.get("NUTR_CONT4");
				
				
				if(i < jsonArr.size()-1) {
					nuDa2+=",";
					nuDa3+=",";
					nuDa4+=",";
					categories +=",";
				}
			}
			categories +="";
			nuDa2 +="]";
			nuDa3 +="]";
			nuDa4 +="]";
			
		}catch(Exception e) {
			System.out.println("ArrayList직력화중 에러 발생");
		}
		
		System.out.println("json: "+ json);
		
		JSONObject obj = new JSONObject(); 
		
		System.out.println("nuDa2: "+ nuDa2);
		System.out.println("nuDa3: "+ nuDa3);
		System.out.println("nuDa4: "+ nuDa4);
		System.out.println("cate:" + categories);
		
		//obj.put("categories", "[\r\n" + "  \"1월\",\r\n" + "  \"2월\",\r\n" + "  \"3월\",\r\n"+ "  \"4월\"\r\n"+ "]");
		
		obj.put("categories", categories);
		obj.put("nuDa2", nuDa2);
		obj.put("nuDa3", nuDa3);
		obj.put("nuDa4", "[30, 10, 4]");
		result= obj.toJSONString();
		
		return result;
	}
	
	@RequestMapping("/dataEdit.do")
	@ResponseBody
	public String dataEdit(@RequestBody String jsonData) throws JsonMappingException, JsonProcessingException  {
		
		 
		String result="1";
		String json="";
		String decodeVal = "{\"items\":\r\n";
		System.out.println("dataEdit 실행");
		
		System.out.println( "수정 데이터: "+jsonData);
		try {
			decodeVal += URLDecoder.decode(jsonData, "utf-8");
			System.out.println("디코딩 문자: "+decodeVal);
		} catch (UnsupportedEncodingException e) {
			System.out.println("디코딩중 에러발생");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		decodeVal=decodeVal.replace("jsonData=[{", "[{");
		decodeVal +=",\"pageNo\":\"1\",\"totalCount\":\"94\",\"numOfRows\":\"10\"}";
		System.out.println("치환후 decodeVal: "+ decodeVal);
		
		
		String orgCode  = decodeVal;
		System.out.println("1");
		JSONParser parser = new JSONParser();
		System.out.println("2");
		Object obj = null;
		
		try {
			//obj = parser.parse( strJson );
			obj = parser.parse( decodeVal );
			//JSONParser jsonParser = new JSONParser();
			//JSONObject jsonObject = (JSONObject)jsonParser.parse(decodeVal);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("parser.pare 실행중 오류");
			e.printStackTrace();
		}
		
		System.out.println("3");
		JSONObject jsonObj = (JSONObject) obj;
		JSONArray jarray = new JSONArray();
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	
		System.out.println("확인"+jsonObj.get("items").toString());
		List<Items> itemList =  new ArrayList<>();
		
		JSONArray items = (JSONArray)jsonObj.get("items");
		JSONObject jsonObj2=null ;

		for(int i = 0; i<items.size();i++) {
			jsonObj2 = (JSONObject)items.get(i);
			System.out.println((String)jsonObj2.get("DESC_KOR"));
			System.out.println((String)jsonObj2.get("DESC_KOR").getClass().getName());
			System.out.println("index1: "+ jsonObj2.get("IDX_NU"));
			long index2 = (long) jsonObj2.get("IDX_NU");
			int index3 =(int)index2;
			System.out.println("index3: "+index3);
			
			Items items2 = new Items();
			
			items2.setIDX_NU(index3);
			items2.setNUTR_CONT1((String)jsonObj2.get("NUTR_CONT1"));
			items2.setNUTR_CONT2((String)jsonObj2.get("NUTR_CONT2"));
			items2.setNUTR_CONT3((String)jsonObj2.get("NUTR_CONT3"));
			items2.setNUTR_CONT4((String)jsonObj2.get("NUTR_CONT4"));
			items2.setSERVING_WT((String)jsonObj2.get("SERVING_WT"));
			System.out.println(items2.getNUTR_CONT1());
			System.out.println(items2.getNUTR_CONT2());
			System.out.println(items2.getNUTR_CONT3());
			System.out.println(items2.getNUTR_CONT4());
			System.out.println(items2.getSERVING_WT());
			dataMapper.updateData(items2);
		}
	
		System.out.println("수정완료");
		return result;
	}
		
	
}
