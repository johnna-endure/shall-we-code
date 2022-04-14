package com.shallwecode.project.entity

import com.shallwecode.common.exception.entity.NotHasIdEntityException
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class TechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var _id: Long? = null

    val id: Long
        get() = this._id ?: throw NotHasIdEntityException()

}