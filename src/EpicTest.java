import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    public void EpicsWithEqualIdShouldBeEqual() {
        Epic epic1 = new Epic(10, "Навести порядок на даче", "Поставить беседку за 100к", Status.NEW);
        Epic epic2 = new Epic(10, "Сдать экзамен", "12 июня в 9:00",
                Status.IN_PROGRESS);
        assertEquals(epic1, epic2,
                "Ошибка! Наследники класса Task должны быть равны друг другу, если равен их id;");
    }
}