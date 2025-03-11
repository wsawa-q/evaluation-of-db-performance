package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.microservice.service.DockerOrchestratorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orchestrator")
public class OrchestratorController {

    private final DockerOrchestratorService dockerService;

    public OrchestratorController(DockerOrchestratorService dockerService) {
        this.dockerService = dockerService;
    }

    @PostMapping("/start")
    public String startService(@RequestParam String composeFile) throws Exception {
        return dockerService.startService(composeFile);
    }
}

