<%-- <%@ include file="header.jsp"%> --%>
<%@ page session="false" %>

<div class="col-md-4 col-sm-12 col-xs-12">
	<div class="row">
		<div class="x_panel">
			<div class="x_title">
				<h2>Buscar Paciente</h2>
				<div class="clearfix"></div>
			</div>
			<div class="x_content text-center">
				<form action="" class="form-horizontal form-label-left">
					<div class="col-md-12 col-sm-12 col-xs-12 form-group">
						<input id="paxAutocomplete" type="text" class="form-control"
							placeholder="BUSCAR" />
					</div>
					<div class="form-group">
						<!--                         <label class="control-label col-md-3 col-sm-3 col-xs-12">Buscar Por:</label> -->
						<div class="col-md-12 col-sm-12 col-xs-12">
							<label>Buscar Por:</label>
							<p>
								ID <input type="radio" class="flat" name="tipoB" id="BuscarID"
									value="1" /> Nombre <input type="radio" class="flat"
									name="tipoB" id="BuscarNombre" value="2" checked="" required />
								A. Paterno <input type="radio" class="flat" name="tipoB"
									id="BuscarApaterno" value="3" />
							</p>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	<div class="row">
<!-- 		<div class="col-md-12 col-sm-12 col-xs-12"> -->
			<div class="x_panel">
				<div class="x_title">
					<h2>Mapa</h2>
					<ul class="nav navbar-right panel_toolbox"></ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
					<div id="googleMap" class="col-md-12 col-sm-12 col-xs-12"
						style="height: 230px;"></div>
				</div>
			</div>
<!-- 		</div> -->
	</div>

</div>


<div class="col-md-8 col-sm-12 col-xs-12">
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">

				<div class="x_title">
					<h2 style="margin-right:20px;">Historial</h2>
<!-- 					<ul class="nav navbar-right panel_toolbox"> -->
					<form id="formHistorico" action="" class="form-inline" ">
						<div class="form-group">
<!-- 							<label class="control-label col-md-4 col-sm-4 col-xs-12" -->
<!-- 								for="parentesco">Parentesco <span class="required">*</span> -->
<!-- 							</label> -->
							<div class="col-md-4 col-sm-6 col-xs-5">
								<select id="mesSelect" class="form-control">
									<option value="1">ENERO</option>
									<option value="2">FEBRERO</option>
									<option value="3">MARZO</option>
									<option value="4">ABRIL</option>
									<option value="5">MAYO</option>
									<option value="6">JUNIO</option>
									<option value="7">JULIO</option>
									<option value="8">AGOSTO</option>
									<option value="9">SEPTIEMBRE</option>
									<option value="10">OCTUBRE</option>
									<option value="11">NOVIEMBRE</option>
									<option value="12">DICIEMBRE</option>
								</select>
							</div>
						</div>
						<div class="form-group">
<!-- 							<label class="control-label col-md-4 col-sm-4 col-xs-12" -->
<!-- 								for="parentesco">Parentesco <span class="required">*</span> -->
<!-- 							</label> -->
							<div class="col-md-4 col-sm-4 col-xs-4">
								<select id="anioSelect" class="form-control" ></select>
							</div>
						</div>
						<div class="form-group">
<!-- 							<label class="control-label col-md-4 col-sm-4 col-xs-12" -->
<!-- 								for="parentesco">Parentesco <span class="required">*</span> -->
<!-- 							</label> -->
							<div class="col-md-12 col-sm-3 col-xs-3">
								<button type="submit" class="btn btn-primary">Buscar</button>
							</div>
						</div>
					</form>
<!-- 					</ul> -->
<!-- 					<ul class="nav navbar-right panel_toolbox"> -->
						
<!-- 						<li> -->
<!-- 							<select> -->
<!-- 								<option>SEPTIEMBRE</option> -->
<!-- 								<option>DICIEMBRE</option> -->
<!-- 							</select> -->
<!-- 						</li> -->
<!-- 						<li> -->
<!-- 							<select> -->
<!-- 								<option>2015</option> -->
<!-- 								<option>2016</option> -->
<!-- 							</select> -->
<!-- 						</li> -->
<!-- 						<li> -->
<!-- 							<input value="BUSCAR" class="btn btn-sm btn-info"> -->
<!-- 						</li> -->
<!-- 					</ul> -->
					<div class="clearfix"></div>
				</div>
				<div class="x_content">

					<span>PACIENTE: </span> <span id="paxSel"
						style="font-weight: bold; font-size: 15px;"></span> <br> <span>ESTIMULADOR:
					</span> <span id="estimSel" style="font-weight: bold; font-size: 15px;"></span>
					<br> <span>VINCULADO?: </span> <span id="vinculado"
						style="font-weight: bold; font-size: 15px;"></span>
					<div class="ln_solid"></div>
					<table id="tablaHistorico" style="text-align: center;"
						class="table table-striped table-bordered dt-responsive nowrap"
						cellspacing="0" width="100%">
						<thead>
							<tr>
								<th>Inicio</th>
								<th>Termino</th>
								<th>Tiempo</th>
								<th>Frec</th>
								<th>Amp</th>
								<th>Onda</th>
								<th></th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>

</div>



<%-- <%@ include file="footer.jsp"%> --%>
<!-- Internos -->
<%-- <script src="${c}/resources/js/interno/medico.js"></script> --%>

<!-- jQuery UI -->
<%-- <script src="${c}/resources/js/jquery-ui.min.js"></script> --%>

</body>
</html>