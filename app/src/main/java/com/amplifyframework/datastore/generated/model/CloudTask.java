package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the CloudTask type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "CloudTasks")
@Index(name = "byTask", fields = {"cloudTeamId"})
public final class CloudTask implements Model {
  public static final QueryField ID = field("CloudTask", "id");
  public static final QueryField NAME = field("CloudTask", "name");
  public static final QueryField DESCRIPTION = field("CloudTask", "description");
  public static final QueryField STATE = field("CloudTask", "state");
  public static final QueryField TEAM = field("CloudTask", "cloudTeamId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String name;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="String") String state;
  private final @ModelField(targetType="CloudTeam", isRequired = true) @BelongsTo(targetName = "cloudTeamId", type = CloudTeam.class) CloudTeam team;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getState() {
      return state;
  }
  
  public CloudTeam getTeam() {
      return team;
  }
  
  private CloudTask(String id, String name, String description, String state, CloudTeam team) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.state = state;
    this.team = team;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      CloudTask cloudTask = (CloudTask) obj;
      return ObjectsCompat.equals(getId(), cloudTask.getId()) &&
              ObjectsCompat.equals(getName(), cloudTask.getName()) &&
              ObjectsCompat.equals(getDescription(), cloudTask.getDescription()) &&
              ObjectsCompat.equals(getState(), cloudTask.getState()) &&
              ObjectsCompat.equals(getTeam(), cloudTask.getTeam());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDescription())
      .append(getState())
      .append(getTeam())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("CloudTask {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("state=" + String.valueOf(getState()) + ", ")
      .append("team=" + String.valueOf(getTeam()))
      .append("}")
      .toString();
  }
  
  public static TeamStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static CloudTask justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new CloudTask(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      description,
      state,
      team);
  }
  public interface TeamStep {
    BuildStep team(CloudTeam team);
  }
  

  public interface BuildStep {
    CloudTask build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep name(String name);
    BuildStep description(String description);
    BuildStep state(String state);
  }
  

  public static class Builder implements TeamStep, BuildStep {
    private String id;
    private CloudTeam team;
    private String name;
    private String description;
    private String state;
    @Override
     public CloudTask build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new CloudTask(
          id,
          name,
          description,
          state,
          team);
    }
    
    @Override
     public BuildStep team(CloudTeam team) {
        Objects.requireNonNull(team);
        this.team = team;
        return this;
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep state(String state) {
        this.state = state;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, String description, String state, CloudTeam team) {
      super.id(id);
      super.team(team)
        .name(name)
        .description(description)
        .state(state);
    }
    
    @Override
     public CopyOfBuilder team(CloudTeam team) {
      return (CopyOfBuilder) super.team(team);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder state(String state) {
      return (CopyOfBuilder) super.state(state);
    }
  }
  
}
