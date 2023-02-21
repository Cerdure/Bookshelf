package com.cerdure.bookshelf.controller.borad;

import com.cerdure.bookshelf.domain.board.Notice;
import com.cerdure.bookshelf.dto.board.NoticeDto;
import com.cerdure.bookshelf.service.classes.board.interfaces.NoticeService;
import com.cerdure.bookshelf.utils.DataUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @GetMapping("/notice")
    public String allNotices(@ModelAttribute("noticeDto") NoticeDto noticeDto, Model model, Pageable pageable) {
        Page<Notice> notices = noticeService.findAll(pageable);
        DataUtils dataUtils = new DataUtils();
        model.addAttribute("dataUtils", dataUtils);
        model.addAttribute("notices", notices);
        return "board-notice";
    }

    @GetMapping("/notice-search")
    public String findNotice(@ModelAttribute NoticeDto noticeDto, Model model, Pageable pageable) throws Exception {
        Page<Notice> notices;
        if (noticeDto.getSearchBy() == 1) {
            notices = noticeService.findByTitle(noticeDto.getInput(), noticeDto.getMemberId(), pageable);
        } else {
            notices = noticeService.findByContent(noticeDto.getInput(), noticeDto.getMemberId(), pageable);
        }
        DataUtils dataUtils = new DataUtils();
        dataUtils.setSearchBy(noticeDto.getSearchBy());
        dataUtils.setInput(noticeDto.getInput());
        dataUtils.setMemberId(noticeDto.getMemberId());
        model.addAttribute("dataUtils", dataUtils);
        model.addAttribute("notices", notices);
        return "board-notice";
    }

    @PostMapping("/notice")
    public String createNotice(@ModelAttribute NoticeDto noticeDto, Authentication authentication, Model model, Pageable pageable) throws Exception {
        noticeService.create(noticeDto, authentication);
        Page<Notice> notices = noticeService.findAll(pageable);
        model.addAttribute("notices", notices);
        return "redirect:/notice";
    }


    @GetMapping("/notice-detail/{noticeId}")
    public String noticeDetail(@PathVariable("noticeId") Long noticeId, Model model) {
        Notice notice = noticeService.findById(noticeId);
        noticeService.hitsPlus(notice);
        Notice prev = noticeService.findPrevNotice(notice);
        Notice next = noticeService.findNextNotice(notice);
        model.addAttribute("notice", notice);
        model.addAttribute("prevNotice", prev);
        model.addAttribute("nextNotice", next);
        return "notice-detail";
    }

    @PostMapping("/notice-modify/{noticeId}")
    public String modify(@PathVariable("noticeId") Long noticeId, @ModelAttribute NoticeDto noticeDto, Authentication authentication, Model model, Pageable pageable) throws Exception {
        Notice notice = noticeService.modify(noticeId, noticeDto, authentication);
        Notice prev = noticeService.findPrevNotice(notice);
        Notice next = noticeService.findNextNotice(notice);
        model.addAttribute("notice", notice);
        model.addAttribute("prevNotice", prev);
        model.addAttribute("nextNotice", next);
        return "notice-detail";
    }

    @PostMapping("/notice-delete/{noticeId}")
    public String delete(@PathVariable("noticeId") Long noticeId, Authentication authentication, Model model, Pageable pageable) throws Exception {
        noticeService.delete(noticeId, authentication);
        Page<Notice> notices = noticeService.findAll(pageable);
        DataUtils dataUtils = new DataUtils();
        model.addAttribute("dataUtils", dataUtils);
        model.addAttribute("notices", notices);
        return "board-notice";
    }

}
