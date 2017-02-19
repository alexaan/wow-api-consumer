package api.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import api.dto.Mounts;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CharacterMountsResult {
    private Long lastModified;
    private String name;
    private String realm;
    private String battlegroup;
    //private Integer class;
    private Integer race;
    private Integer gender;
    private Integer level;
    private Long achievementPoints;
    private String thumbNail;
    private String calcClass;
    private Integer faction;
    private Mounts mounts;
}
