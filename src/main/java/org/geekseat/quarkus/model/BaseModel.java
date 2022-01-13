package org.geekseat.quarkus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import org.geekseat.quarkus.helper.Utility;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@MappedSuperclass
public class BaseModel extends PanacheEntity {

    @JsonIgnore
    @Column(name = "created_by")
    private String createdBy;
    @JsonIgnore
    @Column(name = "updated_by")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public String getMapData() {
        return mapData;
    }

    public void setMapData(String mapData) {
        this.mapData = mapData;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getTransitMapData() {
        return transitMapData;
    }

    public void setTransitMapData(String transitMapData) {
        this.transitMapData = transitMapData;
    }

    public Map<String, Object> getTransitMap() {
        return transitMap;
    }

    public void setTransitMap(Map<String, Object> transitMap) {
        this.transitMap = transitMap;
    }
}
