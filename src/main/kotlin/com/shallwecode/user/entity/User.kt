package com.shallwecode.user.entity

import com.shallwecode.common.exception.entity.EmptyIdEntityException
import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.Password
import com.shallwecode.user.entity.embeddable.PhoneNumber
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.CascadeType.MERGE
import javax.persistence.CascadeType.PERSIST
import javax.persistence.FetchType.LAZY

@Table(name = "user")
@Entity
class User(
    @Embedded var email: Email,
    var name: String,
    var nickname: String? = null,
    @Embedded var password: Password,
    @Embedded var phoneNumber: PhoneNumber,
    var profileImageUrl: String? = null,
    var githubUrl: String? = null,
    var blogUrl: String? = null,
    var deleted: Boolean = false,

    @OneToMany(
        fetch = LAZY,
        cascade = [PERSIST, MERGE],
        mappedBy = "id.userId",
        orphanRemoval = true
    )
    var joinedProjects: MutableList<JoinedProject> = mutableListOf(),

    @Column(name = "create_datetime", updatable = false)
    val createDateTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "update_datetime", updatable = true)
    var updateDateTime: LocalDateTime = LocalDateTime.now(),
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