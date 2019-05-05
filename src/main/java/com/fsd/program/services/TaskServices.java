/**
 * 
 */
package com.fsd.program.services;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.program.entity.ParentTask;
import com.fsd.program.entity.Project;
import com.fsd.program.entity.Task;
import com.fsd.program.entity.User;
import com.fsd.program.repo.ParentTaskRepository;
import com.fsd.program.repo.ProjectRepository;
import com.fsd.program.repo.TaskRepository;
import com.fsd.program.repo.UserRepository;

/**
 * @author mmurugesan1
 *
 */
@RestController
@RequestMapping("/task")
public class TaskServices {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ParentTaskRepository parentTaskRepository;

	@RequestMapping("/getTasks")
	public List<Task> getTasks() {

		List<Task> tasks = taskRepository.findAll();

		for (Task task : tasks) {
			String parentId = task.getParentId();
			if (parentId != null) {
				ParentTask parentTask = parentTaskRepository.findById(parentId).get();
				if (parentTask != null) {
					task.setParentTask(parentTask.getParentTask());
				}
			}
			User user = userRepository.findById(task.getUserId()).get();
			if (user == null) {
				throw new RuntimeException("Not a valid user request");
			}

			Project project = projectRepository.findById(task.getProjectId()).get();
			if (project == null) {
				throw new RuntimeException("Not a valid project request");
			}
			task.setProjectName(project.getProjectName());
			task.setUserName(user.getFirstName() + " " + user.getLastName());
		}

		return tasks;
	}

	@RequestMapping("/getParentTasks")
	public List<ParentTask> getParentTasks() {
		return parentTaskRepository.findAll();
	}

	@RequestMapping("/addUpdate")
	public List<Task> addUpdateTask(@RequestBody Map<String, String> requestMap) throws ParseException {
		taskRepository.save(updateTaskEntity(requestMap));
		return getTasks();
	}
	
	@RequestMapping("/endTask")
	public List<Task> endTask(@RequestBody Map<String, String> requestMap) throws ParseException {
		Task taskEntity = updateTaskEntity(requestMap);
		taskEntity.setStatus("Completed");
		taskRepository.save(taskEntity);
		return getTasks();
	}

	@RequestMapping("/deleteTask")
	public List<Task> deleteTask(@RequestBody Task task) {
		taskRepository.delete(task);
		return taskRepository.findAll();
	}
	
	public Task updateTaskEntity(Map<String, String> requestMap) {
		Task taskEntity = new Task();
		ParentTask parentTaskEntity = new ParentTask();
		taskEntity.setId(requestMap.get("id"));
		taskEntity.setTask(requestMap.get("task"));
		taskEntity.setEndDate(requestMap.get("endDate"));
		taskEntity.setStartDate(requestMap.get("startDate"));
		String parentId = requestMap.get("parentId");
		if ("true".equalsIgnoreCase(requestMap.get("isParentTask"))) {
			parentTaskEntity.setId(parentId);
			parentTaskEntity.setTaskId(requestMap.get("id"));
			parentTaskEntity.setParentTask(requestMap.get("task"));
			parentTaskRepository.save(parentTaskEntity);
		}
		taskEntity.setParentId(parentId);
		taskEntity.setStatus(requestMap.get("status"));
		taskEntity.setPriority(Integer.parseInt(requestMap.get("priority")));

		taskEntity.setProjectId(requestMap.get("projectId"));

		taskEntity.setUserId(requestMap.get("userId"));
		return taskEntity;
	}

}
