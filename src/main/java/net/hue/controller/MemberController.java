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
import org.springframework.web.servlet.ModelAndView;

import net.hue.service.CartService;
import net.hue.service.CategoryService;
import net.hue.service.MemberService;
import net.hue.service.OrderService;
import net.hue.service.ProductService;
import net.hue.service.StockService;
import net.hue.vo.CartVO;
import net.hue.vo.CategoryVO;
import net.hue.vo.MemberVO;
import net.hue.vo.ProductVO;
import net.hue.vo.StockVO;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private StockService stockService;
    
    @Autowired
    private OrderService orderservice;

    // 메인페이지
    @RequestMapping("/member_main")
    public String memberMain(HttpServletRequest request,
    HttpServletResponse response, ProductVO p, CategoryVO c, Model model) throws Exception {

        request.setCharacterEncoding("UTF-8");

       List<ProductVO> plist = productService.getAllProduct(p);
       List<ProductVO> plist2 = productService.getAllProduct2(p);
       List<ProductVO> plist3 = productService.getAllProduct3(p);

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

        model.addAttribute("plist", plist);
        model.addAttribute("plist2", plist2);
        model.addAttribute("plist3", plist3);

        return "member/index";
    }// 메인페이지
    
  //메인페이지 검색 기능
    @RequestMapping("/showSearchResult")
    public ModelAndView showSearchResult(HttpServletRequest request, HttpServletResponse response,
    		@ModelAttribute ProductVO p,CategoryVO c,Model model) {
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
		int limit=8;
   	
    	String find_field=request.getParameter("find_name"); //검색 필드
		String find_name=request.getParameter("find_field");
		
		if(request.getParameter("page") != null) {
			page=Integer.parseInt(request.getParameter("page"));
		}
		
		if(request.getParameter("find_field") != null) {
			find_field=request.getParameter("find_field").trim();
		}
		if(request.getParameter("find_name") != null) {
			find_name=request.getParameter("find_name").trim();
		}
		
		p.setFind_field(find_field);
		p.setFind_name("%"+find_name+"%");
		
		int listcount=this.productService.getSearchListCount(p);
		p.setStartrow((page-1)*8+1);
		p.setEndrow(p.getStartrow()+limit-1);
		
		List<ProductVO> slist=this.productService.getSearchProduct(p);
		
		//페이지 연산
		int maxpage=(int)((double)listcount/limit+0.95);//총 페이지수
        int startpage=(((int)((double)page/8+0.875))-1)*8+1;//시작페이지
        int endpage=maxpage;//마지막 페이지
        if(endpage>startpage+8-1) endpage=startpage+8-1;
		
		ModelAndView listS=new ModelAndView();
		listS.addObject("slist", slist);
		listS.addObject("find_name",find_name);
		listS.addObject("find_field",find_field);
		listS.addObject("page", page);
		listS.addObject("startpage", startpage);
		listS.addObject("endpage",endpage);
		listS.addObject("maxpage", maxpage);
		listS.addObject("listcount", listcount);
		//listS.addObject("lcno", lcno);
		//listS.addObject("scno",scno);
		listS.setViewName("member/showSearchResult");
		return listS;
    }
    
    // 로그인페이지
    @RequestMapping("/member_login")
    public String memberLogin(HttpServletRequest request,
                             HttpServletResponse response,CategoryVO c,Model model) throws Exception {

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
        // 헤데 카테고리 end

        return "member/login";
    }// 로그인페이지
    
    // 로그인OK
    @RequestMapping("/member_login_ok")
    public String memberLoginOK(HttpServletRequest request,
                             HttpServletResponse response,MemberVO m) throws Exception {

    	response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter(); // 출력스트림
        
        String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		m.setId(id); m.setPassword(password);
		
		MemberVO mbean = memberService.getMemberById(id);
		
		if(mbean == null) { // 회원이 아니면
			out.println("<script>");
			out.println("alert('가입 안된 회원입니다.');");
			out.println("history.back();");
			out.println("</script>");
		}else { // 아이디가 맞는데 비번이 다를경우
			if(!mbean.getPassword().equals(password)) {
				out.println("<script>");
				out.println("alert('비번이 다릅니다.');");
				out.println("history.go(-1);");
				out.println("</script>");
		}else {
			String memid = mbean.getId();
			int memno = mbean.getNo();
			String menm = mbean.getName();
			
			//세션 아이디, 식별자 저장
			HttpSession session = request.getSession();
			session.setAttribute("memid", memid);
			session.setAttribute("memno", memno);
			out.println("<script>");
			out.println("alert('"+menm+"님 환영합니다.');");
			out.println("location='member_main';");
			out.println("</script>");
			}
		}
        return null;
    }// 로그인OK
    
    // 로그아웃
    @RequestMapping("/member_log_out")
    public String memberLogOut(HttpServletRequest request,
                             HttpServletResponse response,Model model) throws Exception {

    	response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter(); // 출력스트림
        
        HttpSession session = request.getSession();
		session.invalidate(); // 세션 설정 삭제
		
		out.println("<script>");
		out.println("alert('로그아웃 되었습니다!');");
		out.println("location='member_main';");
		out.println("</script>");
        return null;
    }// 로그아웃
    
 // 회원가입
    @RequestMapping("/member_join")
    public String memberJoin(HttpServletRequest request,
                             HttpServletResponse response,CategoryVO c,Model model) throws Exception {

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
        // 헤데 카테고리 end

        return "member/join";
    }// 회원가입
    
 // 회원가입OK
    @RequestMapping("/member_join_ok")
    public String memberJoinOk(HttpServletRequest request,
                             HttpServletResponse response,MemberVO m) throws Exception {

    	response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter(); // 출력스트림
		
        String id=request.getParameter("id");
		String password=request.getParameter("password");
		String name=request.getParameter("name");
		String zip=request.getParameter("zip");
		String addr1=request.getParameter("addr1");
		String addr2=request.getParameter("addr2");
		String mobile1=request.getParameter("mobile1");
		String mobile2=request.getParameter("mobile2");
		String mobile3=request.getParameter("mobile3");
		String email=request.getParameter("email");
		String gender=request.getParameter("gender");
		
		m.setId(id); m.setPassword(password); m.setName(name);
		m.setZip(zip); m.setAddr1(addr1); m.setAddr2(addr2);
		m.setMobile1(mobile1); m.setMobile2(mobile2); m.setMobile3(mobile3);
		m.setEmail(email); m.setGender(gender);
		
		boolean isDuplicate = memberService.checkDuplicateId(id);

	    if (isDuplicate) {
	        out.println("<script>");
	        out.println("alert('이미 사용 중인 아이디입니다.');");
	        out.println("location='member_join';");
	        out.println("</script>");
	    } else {
	        int cnt = memberService.insertMember(m);

	        if (cnt == 1) {
	            out.println("<script>");
	            out.println("alert('" + name + "님 가입 축하드립니다.');");
	            out.println("location='member_login';");
	            out.println("</script>");
	        } 
	    }
        return null;
    }// 회원가입OK
    
    // AJAX ID중복체크
    @RequestMapping("/member_idcheck")
    public String memberIdCheck(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {

    	response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter(); // 출력스트림
		
        String id=request.getParameter("id");
		
		MemberVO db_id = memberService.idCheck(id);
		
		String result = "ID중복체크 비정상"; // 중복아이디가 없을 경우 반환값
		if(db_id != null) { // 중복아이디가 있는경우
			result = "ID중복체크 정상";
		}
		out.println(result);
        return null;
    }// AJAX ID중복체크 
    
    //아이디 찾기
    @RequestMapping("/member_find_ID")
    public String FindId(HttpServletRequest request,
    		HttpServletResponse response,Model model) throws Exception {
    	
    	return "member/find_ID";
    }//아이디 찾기
    
    //아이디 찾기 결과
    @RequestMapping("/member_find_ID_result")
    public String FindIdResult(HttpServletRequest request,
    		HttpServletResponse response,MemberVO m, Model model) throws Exception {
    	
    	response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter(); // 출력스트림
             
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        m.setName(name); m.setEmail(email);
        
        MemberVO mid = memberService.findId(m);
    	
        if(mid == null) {
        	out.println("<script>");
        	out.println("alert('회원정보를 찾을 수 없습니다!');");
        	out.println("history.back();");
        	out.println("</script>");
        }else {
        	request.setAttribute("memid", mid.getId());
        	return "member/findIdResult";
        }
    	return null;
    }//아이디 찾기 결과
    
    //비밀번호 찾기
    @RequestMapping("/member_find_pwd")
    public String FindPwd(HttpServletRequest request,
    		HttpServletResponse response,Model model) throws Exception {
    	
    	return "member/find_pwd";
    }//비밀번호 찾기
    
  //비밀번호 찾기 결과
    @RequestMapping("/member_find_pwd_result")
    public String FindPwdResult(HttpServletRequest request,
    		HttpServletResponse response,MemberVO m, Model model) throws Exception {
    	
    	response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter(); // 출력스트림
        
        String id = request.getParameter("id");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
        m.setName(name); m.setEmail(email); m.setId(id);
        
        MemberVO mpwd = memberService.findPwd(m);
    	
        if(mpwd == null) {
        	out.println("<script>");
        	out.println("alert('회원정보를 찾을 수 없습니다!');");
        	out.println("history.back();");
        	out.println("</script>");
        }else {
        	request.setAttribute("mempwd", mpwd.getPassword());
        	return "member/findPwdResult";
        }
    	return null;
    }//비밀번호 찾기 결과    
    
  //주문 정보 창
    @RequestMapping("/orderview")
    public String orderview(HttpServletRequest request, HttpServletResponse response,
    		ProductVO p, CategoryVO c, Model model) throws Exception{
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
        // 헤데 카테고리 end
    			
        int mno = (int) session.getAttribute("memno");
		
		String[] rowcheck = request.getParameterValues("rowcheck");

		int ctno = -1;
		
		List<CartVO> ctlist = cartService.getAllItem(mno);
		model.addAttribute("ctlist", ctlist);
		int totalPrice = 0;
		
		String madedToTheOneRowcheck = "";
		
		List<CartVO> selectlist = cartService.selectItem(ctno);

		
		if(rowcheck != null){ // 장바구니를 통해 들어왔을 경우
			for(String no: rowcheck){
				selectlist.add(cartService.getItem(Integer.parseInt(no)));
			}
			//다음페이지로 넘길때 rowcheck를 한번 더 사용하기 위해 하나의 문자열로 만듬.
			if(rowcheck.length == 1){
				madedToTheOneRowcheck = rowcheck[0] + ",";	
			}else if(rowcheck.length > 1){
				madedToTheOneRowcheck = "" + rowcheck[0];
				for(int i = 1; i< rowcheck.length; i++){
					madedToTheOneRowcheck += "," + rowcheck[i];
				}
			}	
		}else{ // 바로 구매하기 버튼을 눌러 들어온 경우
			ctno = Integer.parseInt(request.getParameter("ctno")); // 장바구니에 담긴 상품의 번호를 들고와야 함.
			selectlist.add(cartService.getItem(ctno));
		}
		
		for(CartVO ctbean : selectlist){
			totalPrice += ctbean.getOneprice()*ctbean.getQty();
		}
		
		request.setAttribute("mbean", mbean);		
		request.setAttribute("rowcheck", rowcheck);
		request.setAttribute("ctlist", ctlist);
		request.setAttribute("selectlist", selectlist);
		request.setAttribute("madedToTheOneRowcheck", madedToTheOneRowcheck);
		request.setAttribute("ctno", ctno);
		request.setAttribute("mno", mno);
		request.setAttribute("totalPrice", totalPrice);
		System.out.println(ctlist.size());
    	return "member/orderView";
    }//orderview()
    
    // 바로주문
    @RequestMapping("/orderSkipCart")
    public String orderSkipCart(HttpServletRequest request, HttpServletResponse response,
    		ProductVO p, CategoryVO c, Model model) throws Exception{
    	response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		
		int mno = (int) session.getAttribute("memno");
		int pno = Integer.parseInt(request.getParameter("pno"));
		int qty = Integer.parseInt(request.getParameter("qty"));
		String opname = request.getParameter("opname");

		int cnt = cartService.insertItem(mno, pno, qty, opname);
		int ctno = cartService.getMaxCtno(mno);

		if (cnt > 0) {
			out.println("<script>");
			out.println("alert('주문정보 입력창으로 이동합니다.');");
			out.println("location='orderview?ctno="+ctno+"';");
			out.println("</script>");
		}
		return null;
    }
    
    // 주문OK
    @RequestMapping("/orderProc")
    public String orderProc(HttpServletRequest request, HttpServletResponse response,
    		ProductVO p, CategoryVO c, Model model) throws Exception{
    	response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		
		String ctno = request.getParameter("ctno"); // 바로 결제하기를 통해 넘어왔을 경우만 null이 아님
		String rowcheck = request.getParameter("rowcheck"); // 장바구니 통해서 넘어온 경우만 null이 아님
		
		MemberVO mbean = new MemberVO();
		mbean.setNo((int)session.getAttribute("memno"));
		mbean.setName(request.getParameter("name"));
		mbean.setZip(request.getParameter("zip"));
		mbean.setAddr1(request.getParameter("addr1"));
		mbean.setAddr2(request.getParameter("addr2"));
		mbean.setMobile1(request.getParameter("mobile1"));
		mbean.setMobile2(request.getParameter("mobile2"));
		mbean.setMobile3(request.getParameter("mobile3"));
		
		/* 결제를 진짜로 진행시키기 전에 재고가 결제를 진행시키기에 충분한지 체크 */
		boolean isEnoughStcok = true;
		String msg = "";
		
		if(ctno.equals("-1") == true){ // 장바구니를 통해 넘어온 경우 => rowcheck를 모두 이용
			String[] rowcheckArr = rowcheck.split(",");
			for(String no : rowcheckArr){
				//장바구니에 담겨있는 상품의 pno, 옵션 이름, 주문수량을 얻음
				CartVO ctbean = cartService.getItem(Integer.parseInt(no));
				int pno = ctbean.getPno();
				String opname = ctbean.getOpname();
				int qty = ctbean.getQty();
				System.out.println("ctbean - " + ctbean);
				//상품의 재고데이터를 가져와서 재고가 충분한지 체크함
				List<StockVO> stlist = stockService.getAllStockByPno(pno);
				for(StockVO stbean : stlist){
					if(opname.equals(stbean.getOpname())){
						ProductVO pbean = productService.getProduct(pno);
						System.out.println("계산결과");
						System.out.println(stbean.getCount() - qty < 0);
						if(stbean.getCount() - qty < 0){ // (해당 상품의 재고 - 주문할 수량)
							isEnoughStcok = false;
							msg += pbean.getName() + "의 재고부족. ";
							break;
						}
					}
				}
				if(isEnoughStcok){
					break;
				}
			}
		}
		else{	// 바로 결제하기를 타고 넘어온 경우 => 1개의 상품이므로 ctno를 바로이용
			
			//장바구니에 담겨있는 상품의 pno, 옵션 이름, 주문수량을 얻음
			CartVO ctbean = cartService.getItem(Integer.parseInt(ctno));
			int pno = ctbean.getPno();
			String opname = ctbean.getOpname();
			int qty = ctbean.getQty();
			
			//상품의 재고데이터를 가져와서 재고가 충분한지 체크함
			List<StockVO> stlist = stockService.getAllStockByPno(pno);
			for(StockVO stbean : stlist){
				if(opname.equals(stbean.getOpname())){
					ProductVO pbean = productService.getProduct(pno);
					System.out.println("계산결과");
					System.out.println(stbean.getCount() - qty < 0);
					if(stbean.getCount() - qty < 0){ // (해당 상품의 재고 - 주문할 수량)
						isEnoughStcok = false;
						msg += pbean.getName() + "의 재고부족. ";
						break;
					}
				}
			}
		}
		if(isEnoughStcok){
			//결제를 진행하고, 장바구니에서 해당 상품들을 제거
			if(ctno.equals("-1") == true){ // 장바구니를 통해 넘어온 경우 => rowcheck를 모두 이용
				String[] rowcheckArr = rowcheck.split(","); //rowcheck 파싱
				for(String no : rowcheckArr){
					CartVO ctbean = cartService.getItem(Integer.parseInt(no));
					orderservice.insertOrder(ctbean, mbean);
					stockService.updateByOrder(ctbean.getPno(), ctbean.getOpname(), ctbean.getQty());//해당 상품의 재고수량 update
				}
				// 주문이 완료된 상품은 장바구니에서 제거
				for(String no : rowcheckArr){
					cartService.deleteItem((Integer.parseInt(no))); 
				}
				msg = "주문성공";
			}else{	// 바로 결제하기를 타고 넘어온 경우 => 1개의 상품이므로 ctno를 바로이용
				CartVO ctbean = cartService.getItem(Integer.parseInt(ctno)); // 상품정보를 얻음.
				orderservice.insertOrder(ctbean, mbean);
				stockService.updateByOrder(ctbean.getPno(), ctbean.getOpname(), ctbean.getQty()); //해당 상품의 재고수량 update
				cartService.deleteItem(Integer.parseInt(ctno));// 주문이 완료된 상품은 장바구니에서 제거
				msg = "주문성공";
			}
		}
		out.println("<script>");
		out.println("alert('"+msg+".');");
		out.println("location='member_main';");
		out.println("</script>");
	
		return null;
    }

}
