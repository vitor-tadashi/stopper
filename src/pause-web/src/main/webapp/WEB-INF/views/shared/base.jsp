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
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	
	<title>PAUSE</title>
	
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700' rel='stylesheet' type='text/css'>
    <link href='<c:url value="/css/bootstrap.min.css"/>' rel="stylesheet">
    <link href='<c:url value="/plugins/bootstrap-select/bootstrap-select.min.css"/>' rel="stylesheet">
    <link href='<c:url value="/css/nifty.min.css"/>' rel="stylesheet">
    <link href='<c:url value="/css/themes/type-a/theme-light.min.css"/>' rel="stylesheet">
    <link href='<c:url value="/premium/icon-sets/icons/line-icons/premium-line-icons.min.css"/>' rel="stylesheet">
    <link href='<c:url value="/plugins/font-awesome/css/font-awesome.min.css"/>' rel="stylesheet">
    <link href='<c:url value="/css/custom/custom.css"/>' rel="stylesheet">
	<!-- css serão carregados aqui -->
	<layout:block name="css"> </layout:block>
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
                    <a class="navbar-brand" href='<c:url value="/"/>'>
                        <img src='img/logo.png' alt="PAUSE" class="brand-icon">
                        <div class="brand-title">
                            <span class="brand-text"></span>
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
                        <sec:authorize access="hasRole('ROLE_MULTI-EMPRESA')">
                        <li>
                            <div class="username text-right"><a href="/pause/selecionaMultiEmpresa">Trocar empresa</a></div>
           
                        </sec:authorize>
                       
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
                                            <c:choose>
                                           		<c:when test="${user.funcionario.genero.genero eq 'Feminino'}">
                                           			<img class="img-circle img-sm img-border" src='<c:url value="/img/profile-photos/1.png"/>' alt="Profile Picture">
                                           		</c:when>
                                           		<c:otherwise>
                                           			<img class="img-circle img-sm img-border" src='<c:url value="/img/profile-photos/7.png"/>' alt="Profile Picture">
                                           		</c:otherwise>
                                           	</c:choose>
                                        </div>
                                        <a  class="box-block" data-toggle="collapse" aria-expanded="false">
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
										&nbsp;
										</li>
									</ul>
								</div>
                                <ul id="mainnav-menu" class="list-group" style="margin-top:-25px;">
                                	<!--Category name-->
						            <li class="list-header">Operações</li>
						            
						            <sec:authorize access="hasAnyRole('ROLE_IMPORTAR_APONTAMENTOS', 'ROLE_MULTI_EMPRESA')">
							           <li>
							            
	                                		<a href='<c:url value="/importacao"/>' data-original-title="" title="">
	                                            <i class="pli-download"></i>
	                                            <span class="menu-title">
	                                            	Importar
	                                            </span>
	                                        </a>
							            </li>
						            </sec:authorize>
									<li>
                                		<a href='<c:url value="/gerenciar-apontamento"/>'>
                                            <i class="pli-page"></i>
                                            <span class="menu-title">
                                            	Gerenciar horas
                                            </span>
                                        </a>
						            </li>
						            
						            <sec:authorize access="hasAnyRole('ROLE_CONSULTAR_BANCO', 'ROLE_MULTI_EMPRESA')">
							            <li>
	                                		<a href='<c:url value="/consultar-apontamento"/>'>
	                                            <i class="pli-magnifi-glass"></i>
	                                            <span class="menu-title">
	                                            	Consultar banco
	                                            </span>
	                                        </a>	
							            </li>
						            </sec:authorize>
    
						            <sec:authorize access="hasAnyRole('ROLE_GERAR_RELATORIOS', 'ROLE_MULTI_EMPRESA')">
										<li>
	                                		<a href='<c:url value="/relatorio"/>'>
	                                            <i class="pli-bar-chart"></i>
	                                            <span class="menu-title">
	                                            	Relatório
	                                            </span>
	                                        </a>
							            </li>
						            </sec:authorize>
								
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
            <p class="pad-lft">&#0169; Verity Consultoria Em Tecnologia Ltda</p>
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
    <script src='<c:url value="/plugins/bootstrap-select/bootstrap-select.min.js"/>'></script>
    <script src='<c:url value="/js/nifty.min.js"/>'></script>

    <script type="text/javascript">
    	function formatardataHtml(s){
		    if (s) { 
			    s = s.replace(/(\d{4})-(\d{1,2})-(\d{1,2})/, function(match,y,m,d) { 
			        return d + '/' + m + '/' + y;  
			    });
			}
		    return s;
    	}
    	
    	$( document ).ready(function() {
    		var year = new Date();
    		
    		year = year.getFullYear();
    		
    		$('#footer').html('&#0169; ' + year + ' Verity Consultoria Em Tecnologia Ltda');
    		
    		$('[data-toggle="tooltip"]').tooltip();   
    	});
    	
    </script>
	<!-- scripts serão carregados aqui -->
	<layout:block name="scripts"> </layout:block>
</body>
</html>