package taborski.kleczek.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import taborski.kleczek.User.model.User;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void shouldNotLoginUser() throws Exception {
        User user = new User();
        user.setName("dawid");
        user.setPassword("dawid");
        this.mockMvc.perform(post("/api/user/login").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldRegisterUserAndLogin() throws Exception {
        User user = new User();
        user.setName("dawid");
        user.setPassword("dawid");
        this.mockMvc.perform(post("/api/user/register").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))).andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(post("/api/user/login").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetUser() throws Exception {
        User user = new User();
        user.setName("dawid");
        user.setPassword("dawid");
        this.mockMvc.perform(post("/api/user/register").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))).andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/api/user/1")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotGetUser() throws Exception {
        this.mockMvc.perform(get("/api/user/1")).andDo(print())
                .andExpect(status().isBadRequest());
    }
}
