package Test;

import enums.Status;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void getHistoryShouldReturnListOf10Tasks() {
        for (int i = 0; i < 20; i++) {
            taskManager.addTask(new Task("Some name", "Some description"));
        }

        List<Task> tasks = taskManager.getTasks();
        for (Task task : tasks) {
            taskManager.getTaskByID(task.getId());
        }

        List<Task> list = taskManager.getHistory();
        assertEquals(10, list.size(), "Неверное количество элементов в истории ");
    }

    @Test
    public void getHistoryShouldReturnOldTaskAfterUpdate() {
        Task washFloor = new Task("Помыть мотоцикл", "С новым шампунем");
        taskManager.addTask(washFloor);
        taskManager.getTaskByID(washFloor.getId());
        taskManager.updateTask(new Task(washFloor.getId(), "Не забыть помыть мотоцикл",
                "Можно и без шампуня", Status.IN_PROGRESS));
        List<Task> tasks = taskManager.getHistory();
        Task oldTask = tasks.getFirst();
        assertEquals(washFloor.getName(), oldTask.getName(), "В истории не сохранилась старая версия задачи");
        assertEquals(washFloor.getDescription(), oldTask.getDescription(),
                "В истории не сохранилась старая версия задачи");

    }

    @Test
    public void getHistoryShouldReturnOldEpicAfterUpdate() {
        Epic flatRenovation = new Epic("Отремонтировать мотоцикл", "Нужно успеть за отпуск");
        taskManager.addEpic(flatRenovation);
        taskManager.getEpicByID(flatRenovation.getId());
        taskManager.updateEpic(new Epic(flatRenovation.getId(), "Новое имя", "новое описание",
                Status.IN_PROGRESS));
        List<Task> epics = taskManager.getHistory();
        Epic oldEpic = (Epic) epics.getFirst();
        assertEquals(flatRenovation.getName(), oldEpic.getName(),
                "В истории не сохранилась старая версия эпика");
        assertEquals(flatRenovation.getDescription(), oldEpic.getDescription(),
                "В истории не сохранилась старая версия эпика");
    }

    @Test
    public void getHistoryShouldReturnOldSubtaskAfterUpdate() {
        Epic flatRenovation = new Epic("Отремонтировать мотоцикл", "Нужно успеть за отпуск");
        taskManager.addEpic(flatRenovation);
        Subtask flatRenovationSubtask3 = new Subtask("Заказать новый монитор", "С частотой 120Гц",
                flatRenovation.getId());
        taskManager.addSubtask(flatRenovationSubtask3);
        taskManager.getSubtaskByID(flatRenovationSubtask3.getId());
        taskManager.updateSubtask(new Subtask(flatRenovationSubtask3.getId(), "Новое имя",
                "новое описание", Status.IN_PROGRESS, flatRenovation.getId()));
        List<Task> subtasks = taskManager.getHistory();
        Subtask oldSubtask = (Subtask) subtasks.getFirst();
        assertEquals(flatRenovationSubtask3.getName(), oldSubtask.getName(),
                "В истории не сохранилась старая версия эпика");
        assertEquals(flatRenovationSubtask3.getDescription(), oldSubtask.getDescription(),
                "В истории не сохранилась старая версия эпика");
    }
}