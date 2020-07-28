package miu.edu.restnotifierdurable.notifier;

import miu.edu.restnotifierdurable.dto.DataDto;
import miu.edu.restnotifierdurable.durableQueue.DurableQueue;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;

@Component
public class DurableNotifier {

    private final DurableQueue durableQueue;

    public DurableNotifier(DurableQueue durableQueue) {
        this.durableQueue = durableQueue;
    }

    public void notifyServers(){
       for(String server: durableQueue.getErrorList().keySet()){
           List<DataDto> datas = durableQueue.getErrorList().get(server);
           Iterator i = datas.iterator();
           System.out.println("Trying to access : " + server);
           while (i.hasNext()) {
               boolean isConnected =true;
               RestTemplate restTemplate = new RestTemplate();
               DataDto dto = (DataDto) i.next();
               HttpEntity<DataDto> entity = new HttpEntity<>(dto);
               try {
                   System.out.println("Trying to send: " + dto);
                   restTemplate.postForObject(server, entity, DataDto.class);
               } catch (ResourceAccessException e) {
                   isConnected=false;
               }
               if (isConnected){
                   i.remove();
               }
           }
           System.out.println("- - - - - - - - - - - - - -");
       }
    }

}
