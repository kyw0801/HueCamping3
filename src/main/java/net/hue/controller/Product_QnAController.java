package net.hue.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.hue.service.CartService;
import net.hue.service.CategoryService;
import net.hue.service.MemberService;
import net.hue.service.NoticeService;
import net.hue.service.ProductQnAService;
import net.hue.vo.CategoryVO;
import net.hue.vo.MemberVO;
import net.hue.vo.ProductQnAReplyVO;
import net.hue.vo.ProductQnAVO;
import net.hue.vo.ProductVO;

@Controller
public class Product_QnAController {

	@Autowired
	private ProductQnAService productQnAService;
	
	@Autowired
    private MemberService memberService;
	
	@Autowired
    private CategoryService categoryService;
	
	@Autowired
    private CartService cartService;
	
	//QnA 목록
	@RequestMapping("/productQnA_list")
	public ModelAndView productQnA_list(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute ProductQnAVO pq,CategoryVO c,ProductVO p,Model model) {
		response.setContentType("text/html;charset=UTF-8");
		
		// 헤더 부분  오른쪽상단 메뉴바
        HttpSession session = request.getSession();
        String memid = (String) session.getAttribute("memid");

        MemberVO mbean = memberService.getMemberById(memid);

        if (memid != null) {
            int result = 0;
            if (session.getAttribute("memno") != null) {
                int memno = (int) session.getAttribute("memno");
                result = cartService.countItemInCart(memno);
                model.addAttribute("result", result);
            }
        }
        model.addAttribute("mbean", mbean);
        // 헤더 오른쪽 상단 메뉴 end

        // 헤더 카테고리
        List<CategoryVO> clist = categoryService.getAllCategory(c);
        model.addAttribute("clist", clist);
        // 헤더 카테고리 end
				
		
		int page=1;
		int limit=10;
		
		String find_field=request.getParameter("find_name"); //검색 필드
		String find_name=request.getParameter("find_field");//검색어
		
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		
		
		if(request.getParameter("find_field") != null) {
			find_field=request.getParameter("find_field").trim();
		}
		if(request.getParameter("find_name") != null) {
			find_name=request.getParameter("find_name").trim();
		}
		
		//ProductQnAVO findB=new ProductQnAVO();
		pq.setFind_field(find_field);
		pq.setFind_name("%"+find_name+"%");
			
		int listcount=this.productQnAService.getListCount(pq);
		pq.setStartrow((page-1)*10+1);//시작행번호
		pq.setEndrow(pq.getStartrow()+limit-1);//끝행번호
		
		List<ProductQnAVO> blist=this.productQnAService.getBoardList(pq);
		
		
		//페이지 연산
		int maxpage=(int)((double)listcount/limit+0.95);//총 페이지수
        int startpage=(((int)((double)page/10+0.9))-1)*10+1;//시작페이지
        int endpage=maxpage;//마지막 페이지
        if(endpage>startpage+10-1) endpage=startpage+10-1;
        
        ModelAndView listP=new ModelAndView();
        listP.addObject("blist",blist);//blist속성키이름에 목록을 저장
        listP.addObject("page", page);//쪽번호 
        listP.addObject("startpage",startpage);//시작페이지
        listP.addObject("endpage",endpage);//마지막 페이지
        listP.addObject("maxpage",maxpage);
        listP.addObject("listcount",listcount);
        listP.addObject("find_field",find_field);//board_title,board_cont 검색필드가 저장
        listP.addObject("find_name", find_name);
		
        listP.setViewName("productQnA/productQnA_list");//뷰페이지 경로 및 파일명
		return listP;
		
	}//productQnA_list()
		
		
	//QnA 게시물 내용 보기
	@RequestMapping("/productQnA_view")
	public String productQnA_view(@RequestParam("board_no") int board_no,
			@RequestParam("state") String state,
			HttpServletRequest request,
			Model m,@ModelAttribute ProductQnAVO pv,CategoryVO c,ProductVO p,Model model) throws Exception{
		
		// 헤더 부분  오른쪽상단 메뉴바
        HttpSession session = request.getSession();
        String memid = (String) session.getAttribute("memid");

        MemberVO mbean = memberService.getMemberById(memid);

        if (memid != null) {
            int result = 0;
            if (session.getAttribute("memno") != null) {
                int memno = (int) session.getAttribute("memno");
                result = cartService.countItemInCart(memno);
                model.addAttribute("result", result);
            }
        }
        model.addAttribute("mbean", mbean);
        // 헤더 오른쪽 상단 메뉴 end

        // 헤더 카테고리
        List<CategoryVO> clist = categoryService.getAllCategory(c);
        model.addAttribute("clist", clist);
        // 헤더 카테고리 end
		
		int page=1;
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));			
		}
		ProductQnAVO prov=this.productQnAService.getBoardView(board_no);
		String productQnA_view=prov.getBoard_cont().replace("\n", "<br/>");
		
		
		List<ProductQnAReplyVO> replist=this.productQnAService.getReplyView(board_no,page);
		
		
		m.addAttribute("replist", replist);
		m.addAttribute("prov", prov);
		m.addAttribute("pview", productQnA_view);
		m.addAttribute("page",page);
		
		if(state.equals("view")) {//내용보기
			return "productQnA/productQnA_view";// /WEB-INF/productQnA/
			//productQnA_view.jsp 뷰페이지로 이동
		}else if(state.equals("reply")) {//답변글 폼
			return "productQnA/productQnA_reply";
		}else if(state.equals("edit")) {//수정폼
			return "productQnA/productQnA_edit";
		}else if(state.equals("del")) {//삭제폼
			return "productQnA/productQnA_del";
		}
		
		return state;
	}//productQnA_view()
	
	//QnA 게시판 글쓰기
	@RequestMapping("/productQnA_write")
	public String productQnA_write(HttpServletRequest request, Model m,CategoryVO c,ProductVO p,Model model) {
		
		// 헤더 부분  오른쪽상단 메뉴바
        HttpSession session = request.getSession();
        String memid = (String) session.getAttribute("memid");

        MemberVO mbean = memberService.getMemberById(memid);

        if (memid != null) {
            int result = 0;
            if (session.getAttribute("memno") != null) {
                int memno = (int) session.getAttribute("memno");
                result = cartService.countItemInCart(memno);
                model.addAttribute("result", result);
            }
        }
        model.addAttribute("mbean", mbean);
        // 헤더 오른쪽 상단 메뉴 end

        // 헤더 카테고리
        List<CategoryVO> clist = categoryService.getAllCategory(c);
        model.addAttribute("clist", clist);
        //헤더 카테고리 end
		
		int page=1;
		if(request.getParameter("page") != null) {//get으로 전달된 쪽번호가 있는 경우만 실행
			page=Integer.parseInt(request.getParameter("page"));//쪽번호를 정수 숫자로 변경해서
			//저장
		}
		
		m.addAttribute("page",page);
		return "productQnA/productQnA_write";//뷰페이지 경로
	}//productQnA_write()
	
		
	//QnA 게시판 글 저장
	@RequestMapping("/productQnA_write_ok")
	public ModelAndView productQnA_write_ok(@ModelAttribute ProductQnAVO pq) {
		
		this.productQnAService.insertBoard(pq);	
		return new ModelAndView("redirect:/productQnA_list");
	}//productQnA_write_ok()
	
	
	//QnA 게시글 수정
	@RequestMapping("/productQnA_edit_ok")
	public String productQnA_edit_ok(@ModelAttribute ProductQnAVO epq, HttpServletResponse response,
			HttpServletRequest request) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		int page=1;
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));			
		}
		
		ProductQnAVO db_pwd=this.productQnAService.getBoardView(epq.getBoard_no());
		if(!db_pwd.getBoard_pwd().equals(epq.getBoard_pwd())) {
			out.println("<script>");
			out.println("alert('비번이 다릅니다!');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			this.productQnAService.editBoard(epq);//게시물 수정		
			return "redirect:/productQnA_view?board_no="+epq.getBoard_no()+
					"&page="+page+"&state=view";
		
		}	
		return null;
	}
	
	
	//QnA 게시글 삭제
	@RequestMapping("/productQnA_del_ok")
	public ModelAndView productQnA_del_ok(int board_no,int page,@RequestParam("del_pwd") String del_pwd,
			HttpServletResponse response, HttpServletRequest request) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
				
		
		
		
		ProductQnAVO db_pwd=this.productQnAService.getBoardView(board_no);		
		if(!db_pwd.getBoard_pwd().equals(del_pwd)) {
			out.println("<script>");
			out.println("alert('비번이 다릅니다!');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			this.productQnAService.delBoard(board_no);//게시판 삭제
			//ModelAndView pdm=new ModelAndView();
			//pdm.setViewName("redirect:/productQnA_list?page="+page);
			ModelAndView dm=new ModelAndView();
			dm.setViewName("redirect:/productQnA_list?page="+page);		
			return dm;
		}		
		return null;

		
		
	}//productQnA_del_ok()
	
	
	//QnA 게시판 글 답글 기능(댓글 창)
	@RequestMapping("/productQnA_reply")
	public String productQnA_reply(HttpServletRequest request, Model m) {
		int page=1;
		if(request.getParameter("page") != null) {//get으로 전달된 쪽번호가 있는 경우만 실행
			page=Integer.parseInt(request.getParameter("page"));//쪽번호를 정수 숫자로 변경해서
			//저장
		}
		int board_no=Integer.parseInt(request.getParameter("board_no"));
		request.setAttribute("board_no",board_no);
		m.addAttribute("page",page);
		return "productQnA/productQnA_reply";//뷰페이지 경로
	}//productQnA_reply()
	
	//QnA 게시판 글 답글 저장(댓글 창)
	@RequestMapping("/productQnA_reply_ok")
	public String productQnA_reply_ok(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ProductQnAReplyVO rpq) {
		int page=Integer.parseInt(request.getParameter("page"));
		/*if(request.getParameter("page") != null) {//get으로 전달된 쪽번호가 있는 경우만 실행
			page=Integer.parseInt(request.getParameter("page"));//쪽번호를 정수 숫자로 변경해서
			//저장
		}*/
		int board_no=Integer.parseInt(request.getParameter("board_no"));
		
		request.setAttribute("page",page);
		request.setAttribute("board_no",board_no);
		
		this.productQnAService.insertReply(rpq);	
		return "redirect:/productQnA_view?board_no="+rpq.getBoard_no()+
				"&page="+page+"&state=view";
	}//productQnA_reply_ok()
	
	//FAQ
	@RequestMapping("/FAQ")
	public String FAQ(HttpServletRequest request, HttpServletResponse response,CategoryVO c,ProductVO p,Model model) {
		// 헤더 부분  오른쪽상단 메뉴바
        HttpSession session = request.getSession();
        String memid = (String) session.getAttribute("memid");

        MemberVO mbean = memberService.getMemberById(memid);

        if (memid != null) {
            int result = 0;
            if (session.getAttribute("memno") != null) {
                int memno = (int) session.getAttribute("memno");
                result = cartService.countItemInCart(memno);
                model.addAttribute("result", result);
            }
        }
        model.addAttribute("mbean", mbean);
        // 헤더 오른쪽 상단 메뉴 end

        // 헤더 카테고리
        List<CategoryVO> clist = categoryService.getAllCategory(c);
        model.addAttribute("clist", clist);
        // 헤데 카테고리 end
		
		
		return "productQnA/FAQ";
	}
	
	
	
}
