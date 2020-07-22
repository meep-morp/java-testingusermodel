package com.lambdaschool.usermodel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.usermodel.UserModelApplication;
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
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import static org.junit.Assert.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                classes = UserModelApplication.class)
@AutoConfigureMockMvc
public class UserControllerIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @LocalServerPort
    private int port;

    HttpHeaders headers = new HttpHeaders();
    TestRestTemplate restTemplate = new TestRestTemplate();


    @Test
    public void listAllUsers() throws Exception{
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/users/users"), HttpMethod.GET, entity, String.class);
        String expected = "[\n" +
                "    {\n" +
                "        \"userid\": 4,\n" +
                "        \"username\": \"admin\",\n" +
                "        \"primaryemail\": \"admin@lambdaschool.local\",\n" +
                "        \"useremails\": [\n" +
                "            {\n" +
                "                \"useremailid\": 5,\n" +
                "                \"useremail\": \"admin@email.local\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"useremailid\": 6,\n" +
                "                \"useremail\": \"admin@mymail.local\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"roles\": [\n" +
                "            {\n" +
                "                \"role\": {\n" +
                "                    \"roleid\": 1,\n" +
                "                    \"name\": \"ADMIN\"\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"role\": {\n" +
                "                    \"roleid\": 2,\n" +
                "                    \"name\": \"USER\"\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"role\": {\n" +
                "                    \"roleid\": 3,\n" +
                "                    \"name\": \"DATA\"\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"userid\": 7,\n" +
                "        \"username\": \"cinnamon\",\n" +
                "        \"primaryemail\": \"cinnamon@lambdaschool.local\",\n" +
                "        \"useremails\": [\n" +
                "            {\n" +
                "                \"useremailid\": 8,\n" +
                "                \"useremail\": \"cinnamon@mymail.local\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"useremailid\": 9,\n" +
                "                \"useremail\": \"hops@mymail.local\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"useremailid\": 10,\n" +
                "                \"useremail\": \"bunny@email.local\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"roles\": [\n" +
                "            {\n" +
                "                \"role\": {\n" +
                "                    \"roleid\": 2,\n" +
                "                    \"name\": \"USER\"\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"role\": {\n" +
                "                    \"roleid\": 3,\n" +
                "                    \"name\": \"DATA\"\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"userid\": 11,\n" +
                "        \"username\": \"barnbarn\",\n" +
                "        \"primaryemail\": \"barnbarn@lambdaschool.local\",\n" +
                "        \"useremails\": [\n" +
                "            {\n" +
                "                \"useremailid\": 12,\n" +
                "                \"useremail\": \"barnbarn@email.local\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"roles\": [\n" +
                "            {\n" +
                "                \"role\": {\n" +
                "                    \"roleid\": 2,\n" +
                "                    \"name\": \"USER\"\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"userid\": 13,\n" +
                "        \"username\": \"puttat\",\n" +
                "        \"primaryemail\": \"puttat@school.lambda\",\n" +
                "        \"useremails\": [],\n" +
                "        \"roles\": [\n" +
                "            {\n" +
                "                \"role\": {\n" +
                "                    \"roleid\": 2,\n" +
                "                    \"name\": \"USER\"\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"userid\": 14,\n" +
                "        \"username\": \"misskitty\",\n" +
                "        \"primaryemail\": \"misskitty@school.lambda\",\n" +
                "        \"useremails\": [],\n" +
                "        \"roles\": [\n" +
                "            {\n" +
                "                \"role\": {\n" +
                "                    \"roleid\": 2,\n" +
                "                    \"name\": \"USER\"\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "]";
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void getUserById() throws Exception{
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/users/user/4"), HttpMethod.GET, entity, String.class);

        String expected = "{\n" +
                "    \"userid\": 4,\n" +
                "    \"username\": \"admin\",\n" +
                "    \"primaryemail\": \"admin@lambdaschool.local\",\n" +
                "    \"useremails\": [\n" +
                "        {\n" +
                "            \"useremailid\": 5,\n" +
                "            \"useremail\": \"admin@email.local\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"useremailid\": 6,\n" +
                "            \"useremail\": \"admin@mymail.local\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"roles\": [\n" +
                "        {\n" +
                "            \"role\": {\n" +
                "                \"roleid\": 1,\n" +
                "                \"name\": \"ADMIN\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"role\": {\n" +
                "                \"roleid\": 2,\n" +
                "                \"name\": \"USER\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"role\": {\n" +
                "                \"roleid\": 3,\n" +
                "                \"name\": \"DATA\"\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void getUserByName() throws Exception{
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/users/user/name/cinnamon"), HttpMethod.GET, entity, String.class);

        String expected = "{\n" +
                "    \"userid\": 7,\n" +
                "    \"username\": \"cinnamon\",\n" +
                "    \"primaryemail\": \"cinnamon@lambdaschool.local\",\n" +
                "    \"useremails\": [\n" +
                "        {\n" +
                "            \"useremailid\": 8,\n" +
                "            \"useremail\": \"cinnamon@mymail.local\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"useremailid\": 9,\n" +
                "            \"useremail\": \"hops@mymail.local\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"useremailid\": 10,\n" +
                "            \"useremail\": \"bunny@email.local\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"roles\": [\n" +
                "        {\n" +
                "            \"role\": {\n" +
                "                \"roleid\": 2,\n" +
                "                \"name\": \"USER\"\n" +
                "            }\n" +
                "        },\n" +
                "        {\n" +
                "            \"role\": {\n" +
                "                \"roleid\": 3,\n" +
                "                \"name\": \"DATA\"\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void getUserLikeName() throws Exception{
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/users/user/name/like/cin"), HttpMethod.GET, entity, String.class);

        String expected = "[\n" +
                "    {\n" +
                "        \"userid\": 7,\n" +
                "        \"username\": \"cinnamon\",\n" +
                "        \"primaryemail\": \"cinnamon@lambdaschool.local\",\n" +
                "        \"useremails\": [\n" +
                "            {\n" +
                "                \"useremailid\": 8,\n" +
                "                \"useremail\": \"cinnamon@mymail.local\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"useremailid\": 9,\n" +
                "                \"useremail\": \"hops@mymail.local\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"useremailid\": 10,\n" +
                "                \"useremail\": \"bunny@email.local\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"roles\": [\n" +
                "            {\n" +
                "                \"role\": {\n" +
                "                    \"roleid\": 2,\n" +
                "                    \"name\": \"USER\"\n" +
                "                }\n" +
                "            },\n" +
                "            {\n" +
                "                \"role\": {\n" +
                "                    \"roleid\": 3,\n" +
                "                    \"name\": \"DATA\"\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "]";

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    public void addNewUser() {
//        User newUser = new User("mojo", "Coffee123", "mojo@lambdaschool.local");
//
//        HttpEntity<User> entity = new HttpEntity<User>(newUser, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                createURLWithPort("/users/user"),
//                HttpMethod.POST, entity, String.class);
//
//        String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
//
//        assertTrue(actual.contains("/students/Student1/courses/"));
    }

    @Test
    public void updateFullUser() {

    }

    @Test
    public void updateUser() {

    }

    @Test
    public void deleteUserById() {

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}