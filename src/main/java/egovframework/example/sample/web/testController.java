package egovframework.example.sample.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;

@Controller
public class testController {
	
	@RequestMapping(value = "/testNutrient.do")
	public ModelAndView testNutrient() {
		
		/*페이지 설정*/
		ModelAndView mv = new ModelAndView();
		mv.setViewName("test/testNutrient");
		
		return mv;
	}
	
	@RequestMapping("/infoUrl.do")
	@ResponseBody
	public String searchTourInfo(String searchValue) {
		
		//System.out.println("infoUrl.do");
		//System.out.println(searchValue);
		String info="";
		try {
			info = getInfoService(searchValue);
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
	
	public String getInfoService(String searchValue) throws Exception {
	  
	    StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=Tqi6GXzISVFFUWsPcky9vgkUk4M2XhuAByFsXN5adVLBkRL8ZLTVI1qQ%2Bzo3PVJeCXI5%2FZfhvuEPEFYjH4F0mg%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("desc_kor","UTF-8") + "=" + URLEncoder.encode(searchValue, "UTF-8")); /*식품이름*/
      //  urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
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
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
        
        String result = sb.toString();       
        
        return result;
    }
	/*java로 json Parsing 도전해보기*/
	public String jsonParsing(String parseLine) {
		String result="";
		
		System.out.println("parse 메소드 출력1");
		JSONParser parser = new JSONParser();
		System.out.println("parse 메소드 출력2");
		JSONObject obj = null;
		
		try {
			obj = (JSONObject)parser.parse(parseLine);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("parse obj 변환중 에러");
		}
		
		System.out.println(obj);
		System.out.println(obj.get("body"));
		
		return result;
	}
	
	
	

}
