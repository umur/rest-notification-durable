package miu.edu.restnotifierdurable.durableQueue;


import lombok.Getter;
import miu.edu.restnotifierdurable.dto.DataDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Getter
public class DurableQueue {

    private Map<String, List<DataDto>> errorList = new HashMap<>();

    public void add(String server, DataDto dto) {
        if (!errorList.containsKey(server)) {
            errorList.put(server, new ArrayList<>());
        }
        errorList.get(server).add(dto);
    }

}
