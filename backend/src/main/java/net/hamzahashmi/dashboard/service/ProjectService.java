package net.hamzahashmi.dashboard.service;

import net.hamzahashmi.dashboard.entity.Project;
import net.hamzahashmi.dashboard.repository.ProjectRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveProject(Project project){
        return projectRepository.save(project);
    }
    public Optional<Project> getById(ObjectId id){
        return projectRepository.findById(id);
    }
}
