package com.shallwecode.user.entity.embeddable

import javax.persistence.Embeddable
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Embeddable
class LongId(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long
    ) : java.io.Serializable {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LongId

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}