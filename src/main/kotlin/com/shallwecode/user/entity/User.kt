package com.shallwecode.user.entity

import com.shallwecode.common.exception.entity.EmptyIdEntityException
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.Password
import com.shallwecode.user.entity.embeddable.PhoneNumber
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class User(
    @Embedded val email: Email,
    val name: String,
    val nickname: String? = null,
    @Embedded val password: Password,
    @Embedded val phoneNumber: PhoneNumber,
    val profileImageUrl: String? = null,
    val githubUrl: String? = null,
    val blogUrl: String? = null,
    val deleted: Boolean = false,

    val createDateTime: LocalDateTime = LocalDateTime.now(),
    var updateDateTime: LocalDateTime = LocalDateTime.now()
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var _id: Long? = null

    val id: Long
        get() = this._id
            ?: throw EmptyIdEntityException()


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