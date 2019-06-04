package top.luyuni.qaa.async.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.luyuni.qaa.async.EventHandler;
import top.luyuni.qaa.async.EventModel;
import top.luyuni.qaa.async.EventType;
import top.luyuni.qaa.service.ISearchService;

import java.util.Arrays;
import java.util.List;

@Component
public class AddQuestionHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(AddQuestionHandler.class);
    @Autowired
    private ISearchService ISearchService;

    @Override
    public void doHandle(EventModel model) {
        try {
            ISearchService.indexQuestion(model.getEntityId(),
                    model.getExt("title"), model.getExt("content") );
        } catch (Exception e) {
            logger.error("增加题目索引失败");
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.ADD_QUESTION);
    }
}
