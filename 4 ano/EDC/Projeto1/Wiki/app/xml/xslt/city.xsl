<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <h3 style="color: black ">City:
            <xsl:value-of select="city/name"/>
        </h3>
        <xsl:if test="city/name != ''">
            <p><b>Name: </b> <xsl:value-of select="city/name"/></p>
        </xsl:if>
        <xsl:if test="city/latitude != ''">
            <p><b>Latitude: </b> <xsl:value-of select="city/latitude"/></p>
        </xsl:if>
        <xsl:if test="city/longitude != ''">
            <p><b>Longitude: </b> <xsl:value-of select="city/longitude"/></p>
        </xsl:if>
        <xsl:if test="city/elevation != ''">
            <p><b>Elevation: </b> <xsl:value-of select="city/elevation"/></p>
        </xsl:if>
        <xsl:if test="city/population != ''">
            <p><b>Population: </b></p>
            <p></p>
            <xsl:for-each select="city//population">
                <p style="padding-left: 40px" ><b><xsl:value-of select="@year"/>: </b><xsl:value-of select="text()"/> </p>
            </xsl:for-each>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>