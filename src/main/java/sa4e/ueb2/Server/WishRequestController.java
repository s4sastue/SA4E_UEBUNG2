package sa4e.ueb2.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WishRequestController{
    @Autowired
    private WishService wishService;

    @PostMapping(value = "/api", consumes = "application/json", produces = "application/json")
    public Wish registerWish(@RequestBody Wish wish) {
        return wishService.saveWish(wish);
    }

}