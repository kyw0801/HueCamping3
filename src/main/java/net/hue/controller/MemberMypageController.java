package net.hue.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import net.hue.service.CartService;
import net.hue.service.CategoryService;
import net.hue.service.MemberMypageService;
import net.hue.service.MemberService;
import net.hue.service.ProductService;
import net.hue.vo.CategoryVO;
import net.hue.vo.MemberVO;
import net.hue.vo.OrderVO;
import net.hue.vo.ProductVO;
import net.hue.vo.WishlistVO;

@Controller
public class MemberMypageController {
	
	@Autowired
    private MemberMypageService memberMypageService;
    
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
    private MemberService memberService;

	@Autowired
    private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	//마이페이지(관심상품 목록)
	@RequestMapping("/member_mypage")
	public String mypageMain(HttpServletRequest request,HttpServletResponse response,
			WishlistVO wl, CategoryVO c, ProductVO p, Model model) throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
		
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
        
        int mno = (int) session.getAttribute("memno");
		List<WishlistVO> wlist = memberMypageService.getAllItem(mno);
		model.addAttribute("wlist", wlist);

		return "mypage_login/mypage_login";
	}//mypageMain()

	//관심목록 추가(저장)
	@RequestMapping("/addWishlist")
	public String addWishlist(HttpServletRequest request,
    		HttpServletResponse response, Model model) throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter(); // 출력스트림
        
        HttpSession session = request.getSession();
        int mno = (int) session.getAttribute("memno");
		int pno = Integer.parseInt(request.getParameter("pno"));
		int qty = Integer.parseInt(request.getParameter("qty"));
		String opname = request.getParameter("opname");
		
		int cnt = memberMypageService.insertItem(mno,pno,qty,opname);
		
		if(cnt >0) {
        	out.println("<script>");
        	out.println("alert('관심목록 추가 성공');");
        	out.println("location='member_mypage';");
        	out.println("</script>");
        }else {
        	out.println("<script>");
        	out.println("alert('장바구니 담기 실패');");
        	out.println("location='member_mypage';");
        	out.println("</script>");
        }
		return null;
	}//addWishlist()
	
	//관심목록 삭제
	@RequestMapping("/wishlist_del")
	public String wishlist_del(HttpServletResponse response,
			HttpServletRequest request, Model model) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter(); 
		
        int itemno = Integer.parseInt(request.getParameter("itemno"));
        int cnt = memberMypageService.delWishlist(itemno);
		//this.memberMypageService.delWishlist(no);
        if(cnt >0) {
        	out.println("<script>");
        	out.println("alert('관심상품 삭제 성공');");
        	out.println("location='member_mypage';");
        	out.println("</script>");
        }else {
        	out.println("<script>");
        	out.println("alert('관심상품 삭제 실패');");
        	out.println("location='member_mypage';");
        	out.println("</script>");
        }
		return null;
	}
	
	
	
    //회원정보 수정
    @RequestMapping("/member_mypage_edit")
    public String mypage_edit(HttpServletResponse response, HttpServletRequest request,
    		MemberVO m, Model model, CategoryVO c) throws Exception{
    	
    	response.setContentType("text/html;charset=UTF-8");
    	request.setCharacterEncoding("UTF-8");
    	
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
    	
    	List<CategoryVO> clist = categoryService.getAllCategory(c);
        model.addAttribute("clist", clist);//카테고리

		return "mypage_login/mypage_edit";
    }
  
  //회원정보 수정 ok
    @RequestMapping(value="/member_mypage_edit_ok",method=RequestMethod.POST)
    public String memberEditOK(HttpServletRequest request,
    		HttpServletResponse response, MemberVO m) throws Exception{
    	
    	response.setContentType("text/html;charset=UTF-8");
    	request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림

		HttpSession session = request.getSession();

		
		String id=request.getParameter("id");
		String name=request.getParameter("name");
		String zip=request.getParameter("zip");
		String addr1=request.getParameter("addr1");
		String addr2=request.getParameter("addr2");
		String mobile1=request.getParameter("mobile1");
		String mobile2=request.getParameter("mobile2");
		String mobile3=request.getParameter("mobile3");
		String email=request.getParameter("email");
		String gender=request.getParameter("gender");
		String password=request.getParameter("password");

		int mno = (int)session.getAttribute("memno");
		
		System.out.println("mno : " + mno);

		System.out.println(password);

		MemberVO db_pwd=this.memberMypageService.contmember(mno);

		if(!db_pwd.getPassword().equals(password)) { 
			out.println("<script>");
			out.println("alert('비밀번호를 확인하세요.');");
			out.println("history.go(-1);");
			out.println("</script>");
		}else {
			m.setId(id); m.setName(name);
			m.setZip(zip); m.setAddr1(addr1); m.setAddr2(addr2);
			m.setMobile1(mobile1); m.setMobile2(mobile2); m.setMobile3(mobile3);
			m.setEmail(email); m.setGender(gender); m.setPassword(password);
			m.setNo(mno);
			this.memberMypageService.editMember(m);
			
			session.invalidate(); // 세션 설정 삭제
			out.println("<script>");
			out.println("alert('수정된 정보로 로그인 해주세요.');");
			out.println("location.href='/member_login';");
			out.println("</script>");
			
		}
		return null;
    }
    
    //회원 탈퇴 
    @RequestMapping("/member_mypage_del")
    public String member_del(HttpServletResponse response, HttpServletRequest request,
    		MemberVO m, Model model, CategoryVO c) throws Exception{
    	
    	response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		List<CategoryVO> clist = categoryService.getAllCategory(c);
        model.addAttribute("clist", clist);//카테고리
        
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
        
    	return "mypage_login/mypage_del";
    }
    
    //회원탈퇴 OK
    @RequestMapping("/member_mypage_del_ok")
    public String memberDelOK( HttpServletRequest request,
    		HttpServletResponse response,MemberVO m) throws Exception {
    	
    	response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter(); // 출력스트림
        
        HttpSession session = request.getSession();
        
        String id= request.getParameter("delete_id");
        String email=request.getParameter("delete_email");
        String password=request.getParameter("delete_pwd"); 
        
        m.setId(id); m.setPassword(password); m.setEmail(email);
        
        int cnt = this.memberMypageService.getMemberByIdEmailAndPassword(m); 
        if(cnt > 0) {
			session.invalidate(); // 세션 설정 삭제
			out.println("<script>");
			out.println("alert('정상적으로 회원 탈퇴 되었습니다.');");
			out.println("location='member_main';");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('입력된 회원정보가 없습니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
			}

    	return null;
    }
    
    //주문 접수 내역
    @RequestMapping("/member_mypage_orderlist")
    public String pro_list(Model listO,HttpServletRequest request,HttpServletResponse response,
    		CategoryVO c,Model model) throws Exception{
    	
    	response.setContentType("text/html;charset=UTF-8");
    	request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		List<CategoryVO> clist = categoryService.getAllCategory(c);
        model.addAttribute("clist", clist);//카테고리
        
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
        
		List<OrderVO> list = memberMypageService.getProList(memid);
		model.addAttribute("list", list);
		
    	return "mypage_login/mypage_orderlist";
    }//pro_list()
    
  //주문 접수 내역 추가
    @RequestMapping("/addOrderlist")
	public String addOrderlist(HttpServletRequest request,
    		HttpServletResponse response, Model model) throws Exception{
		
		response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter(); // 출력스트림
        
        HttpSession session = request.getSession();
        int mno = (int) session.getAttribute("memno");
		int pno = Integer.parseInt(request.getParameter("pno"));
		int qty = Integer.parseInt(request.getParameter("qty"));
		String opname = request.getParameter("opname");
		
		int cnt = memberMypageService.insertItem(mno,pno,qty,opname);
		
		if(cnt >0) {
        	out.println("<script>");
        	out.println("alert('주문 성공');");
        	out.println("location='member_mypage';");
        	out.println("</script>");
        }else {
        	out.println("<script>");
        	out.println("alert('주문 실패');");
        	out.println("location='member_mypage';");
        	out.println("</script>");
        }
		return "mypage_login/mypage_orderlist";
	}
    
    
    //주문 접수 내역 삭제
    @RequestMapping("/delete_orderlist")
    public String delete_orderlist(HttpServletResponse response,
    		HttpServletRequest request,Model model) throws Exception{
    	response.setContentType("text/html;charset=UTF-8");
    	request.setCharacterEncoding("UTF-8");
		PrintWriter out=response.getWriter();
		
		int no = Integer.parseInt(request.getParameter("no"));
		
		int cnt = memberMypageService.delOrderlist(no);
		
		if(cnt >0) {
        	out.println("<script>");
        	out.println("alert('주문내역 삭제 성공');");
        	out.println("location='member_mypage_orderlist';");
        	out.println("</script>");
        }else {
        	out.println("<script>");
        	out.println("alert('주문내역 삭제 실패');");
        	out.println("location='member_mypage_orderlist';");
        	out.println("</script>");
        }
    	return null;
		
		
    }
    
}














