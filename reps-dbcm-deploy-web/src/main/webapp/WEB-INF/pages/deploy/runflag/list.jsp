<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>部署项目进度列表</title>
	<reps:theme/>
</head>
<body>
<reps:container layout="true">
	<reps:panel title="部署项目进度列表" id="top" dock="top" method="post" action="list.mvc" formId="queryForm">
	</reps:panel>
	<reps:panel id="mybody" dock="center">
		<reps:grid id="runFlagList" items="${list}" var="runFlag"  flagSeq="false">
			<reps:gridrow>
				<reps:gridfield title="部署名称" width="15" align="center">${runFlag.cmDeployUpdatePlan.cmDeploy.deployName}</reps:gridfield>
				<reps:gridfield title="任务id" width="15" align="center">${runFlag.planId}</reps:gridfield>
				<reps:gridfield title="更新时间" width="15" align="center"><fmt:formatDate value="${runFlag.updateTime }" pattern="yyyy-MM-dd HH:mm:ss"/></reps:gridfield>
				<reps:gridfield title="正在执行的内容" width="15" align="center">${runFlag.updateCont}</reps:gridfield>
				<reps:gridfield title="运行结果标识" width="15" align="center">${runFlag.updateFlag}</reps:gridfield>
				<reps:gridfield title="执行情况" width="40" align="center">${runFlag.updateLog}</reps:gridfield>
				<reps:gridfield title="操作" width="10">
					<reps:ajax cssClass="delete-table" messageCode="manage.action.delete" confirm="您确定要删除所选行吗？"
							redirect="list.mvc" url="delete.mvc">
						</reps:ajax>
				</reps:gridfield>
			</reps:gridrow>
		</reps:grid>
	</reps:panel>
</reps:container>
</body>
</html>
