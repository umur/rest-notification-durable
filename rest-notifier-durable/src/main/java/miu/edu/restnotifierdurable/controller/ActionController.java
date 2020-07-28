package miu.edu.restnotifierdurable.controller;

import miu.edu.restnotifierdurable.dto.DataDto;
import miu.edu.restnotifierdurable.durableQueue.DurableQueue;
import miu.edu.restnotifierdurable.notifier.DurableNotifier;
import miu.edu.restnotifierdurable.servers.ServerList;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/actions")
public class ActionController {

    private final ServerList serverList;
    private final DurableQueue durableQueue;
    private final DurableNotifier durableNotifier;

    public ActionController(ServerList serverList,
                            DurableQueue durableQueue,
                            DurableNotifier durableNotifier) {
        this.serverList = serverList;
        this.durableQueue = durableQueue;
        this.durableNotifier = durableNotifier;
    }

    @GetMapping
    public String notifyServers() {
        System.out.println("notifying");

        // setting dto
        RestTemplate restTemplate = new RestTemplate();
        DataDto dto = new DataDto("umur");
        HttpEntity<DataDto> entity = new HttpEntity<>(dto);

        // notifying
        for (int i = 0; i < serverList.getServerList().size(); i++) {
            try {
                restTemplate.postForObject(serverList.getServerList().get(i), entity, DataDto.class);
            } catch (ResourceAccessException e) {
                durableQueue.add(serverList.getServerList().get(i), dto);
            }
        }
        return "notifying . . .";
    }

    @GetMapping("/errors")
    public Map<String, List<DataDto>> getErrorsInTheMap() {
        return durableQueue.getErrorList();
    }

    @GetMapping("/start")
    public void start(){
        durableNotifier.notifyServers();
    }

}


