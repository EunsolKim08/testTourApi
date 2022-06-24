<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://uicdn.toast.com/grid/latest/tui-grid.css" />
<script src="https://uicdn.toast.com/grid/latest/tui-grid.js"></script>
<script type="text/javascript" src="https://uicdn.toast.com/tui.pagination/v3.4.0/tui-pagination.js"></script>
<link rel="stylesheet" href="https://uicdn.toast.com/chart/latest/toastui-chart.min.css" />
<script src="https://uicdn.toast.com/chart/latest/toastui-chart.min.js"></script>

<meta charset="UTF-8">
<title>식품_영양성분_DB서비스</title>
</head>
<body>
<div style="margin:100px;">
	<h1>식품_영양성분_DB서비스</h1>
	<br/><br/>
	데이터 출력: 
	<form name="frm2">
		<input type="radio" name="dataPrint" value="sta" onchange="changeVa()" checked/>표
		<input type="radio" name="dataPrint" value="cha" onchange="changeVa()"/>차트
	</form>
	<script>
	var searchValue="";
	var item = "";
	var formName="";
	var companyName="";
	function search(){
		formName = document.frm.foodName.value;
		company = document.frm.company.value;
		
		console.log(formName);
		console.log(company);
		
		searchValue = formName;
		companyName = company;
		
		if(searchValue ==  ""){
			alert("영양정보 검색을 위해서는 반드시 식품명을 검색해야합니다.");
			return document.frm.foodName.focus();
		}
		let obj = {
				searchValue: searchValue,
				companyName: companyName
		};
		$.ajax({ 
			url :'infoUrl.do',
		    dataType:"json",
		    data : obj,
		    success: function(data){ 
		    	item = data.body.items;
		        grid.resetData(item);
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
	
	function onKeyUp(e){
	    if (e.keyCode == 13){
	    	return document.frm.foodName.focus();
	    }
	}
	function apiTest2() {
		formName = document.frm.foodName.value;
		company = document.frm.company.value;
		
		console.log(formName);
		console.log(company);
		
		searchValue = formName;
		companyName = company;
		
		if(searchValue ==  ""){
			alert("영양정보 검색을 위해서는 반드시 식품명을 검색해야합니다.");
			return document.frm.foodName.focus();
		}
		
		let obj = {
				searchValue : searchValue,
				companyName : companyName
		}
		$.ajax({ 
			url :'jsonParsing.do',
		    dataType:"json",
		    data: obj,
		    success: function(data){ 
		    	console.log("s"); 
		    	console.log(data);
		    	item = data;
		        grid.resetData(item);
		    },
		    error: function(data) {
		    	console.log("e"); 
		    }
		});
	}
	function apiTest3() {
		formName = document.frm.foodName.value;
		company = document.frm.company.value;
		
		console.log(formName);
		
		searchValue = formName;
		companyName = company;
		
		if(searchValue ==  ""){
			alert("영양정보 검색을 위해서는 반드시 식품명을 검색해야합니다.");
			return document.frm.foodName.focus();
		}
		
		let obj = {
				searchValue : searchValue,
				companyName : companyName
		}
		$.ajax({ 
			url :'mapJsonParsing3.do',
		    dataType:"json",
		    data: obj,
		    success: function(data){ 
		    	console.log("ms"); 
		    	console.log(data);
		    	item = data.body.items;
		        grid.resetData(item);
		    },
		    error: function(data) {
		    	console.log("me"); 
		    }
		});
	}
	
	function dataSearch(){
		formName = document.frm.foodName.value;
		company = document.frm.company.value;
		
		console.log(formName);
		
		searchValue = formName;
		companyName = company;
		
		if(searchValue ==  ""){
			alert("영양정보 검색을 위해서는 반드시 식품명을 검색해야합니다.");
			return document.frm.foodName.focus();
		}
		
		let obj = {
				searchValue : searchValue,
				companyName : companyName
		}
		$.ajax({ 
			url :'dataSearch.do',
		    dataType:"json",
		    data: obj,
		    success: function(data){ 
		    	console.log("ds"); 
		    	console.log(data);
		    	console.log(data.items);
		    	item = data.items;
		     grid.resetData(item);
		    	//grid.readData(item);
		    },
		    error: function(data) {
		    	console.log("de"); 
		    }
		});
	}
	function dataEdit(){
		console.log("데이터 수정");
		
		var obj = grid.getModifiedRows().updatedRows;
		
		console.log("수정된 부분: " + obj);
		var jsonObj= JSON.stringify(obj);
		console.log("json 변환: "+jsonObj );
		var x= jsonObj.toString();
		console.log(x);
		
		$.ajax({ 
			url :'dataEdit.do',
			type: 'POST', 
		    dataType:"json",
		    data: { 
		    	jsonData: jsonObj,
		    },
		    contentType : 'application/json; charset=UTF-8',
		    success: function(data){ 
		    console.log("edit 완료");
		    	//grid.readData(item);
		    },
		    error: function(data) {
		    console.log("edit 실패");
		    }
		});
	}
	</script>
	<!-- <button id="testbtn" onclick="testfun()">btn</button> -->
	<div>
		<form name="frm" id="frm" style="margin-top:50px; margin-left:40%; margin-bottom:50px;">
			<div id ="foodBox" style="font-size:20px;">
			<span style="margin-right:40px;">
				회사명 선택
				<select id="company" name="company" style="font-size:20px;">
					<option value=''>--선택--</option>
					<option value='농심'>--농심--</option>
					<option value='빙그레'>--빙그레--</option>
					<option value='삼양식품'>--삼양식품--</option>
				</select>
			</span>
			식품명
			<input type="text" name="foodName" id="foodName" style="height:20px; width:100px; font-size:18px;"
			/>
		<!-- 	<button type="button" id="searchNutirent" name="searchNutirent" onclick="search()" 
				style="font-size:20px; background-color:#5882FA; border-color:#5882FA; color:#FFFFFF;">api1 검색하기</button>
			<button type="button" id="searchNutirent" name="searchNutirent" onclick="apiTest2()" 
				style="font-size:20px; background-color:#5882FA; border-color:#5882FA; color:#FFFFFF;">api2 검색하기</button> -->
			<button type="button" id="searchNutirent" name="searchNutirent" onclick="apiTest3()" 
				style="font-size:20px; background-color:#5882FA; border-color:#5882FA; color:#FFFFFF;">api3 </button>
			<button type="button" id="searchNutirent" name="searchNutirent" onclick="dataSearch()" 
				style="font-size:20px; background-color:#5882FA; border-color:#5882FA; color:#FFFFFF;">데이터검색 </button>
			</div>
		</form>
	</div>
	<br/><hr/>
	<div style="margin-top:50px; margin-left:80%">
		<button type="button" id="searchNutirent" name="searchNutirent" onclick="dataEdit()" 
				style="font-size:20px; background-color:#747474; border-color:#747474; color:#FFFFFF;">수정하기</button>
	</div>
	
	<br/>
	<br/>
	
	<br>
	
	<div id="grid"></div>
	<script>
	 var grid = new tui.Grid({
 		  el: document.getElementById('grid'),
 		  pagination:true,
 		  columns: [
 		    {
 		      header: '식품이름',
 		      name: 'DESC_KOR' 		      
 		    },
 		   {
 	 		      header: '제조사',
 	 		      name: 'ANIMAL_PLANT'
 	 		    },
 		    {
 			      header: '1회제공량 (g)',
 			      name: 'SERVING_WT',
 			      editor: 'text',
 			      
 			},
 			 {
 			      header: '열량 (kcal)',
 			      name: 'NUTR_CONT1',
 			      sortable: true,
 			      sortingType: 'asc',
 			      editor: 'text',
 			 
 			 },
 			 {
 			      header: '탄수화물 (g)',
 			      name: 'NUTR_CONT2',
 			      sortable: true,
 			      sortingType: 'asc',
 			      editor: 'text',
 			      afterChange(ev) {
 			         console.log('After change:' + ev);
 			       },
 			 },
 			 {
 			      header: '단백질 (g)',
 			      name: 'NUTR_CONT3',
 			      sortable: true,
 			      sortingType: 'asc',
 			      editor: 'text'
 			 },
 			 {
 			      header: '지방 (g)',
 			      name: 'NUTR_CONT4',
 			      sortable: true,
 			      sortingType: 'asc',
 			      editor: 'text'
 			 },
 			 
 			]
 	});
	
	</script>
</div>

<div id="chart"></div>
<script type="text/javascript">
var dataCh="";
function changeVa(){
	dataCh = document.frm2.dataPrint.value;
	
	console.log(dataCh);
	formName = document.frm.foodName.value;
	company = document.frm.company.value;
	
	console.log(formName);
	
	searchValue = formName;
	companyName = company;
	
	if(searchValue ==  ""){
		alert("차트정보 검색을 위해서는 반드시 식품명을 검색해야합니다.");
		return document.frm.foodName.focus();
	}
	
	let obj = {
			searchValue : searchValue,
			companyName : companyName
	}
	var chaData= "";
	var nuDa2="";
	var nuDa3="";
	var nuDa4="";
	if(dataCh == 'cha'){
		console.log("차트설정");
		/* namespace */
		let obj = {
				searchValue : searchValue,
				companyName : companyName
		}
		$.ajax({ 
			url :'getChartData.do',
		    dataType:"json",
		    data: obj,
		    success: function(data){ 
			    console.log(data);
			    console.log(data.categories);
			    chaData = data.categories;
			    nuDa2 = data.nuDa2;
			    nuDa3 = data.nuDa3;
			    nuDa4 = data.nuDa4;
			    //console.log(data.array2.categories);
			    console.log("chaData: "+chaData);
		    },
		    error: function(data) {
		    	console.log("de"); 
		    }
		});
		
		
		 $.when($.ajax("getChartData.do")).done(function(){
			// console.log("chaData2: "+JSON.parse('["'+chaData+'"]'));
			 const Chart = toastui.Chart;
			 const el = document.getElementById('chart');
				
			// var datCa = JSON.parse(chaData);
			 var cNuDa2 =  JSON.parse(nuDa2);
			 var cNuDa3 =  JSON.parse(nuDa3);
			 var cNuDa4 =  JSON.parse(nuDa4);
			 //var datCa = JSON.parse('['+"1월", "2월", "3월"+']');
			 var i = 0;
			 var jsonRe="[";
			const arr=chaData.split(",");
			while(i < arr.length){
				if( i != arr.length-1){
					jsonRe +='"' +arr[i]+'",';
				}else{
					jsonRe +='"' +arr[i]+'"]';
				}
				i++;
			}
			
			 
			 console.log("파싱 준비: " + jsonRe);
			 var x = '["1월", "2월", "3월"]';
			 console.log("x는 "+ x);
			 var y = '['+ '"4월","5월","6월"' +']'; 
			 console.log("y는 "+ y);
			var datCa = JSON.parse(jsonRe);
			var dataSe =  [
			   {
			       name: '탄수화물(g)',
			       data: cNuDa2,
			    },
			    {
			      name: '단백질(g)',
			       data: cNuDa3,
			     },
			     {
				    name: '지방(g)',
				    data: cNuDa4,
					 }
				];
				const data = {
				  categories: datCa,
				  series: dataSe,
				};
				const options = {
				  chart: { width: 700, height: 400 },
				};
				const chart = Chart.barChart({ el, data, options });
	     });
	} //차트 선택시
	if(dataCh == 'sta'){
		
	}
}

grid.on('editingFinish', ev => {
    console.log('editing key: ' + ev.rowKey);
    console.log('editing 컬럼명: ' + ev.columnName);
  });

</script>


</body>
</html>