package com.shallwecode.user.entity

import com.shallwecode.common.exception.entity.NoIdEntityException
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.Password
import javax.persistence.*

@Entity
class User(

    @Embedded var email: Email,
    var name: String,
    var nickname: String?,
    @Embedded var password: Password,
    var phoneNumber: String,
    var profileImage: String?,
    var githubUrl: String?,
    var blogUrl: String?,
    var deleted: Boolean
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var _id: Long? = null // jpa 용 식별자 할당 필드

    val id: Long
        get() = this._id ?: throw NoIdEntityException()



    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}