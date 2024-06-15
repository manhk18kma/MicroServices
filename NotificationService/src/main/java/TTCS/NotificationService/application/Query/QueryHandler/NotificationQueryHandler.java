package TTCS.NotificationService.application.Query.QueryHandler;

import TTCS.NotificationService.Domain.Model.EmailOTP;
import TTCS.NotificationService.application.Query.Query.EmailOTPQueryGetAll;
import TTCS.NotificationService.application.Query.Query.EmailOTPQueryGetByID;
import TTCS.NotificationService.infrastructure.persistence.Repository.EmailOTPRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationQueryHandler {
    final EmailOTPRepository emailOTPRepository;

    @QueryHandler
    public List<EmailOTP> handle(EmailOTPQueryGetAll queryGetAll) {
        int pageNo = queryGetAll.getPageNo();
        int pageSize = queryGetAll.getPageSize();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<EmailOTP> page = emailOTPRepository.findAll(pageable);
        return page.getContent();
    }

    @QueryHandler
    public List<EmailOTP> handle(EmailOTPQueryGetByID queryGetAll) {
        int pageNo = queryGetAll.getPageNo();
        int pageSize = queryGetAll.getPageSize();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<EmailOTP> page = emailOTPRepository.findAllByIdAccount(queryGetAll.getIdAccount(),pageable);
        System.out.println("??");
        page.getContent().forEach(System.out::println);
        return page.getContent();
    }
}
