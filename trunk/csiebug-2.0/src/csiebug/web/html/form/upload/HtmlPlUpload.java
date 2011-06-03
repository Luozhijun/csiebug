package csiebug.web.html.form.upload;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import csiebug.util.AssertUtility;
import csiebug.util.WebUtility;
import csiebug.web.html.HtmlBuilder;
import csiebug.web.html.HtmlComponentNoBody;
import csiebug.web.html.HtmlRenderException;

public class HtmlPlUpload extends HtmlComponentNoBody {
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
	private List<Map<String, String>> filters;
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
	
	public HtmlPlUpload(String id, String runtimes, String url, String maxFileSize, String chunkSize, String uniqueNames, String resizeWidth, String resizeHeight, String resizeQuality, List<Map<String, String>> filters, String flashSWFURL, String silverlightXAPURL, String browseButton, String dropElement, String container, String multipart, String multipartParams, String requiredFeatures, String headers, String preinit, String dragdrop, String rename, String multipleQueues, String urlstreamUpload) {
		this.uploadId = id;
		
		//General options
		this.runtimes = runtimes;
		this.url = url;
		this.maxFileSize = maxFileSize;
		this.chunkSize = chunkSize;
		this.uniqueNames = uniqueNames;
		this.resizeWidth = resizeWidth;
		this.resizeHeight = resizeHeight;
		this.resizeQuality = resizeQuality;
		this.filters = filters;
		this.flashSWFURL = flashSWFURL;
		this.silverlightXAPURL = silverlightXAPURL;
		this.browseButton = browseButton;
		this.dropElement = dropElement;
		this.container = container;
		this.multipart = multipart;
		this.multipartParams = multipartParams;
		this.requiredFeatures = requiredFeatures;
		this.headers = headers;
		
		//Queue widget specific options
		this.preinit = preinit;
		this.dragdrop = dragdrop;
		this.rename = rename;
		this.multipleQueues = multipleQueues;
		this.urlstreamUpload = urlstreamUpload;	
	}
	
	@Override
	public String renderStart() throws HtmlRenderException {
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		
		if(AssertUtility.isNotNullAndNotSpace(uploadId)) {
			try {
				makeOnLoadScript();
			} catch (NamingException e) {
				throw new HtmlRenderException(e);
			} catch (UnsupportedEncodingException e) {
				throw new HtmlRenderException(e);
			}
			
			htmlBuilder.divStart().id(uploadId).tagClose().p().text("You browser doesn't have Flash, Silverlight, Gears, BrowserPlus or HTML5 support.").pEnd().divEnd();
		}
		
		return htmlBuilder.toString();
	}

	@Override
	public String renderEnd() throws HtmlRenderException {
		return "";
	}
	
	private void makeOnLoadScript() throws NamingException, UnsupportedEncodingException {
		StringBuffer script = new StringBuffer();
		
		script.append("$('#" + uploadId + "').pluploadQueue({\n");
		boolean hasOneAttribute = false;
		if(AssertUtility.isNotNullAndNotSpace(runtimes)) {
			script.append("	runtimes : '" + runtimes + "'");
			hasOneAttribute = true;
		}
		setStringAttribute(script, "url", url, hasOneAttribute);
		setStringAttribute(script, "max_file_size", maxFileSize, hasOneAttribute);
		setStringAttribute(script, "chunk_size", chunkSize, hasOneAttribute);
		setStringAttribute(script, "unique_names", uniqueNames, hasOneAttribute);
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("width", resizeWidth);
		map.put("height", resizeHeight);
		map.put("quality", resizeQuality);
		setMapAttribute(script, "resize", map, hasOneAttribute);
		setListMapAttribute(script, "filters", filters, hasOneAttribute);
		setStringAttribute(script, "flash_swf_url", flashSWFURL, hasOneAttribute);
		setStringAttribute(script, "silverlight_xap_url", silverlightXAPURL, hasOneAttribute);
		setStringAttribute(script, "browse_button", browseButton, hasOneAttribute);
		setStringAttribute(script, "drop_element", dropElement, hasOneAttribute);
		setStringAttribute(script, "container", container, hasOneAttribute);
		setStringAttribute(script, "multipart", multipart, hasOneAttribute);
		script.append("\n});\n");

		webutil.addPageLoadScript(script.toString());
	}
	
	private void setStringAttribute(StringBuffer script, String key, String value, boolean hasOneAttribute) {
		if(AssertUtility.isNotNullAndNotSpace(value)) {
			if(hasOneAttribute) {
				script.append(",\n");
			}
			script.append("	" + key + " : '" + value + "'");
			hasOneAttribute = true;
		}
	}
	
	private void setMapAttribute(StringBuffer script, String key, Map<String, String> values, boolean hasOneAttribute) {
		if(!AssertUtility.isAllElementNull(values)) {
			if(hasOneAttribute) {
				script.append(",\n");
			}
			script.append("	" + key + " : " + getMapAttribute(values));
			hasOneAttribute = true;
		}
	}
	
	private String getMapAttribute(Map<String, String> values) {
		if(!AssertUtility.isAllElementNull(values)) {
			StringBuffer value = new StringBuffer("{");
			Iterator<Map.Entry<String, String>> iterator = values.entrySet().iterator();
			while(iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				if(entry.getValue() != null) {
					if(!value.toString().trim().equals("")) {
						value.append(", ");
					}
					
					value.append(entry.getKey() + " : " + entry.getValue());
				}
			}
			value.append("}");
			
			return value.toString();
		} else {
			return "";
		}
	}
	
	private void setListMapAttribute(StringBuffer script, String key, List<Map<String, String>> values, boolean hasOneAttribute) {
		if(values != null && values.size() != 0) {
			if(hasOneAttribute) {
				script.append(",\n");
			}
			script.append("	" + key + " : [\n");
			for(int i = 0; i < values.size(); i++) {
				if(i != 0) {
					script.append(",\n");
				}
				script.append("		" + getMapAttribute(values.get(i)));
			}
			script.append("	]");
			hasOneAttribute = true;
		}
	}
}
