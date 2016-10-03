<%-- <%@ include file="header.jsp"%> --%>
<%@ page session="false" %>

<div class="col-md-7 col-sm-12 col-xs-12">
	<div class="x_panel">
		<div class="x_title">
			<h2>Lista de M&eacute;dicos</h2>
			<div class="clearfix"></div>
		</div>
		<div class="x_content text-center">
			<table id="tablaMedicos"
				class="table table-striped table-bordered dt-responsive nowrap"
				cellspacing="0" width="100%">
				<thead>
					<tr>
						<th>ID</th>
						<th>Nombre</th>
						<th>A. Paterno</th>
						<th>A. Materno</th>
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
					<h2>M&eacute;dico</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li>
							<div class="">
								<label> Estado <input id="checkEnabled" type="checkbox"
									class="js-switch" onchange="cambiarEstadoMedico(1)" />
								</label>
							</div>
						</li>
						<li style="margin-left: 20px;">Editar <input type="checkbox"
							id="checkEditarMedico" class="flat" />
						</li>
						<li style="margin-left: 10px;">Nuevo <input type="checkbox"
							id="checkNuevoMedico" class="flat" />
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">

					<form id="formMedico" action=""
						class="form-horizontal form-label-left">
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="nombreMed">Nombre <span class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="nombreMed"
									class="form-control col-md-7 col-xs-12 editar">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="apaternoMed">A. Paterno <span class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="apaternoMed"
									class="form-control col-md-7 col-xs-12 editar">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="amaternoMed">A. Materno <span class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="amaternoMed"
									class="form-control col-md-7 col-xs-12 editar">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="institucion">Instituci&oacute;n <span
								class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="institucion"
									class="form-control col-md-7 col-xs-12 editar">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="consultorio">Consultorio <span class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="number" id="consultorio"
									class="form-control col-md-7 col-xs-12 editar">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="especialidad">Especialidad<span class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="especialidad"
									class="form-control col-md-7 col-xs-12 editar">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="cedula">C&eacute;dula <span class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="cedula"
									class="form-control col-md-7 col-xs-12 editar">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="telCel">Tel. Celular </label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="telCel"
									class="form-control col-md-7 col-xs-12 ">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="telCasa">Tel. Casa </label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="telCasa"
									class="form-control col-md-7 col-xs-12 ">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="telOf">Tel. Oficina </label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="telOf"
									class="form-control col-md-7 col-xs-12 ">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="usuario">Usuario <span class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="usuario"
									class="form-control col-md-7 col-xs-12 editar">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="password">Password <span class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="password"
									class="form-control col-md-7 col-xs-12 editar">
							</div>
						</div>
						<div class="ln_solid"></div>
						<div class="form-group">
							<div class="col-md-11 col-sm-12 col-xs-12 col-md-offset-1">
								<button type="submit" class="btn btn-block btn-lg btn-primary">Guardar</button>
							</div>
						</div>

					</form>

				</div>
			</div>
		</div>
	</div>

</div>



<%-- <%@ include file="footer.jsp"%> --%>

<%-- <script src="${c}/resources/js/interno/admin_medico.js"></script> --%>

</body>
</html>