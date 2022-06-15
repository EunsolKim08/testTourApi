<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://uicdn.toast.com/grid/latest/tui-grid.css" />
<script src="https://uicdn.toast.com/grid/latest/tui-grid.js"></script>
<script type="text/javascript" src="https://uicdn.toast.com/tui.pagination/v3.4.0/tui-pagination.js"></script>
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
		
		searchValue = formName;
		
		if(searchValue ==  ""){
			alert("영양정보 검색을 위해서는 반드시 식품명을 검색해야합니다.");
			return document.frm.foodName.focus();
		}
		
		let obj = {
				searchValue: searchValue
		};
		
		$.ajax({ 
			url :'infoUrl.do',
		    dataType:"json",
		    data : obj,
		    success: function(data){ 
		    	item = data.body.items;
		    	//console.log(data.body.items);
		        //console.log(item);
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
	    	// e.preventDefault();
	    	return document.frm.foodName.focus();
	    }
	}
	function apiTest2() {
		$.ajax({ 
			url :'jsonParsing.do',
		    dataType:"json",
		    success: function(data){ 
		    	console.log("s"); 
		    	console.log(data);
		    },
		    error: function(data) {
		    	console.log("e"); 
		    	//console.log(data.items);
		    }
		});
	}
	
	</script>
	<!-- <button id="testbtn" onclick="testfun()">btn</button> -->
	 <button type="button" id="testBtn2" onclick="apiTest2()">apiTest2</button>
	<div>
		<form name="frm" id="frm" style="margin-top:50px; margin-left:60%; margin-bottom:50px;">
			<div id ="foodBox" style="font-size:20px;">
			식품명
			<input type="text" name="foodName" id="foodName" style="height:20px; width:100px; font-size:18px;"
			/>
			<button type="button" id="searchNutirent" name="searchNutirent" onclick="search()" 
				style="font-size:20px; background-color:#5882FA; border-color:#5882FA; color:#FFFFFF;">검색하기</button>
			</div>
		</form>
	</div>
	<br/><hr/>
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
 			      header: '1회제공량 (g)',
 			      name: 'SERVING_WT',
 			},
 			 {
 			      header: '열량 (kcal)',
 			      name: 'NUTR_CONT1',
 			      sortable: true,
 			      sortingType: 'asc'
 			 },
 			 {
 			      header: '탄수화물 (g)',
 			      name: 'NUTR_CONT2',
 			     sortable: true,
 			      sortingType: 'asc'
 			 },
 			 {
 			      header: '단백질 (g)',
 			      name: 'NUTR_CONT3',
 			     sortable: true,
 			      sortingType: 'asc'
 			 },
 			 {
 			      header: '지방 (g)',
 			      name: 'NUTR_CONT4',
 			     sortable: true,
 			      sortingType: 'asc'
 			 }
 			]

 	});
	
	</script>
</div>
	
</body>
</html>