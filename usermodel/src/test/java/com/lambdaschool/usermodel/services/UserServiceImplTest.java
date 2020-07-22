package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        List<User> users = userService.findAll();

        users.forEach(user -> System.out.format("%s: %s \n", user.getUserid(), user.getUsername()));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void a_findUserById() {
        assertEquals("admin", userService.findUserById(4).getUsername());
    }

    @Test(expected = EntityNotFoundException.class)
    public void ab_findUserByIdFail() {
        assertEquals("admin", userService.findUserById(111).getUsername());
    }

    @Test
    public void b_findByNameContaining() {
        assertEquals(1, userService.findByNameContaining("ad").size());
    }

    @Test
    public void bb_findByNameContainingFail() {
        assertEquals(0, userService.findByNameContaining("zzzzzz").size());
    }

    @Test
    public void c_findAll() {
        assertEquals(5, userService.findAll().size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void z_delete() {
        userService.delete(13);
        userService.findUserById(13);
    }

    @Test(expected = EntityNotFoundException.class)
    public void zb_deleteFail() {
        userService.delete(2222);
    }

    @Test
    public void d_findByName() {
        assertEquals("cinnamon", userService.findByName("cinnamon").getUsername());
    }

    @Test(expected = EntityNotFoundException.class)
    public void db_findByNameFail() {
        assertEquals("zzzzz", userService.findByName("zzzzz").getUsername());
    }

    @Test
    public void e_save() {
        String username = "adrian";
        User u2 = new User(username,
                "1234567",
                "adrian@lambdaschool.test");
        u2.getUseremails()
                .add(new Useremail(u2,
                        "cinnamon@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "hops@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "bunny@email.local"));
        User newUser = userService.save(u2);
        assertNotNull(newUser);
        assertEquals(newUser.getUsername(), username);
    }

    @Test
    public void eb_savePutVer() {
        String username = "adrian2";
        User u2 = new User(username,
                "1234567",
                "adrian@lambdaschool.testtt");
        u2.setUserid(14);
        User newUser = userService.save(u2);
        u2.getUseremails()
                .add(new Useremail(u2,
                        "cinnamon@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "hops@mymail.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "bunny@email.local"));
        newUser.setUserid(11);

        assertNotNull(newUser);
        assertEquals(newUser.getUsername(), username);
    }

    @Test
    public void f_update() {
        String username= "notadminnytrytr";
        User u2 = new User(username,
                "1234567ytryr",
                "adrian@lambdaschool.testttytry");
        u2.setUserid(11);

        User newUser = userService.update(u2, 11);
        assertNotNull(newUser);
        assertEquals(newUser.getUsername(), username);
    }

    @Test(expected = EntityNotFoundException.class)
    public void fb_updateFail() {
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
        u2.setUserid(11);

        User newUser = userService.update(u2, 77777);
        assertNotNull(newUser);
        assertEquals(newUser.getUsername(), username);
    }

    @Test
    public void g_deleteAll() {
        userService.deleteAll();
        assertEquals(0, userService.findAll().size());
    }
}