package com.shallwecode.project.entity

import javax.persistence.Embeddable

//@Entity
//@Table(name = "tech_stack")
@Embeddable
class TechStack(
    val name: String,
    val logoImageUrl: String
)