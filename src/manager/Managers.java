package manager;

public class Managers {

    // Метод возвращает экземпляр TaskManager
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    // Метод для получения менеджера истории
    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
