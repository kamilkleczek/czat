package taborski.dawid.history;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import taborski.dawid.history.model.History;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class HttpRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldReturnEmptyArray() throws Exception {
        this.mockMvc.perform(get("/api/history")).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldAddHistoryAddHistoryShouldNotBeEmpty() throws Exception {
        History newHistory = new History();
        newHistory.setMessage("test message");
        newHistory.setSenderId(1l);
        newHistory.setType("type");

        this.mockMvc.perform(post("/api/history").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newHistory))).andDo(print())
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", notNullValue()));

        this.mockMvc.perform(get("/api/history")).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
    }
}
