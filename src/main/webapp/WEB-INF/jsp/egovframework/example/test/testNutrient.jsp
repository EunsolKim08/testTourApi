<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://uicdn.toast.com/grid/latest/tui-grid.css" />
<script src="https://uicdn.toast.com/grid/latest/tui-grid.js"></script>
<meta charset="UTF-8">
<title>식품_영양성분_DB서비스</title>
</head>
<body>
<div style="margin:100px;">
	<h1>식품_영양성분_DB서비스</h1>
	<br/><br/>
	<script>
	var searchValue="";
	var item = "";
	var formName="";
	function search(){
		formName = document.frm.foodName.value;
		console.log(formName);
		
		searchValue=formName;
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
	
	
	</script>
	<!-- <button id="testbtn" onclick="testfun()">btn</button> -->
	<form name="frm" id="frm" style="margin-top:50px; margin-left:50%; margin-bottom:50px;">
		식품명으로 검색하기
		<input type="text" name="foodName" id="foodName"/>
		<button type="button" id="testbtn" onclick="search()">검색하기 버튼</button>
	</form>
	<br/><hr/>
	<br/>
	<br/>
	
	<br>
	<div id="grid"></div>
	<script>
	 var grid = new tui.Grid({
 		  el: document.getElementById('grid'),
 		  columns: [
 		    {
 		      header: '식품이름',
 		      name: 'DESC_KOR'
 		    },
 		    {
 			      header: '1회제공량 (g)',
 			      name: 'SERVING_WT'
 			},
 			 {
 			      header: '열량 (kcal)',
 			      name: 'NUTR_CONT1'
 			 },
 			 {
 			      header: '탄수화물 (g)',
 			      name: 'NUTR_CONT2'
 			 },
 			 {
 			      header: '단백질 (g)',
 			      name: 'NUTR_CONT3'
 			 },
 			 {
 			      header: '지방 (g)',
 			      name: 'NUTR_CONT4'
 			 }
 			]
 	})
	</script>
</div>
</body>
</html>