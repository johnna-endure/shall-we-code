package com.shallwecode.user.entity

import com.shallwecode.common.exception.entity.NotHasIdEntityException
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.Password
import com.shallwecode.user.entity.embeddable.PhoneNumber
import javax.persistence.*

@Entity
class User(
    @Embedded var email: Email,
    var name: String,
    var nickname: String? = null,
    @Embedded var password: Password,
    @Embedded var phoneNumber: PhoneNumber,
//    var phoneNumber: String,
    var profileImage: String? = null,
    var githubUrl: String? = null,
    var blogUrl: String? = null,
    var deleted: Boolean = false
) {
    /**
     * jpa 용 식별자 필드입니다.
     * 이 필드로 id에 접근해도 상관없지만 되도록이면 id가 다 안전합니다.
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var _id: Long? = null

    val id: Long
        get() = this._id
            ?: throw NotHasIdEntityException("${this::class.simpleName} 엔티티의 id가 할당되지 않았습니다.")



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