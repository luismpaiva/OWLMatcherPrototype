<?php
require_once 'requires.php';

$consumerstr = "consumer";
$providerstr = "provider";
$menu = new Menu();

?>
<head>
	<title>Semantic Translator</title>
	<link rel="shortcut icon" href="../images/favicon.ico" type="image/x-icon">
	<link rel="icon" href="../images/favicon.ico" type="image/x-icon">	
	<link rel="stylesheet" type="text/css" href="../lib/bootstrap-3.3.6/css/bootstrap.min.css">
 	<link rel="stylesheet" type="text/css" href="../css/main.css"> 
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<script src="../lib/bootstrap-3.3.6/js/bootstrap.min.js"></script>
	<script src="../js/automation.js"></script>
</head>
<body>
<?php
echo $menu->getMenuHtml();
?>

	<div id="manualmode_id" style="margin: 0 0">
		<div style="text-align: center; margin: 0;"><p style="padding: 0; margin: 1% 0;">Semantic Translator</p></div>
		<div style="background-color: #f5f5f5; margin: 0; padding:0;">
			<form id="loginform" class="form-inline" name="providerxsd" action="../php/javaexec.php" method="post" accept-charset="utf-8" enctype="multipart/form-data">
			<div id="inputarea" style="width:65%; margin:auto; padding:1% 0">
				<div id="<?php echo $providerstr;?>_box" style="display:inline-block; padding: 0 2%; width:49.5%;">
					<textarea name="<?php echo $providerstr;?>_area" class="form-control" rows="4" cols="50" style="width:100%" placeholder="Select <?php echo $providerstr ?> XSD below!"></textarea>	<br/>
				    <!--  <input type="file" class="form-control " style="width:100%"  name="password" placeholder="&lt;choose XSD file for provider&gt;" /><br/>  -->
					<!--  <button type="submit" class="btn btn-info " style="width:100%" ><?php // echo ucwords($providerstr) ?> XSD</button>  -->
				    <label class="btn btn-info btn-file" style="width:100%" >
				        <?php echo ucwords($providerstr) ?> XSD 
				        <input name="<?php echo $providerstr;?>file" type="file" style="width:100% /*display: none;*/">
				    </label>
				</div>
				<div id="<?php echo $consumerstr;?>_box" style="display:inline-block; padding: 0 2%; width:49.5%;">
				  	<textarea name="<?php echo $consumerstr;?>_area" class="form-control" rows="4" cols="50" style="width:100%" placeholder="Select <?php echo $consumerstr?> XSD below!"></textarea>	<br/>
				    <!-- <input type="file" class="form-control" style="width:100%"  name="password" placeholder="&lt;choose XSD file for <?php // echo $consumerstr?>&gt;" /><br/>  -->
				    <label class="btn btn-info btn-file" style="width:100%" >
				        <?php echo ucwords($consumerstr) ?> XSD 
				        <input name="<?php echo $consumerstr;?>file" type="file" style="width:100% /*display: none;*/">
				    </label>
				</div>
				
				<div style="padding: 1% 0 0 0;">
					<button type="submit" class="btn btn-info" style="margin: 0 0 0 2%; width:95.5%" align="center"><?php echo 'Translate';	?></button>
				</div>
					
				</div>
			</form>
		</div>
	</div>
	<div id="automatedmode_id" class="text-center" style="display:none; height:auto;">
		<div class="panel-group">
			<div id="row1" class="row">
			    <div id="row1-col1" class="col-md-3">
					<div class="panel panel-default">
					  <div class="panel-heading">Controls</div>
					  <div class="panel-body">
					  	<div id="row1-col1-row1" class="row">
					    		<button id ="start_all_id" type="button" onclick="start_all_providers()" class="btn btn-success">START</button>
					    		<button id ="stop_all_id" type="button" onclick="stop_all_providers()" class="btn btn-danger">STOP</button>
					    		<button id ="get_msg_id" type="button" onclick="getProviderMessage(2,10)" class="btn btn-info">MESSAGE</button>
						</div>
					  	<div id="row1-col1-row2" class="row">
				    			<button id ="reset_on_id" type="button" onclick="switch_reset()" class="btn btn-xs btn-danger">RESET</button>
					    		<div class="btn-group btn-group-md">
					    			<button id ="reset_p0_id" type="button" onclick="resetfilefolder(0);" class="btn btn-xs btn-danger disabled">ALL</button>
						    		<button id ="reset_p1_id" type="button" onclick="resetfilefolder(1);" class="btn btn-xs btn-danger disabled">P1</button>
						    		<button id ="reset_p2_id" type="button" onclick="resetfilefolder(2);" class="btn btn-xs btn-danger disabled">P2</button>
						    		<button id ="reset_p3_id" type="button" onclick="resetfilefolder(3);" class="btn btn-xs btn-danger disabled">P3</button>
					    		</div>
						</div>
					  </div>
					</div>
			    </div>
			    <div id="row1-col2" class="col-md-6">
				    <div id="row1-col2-row1" class="row">
				    	<div id="row1-col2-row1-col1" class="col-md-6">
							<div id="providerxsd_id_panel" class="panel panel-default">
							  <div class="panel-heading">Provider XSD</div>
							  <div id="providerxsd_id_panel_body" class="panel-body panel-device-content">Panel Content</div>
							</div>
				    	</div>
				    	<div id="row1-col2-row1-col2" class="col-md-6">
							<div id="consumerxsd_id_panel" class="panel panel-default">
							  <div class="panel-heading">Consumer XSD</div>
							  <div id="consumerxsd_id_panel_body" class="panel-body panel-device-content">Panel Content</div>
							</div>
				    	</div>
					</div>
				    <div id="row1-col2-row2" class="row">
				    	<div id="row1-col2-row2-col1" class="col-md-3"></div>
				    	<div id="row1-col2-row2-col2" class="col-md-6">
							<div class="panel panel-default panel-semantic_translator-head" onclick="translate_xml_msg_to_consumer();">
							  <div class="panel-heading panel-semantic-translator">Semantic Translator</div>
<!-- 							  <div class="panel-body">Panel Content</div> -->
							</div>
						</div>
				    	<div id="row1-col2-row2-col3" class="col-md-3"></div>
					</div>
			    </div>
			    <div id="row1-col3" class="col-md-3">
					<div id="xslt_id_panel" class="panel panel-default">
					  <div class="panel-heading">XSL Translator</div>
					  <div id="xslt_id_panel_body" class="panel-body panel-device-content">Panel Content</div>
					</div>
			    </div>
			</div>
			<div id="row2" class="row">
			    <div id="row2-col1" class="col-md-3">
				    <div id="row2-col1-row1" class="row">
				    	<div id="row2-col1-row1-col1" class="col-md-2"></div>
				    	<div id="row2-col1-row1-col2" class="col-md-8">
							<div onclick="act(1);" class="panel panel-primary panel-device-dimensions">
							  <div class="panel-heading">Provider 1</div>
							  <div id="provider_1_id_body" class="panel-body panel-body-device">Panel Content</div>
							</div>
				    	</div>
				    	<div id="row2-col1-row1-col3" class="col-md-2">
				    		<img id="wifi-img-provider1" class="wifi-image" alt="" src="../images/signal-256x256-LtoW.png" width="50" height="50">
				    	</div>
				    </div>
				    <div id="row2-col1-row2" class="row">
				    	<div id="row2-col1-row2-col1" class="col-md-2"></div>
				    	<div id="row2-col1-row2-col2" class="col-md-8">
							<div onclick="act(2);" class="panel panel-primary panel-device-dimensions">
							  <div class="panel-heading">Provider 2</div>
							  <div id="provider_2_id_body" class="panel-body panel-body-device">Panel Content</div>
							</div>
				    	</div>
				    	<div id="row2-col1-row2-col3" class="col-md-2">
				    		<img id="wifi-img-provider2" class="wifi-image" alt="" src="../images/signal-256x256-LtoW.png" width="50" height="50">
				    	</div>
				    </div>
				    <div id="row2-col1-row3" class="row">
				    	<div id="row2-col1-row2-col1" class="col-md-2"></div>
				    	<div id="row2-col1-row2-col2" class="col-md-8">
							<div onclick="act(3);" class="panel panel-primary panel-device-dimensions">
							  <div class="panel-heading">Provider 3</div>
							  <div id="provider_3_id_body" class="panel-body panel-body-device">Panel Content</div>
							</div>
				    	</div>
				    	<div id="row2-col1-row2-col3" class="col-md-2">
				    		<img id="wifi-img-provider3" class="wifi-image" alt="" src="../images/signal-256x256-LtoW.png" width="50" height="50">
				    	</div>
				    </div>
			    </div>
			    <div id="row2-col2" class="col-md-6">
<!-- 					<div class="panel panel-danger"> -->
<!-- 					  <div class="panel-heading">Panel Heading</div> -->
<!-- 					  <div class="panel-body /*panel-central-area-dimensions*/">Panel Content</div> -->
<!-- 					</div> -->
			    </div>
			    <div id="row2-col3" class="col-md-3">
				    <div id="row2-col3-row1" class="row">
				    	<div id="row2-col3-row1-col1" class="col-md-2"></div>
				    	<div id="row2-col3-row1-col2" class="col-md-8">
							<div class="panel panel-danger panel-device-dimensions">
							  <div id="consumer1_head_id" class="panel-heading panel-device-notcommunicating">Consumer</div>
							  <div id="consumer_1_id_body" class="panel-body">Panel Content</div>
							</div>
				    	</div>
				    	<div id="row2-col3-row1-col3" class="col-md-2">
				    	</div>
				    </div>
				    <div id="row3-col1" class="col-md-12">
				    </div>
			    </div>
			 </div>
		 </div>
  	</div>
	
	
</body>
</html>
	

