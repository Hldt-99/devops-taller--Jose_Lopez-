package com.devops.taller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/tasks/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("API REST DevOps Taller Java funcionando correctamente"));
    }

    @Test
    public void testGetAllTasks() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testCreateTask() throws Exception {
        String jsonPayload = "{\"title\": \"Nueva tarea de prueba Java\"}";
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Nueva tarea de prueba Java"));
    }
}
