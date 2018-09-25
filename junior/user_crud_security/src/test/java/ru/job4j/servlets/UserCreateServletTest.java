package ru.job4j.servlets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.servlets.logic.ValidateService;
import ru.job4j.servlets.logic.ValidateStub;
import ru.job4j.servlets.presentation.UserCreateServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class UserCreateServletTest {

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse resp = mock(HttpServletResponse.class);
    private final HttpSession session = mock(HttpSession.class);
    private final ValidateStub validate = new ValidateStub();

    @Before
    public void initSession() {
        when(req.getSession()).thenReturn(session);
        when(req.getSession().getAttribute("login")).thenReturn("admin");
    }

    @Test
    public void whenAddUserThenStoreIt() throws ServletException, IOException {
        PowerMockito.mockStatic(ValidateService.class);
        Mockito.when(ValidateService.getInstance()).thenReturn(validate);
        when(req.getParameter("login")).thenReturn("Petr Arsentev");
        when(req.getParameter("name")).thenReturn("Petr Arsentev");
        when(req.getParameter("email")).thenReturn("Petr Arsentev");
        when(req.getParameter("password")).thenReturn("Petr Arsentev");
        when(req.getParameter("id")).thenReturn("2");
        when(req.getParameter("role")).thenReturn("1");
        int prevSize = validate.findAll().size();
        new UserCreateServlet().doPost(req, resp);
        assertThat(validate.findById(2) == null, is(false));
        assertThat(validate.findById(2).getName(), is("Petr Arsentev"));
        assertThat(prevSize < validate.findAll().size(), is(true));
    }

    @Test
    public void whenAdminUpdateUserThenUpdateComplete() throws ServletException, IOException {
        whenAddUserThenStoreIt();
        when(req.getParameter("login")).thenReturn("Petr Arsentev!");
        when(req.getParameter("name")).thenReturn("Petr Arsentev");
        when(req.getParameter("email")).thenReturn("Petr Arsentev");
        when(req.getParameter("password")).thenReturn("Petr Arsentev");
        when(req.getParameter("id")).thenReturn("2");
        when(req.getParameter("role")).thenReturn("1");
        new UserCreateServlet().doPost(req, resp);
        assertThat(validate.findById(2).getLogin().equals("Petr Arsentev"), is(false));
    }

    @Test
    public void whenAdminDeleteUserThenCardinalityOfUsersSetDecrease() throws ServletException, IOException {
        whenAddUserThenStoreIt();
        when(req.getParameter("login")).thenReturn("Petr Arsentev");
        when(req.getParameter("name")).thenReturn("Petr Arsentev");
        when(req.getParameter("email")).thenReturn("Petr Arsentev");
        when(req.getParameter("password")).thenReturn("Petr Arsentev");
        when(req.getParameter("id")).thenReturn("2");
        when(req.getParameter("role")).thenReturn("1");
        when(req.getParameter("operation")).thenReturn("delete");
        int prevSize = validate.findAll().size();
        new UserCreateServlet().doPost(req, resp);
        assertThat(prevSize < validate.findAll().size(), is(false));
    }
}
