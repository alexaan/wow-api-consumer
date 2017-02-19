package api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Mount {
    String name;
    Long spellId;
    Long creatureId;
    Long itemId;
    Integer qualityId;
    String icon;
    Boolean isGround;
    Boolean isFlying;
    Boolean isAquatic;
    Boolean isJumping;
}
