package com.shallwecode.project.entity

import com.shallwecode.common.exception.entity.NotHasIdEntityException
import com.shallwecode.user.entity.User
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
class Project(
    val status: ProjectStatus,
    val title: String,
    val description: String,

    @ManyToOne
    val createdUser: User,


    @OneToMany
    val techStackList: List<TechStack>,
    val gitUrl: String,
    val createDateTime: LocalDateTime,
    val updateDateTime: LocalDateTime,
) {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    var _id: Long? = null

    val id: Long
        get() = this._id
            ?: throw NotHasIdEntityException()

}