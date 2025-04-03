public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Task wash = new Task("Помыть мотоцикл", "С новым шампунем");
        Task washCreated = taskManager.addTask(wash);
        System.out.println(washCreated);

        Task washTheEquipmentToUpgrade = new Task(wash.getId(), "Не забыть помыть мотоцикл",
                "Можно и без шампуня", Status.IN_PROGRESS);

        Task washUpdated = taskManager.updateTask(washTheEquipmentToUpgrade);
        System.out.println(washUpdated);

        Epic repair = new Epic("Отремонтировать мотоцикл", "Нужно успеть за отпуск");
        taskManager.addEpic(repair);
        System.out.println(repair);

        Subtask repairSubtask1 = new Subtask ("Переобуть колёса", "Заднее обязательно!",
                repair.getId());
        Subtask repairSubtask2 = new Subtask("Заменить все жидкости", "Отходы утилизировать",
                repair.getId());
        taskManager.addSubtask(repairSubtask1);
        taskManager.addSubtask(repairSubtask2);
        System.out.println(repair);
        repairSubtask2.setStatus(Status.DONE);
        taskManager.updateSubtask(repairSubtask2);
        System.out.println(repair);


    }
}
