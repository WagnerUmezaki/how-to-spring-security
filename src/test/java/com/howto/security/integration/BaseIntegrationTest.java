package com.howto.security.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.howto.security.repository.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public abstract class BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected AppUserRepository appUserRepository;

    @BeforeEach
    void setup() {
        cleanDatabase();
    }

    protected String toJson(final Object obejct) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        mapper.registerModule(javaTimeModule);
        return mapper.writeValueAsString(obejct);
    }

    protected <T> T toObject(final String jsonAsString, final Class<T> clazz) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        mapper.registerModule(javaTimeModule);
        return mapper.readValue(jsonAsString, clazz);
    }

    protected <T> T toObject(final String jsonAsString, final TypeReference<T> typeReference) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        mapper.registerModule(javaTimeModule);
        return mapper.readValue(jsonAsString, typeReference);
    }

    private void cleanDatabase() {
        log.info("Cleaning database for next integration test");
        appUserRepository.deleteAll();
    }
}
