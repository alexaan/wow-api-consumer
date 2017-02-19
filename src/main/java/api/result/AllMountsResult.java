package api.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import api.dto.Mount;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AllMountsResult {
    List<Mount> mounts;
}
