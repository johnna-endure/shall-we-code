package com.shallwecode.project.entity

import com.shallwecode.common.exception.entity.NotHasIdEntityException
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id

@Entity
class Project(
    val title: String,
    val description: String,

) {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    var _id: Long? = null

    val id: Long
        get() = this._id
            ?: throw NotHasIdEntityException("${this::class.simpleName} 엔티티의 id가 할당되지 않았습니다.")

}