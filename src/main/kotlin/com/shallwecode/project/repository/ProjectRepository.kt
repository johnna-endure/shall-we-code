package com.shallwecode.project.repository

import com.shallwecode.project.entity.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface ProjectRepository : JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    @Query(
        "select p from Project p " +
                "left join fetch p.joinedUsers " +
                "where p.id = :id"
    )
    fun findProjectWithJoinedUsers(id: Long): Project?

    @Query(
        "select p from Project p " +
                "left join fetch p.techStacks " +
                "where p.id = :id"
    )
    fun findProjectWithTechStacks(id: Long): Project?

}