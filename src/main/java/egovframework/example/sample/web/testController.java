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
		
		/*????????? ??????*/
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
        urlBuilder.append("&" + URLEncoder.encode("desc_kor","UTF-8") + "=" + URLEncoder.encode(searchValue, "UTF-8")); /*????????????*/
      //  urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*???????????????*/
       // urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*??? ????????? ?????? ???*/
        urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*??????????????? ??????(xml/json) Default: xml*/
         
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
	
	/*java??? json Parsing ???????????????*/
	@RequestMapping("/jsonParsing.do")
	@ResponseBody
	public String jsonParsing(String searchValue, String companyName) throws UnsupportedEncodingException, IOException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(companyName);
		String result="1";
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1"); /*URL*/
	    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Tqi6GXzISVFFUWsPcky9vgkUk4M2XhuAByFsXN5adVLBkRL8ZLTVI1qQ%2Bzo3PVJeCXI5%2FZfhvuEPEFYjH4F0mg%3D%3D"); /*Service Key*/
	    urlBuilder.append("&" + URLEncoder.encode("desc_kor","UTF-8") + "=" + URLEncoder.encode(searchValue, "UTF-8")); /*????????????*/
	    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*???????????????*/
	    // urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*??? ????????? ?????? ???*/
	    urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*??????????????? ??????(xml/json) Default: xml*/
	         
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
		//?????? jsonArray?????? return itmes???????????? error??? ???????????? ???????????? items??? ?????? return?????? ?????????.
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
	    urlBuilder.append("&" + URLEncoder.encode("desc_kor","UTF-8") + "=" + URLEncoder.encode("?????????", "UTF-8")); /*????????????*/
	    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*???????????????*/
	    // urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*??? ????????? ?????? ???*/
	    urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*??????????????? ??????(xml/json) Default: xml*/
	    
	    
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
		//?????? jsonArray?????? return itmes???????????? error??? ???????????? ???????????? items??? ?????? return?????? ?????????.
		return items.toJSONString();
	}
	
	/*
	 	jsonParsing ??????3. jackson??? json-data bind???????????? object-mapper??????. 
	 */
	@RequestMapping("/mapJsonParsing3.do")
	@ResponseBody
	public String mapJsonParsing3(String searchValue, String companyName) throws IOException {
		
		String result="";
		ObjectMapper mapper = new ObjectMapper();
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1"); /*URL*/
	    urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Tqi6GXzISVFFUWsPcky9vgkUk4M2XhuAByFsXN5adVLBkRL8ZLTVI1qQ%2Bzo3PVJeCXI5%2FZfhvuEPEFYjH4F0mg%3D%3D"); /*Service Key*/
	    urlBuilder.append("&" + URLEncoder.encode("desc_kor","UTF-8") + "=" + URLEncoder.encode(searchValue, "UTF-8")); /*????????????*/
	    urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*???????????????*/
	    // urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*??? ????????? ?????? ???*/
	    urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*??????????????? ??????(xml/json) Default: xml*/
	    
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
	    System.out.println("sb: "+ sb); // ?????? ????????? ???????????? ????????????..
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
		    urlBuilder.append("&" + URLEncoder.encode("desc_kor","UTF-8") + "=" + URLEncoder.encode(searchValue, "UTF-8")); /*????????????*/
		  //  urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*???????????????*/
		    urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); /*??? ????????? ?????? ???*/
		    urlBuilder.append("&" + URLEncoder.encode("type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*??????????????? ??????(xml/json) Default: xml*/
		    
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
				System.out.println("?????? x??? fResult: "+ fResult);
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
			System.out.println("????????? ???????????? O.");
			insertFlag = 1;
			JSONObject obj = new JSONObject();                
			obj.put("items", selectItem);
			System.out.println("????????? ?????????: "+selectItem);
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
		System.out.println("????????????");
		
		
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
			System.out.println("ArrayList???????????? ?????? ??????");
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
		
		System.out.println("????????? ?????? ???");
		
		String result="1";
		String json="";
		String decodeVal = "{\"items\":\r\n";
		
		try {
			decodeVal = URLDecoder.decode(jsonData, "utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("???????????? ????????????");
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
			System.out.println("parser.pare ????????? ??????");
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
			System.out.println("????????? ???????????? ?????? ??? ??????");
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
		System.out.println("????????????");
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
		/* ????????? ?????? SORT_NU ??????*/
		staticsMapper.updateSort(map);
		
		result= "1";
		
		ArrayList<Items> filterList = null;
		
		
		filterList= staticsMapper.filterSortData(map);
		System.out.println("SELECT ?????? ??????");
		JSONObject obj = new JSONObject(); 
	
		obj.put("items", filterList);
		
	
		result = obj.toString();
	
		return obj.toJSONString();	
	}
	
	/*?????? ????????? ???????????? ?????? ??????*/
	public static String filePath = "C:\\poi_temp";
	/*?????? ?????? ??????*/
	public static String fileNm = "poiData.xlsx";
	
	
	/*?????? ?????? ?????? ????????? ?????????.*/
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
		
		/*poi ?????? ????????? api??? ???????????? ?????? ???????????? ??????*/
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
			System.out.println("???????????? ????????????");
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
			System.out.println("????????? ???????????? ?????? ??? ??????");
		}
		
		// ??? Workbook ??????
        XSSFWorkbook workbook = new XSSFWorkbook();

        // ??? Sheet??? ??????
        XSSFSheet sheet = workbook.createSheet("Nutrient data");
        XSSFCellStyle xstyle = workbook.createCellStyle();

        // Sheet??? ????????? ?????? ??????????????? Map??? ??????
        Map<String, Object[]> data = new HashMap<>();
        data.put("0", new Object[]{"????????????","1??? ?????????(g)", "?????????(kcal)", "????????????(g)","?????????(g)","??????(g)"});
        
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
			
			//num??? ???????????? Object ????????? ???????????????.
			data.put(num, new Object[]{DESC_KOR,SERVING_WT, NUTR_CONT1, NUTR_CONT2, NUTR_CONT3, NUTR_CONT4});
		}
    
      
        int rownum = 0;

        //cell??? 0????????? headr???.. ??????????????? data??? ?????????.
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
		        	String path = filePath+ "\\"+fileNm; // ????????? ????????? ??? ????????????('\') ??????
		        	// C:\\poi_temp
		        	File file = new File(path);
		        	response.setHeader("Content-Disposition", "attachment;filename=" + file.getName()); // ???????????? ????????? ????????? ???????????? ????????? ??????????????? ???????????? ??????
		        	FileInputStream fileInputStream = new FileInputStream(path); // ?????? ???????????? 
		        	OutputStream out = response.getOutputStream();
		        	
		        	int read = 0;
		                byte[] buffer = new byte[1024];
		                while ((read = fileInputStream.read(buffer)) != -1) { // 1024???????????? ?????? ???????????? outputStream??? ??????, -1??? ????????? ????????? ?????? ????????? ??????
		                    out.write(buffer, 0, read);
		                }
		            out.close();
		        } catch (Exception e) {
		            throw new Exception("???????????? ?????? ??? ??????");
		        }
		}
		
	}
	/*????????? ??????????????? path??????*/
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
		/*?????? ????????? ?????????*/
		for (MultipartFile mf : fileList)
		{
			String originFileName = mf.getOriginalFilename(); //?????? ?????? ??? 
		 	long fileSize = mf.getSize(); // ?????? ?????????
		 	
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
	        	 System.out.println("??????????????? ??????1");
	             // TODO Auto-generated catch block
	             e.printStackTrace();
	         } catch (IOException e) {
	             // TODO Auto-generated catch block
	        	 System.out.println("??????????????? ??????2");
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
	    System.out.println("???????????????: "+fileList);
	   // String path = application.getRealPath("[??????]");
	    
	    File fileDir = new File(fileUploadPath);
	    
	    if (!fileDir.exists()) {
	    	fileDir.mkdirs();
	    }
	    
	    long time = System.currentTimeMillis();
	    
	    for (MultipartFile mf : fileList) {
	    	System.out.println("3333");
	    	String originFileName = mf.getOriginalFilename(); // ?????? ?????? ???
	        String saveFileName = String.format("%d_%s", time, originFileName);
	        System.out.println(originFileName);
	        try {
	        	// ????????????
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
				+ "  \"param\": \"??????\",\r\n"
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
				+ "      \"DESC_KOR\": \"??????,??????????????????????????????\",\r\n"
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
			System.out.println("json?????? ?????? ??? ????????????");
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
			/*json Data ??????*/
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
				/*?????? ????????? ??????*/
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
	
		System.out.println("??????222: "+ obj);
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
	
		System.out.println("??????333: "+ test);
	
		
		return result;
	}
	
}
