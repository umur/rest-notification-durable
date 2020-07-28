package miu.edu.restnotifierdurable.servers;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class ServerList {

    private List<String> serverList = new ArrayList<>();

    public ServerList(){
        serverList.add("http://localhost:8020/api/actions");
        serverList.add("http://localhost:8021/api/actions");
        serverList.add("http://localhost:8022/api/actions");
    }


}
