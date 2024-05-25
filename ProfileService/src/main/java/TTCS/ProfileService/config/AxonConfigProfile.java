package TTCS.ProfileService.config;

import com.thoughtworks.xstream.XStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

;


@Configuration
public class AxonConfigProfile {


    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
      
        xStream.allowTypesByWildcard(new String[] {
                "TTCS.**",
                "KMA.TTCS.**"
        });
        xStream.omitField(KMA.TTCS.CommonService.event.AccountProfile.ProfileCreateEvent.class, "id");
        return xStream;
    }


}
