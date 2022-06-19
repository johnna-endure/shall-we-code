package com.shallwecode.project.entity

import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "project")
class Project(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "project_id")
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, updatable = true)
    var status: ProjectStatus,

    @Column(name = "title", length = 100, nullable = false, updatable = true)
    var title: String,

    @Column(name = "description", nullable = true, updatable = true)
    var description: String,

    @Column(name = "create_user_id", nullable = false, updatable = false)
    val createdUserId: Long,

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "joined_user",
        joinColumns = [JoinColumn(name = "project_id")]
    )
    var joinedUsers: MutableList<JoinedUser> = mutableListOf(),

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "tech_stack",
        joinColumns = [JoinColumn(name = "project_id")]
    )
    var techStacks: MutableList<TechStack> = mutableListOf(),

    @Column(name = "github_url", nullable = true, updatable = true)
    var githubUrl: String? = null,

    @Column(name = "create_datetime", nullable = false, updatable = false)
    val createDateTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "update_datetime", nullable = false, updatable = true)
    var updateDateTime: LocalDateTime = LocalDateTime.now(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Project

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
