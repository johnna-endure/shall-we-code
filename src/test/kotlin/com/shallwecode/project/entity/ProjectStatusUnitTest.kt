package com.shallwecode.project.entity

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProjectStatusUnitTest {

    @Test
    fun `RECRUITING name 체크`() {
        assertThat(ProjectStatus.RECRUITING.name).isEqualTo("RECRUITING")
    }

    @Test
    fun `COMPLETED name 체크`() {
        assertThat(ProjectStatus.COMPLETED.name).isEqualTo("COMPLETED")
    }

}