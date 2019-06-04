package top.luyuni.qaa.service;

import org.springframework.beans.factory.InitializingBean;

public interface ISensitiveService extends InitializingBean {

    /**
     * 过滤敏感词
     * @param text 带过滤文本
     * @return 处理过的文本
     */
    String filter(String text) ;

}
