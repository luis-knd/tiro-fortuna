package factories;

import com.github.javafaker.Faker;
import com.tirofortuna.controllers.dto.DrawResultDTO;
import com.tirofortuna.entities.Draw;
import com.tirofortuna.entities.DrawResult;

import java.util.List;

public class DrawResultFactory {
    public static DrawResult createFakeDrawResult() {
        Faker faker = new Faker();
        return new DrawResult(
            faker.number().randomNumber(),
            DrawFactory.createFakeDraw(),
            faker.code().isbnGroup()
        );
    }

    public static DrawResultDTO createFakeDrawResultDTO() {
        Faker faker = new Faker();
        return new DrawResultDTO(
            null,
            DrawFactory.createFakeDraw(),
            faker.code().isbnGroup()
        );
    }

    public static List<DrawResult> createFakeDrawResultList(int quantity) {
        Faker faker = new Faker();
        List<Draw> draws = DrawFactory.createFakeDrawList(quantity);
        List<DrawResult> drawResults = new java.util.ArrayList<>(List.of());
        for (long id = 1L; id <= quantity; id++) {
            drawResults.add(
                new DrawResult(
                    id,
                    draws.get((int) id - 1),
                    faker.code().isbnGroup()
                )
            );
        }
        return drawResults;
    }
}
