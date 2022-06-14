<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://uicdn.toast.com/grid/latest/tui-grid.css" />
<script src="https://uicdn.toast.com/grid/latest/tui-grid.js"></script>
<meta charset="UTF-8">
<title>보령 관광 json Data 실험</title>
</head>
<body>
<h1>보령관광 json Data 실험</h1>
<hr/>
<script>
var searchValue="";
var item = "";
function search(form){
	console.log(form.selectThema.value);
	searchValue = form.selectThema.value;
	
	let obj = {
			searchValue: searchValue
	};
	
	$.ajax({ 
		url :'infoUrl.do',
	    dataType:"json",
	    data : obj,
	    success: function(data){ 
	    	console.log("infoS"); 
	    	console.log(data);
	    	console.log(data.body);
	    	console.log(data.body.items);
	    	item = data.body.items;
	    	
	        console.log(item);
	        const grid2 = new tui.Grid({
	  		  el: document.getElementById('grid2'),
	  		  data: item,
	  		  columns: [
	  		    {
	  		      header: 'DESC_KOR',
	  		      name: 'DESC_KOR'
	  		    },
	  		    {
	  			      header: 'SERVING_WT',
	  			      name: 'SERVING_WT'
	  			},
	  			 {
	  			      header: 'NUTR_CONT1',
	  			      name: 'NUTR_CONT1'
	  			 },
	  			 {
	  			      header: 'NUTR_CONT2',
	  			      name: 'NUTR_CONT2'
	  			 },
	  			 {
	  			      header: 'NUTR_CON3',
	  			      name: 'NUTR_CONT3'
	  			 }
	  			]
	  	})
	    },
	    error: function(data) {
	    	console.log("infoE"); 
	    }
	});
}

function testfun() {
	let obj = {
			name: "ss",
			ggg:"aa"
	};
	
	$.ajax({ 
		url :'testcall.do',
	    dataType:"json",
	    data : obj,
	    success: function(data){ 
	    	console.log("s"); 
	    },
	    error: function(data) {
	    	console.log("e"); 
	    }
	});
}


</script>
<button id="testbtn" onclick="testfun()">btn</button>
<form name="frm" style="margin-top:50px; margin-left:70%; margin-bottom:50px;"  onchange="return search(this)">
	테마에 따른 관광지 검색
	<select name="selectThema" id ="selectThema">	
		<option value = "">선택</option>	
		<option value = "1064">섬</option>	
		<option value = "1065">해수욕장</option>	
		<option value = "1066">항구</option>
		<option value = "1067">산/계곡</option>
		<option value = "1068">호수</option>
		<option value = "1069">축제</option>
		<option value = "1070">체험/마을</option>
		<option value = "1071">휴양/공원</option>
		<option value = "1072">박물관</option>
		<option value = "1073">보물/서적</option>
		<option value = "1074">천연기념물</option>
		<option value = "1075">유/무형 문화재</option>
		<option value = "1076">기념물/민속자료</option>
		<option value = "1077">문화재 자료</option>
	</select>
</form>
<br>
<div id="grid"></div>
<script>
        // GRID를 생성한다.
		var grid = new tui.Grid( {
			el: document.getElementById('grid'),
			columns: [
				{
					header: '테마명',
					name: 'themename'
				},
				{
					header: '간략설명글',
					name: 'explain'
				},
				{
					header: '지역명',
					name: 'localname'
				},
				{
					header: '주소',
					name: 'locationaddr'
				}
			]
		} );

        // GRID 에 데이터를 입력한다.
		var arrData = [
			{
				themename: '보령머드축제',
				explain: '머드축제에 오세요',
				localname:'대천해수욕장',
				locationaddr:'충청남도 보령시 대천'
			}
		];

		grid.resetData(arrData);
	</script>
<div id="grid2"></div>
</body>
</html>