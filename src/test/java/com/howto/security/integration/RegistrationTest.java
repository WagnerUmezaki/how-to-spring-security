package com.howto.security.integration;

import com.howto.security.controller.auth.request.RegisterRequest;
import com.howto.security.entity.AppUserEntity;
import com.howto.security.entity.AppUserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

class RegistrationTest extends BaseIntegrationTest{

    @Test
    void successfulRegistration() throws Exception{
        // Given
        final RegisterRequest registerRequest = RegisterRequest.builder()
                .email("teste@teste.com")
                .password("password")
                .nickname("abc")
                .build();

        // When
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJson(registerRequest));
        final MvcResult response = mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        // Then
        Assertions.assertThat(response.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        final AppUserEntity appUserEntity = appUserRepository.findByEmail("teste@teste.com").orElseThrow();
        Assertions.assertThat(appUserEntity.getId()).isNotNull();
        Assertions.assertThat(appUserEntity.getCode()).isNotNull();
        Assertions.assertThat(appUserEntity.getNickname()).isEqualTo("abc");
        Assertions.assertThat(appUserEntity.getPassword()).isNotNull();
        Assertions.assertThat(appUserEntity.getPassword()).isNotEqualTo("password");
        Assertions.assertThat(appUserEntity.getRole()).isEqualTo(AppUserRole.NORMAL);
    }
}
