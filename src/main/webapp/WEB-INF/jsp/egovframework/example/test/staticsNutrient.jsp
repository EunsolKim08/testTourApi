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
<script>
	function filterSearch(){
		console.log("필터기준 조회");
		
		var groupCd = document.staticSearch.grpCd.value;
		console.log("그룹코드: " + groupCd );
		var nutrientCd =  document.staticSearch.nCd.value;
		console.log("필수 기준 코드1: "+ nutrientCd);
		var searchWord = document.staticSearch.searchWord.value;
		console.log("필수 기준 코드2: "+ searchWord);
	}
	function changeVa(){
		console.log("데이터 출력 값 변경");
	}
</script>
</head>
<body>
	<h2 style="margin:100px;">영양성분 3가지방법 통계 분석</h2>
	
	
	<div style="margin:100px; font-size:20px;">
	| 필수입력<br/><br/>
		<form name="staticSearch" id="staticSearch">
			구분: 
			<select name="grpCd" id="grpCd" style="font-size:20px; margin-right:20px;">
				<option value="">-- 선택 --</option>
				<option value="A0005">영양성분 검색</option>
			</select>
			필수1:
			<select name="nCd" style="font-size:20px; margin-right:20px;">
				<option value="">--전체--</option>
				<option value="N01">칼로리</option>
				<option value="N02">탄수화물</option>
				<option value="N03">단백질</option>
				<option value="N04">지방</option>
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
			<input type="radio" name="dataPrint" value="sta" onchange="changeVa()" />차트
		</form>
		<br/><br/>
	</div>
	<div id="grid"></div>
</body>
</html>