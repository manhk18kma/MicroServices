package TTCS.IdentityService.config;

import com.thoughtworks.xstream.XStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {
 
    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
      
        xStream.allowTypesByWildcard(new String[] {
                "TTCS.**",
                "KMA.TTCS.**",
                "**"
        });

        xStream.omitField(TTCS.IdentityService.application.Command.CommandEvent.Event.AccountActiveEvent.class, "id");
        xStream.omitField(KMA.TTCS.CommonService.event.AccountProfile.ProfileCreateEvent.class, "id");
        xStream.omitField(TTCS.IdentityService.application.Command.CommandEvent.Event.AccountActiveEvent.class, "userStatus");


        return xStream;
    }
}
