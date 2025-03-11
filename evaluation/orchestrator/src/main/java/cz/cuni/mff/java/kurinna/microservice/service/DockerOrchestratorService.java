package cz.cuni.mff.java.kurinna.microservice.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.core.DockerClientBuilder;
import org.springframework.stereotype.Service;

@Service
public class DockerOrchestratorService {

    private final DockerClient dockerClient;

    public DockerOrchestratorService() {
        this.dockerClient = DockerClientBuilder.getInstance().build();
    }

    public String startService(String composeFile) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("docker-compose", "-f", composeFile, "up", "-d");
        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            return "Service started using " + composeFile;
        } else {
            throw new RuntimeException("Failed to start service with " + composeFile);
        }
    }
}

