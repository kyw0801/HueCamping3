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
import net.hue.service.ProductService;
import net.hue.vo.CategoryVO;
import net.hue.vo.MemberVO;
import net.hue.vo.NoticeReplyVO;
import net.hue.vo.NoticeVO;
import net.hue.vo.ProductVO;

@Controller
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	@Autowired
    private MemberService memberService;
	
	@Autowired
    private CategoryService categoryService;
	
	@Autowired
    private CartService cartService;
	
	
	
		//공지사항 목록
		@RequestMapping("/notice")
		public ModelAndView notice(HttpServletRequest request, HttpServletResponse response,
				@ModelAttribute NoticeVO pn,CategoryVO c,ProductVO p,Model model) {
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
	        // 헤데 카테고리 end
			
			
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
			
			
			pn.setFind_field(find_field);
			pn.setFind_name("%"+find_name+"%");
				
			int listcount=this.noticeService.getListCount(pn);
			pn.setStartrow((page-1)*10+1);//시작행번호
			pn.setEndrow(pn.getStartrow()+limit-1);//끝행번호
			
			List<NoticeVO> nlist=this.noticeService.getBoardList(pn);
			
			
			//페이지 연산
			int maxpage=(int)((double)listcount/limit+0.95);//총 페이지수
	        int startpage=(((int)((double)page/10+0.9))-1)*10+1;//시작페이지
	        int endpage=maxpage;//마지막 페이지
	        if(endpage>startpage+10-1) endpage=startpage+10-1;
	        
	        ModelAndView listN=new ModelAndView();
	        listN.addObject("nlist",nlist);//nlist속성키이름에 목록을 저장
	        listN.addObject("page", page);//쪽번호 
	        listN.addObject("startpage",startpage);//시작페이지
	        listN.addObject("endpage",endpage);//마지막 페이지
	        listN.addObject("maxpage",maxpage);
	        listN.addObject("listcount",listcount);
	        listN.addObject("find_field",find_field);//board_title,board_cont 검색필드가 저장
	        listN.addObject("find_name", find_name);
			
	        listN.setViewName("notice/notice");//뷰페이지 경로 및 파일명
			return listN;
			
		}
		
		//공지사항 글 쓰기
		@RequestMapping("/notice_write")
		public String notice_write(HttpServletRequest request, Model m,CategoryVO c,ProductVO p,Model model) {
			
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
			
			int page=1;
			if(request.getParameter("page") != null) {//get으로 전달된 쪽번호가 있는 경우만 실행
				page=Integer.parseInt(request.getParameter("page"));//쪽번호를 정수 숫자로 변경해서
				//저장
			}
			
			m.addAttribute("page",page);
			return "notice/notice_write";//뷰페이지 경로
		}//notice_write()
		
		//공지사항 글 저장
		@RequestMapping("/notice_write_ok")
		public ModelAndView notice_write_ok(@ModelAttribute NoticeVO pn) {
			
			this.noticeService.insertBoard(pn);	
			return new ModelAndView("redirect:/notice");
		}
		
		
		
		//공지사항 글 내용보기
		@RequestMapping("/notice_view")
		public String notice_view(@RequestParam("board_no") int board_no,
				@RequestParam("state") String state,
				HttpServletRequest request,
				Model m,@ModelAttribute NoticeVO pn,CategoryVO c,ProductVO p,Model model) throws Exception{
			
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
			
			
			int page=1;
			if(request.getParameter("page") != null) {
				page=Integer.parseInt(request.getParameter("page"));			
			}
			NoticeVO nov=this.noticeService.getBoardView(board_no);
			String notice_view=nov.getBoard_cont().replace("\n", "<br/>");
			
			
			List<NoticeReplyVO> notice_replist=this.noticeService.getReplyView(board_no,page);
			
			
			m.addAttribute("notice_replist", notice_replist);
			m.addAttribute("nov", nov);
			m.addAttribute("nview", notice_view);
			m.addAttribute("page",page);
			
			if(state.equals("view")) {//내용보기
				return "notice/notice_view";// /WEB-INF/productQnA/
				//productQnA_view.jsp 뷰페이지로 이동
			}else if(state.equals("reply")) {//답변글 폼
				return "notice/notice_reply";
			}else if(state.equals("edit")) {//수정폼
				return "notice/notice_edit";
			}else if(state.equals("del")) {//삭제폼
				return "notice/notice_del";
			}
			
			return state;
		}//notice_view()
	
	//공지사항 게시글 수정
		@RequestMapping("/notice_edit_ok")
		public String notice_edit_ok(@ModelAttribute NoticeVO epn, HttpServletResponse response,
				HttpServletRequest request) throws Exception{
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out=response.getWriter();
			
			int page=1;
			if(request.getParameter("page") != null) {
				page=Integer.parseInt(request.getParameter("page"));			
			}
			
			NoticeVO db_pwd=this.noticeService.getBoardView(epn.getBoard_no());
			if(!db_pwd.getBoard_pwd().equals(epn.getBoard_pwd())) {
				out.println("<script>");
				out.println("alert('비번이 다릅니다!');");
				out.println("history.back();");
				out.println("</script>");
			}else {
				this.noticeService.editBoard(epn);//게시물 수정		
				return "redirect:/notice_view?board_no="+epn.getBoard_no()+
						"&page="+page+"&state=view";
			
			}
			return null;
		}
		
	//공지사항 게시글 삭제
		@RequestMapping("/notice_del_ok")
		public ModelAndView notice_del_ok(int board_no,int page,@RequestParam("del_pwd") String del_pwd,
				HttpServletResponse response, HttpServletRequest request) throws Exception{
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out=response.getWriter();
			
			NoticeVO db_pwd=this.noticeService.getBoardView(board_no);		
			if(!db_pwd.getBoard_pwd().equals(del_pwd)) {
				out.println("<script>");
				out.println("alert('비번이 다릅니다!');");
				out.println("history.back();");
				out.println("</script>");
			}else {
				this.noticeService.delBoard(board_no);//게시판 삭제				
				ModelAndView dm=new ModelAndView();
				dm.setViewName("redirect:/notice?page="+page);		
				return dm;
			}			
			return null;
		}//notice_del_ok
		
		
		
	//공지사항 게시글 댓글 쓰기
		@RequestMapping("/notice_reply")
		public String notice_reply(HttpServletRequest request, Model m) {
			int page=1;
			if(request.getParameter("page") != null) {//get으로 전달된 쪽번호가 있는 경우만 실행
				page=Integer.parseInt(request.getParameter("page"));//쪽번호를 정수 숫자로 변경해서
				//저장
			}
			int board_no=Integer.parseInt(request.getParameter("board_no"));
			request.setAttribute("board_no",board_no);
			m.addAttribute("page",page);
			return "notice/notice_reply";//뷰페이지 경로
		}//notice_reply()
		
		//공지사항 게시글 답글 저장(댓글 창)
		@RequestMapping("/notice_reply_ok")
		public String notice_reply_ok(HttpServletRequest request, HttpServletResponse response, @ModelAttribute NoticeReplyVO rpn) {
			int page=Integer.parseInt(request.getParameter("page"));
			/*if(request.getParameter("page") != null) {//get으로 전달된 쪽번호가 있는 경우만 실행
				page=Integer.parseInt(request.getParameter("page"));//쪽번호를 정수 숫자로 변경해서
				//저장
			}*/
			int board_no=Integer.parseInt(request.getParameter("board_no"));
			
			request.setAttribute("page",page);
			request.setAttribute("board_no",board_no);
			
			this.noticeService.insertReply(rpn);	
			return "redirect:/notice_view?board_no="+rpn.getBoard_no()+
					"&page="+page+"&state=view";
		}//notice_reply_ok()
}
