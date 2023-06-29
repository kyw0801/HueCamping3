package net.hue.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.hue.service.CartService;
import net.hue.service.CategoryService;
import net.hue.service.MemberService;
import net.hue.service.OrderService;
import net.hue.service.ProductService;
import net.hue.service.StockService;
import net.hue.vo.CategoryVO;
import net.hue.vo.MemberVO;
import net.hue.vo.OrderVO;
import net.hue.vo.ProductVO;
import net.hue.vo.StockVO;

@Controller
public class AdminController {
	
	@Autowired
    private MemberService memberService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private OrderService OrderService;
    
    @Autowired
    private StockService stockService;
    
    //카테고리관리
    @RequestMapping("/admin_category")
    public String adminCate(HttpServletRequest request, HttpServletResponse response, 
    		CategoryVO c, Model model, HttpSession session ) throws Exception{
    	
    	response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String admin_id = (String)session.getAttribute("admin_id");
		
		/*if(admin_id == null) {
			out.println("<script>");
			out.println("alert('관리자로 다시 로그인 하세요!');");
			out.println("location='admin_login';");
			out.println("</script>");
		}else {
			*/
			List<CategoryVO> clist = categoryService.getAllCategory(c);
	        model.addAttribute("clist", clist);
	        
			return "admin/categoryManage/categoryManage";
		//}
    	//return null;
    }
    
    // L카테고리 입력
    @RequestMapping("/insertLCategory")
    public ModelAndView insertLCate(HttpServletRequest request, HttpServletResponse response, 
    		CategoryVO c, HttpSession session ) throws Exception{
    	
    	response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String lcname = request.getParameter("lcname");
		
		c.setLname(lcname);
		
		this.categoryService.insertLCategory(c);

		out.println("<script>");
		out.println("alert('대분류 추가 성공');");
		out.println("location='/admin_category';");
		out.println("</script>");
		
		return null;

    }
    // S카테고리 입력
    @RequestMapping("/insertSCategory")
    public ModelAndView insertSCate(HttpServletRequest request, HttpServletResponse response, 
    		CategoryVO c, HttpSession session ) throws Exception{
    	response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String sname = request.getParameter("scname");
		
		c.setSname(sname);

		this.categoryService.insertSCategory(c);

		out.println("<script>");
		out.println("alert('소분류 추가 성공');");
		out.println("location='/admin_category';");
		out.println("</script>");
		
		return null;
    }
    
    // L카테고리 삭제
    @RequestMapping("/LCateDel_OK")
	public String LCateDel_OK(HttpServletResponse response,
			HttpServletRequest request, CategoryVO c) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		int lno = Integer.parseInt(request.getParameter("lno"));
		int lstep = Integer.parseInt(request.getParameter("lstep"));
		
		int cnt = categoryService.countScategory(lno);

		String msg = "";
		if(cnt != 0){
			msg = "소분류가 존재하여 삭제가 불가합니다.";
		}
		else{
			int result = this.categoryService.deleteLCategory(lno, lstep);
			if(result > 0){
				msg = "대분류 삭제 성공";		
			}
			else{
				msg = "대분류 삭제 실패";	
			}
		}
		out.println("<script>");
		out.println("alert('"+msg+"');");
		out.println("location='/admin_category';");
		out.println("</script>");	
	
		return null;
	}
    // S카테고리 삭제
    @RequestMapping("/SCateDel_OK")
	public String SCateDel_OK(HttpServletResponse response,
			HttpServletRequest request, CategoryVO c) throws Exception{
    	response.setContentType("text/html;charset=UTF-8");
    	PrintWriter out = response.getWriter(); // 출력스트림
    	request.setCharacterEncoding("UTF-8");

    	int sno = Integer.parseInt(request.getParameter("sno"));

    	int cnt = categoryService.deleteSCategory(sno);

    	String msg = "";
    	
    	if(cnt > 0){
    		msg = "소분류 삭제 성공";		
    	}
    	else{
    		msg = "소분류 삭제 실패";	
    	}
    	
    	out.println("<script>");
    	out.println("alert('"+msg+"');");
    	out.println("location='/admin_category';");
    	out.println("</script>");	
    	
    	return null;	
	}
    
    // L카테고리 수정
    @RequestMapping("/LCateUpdate_OK")
	public String LCateUpdate_OK(HttpServletResponse response,
			HttpServletRequest request, CategoryVO c) throws Exception{
    	response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		request.setCharacterEncoding("UTF-8");
		
		int lno = Integer.parseInt(request.getParameter("lno"));
		String i = request.getParameter("i");	
		String lcname = request.getParameter("lcname" + i);

		int cnt = categoryService.updateLCategoryName(lcname, lno);

		if(cnt > 0){
			out.println("<script>");
			out.println("alert('대분류 수정 성공');");
			out.println("location='admin_category';");
			out.println("</script>");	
		}
		else{
			out.println("<script>");
			out.println("alert('대분류 수정 실패');");
			out.println("location='admin_category.';");
			out.println("</script>");
		}

		return null;
	}
    // S카테고리 수정
    @RequestMapping("/SCateUpdate_OK")
	public String SCateUpdate_OK(HttpServletResponse response,
			HttpServletRequest request, CategoryVO c) throws Exception{
    	response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		request.setCharacterEncoding("UTF-8");
		
		int sno = Integer.parseInt(request.getParameter("sno"));
		String i = request.getParameter("i");	
		String scname = request.getParameter("scname" + i);
		
		System.out.println("sno:" + sno);
		System.out.println("i:" + i);
		System.out.println("scname:" + scname);
		
		int cnt = categoryService.updateSCategoryName(scname, sno);
		
		if(cnt > 0){
			out.println("<script>");
			out.println("alert('소분류 수정 성공');");
			out.println("location='admin_category';");
			out.println("</script>");	
		}
		else{
			out.println("<script>");
			out.println("alert('소분류 수정 실패');");
			out.println("location='admin_category.';");
			out.println("</script>");
		}
		return null;
	}
    
    // L카테고리 이동 - 위
    @RequestMapping("/LCateUp")
	public String LCateUp(HttpServletResponse response,
			HttpServletRequest request, CategoryVO c) throws Exception{
    	response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		int lno = Integer.parseInt(request.getParameter("lno"));
		int lstep = Integer.parseInt(request.getParameter("lstep")); // 해당 대분류의 순서
		
		int result = -1;
		
		if (lstep > 1) { // 수정하려는 행의 step이 맨 첫번째가 아닌 경우
			result = categoryService.updateUpLStep(lno, lstep);
			
			return "redirect:admin_category";
		}else if(lstep == 1) {
			out.println("<script>");
			out.println("alert('대분류의 처음입니다.');");
			out.println("location='admin_category';");
			out.println("</script>");
		}
		return null;
	}
    // L카테고리 이동 - 아래
    @RequestMapping("/LCateDown")
	public String LCateDown(HttpServletResponse response,
			HttpServletRequest request, CategoryVO c) throws Exception{
    	response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		
		request.setCharacterEncoding("UTF-8");
		
		int lno = Integer.parseInt(request.getParameter("lno"));
		int lstep = Integer.parseInt(request.getParameter("lstep")); // 해당 대분류의 순서
		
		int totalRows = categoryService.countLcategory(c);
		int result = -1;
		
		if (totalRows != -1 && lstep < totalRows) { // cdao.countLcategory() 처리가 성공하였고, 수정하려는 행의 step이 마지막이 아닌 경우
			result = categoryService.updateDownLStep(lno, lstep);
		
			return "redirect:admin_category";
		}
		else if(totalRows != -1 && lstep == totalRows){
			out.println("<script>");
			out.println("alert('대분류의 끝입니다.');");
			out.println("location='admin_category';");
			out.println("</script>");
		}
		return null;
	}
    // S카테고리 이동 - 위
    @RequestMapping("/SCateUp")
    public String SCateUp(HttpServletResponse response,
    		HttpServletRequest request, CategoryVO c) throws Exception{
    	response.setContentType("text/html;charset=UTF-8");
    	PrintWriter out = response.getWriter(); // 출력스트림

    	request.setCharacterEncoding("UTF-8");

    	int lno = Integer.parseInt(request.getParameter("lno"));
    	int sno = Integer.parseInt(request.getParameter("sno"));
		int sstep = Integer.parseInt(request.getParameter("sstep")); // 해당 대분류의 순서

    	int result = -1;

    	if (sstep > 1) { // 수정하려는 행의 step이 맨 첫번째가 아닌 경우
			result = categoryService.updateUpSStep(lno, sno, sstep);
			
			return "redirect:admin_category";
		}else if(sstep == 1) {
			out.println("<script>");
			out.println("alert('소분류의 처음입니다.');");
			out.println("location='admin_category';");
			out.println("</script>");
		}
		return null;
    }
    // S카테고리 이동 - 아래
    @RequestMapping("/SCateDown")
    public String SCateDown(HttpServletResponse response,
    		HttpServletRequest request, CategoryVO c) throws Exception{
    	response.setContentType("text/html;charset=UTF-8");
    	PrintWriter out = response.getWriter(); // 출력스트림

    	request.setCharacterEncoding("UTF-8");

    	int lno = Integer.parseInt(request.getParameter("lno"));
		int sno = Integer.parseInt(request.getParameter("sno"));
		int sstep = Integer.parseInt(request.getParameter("sstep")); // 해당 대분류의 순서

		int totalRows = categoryService.countScategory(lno);	// lno에 해당하는 소분류의 총 개수	
    	int result = -1;

    	if (totalRows != -1 && sstep < totalRows) { // cdao.countLcategory() 처리가 성공하였고, 수정하려는 행의 step이 마지막이 아닌 경우
			result = categoryService.updateDownSStep(lno, sno, sstep);

    		return "redirect:admin_category";
    	}
    	else if(totalRows != -1 && totalRows == sstep) {
			out.println("<script>");
			out.println("alert('소분류의 끝입니다.');");
			out.println("location='admin_category';");
			out.println("</script>");
    	}
    	return null;
    }
    
    
    
    //주문관리
    @RequestMapping("/admin_order")
    public ModelAndView adminOrder(HttpServletRequest request, HttpServletResponse response, 
    		OrderVO o, HttpSession session ) throws Exception{
    	
    	response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String admin_id = (String)session.getAttribute("admin_id");
		
		/*if(admin_id == null) {
			out.println("<script>");
			out.println("alert('관리자로 다시 로그인 하세요!');");
			out.println("location='admin_login';");
			out.println("</script>");
		}else {
			*/
			int page=1;
			int limit=10;
			
			if(request.getParameter("page") != null) {
				page=Integer.parseInt(request.getParameter("page"));
			}
			
			String find_name=request.getParameter("find_name");//검색어
			String find_field=request.getParameter("find_field");//검색 필드
			
			o.setFind_field(find_field); 
			o.setFind_name("%"+find_name+"%");
			
			int listcount=OrderService.getListCount(o);
			
			o.setStartrow((page-1)*10+1);//시작행번호
			o.setEndrow(o.getStartrow()+limit-1);//끝행번호
			
			List<OrderVO> olist=OrderService.getOrderList(o);

			//페이지 연산
	        int maxpage=(int)((double)listcount/limit+0.95);//총 페이지수
	        int startpage=(((int)((double)page/10+0.9))-1)*10+1;//시작페이지
	        int endpage=maxpage;//마지막 페이지
	        if(endpage>startpage+10-1) endpage=startpage+10-1;
	        ModelAndView listO = new ModelAndView();
	        listO.addObject("olist", olist);
	        listO.addObject("page",page);
	        listO.addObject("startpage",startpage);
	        listO.addObject("endpage",endpage);
	        listO.addObject("maxpage",maxpage);
	        listO.addObject("listcount",listcount);   
	        listO.addObject("find_field",find_field);
	        listO.addObject("find_name", find_name);

	        listO.setViewName("admin/orderManage/orderList");
			
			return listO;
			
		//}
    	//return null;
    }
    
    //주문 삭제
    @RequestMapping("/ordre_delete_ok")
    public ModelAndView ordre_delete_ok(HttpServletRequest request, HttpServletResponse response, 
    		ProductVO p, HttpSession session ) throws Exception{
    	
    	response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();

		String msg = "";
		
		int pno = Integer.parseInt(request.getParameter("pno"));
		
		int cnt = this.OrderService.deleteorder(pno);
		
		if(cnt > 0) {
			out.println("<script>");
			out.println("alert('주문목록 삭제 완료되었습니다.');");
			out.println("location='admin_order';");
			out.println("</script>");
		}	
		return null;
    }
    
    //상품관리
    @RequestMapping("/admin_product")
    public ModelAndView adminProd(HttpServletRequest request, HttpServletResponse response, 
    		ProductVO p,  HttpSession session ) throws Exception{
    	
    	response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String admin_id = (String)session.getAttribute("admin_id");
		
		/*if(admin_id == null) {
			out.println("<script>");
			out.println("alert('관리자로 다시 로그인 하세요!');");
			out.println("location='admin_login';");
			out.println("</script>");
		}else {
			*/
			int page=1;
			int limit=10;

			if(request.getParameter("page") != null) {
				page=Integer.parseInt(request.getParameter("page"));
			}
			
			String find_name=request.getParameter("find_name");//검색어
			String find_field=request.getParameter("find_field");//검색 필드
			p.setFind_field(find_field); 
			p.setFind_name("%"+find_name+"%");
			
			int listcount=productService.getListCount(p);
			
			p.setStartrow((page-1)*10+1);//시작행번호
			p.setEndrow(p.getStartrow()+limit-1);//끝행번호
			
			List<ProductVO> plist=productService.getProdList(p);

			//페이지 연산
	        int maxpage=(int)((double)listcount/limit+0.95);//총 페이지수
	        int startpage=(((int)((double)page/10+0.9))-1)*10+1;//시작페이지
	        int endpage=maxpage;//마지막 페이지
	        if(endpage>startpage+10-1) endpage=startpage+10-1;
	        ModelAndView listP = new ModelAndView();
	        listP.addObject("plist", plist);
	        listP.addObject("page",page);
	        listP.addObject("startpage",startpage);
	        listP.addObject("endpage",endpage);
	        listP.addObject("maxpage",maxpage);
			listP.addObject("listcount",listcount);   
			listP.addObject("find_field",find_field);
			listP.addObject("find_name", find_name);

			listP.setViewName("admin/productManage/prodList");//뷰페이지 경로
			
			return listP;
	        
		//}
    	//return null;
    }
    
    // 상품 등록 페이지
    @RequestMapping("/insertProduct")
	public String insertProduct(HttpServletResponse response, HttpSession session, 
			HttpServletRequest request, CategoryVO c, Model model) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		/*
		if(isAdminLogin(session, response)) {  // true 이면 관리자로 로그인 된 상태
			int page = 1;
		 */	
		List<CategoryVO> lcateList = this.categoryService.getLCategory(c);
		request.setCharacterEncoding("UTF-8");

		model.addAttribute("lcateList", lcateList);
		return "admin/productManage/prodInsertForm";			
		//}
		//return null;
    }
    // 소분류 리턴
    @GetMapping("/returnSCategory")
    public JSONObject returnSCategory(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
    	response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		request.setCharacterEncoding("UTF-8");
		
		int lno = Integer.parseInt(request.getParameter("lno"));
		
		List<CategoryVO> scateList = categoryService.getOnlySmallCategory(lno);

		JSONObject jsonList = new JSONObject();
		JSONArray itemList = new JSONArray();
		
		for(CategoryVO cbean : scateList){
			JSONObject tempJson = new JSONObject();
			tempJson.put("sno", cbean.getSno());
			tempJson.put("sname", cbean.getSname());
			itemList.add(tempJson);
		}
		jsonList.put("ITEMS", itemList);
		out.print(jsonList);
		
        return null;
    }
	// 상품 등록 저장
	@RequestMapping("/insertProduct_OK")
	public ModelAndView insertProduct_OK(HttpSession session, HttpServletResponse response, 
			HttpServletRequest request, ProductVO p, StockVO s) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter(); // 출력스트림
		request.setCharacterEncoding("UTF-8");
		
		// 1. 웹서버 내에서 저장될 위치를 지정하기 위해, jsp의 내장객체 config를 이용해서 웹서버 내에서 특정 위치의 경로를 생성
		String targetLocation = request.getRealPath("product_images");
		 
		// 2. 최대 업로드 가능 사이즈를 지정 및 인코딩 방식 지정
		int maxSize = 1024 * 1024 * 10; 	// 5메가
		String encType = "UTF-8";
		// 3. 웹서버 폴더 내의 targetLocation 경로로 업로드 실행(객체 생성 = 업로드 실행임)
		MultipartRequest multi = new MultipartRequest(request, targetLocation, maxSize, encType, new DefaultFileRenamePolicy());
		/* form에서 요청한 정보를 가져오는 방법(바로 위에서 만든 객체를 이용함, 파일 업로드를 위해 넘어온 경우에는 request 내장 객체로 접근 불가) */
		
		String selLargeCategory = multi.getParameter("selLargeCategory");
		String selSmallCategory = multi.getParameter("selSmallCategory");
		String name = multi.getParameter("name");
		int oriprice = Integer.parseInt(multi.getParameter("oriprice"));
		int discprice = Integer.parseInt(multi.getParameter("discprice"));
		String info = multi.getParameter("info");
		File mainImg = multi.getFile("mainImg");
		File detailImg1 = multi.getFile("detailImg1");
		File detailImg2 = multi.getFile("detailImg2");
		File detailImg3 = multi.getFile("detailImg3");
		File detailImg4 = multi.getFile("detailImg4");
		
		p.setLcname(selLargeCategory); p.setScname(selSmallCategory); p.setName(name);
		p.setOriprice(oriprice); p.setDiscprice(discprice); p.setInfo(info); 
		if(mainImg!=null) {//첨부한 이진파일이 있는 경우 실행
			String omainImg=mainImg.getName();//첨부한 파일명
			Calendar cal=Calendar.getInstance();//칼렌더는 추상클래스로 new로 객체 생성을 못함. 년월일 시분초 값을 반환
			int year=cal.get(Calendar.YEAR);//년도값
			int month=cal.get(Calendar.MONTH)+1;//월값, +1을 한 이유는 1월이 0으로 반환 되기 때문에
			int date=cal.get(Calendar.DATE);//일값
			String homedir=targetLocation+"/"+year+"-"+month+"-"+date;//오늘 날짜 폴더 경로 저장
			File path01=new File(homedir);
			if(!(path01.exists())){
				path01.mkdir();//오늘날짜 폴더 생성
			}

			//첫문자는 0부터 시작
			String remainImg="product"+omainImg;//새로운 파일명 저장
			String mainImgDBName="/"+year+"-"+month+"-"+date+"/"+remainImg;//데이터베이스에 저장될 레코드값
			mainImg.renameTo(new File(homedir+"/"+remainImg));
			p.setMainImgN(mainImgDBName);
		}else {//첨부파일이 없는 경우
			String mainImgDBName="";
			p.setMainImgN(mainImgDBName);
		}
		if(detailImg1!=null) {//첨부한 이진파일이 있는 경우 실행
			String odetailImg1=detailImg1.getName();//첨부한 파일명
			Calendar cal=Calendar.getInstance();//칼렌더는 추상클래스로 new로 객체 생성을 못함. 년월일 시분초 값을 반환
			int year=cal.get(Calendar.YEAR);//년도값
			int month=cal.get(Calendar.MONTH)+1;//월값, +1을 한 이유는 1월이 0으로 반환 되기 때문에
			int date=cal.get(Calendar.DATE);//일값
			String homedir=targetLocation+"/"+year+"-"+month+"-"+date;//오늘 날짜 폴더 경로 저장
			File path01=new File(homedir);
			if(!(path01.exists())){
				path01.mkdir();//오늘날짜 폴더 생성
			}

			//첫문자는 0부터 시작
			String redetailImg1="product"+odetailImg1;//새로운 파일명 저장
			String detailImg1DBName="/"+year+"-"+month+"-"+date+"/"+redetailImg1;//데이터베이스에 저장될 레코드값
			detailImg1.renameTo(new File(homedir+"/"+redetailImg1));
			p.setDetailImgN1(detailImg1DBName);
		}else {//첨부파일이 없는 경우
			String detailImg1DBName="";
			p.setDetailImgN1(detailImg1DBName);
		}
		if(detailImg2!=null) {//첨부한 이진파일이 있는 경우 실행
			String odetailImg2=detailImg2.getName();//첨부한 파일명
			Calendar cal=Calendar.getInstance();//칼렌더는 추상클래스로 new로 객체 생성을 못함. 년월일 시분초 값을 반환
			int year=cal.get(Calendar.YEAR);//년도값
			int month=cal.get(Calendar.MONTH)+1;//월값, +1을 한 이유는 1월이 0으로 반환 되기 때문에
			int date=cal.get(Calendar.DATE);//일값
			String homedir=targetLocation+"/"+year+"-"+month+"-"+date;//오늘 날짜 폴더 경로 저장
			File path01=new File(homedir);
			if(!(path01.exists())){
				path01.mkdir();//오늘날짜 폴더 생성
			}

			//첫문자는 0부터 시작
			String redetailImg2="product"+odetailImg2;//새로운 파일명 저장
			String detailImg2DBName="/"+year+"-"+month+"-"+date+"/"+redetailImg2;//데이터베이스에 저장될 레코드값
			detailImg2.renameTo(new File(homedir+"/"+redetailImg2));
			p.setDetailImgN2(detailImg2DBName);
		}else {//첨부파일이 없는 경우
			String detailImg2DBName="";
			p.setDetailImgN2(detailImg2DBName);
		}
		if(detailImg3!=null) {//첨부한 이진파일이 있는 경우 실행
			String odetailImg3=detailImg3.getName();//첨부한 파일명
			Calendar cal=Calendar.getInstance();//칼렌더는 추상클래스로 new로 객체 생성을 못함. 년월일 시분초 값을 반환
			int year=cal.get(Calendar.YEAR);//년도값
			int month=cal.get(Calendar.MONTH)+1;//월값, +1을 한 이유는 1월이 0으로 반환 되기 때문에
			int date=cal.get(Calendar.DATE);//일값
			String homedir=targetLocation+"/"+year+"-"+month+"-"+date;//오늘 날짜 폴더 경로 저장
			File path01=new File(homedir);
			if(!(path01.exists())){
				path01.mkdir();//오늘날짜 폴더 생성
			}

			//첫문자는 0부터 시작
			String redetailImg3="product"+odetailImg3;//새로운 파일명 저장
			String detailImg3DBName="/"+year+"-"+month+"-"+date+"/"+redetailImg3;//데이터베이스에 저장될 레코드값
			detailImg3.renameTo(new File(homedir+"/"+redetailImg3));
			p.setDetailImgN3(detailImg3DBName);
		}else {//첨부파일이 없는 경우
			String detailImg3DBName="";
			p.setDetailImgN3(detailImg3DBName);
		}
		if(detailImg4!=null) {//첨부한 이진파일이 있는 경우 실행
			String odetailImg4=detailImg4.getName();//첨부한 파일명
			
			Calendar cal=Calendar.getInstance();//칼렌더는 추상클래스로 new로 객체 생성을 못함. 년월일 시분초 값을 반환
			int year=cal.get(Calendar.YEAR);//년도값
			int month=cal.get(Calendar.MONTH)+1;//월값, +1을 한 이유는 1월이 0으로 반환 되기 때문에
			int date=cal.get(Calendar.DATE);//일값
			String homedir=targetLocation+"/"+year+"-"+month+"-"+date;//오늘 날짜 폴더 경로 저장
			File path01=new File(homedir);
			if(!(path01.exists())){
				path01.mkdir();//오늘날짜 폴더 생성
			}

			//첫문자는 0부터 시작
			String redetailImg4="product"+odetailImg4;//새로운 파일명 저장
			String detailImg4DBName="/"+year+"-"+month+"-"+date+"/"+redetailImg4;//데이터베이스에 저장될 레코드값

			detailImg4.renameTo(new File(homedir+"/"+redetailImg4));
			
			p.setDetailImgN4(detailImg4DBName);
		}else {//첨부파일이 없는 경우
			String detailImg4DBName="";
			p.setDetailImgN4(detailImg4DBName);
		}
		
		
		String preParseStr = multi.getParameter("opnums");
		String[] strArr = preParseStr.split(",");
		
		for(String n : strArr){
			String opname = multi.getParameter("opn_" + n); // 실제로 값을 얻는 부분
			int count = Integer.parseInt(multi.getParameter("stock_" + n));
			System.out.println(opname + " " + count);
			
			s.setOpname(opname); s.setCount(count);
		}

		int cnt1 = productService.insertProduct(p);
		if(cnt1 > 0){
			System.out.println("상품 삽입 완료");
		}else{
			System.out.println("상품 삽입 실패");
		}
		
		int cnt2 = stockService.insertStock(s);
		
		if(cnt2 > 0){
			System.out.println("재고 삽입 완료");
		}else{
			System.out.println("재고 삽입 실패");
		}
		out.println("<script>");
		out.println("alert('상품등록에 성공하였습니다.');");
		out.println("location='/admin_product';");
		out.println("</script>");
		
		return null;
	}
	
	// 상품 삭제
	@RequestMapping("/prodDel_OK")
	public ModelAndView prodDel_OK(int pno, HttpServletResponse response,
			HttpServletRequest request, ProductVO p) throws Exception{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		
		ProductVO plist = this.productService.getProduct(pno);
		request.setAttribute("plist", plist);
		
		/* 존재하는 사진부터 지우기 */
		String targetLocation = request.getServletContext().getRealPath("/WEB-INF/views/admin/product_images"); 
		
		if(p.getMainImgN() != null){
			File delFile = new File(targetLocation, p.getMainImgN());
			boolean isSuccessed = false;
			if(delFile.exists()){ // true, false
				isSuccessed = delFile.delete(); // 파일 삭제
			}
		}
		if(p.getDetailImgN1() != null){
			File delFile = new File(targetLocation, p.getDetailImgN1());
			boolean isSuccessed = false;
			if(delFile.exists()){ // true, false
				isSuccessed = delFile.delete(); // 파일 삭제
			}
		}
		if(p.getDetailImgN2() != null){
			File delFile = new File(targetLocation, p.getDetailImgN2());
			boolean isSuccessed = false;
			if(delFile.exists()){ // true, false
				isSuccessed = delFile.delete(); // 파일 삭제
			}	
		}
		if(p.getDetailImgN3() != null){
			File delFile = new File(targetLocation, p.getDetailImgN3());
			boolean isSuccessed = false;
			if(delFile.exists()){ // true, false
				isSuccessed = delFile.delete(); // 파일 삭제
			}	
		}
		if(p.getDetailImgN4() != null){
			File delFile = new File(targetLocation, p.getDetailImgN4());
			boolean isSuccessed = false;
			if(delFile.exists()){ // true, false
				isSuccessed = delFile.delete(); // 파일 삭제
			}	
		}
		
		/* DB에서 해당 행 지우기 */
		//cnt값은 사실
		int cnt = this.productService.prodDel(pno);

		if(cnt > 0) {
			/* 해당하는 상품번호에 사이즈테이블 데이터를 삭제 한다. */
			int cnt2 = stockService.deleteAllStock(pno);
			out.println("<script>");
			out.println("alert('상품삭제가 완료되었습니다.');");
			out.println("location='/admin_product';");
			out.println("</script>");
		}else {
			out.println("<script>");
			out.println("alert('상품삭제가 실패되었습니다.');");
			out.println("location='/admin_product';");
			out.println("</script>");
		}
		return null;
	}
    
    
    //회원관리
    @RequestMapping("/admin_member")
    public ModelAndView adminMember(HttpServletRequest request, HttpServletResponse response, 
    		MemberVO m, HttpSession session ) throws Exception{
    	
    	response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String admin_id = (String)session.getAttribute("admin_id");
		
		/*if(admin_id == null) {
			out.println("<script>");
			out.println("alert('관리자로 다시 로그인 하세요!');");
			out.println("location='admin_login';");
			out.println("</script>");
		}else {
			*/
			int page=1;
			int limit=10;
			
			if(request.getParameter("page") != null) {
				page=Integer.parseInt(request.getParameter("page"));
			}
			
			String find_name=request.getParameter("find_name");//검색어
			String find_field=request.getParameter("find_field");//검색 필드
			m.setFind_field(find_field); 
			m.setFind_name("%"+find_name+"%");
			
			int listcount=memberService.getListCount(m);

			m.setStartrow((page-1)*10+1);//시작행번호
			m.setEndrow(m.getStartrow()+limit-1);//끝행번호
			
			List<MemberVO> mlist = memberService.getMemList(m);
			
			//페이지 연산
	        int maxpage=(int)((double)listcount/limit+0.95);//총 페이지수
	        int startpage=(((int)((double)page/10+0.9))-1)*10+1;//시작페이지
	        int endpage=maxpage;//마지막 페이지
	        if(endpage>startpage+10-1) endpage=startpage+10-1;
	        ModelAndView listM = new ModelAndView();
	        listM.addObject("mlist",mlist);//list속성키이름에 목록을 저장
	        listM.addObject("page", page);//쪽번호 
	        listM.addObject("startpage",startpage);//시작페이지
	        listM.addObject("endpage",endpage);//마지막 페이지
	        listM.addObject("maxpage",maxpage);
	        listM.addObject("listcount",listcount);
	        listM.addObject("find_field",find_field);
	        listM.addObject("find_name",find_name);
	        
	        listM.setViewName("admin/userManage/userList");
	        
			return listM;
		//}
    	//return null;
    }    
	

}
