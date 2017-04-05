<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:variable name="consumerValueUnits">
		<xsl:call-template name="getConsumerValueUnits">
			<xsl:with-param name="providerValueUnits" select = "/values/units" />
		</xsl:call-template>
	</xsl:variable>

	<xsl:template match="/">
		<data>
			<sensortemp>        <xsl:value-of select="/values/indoorTemp" />       </sensortemp>

			<units>        <xsl:value-of select="$consumerValueUnits" />      </units>
		</data>
	</xsl:template>	

	<xsl:template name = "getConsumerValueUnits" >
		<xsl:param name = "providerValueUnits" />
		<xsl:choose>
			<xsl:when test="$providerValueUnits='Cel'">C</xsl:when>
			<xsl:when test="$providerValueUnits='Fah'">F</xsl:when>
			<xsl:otherwise />
		</xsl:choose>
	</xsl:template>  

</xsl:stylesheet>


