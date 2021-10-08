<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <h3 style="color: black ">Province:
            <xsl:value-of select="province/name"/>
        </h3>
        <xsl:if test="province/name != ''">
            <p>
                <b>Name:</b>
                <xsl:value-of select="province/name"/>
            </p>
        </xsl:if>
        <xsl:if test="province/localname != ''">
            <p>
                <b>Local Name:</b>
                <xsl:value-of select="province/localname"/>
            </p>
        </xsl:if>
        <xsl:if test="province/area != ''">
            <p>
                <b>Area:</b>
                <xsl:value-of select="province/area"/>
            </p>
        </xsl:if>
        <xsl:if test="province/population != ''">
            <p>
                <b>Population:</b>
                <xsl:value-of select="province/population"/>
            </p>
        </xsl:if>
        <xsl:if test="province//city != ''">
            <p>
                <b>City(ies):</b>
            </p>
            <xsl:for-each select="province//city">
                <xsl:sort select="name"/>
                <a style="color: black">
                    <xsl:attribute name="href">/city/<xsl:value-of select="@id"/>
                    </xsl:attribute>
                    <p style="padding-left: 40px">
                        <xsl:value-of select="name/text()"/>
                    </p>
                </a>
            </xsl:for-each>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>