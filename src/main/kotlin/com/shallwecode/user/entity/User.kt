package com.shallwecode.user.entity

import com.shallwecode.user.entity.embeddable.Email
import com.shallwecode.user.entity.embeddable.Password
import com.shallwecode.user.entity.embeddable.PhoneNumber
import java.time.LocalDateTime
import javax.persistence.*
import javax.persistence.FetchType.LAZY

@Table(name = "user")
@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    var id: Long? = null,

    @Embedded var email: Email,
    var name: String,
    var nickname: String? = null,
    @Embedded var password: Password,
    @Embedded var phoneNumber: PhoneNumber,
    var profileImageUrl: String? = null,
    var githubUrl: String? = null,
    var blogUrl: String? = null,
    var deleted: Boolean = false,

    @ElementCollection(fetch = LAZY)
    @CollectionTable(
        name = "joined_project",
        joinColumns = [JoinColumn(name = "user_id")]
    )
    var joinedProjects: MutableList<JoinedProject> = mutableListOf(),


    @Column(name = "create_datetime", updatable = false)
    val createDateTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "update_datetime", updatable = true)
    var updateDateTime: LocalDateTime = LocalDateTime.now(),
) {
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