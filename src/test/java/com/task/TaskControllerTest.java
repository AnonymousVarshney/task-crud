package com.task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.task.dao.TaskDaoInterface;
import com.task.model.StatusEnum;
import com.task.model.Task;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TaskControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

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
    public void find_TaskId_OK() throws Exception {

        mockMvc.perform(get("/rest/tasks/get/1"))
                /*.andDo(print())*/
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Task Name")))
                .andExpect(jsonPath("$.description", is("Task desc")));

        verify(mockRepository, times(1)).returnTaskById(1);

    }

    @Test
    public void find_allTask_OK() throws Exception {

        List<Task> tasks = Arrays.asList(
                new Task(1, "Task A", "Task desc",
                        StatusEnum.inprogress, LocalDate.now()),
                new Task(2, "Task B", "Task desc2",
                        StatusEnum.inprogress, LocalDate.now()));

        when(mockRepository.returnAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/rest/tasks/getall"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Task A")))
                .andExpect(jsonPath("$[0].description", is("Task desc")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Task B")))
                .andExpect(jsonPath("$[1].description", is("Task desc2")));

        verify(mockRepository, times(1)).returnAllTasks();
    }

    @Test
    public void find_taskIdNotFound_404() throws Exception {
        mockMvc.perform(get("/rest/tasks/get/5")).andExpect(status().isNotFound());
    }

    @Test
    public void save_task_OK() throws Exception {

        Task newTask = new Task(3, "test task", "test desc",
                StatusEnum.inprogress, LocalDate.now());
        when(mockRepository.createTask(any(Task.class))).thenReturn(newTask);

        mockMvc.perform(post("/rest/tasks/create")
                        .content(om.writeValueAsString(newTask))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.title", is("test task")))
                .andExpect(jsonPath("$.description", is("test desc")));

        verify(mockRepository, times(1)).createTask(any(Task.class));

    }

    @Test
    public void update_task_OK() throws Exception {

        Task updateTask = new Task(1, "test task", "test desc2",
                StatusEnum.inprogress, LocalDate.now());
        when(mockRepository.updateTask(any(Task.class))).thenReturn(updateTask);

        mockMvc.perform(put("/rest/tasks/update")
                        .content(om.writeValueAsString(updateTask))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test task")))
                .andExpect(jsonPath("$.description", is("test desc2")));


    }


    @Test
    public void delete_task_OK() throws Exception {

        when(mockRepository.deleteTaskById(1)).thenReturn("success");

        mockMvc.perform(delete("/rest/tasks/delete/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(mockRepository, times(1)).deleteTaskById(1);
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