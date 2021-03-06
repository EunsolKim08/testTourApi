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
	double NUTR_CONT1; //열량
	double NUTR_CONT2; //탄수
	double NUTR_CONT3;//단백질
	double NUTR_CONT4;//지방
	String ANIMAL_PLANT; //회사명
	int IDX_NU;
	
	
}
