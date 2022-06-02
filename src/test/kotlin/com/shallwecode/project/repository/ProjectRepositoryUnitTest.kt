package com.shallwecode.project.repository

import com.shallwecode.project.entity.ProjectTable
import org.springframework.data.jpa.repository.JpaRepository

interface ProjectRepositoryUnitTest : JpaRepository<ProjectTable, Long> {
}