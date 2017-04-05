<?php
// 	class SemanticMessage {
		
// 		function getMessage(){

// 		}
		
// 	}
	$operation = $_POST['operation'];
	$filelocation = '/var/www/SemanticTranslatorWeb/msgfiles/';
// 	$filelocation = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\SemanticTranslatorWeb\\msgfiles\\";
	
	switch ($operation) {
		// normal functioning
		case 1: $provider_id = $_POST['provider_id'];
    			$provider_msg = $_POST['provider_msg'];
//     			$filelocation = '/var/www/SemanticTranslatorWeb/msgfiles/';
//     			$filelocation = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\SemanticTranslatorWeb\\msgfiles\\";
    			$filename = $filelocation.'msg_id'.$provider_id.'_c'.$provider_msg.'.msg';
    			echo file_get_contents($filename);
				break;
		//get xslt
		case 2: 
				$filelocation = '/var/www/SemanticTranslatorWeb/php/';
// 				$filelocation = 'C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\SemanticTranslatorWeb\\php\\';
				$filename = $filelocation.'targetXSLT.xslt';
    			echo file_get_contents($filename);
				break;
		//get translated message
		case 3: $provider_id = $_POST['provider_id'];
    			$provider_msg = $_POST['provider_msg'];
//     			$filelocation = '/var/www/SemanticTranslatorWeb/msgfiles/';
//     			$filelocation = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\SemanticTranslatorWeb\\msgfiles\\";
				
    			$filename = $filelocation.'msg_id'.$provider_id.'_c'.$provider_msg.'.msg';
    			$translatedfilename = $filelocation.'translated_msg_id'.$provider_id.'_c'.$provider_msg.'.msg';
    			
    			exec("java -cp ../java/SemanticTranslatorWebApp.jar;../java/*.jar uninova.cts.arrowhead.owlmatcher.web.SemanticTranslatorWebApp 
    					4 "
    					.$filelocation."/".$filename." "
    					.$filelocation."/".$translatedfilename, 
    						$output);
     			foreach ($output as $outline) {
     				echo $outline;
     			}
// 				echo $output;
    			echo file_get_contents($translatedfilename);
    			break;
		// reset 
		case 4:
				$device_id = $_POST['device_id'];
	 			echo "Device id received is: ".$device_id;
// 	 			$filelocation = '/var/www/SemanticTranslatorWeb/msgfiles/';
// 	 			$filelocation = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\SemanticTranslatorWeb\\msgfiles\\";
	 			if ($device_id == 0) {
// 				$filelocation = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\SemanticTranslatorWeb\\msgfilestest\\";
				$files = glob($filelocation.'/*'); // get all file names
				foreach($files as $file){ // iterate files
					if(is_file($file))
						unlink($file); // delete file
				}
				echo "Deleted all files (php)";
			} else {
				$device_id_str = '_id'.$device_id.'_';
				$files = glob($filelocation.'/*'); // get all file names
				foreach($files as $file){ // iterate files
					if( (is_file($file)) && strpos($file, $device_id_str) !== false)
						unlink($file); // delete file
				}
				
			}
			break;
		//get xsds (provider/consumer)
		case 5: $device = $_POST['device'];
				$filelocation = '/var/www/SemanticTranslatorWeb/php/';
// 				$filelocation = 'C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\SemanticTranslatorWeb\\php\\';
				$filename = $filelocation.$device.'.xsd';
    			echo file_get_contents($filename);
				break;
		default:break;
	}
?>