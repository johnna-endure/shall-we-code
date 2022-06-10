package com.shallwecode.project.repository

import com.shallwecode.project.entity.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ProjectRepository : JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
}