package com.shallwecode.project.entity

import com.shallwecode.common.exception.entity.NotHasIdEntityException
import javax.persistence.*

@Entity
@Table(name = "tech_stack")
class TechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "techstack_id")
    var _id: Long? = null

    val id: Long
        get() = this._id ?: throw NotHasIdEntityException()

}