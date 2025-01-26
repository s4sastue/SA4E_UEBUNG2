package sa4e.ueb2.Server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    @Autowired
    private WishRepository wishRepository;

    public Wish saveWish(Wish wish) {
        return wishRepository.save(wish);
    }

}
