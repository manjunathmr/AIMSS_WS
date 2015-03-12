<!DOCTYPE html>
<html lang="en">
<head>
<title>Rd</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.css" rel="stylesheet" media="screen">

<link href="css/font-awesome.min.css" rel="stylesheet">
<link href="css/dashboard.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.10.0.custom.css" media="screen" />
<link rel="stylesheet" type="text/css" href="css/marker.css" media="screen" />

</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<!--   <div class="row-fluid">
          	<div class="span12">
              <div class="text-center text-success"><h2 style="color:green;">Rd Efficiency Ratio(AER) <span title="AER=(Number of fields extracted by system )/(Total number of fields needed by application)" style="color:black;"><sup><i class="icon-info"></i></sup></span><span>:</span><span class="latestAer"></h2></span></div>
            </div><!--/span-->
				<!--> </div><!--/row-->
				<div class="row-fluid">
					<div class="span12">
						<div class="aerGraphWrapperClass"
							style="min-width: 200px; height: 400px; margin: 0 auto"></div>
						<!--/span-->
					</div>
					<!--/row-->
					<div class="row-fluid">
						<div class="span6">
							<div class="text-center">
								<h3>Data Mapper</h3>
							</div>
						</div>
						<!--/span-->
						<div class="span6">
							<div class="text-center">
								<h3>AER Table</h3>
							</div>
						</div>
						<!--/span-->
					</div>
					<!--/row-->
					<div class="row-fluid">
						<div class="span6 confidenceTableWrapperClass">
							<table class="confidenceTable table table-striped table-bordered"></table>
						</div>
						<!--/span-->
						<div class="span6 aerTableWrapperClass">
							<table class="aerTable table table-striped table-bordered"></table>
						</div>
						
						<!--/span-->
					</div>
					<!--/row-->
					<div class="row-fluid">
						<div class="span6">
							<div class="text-center">
								<h3>Base Intelligence</h3>
							</div>
						</div>
						<!--/span-->
					</div>
					<!-- /row -->
					<div class="row-fluid">
						<div class="span6 miTableWrapperClass">
							<table class="miTable table table-striped table-bordered"></table>
						</div>
						<!--/span-->
					</div>
					<!-- row -->
				</div>
				<!--/span-->
			</div>
			<!--/row-->
		</div>
		<!--/.fluid-container-->
	</div>

		<!-- Modal -->
		<div id="tbd" class="modal hide fade" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">x</button>
				<h3 id="myModalLabel">To be implemented</h3>
			</div>
			<div class="modal-body">
				<p>This functionality is coming soon...</p>
			</div>
			<div class="modal-footer">
				<button class="btn" data-dismiss="modal" aria-hidden="true">Ok</button>
			</div>
		</div>

		<!-- Modal -->
		<div id="resetDBModal" class="modal hide fade" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">x</button>
				<h3 id="myModalLabel">Confirm</h3>
			</div>
			<div class="modal-body">
				<p>Are you sure you wish to reset the db?</p>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary resetDBConfirm">Reset</button>
				<button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
			</div>
		</div>
		
		<!-- Modal -->
		<div id="adminModal" class="modal hide fade" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">x</button>
				<h3 id="adminModalHeaderLabel">Import</h3>
			</div>
			<div class="modal-body">
				<p>You need admin privileges to <span id="adminModalBodySpan"></span>.</p>
			</div>
			<div class="modal-footer">
				<button class="btn" data-dismiss="modal" aria-hidden="true">Ok</button>
			</div>
		</div>

		<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="http://code.jquery.com/ui/1.10.0/jquery-ui.js"></script>
		<script type="text/javascript" src="js/jquery.tools.min.js"></script>
		<script type="text/javascript" src="js/jquery.dd.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="http://code.highcharts.com/highcharts.js"></script>
		<script src="http://code.highcharts.com/modules/exporting.js"></script>
		<script src="js/underscore-min.js"></script>
</body>
</html>