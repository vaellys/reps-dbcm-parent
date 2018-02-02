<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>部署项目详情</title>
	<reps:theme />
</head>
<body>
<reps:container>
	<reps:panel id="first" title="部署项目详情" dock="top" action="show.mvc" formId="xform" validForm="true" >
		<reps:formcontent>
			<reps:formfield label="项目代码" labelStyle="width:17%" textStyle="width:15%">
				<reps:input name="prjCode" maxLength="30" readonly="true">${cmDeploy.prjCode }</reps:input>
			</reps:formfield>
			<reps:formfield label="部署名称" labelStyle="width:10%" textStyle="width:40%">
				<reps:input name="deployName" maxLength="30" readonly="true">${cmDeploy.deployName }</reps:input>
			</reps:formfield>
			<reps:formfield label="部署元数据" fullRow="true">
				<reps:input name="metaData" maxLength="1000" multiLine="true" style="width:535px;height:200px" readonly="true">${cmDeploy.metaData }</reps:input>
			</reps:formfield>
			<reps:formfield label="维护序号" fullRow="true">
				<reps:input name="whxh" dataType="integernum" readonly="true">${cmDeploy.whxh }</reps:input>
			</reps:formfield>
		</reps:formcontent>
		<reps:formbar>
			<reps:button cssClass="btn_back" type="button" onClick="back()"
					messageCode="manage.action.return" />
		</reps:formbar>
	</reps:panel>
</reps:container>
<script type="text/javascript">
	var skip = function(data) {
		messager.message(data, function(){ back(); });
	};
	
	function back() {
		window.location.href= "list.mvc";
	}
	
</script>
</body>
</html>