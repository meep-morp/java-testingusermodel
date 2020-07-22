package com.lambdaschool.usermodel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import com.lambdaschool.usermodel.services.UserService;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private List<User> users;

    @Before
    public void setUp() throws Exception {
        users = new ArrayList<>();

        Role r1 = new Role("admin");
        r1.setRoleid(1);
        Role r2 = new Role("user");
        r2.setRoleid(2);
        Role r3 = new Role("data");
        r3.setRoleid(3);

        // admin, data, user
        User u1 = new User("admin",
                "password",
                "admin@lambdaschool.local");
        u1.getRoles().add(new UserRoles(u1, r1));
        u1.getRoles().add(new UserRoles(u1, r2));
        u1.getRoles().add(new UserRoles(u1, r3));
        u1.getUseremails()
                .add(new Useremail(u1,
                        "admin@email.local"));
        u1.getUseremails().get(0).setUseremailid(1);
        u1.setUserid(1);

        // data, user
        User u2 = new User("cinnamon",
                "1234567",
                "cinnamon@lambdaschool.local");
        u2.getRoles().add(new UserRoles(u2, r2));
        u2.getRoles().add(new UserRoles(u2, r3));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "hops@mymail.local"));
        u2.getUseremails().get(0).setUseremailid(3);
        u2.setUserid(2);

        // user
        User u3 = new User("barnbarn",
                "ILuvM4th!",
                "barnbarn@lambdaschool.local");
        u3.getRoles().add(new UserRoles(u3, r2));
        u3.getUseremails()
                .add(new Useremail(u3,
                        "barnbarn@email.local"));
        u3.getUseremails().get(0).setUseremailid(6);
        u3.setUserid(3);

        User u4 = new User("puttat",
                "password",
                "puttat@school.lambda");
        u4.getRoles().add(new UserRoles(u4, r2));
        u4.setUserid(4);

        User u5 = new User("misskitty",
                "password",
                "misskitty@school.lambda");
        u5.getRoles().add(new UserRoles(u5, r2));
        u5.setUserid(5);

        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
        users.add(u5);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listAllUsers() throws Exception{
        String apiUrl = "/users/users";
        Mockito.when(userService.findAll()).thenReturn(users);
        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(users);
        assertEquals(er, tr);
    }

    @Test
    public void getUserById() throws Exception{
        String apiUrl = "/users/user/2";
        Mockito.when(userService.findUserById(2)).thenReturn(users.get(1));
        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(users.get(1));
        assertEquals(er ,tr);
    }

    @Test
    public void getUserByName() throws Exception{
        String apiUrl = "/users/user/name/cinnamon";
        Mockito.when(userService.findByName("cinnamon")).thenReturn(users.get(1));
        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(users.get(1));
        assertEquals(er, tr);
    }

    @Test
    public void getUserLikeName() throws Exception{
        String apiUrl = "/users/user/name/like/cin";
        Mockito.when(userService.findByNameContaining("cin")).thenReturn(users);
        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl).accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(users);
        assertEquals(er, tr);
    }

    @Test
    public void addNewUser() throws Exception{
        String apiUrl = "/users/user";

        String username= "notadmin";
        User u2 = new User(username,
                "1234567",
                "adrian@lambdaschool.testt");
        u2.getUseremails()
                .add(new Useremail(u2,
                        "cinnamon@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "hops@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "bunny@email.local"));
        u2.setUserid(110);

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(u2);
        Mockito.when(userService.save(any(User.class))).thenReturn(u2);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(userString);
        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateFullUser() throws Exception {
        String apiUrl = "/users/user/10";

        String username= "adminn";
        User u2 = new User(username,
                "1234567",
                "adrian@lambdaschool.testst");
        u2.getUseremails()
                .add(new Useremail(u2,
                        "cinnamon@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "hops@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "bunny@email.local"));
        u2.setUserid(110);

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(u2);

        Mockito.when(userService.save(any(User.class))).thenReturn(u2);
        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(userString);
        mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateUser() throws Exception{
        String apiUrl = "/users/user/10";

        String username= "adminn";
        User u2 = new User(username,
                "1234567",
                "adrian@lambdaschool.testst");
        u2.setUserid(3);

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(u2);

        Mockito.when(userService.save(any(User.class))).thenReturn(u2);
        RequestBuilder rb = MockMvcRequestBuilders.patch(apiUrl).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(userString);
        mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteUserById() throws Exception{
        String apiUrl = "/users/user/10";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl);
        mockMvc.perform(rb).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }
}