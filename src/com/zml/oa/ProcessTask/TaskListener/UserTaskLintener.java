package com.zml.oa.ProcessTask.TaskListener;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zml.oa.service.activiti.ProcessServiceImp;

/**
 * 动态用户任务分配
 * @author ZML
 *
 */
@Component
@Transactional
public class UserTaskLintener implements TaskListener {
	private static final Logger logger = Logger.getLogger(UserTaskLintener.class);
    @Autowired
    protected RepositoryService repositoryService;
    
	@Override
	public void notify(DelegateTask delegateTask) {

		//现在写死了，以后完善，用UserTask表来维护 流程 和用户 之间的关系，在流程图中手动设置待办用户或组
		
		String taskDefinitionKey = delegateTask.getTaskDefinitionKey();		//directorAudit
		logger.info("&&&&&&&&&&&&&taskDefinitionKey: "+taskDefinitionKey);
//		String processDefinitionId = delegateTask.getProcessDefinitionId();	//com.zml.oa.vacation:8:30012
//		
//		ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
//		String processDefinitionName = processDefinition.getName();	//请假流程
//		String processDefinitionKey = processDefinition.getKey();	//com.zml.oa.vacation
		
		if("directorAudit".equals(taskDefinitionKey)){
			delegateTask.addCandidateGroup("director");
		}else if("hrAudit".equals(taskDefinitionKey)){
			delegateTask.addCandidateGroup("hr");
		}else if("managerAudit".equals(taskDefinitionKey)){
			delegateTask.addCandidateGroup("manager");
		}else if("financeAudit".equals(taskDefinitionKey)){
			delegateTask.addCandidateGroup("finance");
		}else if("employeeAudit".equals(taskDefinitionKey)){
			delegateTask.addCandidateGroup("employee");
		}else if("bossAudit".equals(taskDefinitionKey)){
			delegateTask.setAssignee("boss");
		}
	}
}