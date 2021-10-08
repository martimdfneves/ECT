<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xss="http://www.w3.org/1999/XSL/Transform">


    <xsl:template match="/">
        <h2>
            <xss:value-of select="rss/channel/title"/>
        </h2>
        <div class="row">
            <br></br>
            <xsl:apply-templates select="//item" mode="inner"/>
        </div>
    </xsl:template>
    <xsl:template match="item" mode="inner">

        <div class="col-lg-4 col-sm-6 mb-4" style="min-width: 0;
                                                    word-wrap: break-word;
                                                    background-color: #fff;
                                                    background-clip: border-box;
                                                    border: 1px solid rgba(0, 0, 0, 0.125);
                                                    border-radius: 0.25rem;
                                                    border-bottom-right-radius: 0.25rem;
                                                    border-bottom-left-radius: 0.25rem;">
            <div class="portfolio-item">
                <br/>
                <br/>
                <h2>
                    <a style="color: #009933">
                        <xsl:attribute name="href">
                            <xsl:value-of select="link"/>
                        </xsl:attribute>
                        <xsl:value-of select="title"/>
                    </a>
                </h2>
                <br/>
                <p>
                    <xsl:value-of select="description"/>
                </p>
                <div class="portfolio-caption" style="color: #484848">
                    <xsl:value-of select="pubDate"/>
                    <br/>
                    <xsl:value-of select="author"/>
                </div>
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>