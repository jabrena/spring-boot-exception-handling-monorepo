package microservices.infrastructure.rest;

import microservices.application.MyGOTOException;
import microservices.application.MyOptionalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/optional",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class OptionalController {

    private MyOptionalService myService;

    public OptionalController(MyOptionalService myService) {
        this.myService = myService;
    }

    @GetMapping("resource")
    public @ResponseBody ResponseEntity<OkResponse> resource(@RequestParam Integer id) {
        return toOkResponse(myService.service(id));
    }

    //Scenario forced by the Developer
    @GetMapping("resource2")
    public @ResponseBody ResponseEntity<OkResponse> resource2() throws MyGOTOException {
        return toOkResponse(myService.service2());
    }

    private ResponseEntity<OkResponse> toOkResponse(Optional<String> response) {
        return response
                .map(value -> ResponseEntity.ok().body(new OkResponse(value)))
                .orElse(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }
}
