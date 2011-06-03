package csiebug.web.taglib.form.upload;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import csiebug.util.WebUtility;
import csiebug.web.html.form.upload.HtmlPlUpload;

public class PlUploadTag extends BodyTagSupport implements TryCatchFinally {
	private static final long serialVersionUID = 1L;
	
	private String uploadId;
	//General options
	private String runtimes;
	private String url;
	private String maxFileSize;
	private String chunkSize;
	private String uniqueNames;
	private String resizeWidth;
	private String resizeHeight;
	private String resizeQuality;
	private String filters;
	private String flashSWFURL;
	private String silverlightXAPURL;
	private String browseButton;
	private String dropElement;
	private String container;
	private String multipart;
	private String multipartParams;
	private String requiredFeatures;
	private String headers;
	
	//Queue widget specific options
	private String preinit;
	private String dragdrop;
	private String rename;
	private String multipleQueues;
	private String urlstreamUpload;
	
	private WebUtility webutil = new WebUtility();
	
	public int doStartTag() throws JspException {
		try { 
            JspWriter out = pageContext.getOut();
            
            @SuppressWarnings("unchecked")
			HtmlPlUpload htmlPlUpload = new HtmlPlUpload(uploadId, runtimes, url, maxFileSize, chunkSize, uniqueNames, resizeWidth, resizeHeight, resizeQuality, (List<Map<String, String>>)webutil.getRequestAttribute(filters), flashSWFURL, silverlightXAPURL, browseButton, dropElement, container, multipart, multipartParams, requiredFeatures, headers, preinit, dragdrop, rename, multipleQueues, urlstreamUpload);
            out.println(htmlPlUpload.render());
        } catch(Exception e) {
        	throw new JspException(e);
        } 
 
        return SKIP_BODY; 
	}
	
	public void doCatch(Throwable e) throws Throwable {
		throw new JspException("PlUploadTag Problem: " + e.getMessage());
	}

	public void doFinally() {
		
	}

	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}

	public String getUploadId() {
		return uploadId;
	}

	public void setRuntimes(String runtimes) {
		this.runtimes = runtimes;
	}

	public String getRuntimes() {
		return runtimes;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setMaxFileSize(String maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public String getMaxFileSize() {
		return maxFileSize;
	}

	public void setChunkSize(String chunkSize) {
		this.chunkSize = chunkSize;
	}

	public String getChunkSize() {
		return chunkSize;
	}

	public void setUniqueNames(String uniqueNames) {
		this.uniqueNames = uniqueNames;
	}

	public String getUniqueNames() {
		return uniqueNames;
	}

	public void setResizeWidth(String resizeWidth) {
		this.resizeWidth = resizeWidth;
	}

	public String getResizeWidth() {
		return resizeWidth;
	}

	public void setResizeHeight(String resizeHeight) {
		this.resizeHeight = resizeHeight;
	}

	public String getResizeHeight() {
		return resizeHeight;
	}

	public void setResizeQuality(String resizeQuality) {
		this.resizeQuality = resizeQuality;
	}

	public String getResizeQuality() {
		return resizeQuality;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public String getFilters() {
		return filters;
	}

	public void setFlashSWFURL(String flashSWFURL) {
		this.flashSWFURL = flashSWFURL;
	}

	public String getFlashSWFURL() {
		return flashSWFURL;
	}

	public void setSilverlightXAPURL(String silverlightXAPURL) {
		this.silverlightXAPURL = silverlightXAPURL;
	}

	public String getSilverlightXAPURL() {
		return silverlightXAPURL;
	}

	public void setBrowseButton(String browseButton) {
		this.browseButton = browseButton;
	}

	public String getBrowseButton() {
		return browseButton;
	}

	public void setDropElement(String dropElement) {
		this.dropElement = dropElement;
	}

	public String getDropElement() {
		return dropElement;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public String getContainer() {
		return container;
	}

	public void setMultipart(String multipart) {
		this.multipart = multipart;
	}

	public String getMultipart() {
		return multipart;
	}
}
