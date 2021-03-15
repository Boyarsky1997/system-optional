package com.github.boyarsky1997.systemoptional.db;

import com.github.boyarsky1997.systemoptional.model.Comment;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.List;

public class CommentDAOTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private CommentDAO commentDao;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        commentDao = new CommentDAO(mockConnection);
    }

    @Test
    public void testGetAllCommentOnUserId() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true, true, false);
        Mockito.when(mockResultSet.getInt(1)).thenReturn(1, 2);
        Mockito.when(mockResultSet.getString(2)).thenReturn("Hello", "World");
        Mockito.when(mockResultSet.getInt(3)).thenReturn(11, 12);
        Mockito.when(mockResultSet.getInt(4)).thenReturn(21, 22);
        Mockito.when(mockResultSet.getDate(5)).thenReturn(new Date(12), new Date(22));
        Mockito.when(mockResultSet.getString(6)).thenReturn("Vitalik", "Roman");
        Mockito.when(mockResultSet.getString(7)).thenReturn("Foo", "Faa");

        List<Comment> actual = commentDao.getAllCommentOnUserId(123);

        Assert.assertNotNull(actual);
        Assert.assertFalse(actual.isEmpty());
        Assert.assertEquals(actual.size(), 2);
        Assert.assertEquals(actual.get(0).getId(), 1);
        Assert.assertEquals(actual.get(0).getMessage(), "Hello");
        Assert.assertEquals(actual.get(0).getTeacherId(), 11);
        Assert.assertEquals(actual.get(0).getStudentId(), 21);
        Assert.assertEquals(actual.get(0).getDate(), new Date(12));
        Assert.assertEquals(actual.get(0).getNameTeacher(), "Vitalik");
        Assert.assertEquals(actual.get(0).getTeacherSurname(), "Foo");
        Assert.assertEquals(actual.get(1).getId(), 2);
        Assert.assertEquals(actual.get(1).getMessage(), "World");
        Assert.assertEquals(actual.get(1).getTeacherId(), 12);
        Assert.assertEquals(actual.get(1).getStudentId(), 22);
        Assert.assertEquals(actual.get(1).getDate(), new Date(22));
        Assert.assertEquals(actual.get(1).getNameTeacher(), "Roman");
        Assert.assertEquals(actual.get(1).getTeacherSurname(), "Faa");
        Mockito.verify(mockPreparedStatement).setInt(1, 123);
        Mockito.verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testGetStudentCourse() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        List<Comment> actual = commentDao.getAllCommentOnUserId(123);

        Assert.assertNotNull(actual);
        Assert.assertTrue(actual.isEmpty());
    }

}