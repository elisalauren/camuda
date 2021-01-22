package de.bamero;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.FilterService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.filter.Filter;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.TaskQuery;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableProcessApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private ProcessEngine processEngine;

	@EventListener
	private void processPostDeploy(PostDeployEvent event) {

		IdentityService identityService = processEngine.getIdentityService();
		User peter = identityService.newUser("peter");
		peter.setFirstName("peter");
		peter.setLastName("peter");
		peter.setPassword("peter");
		identityService.saveUser(peter);

		User hans = identityService.newUser("hans");
		hans.setFirstName("hans");
		hans.setLastName("hans");
		hans.setPassword("hans");
		identityService.saveUser(hans);

		Filter myTasksFilter = createMyTasksFilter();
		
		createAuthorizationForMyTasks(myTasksFilter, hans.getId());
		createAuthorizationForMyTasks(myTasksFilter, peter.getId());
		
		createAuthorizationForTasklist(peter.getId());
		createAuthorizationForTasklist(hans.getId());
	}

	private Filter createMyTasksFilter() {
		TaskService taskService = processEngine.getTaskService();
		TaskQuery taskQuery = taskService.createTaskQuery().taskAssigneeExpression("${ currentUser() }").active();

		FilterService filterService = processEngine.getFilterService();
		Filter myTasksFilter = filterService.newTaskFilter("My Tasks");
		myTasksFilter.setOwner("demo");
		myTasksFilter.setQuery(taskQuery);
		return filterService.saveFilter(myTasksFilter);
	}

	private void createAuthorizationForTasklist(String userId) {
		AuthorizationService authorizationService = processEngine.getAuthorizationService();
		Authorization auth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);

		auth.setUserId(userId);
		//  -OR-
		//auth.setGroupId("management");

		auth.setResource(Resources.APPLICATION);
		auth.setResourceId("tasklist");

		auth.addPermission(Permissions.ALL);
		authorizationService.saveAuthorization(auth);
	}

	private void createAuthorizationForMyTasks(Filter filter, String userId) {
		
		AuthorizationService authorizationService = processEngine.getAuthorizationService();
		Authorization auth = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);

		auth.setUserId(userId);
		//  -OR-
		//auth.setGroupId("management");

		auth.setResource(Resources.FILTER);
		auth.setResourceId(filter.getId());

		auth.addPermission(Permissions.READ);
		authorizationService.saveAuthorization(auth);
	}
}
