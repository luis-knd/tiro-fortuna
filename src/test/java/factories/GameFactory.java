package factories;

import com.github.javafaker.Faker;
import com.tirofortuna.controllers.dto.GameDTO;
import com.tirofortuna.entities.Game;

import java.util.List;

public class GameFactory {
    public static Game createFakeGame() {
        Faker faker = new Faker();
        return new Game(faker.number().randomNumber(), faker.app().name(), null);
    }

    public static GameDTO createFakeGameDTO() {
        Faker faker = new Faker();
        return new GameDTO(faker.number().randomNumber(), faker.app().name(), null);
    }

    public static List<Game> createFakeGameList(int quantity) {
        Faker faker = new Faker();
        List<Game> games = new java.util.ArrayList<>(List.of());
        for (long id = 1L; id <= quantity; id++) {
            games.add(new Game(id, faker.app().name(), null));
        }
        return games;
    }
}
