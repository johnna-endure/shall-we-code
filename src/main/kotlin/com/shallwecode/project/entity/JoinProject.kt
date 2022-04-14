package com.shallwecode.project.entity

import com.shallwecode.common.exception.entity.NotHasIdEntityException
import com.shallwecode.user.entity.User
import javax.persistence.*

@Entity
class JoinProject(
    @ManyToOne
    @JoinColumn(name = "id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "")
    val project: Project
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var _id: Long? = null

    val id: Long
        get() = this._id ?: throw NotHasIdEntityException()


}