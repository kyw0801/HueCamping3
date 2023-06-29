package net.hue.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

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

import com.oreilly.servlet.MultipartRequest;

import net.hue.service.CartService;
import net.hue.service.CategoryService;
import net.hue.service.MemberService;
import net.hue.service.NoticeService;
import net.hue.service.ReviewService;
import net.hue.vo.CategoryVO;
import net.hue.vo.MemberVO;
import net.hue.vo.ProductVO;
import net.hue.vo.ReviewReplyVO;
import net.hue.vo.ReviewVO;

@Controller
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
    private MemberService memberService;
	
	@Autowired
    private CategoryService categoryService;
	
	@Autowired
    private CartService cartService;
	
	//포토후기 목록 보기
	@RequestMapping("/review_list")
	public ModelAndView review_list(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute ReviewVO pr,CategoryVO c,ProductVO p,Model model) {
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
		
		//ProductQnAVO findB=new ProductQnAVO();
		pr.setFind_field(find_field);
		pr.setFind_name("%"+find_name+"%");
			
		int listcount=this.reviewService.getListCount(pr);
		pr.setStartrow((page-1)*10+1);//시작행번호
		pr.setEndrow(pr.getStartrow()+limit-1);//끝행번호
		
		List<ReviewVO> rlist=this.reviewService.getBoardList(pr);
		
		
		//페이지 연산
		int maxpage=(int)((double)listcount/limit+0.95);//총 페이지수
        int startpage=(((int)((double)page/10+0.9))-1)*10+1;//시작페이지
        int endpage=maxpage;//마지막 페이 지
        if(endpage>startpage+10-1) endpage=startpage+10-1;
        
        ModelAndView listR=new ModelAndView();
        listR.addObject("rlist",rlist);//rlist속성키이름에 목록을 저장
        listR.addObject("page", page);//쪽번호 
        listR.addObject("startpage",startpage);//시작페이지
        listR.addObject("endpage",endpage);//마지막 페이지
        listR.addObject("maxpage",maxpage);
        listR.addObject("listcount",listcount);
        listR.addObject("find_field",find_field);//board_title,board_cont 검색필드가 저장
        listR.addObject("find_name", find_name);

        listR.setViewName("review/review_list");//뷰페이지 경로 및 파일명
		
		return listR;
	}//review_list()
	
	
	//포토후기 게시물 내용 보기
	@RequestMapping("/review_view")
	public String review_view(@RequestParam("board_no") int board_no,
			@RequestParam("state") String state,
			HttpServletRequest request,
			Model m,@ModelAttribute ReviewVO pr,CategoryVO c,ProductVO p,Model model) throws Exception{		
		
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
		
		//String state=request.getParameter("state");
		// state=view(내용보기)/edit(수정)/del(삭제)
		
		//ReviewVO rev=this.reviewService.getBoardView(board_no);
		
		if(state.equals("view")) {
			this.reviewService.updateHit(board_no);
		}
		
		ReviewVO rc=this.reviewService.getBoardView(board_no);
		//번호에 해당하는 게시판 내용 불러오기
		String review_view=rc.getBoard_cont().replace("\n","<br>");
		//입력한 글내용 엔터키치면 줄바꿈되어서 나옴
		
		//댓글
		List<ReviewReplyVO> replist=this.reviewService.getReplyView(board_no,page);
		m.addAttribute("replist", replist);

		m.addAttribute("board_no", board_no);
		m.addAttribute("rc", rc);
		m.addAttribute("rview", review_view);
		m.addAttribute("page", page);
		
        
        if(state.equals("view")) {//내용보기 일때
        	return "review/review_view";
        }else if(state.equals("reply")) {//답변글 폼일때
        	return "review/review_reply";
        }else if(state.equals("edit")) {//수정폼 일때
        	return "review/review_edit";
        }else if(state.equals("del")) {//삭제폼 일때
        	return "review/review_del";
        }
        return state;
	}//review_view()
	
	
	//포토후기 게시판 글쓰기
	@RequestMapping("/review_write")
	public ModelAndView review_write(HttpServletRequest request,@RequestParam("page") int page,
			CategoryVO c,ProductVO p,Model model) {
		
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
		
		ModelAndView wm=new ModelAndView("review/review_write");
		wm.addObject("page",page);
		return wm;
	}//review_write()
	
	//포토후기 게시판 글 저장
	@RequestMapping("/review_write_ok")
	public String review_write_ok(ReviewVO pr,HttpServletRequest request) throws Exception{
		String saveFolder=request.getRealPath("upload");
		//이진 파일 업로드 서버 경로				
		int fileSize=5*1024*1024; //이진파일 업로드 최대크기
		MultipartRequest multi=null; //이진파일을 가져올 참조변수
		
		multi=new MultipartRequest(request,saveFolder,fileSize,"UTF-8");	
		
		String board_name=multi.getParameter("board_name");
		String board_title=multi.getParameter("board_title");
		String board_pwd=multi.getParameter("board_pwd");
		String board_cont=multi.getParameter("board_cont");

		File upFile = multi.getFile("board_file1");//첨부한 이진파일을 가져온다.
		    
		if(upFile != null) {//첨부한 이진파일이 있는 경우 실행 
			String fileName=upFile.getName();//첨부한 파일명
			Calendar cal=Calendar.getInstance();//칼렌더는 추상클래스로 new로 객체 생성을 못함. 년월일 시분초 값을 반환
			int year=cal.get(Calendar.YEAR);//년도값
			int month=cal.get(Calendar.MONTH)+1;//월값, +1을 한 이유는 1월이 0으로 반환 되기 때문에
			int date=cal.get(Calendar.DATE);//일값
			
			String homedir=saveFolder+"/"+year+"-"+month+"-"+date;//오늘 날짜 폴더 경로 저장
			File path01=new File(homedir);
			
			if(!(path01.exists())){
				path01.mkdir();//오늘날짜 폴더 생성
			}
			Random r=new Random();//난수를 발생시키는 클래스
			int random=r.nextInt(100000000);//0이상 1억 미만의 정수 숫자 난수 발생
			
			/*첨부 파일 확장자를 구함*/
			int index=fileName.lastIndexOf(".");//마침표를 맨 오른쪽부터 찾아서 가장 먼저 나오는 .의 위치번호를 맨 왼쪽부터 카운터 해서 반환
			//첫문자는 0부터 시작
			String fileExtendsion=fileName.substring(index+1);//마침표 이후부터 마지막 문자까지 구함.즉 첨부파일 확장자를 구함.
			String refileName="review"+year+month+date+random+"."+fileExtendsion;//새로운 파일명 저장
			String fileDBName="/"+year+"-"+month+"-"+date+"/"+refileName;//데이터베이스에 저장될 레코드값
			upFile.renameTo(new File(homedir+"/"+refileName));//생성된 폴더에 변경된 파일명으로 실제 업로드
			
			pr.setBoard_file1(fileDBName);
		}else {//첨부파일이 없는 경우
			String fileDBName="";
			pr.setBoard_file1(fileDBName);
		}
		pr.setBoard_name(board_name); pr.setBoard_title(board_title);
		pr.setBoard_pwd(board_pwd); pr.setBoard_cont(board_cont);
		
		this.reviewService.insertBoard(pr);//자료실 저장
		
		return "redirect:/review_list";
	}//productQnA_write_ok()
		
		
	//포토후기 게시판 글 수정
		@RequestMapping("/review_edit_ok")
		public String review_edit_ok(@ModelAttribute ReviewVO epr, HttpServletResponse response,
			HttpServletRequest request) throws Exception{
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out=response.getWriter();
			//String saveFolder=request.getRealPath("upload");
			//int fileSize=5*1024*1024;
			//MultipartRequest multi=null;//첨부된 파일을 받을 참조변수
			//multi=new MultipartRequest(request,saveFolder,fileSize,"UTF-8");
			
			//int board_no=Integer.parseInt(request.getParameter("board_no"));
			
			int page=1;
			if(request.getParameter("page") != null) {
				page=Integer.parseInt(request.getParameter("page"));			
			}
			/*
			String board_name=request.getParameter("board_name");
			String board_title=request.getParameter("board_title");
			String board_pwd=request.getParameter("board_pwd");
			String board_cont=request.getParameter("board_cont");
			*/
			ReviewVO db_pwd=this.reviewService.getBoardView(epr.getBoard_no());
			
			if(!db_pwd.getBoard_pwd().equals(epr.getBoard_pwd())) {
				out.println("<script>");
				out.println("alert('비번이 다릅니다!');");
				out.println("history.back();");
				out.println("</script>");
			}else {
				//epr.setBoard_no(board_no); epr.setBoard_name(board_name);
				//epr.setBoard_title(board_title); epr.setBoard_cont(board_cont);
				
				this.reviewService.editBoard(epr);
				/*ModelAndView em = new ModelAndView();
				em.setViewName("review_view?board_no="+epr.getBoard_no()+"&page="+page+"&state=view");
				em.addObject("board_no", board_no);
				em.addObject("page", page);
				em.addObject("state", "view");*/
				return "redirect:/review_view?board_no="+epr.getBoard_no()+
						"&page="+page+"&state=view";
				
			}
			
			return null;
		}//review_edit_ok()
	
		
	//포토후기 게시판 글 삭제
	@RequestMapping("/review_del_ok")
	public ModelAndView review_del_ok(int board_no,int page,@RequestParam("del_pwd") String del_pwd,
			HttpServletResponse response,HttpServletRequest request) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		String delFolder=request.getRealPath("upload");
		
		ReviewVO db_pwd = this.reviewService.getBoardView(board_no);
		if(!db_pwd.getBoard_pwd().equals(del_pwd)) {
			out.println("<script>");
			out.println("alert('비번이 다릅니다!');");
			out.println("history.back();");
			out.println("</script>");
		}else {
			this.reviewService.delReview(board_no);
			
			if(db_pwd.getBoard_file1() != null) {//기존 첨부파일이 있는 경우에 실행
				File delFile=new File(delFolder+db_pwd.getBoard_file1());//삭제할 파일객체 생성
				delFile.delete();//폴더는 삭제 안되고,폴더 안의 삭제될 파일만 삭제된다.
			}
			ModelAndView dm=new ModelAndView();
			dm.setViewName("redirect:/review_list?page="+page);
			return dm;
		}				
		return null;
	}//review_del_ok()
	
	
	//포토후기 게시글 댓글 기능(댓글 창)
	@RequestMapping("/review_reply")
	public String review_reply(HttpServletRequest request, Model m) {
		int page=1;
		if(request.getParameter("page") != null) {//get으로 전달된 쪽번호가 있는 경우만 실행
			page=Integer.parseInt(request.getParameter("page"));//쪽번호를 정수 숫자로 변경해서
			//저장
		}
		int board_no=Integer.parseInt(request.getParameter("board_no"));
		request.setAttribute("board_no",board_no);
		m.addAttribute("page",page);	
		return "review/review_reply";
	}//review_reply()
	
	
	//포토후기 게시글 댓글 저장(댓글 창)
	@RequestMapping("/review_reply_ok")
	public String review_reply_ok(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ReviewReplyVO pr_re) {
		int page=Integer.parseInt(request.getParameter("page"));
		int board_no=Integer.parseInt(request.getParameter("board_no"));
		
		request.setAttribute("page",page);
		request.setAttribute("board_no",board_no);
		
		this.reviewService.insertReply(pr_re);
		return "redirect:/review_view?board_no="+pr_re.getBoard_no()+
				"&page="+page+"&state=view";
	}//review_reply_ok()
	
	
}
