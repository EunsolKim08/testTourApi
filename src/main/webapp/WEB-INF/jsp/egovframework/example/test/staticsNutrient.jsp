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
<title>영양성분 3가지방법 통계 분석</title>

</head>
<body>
	<h2 style="margin:100px;">영양성분 3가지방법 통계 분석</h2>
	
	
	<div style="margin:100px; font-size:20px;">
	| 필수입력<br/><br/>
		<form name="staticSearch" id="staticSearch">
			구분: 
			<select name="grpCd" id="grpCd" style="font-size:20px; margin-right:20px; text-align:center;">
				<option value="">--- 선택 ---</option>
				<option value="A0005">영양성분 검색</option>
			</select>
			필수1:
			<select name="nCd" style="font-size:20px; margin-right:20px; text-align:center;">
				<option value="">--- 전체 ---</option>
				<option value="N01">칼로리 기준 TOP5</option>
				<option value="N02">탄수화물 기준 TOP5</option>
				<option value="N03">단백질 기준 TOP5</option>
				<option value="N04">지방  기준 TOP5</option>
			</select>
			필수2:
			<input type="text" id="searchWord" name="searchWord" style="font-size:20px; margin-right:20px;">
			<button type="button" style="font-size:20px;" onclick="filterSearch()">조회</button>
		</form>
	</div>
	<div style="margin:100px; font-size:20px;">
		<form name="frm" id="frm">
		| 데이터출력 &nbsp;
			<input type="radio" name="dataPrint" value="sta" onchange="changeVa()" checked/>표
			<input type="radio" name="dataPrint" value="cha" onchange="changeVa()" />차트
		</form>
		<br/><br/>
	</div>
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
			      header: '열량 (kcal)',
			      name: 'NUTR_CONT1',			 
			 },
			 {
			      header: '탄수화물 (g)',
			      name: 'NUTR_CONT2',
			  
			 },
			 {
			      header: '단백질 (g)',
			      name: 'NUTR_CONT3',
			 },
			 {
			      header: '지방 (g)',
			      name: 'NUTR_CONT4',
			 },
			 
			]
	});
	</script>
	<div id="chart" style="margin-left:100px;" ></div>
<script>
var groupCd ="";
var nutrientCd="";
var searchWord ="";
	function filterSearch(){
		console.log("필터기준 조회");
		var item="";
		groupCd = document.staticSearch.grpCd.value;
		console.log("그룹코드: " + groupCd );
		nutrientCd =  document.staticSearch.nCd.value;
		console.log("필수 기준 코드1: "+ nutrientCd);
		searchWord = document.staticSearch.searchWord.value;
		console.log("필수 기준 코드2: "+ searchWord);
		
		
		let obj={
			groupCd : groupCd,
			nutrientCd : nutrientCd,
			searchWord : searchWord
		}
		$.ajax({ 
			url :'filterSearch.do',
			type: 'GET', 
		    dataType:"json",
		    data : obj,
		    success: function(data){ 
		    	console.log("fS");
		    	console.log(data);
		    	item = data.items;
		        grid.resetData(item);
		    },
		    error: function(data) {
		    	console.log("fE"); 
		    }
		});
	}
	var dataCh="";
	function changeVa(){
		console.log("데이터 출력 값 변경");
		dataCh = document.frm.dataPrint.value;
	
		if(dataCh == 'cha'){
			//$('#grid').empty();
			console.log("차트설정");
			console.log("데이터 선택 밴경: "+ dataCh);		
			$("#grid").hide();
			
			let obj={
					groupCd : groupCd,
					nutrientCd : nutrientCd,
					searchWord : searchWord
				}
			
			$.ajax({ 
				url :'filterChart.do',
				type: 'GET', 
			    dataType:"json",
			    data : obj,
			    success: function(cdata){ 
			    	console.log("fS");
			    	console.log(cdata);
			    	//item = data.items;
			       // grid.resetData(item);
			       
			       
			    	 chaData = cdata.categories;
					    nuDa2 = cdata.nuDa2;
					    nuDa3 = cdata.nuDa3;
					    nuDa4 = cdata.nuDa4;
					    //console.log(data.array2.categories);
					    console.log("chaData: "+chaData);
					    
						// console.log("chaData2: "+JSON.parse('["'+chaData+'"]'));
						 const Chart = toastui.Chart;
						 const el = document.getElementById('chart');
							
						// var datCa = JSON.parse(chaData);
						console.log("####### : " + nuDa2)
						 var cNuDa2 =  JSON.parse(nuDa2);
						 var cNuDa3 =  JSON.parse(nuDa3);
						 var cNuDa4 =  JSON.parse(nuDa4);
						 //var datCa = JSON.parse('['+"1월", "2월", "3월"+']');
						 var i = 0;
						 var jsonRe="[";
						const arr=chaData.split("/");
						while(i < arr.length){
							if( i != arr.length-1){
								jsonRe +='"' +arr[i]+'",';
							}else{
								jsonRe +='"' +arr[i]+'"]';
							}
							i++;
						}
						
						 
						 console.log("파싱 준비: " + jsonRe);
						
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
			       
			    },
			    error: function(cdata) {
			    	console.log("fE"); 
			    }
			});
		}else if(dataCh == 'sta'){
			$('#chart').empty();
			$("#grid").show();
			console.log("데이터 선택 밴경: "+ dataCh);
			
		}
	}
</script>
</body>
</html>