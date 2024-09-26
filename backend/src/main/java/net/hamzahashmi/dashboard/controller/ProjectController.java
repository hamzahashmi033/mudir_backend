package net.hamzahashmi.dashboard.controller;

import net.hamzahashmi.dashboard.entity.Project;
import net.hamzahashmi.dashboard.entity.User;
import net.hamzahashmi.dashboard.repository.ProjectRepository;
import net.hamzahashmi.dashboard.service.ProjectService;
import net.hamzahashmi.dashboard.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectRepository projectRepository;
    @PostMapping("/create/{email}")
    public ResponseEntity<?> createProject(@RequestBody Project project, @PathVariable String email){
        try {
        User user = userService.findUserByEmail(email);
        String message;
        if (user != null){
            Project newProject = projectService.saveProject(project);
            user.getProjects().add(newProject);
            userService.saveUser(user);
            message = "Project has been created successfully";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        message = "User not found";
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }catch (RuntimeException e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all-projects/{email}")
    public ResponseEntity<?> getAllProjectsOfUser(@PathVariable String email){
        try {

        User user = userService.findUserByEmail(email);
        List<Project> all = user.getProjects();
        return new ResponseEntity<>(all,HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/single-project/{projectId}/{email}")
    public ResponseEntity<?> getSingleProjectById(@PathVariable ObjectId projectId,@PathVariable String email){
        User user = userService.findUserByEmail(email);
        List<Project> collect = user.getProjects()
                .stream().filter(x->x.getId().equals(projectId))
                .collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<Project> project = projectService.getById(projectId);
            if(project.isPresent()){
                return new ResponseEntity<>(project.get(),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/delete/{projectId}/{email}")
    public ResponseEntity<?> deleteProjectById(@PathVariable ObjectId projectId,@PathVariable String email){
        boolean removed = false;
        String message;
        try{
         User user = userService.findUserByEmail(email);
         removed = user.getProjects().removeIf(x->x.getId().equals(projectId));
         if(removed){
             userService.saveUser(user);
             projectRepository.deleteById(projectId);
             message = "Deleted project Successfully";
             return new ResponseEntity<>(message,HttpStatus.OK);
         }else{
             message = "Project Not Found";
             return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
         }
        }catch (RuntimeException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/update/{projectId}/{email}")
    public ResponseEntity<?> updateProjectById(@PathVariable ObjectId projectId,@PathVariable String email,@RequestBody Project newProject){
        User user = userService.findUserByEmail(email);
        String message;
        List<Project> collect = user.getProjects()
                .stream().filter(x->x.getId().equals(projectId))
                .collect(Collectors.toList());
        if(!collect.isEmpty()){
            Optional<Project> project = projectService.getById(projectId);
            if(project.isPresent()){
                Project foundProject = project.get();
                foundProject.setTitle(newProject.getTitle() != null && !newProject.getTitle().isEmpty() ? newProject.getTitle() : foundProject.getTitle());
                foundProject.setCategory(newProject.getCategory() != null && !newProject.getCategory().isEmpty() ? newProject.getCategory() : foundProject.getCategory());
                foundProject.setRemarks(newProject.getRemarks() != null && !newProject.getRemarks().isEmpty() ? newProject.getRemarks() : foundProject.getRemarks());
                if (!newProject.getCategory().isEmpty()) {
                    foundProject.getTags().addAll(newProject.getTags());
                }
                projectService.saveProject(foundProject);
                message = "Project Updated Successfully";
                return new ResponseEntity<>(message,HttpStatus.OK);
            }
        }
        message = "Project not found";
        return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
    }
}
