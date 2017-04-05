<?php

class Menu{
	var $numbuttons = 0;
	var $buttons = [];
	
	function Menu(){}
	
	function getMenuHtml(){
		return '<div class="panel panel-default">
	  <div class="panel-heading">
		<div class="row">
			<div class="col-md-4">
				<div class="btn-group">
				  <button id ="newtranslator_id" type="button" onclick="document.location=\'home.php\'" class="btn btn-primary">New Translator</button>
				  <button id ="downloadxslt_id" onclick="document.location=\'downloadfile.php\'" class="btn btn-default disabled">Download XSLT</button>
				  <button id ="translatexml_id" type="button" class="btn btn-default disabled">Translate XML</button>
				</div>
			</div>
			<div class="col-md-4 text-center">
				<div class="btn-group">
					<button id ="automated_id" type="button" onclick="auto_manual_switch(0);" class="btn btn-danger">AUTO</button>	
					<button id ="manual_id" type="button" onclick="auto_manual_switch(1);" class="btn btn-success">MANUAL</button>	
				</div>
			</div>
			<div class="col-md-4">
				<form action="downloadsample.php" method="post">
					<div class="btn-group pull-right">
						<button id ="providerxsd_id" name="xsdsample" value="providerxsd" class="btn btn-default">Provider XSD</button>
				  		<button id ="consumerxsd_id" name="xsdsample" value="consumerxsd" class="btn btn-default">Consumer XSD</button>
					</div>
				</form>
			</div>
		  </div>
		</div>
  	</div>';
	}
}

class Button{
	var $idname;
	var $name;
	var $value;
	var $title = "Provider XSD";
	var $class_def = "btn btn-default disabled";
	var $onclickevent = "document.location=\'downloadfile.php\'";
	
	function Button(){

	}
	
	function getButtonHtml(){
		return '<button '.$idname.'onclick=\"'.$onclickevent.'\" class="'.$class_def.'">'.$title.'</button>';
	}
}
?>
