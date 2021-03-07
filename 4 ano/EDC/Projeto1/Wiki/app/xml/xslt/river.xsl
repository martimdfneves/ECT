<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <h3 style="color: black ">River:
            <xsl:value-of select="river/name"/>
        </h3>

        <p><b>Flows to: </b> <xsl:value-of select="river/to"/></p>
        <xsl:if test="river/area">
            <p><b>Area: </b> <xsl:value-of select="river/area"/></p>
        </xsl:if>
        <xsl:if test="river/length">
            <p><b>Length: </b> <xsl:value-of select="river/length"/></p>
        </xsl:if>

        <p><b>Source: </b> <xsl:value-of select="river/source/located"/></p>

        <p style="padding-left: 40px">Latitude: <xsl:value-of select="river/source/latitude"/> </p>
        <p style="padding-left: 40px">Longitude: <xsl:value-of select="river/source/longitude"/> </p>
        <p style="padding-left: 40px">Elevation: <xsl:value-of select="river/source/elevation"/> m</p>
        <xsl:if test="//province">
           <p><b>Passes through: </b></p>
            <xsl:for-each select="//province">
                <xsl:sort select="text()"/>
                <xsl:if test="text() != '()'">
                    <p style="padding-left: 40px"><xsl:value-of select="text()"/> </p>
                </xsl:if>
            </xsl:for-each>
        </xsl:if>
        <p><b>Estuary: </b> <xsl:value-of select="river/estuary/located"/> </p>
        <p style="padding-left: 40px">Latitude: <xsl:value-of select="river/estuary/latitude"/> </p>
        <p style="padding-left: 40px">Longitude: <xsl:value-of select="river/estuary/longitude"/> </p>
        <p style="padding-left: 40px">Elevation: <xsl:value-of select="river/estuary/elevation"/> m</p>

    </xsl:template>
</xsl:stylesheet>