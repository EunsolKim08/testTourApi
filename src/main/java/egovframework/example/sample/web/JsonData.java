package egovframework.example.sample.web;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class JsonData {
	
	//String orginalName;
	//String saveName;
	List<createdRows> createdRows;
	List<deletedRows> deletedRows;
}
