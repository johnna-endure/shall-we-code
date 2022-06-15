package com.shallwecode.project.repository

import com.shallwecode.project.entity.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface ProjectRepository : JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    @Query(
        "select p from Project p " +
                "join fetch p.joinedUsers " +
                "where p._id = :id"
    )
    fun findProjectJoinFetchJoinedUsers(id: Long): Project?

    // TODO 테스트 필요
    @Query(
        "select p from Project p " +
                "join fetch p.techStacks " +
                "where p._id = :id"
    )
    fun findProjectJoinFetchTechStacks(id: Long): Project?
}