package top.luyuni.qaa.async;

import java.util.List;

/**
 * 事件取出后由谁处理
 */
public interface EventHandler {
    /**
     * 处理事件
     * @param model
     */
    void doHandle(EventModel model);

    /**
     * Handler关心哪些事件Event
     * @return
     */
    List<EventType> getSupportEventTypes();
}
