package com.github.boyarsky1997.systemoptional.db;

import com.github.boyarsky1997.systemoptional.model.Course;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CourseDAOTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private CourseDAO courseDAO;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        courseDAO = new CourseDAO(mockConnection);
    }

    @Test
    public void testGetAll() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true, true, false);
        Mockito.when(mockResultSet.getInt(1)).thenReturn(1, 2);
        Mockito.when(mockResultSet.getString(2)).thenReturn("Foo", "Faa");
        Mockito.when(mockResultSet.getInt(3)).thenReturn(11, 12);
        Mockito.when(mockResultSet.getString(4)).thenReturn("Hello", "World");

        List<Course> actual = courseDAO.getAll();

        Assert.assertNotNull(actual);
        Assert.assertFalse(actual.isEmpty());
        Assert.assertEquals(actual.size(), 2);
        Assert.assertEquals(actual.get(0).getId(), 1);
        Assert.assertEquals(actual.get(0).getTitle(), "Foo");
        Assert.assertEquals(actual.get(0).getTeacherId(), 11);
        Assert.assertEquals(actual.get(0).getDescription(), "Hello");

        Assert.assertEquals(actual.get(1).getId(), 2);
        Assert.assertEquals(actual.get(1).getTitle(), "Faa");
        Assert.assertEquals(actual.get(1).getTeacherId(), 12);
        Assert.assertEquals(actual.get(1).getDescription(), "World");
        Mockito.verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testGetAllWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        List<Course> actual = courseDAO.getAll();

        Assert.assertNotNull(actual);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void testAddUserIdAndCourseId() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);

        courseDAO.addUserIdAndCourseId(11, 22);

        Mockito.verify(mockPreparedStatement).setInt(1, 11);
        Mockito.verify(mockPreparedStatement).setInt(2, 22);
        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
    }

    @Test
    public void testAddUserIdAndCourseIdWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.execute())
                .thenThrow(SQLException.class);

        courseDAO.addUserIdAndCourseId(11, 22);

        Mockito.verify(mockPreparedStatement).setInt(1, 11);
        Mockito.verify(mockPreparedStatement).setInt(2, 22);
        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
    }

    @Test
    public void testInsert() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next())
                .thenReturn(true);
        Mockito.when(mockResultSet.getInt(Mockito.anyInt()))
                .thenReturn(12345);

        int actual = courseDAO.insert("Roman", "Hello", 22);

        Assert.assertEquals(actual, 12345);
        Mockito.verify(mockPreparedStatement).setString(1, "Roman");
        Mockito.verify(mockPreparedStatement).setInt(2, 22);
        Mockito.verify(mockPreparedStatement).setString(3, "Hello");
        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockConnection, Mockito.times(2))
                .prepareStatement(Mockito.anyString());
        Mockito.verify(mockResultSet).next();
        Mockito.verify(mockResultSet).getInt(1);
    }

    @Test
    public void testInsertWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.execute())
                .thenThrow(SQLException.class);

        int actual = courseDAO.insert("Roman", "Hello", 22);

        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
    }

    @Test
    public void testInsertWhenException1() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        int actual = courseDAO.insert("Roman", "Hello", 22);

        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement).executeQuery();

    }

    @Test
    public void testUpdate() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);

        courseDAO.update(111, "Мова", "Українська");

        Mockito.verify(mockPreparedStatement).setString(1, "Мова");
        Mockito.verify(mockPreparedStatement).setString(2, "Українська");
        Mockito.verify(mockPreparedStatement).setInt(3, 111);
        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
    }

    @Test
    public void testUpdateWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.execute())
                .thenThrow(SQLException.class);

        courseDAO.update(111, "Мова", "Українська");

        Mockito.verify(mockPreparedStatement).setString(1, "Мова");
        Mockito.verify(mockPreparedStatement).setString(2, "Українська");
        Mockito.verify(mockPreparedStatement).setInt(3, 111);
        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
    }

    @Test
    public void testGetAllCourseOnTeacherId() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next())
                .thenReturn(true, true, false);
        Mockito.when(mockResultSet.getInt(1)).thenReturn(1, 2);
        Mockito.when(mockResultSet.getString(2)).thenReturn("Foo", "Faa");
        Mockito.when(mockResultSet.getString(3)).thenReturn("Zip", "Zop");

        List<Course> actual = courseDAO.getAllCourseOnTeacherId(111);

        Assert.assertNotNull(actual);
        Assert.assertFalse(actual.isEmpty());
        Assert.assertEquals(actual.size(), 2);
        Assert.assertEquals(actual.get(0).getId(), 1);
        Assert.assertEquals(actual.get(0).getTitle(), "Foo");
        Assert.assertEquals(actual.get(0).getDescription(), "Zip");
        Assert.assertEquals(actual.get(1).getId(), 2);
        Assert.assertEquals(actual.get(1).getTitle(), "Faa");
        Assert.assertEquals(actual.get(1).getDescription(), "Zop");
    }

    @Test
    public void testGetAllCourseOnTeacherIdWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        List<Course> actual = courseDAO.getAllCourseOnTeacherId(111);

        Assert.assertNotNull(actual);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void testGetStudentCourse() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next())
                .thenReturn(true, true, false);
        Mockito.when(mockResultSet.getInt(1)).thenReturn(11, 22);
        Mockito.when(mockResultSet.getString(2)).thenReturn("Ffa", "Ffo");
        Mockito.when(mockResultSet.getString(3)).thenReturn("Kik", "Kak");
        List<Course> actual = courseDAO.getStudentCourse(111);

        Assert.assertNotNull(actual);
        Assert.assertFalse(actual.isEmpty());
        Assert.assertEquals(actual.size(), 2);
        Assert.assertEquals(actual.get(0).getId(), 11);
        Assert.assertEquals(actual.get(0).getTitle(), "Ffa");
        Assert.assertEquals(actual.get(0).getDescription(), "Kik");
        Assert.assertEquals(actual.get(1).getId(), 22);
        Assert.assertEquals(actual.get(1).getTitle(), "Ffo");
        Assert.assertEquals(actual.get(1).getDescription(), "Kak");
    }

    @Test
    public void testGetStudentCourseWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        List<Course> actual = courseDAO.getStudentCourse(111);

        Assert.assertNotNull(actual);
        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void testCheck() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next())
                .thenReturn(true);

        boolean actual = courseDAO.checkIsAssignedStudentOnCourse(111, 222);

        Assert.assertTrue(actual);
        Mockito.verify(mockPreparedStatement).setInt(1, 111);
        Mockito.verify(mockPreparedStatement).setInt(2, 222);
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockResultSet).next();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }

    @Test
    public void testCheckWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        boolean actual = courseDAO.checkIsAssignedStudentOnCourse(111, 222);

        Mockito.verify(mockPreparedStatement).setInt(1, 111);
        Mockito.verify(mockPreparedStatement).setInt(2, 222);
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }

    @Test
    public void testGetById() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true, false);
        Mockito.when(mockResultSet.getInt(1)).thenReturn(5);
        Mockito.when(mockResultSet.getString(2)).thenReturn("Укр");
        Mockito.when(mockResultSet.getInt(3)).thenReturn(3);
        Mockito.when(mockResultSet.getString(4)).thenReturn("move");

        Course actual = courseDAO.getById(4);

        Assert.assertNotNull(actual);
        Assert.assertEquals(actual.getId(), 5);
        Assert.assertEquals(actual.getTitle(), "Укр");
        Assert.assertEquals(actual.getTeacherId(), 3);
        Assert.assertEquals(actual.getDescription(), "move");
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement).setInt(1,4);
        Mockito.verify(mockPreparedStatement,Mockito.never()).execute();
    }

    @Test
    public void testGetByIdWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        Course actual = courseDAO.getById(4);

        Mockito.verify(mockPreparedStatement).setInt(1,4);
        Mockito.verify(mockPreparedStatement,Mockito.never()).execute();
    }
}