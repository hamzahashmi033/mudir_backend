package net.hamzahashmi.dashboard.repository;

import net.hamzahashmi.dashboard.entity.Project;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, ObjectId> {
}
