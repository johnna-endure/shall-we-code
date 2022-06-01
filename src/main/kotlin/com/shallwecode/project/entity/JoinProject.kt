package com.shallwecode.project.entity

import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Table(name = "join_project")
@Entity
class JoinProject(
    @EmbeddedId
    var id: JoinProjectId
)



