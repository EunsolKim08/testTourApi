package egovframework.example.sample.web;

import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class Items {
	
	String DESC_KOR;
	String SERVING_WT; //1회제공량
	//String nutr_cont1; //열랑
	//String nutr_cont2; //탄수
	//String nutr_cont3;//단백질
	//String nutr_cont4;//지방
	

}
