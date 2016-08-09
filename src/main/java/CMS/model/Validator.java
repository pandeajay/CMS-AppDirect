package CMS.model;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import CMS.model.User;

@Component
public class Validator {

    public Optional<ProcessResult> checkEventType(Optional<EventType> eventType){
        if(!eventType.isPresent()){
            return Optional.of(new ProcessResult(EventErrorStatus.INVALID_RESPONSE));
        }
        return Optional.empty();
    }

    public Optional<ProcessResult> checkAlreadyAssigned(User user, List<User> users){
        if(new HashSet<>(users).contains(user)){
            return Optional.of(new ProcessResult(EventErrorStatus.USER_ALREADY_EXISTS));
        }
        return Optional.empty();
    }

    public Optional<ProcessResult> checkUnassignedUser(User user, List<User> users){
        if(!new HashSet<>(users).contains(user)){
            return Optional.of(new ProcessResult(EventErrorStatus.USER_NOT_FOUND));
        }
        return Optional.empty();
    }

    public Optional<ProcessResult> checkSubscription(Optional<Subscription> subscription){
        if(!subscription.isPresent()){
            return Optional.of(new ProcessResult(EventErrorStatus.ACCOUNT_NOT_FOUND));
        }
        return Optional.empty();
    }

}
