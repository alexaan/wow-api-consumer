package api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Mounts {
    private Integer numCollected;
    private Integer numNotCollected;
    private List<Mount> collected;
    private List<Mount> notCollected;
}
