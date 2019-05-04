/**
 * 
 */
package com.fsd.program.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fsd.program.entity.Project;

/**
 * @author kj
 *
 */
public interface ProjectRepository extends MongoRepository<Project, String> {

	public Project findByProjectId(String projectId);

}
