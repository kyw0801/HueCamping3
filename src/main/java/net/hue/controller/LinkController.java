package net.hue.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.hue.service.CategoryService;
import net.hue.vo.CategoryVO;


@Controller
public class LinkController {
	
	@Autowired
	private CategoryService categoryService;
	
	//이용약관
	@RequestMapping("/guideLink_useTerms")
	public String useTerms(HttpServletRequest request, HttpServletResponse response, CategoryVO c, Model model) throws Exception{

		response.setContentType("text/html;charset=UTF-8");
		
		// 헤더 카테고리
        List<CategoryVO> clist = categoryService.getAllCategory(c);
        model.addAttribute("clist", clist);
        // 헤데 카테고리 end

		return "guideLink/useTerms";
	}
	
	//이용약관
	@RequestMapping("/guideLink_privacyManage")
	public String privacyManage(HttpServletRequest request, HttpServletResponse response, CategoryVO c, Model model) throws Exception{

		response.setContentType("text/html;charset=UTF-8");
		
		// 헤더 카테고리
        List<CategoryVO> clist = categoryService.getAllCategory(c);
        model.addAttribute("clist", clist);
        // 헤데 카테고리 end

		return "guideLink/privacyManage";
	}

	//이용약관
	@RequestMapping("/guideLink_useInfo")
	public String useInfo(HttpServletRequest request, HttpServletResponse response, CategoryVO c, Model model) throws Exception{

		response.setContentType("text/html;charset=UTF-8");
		
		// 헤더 카테고리
        List<CategoryVO> clist = categoryService.getAllCategory(c);
        model.addAttribute("clist", clist);
        // 헤데 카테고리 end

		return "guideLink/useInfo";
	}

}
