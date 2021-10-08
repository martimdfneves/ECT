<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <h3 style="color: black ">Sea:
            <xsl:value-of select="sea/name"/>
        </h3>
        <xsl:if test="sea/area">
            <p>
                <b>Area:</b>
                <xsl:value-of select="sea/area"/> km&#178;
            </p>
        </xsl:if>
        <xsl:if test="sea/depth">
            <p>
                <b>Max depth:</b>
                <xsl:value-of select="sea/depth"/> m
            </p>
        </xsl:if>
        <p>
            <b>Borders with(countries):</b>
        </p>
        <xsl:for-each select="sea//country">
            <xsl:sort select="text()"/>
            <p style="padding-left: 40px">
                <xsl:value-of select="text()"/>
            </p>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>