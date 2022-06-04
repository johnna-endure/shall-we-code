package com.shallwecode.project.repository

import com.shallwecode.project.entity.Project
import org.springframework.data.jpa.repository.JpaRepository

interface ProjectRepositoryUnitTest : JpaRepository<Project, Long> {
}