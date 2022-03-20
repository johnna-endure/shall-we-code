package com.shallwecode.testconfig

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(MockitoExtension::class)
interface MockControllerTestConfig {
}