import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    private static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    void addNewTask() {
        //проверяем, что InMemoryTaskManager добавляет задачи и может найти их по id;
        final Task task = taskManager.addTask(new Task("Test addNewTask",
                "Test addNewTask description"));
        final Task savedTask = taskManager.getTaskByID(task.getId());
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final List<Task> tasks = taskManager.getTasks();
        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void addNewEpicAndSubtasks() {
        //проверяем, что InMemoryTaskManager добавляет эпики и подзадачи и может найти их по id;
        final Epic flatRenovation = taskManager.addEpic(new Epic("Отремонтировать мотоцикл",
                "Нужно успеть за отпуск"));
        final Subtask flatRenovationSubtask1 = taskManager.addSubtask(new Subtask
                ("Переобуть колёса", "Заднее обязательно!", flatRenovation.getId()));
        final Subtask flatRenovationSubtask2 = taskManager.addSubtask(new Subtask
                ("Заменить все жидкости", "Отходы утилизировать", flatRenovation.getId()));
        final Subtask flatRenovationSubtask3 = taskManager.addSubtask(new Subtask
                ("Заказать новый монитор", "С частотой 120Гц",
                flatRenovation.getId()));
        final Epic savedEpic = taskManager.getEpicByID(flatRenovation.getId());
        final Subtask savedSubtask1 = taskManager.getSubtaskByID(flatRenovationSubtask1.getId());
        final Subtask savedSubtask2 = taskManager.getSubtaskByID(flatRenovationSubtask2.getId());
        final Subtask savedSubtask3 = taskManager.getSubtaskByID(flatRenovationSubtask3.getId());
        assertNotNull(savedEpic, "Эпик не найден.");
        assertNotNull(savedSubtask2, "Подзадача не найдена.");
        assertEquals(flatRenovation, savedEpic, "Эпики не совпадают.");
        assertEquals(flatRenovationSubtask1, savedSubtask1, "Подзадачи не совпадают.");
        assertEquals(flatRenovationSubtask3, savedSubtask3, "Подзадачи не совпадают.");

        final List<Epic> epics = taskManager.getEpics();
        assertNotNull(epics, "Эпики не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
        assertEquals(flatRenovation, epics.getFirst(), "Эпики не совпадают.");

        final List<Subtask> subtasks = taskManager.getSubtasks();
        assertNotNull(subtasks, "Подзадачи не возвращаются.");
        assertEquals(3, subtasks.size(), "Неверное количество подзадач.");
        assertEquals(savedSubtask1, subtasks.getFirst(), "Подзадачи не совпадают.");
    }

    @Test
    public void updateTaskShouldReturnTaskWithTheSameId() {
        final Task expected = new Task("имя", "описание");
        taskManager.addTask(expected);
        final Task updatedTask = new Task(expected.getId(), "новое имя", "новое описание", Status.DONE);
        final Task actual = taskManager.updateTask(updatedTask);
        assertEquals(expected.getId(), actual.getId(), "IDs не совпадают");

    }

    @Test
    public void updateEpicShouldReturnEpicWithTheSameId() {
        final Epic expected = new Epic("имя", "описание");
        taskManager.addEpic(expected);
        final Epic updatedEpic = new Epic(expected.getId(), "новое имя", "новое описание", Status.DONE);
        final Epic actual = taskManager.updateEpic(updatedEpic);
        assertEquals(expected.getId(), actual.getId(), "IDs не совпадают");

    }

    @Test
    public void updateSubtaskShouldReturnSubtaskWithTheSameId() {
        final Epic epic = new Epic("имя", "описание");
        taskManager.addEpic(epic);
        final Subtask expected = new Subtask("имя", "описание", epic.getId());
        taskManager.addSubtask(expected);
        final Subtask updatedSubtask = new Subtask(expected.getId(), "новое имя", "новое описание",
                Status.DONE, epic.getId());
        final Subtask actual = taskManager.updateSubtask(updatedSubtask);
        assertEquals(expected, actual, "Вернулась подзадача с другим id");
    }

    @Test
    public void deleteTasksShouldReturnEmptyList() {
        taskManager.addTask(new Task("Купить книги", "Список в заметках"));
        taskManager.addTask(new Task("Помыть мотоцикл", "С новым шампунем"));
        taskManager.deleteTasks();
        List<Task> tasks = taskManager.getTasks();
        assertTrue(tasks.isEmpty(), "После удаления задач список должен быть пуст.");
    }

    @Test
    public void deleteEpicsShouldReturnEmptyList() {
        taskManager.addEpic(new Epic("Отремонтировать мотоцикл", "Нужно успеть за отпуск"));
        taskManager.deleteEpics();
        List<Epic> epics = taskManager.getEpics();
        assertTrue(epics.isEmpty(), "После удаления эпиков список должен быть пуст.");
    }

    @Test
    public void deleteSubtasksShouldReturnEmptyList() {
        Epic flatRenovation = new Epic("Отремонтировать мотоцикл", "Нужно успеть за отпуск");
        taskManager.addEpic(flatRenovation);
        taskManager.addSubtask(new Subtask("Переобуть колёса", "Заднее обязательно!",
                flatRenovation.getId()));
        taskManager.addSubtask(new Subtask("Заменить все жидкости", "Отходы утилизировать",
                flatRenovation.getId()));
        taskManager.addSubtask(new Subtask("Заказать новый монитор", "С частотой 120Гц",
                flatRenovation.getId()));

        taskManager.deleteSubtasks();
        List<Subtask> subtasks = taskManager.getSubtasks();
        assertTrue(subtasks.isEmpty(), "После удаления подзадач список должен быть пуст.");
    }

    @Test
    public void deleteTaskByIdShouldReturnNullIfKeyIsMissing() {
        taskManager.addTask(new Task(1, "Купить книги", "Список в заметках", Status.NEW));
        taskManager.addTask(new Task(2, "Помыть мотоцикл", "С новым шампунем", Status.DONE));
        assertNull(taskManager.deleteTaskByID(3));
    }

    @Test
    public void deleteEpicByIdShouldReturnNullIfKeyIsMissing() {
        taskManager.addEpic
                (new Epic(1, "Отремонтировать мотоцикл", "Нужно успеть за отпуск", Status.IN_PROGRESS));
        taskManager.deleteEpicByID(1);
        assertNull(taskManager.deleteTaskByID(1));
    }

    @Test
    public void deleteSubtaskByIdShouldReturnNullIfKeyIsMissing() {
        Epic flatRenovation = new Epic("Отремонтировать мотоцикл", "Нужно успеть за отпуск");
        taskManager.addEpic(flatRenovation);
        taskManager.addSubtask(new Subtask("Переобуть колёса", "Заднее обязательно!",
                flatRenovation.getId()));
        taskManager.addSubtask(new Subtask("Заменить все жидкости", "Отходы утилизировать",
                flatRenovation.getId()));
        taskManager.addSubtask(new Subtask("Заказать новый монитор", "С частотой 120Гц",
                flatRenovation.getId()));
        assertNull(taskManager.deleteSubtaskByID(5));
    }


    @Test
    void TaskCreatedAndTaskAddedShouldHaveSameVariables() {
        Task expected = new Task(1, "Помыть мотоцикл", "С новым шампунем", Status.DONE);
        taskManager.addTask(expected);
        List<Task> list = taskManager.getTasks();
        Task actual = list.getFirst();
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

}