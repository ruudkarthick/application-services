/**
 * 
 */
package com.fsd.program.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fsd.program.entity.Task;

/**
 * @author kj
 *
 */
public interface TaskRepository extends MongoRepository<Task, String> {

	public Task findByTaskId(String taskId);

}
