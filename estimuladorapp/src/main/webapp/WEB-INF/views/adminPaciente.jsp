<%-- <%@ include file="header.jsp"%> --%>
<%@ page session="false" %>

<div class="col-md-7 col-sm-12 col-xs-12">
	<div class="x_panel">
		<div class="x_title">
			<h2>Lista de Pacientes</h2>
			<div class="clearfix"></div>
		</div>
		<div class="x_content text-center">
			<table id="tablaPacientes"
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
					<h2>Paciente</h2>
					<ul class="nav navbar-right panel_toolbox">
						<li>
							<div class="">
								<label> Estado <input id="checkEnabledPax" type="checkbox"
									class="js-switch"  />
								</label>
							</div>
						</li>
						<li style="margin-left: 20px;">Editar <input type="checkbox"
							id="checkEditarPax" class="flat" />
						</li>
						<li style="margin-left: 10px;">Nuevo <input type="checkbox"
							id="checkNuevoPax" class="flat" />
						</li>
					</ul>
					<div class="clearfix"></div>
				</div>
				<div class="x_content">
				
					<form action="" id="formVinc" class="form-horizontal ">
						<div class="form-group col-md-7 col-sm-7 col-xs-12">
							<label class="control-label col-md-12 col-sm-12 col-xs-12" style="text-align: center;"
								for="estimPax">Estimulador
							</label> <br>
							<div class="col-md-12 col-sm-12 col-xs-12">
								<input type="text" id="estimPax"  style="text-align: center;"
									class="form-control col-md-7 col-xs-12 " disabled>
							</div>
						</div>
						<div class="form-group col-md-5 col-sm-5 col-xs-12">
							<label class="control-label col-md-12 col-sm-12 col-xs-12" style="text-align: center;"
								for="vincPax">Vinculado
							</label> <br>
							<div class="col-md-12 col-sm-12 col-xs-12">
								<input type="text" id="vincPax"  style="text-align: center;"
									class="form-control col-md-12 col-sm-12 col-xs-12 ">
							</div>
						</div>
					</form>

					<form id="formPax" action=""
						class="form-horizontal form-label-left">
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="nombrePax">Nombre <span class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="nombrePax"
									class="form-control col-md-7 col-xs-12 editar">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="apaternoPax">A. Paterno <span class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="apaternoPax"
									class="form-control col-md-7 col-xs-12 editar">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="amaternoPax">A. Materno <span class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="amaternoPax"
									class="form-control col-md-7 col-xs-12 editar">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="direccion">Direcci&oacute;n <span
								class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="direccion"
									class="form-control col-md-7 col-xs-12 editar">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="email">Email <span class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="email" id="email"
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
						<div class="form-group ">
							<label style="text-align: left;" class="control-label col-md-12 col-sm-12 col-xs-12 ">
								RESPONSABLE
							</label>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="nombreResp">Nombre <span class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="nombreResp"
									class="form-control col-md-7 col-xs-12 opcional">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="apaternoResp">A. Paterno <span class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="apaternoResp"
									class="form-control col-md-7 col-xs-12 opcional">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="amaternoResp">A. Materno <span class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<input type="text" id="amaternoResp"
									class="form-control col-md-7 col-xs-12 opcional">
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-md-4 col-sm-4 col-xs-12"
								for="parentesco">Parentesco <span class="required">*</span>
							</label>
							<div class="col-md-8 col-sm-8 col-xs-12">
								<select id="parentesco"
									class="form-control col-md-7 col-xs-12"></select>
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

<%-- <script src="${c}/resources/js/interno/admin_paciente.js"></script> --%>

</body>
</html>