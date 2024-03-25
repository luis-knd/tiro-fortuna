package factories;

import com.github.javafaker.Faker;
import com.tirofortuna.controllers.dto.DrawDTO;
import com.tirofortuna.entities.Draw;
import com.tirofortuna.entities.Game;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DrawFactory {

    public static Draw createFakeDraw() {
        Faker faker = new Faker();
        Game game = GameFactory.createFakeGame();
        return new Draw(
            faker.number().randomNumber(),
            faker.date().past(1, TimeUnit.DAYS),
            game,
            null
        );
    }

    public static DrawDTO createFakeDrawDTO() {
        Faker faker = new Faker();
        Game game = GameFactory.createFakeGame();
        return new DrawDTO(
            null,
            faker.date().past(1, TimeUnit.DAYS),
            game,
            null
        );
    }

    public static List<Draw> createFakeDrawList(int quantity) {
        Faker faker = new Faker();
        List<Draw> draws = new java.util.ArrayList<>(List.of());
        for (long id = 1L; id <= quantity; id++) {
            draws.add(
                new Draw(
                    id,
                    faker.date().past((int) id, TimeUnit.DAYS),
                    GameFactory.createFakeGame(),
                    null)
            );
        }
        return draws;
    }
}
