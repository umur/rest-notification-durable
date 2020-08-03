package miu.edu.server24.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/actions")
public class ActionController {

    @PostMapping
    public void notifyMe(@RequestBody miu.edu.server24.controller.DataDto dataDto) {
        System.out.println(dataDto);
    }

}

@Getter
@Setter
@Data
class DataDto {
    private String data;
}
