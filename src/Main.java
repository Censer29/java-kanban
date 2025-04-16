public class Main {

    public static void main(String[] args) {

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Task wash = new Task("Помыть мотоцикл", "С новым шампунем");
        Task washCreated = inMemoryTaskManager.addTask(wash);
        System.out.println(washCreated);

        Task washTheEquipmentToUpgrade = new Task(wash.getId(), "Не забыть помыть мотоцикл",
                "Можно и без шампуня", Status.IN_PROGRESS);

        Task washUpdated = inMemoryTaskManager.updateTask(washTheEquipmentToUpgrade);
        System.out.println(washUpdated);

        Epic repair = new Epic("Отремонтировать мотоцикл", "Нужно успеть за отпуск");
        inMemoryTaskManager.addEpic(repair);
        System.out.println(repair);

        Subtask repairSubtask1 = new Subtask ("Переобуть колёса", "Заднее обязательно!",
                repair.getId());
        Subtask repairSubtask2 = new Subtask("Заменить все жидкости", "Отходы утилизировать",
                repair.getId());
        inMemoryTaskManager.addSubtask(repairSubtask1);
        inMemoryTaskManager.addSubtask(repairSubtask2);
        System.out.println(repair);
        repairSubtask2.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubtask(repairSubtask2);
        System.out.println(repair);
    }
}
