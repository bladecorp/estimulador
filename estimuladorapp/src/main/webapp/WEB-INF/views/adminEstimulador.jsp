<%-- <%@ include file="header.jsp"%> --%>
<%@ page session="false" %>

<div class="col-md-7 col-sm-12 col-xs-12">
	<div class="x_panel">
		<div class="x_title">
			<h2>Lista de Estimuladores</h2>
			<div class="clearfix"></div>
		</div>
		<div class="x_content text-center">
			<table id="tablaEstimuladores"
				class="table table-striped table-bordered dt-responsive nowrap"
				cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>Serie</th>
						<th>Estado</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>


<div class="col-md-5 col-sm-12 col-xs-12">
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">

				<div class="x_title">
					<h2>Estimulador</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li>
							<div class="">
								<label> Liberar <input id="checkEstimEnabled" type="checkbox"
									class="js-switch" onchange="cambiarEstadoMedico(1)" />
								</label>
							</div>
						</li>
						<li style="margin-left: 20px;">Editar <input type="checkbox"
							id="checkEditarEstim" class="flat" />
						</li>
						<li style="margin-left: 10px;">Nuevo <input type="checkbox"
							id="checkNuevoEstim" class="flat" />
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">

					<form id="formEstim" action=""
						class="form-horizontal form-label-left">
						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12"
								for="serieEstim">Serie <span class="required">*</span>
							</label>
							<div class="col-md-9 col-sm-9 col-xs-12">
								<input type="text" id="serieEstim"
									class="form-control col-md-7 col-xs-12 edit">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-3 col-sm-3 col-xs-12"
								for="paxEstim">Vinculado 
							</label>
							<div class="col-md-9 col-sm-9 col-xs-12">
								<input type="text" id="paxEstim" disabled
									class="form-control col-md-7 col-xs-12">
							</div>
						</div>
						<div class="ln_solid"></div>
						<div class="form-group">
							<div class="col-md-11 col-sm-12 col-xs-12 col-md-offset-1">
								<button type="submit" class="btn btn-block btn-primary edit">Guardar</button>
							</div>
						</div>

					</form>

				</div>
			</div>
		</div>
	</div>
	
	
	<!-- FILA 2 COLUMNA DERECHA -->
	<div class="row">
		<div class="col-md-12 col-sm-12 col-xs-12">
			<div class="x_panel">
			<div class="x_title">
				<h2>Vincular</h2>
				<div class="clearfix"></div>
			</div>
			<div class="x_content text-center">
				<form id="formVincular" action="" class="form-horizontal form-label-left input-mask">
					<div class="form-group">
						<label class="control-label col-md-3 col-sm-3 col-xs-12"
							for="estimVinc">Estimulador
						</label>
						<div class="col-md-9 col-sm-9 col-xs-12">
							<input type="text" id="estimVinc" disabled
								class="form-control">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3 col-sm-3 col-xs-12"
							for="paxVinc">Paciente
						</label>
						<div class="col-md-9 col-sm-9 col-xs-12">
							<input type="text" id="paxVinc" disabled
								class="form-control">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-md-3 col-sm-3 col-xs-12"
							for="paxAutocomplete">Buscar
						</label>
						<div class="col-md-9 col-sm-9 col-xs-12">
							<input id="paxAutocomplete" type="text" class="form-control"/> 
						</div>
					</div>
					<div class="form-group">
						<!--                         <label class="control-label col-md-3 col-sm-3 col-xs-12">Buscar Por:</label> -->
						<div class="col-md-12 col-sm-12 col-xs-12">
							<label>Buscar Por:</label>
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
					<div class="ln_solid"></div>
					<div class="form-group">
						<div class="col-md-11 col-sm-12 col-xs-12 col-md-offset-1">
							<button id="btnVincular" type="submit" class="btn btn-block btn-primary">Vincular</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		</div>
	</div>
	
	
	

</div> <!-- .FIN COLUMNA DERECHA -->



<%-- <%@ include file="footer.jsp"%> --%>

<%-- <script src="${c}/resources/js/interno/admin_estimulador.js"></script> --%>

<!-- jQuery UI -->
<%--     <script src="${c}/resources/js/jquery-ui.min.js"></script> --%>

<!-- </body> -->
<!-- </html> -->