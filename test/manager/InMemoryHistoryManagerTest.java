package manager;

import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private InMemoryHistoryManager manager;
    private Task task1;
    private Task task2;
    private Task task3;
    private Epic epic;
    private final Subtask subtask;

    InMemoryHistoryManagerTest(Subtask subtask) {
        this.subtask = subtask;
    }

    @BeforeEach
    void setUp() {
        manager = new InMemoryHistoryManager();
        task1 = new Task("Task 1");
        task2 = new Task("Task 2");
        task3 = new Task("Task 3");
        new Subtask(1);
        epic = new Epic();
    }

    // Базовые тесты добавления и удаления
    @Test
    void testAddSingleTask() {
        manager.add(task1);
        assertEquals(1, manager.getHistory().size());
        assertEquals(task1, manager.getHistory().getFirst());
    }

    @Test
    void testAddMultipleTasks() {
        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        assertEquals(3, manager.getHistory().size());
        assertEquals(task1, manager.getHistory().get(0));
        assertEquals(task2, manager.getHistory().get(1));
        assertEquals(task3, manager.getHistory().get(2));
    }

    @Test
    void testRemoveTask() {
        manager.add(task1);
        manager.add(task2);
        manager.remove(task1.getId());
        assertEquals(1, manager.getHistory().size());
        assertEquals(task2, manager.getHistory().getFirst());
    }

    @Test
    void testUpdateExistingTask() {
        manager.add(task1);
        Task updatedTask = new Task("Updated Task 1");
        manager.add(updatedTask);
        assertEquals(1, manager.getHistory().size());
        assertEquals(updatedTask, manager.getHistory().getFirst());
    }

    // Тесты целостности данных
    @Test
    void testSubTaskRemoval() {
        epic.addSubtask(subtask);
        manager.add(epic);
        manager.add(subtask);

        manager.remove(subtask.getId());

    }

    @Test
    void testTaskIdIntegrity() {
        manager.add(task1);
        task1.setId(2); // Попытка изменить ID
        manager.add(task1);

        assertEquals(1, manager.getHistory().size());
        assertEquals(1, manager.getHistory().getFirst().getId());
    }

    // Тесты сеттеров
    @Test
    void testSetterChanges() {
        manager.add(task1);
        task1.setName("Changed Name");

        Task storedTask = manager.getHistory().getFirst();
        assertEquals("Task 1", storedTask.getName()); // Убедимся, что изменения не повлияли
    }

    @Test
    void testConcurrentModification() {
        manager.add(task1);
        manager.add(task2);

        List<Task> history = manager.getHistory();
        history.removeFirst(); // Попытка изменить исходный список

        assertEquals(2, manager.getHistory().size()); // Убедимся, что исходный список не изменился
    }

    // Тесты производительности
    @Test
    void testPerformance() {
        for (int i = 0; i < 1000; i++) {
            manager.add(new Task("Test " + i));
        }

        assertEquals(1000, manager.getHistory().size());
        assertTrue(manager.getHistory().contains(new Task("Test 500")));
    }

}