<?php
	$filename = '';
	
// 	if (file_exists($filename)) {
// 		header('Content-Description: File Transfer');
// 		header('Content-Type: application/octet-stream');
// 		header('Content-Disposition: attachment; filename="'.basename($filename).'"');
// 		header('Expires: 0');
// 		header('Cache-Control: must-revalidate');
// 		header('Pragma: public');
// 		header('Content-Length: ' . filesize($filename));
// 		readfile($filename);
// 		exit;
// 	}	
	
// echo '</br>';
// print_r($_POST);
// echo '</br>';	
	
	$sampleid = $_POST['xsdsample'];
	
	switch ($sampleid) {
		case "providerxsd": $filename = 'provider.xsd';
								
			break;
		case "consumerxsd": $filename = 'consumer.xsd';
			break;
		default: break;
	}
	$filecontents = file_get_contents($filename);
	
	header("Content-Type: text/xml");
	header('Content-Disposition: attachment; filename="'.$filename.'"');
	header("Content-Length: " . strlen($filecontents));
	echo $filecontents;
	
	exit;
?>