<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="../pigeonAppServer/resources/js/jquery-1.9.1.min.js" ></script>
	<script type="text/javascript">
	
		function btn_login_onclick() {
			var jsondata = "{\"LOGIN_TYPE\":\"1\",\"USER_PHONE\":\"18612122121\",\"USER_PWD\":\"mxNlRA4bnv0wkxuP7KLbjOvR5p7t9p8Z+Px3lqI1uPsVQNGUQiueT2ZF50ezJfNmUrcfHbUUuarQlQ7BB8Il1A75Qoa3WPIabjH+KAfz8JG5HZdQRNbd9p5lzr8XDvGQhV/gzx/EP2mAKcT74HR/1Bb7p90PTBYhFasgywmbqds=\",\"LOGIN_NW\":\"1\",\"LOGIN_MAC\":\"28E347330053\",\"APP_TYPE\":\"IOS\"}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/login/loginin",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	            	//var result = evel(data);
	                alert(data);
	            }
			});
		}
	
		<!-- 测试注册接口 -->
		function btn_regedit_onclick(){
			//var jsondata = "{\"USER_PHONE\":18310060620,\"USER_PWD\":\"12sdf3\"}]";<!-- 调用接口所需传入的参数 -->
			var jsondata = "{\"USER_SEX\":\"1\",\"DOVECOTE_NAME\":\"名称\",\"LOGIN_NW\":\"123\",\"LOGIN_MAC\":\"00-16-3E-02-0B-93\",\"USER_PHONE\":\"18612345656\",\"USER_PWD\":\"qqqqqq\",\"FEED_PIGEON_YEAR\":\"3\"}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/service/userRegedit",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	            	//var result = evel(data);
	                alert(data);
	            }
			});
		}
		
		<!-- 测试添加信鸽接口 -->
		function btn_addPigeon_onclick() {
			var jsondata = "{\"TOKEN\":\"16B669214AE4E4FF9D7EF4D28FC43A5C\",\"USER_OBJ_ID\":\"402881e657fbdc680157fbe499f80000\",\"FOOT_RING_CODE\":\"10203013\",\"PIGEON_LINEAGE\":\"血统\",\"PLUMAGE_COLOR\":\"羽色\",\"PIGEON_BIRTHDAY\":\"2016-10-25 21:35:12\",\"PIGEN_SEX\":\"1\",\"EYE_SAND\":\"眼砂\",\"PIGEON_STATUS\":\"1\",\"PIGEON_SCORE\":\"赛绩\"}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/service/addPigeonInfo",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	            	var result = eval('(' + data + ')');  
	                alert(result);
	            }
			});
		}
		
		<!-- 测试添加鸽环接口 -->
		function btn_addRing_onclick() {
			var jsondata = "{\"TOKEN\":\"16B669214AE4E4FF9D7EF4D28FC43A5C\",\"USER_OBJ_ID\":\"402881e657fbdc680157fbe499f80000\",\"RING_CODE\":\"10203012\",\"MANUFACTURE_DATE\":\"2016-10-25 22:20:20\",\"OUTPUT_ADDRESS\":\"生产地点\",\"SPECIFICATION\":\"100\",\"START_DATE\":\"2016-10-25 21:35:12\",\"VALIDITY_PERIOD\":\"2016-10-25 21:35:12\",\"SERVICE_TERM\":\"2016-10-25 21:35:12\",\"RING_STATUS\":\"2\"}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/service/addRingInfo",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	            	var result = eval('(' + data + ')');  
	                alert("成功！" + result);
	            }
			});
		}
		
		<!-- 测试查询信鸽列表接口 -->
		function btn_getPigeonList_onclick() {
			var jsondata = "{\"TOKEN\":\"16B669214AE4E4FF9D7EF4D28FC43A5C\",\"USER_OBJ_ID\":\"402881e657fbdc680157fbe499f80000\"}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/service/getPigeonsWithList",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	            	var result = eval('(' + data + ')');  
	                alert("成功！" + result);
	            }
			});
		}
		
		<!-- 测试查询鸽环列表接口 -->
		function btn_getRingList_onclick() {
			var jsondata = "{\"TOKEN\":\"16B669214AE4E4FF9D7EF4D28FC43A5C\",\"USER_OBJ_ID\":\"0b60a9c3586b575e015876857bcb0068\"}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/service/getRingAndPigeonCodeWithList",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	            	var result = eval('(' + data + ')');  
	                alert("成功！" + result);
	            }
			});
		}
		
		<!-- 删除鸽环接口 -->
		function btn_deleteRing_onclick() {
			var jsondata = "{\"TOKEN\":\"16B669214AE4E4FF9D7EF4D28FC43A5C\",\"RING_OBJ_ID\":\"402881e657fc3ccb0157fc3e32e50000,402881e6580686a80158069304ee0004\"}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/service/deleteRingById",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	                alert("成功！" + result);
	            }
			});
		}
		
		function btn_pigeonBindRing_onclick() {
			var jsondata = "{\"TOKEN\":\"16B669214AE4E4FF9D7EF4D28FC43A5C\",\"PIGEON_OBJ_ID\":[\"402881e657fc27f70157fc29d6580000\",\"402881e657fc670a0157fc678b8f0000\"],\"RING_OBJ_ID\":[\"402881e65806b9a1015806bb2c760000\",\"402881e65806b9a1015806bb412c0002\"]}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/service/pigeonBingRingWithBatch",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	                alert("成功！" + result);
	            }
			});
		}
		
		//调用实时查询信鸽记录
		function btn_queryPigeonFlying_onclick() {
			var jsondata = "{\"TOKEN\":\"16B669214AE4E4FF9D7EF4D28FC43A5C\",\"LATEST_TIME\":\"\",\"PIGEON_OBJ_ID\":[\"0b60a9c3586b575e01586c215c880027\"]}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/service/getPigeonFlying",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	                alert("成功！" + result);
	            }
			});
		}
		
		function btn_timingSwitch_onclick() {
			var jsondata = "{\"TOKEN\":\"16B669214AE4E4FF9D7EF4D28FC43A5C\",\"RING_CODE\":\"659487254955607\",\"BOOTTIME\":\"0015\",\"SDTIME\":\"1330\"}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/service/timingSwitch",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	                alert("成功！" + result);
	            }
			});
		}
		
		function btn_getByRouteId_onclick() {
			var jsondata = "{\"ROUTE_ID\":\"0b60a9c35860c67e01586b3219ca01\",\"TOKEN\":\"16B669214AE4E4FF9D7EF4D28FC43A5C\"}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/route/getPigeonFlyingByRouteId",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	                alert("成功！" + result);
	            }
			});
		}
		
		function btn_startFlying_onclick() {
			var jsondata = "{\"TOKEN\":\"16B669214AE4E4FF9D7EF4D28FC43A5C\",\"PIGEON_OBJ_ID\":[\"0b60a9c35a36706a015a5e9791d9024a\", \"0b60a9c3583155015834695e0005\"]}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/service/startFlying",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	                alert("成功！" + result);
	            }
			});
		}
		
		function btn_endFlying_onclick() {
			var jsondata = "{\"TOKEN\":\"16B669214AE4E4FF9D7EF4D28FC43A5C\",\"PIGEON_OBJ_ID\":[\"0b60a9c3583155015832ded50004\", \"0b60a9c3583155015834695e0005\"]}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/service/endFlying",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	                alert("成功！" + result);
	            }
			});
		}
		
		function btn_finishFlying_onclick() {
			var jsondata = "{\"QUERY_DATE\":\"2016-11-16\",\"TOKEN\":\"16B669214AE4E4FF9D7EF4D28FC43A5C\",\"PIGEON_OBJ_ID\":\"0b60a9c35860c67e015866e3b43000ce\"}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/service/getPigeonHistoryFlying",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	                alert("成功！" + result);
	            }
			});
		}
		
		function btn_thriftPower_onclick() {
			var jsondata = "{\"POSITION_MODE\":3,\"TOKEN\":\"16B669214AE4E4FF9D7EF4D28FC43A5C\",\"RING_CODE\":\"659487254955607\",\"REPORTED_FREQ\":3}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/service/thriftPowerConfig",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	                alert("成功！" + result);
	            }
			});
		}
		
		function btn_getAppVersion_onclick() {
			var jsondata = "{\"APP_TYPE\":\"apk\"}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/service/getAppVersion",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	                alert("成功！" + result);
	            }
			});
		}
		
		function btn_getSonser_onclick() {
			var jsondata = "{\"TOKEN\":\"16B669214AE4E4FF9D7EF4D28FC43A5C\",\"RING_CODE\":\"869758001210114\"}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/service/getSensorByRingCode",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	                alert("成功！" + result);
	            }
			});
		}
		
		function btn_findPwd_onclick() {
			var jsondata = "{\"USER_PHONE\":\"18612122121\",\"USER_EMAIL\":\"feng.mingbao@foxmail.com\"}";
			var param = {params:jsondata};
			$.ajax({
				type:"GET",
	            url:"/pigeonAppServer/service/sendFindPwdMail",
	            dataType:"json",
	            contentType:"application/json",
	            data:param,
	            success:function(data){
	                alert("成功！" + result);
	            }
			});
		}
		
	</script>
	
  </head>
  
  <body>
  	<button id="btn_regedit" onclick="btn_login_onclick()">登陆</button><br/>
    <button id="btn_regedit" onclick="btn_regedit_onclick()">注册</button><br/>
    <button id="btn_regedit" onclick="btn_addPigeon_onclick()">新增信鸽</button><br/>
    <button id="btn_regedit" onclick="btn_addRing_onclick()">新增鸽环</button><br/>
    <button id="btn_regedit" onclick="btn_getPigeonList_onclick()">查询信鸽</button><br/>
    <button id="btn_regedit" onclick="btn_getRingList_onclick()">查询鸽环</button><br/>
    <button id="btn_regedit" onclick="btn_deleteRing_onclick()">删除鸽环</button><br/>
    <button id="btn_regedit" onclick="btn_pigeonBindRing_onclick()">信鸽与鸽环绑定</button><br/>
    <button id="btn_regedit" onclick="btn_queryPigeonFlying_onclick()">调用实时查询信鸽记录</button><br/>
    <button id="btn_regedit" onclick="btn_timingSwitch_onclick()">定时开关机</button><br/>
    <button id="btn_regedit" onclick="btn_thriftPower_onclick()">省电模式设置</button><br/>
    <button id="btn_regedit" onclick="btn_getByRouteId_onclick()">根据行程查询信鸽飞行记录</button><br/>
    <button id="btn_regedit" onclick="btn_startFlying_onclick()">开始飞行</button><br/>
    <button id="btn_regedit" onclick="btn_endFlying_onclick()">结束飞行</button><br/>
    <button id="btn_regedit" onclick="btn_finishFlying_onclick()">调用查询历史信鸽记录</button><br/>
    <button id="btn_regedit" onclick="btn_getAppVersion_onclick()">获取APK版本</button><br/>
    <button id="btn_regedit" onclick="btn_getSonser_onclick()">获取设备气压测试数据</button><br/>
    <button id="btn_regedit" onclick="btn_findPwd_onclick()">密码找回</button><br/>

    <!-- <table style="margin: 25px auto;" border="0" cellspacing="0" cellpadding="0" width="648" align="center">
		<tbody>
			<tr><td style='color:#40AA53;'><h1 style='margin-bottom:10px;'>詹森019</h1></td></tr>
			<tr>
				<td style="border-left: 1px solid #D1FFD1; padding: 20px 20px 0px; background: none repeat scroll 0% 0% #ffffff; border-top: 5px solid #40AA53; border-right: 1px solid #D1FFD1;">
					<p>你好</p>
				</td>
			</tr>
			<tr>
				<td style="border-left: 1px solid #D1FFD1; padding: 10px 20px; background: none repeat scroll 0% 0% #ffffff; border-right: 1px solid #D1FFD1;">
					<p style="font-weight:bold">请点击下面链接进行密码重置<br/><br/><a href="https://www.oschina.net/home/reset-pwd2?email=507495957@qq.=com&key=33ab2c10ecd472d3151841e783edd22b2808721f">https://www.oschina.net=/home/reset-pwd2?email=507495957@qq.com&key=33ab2c10ecd472d3151841e783e=dd22b2808721f</a></p>
				</td>
			</tr>
			<tr>
				<td style="border-bottom: 1px solid #D1FFD1; border-left: 1px solid #D1FF=D1; padding: 0px 20px 20px; background: none repeat scroll 0% 0% #ffffff; b=order-right: 1px solid #D1FFD1;">
					<hr style='color:#ccc;'/>
					<p style='color:#060;font-size:9pt;'>想了解更多信息，请访问&nbsp;<a href="http://www.oschina.net" target="_blank">http://www.oschina.net</a></p>
				</td>
			</tr>
		</tbody>
	</table> -->
  </body>
</html>
