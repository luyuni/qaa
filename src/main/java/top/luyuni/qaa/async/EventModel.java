package top.luyuni.qaa.async;

import java.util.HashMap;
import java.util.Map;

/**
 * 事件发生现场
 */
public class EventModel {
    /**
     * 事件类型         比如你点赞了评论，那么这个就是点赞评论事件
     */
    private EventType type;
    /**
     * 触发者            比如你点赞了评论，那么这个就是你
     */
    private int actorId;
    /**
     * 触发对象           比如你点赞了评论，那么这个就是评论
     */
    private int entityType;
    /**
     * 触发对象的id        比如你给评论点赞，这个就是被赞的那条评论
     */
    private int entityId;
    /**
     * 触发对象的拥有者id    比如你给点赞，评论点赞这个就是评论的人
     */
    private int entityOwnerId;

    /**
     * 扩展字段
     */
    private Map<String, String> exts = new HashMap<String, String>();

    public EventModel() { }

    public EventModel setExt(String key, String value) {
        exts.put(key, value);
        return this;
    }

    public EventModel(EventType type) {
        this.type = type;
    }

    public String getExt(String key) {
        return exts.get(key);
    }


    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }
}
