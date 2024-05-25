package TTCS.IdentityService.application.Command.Saga;

import KMA.TTCS.CommonService.command.AccountProfileCommand.ProfileCreateCommand;
import KMA.TTCS.CommonService.event.AccountProfile.ProfileCreateEvent;
import TTCS.IdentityService.application.Command.CommandEvent.Event.AccountCreateEvent;
import TTCS.IdentityService.domain.enumType.Gender;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.*;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Saga
public class AccountSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "idAccount")
    private void handle(AccountCreateEvent event) {
        System.out.println("AccountCreateEvent in Saga for AccountID : "+event.getIdAccount());
        log.info(event.toString());
        try {
            SagaLifecycle.associateWith("idProfile", event.getIdProfile());
            ProfileCreateCommand command = new ProfileCreateCommand();
            command.setIdAccount(event.getIdAccount());
            command.setIdProfile(event.getIdProfile());
            command.setFullName(event.getFullName());
            command.setUrlProfilePicture(event.getUrlProfilePicture());
            command.setBiography(event.getBiography());
            command.setDateOfBirth(event.getDateOfBirth());

            if(event.getGender().equals(Gender.MALE)){
                command.setGender(KMA.TTCS.CommonService.enumType.Gender.MALE);
            }else if (event.getGender().equals(Gender.FEMALE)) {
                command.setGender(KMA.TTCS.CommonService.enumType.Gender.FEMALE);
            }else {
                command.setGender(KMA.TTCS.CommonService.enumType.Gender.OTHER);
            }
            commandGateway.send(command);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    @EndSaga
    @SagaEventHandler(associationProperty = "idProfile")
    private void handle(ProfileCreateEvent event) {
        System.out.println("ProfileCreateEvent : ProfileCreate Successfully: "+event.getIdProfile());
            log.info(event.toString());
        }
    }
