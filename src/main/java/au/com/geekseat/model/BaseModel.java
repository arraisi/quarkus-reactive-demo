package au.com.geekseat.model;

import au.com.geekseat.helper.Utility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@MappedSuperclass
public class BaseModel {
    @Id
    @GeneratedValue
    private Long id;
    @JsonIgnore
    protected String creator;
    @JsonIgnore
    protected String editor;
    @JsonIgnore
    private String createdBy;
    @JsonIgnore
    private String updatedBy;
    @JsonIgnore
    private LocalDateTime created;
    @JsonIgnore
    private LocalDateTime updated;
    @JsonIgnore
    protected String mapData;
    @Transient
    protected Map<String, Object> map = new HashMap<>();
    @Transient
    @JsonIgnore
    protected String transitMapData;
    @Transient
    protected Map<String, Object> transitMap = new HashMap<>();

    public void createdBy() {
        created = Utility.now();
//        setCreatorId(principal.getId());
//        setCreator(Utility.gson.toJson(principal.essence()));
    }

    public void updatedBy() {
        this.updated = Utility.now();
//        setEditorId(principal.getId());
//        setEditor(Utility.gson.toJson(principal.essence()));
    }
}
