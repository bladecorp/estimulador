<%-- <%@ include file="header.jsp"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false" %>
<div class="col-md-12 col-sm-12 col-xs-12">
	
		<div class="x_panel">
			<div class="x_title">
				<h2>Configurar Estimulaci&oacute;n</h2>
				<form action="" class="form-horizontal">
					<ul class="nav navbar-right panel_toolbox">
					<li>
						<div class="form-group">
						<!--                         <label class="control-label col-md-3 col-sm-3 col-xs-12">Buscar Por:</label> -->
						<div class="col-md-12 col-sm-12 col-xs-12">
<!-- 							<label>Buscar Por:</label> -->
							<p>
								ID <input
									type="radio" class="flat" name="tipoB" id="BuscarID" value="1" />
								Nombre <input type="radio" class="flat" name="tipoB" id="BuscarNombre" 
									value="2" checked="" required /> 
								A. Paterno <input type="radio" class="flat" name="tipoB" id="BuscarApaterno" 
									value="3" />
							</p>
						</div>
					</div>
					</li>
					<li>
						<div class="form-group">
							<input type="text" id="buscarPax" class="form-control" placeholder="PACIENTE">
						</div>
					</li>
				</form>
			</ul>
				<div class="clearfix"></div>
			</div>
			<div class="x_content text-center">
			<form action="" class="form-inline text-left">
				<div class="row">
					<div class="form-group  col-md-8 col-sm-12 col-xs-12">
						<span>Paciente: </span><span id="paxSel" style="font-weight:bold;font-size:15pt;"></span>
					</div>
					<div class="form-group">
                    	<label for="estimSel">Estimulador</label>
                    	<input type="text" id="estimSel" class="form-control" size="7" style="text-align:center;">
                  	</div>
					<div class="form-group ">
                    	<label for="vinculado">Vinculado</label>
                    	<input type="text" id="vinculado" class="form-control" size="1" style="text-align:center;">
                  	</div>
				</div>
			</form>
			<form id="formEstimulacion">
				<div class="row">
					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="ln_solid"></div>
						<span class="tituloKnob">Repeticiones</span> <br> <input 
							id="repeticiones" value="5">
					</div>
					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="ln_solid"></div>
						<span class="tituloKnob">Tiempo</span> <br> <input 
							id="tiempo" value="3">
					</div>
					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="ln_solid"></div>
						<span class="tituloKnob">Frecuencia</span> <br> <input 
							id="frecuencia" value="10.6">
					</div>
					<div class="col-md-3 col-sm-12 col-xs-12">
						<div class="ln_solid"></div>
						<span class="tituloKnob">Amplitud</span> <br> <input 
							id="amplitud" value="20">
					</div>
				</div>


				<!-- FILA 2 -->
				<div class="row">
					<div class="col-md-6 col-sm-12 col-xs-12">
						<span class="tituloKnob">Tipo de Onda</span> <br>
						<div class="form-group">
							<select id="tiposOnda" class="form-control">
								<c:forEach items="${tiposOnda}" var="onda">
									<option value="${onda.id}">${onda.tipo}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-md-6 col-sm-12 col-xs-12">
						<span class="tituloKnob">Rango de Fechas</span> <br>
						<div class="control-group">
							<div class="controls">
								<div class="input-prepend input-group">
									<span class="add-on input-group-addon"><i
										class="glyphicon glyphicon-calendar fa fa-calendar"></i></span> <input
										type="text" id="fechas" class="form-control" />
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- .FIN ROW 2 -->

				<!-- FILA 3 -->
				<div class="row">
					<div class="col-md-12 col-sm-12 col-xs-12">
						<span class="tituloKnob">Rango de Tiempo</span> <br> <input
							id="rangoHora">
					</div>
				</div>
				<!-- .FIN ROW 3 -->


				<!-- FILA 4 -->
				<div class="row">
					<div class="ln_solid"></div>
					<input type="submit" class="btn btn-primary" value="Estimular">
				</div>
			</form>
			</div>
		</div>
	
</div>



<%-- <%@ include file="footer.jsp"%> --%>
<!-- jQuery Knob -->
<%-- <script src="${c}/resources/js/jquery.knob.min.js"></script> --%>
<!-- moment JS -->
<%-- <script src="${c}/resources/js/moment-with-locales.min.js"></script> --%>
<!-- Slide Range Picker -->
<%-- <script src="${c}/resources/js/ion.rangeSlider.min.js"></script> --%>
<!-- Date Range Picker -->
<%-- <script src="${c}/resources/js/daterangepicker.js"></script> --%>
<!-- jQuery UI -->
<%-- <script src="${c}/resources/js/jquery-ui.min.js"></script> --%>

<%-- <script src="${c}/resources/js/interno/estimulacion.js"></script> --%>

<!-- </body> -->
<!-- </html> -->