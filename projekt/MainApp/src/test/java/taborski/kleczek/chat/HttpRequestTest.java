package taborski.kleczek.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import taborski.kleczek.chat.controller.ChatController;
import taborski.kleczek.chat.controller.IHistoryAppClient;
import taborski.kleczek.chat.controller.IUserAppClient;
import taborski.kleczek.chat.entity.History;
import taborski.kleczek.chat.entity.User;
import taborski.kleczek.chat.model.Message;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ChatController.class)
public class HttpRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IUserAppClient userAppClient;

    @MockBean
    private IHistoryAppClient historyAppClient;

    @Test
    public void shouldReturnHistory() throws Exception {
        when(historyAppClient.getHistory()).thenReturn(this.getHistory());

        User user = new User();
        user.setId(1l);
        user.setName("Mock name");
        user.setPassword("Mock password");
        when(userAppClient.getUser(1l)).thenReturn(user);


        this.mockMvc.perform(get("/api/history")).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void shouldReturnEmptyHistory() throws Exception {
        when(historyAppClient.getHistory()).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/api/history")).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldLoginUser() throws Exception {

        User user = new User();
        user.setId(1l);
        user.setName("Mock name");
        user.setPassword("Mock password");
        when(userAppClient.loginUser(user)).thenReturn(user);


        this.mockMvc.perform(post("/api/login").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    public void shouldNotLoginUser() throws Exception {

        User user = new User();
        user.setId(1l);
        user.setName("Mock name");
        user.setPassword("Mock password");
        when(userAppClient.loginUser(user)).thenThrow(NullPointerException.class);


        this.mockMvc.perform(post("/api/login").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))).andDo(print())
                .andExpect(status().isBadRequest());
    }

//    @Test
//    public void shouldGetUser() throws Exception {
//
//        User user = new User();
//        user.setId(1l);
//        user.setName("Mock name");
//        user.setPassword("Mock password");
//        when(userAppClient.getUser(1l)).thenReturn(user);
//
//
//        this.mockMvc.perform(get("/api/1")).andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", notNullValue()));
//    }
//
//    @Test
//    public void shouldNotGetUser() throws Exception {
//        when(userAppClient.getUser(1l)).thenThrow(NullPointerException.class);
//        this.mockMvc.perform(post("/api/login")).andDo(print())
//                .andExpect(status().isBadRequest());
//    }

    @Test
    public void shouldRegisterUser() throws Exception {

        User user = new User();
        user.setId(1l);
        user.setName("Mock name");
        user.setPassword("Mock password");
        when(userAppClient.registerUser(user)).thenReturn(user);


        this.mockMvc.perform(post("/api/register").contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    public void shouldNotRegisterUser() throws Exception {
        User user = new User();
        user.setId(1l);
        user.setName("Mock name");
        user.setPassword("Mock password");
        when(userAppClient.registerUser(user)).thenThrow(NullPointerException.class);
        this.mockMvc.perform(post("/api/register")).andDo(print())
                .andExpect(status().isBadRequest());
    }


    public List<History> getHistory() {
        List<History> fullHistory = new ArrayList();
        History history = new History();
        history.setId(1l);
        history.setMessage("Mock message");
        history.setType(Message.Type.JOIN.toString());
        history.setSenderId(1l);

        fullHistory.add(history);

        return fullHistory;
    }


}
