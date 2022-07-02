package com.shallwecode.project.entity

import javax.persistence.Embeddable

@Embeddable
data class TechStack(
    val name: String
) : java.io.Serializable