package com.shallwecode.project.entity

import com.shallwecode.common.exception.entity.EmptyIdEntityException
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "project")
class Project(
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, updatable = true)
    var status: ProjectStatus,

    @Column(name = "title", nullable = false, updatable = true)
    var title: String,

    @Column(name = "description", nullable = true, updatable = true)
    var description: String,

    @Column(name = "create_user_id", nullable = false, updatable = false)
    val createdUser: Long,

    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.PERSIST, CascadeType.MERGE],
        mappedBy = "id.projectId"
    )
    var joinedUsers: MutableList<UserProject> = mutableListOf(),

    var githubUrl: String? = null,
    @Column(name = "create_datetime", updatable = false)
    val createDateTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "update_datetime", updatable = true)
    var updateDateTime: LocalDateTime = LocalDateTime.now(),
) {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "project_id")
    var _id: Long? = null

    val id: Long
        get() = this._id
            ?: throw EmptyIdEntityException()

}