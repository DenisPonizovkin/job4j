package ru.job4j.generic.container.map.model;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void whenUsersEqualThenHashCodesEqual() {
       User u1 = new User("name", 1, null, 1000);
       User u2 = new User(u1.getName(), u1.getChildren(), u1.getBirthday(), u1.getCard());

       assertThat(u1.hashCode(), is(u2.hashCode()));
    }

    @Test
    public void whenUsersNotEqualThenHashCodesNotEqual() {
        User u1 = new User("name1", 1, null, 1000);
        User u2 = new User("name2", 1, null, 1111);

        assertThat(u1.hashCode() == u2.hashCode(), is(false));
    }

    @Test
    public void whenGetHashCodeTwiceThenHashCodeSame() {
        User u = new User("name1", 1, null, 1000);

        assertThat(u.hashCode(), is(u.hashCode()));
    }
}
