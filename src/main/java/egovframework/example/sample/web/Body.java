package egovframework.example.sample.web;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class Body {
	
	/*이상하게 배열형식이지만 List는 안받고 Object 형식으로 받으니 받아진다.*/
	 //List<Items> items;
	Object items;
	private String pageNo;
	private String totalCount;
	private String numOfRows;
}
