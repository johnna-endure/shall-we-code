package com.shallwecode.project.entity

import javax.persistence.Embeddable

@Embeddable
class TechStack(
    val name: String
) : java.io.Serializable