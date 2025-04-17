package task;

import enums.Status;
import task.Task;

import java.util.Objects;

public class Subtask extends Task {

    private final int epicID;

    public Subtask(String name, String description, int epicID) {
        super(name, description);
        if (epicID <= 0 || epicID == getId()) {
            throw new IllegalArgumentException("Invalid epicID");
        }
        this.epicID = epicID;
    }

    public Subtask(int id, String name, String description, Status status, int epicID) {
        super(id, name, description, status);
        if (epicID <= 0 || epicID == id) {
            throw new IllegalArgumentException("Invalid epicID");
        }
        this.epicID = epicID;
    }

    public int getEpicID() {
        return epicID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask that = (Subtask) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", epicID=" + epicID +
                ", status=" + getStatus() +
                '}';
    }
}