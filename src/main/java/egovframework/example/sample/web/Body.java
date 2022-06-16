package egovframework.example.sample.web;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class Body {
	
	 List<Items> items;
	//private Items items;
	private String pageNo;
	private String totalCount;
	private String numOfRows;
}
