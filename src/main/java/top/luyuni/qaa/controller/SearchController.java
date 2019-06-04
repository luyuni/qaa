package top.luyuni.qaa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import top.luyuni.qaa.model.EntityType;
import top.luyuni.qaa.model.Question;
import top.luyuni.qaa.model.ViewObject;
import top.luyuni.qaa.service.IFollowService;
import top.luyuni.qaa.service.IQuestionService;
import top.luyuni.qaa.service.ISearchService;
import top.luyuni.qaa.service.IUserService;

import java.util.ArrayList;
import java.util.List;


@Controller
public class SearchController {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    private ISearchService ISearchService;

    @Autowired
    private IFollowService IFollowService;

    @Autowired
    private IUserService IUserService;

    @Autowired
    private IQuestionService IQuestionService;

    @RequestMapping(path = {"/search"}, method = {RequestMethod.GET})
    public String search(Model model, @RequestParam("q") String keyword,
                         @RequestParam(value = "offset", defaultValue = "0") int offset,
                         @RequestParam(value = "count", defaultValue = "10") int count) {
        try {
            List<Question> questionList = ISearchService.searchQuestion(keyword, offset, count,
                    "<em>", "</em>");
            List<ViewObject> vos = new ArrayList<>();
            for (Question question : questionList) {
                Question q = IQuestionService.getById(question.getId());
                ViewObject vo = new ViewObject();
                if (question.getContent() != null) {
                    q.setContent(question.getContent());
                }
                if (question.getTitle() != null) {
                    q.setTitle(question.getTitle());
                }
                vo.set("question", q);
                vo.set("followCount", IFollowService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
                vo.set("user", IUserService.getUser(q.getUserId()));
                vos.add(vo);
            }
            model.addAttribute("vos", vos);
            model.addAttribute("keyword", keyword);
        } catch (Exception e) {
            logger.error("搜索评论失败" + e.getMessage());
        }
        return "result";
    }
}
