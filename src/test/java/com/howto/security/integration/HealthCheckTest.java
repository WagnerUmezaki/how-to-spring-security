package com.howto.security.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


class HealthCheckTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHealthCheck() throws Exception{
        // Given
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/actuator/health");
        // When
        final MvcResult response = mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        // Then
        Assertions.assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.getResponse().getContentAsString()).contains("\"UP\"");
    }
}
