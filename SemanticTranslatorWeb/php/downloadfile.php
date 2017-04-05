<?php
	$result1 = '<?xml version="1.0" encoding="UTF-8"?>
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
	
	exit;
?>