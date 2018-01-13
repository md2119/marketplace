package com.intuit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.intuit.api.ProjectStatus;
import com.intuit.base.MarketplaceProject;

@Transactional
public interface ProjectRepository extends JpaRepository<MarketplaceProject, Long>{
	MarketplaceProject findById(Long id);
	List<MarketplaceProject> findByStatus(ProjectStatus status);

}
