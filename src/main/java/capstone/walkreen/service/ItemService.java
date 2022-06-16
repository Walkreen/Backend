package capstone.walkreen.service;

import capstone.walkreen.dto.*;
import capstone.walkreen.entity.Daily;
import capstone.walkreen.entity.User;
import capstone.walkreen.exception.NotExistMonthDailyException;
import capstone.walkreen.repository.DailyRepository;
import capstone.walkreen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.FetchProfile;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//import static com.sun.tools.doclint.Entity;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final AuthService authService;
    private final DailyRepository dailyRepository;
    private final UserRepository userRepository;

    public ItemResponse getItem(HttpServletRequest httpServletRequest){
        final User user = authService.getUserByToken(httpServletRequest);

        ItemResponse itemResponse = new ItemResponse(user.getPrepoint(),user.getItem());

        return itemResponse;
    }

    public ItemResponse buyItem(ItemRequest itemRequest ,HttpServletRequest httpServletRequest){
        final User user = authService.getUserByToken(httpServletRequest);

        user.setItem(itemRequest.getItem());
        user.setPrepoint(itemRequest.getPrepoint());
        userRepository.save(user);

        ItemResponse userItemResponse = new ItemResponse(user.getPrepoint(),user.getItem());

        return userItemResponse;
    }

}
