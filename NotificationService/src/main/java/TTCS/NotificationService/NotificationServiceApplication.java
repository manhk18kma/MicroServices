package TTCS.NotificationService;

import TTCS.NotificationService.config.NotificationConfigXtream;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import java.util.*;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@Import({NotificationConfigXtream.class})

public class NotificationServiceApplication implements CommandLineRunner  {


	@Autowired
	private ChatMessageRepository chatMessageRepository;
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	@Autowired
	private ChatProfileRepository chatProfileRepository;
	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
//		// Tạo ChatProfile
//		ChatProfile chatProfile1 = ChatProfile.builder()
//				.id(UUID.randomUUID().toString())
//				.name("chatProfile1")
//				.chatRoomLastUsed(new HashMap<>())
//				.build();
//
//		ChatProfile chatProfile2 = ChatProfile.builder()
//				.id(UUID.randomUUID().toString())
//				.name("chatProfile2")
//				.chatRoomLastUsed(new HashMap<>())
//				.build();
//
//		// Tạo ChatRoom
//		ChatRoom chatRoom = ChatRoom.builder()
//				.id(UUID.randomUUID().toString())
//				.chatRoomName("General")
//				.idMessages(new HashSet<>())
//				.idChatProfiles(new HashSet<>())
//				.build();
//
//		// Tạo ChatMessage
//		ChatMessage message1 = ChatMessage.builder()
//				.id(UUID.randomUUID().toString())
//				.content("content1")
//				.idChatRoom(chatRoom.getId())
//				.idChatProfile(chatProfile1.getId())
//				.timeStamp(new Date())
//				.build();
//
//		ChatMessage message2 = ChatMessage.builder()
//				.id(UUID.randomUUID().toString())
//				.content("content2")
//				.idChatRoom(chatRoom.getId())
//				.idChatProfile(chatProfile1.getId())
//				.timeStamp(new Date())
//				.build();
//
//
//		ChatMessage message3 = ChatMessage.builder()
//				.id(UUID.randomUUID().toString())
//				.content("content3")
//				.idChatRoom(chatRoom.getId())
//				.idChatProfile(chatProfile2.getId())
//				.timeStamp(new Date())
//				.build();
//
//		// Thêm các message vào chatRoom
//		chatRoom.getIdMessages().add(message1.getId());
//		chatRoom.getIdMessages().add(message2.getId());
//		chatRoom.getIdMessages().add(message3.getId());
//
//		// Thêm các chatProfile vào chatRoom
//		chatRoom.getIdChatProfiles().add(chatProfile1.getId());
//		chatRoom.getIdChatProfiles().add(chatProfile2.getId());
//
//		// Set chatRoom cho các message
//		message1.setIdChatRoom(chatRoom.getId());
//		message2.setIdChatRoom(chatRoom.getId());
//		message3.setIdChatRoom(chatRoom.getId());
//
//		// Thiết lập thời gian sử dụng mới nhất cho các chatProfile
//		chatProfile1.getChatRoomLastUsed().put(chatRoom.getId(), new Date(System.currentTimeMillis()));
//		chatProfile2.getChatRoomLastUsed().put(chatRoom.getId(), new Date(System.currentTimeMillis() + 60000));
//		// Lưu các đối tượng vào cơ sở dữ liệu
//		chatProfileRepository.save(chatProfile1);
//		chatProfileRepository.save(chatProfile2);
//		chatRoomRepository.save(chatRoom);
//		chatMessageRepository.save(message1);
//		chatMessageRepository.save(message2);
//		chatMessageRepository.save(message3);
	}

}
