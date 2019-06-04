package top.luyuni.qaa.async;

public enum EventType {
    /**
     * 点赞事件
     */
    LIKE(0),
    /**
     * 评论事件
     */
    COMMENT(1),
    /**
     * 登录事件
     */
    LOGIN(2),
    /**
     * 邮件事件
     */
    MAIL(3),
    /**
     * 关注事件
     */
    FOLLOW(4),
    /**
     * 取关事件
     */
    UNFOLLOW(5),
    /**
     * 提问事件
     */
    ADD_QUESTION(6);

    private int value;
    EventType(int value) { this.value = value; }
    public int getValue() { return value; }
}
