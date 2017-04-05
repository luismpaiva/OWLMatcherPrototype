/**
 * 
 */

var automated = 0;
var manual = 1;

function hideManualSemanticTranslatorArea(){}

function getMessage(){}

function auto_manual_switch(selector){
	var speed = "slow";
	if (selector == 0) {
		$("#manualmode_id").hide(speed);
		$("#automatedmode_id").show(speed);
	} else {
		$("#manualmode_id").show(speed);
		$("#automatedmode_id").hide(speed);
	}
}

//function getXSD(type){	/* 
//	 * Type: 0 - provider
//	 * Type: 1 - consumer
//	 */
//}

var provider1time = 10000;
var provider2time = 5000;
var provider3time = 2500;

var provider1on = false;
var provider2on = false;
var provider3on = false;

var provider1 = function(){
	provider1on = true;
	var wifi_img = document.getElementById("wifi-img-provider1");
    if(wifi_img.style.display == 'none'){
    	$("#wifi-img-provider1").show();
//    	wifi_img.style.display = "inline"
    }else{
    	$("#wifi-img-provider1").hide();
//    	wifi_img.style.display = "none";
        getProviderMessage(1,providerscurrentemsg[0]);
        getConsumerTranslatedMessage(1,providerscurrentemsg[0]);
    }
}

var provider2 = function(){
	provider2on = true;
	var wifi_img = document.getElementById("wifi-img-provider2");
    if(wifi_img.style.display == 'none'){
    	$("#wifi-img-provider2").show();
//    	wifi_img.style.display = "inline"
    }else{
    	$("#wifi-img-provider2").hide();
//    	wifi_img.style.display = "none";
    }
    getProviderMessage(2,providerscurrentemsg[1]);
    getConsumerTranslatedMessage(2,providerscurrentemsg[1]);
    
}

var provider3 = function(){
	provider3on = true;
	var wifi_img = document.getElementById("wifi-img-provider3");
    if(wifi_img.style.display == 'none'){
    	$("#wifi-img-provider3").show();
//    	wifi_img.style.display = "inline"
    }else{
    	$("#wifi-img-provider3").hide();
//    	wifi_img.style.display = "none";
    }
    getProviderMessage(3,providerscurrentemsg[2]);
    getConsumerTranslatedMessage(3,providerscurrentemsg[2]);


}
window.onload = function(){
	auto_manual_switch(0);
//	auto_manual_switch(1);
	console.log("Getting XSD from provider");
	getXSDcontents("provider",1);

	console.log("Getting XSD from consumer");
	getXSDcontents("consumer",1);
	getXSLTcontents();
}

function act(provider_id) {
	switch(provider_id){
	case 1: if (provider1on) {
			stop_provider(1);
		} else {
			start_provider(1);
		} 
		break;
	case 2: if (provider2on) {
		stop_provider(2);
	} else {
		start_provider(2);
	} 
		break;
	case 3: if (provider3on) {
		stop_provider(3);
	} else {
		start_provider(3);
	} 
		break;
	}
	
}

function stop_provider(provider_id){
	var value = null;
	switch(provider_id){
		case 1: value = interval1;
			provider1on = false;
			break;
		case 2:value = interval2;
			provider2on = false;
			break;
		case 3:value = interval3;
			provider3on = false;
			break;
	}
	clearInterval(value);
}

function start_provider(provider_id){
	switch(provider_id){
		case 1: interval1 = window.setInterval(provider1, provider1time);;
			break;
		case 2: interval2 = window.setInterval(provider2, provider2time);
			break;
		case 3: interval3 = window.setInterval(provider3, provider3time);
			break;
	}
}

function stop_all_providers(){
	if (provider1on)
		stop_provider(1);
	if (provider2on)
		stop_provider(2);
	if (provider3on)
		stop_provider(3);

}

function start_all_providers(){
	if (!provider1on)
		start_provider(1);
	if (!provider2on)
		start_provider(2);
	if (!provider3on)
		start_provider(3);
}

//var interval1 = window.setInterval(provider1, provider1time);
//var interval2 = window.setInterval(provider2, provider2time);
//var interval3 = window.setInterval(provider3, provider3time);

var interval1 = null;
var interval2 = null;
var interval3 = null;

function translate_xml_msg_to_consumer() {
//	var x = document.getElementById("#consumer");
//	x.style.
	document.getElementById('consumer1_head_id').classList.toggle('panel-device-communicating');
	document.getElementById('consumer1_head_id').classList.toggle('panel-device-notcommunicating');
}

var restAddress = 'http://localhost:8080';
//var restAddress = 'http://localhost';

function getProviderMessage(provider_id, provider_msg){
//	console.log("getProviderMessage\n");
	var providerelem_id = "#provider_"+provider_id+"_id_body";
	$.ajax({
//		url: '/php/semanticengine.php',
		url: restAddress + '/SemanticTranslatorWeb/php/semanticengine.php',
		type: 'POST',
		data: {operation:1, provider_id:provider_id,provider_msg:providerscurrentemsg[provider_id-1]},
//		data: {provider_id:3,provider_msg:2},
		success: function(result) {
//			console.log(JSON.stringify(result));
//			console.log("\n\n");
//			console.log(result.length);
			
			if (result.toString().indexOf("xml") > -1) {
//				console.log(result);	
				console.log("received message ("+provider_msg+") from provider "+provider_id+"");
				$(providerelem_id).text(result);
				providerscurrentemsg[provider_id-1]++;
			} else {
				$(providerelem_id).text("NO MESSAGE");
			}
			console.log("Requesting message "+providerscurrentemsg[provider_id-1]+"from provider "+provider_id);
//			document.getElementById('provider_2_id_body').innerHTML = result;
			
//			$(providerelem_id).text(result);
//			provider_msg++;
//			providerscurrentemsg[provider_id]++;
		}
	});	
//	console.log(JSON.stringify(providerscurrentemsg));
}

var providerXSD = null;

function getXSLTcontents(){
	var xslt_id = "#xslt_id_panel_body";
	console.log(xslt_id);
	$.ajax({
		url: restAddress + '/SemanticTranslatorWeb/php/semanticengine.php',
		type: 'POST',
		data: {operation:2},
		success: function(result) {
			if (result.toString().indexOf("xsl:stylesheet") > -1) {
				console.log("received XSLT for consumer");
				$(xslt_id).text(result);
			} else {
				$(xslt_id).text("NO XSLT");
			}
			console.log("Requesting XSLT ");
		}
	});	
//	console.log(JSON.stringify(providerscurrentemsg));
}

function getConsumerTranslatedMessage(provider_id, provider_msg){
	var xslt_id = "#xslt_id_panel_body";
	var consumerelem_id = "#consumer_1_id_body";
	console.log(xslt_id);
	$.ajax({
		url: restAddress + '/SemanticTranslatorWeb/php/semanticengine.php',
		type: 'POST',
		data: {operation:3, provider_id:provider_id, provider_msg:provider_msg},
		success: function(result) {
			if (result.toString().indexOf("xml") > -1) {
//				console.log(result);	
				console.log("received translated message ("+provider_msg+") from provider "+provider_id+"");
				$(consumerelem_id).text(result);
//				providerscurrentemsg[provider_id-1]++;
			} else {
				$(consumerelem_id).text("NO MESSAGE");
			}
			console.log("Requesting message "+providerscurrentemsg[provider_id-1]+"from provider "+provider_id);
			console.log("Result for translated message: "+result);
		}
	});	
//	console.log(JSON.stringify(providerscurrentemsg));
}

function resetfilefolder(device_id){
	console.log("reset value - "+reset+" for device "+device_id+".");
	
	if(reset){
		$.ajax({
			url: restAddress + '/SemanticTranslatorWeb/php/semanticengine.php',
			type: 'POST',
			data: {operation:4, device_id:device_id},
			success: function(result) {
				console.log("Delete OK \n");
				console.log(result);
				switch (device_id) {
				case 0: providerscurrentemsg = [1,1,1];
					break;
				case 1: providerscurrentemsg[0] = 1;
					break;
				case 2: providerscurrentemsg[1] = 1;
					break;
				case 3: providerscurrentemsg[2] = 1;
					break;
				}
				
			}
		});	
		
	}
}

function getXSDcontents(device,device_id){
	var devicexsd_id = "#"+device+"xsd_id_panel_body";
	console.log(devicexsd_id);
	$.ajax({
		url: restAddress + '/SemanticTranslatorWeb/php/semanticengine.php',
		type: 'POST',
		data: {operation:5, device:device, device_id:device_id},
		success: function(result) {
			if (result.toString().indexOf("xs:schema") > -1) {
				console.log("received provider XSD from "+device+" "+device_id);
				$(devicexsd_id).text(result);
			} else {
				$(devicexsd_id).text("NO XSD");
			}
			console.log("Requesting xsd from "+"from "+device+" "+device_id)+":"+result;
		}
	});	
//	console.log(JSON.stringify(providerscurrentemsg));
}

var providerscurrentemsg = [1,1,1];

var reset = false;

function switch_reset(){
	for (var i=0;i<=3;i++){
		var classname = "disabled";
		switch_reset_button(i);
		switch (i) {
		case 0: switch_reset_button(i,"active");
			break;
		default: break;
		}
		switch_reset_button(i,classname);
	}

	if (reset) {
		reset = false;
		document.getElementById('reset_on_id').classList.toggle('active');
	} else {
		reset = true;
		document.getElementById('reset_on_id').classList.toggle('active');
	}
}

function switch_reset_button(id, classname){
	var idname = "reset_p"+id+"_id";
	document.getElementById(idname).classList.toggle(classname);
}

