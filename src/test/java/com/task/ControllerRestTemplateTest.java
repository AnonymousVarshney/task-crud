package com.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.task.dao.TaskDaoInterface;
import com.task.model.StatusEnum;
import com.task.model.Task;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
@ActiveProfiles("test")
public class ControllerRestTemplateTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private TaskDaoInterface mockRepository;


    @Before
    public void init() {
        om.registerModule(new JavaTimeModule());
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        Task task = new Task(1, "Task Name", "Task desc",
                StatusEnum.inprogress, LocalDate.now());
        when(mockRepository.returnTaskById(1)).thenReturn(task);

    }

    @Test
    public void find_taskId_OK() throws JSONException {

        String expected = "{" +
                "    \"id\": 1," +
                "    \"title\": \"Task Name\"," +
                "    \"description\": \"Task desc\"," +
                "    \"status\": \"inprogress\"," +
                "    \"deadline\": \"2023-01-26\"" +
                "}";

        ResponseEntity<String> response = restTemplate.getForEntity("/rest/tasks/get/1", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());

        JSONAssert.assertEquals(expected, response.getBody(), false);

       // verify(mockRepository, times(1)).findById(1L);

    }


    private static void printJSON(Object object) {
        String result;
        try {
            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
