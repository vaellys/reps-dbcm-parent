<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>项目部署管理列表</title>
	<reps:theme/>
</head>
<body>
<reps:container layout="true">
	<reps:panel title="项目部署管理列表" id="top" dock="top" method="post" action="list.mvc" formId="queryForm">
		<input type="hidden" name="ids" />
		<reps:formcontent parentLayout="true" style="width:80%;">
			<reps:formfield label="部署名称" labelStyle="width:20%;" textStyle="width:27%;">
				<reps:input name="deployName" maxLength="30">${cmDeploy.deployName }</reps:input>
			</reps:formfield>
		</reps:formcontent>
		<reps:querybuttons>
			<reps:ajaxgrid messageCode="manage.button.query" formId="queryForm" gridId="deployList" cssClass="search-form-a"></reps:ajaxgrid>
		</reps:querybuttons>
		<reps:footbar>
			<%-- <reps:button cssClass="add-a" action="toadd.mvc?id=${reward.id }" messageCode="manage.action.add" value="新增"></reps:button> --%>
			<!-- <reps:ajax cssClass="batch-approval-a" confirm="确认批量发布吗?" beforeCall="checkPublishChecked" formId="queryForm" callBack="my" value="批量发布" />
			<reps:ajax cssClass="delete-a" confirm="确认批量删除吗?" beforeCall="checkDeleteChecked" formId="queryForm" redirect="list.mvc" value="批量删除" /> -->
		</reps:footbar>
	</reps:panel>
	<reps:panel id="mybody" dock="center">
		<reps:grid id="deployList" items="${list}" form="queryForm" var="deploy" pagination="${pager}" flagSeq="true">
			<reps:gridrow>
				<%-- <reps:gridcheckboxfield checkboxName="id" align="center" title="" width="5">${deploy.deployId}</reps:gridcheckboxfield> --%>
				<reps:gridfield title="部署ID" width="30" align="center">${deploy.deployId }</reps:gridfield>
				<reps:gridfield title="项目代码" width="20" align="center">${deploy.prjCode}</reps:gridfield>
				<reps:gridfield title="部署名称" width="20" align="center">${deploy.deployName}</reps:gridfield>
				<reps:gridfield title="维护序号" width="20" align="center">${deploy.whxh}</reps:gridfield>
				<reps:gridfield title="操作" width="30">
					<reps:button cssClass="detail-table" action="show.mvc?id=${deploy.deployId}" value="详细"></reps:button>
					<reps:button cssClass="modify-table" messageCode="manage.action.update" action="toedit.mvc?id=${deploy.deployId}"></reps:button>
					<reps:ajax cssClass="function-connect-table" value="执行部署" confirm="您确定要部署吗？"
						redirect="list.mvc" url="executedeploy.mvc?deployId=${deploy.deployId}">
					</reps:ajax>
				</reps:gridfield>
			</reps:gridrow>
		</reps:grid>
	</reps:panel>
</reps:container>
<script type="text/javascript">
	var my = function(data){
		window.location.href= "list.mvc";
	};
	
</script>
</body>
</html>
