package TTCS.ProfileService.config;

import com.thoughtworks.xstream.XStream;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
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
                "KMA.TTCS.**",
                "**"
        });

        xStream.omitField(KMA.TTCS.CommonService.event.AccountProfile.ProfileCreateEvent.class, "id");
        return xStream;
    }
//    @Bean
//    public Serializer serializer() {
//        XStream xStream = new XStream();
//        xStream.allowTypesByWildcard(new String[] {
//                "TTCS.**",
//                "KMA.TTCS.**"
//        });
//
//        // Đăng ký các converter mặc định
//        xStream.registerConverter(new com.thoughtworks.xstream.converters.collections.CollectionConverter(xStream.getMapper()));
//        xStream.registerConverter(new com.thoughtworks.xstream.converters.collections.MapConverter(xStream.getMapper()));
//        xStream.registerConverter(new com.thoughtworks.xstream.converters.extended.ISO8601DateConverter());
//        return XStreamSerializer.builder().xStream(xStream).build();
//    }

}
