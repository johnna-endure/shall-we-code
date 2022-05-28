package com.shallwecode.project.entity

import com.shallwecode.common.exception.entity.EmptyIdEntityException
import com.shallwecode.user.entity.User
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.FetchType.LAZY
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "join_project")
class JoinProject(
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "project_id")
    val project: Project,
    val createDateTime: LocalDateTime,
    val updateDateTime: LocalDateTime
) {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "join_project_id")
    var _id: Long? = null

    val id: Long
        get() = this._id ?: throw EmptyIdEntityException()

}