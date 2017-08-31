<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>PAUSE</title>
	
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700' rel='stylesheet' type='text/css'>
    <link href='<c:url value="/css/bootstrap.min.css"/>' rel="stylesheet">
    <link href='<c:url value="/css/nifty.min.css"/>' rel="stylesheet">
    <link href='<c:url value="/css/themes/type-a/theme-light.min.css"/>' rel="stylesheet">
    <link href='<c:url value="/premium/icon-sets/icons/line-icons/premium-line-icons.min.css"/>' rel="stylesheet">
    <link href='<c:url value="/plugins/themify-icons/themify-icons.min.css"/>' rel="stylesheet">
    <link href='<c:url value="/plugins/font-awesome/css/font-awesome.min.css"/>' rel="stylesheet">
    <style>
		.dataTables_filter{ display: none; }
		@media (max-width: 600px){
			.tabela-css {display: block !important;}
		}
	</style>
	<!-- css serão carregados aqui -->
	<layout:block name="css"> </layout:block>
	
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>

</head>
<body>
	<sec:authentication property="principal" var="user"/>
	<div id="container" class="effect aside-fixed aside-bright mainnav-lg footer-fixed">
	
	<!--NAVBAR-->
        <!--===================================================-->
        <header id="navbar">
            <div id="navbar-container" class="boxed">
                <!--Brand logo & name-->
                <!--================================-->
                <div class="navbar-header">
                    <a href='<c:url value="/"/>' class="navbar-brand">
                        <img src='<c:url value="/img/logo.png"/>' alt="Nifty Logo" class="brand-icon">
                        <div class="brand-title">
                            <span class="brand-text">PAUSE</span>
                        </div>
                    </a>
                </div>
                <!--================================-->
                <!--End brand logo & name-->
                <!--Navbar-->
                <!--================================-->
                <div class="navbar-content clearfix">
					<ul class="nav navbar-top-links pull-left">
                        <!--Navigation toogle button-->
                        <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                        <li class="tgl-menu-btn">
                            <a class="mainnav-toggle" href="#">
                                <i class="pli-view-list"></i>
                            </a>
                        </li>
                        <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                        <!--End Navigation toogle button-->
					</ul>
                    <ul class="nav navbar-top-links pull-right">
                        <!--User-->
                        <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                        <li>
                            <div class="username text-right">${user.funcionario.nome }</div>
                        </li>
                        <!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
                        <!--End user-->
                        <li class="add-tooltip" data-toggle="tooltip" data-container="body" data-placement="bottom" data-original-title="Sair">
                            <a href="#" class="" data-target="#demo-sm-modal" data-toggle="modal"><i class="pli-arrow-inside"></i></a>
                        </li>
                    </ul>
                </div>
                <!--================================-->
                <!--End Navbar-->
            </div>
        </header>
        <!--===================================================-->
        <!--END NAVBAR-->
		<div class="boxed">
			<!--CONTENT CONTAINER-->
            <!--===================================================-->
            <div id="content-container">
				<!-- conteúdo da página será carregado aqui -->
				<layout:block name="contents"> </layout:block>
			</div>
            <!--===================================================-->
            <!--END CONTENT CONTAINER-->
			<!--MAIN NAVIGATION-->
            <!--===================================================-->
            <nav id="mainnav-container">
                <div id="mainnav">
                    <!--Menu-->
                    <!--================================-->
                    <div id="mainnav-menu-wrap">
                        <div class="nano">
                            <div class="nano-content">
                                <div id="mainnav-profile" class="mainnav-profile">
                                    <div class="profile-wrap">
                                        <div class="pad-btm">
                                            <img class="img-circle img-sm img-border" src='<c:url value="/img/profile-photos/1.png"/>' alt="Profile Picture">
                                        </div>
                                        <a href="#profile-nav" class="box-block" data-toggle="collapse" aria-expanded="false">
                                           <p class="mnp-name">${user.funcionario.nome}</p>
                                           <span class="mnp-desc">
                                           	<c:choose>
                                           		<c:when test="${not empty user.funcionario.emailCorporativo}">
                                           			${user.funcionario.emailCorporativo}
                                           		</c:when>
                                           		<c:otherwise>
                                           			Sem e-mail informado
                                           		</c:otherwise>
                                           	</c:choose>
                                           </span>
                                    	</a>
                                    </div>
                                </div>
                                <div id="mainnav-shortcut">
									<ul class="list-unstyled">
										<li class="col-xs-4" data-content="Shortcut 1" data-original-title="" title="">
										&nbsp
										</li>
									</ul>
								</div>
                                <ul id="mainnav-menu" class="list-group" style="margin-top:-25px;">
                                	<!--Category name-->
						            <li class="list-header">Operações</li>
                                	<li>
						                <a href='<c:url value="/importacao"/>'>
						                    <i class="pli-download"></i>
						                    <span class="menu-title">
												<strong>Importação</strong>
											</span>
						                </a>
						            </li>
						            <li>
						                <a href="#">
						                    <i class="pli-magnifi-glass"></i>
						                    <span class="menu-title">
												<strong>Consultar</strong>
											</span>
						                </a>
						            </li>
									<li>
						                <a href="#">
						                    <i class="pli-pen-5"></i>
						                    <span class="menu-title">
												<strong>Gerenciar horas</strong>
											</span>
						                </a>
						            </li>
									<li>
						                <a href='<c:url value="/relatorio"/>'>
						                    <i class="pli-bar-chart"></i>
						                    <span class="menu-title">
												<strong>Relatório</strong>
											</span>
						                </a>
						            </li>
									<li>
										<a href="#" class="" data-target="#demo-sm-modal" data-toggle="modal">
											<i class="pli-arrow-inside"></i>
											<span class="menu-title">
												<strong>Sair</strong>
											</span>
										</a>
						            </li>
								</ul>
                            </div>
                        </div>
                    </div>
                    <!--================================-->
                    <!--End menu-->
                </div>
            </nav>
            <!--===================================================-->
            <!--END MAIN NAVIGATION-->
		</div>
		<!-- FOOTER -->
        <!--===================================================-->
        <footer id="footer">
            <!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
            <!-- Remove the class "show-fixed" and "hide-fixed" to make the content always appears. -->
            <!-- ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ -->
            <p class="pad-lft">&#0169; 2017 Your Company</p>
        </footer>
        <!--===================================================-->
        <!-- END FOOTER -->
        <!-- SCROLL PAGE BUTTON -->
        <!--===================================================-->
        <button class="scroll-top btn">
            <i class="pci-chevron chevron-up"></i>
        </button>
        <!--===================================================-->
	</div>
    <!--===================================================-->
    <!-- END OF CONTAINER -->
	<!--Small Bootstrap Modal-->
    <!--===================================================-->
    <div id="demo-sm-modal" class="modal fade" tabindex="-1">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                    <h4 class="modal-title text-center" id="mySmallModalLabel">Deseja sair do sistema?</h4>
                </div>
                <div class="modal-body">
                    <div class="text-center">
						<a href='<c:url value="/logout"/>' class="btn btn-primary">Sim</a>
						<button data-dismiss="modal" class="btn btn-default" type="button">Não</button>
					</div>
                </div>
            </div>
        </div>
    </div>
    <!--===================================================-->
    <!--End Small Bootstrap Modal-->

    <script src='<c:url value="/js/jquery.min.js"/>'></script>
    <script src='<c:url value="/js/bootstrap.min.js"/>'></script>
    <script src='<c:url value="/js/nifty.min.js"/>'></script>
    
	<!-- scripts serão carregados aqui -->
	<layout:block name="scripts"> </layout:block>
</body>
</html>