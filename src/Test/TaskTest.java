package Test;

import enums.Status;
import org.junit.jupiter.api.Test;
import task.Subtask;
import task.Task;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    public void tasksWithEqualIdShouldBeEqual() {
        Task task1 = new Subtask(10, "Купить яйца", "В Верном", Status.NEW, 5);
        Task task2 = new Subtask(10, "Купить печеньки", "В Магните", Status.DONE, 5);
        assertEquals(task1, task2,
                "Ошибка! Экземпляры класса Task должны быть равны друг другу, если равен их id;");
    }
}