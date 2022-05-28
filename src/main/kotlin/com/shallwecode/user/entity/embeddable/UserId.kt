package com.shallwecode.user.entity.embeddable

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY

@Embeddable
class UserId(

    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    var value: Long
) : Serializable


