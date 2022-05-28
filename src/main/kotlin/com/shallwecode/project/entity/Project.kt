package com.shallwecode.project.entity

import com.shallwecode.common.exception.entity.EmptyIdEntityException
import com.shallwecode.user.entity.User
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.FetchType.LAZY
import javax.persistence.GenerationType.IDENTITY

@Entity
@Table(name = "project")
class Project(
    val status: ProjectStatus,
    val title: String,
    val description: String,

    @ManyToOne(fetch = LAZY)
    val createdUser: User,

    @OneToMany(fetch = LAZY, mappedBy = "_id")
    val joinedUsers: List<JoinProject>,

    @OneToMany(fetch = LAZY, mappedBy = "_id")
    val techStackList: List<TechStack>,

    val gitUrl: String,
    val createDateTime: LocalDateTime,
    val updateDateTime: LocalDateTime,
) {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "project_id")
    var _id: Long? = null

    val id: Long
        get() = this._id
            ?: throw EmptyIdEntityException()

}