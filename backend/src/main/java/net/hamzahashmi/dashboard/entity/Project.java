package net.hamzahashmi.dashboard.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import net.hamzahashmi.dashboard.serializer.ObjectIdSerializer;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "projects")
@Getter
@Setter
public class Project {
    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    private String title;
    private String category;
    private Integer stage;
    private String Status;
    private List<String> tags = new ArrayList<>();
    private String remarks;
}
