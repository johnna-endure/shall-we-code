package com.shallwecode.user.entity

import com.shallwecode.common.exception.entity.NotHasIdEntityException
import com.shallwecode.project.entity.JoinProject
import com.shallwecode.project.entity.Project
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
    val profileImage: String? = null,
    val githubUrl: String? = null,
    val blogUrl: String? = null,
    val deleted: Boolean = false,

    @OneToMany(fetch = FetchType.LAZY)
    val createdProjectsByMe: List<Project> = listOf(),

    @OneToMany(fetch = FetchType.LAZY)
    val joinedProjects: List<JoinProject> = listOf(),

    val createDateTime: LocalDateTime = LocalDateTime.now(),
    var updateDatetime: LocalDateTime = LocalDateTime.now()
) {
    /**
     * jpa 용 식별자 필드입니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var _id: Long? = null

    val id: Long
        get() = this._id
            ?: throw NotHasIdEntityException()


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