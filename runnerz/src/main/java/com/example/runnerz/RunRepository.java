package com.example.runnerz;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class RunRepository {
    private final JdbcClient jdbcClient;

    public RunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Run> getAll() {
        return jdbcClient.sql("SELECT * FROM Run").query(Run.class).list();
    }

    public Optional<Run> getById(Integer id) {
        return jdbcClient.sql("SELECT id, title, started_on, completed_on, miles, location FROM Run WHERE id = :id")
                .param("id", id).query(Run.class).optional();
    }

    public void create(Run run) {
        var updated = jdbcClient.sql("INSERT INTO Run(title, started_on, completed_on, miles, location) VALUES (?, ?, ?, ?, ?)")
                .params(List.of(run.getTitle(), run.getStartedOn(), run.getCompletedOn(), run.getMiles(), run.getLocation().toString()))
                .update();

        Assert.state(updated == 1, "Failed to create run " + run.getTitle());
    }

    public void update(Run run, Integer id) {
        var updated = jdbcClient.sql("UPDATE Run SET title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? WHERE id = ?")
                .params(List.of(run.getTitle(), run.getStartedOn(), run.getCompletedOn(), run.getMiles(), run.getLocation().toString(), id))
                .update();

        Assert.state(updated == 1, "Failed to update run " + run.getTitle());
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("DELETE FROM Run WHERE id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete run " + id);
    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM Run").query().listOfRows().size();
    }

    public void saveAll(List<Run> runs) {
        runs.forEach(this::create);
    }

    public List<Run> findByLocation(String location) {
        return jdbcClient.sql("SELECT * FROM Run WHERE location = :location")
                .param("location", location).query(Run.class).list();
    }
}
