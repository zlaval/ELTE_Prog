package com.zlrx.elte.snake.repository;

import com.zlrx.elte.snake.configuration.DatabaseConfiguration;
import com.zlrx.elte.snake.model.Score;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ScoreRepository {

    private static final String SCORE_SELECT_SQL = "select player_name, score from score order by score desc limit 10 ";
    private static final String SCORE_INSERT_SQL = "insert into score (player_name,score) values (?,?) ";


    public List<Score> load() {
        List<Score> scores = new ArrayList<>();
        Connection connection = DatabaseConfiguration.getINSTANCE().getConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(SCORE_SELECT_SQL);
            while (rs.next()) {
                var score = new Score(
                        rs.getString("player_name"),
                        rs.getInt("score")
                );
                scores.add(score);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scores;
    }

    public void save(Score score) {
        Connection connection = DatabaseConfiguration.getINSTANCE().getConnection();
        try (PreparedStatement st = connection.prepareStatement(SCORE_INSERT_SQL)) {
            st.setString(1, score.getName());
            st.setInt(2, score.getScore());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
