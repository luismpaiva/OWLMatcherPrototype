<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:variable name="consumerValueUnits">
		<xsl:call-template name="getConsumerValueUnits">
			<xsl:with-param name="providerValueUnits" select = "/datavalues/units" />
		</xsl:call-template>
	</xsl:variable>
  
	<xsl:variable name="consumerValueUnits2">
		<xsl:call-template name="getConsumerValueUnits">
			<xsl:with-param name="providerValueUnits" select = "/datavalues/units2" />
		</xsl:call-template>
	</xsl:variable> 
	
	<xsl:template match="/">
		<datavalues>
			<sensor1temp>        <xsl:value-of select="/datavalues/indoorTemp" />       </sensor1temp>
			<sensor1temp>        <xsl:value-of select="/datavalues/outdoorTemp" />      </sensor1temp>
			<sensor2temp>        <xsl:value-of select="/datavalues/indoorTemp" />      </sensor2temp>
			<sensor2temp>        <xsl:value-of select="/datavalues/outdoorTemp" />      </sensor2temp>
	  
			<units>        <xsl:value-of select="$consumerValueUnits" />      </units>
			<units>        <xsl:value-of select="$consumerValueUnits2" />      </units>
		</datavalues>
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


