package com.example.runnerz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    private final RunRepository runRepository;

    public RunController(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    @GetMapping("")
    List<Run> getAll(){
        return runRepository.getAll();
    }

    @GetMapping("/{id}")
    Optional<Run> getById(@PathVariable Integer id){
        return runRepository.getById(id);
    }

    @PostMapping("")
    void addRun(@RequestBody Run run){
        runRepository.create(run);
    }

    @PutMapping("/{id}")
    void updateRun(@RequestBody Run run, @PathVariable Integer id){
        runRepository.update(run,id);
    }

    @DeleteMapping("/{id}")
    void deleteRun(@PathVariable Integer id){
        runRepository.delete(id);
    }
}
