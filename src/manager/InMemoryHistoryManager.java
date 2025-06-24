package manager;

import task.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> history;
    private Node head;
    private Node tail;

    public InMemoryHistoryManager() {
        this.history = new HashMap<>();
    }

    private static class Node {
        Task task;
        Node prev;
        Node next;

        Node(Task task) {
            this.task = task;
        }
    }

    // Метод добавления узла в конец списка
    private void linkLast(Node node) {
        if (node == null) return;

        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail;
        }
        tail = node;
    }

    // Метод удаления узла из списка
    private void removeNode(Node node) {
        if (node == null) return;

        // Обновляем ссылки предыдущего узла
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next; // Если удаляем head
        }

        // Обновляем ссылки следующего узла
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev; // Если удаляем tail
        }
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        // Удаляем существующую задачу, если она есть
        Node existingNode = history.get(task.getId());
        if (existingNode != null) {
            removeNode(existingNode);
        }

        // Создаем новый узел и добавляем его
        Node newNode = new Node(task);
        linkLast(newNode);
        history.put(task.getId(),newNode);
    }

    @Override
    public void remove(int id) {
        Node node = history.get(id);
        if (node != null) {
            removeNode(node);
            history.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> historyList = new ArrayList<>();
        Node current = head;
        while (current != null) {
            historyList.add(current.task);
            current = current.next;
        }
        return historyList;
    }

    @Override
    public void remove(CharSequence id) {

    }
}
