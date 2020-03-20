<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  
  <title>관리자 페이지</title>
	<jsp:include page="/resources/admin_css.jsp"/>
</head>

<body id="page-top">
<!-- 관리자 체크 -->
<jsp:include page="/common/adminCheck.jsp"/>
  <!-- Page Wrapper -->
  <div id="wrapper">

	<jsp:include page="/admin/admin_sidebar.jsp"/>

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">
      
		<jsp:include page="/admin/admin_header.jsp"/>
		
        <!-- Begin Page Content -->
        <div class="container-fluid">
         	<jsp:include page='${pagefile }'></jsp:include>
        </div>
        
        <!-- /.container-fluid -->
      </div>
      <!-- End of Main Content -->
    </div>
    <!-- End of Content Wrapper -->
  </div>
  <!-- End of Page Wrapper -->

  <!-- Scroll to Top Button-->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <jsp:include page="/resources/admin_js.jsp"/>
</body>

</html>
