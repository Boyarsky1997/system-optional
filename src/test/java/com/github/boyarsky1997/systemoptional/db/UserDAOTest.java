package com.github.boyarsky1997.systemoptional.db;

import com.github.boyarsky1997.systemoptional.model.Role;
import com.github.boyarsky1997.systemoptional.model.Teacher;
import com.github.boyarsky1997.systemoptional.model.User;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class UserDAOTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private UserDAO userDAO;


    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        userDAO = new UserDAO(mockConnection);
    }

    @Test
    public void testAddComment() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);

        userDAO.addComment("Roman", 2, 1, new Date(15));

        Mockito.verify(mockPreparedStatement).setString(1, "Roman");
        Mockito.verify(mockPreparedStatement).setInt(2, 2);
        Mockito.verify(mockPreparedStatement).setInt(3, 1);
        Mockito.verify(mockPreparedStatement).setDate(4, new Date(15));
        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
    }

    @Test
    public void testAddCommentWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.execute())
                .thenThrow(SQLException.class);

        userDAO.addComment("Roman", 2, 1, new Date(15));

        Mockito.verify(mockPreparedStatement).setString(1, "Roman");
        Mockito.verify(mockPreparedStatement).setInt(2, 2);
        Mockito.verify(mockPreparedStatement).setInt(3, 1);
        Mockito.verify(mockPreparedStatement).setDate(4, new Date(15));
        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
    }

    @Test
    public void testGetIfStudent() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true);
        Mockito.when(mockResultSet.getInt(1)).thenReturn(3);
        Mockito.when(mockResultSet.getString(2)).thenReturn("STUDENT");
        Mockito.when(mockResultSet.getString(3)).thenReturn("Roman");
        Mockito.when(mockResultSet.getString(4)).thenReturn("Boyarsky");
        Mockito.when(mockResultSet.getString(5)).thenReturn("roman@gmail.com");
        Mockito.when(mockResultSet.getString(6)).thenReturn("123");

        User actual = userDAO.get("roman@gmail.com", "123");

        Assert.assertEquals(actual.getId(), 3);
        Assert.assertEquals(actual.getRole(), Role.STUDENT);
        Assert.assertEquals(actual.getName(), "Roman");
        Assert.assertEquals(actual.getSurname(), "Boyarsky");
        Assert.assertEquals(actual.getLogin(), "roman@gmail.com");
        Assert.assertEquals(actual.getPassword(), "123");
        Mockito.verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testGetIfTeacher() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true);
        Mockito.when(mockResultSet.getInt(1)).thenReturn(3);
        Mockito.when(mockResultSet.getString(2)).thenReturn("Teacher");
        Mockito.when(mockResultSet.getString(3)).thenReturn("Roman");
        Mockito.when(mockResultSet.getString(4)).thenReturn("Boyarsky");
        Mockito.when(mockResultSet.getString(5)).thenReturn("roman@gmail.com");
        Mockito.when(mockResultSet.getString(6)).thenReturn("123");

        User actual = userDAO.get("roman@gmail.com", "123");

        Assert.assertEquals(actual.getId(), 3);
        Assert.assertEquals(actual.getRole(), Role.TEACHER);
        Assert.assertEquals(actual.getName(), "Roman");
        Assert.assertEquals(actual.getSurname(), "Boyarsky");
        Assert.assertEquals(actual.getLogin(), "roman@gmail.com");
        Assert.assertEquals(actual.getPassword(), "123");
        Mockito.verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testGetWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        User actual = userDAO.get("roman@gmail.com", "123");

        Assert.assertNull(actual);
        Mockito.verify(mockPreparedStatement).setString(1, "roman@gmail.com");
        Mockito.verify(mockPreparedStatement).setString(2, "123");
    }


    @Test
    public void testGetAllStudentOnCourseId() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true, true, false);
        Mockito.when(mockResultSet.getInt(1)).thenReturn(3, 2);
        Mockito.when(mockResultSet.getString(2)).thenReturn("STUDENT", "STUDENT");
        Mockito.when(mockResultSet.getString(3)).thenReturn("Roman", "Vitalik");
        Mockito.when(mockResultSet.getString(4)).thenReturn("Boyarsky", "Boyarsky");
        Mockito.when(mockResultSet.getString(5)).thenReturn("roman@gmail.com", "vitalik@gmail.com");
        Mockito.when(mockResultSet.getString(6)).thenReturn("123", "123");

        List<User> actual = userDAO.getAllStudentOnCourseId(2);

        Assert.assertNotNull(actual);
        Assert.assertFalse(actual.isEmpty());
        Assert.assertEquals(actual.get(0).getId(), 3);
        Assert.assertEquals(actual.get(0).getRole(), Role.STUDENT);
        Assert.assertEquals(actual.get(0).getName(), "Roman");
        Assert.assertEquals(actual.get(0).getSurname(), "Boyarsky");
        Assert.assertEquals(actual.get(0).getLogin(), "roman@gmail.com");
        Assert.assertEquals(actual.get(0).getPassword(), "123");
        Assert.assertEquals(actual.get(1).getId(), 2);
        Assert.assertEquals(actual.get(1).getRole(), Role.STUDENT);
        Assert.assertEquals(actual.get(1).getName(), "Vitalik");
        Assert.assertEquals(actual.get(1).getSurname(), "Boyarsky");
        Assert.assertEquals(actual.get(1).getLogin(), "vitalik@gmail.com");
        Assert.assertEquals(actual.get(1).getPassword(), "123");
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement).setInt(1, 2);
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();

    }

    @Test
    public void testGetAllStudentOnCourseIdWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        List<User> actual = userDAO.getAllStudentOnCourseId(2);

        Assert.assertNotNull(actual);
        Assert.assertTrue(actual.isEmpty());
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement).setInt(1, 2);
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }

    @Test
    public void testCheckExistLogin() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true);

        boolean actual = userDAO.checkExistLogin("roman@gmail.com");

        Assert.assertTrue(actual);
        Mockito.verify(mockPreparedStatement).setString(1, "roman@gmail.com");
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }

    @Test
    public void testCheckExistLoginWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        boolean actual = userDAO.checkExistLogin("roman@gmail.com");

        Mockito.verify(mockPreparedStatement).setString(1, "roman@gmail.com");
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }

    @Test
    public void testAllTeacher() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true, false, true, false, true, false);
        Mockito.when(mockResultSet.getInt(1)).thenReturn(1, 2, 3);
        Mockito.when(mockResultSet.getString(2)).thenReturn("TEACHER", "TEACHER", "TEACHER");
        Mockito.when(mockResultSet.getString(3)).thenReturn("R", "V", "S");
        Mockito.when(mockResultSet.getString(4)).thenReturn("Boyarsky", "Boyarsky", "Boyarsky");
        Mockito.when(mockResultSet.getString(5)).thenReturn("roman@gmail.com", "vitalik@gmail.com", "sak@gmail.com");
        Mockito.when(mockResultSet.getString(6)).thenReturn("123", "123", "123");

        List<Integer> list = Arrays.asList(1, 2, 3);

        List<User> actual = userDAO.getAllTeachersById(list);

        Assert.assertNotNull(actual);
        Assert.assertFalse(actual.isEmpty());
        Mockito.verify(mockPreparedStatement).setInt(1, list.get(0));
        Mockito.verify(mockPreparedStatement).setInt(1, list.get(1));
        Mockito.verify(mockPreparedStatement).setInt(1, list.get(2));
        Mockito.verify(mockPreparedStatement, Mockito.times(3)).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
        Assert.assertEquals(actual.get(0).getId(), 1);
        Assert.assertEquals(actual.get(0).getRole(), Role.TEACHER);
        Assert.assertEquals(actual.get(0).getName(), "R");
        Assert.assertEquals(actual.get(0).getSurname(), "Boyarsky");
        Assert.assertEquals(actual.get(0).getLogin(), "roman@gmail.com");
        Assert.assertEquals(actual.get(0).getPassword(), "123");
        Assert.assertEquals(actual.get(1).getId(), 2);
        Assert.assertEquals(actual.get(1).getRole(), Role.TEACHER);
        Assert.assertEquals(actual.get(1).getName(), "V");
        Assert.assertEquals(actual.get(1).getSurname(), "Boyarsky");
        Assert.assertEquals(actual.get(1).getLogin(), "vitalik@gmail.com");
        Assert.assertEquals(actual.get(1).getPassword(), "123");
        Assert.assertEquals(actual.get(2).getId(), 3);
        Assert.assertEquals(actual.get(2).getRole(), Role.TEACHER);
        Assert.assertEquals(actual.get(2).getName(), "S");
        Assert.assertEquals(actual.get(2).getSurname(), "Boyarsky");
        Assert.assertEquals(actual.get(2).getLogin(), "sak@gmail.com");
        Assert.assertEquals(actual.get(2).getPassword(), "123");
    }

    @Test
    public void testAllTeacherWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        List<Integer> list = Arrays.asList(1, 2, 3);

        List<User> actual = userDAO.getAllTeachersById(list);

        Assert.assertTrue(actual.isEmpty());
        Mockito.verify(mockPreparedStatement).setInt(1, list.get(0));
        Mockito.verify(mockPreparedStatement).setInt(1, list.get(1));
        Mockito.verify(mockPreparedStatement).setInt(1, list.get(2));
        Mockito.verify(mockPreparedStatement, Mockito.times(3)).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }

    @Test
    public void testInsertUser() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);

        User user = new Teacher(null, "roman", "ff", "gsfsfsf", "gsgsgs");

        userDAO.insertUser(user);

        Mockito.verify(mockPreparedStatement).setString(1, "TEACHER");
        Mockito.verify(mockPreparedStatement).setString(2, "gsfsfsf");
        Mockito.verify(mockPreparedStatement).setString(3, "gsgsgs");
        Mockito.verify(mockPreparedStatement).setString(4, "roman");
        Mockito.verify(mockPreparedStatement).setString(5, "ff");
        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
    }

    @Test
    public void testInsertUserWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.execute())
                .thenThrow(SQLException.class);

        User user = new Teacher(null,"roman", "ff", "gsfsfsf", "gsgsgs");

        userDAO.insertUser(user);

        Mockito.verify(mockPreparedStatement).setString(1, "TEACHER");
        Mockito.verify(mockPreparedStatement).setString(2, "gsfsfsf");
        Mockito.verify(mockPreparedStatement).setString(3, "gsgsgs");
        Mockito.verify(mockPreparedStatement).setString(4, "roman");
        Mockito.verify(mockPreparedStatement).setString(5, "ff");
        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
    }

    @Test
    public void testGetTeacher() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true, false);
        Mockito.when(mockResultSet.getInt(1)).thenReturn(2);
        Mockito.when(mockResultSet.getString(2)).thenReturn("TEACHER");
        Mockito.when(mockResultSet.getString(3)).thenReturn("Roman");
        Mockito.when(mockResultSet.getString(4)).thenReturn("Boyarsky");
        Mockito.when(mockResultSet.getString(5)).thenReturn("roman@gmail.com");
        Mockito.when(mockResultSet.getString(6)).thenReturn("123");

        User teacher = userDAO.getTeacher(2);

        Assert.assertEquals(teacher.getId(), 2);
        Assert.assertEquals(teacher.getRole(), Role.TEACHER);
        Assert.assertEquals(teacher.getName(), "Roman");
        Assert.assertEquals(teacher.getSurname(), "Boyarsky");
        Assert.assertEquals(teacher.getLogin(), "roman@gmail.com");
        Assert.assertEquals(teacher.getPassword(), "123");
        Mockito.verify(mockPreparedStatement).setInt(1, 2);
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
        Mockito.verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testGetTeacherWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        User teacher = userDAO.getTeacher(2);

        Mockito.verify(mockPreparedStatement).setInt(1, 2);
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
        Mockito.verify(mockPreparedStatement).executeQuery();
    }
}