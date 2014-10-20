package com.mashape.dao;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.mashape.common.TaskToMongoObjMapper;
import com.mashape.domain.Task;
import com.mashape.interfaces.TaskDao;
import com.mongodb.MongoClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TaskDaoMongoImplTest {

    private TaskDao taskDao;

    @Before
    public void setUp() throws Exception {
        Injector injector = Guice.createInjector(new AbstractModule() {

            @Provides TaskToMongoObjMapper providesMapper() {
                return new TaskToMongoObjMapper();
            }

            @Provides MongoClient provideMongoClient() throws UnknownHostException {
                return new MongoClient("localhost");
            }

            @Override
            protected void configure() {
                bind(TaskDao.class).to(TaskDaoMongoImpl.class);
            }
        });

        this.taskDao = injector.getInstance(TaskDao.class);
    }

    @After
    public void tearDown() throws Exception {
        taskDao = null;
    }

    @Test
    public void testGetAll() throws Exception {
        Iterable<Task> tasks = taskDao.getAll();

        assert(tasks != null);
    }

    @Test
    public void testGet() throws Exception {

        Task oneTask = getOneRandomExistingTask();

        Task fetched = taskDao.get(oneTask.getTaskId());

        assertTaskEqual(oneTask, fetched);
     }

    @Test
    public void testUpdate() throws Exception {
        Task oldTask = getOneRandomExistingTask();

        String newTitle = oldTask.getTitle() + "new title appending";
        String newContent = oldTask.getContent() + "new content appending";
        boolean newDoneOrNot = !oldTask.isDone();

        oldTask.setTitle(newTitle);
        oldTask.setContent(newContent);
        oldTask.setDone(newDoneOrNot);

        taskDao.update(oldTask);

        Task newTask = taskDao.get(oldTask.getTaskId());

        assertEquals(newTitle, newTask.getTitle());
        assertEquals(newContent, newTask.getContent());
        assertEquals(newDoneOrNot, newTask.isDone());

    }

    @Test
    public void testInsert() throws Exception {
        Task task = createRandomTask();

        Task result = taskDao.insert(task);

        assertTaskEqual(task, result);

    }

    @Test
    public void testDelete() throws Exception {
        Task task = getOneRandomExistingTask();
        taskDao.delete(task);

        assert(taskDao.get(task.getTaskId()) == null);
    }

    private Task getOneRandomExistingTask() throws IOException {
        Iterable<Task> tasks = taskDao.getAll();
        assert(tasks != null);

        List<Task> taskList = Lists.newArrayList(tasks);
        Task oneTask = taskList.get((int) (taskList.size() * Math.random()));

        return oneTask;
    }

    private Task createRandomTask() {
        Task task = new Task();
        task.setTitle("Task title" + Math.random());
        task.setContent("Task content." + Math.random());
        task.setDone(false);

        return task;
    }

    private void assertTaskEqual(Task task, Task result) {
        assertEquals(task.getContent(), result.getContent());
        assertEquals(task.getTitle(), result.getTitle());
        assertEquals(task.getContent(), result.getContent());
        assertEquals(task.isDone(), result.isDone());
    }
}