package Test;

import enums.Status;
import org.junit.jupiter.api.Test;
import task.Subtask;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {

    @Test
    public void SubtasksWithEqualIdShouldBeEqual() {
        Subtask subtask1 = new Subtask(10, "Купить яйца", "В Верном", Status.NEW, 5);
        Subtask subtask2 = new Subtask(10, "Купить печеньки", "В Магните", Status.DONE, 5);
        assertEquals(subtask1, subtask2,
                "Ошибка! Наследники класса Task должны быть равны друг другу, если равен их id;");
    }
}