<?php 
	require_once 'menu.php';
	$menu = new Menu();
	// declaration/initialization zone
	$xsdfilesfolder = "../xsdfiles";

	
	// reading provider and consumer contents from its respective text areas
	$providertextareacontents = $_POST['provider_area'];
	$consumertextareacontents = $_POST['consumer_area'];
	
	// receiving provider file from user
	$providerfilecontents = $_FILES['providerfile'];
	$providerfilecontentsname = $_FILES['providerfile']['name'];
	$providerfilecontentstmpname = $_FILES['providerfile']['tmp_name'];
	
	// receiving consumer file from user
	$consumerfilecontents = $_FILES['consumerfile'];
	$consumerfilecontentsname = $_FILES['consumerfile']['name'];
	$consumerfilecontentstmpname = $_FILES['consumerfile']['tmp_name'];
	// 	$consumerfilecontents = $_FILES['consumerfile'];


	// get contents from file in tmp folder, and copy to local folder
	$consumercurrentcontents = file_get_contents($consumerfilecontentstmpname);
	$providercurrentcontents = file_get_contents($providerfilecontentstmpname);
	file_put_contents("../xsdfiles/consumerfilecontents.txt", $consumercurrentcontents);
	file_put_contents("../xsdfiles/providerfilecontents.txt", $providercurrentcontents);
	
	file_put_contents("../xsdfiles/providertextareacontents.txt", $providertextareacontents);
	file_put_contents("../xsdfiles/consumertextareacontents.txt", $consumertextareacontents);
	
	// execute java component
 	exec("java -cp ../java/SemanticTranslatorWebApp.jar;../java/*.jar uninova.cts.arrowhead.owlmatcher.web.SemanticTranslatorWebApp 4 ".$xsdfilesfolder."/"."providerfilecontents.txt ".$xsdfilesfolder."/"."consumerfilecontents.txt", $output);
	
// 	exec("java -cp ../java/SemanticTranslatorWebApp1.jar;../java/*.jar uninova.cts.arrowhead.owlmatcher.web.SemanticTranslatorWebApp ".$xsdfilesfolder."/"."providerfilecontents.txt ".$xsdfilesfolder."/"."consumerfilecontents.txt", $output);
	
?>
<html>
<head>
	<title>Semantic Translator</title>
	<link rel="shortcut icon" href="../images/favicon.ico" type="image/x-icon">
	<link rel="icon" href="../images/favicon.ico" type="image/x-icon">	
	<link rel="stylesheet" type="text/css" href="../lib/bootstrap-3.3.6/css/bootstrap.min.css">
 	<link rel="stylesheet" type="text/css" href="../css/main.css"> 
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<script src="../lib/bootstrap-3.3.6/js/bootstrap.min.js"></script>
</head>
<body>
	  <?php echo  $menu->getMenuHtml();
	  ?>
	  
	<?php
		echo '<pre>';
		echo 'Working folder: ';
		echo getcwd();
		echo '</pre>';
		echo '------------ SEMANTIC ENGINE ANSWER ------------';
		echo '</br>';
	//	print_r($output);
		echo "<pre>";
		
		foreach ($output  as $ouputline) {
			echo htmlspecialchars($ouputline);
			echo '</br>';
		}
		echo "</pre>";
		echo '---------- END SEMANTIC ENGINE ANSWER ----------';
		echo '</br>';
	?>
	  </div>
	</div>
</body>
</html>

<?php
/*	$result1 = '<?xml version="1.0" encoding="UTF-8"?>
				<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
				  <xsl:template match="/">
				    <data sensorTempAtt="providerValuetoAtt">
				      <sensorTemp>
				        <xsl:value-of select="/values/indoorTemp" />
				      </sensorTemp>
				      <units>
				        <xsl:value-of select="/values/units" />
				      </units>
				    </data>
				  </xsl:template>
				</xsl:stylesheet>';
	$filename = 'result_file.xml';
	
	header("Content-Type: text/xml");
	header('Content-Disposition: attachment; filename="'.$filename.'"');
	header("Content-Length: " . strlen($result1));
	echo $result1;
	
	exit;*/
?>

<?php 

/*	echo '</br>';
 echo '</br>';
 print_r($_POST);
 echo '</br>';
 echo '</br>';
 print_r($_FILES);
 echo '</br>';
 echo '</br>';
 */
/*
 // printing contents of provider/consumer file variables
 echo '</br> $providerfilecontents </br>';
 print_r($providerfilecontents);
 echo $providerfilecontentsname;
 echo '</br> $providerfilecontentsname </br>';
 echo $providerfilecontentstmpname;

 echo '</br> $consumerfilecontents </br>';
 print_r($consumerfilecontents);
 echo $consumerfilecontentsname;
 echo '</br> $consumerfilecontentsname </br>';
 echo $consumerfilecontentstmpname;
 */
// 	echo $consumerfilecontents;
// 	print_r($_FILES);
/*	echo '</br>';
 echo '------------ TEXT AREA CONTENTS ------------';
 echo '</br>';
 echo "<pre>";
 echo htmlspecialchars($providertextareacontents);
 echo "</pre>";
 echo '</br>';
 echo "<pre>";
 echo htmlspecialchars($consumertextareacontents);
 echo "</pre>";

 echo '---------- END TEXT AREA CONTENTS ----------';
 echo '</br>';
 */
// 	exec("java -jar ../java/HelloJavaPHP.jar ../xsdfiles/consumerfilecontents.txt ../xsdfiles/consumertextareacontents.txt", $output);
// 	exec("java -jar ../java/SemanticTranslatorWebApp.jar ../xsdfiles/consumerfilecontents.txt ../xsdfiles/consumertextareacontents.txt", $output);
/*
 echo '</br>';
 echo getcwd();
 echo '</br>';
 */
/*	echo '</br>';
 echo '------------ FILE CONTENTS B ------------';
 echo "<pre>";
 // 	echo htmlspecialchars($output[3]);
 echo "</pre>";
 // $xmlcontent = simplexml_load_string($output[3]);
 echo '---------- END FILE CONTENTS B ----------';
 echo '</br>';
 echo (string) $xmlcontent;
 print_r($output);
 echo '</br>';
 echo $output[0];
 echo '</br>';
 echo $output[1];
 echo '</br>';
 var_dump($output);*/
/*
 echo '</br>';
 echo '------------ WHO AM I ------------';
 exec("whoami", $result);
 print_r($result);
 echo '------------ END OF WHO AM I ------------';
 echo '</br>';
 echo '</br>';
 */

// 	echo '------------ SENDING FILE TESTS ------------';
// 	echo '</br>';

// 	$result1 = 'Generated string that is different every time. 20121107160322';
// 	$filename = 'result_file.txt';

// 	header("Content-Type: text/plain");
// 	header('Content-Disposition: attachment; filename="'.$filename.'"');
// 	header("Content-Length: " . strlen($result1));
// 	echo $result1;

// 	exit;
// 	echo '</br>';

// 	echo '------------ SENDING FILE TESTS ------------';
// 	echo '</br>';


?>