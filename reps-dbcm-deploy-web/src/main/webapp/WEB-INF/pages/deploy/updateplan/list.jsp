<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title></title>
	<reps:theme />
</head>
<body>
<reps:container layout="true">
	<reps:panel id="mytop" dock="top" action="${ctx}/reps/deploy/updateplan/executeup.mvc" method="post" formId="queryForm">
		<reps:formcontent parentLayout="true" style="width:80%;">
			<reps:formfield label="订单号" labelStyle="width:20%;" textStyle="width:27%;">
				<reps:input name="orderNo">${query.orderNo}</reps:input>
			</reps:formfield>
			<reps:formfield label="订单状态" labelStyle="width:20%;" textStyle="width:27%;">
				<reps:select name="status" jsonData="{'':'', '0':'未处理', '1':'已处理', '2':'已取消', '3':'已完成'}">${query.status}</reps:select>
			</reps:formfield>
		</reps:formcontent>
		<reps:querybuttons>
			<reps:ajaxgrid messageCode="manage.button.query" formId="queryForm"  gridId="mygrid" callBack="setDisabled" cssClass="search-form-a"></reps:ajaxgrid>
		</reps:querybuttons>
		<reps:footbar>
		</reps:footbar>
	</reps:panel>
</reps:container>
<script type="text/javascript">
 	
</script>
</body>
</html>